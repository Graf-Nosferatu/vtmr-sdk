/**
 * Meet Vukodlak 40.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FLS2_MeetVuk extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int TIMER_ID_DROPCONVERSATION	= 1;
	private static final int TIMER_ID_DROP				= 2;

	private CodexActor		_Vukodlak;
	private CodexActor		_Anezka;
	private CodexActor		_Christof;
	private CodexActor		_Lily;
	private CodexActor		_Samuel;
	private CodexActor		_Wilhem;

	private CodexRegion		_VukodlakRegion;
	private CodexThing		_PitDoor;

	private int				christofGUID;
	private int				vukodlakGUID;
	private int				anezkaGUID;
	private int				lilyGUID;
	private int				samuelGUID;
	private int				wilhemGUID;

	private float			_humanity;

	private boolean			bVukDiablerizeConversation = false;
	private boolean			bVukFightConversation = false;
	private boolean			bVukConversation = false;
	private boolean			bDropConversation = false;

	public static String _params[] = {"Vukodlak", "Vukodlak Region", "Anezka", "Pit Door"};

	public FLS2_MeetVuk(CodexActor Vukodlak, CodexRegion VukodlakRegion, CodexActor Anezka, CodexThing PitDoor)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Vukodlak = new CodexActor(Vukodlak.GetGUID());
		_Anezka = new CodexActor(Anezka.GetGUID());
		_VukodlakRegion = new CodexRegion(VukodlakRegion.GetGUID());
		_PitDoor = new CodexThing(PitDoor.GetGUID());
		
		CaptureThing(_Vukodlak.GetGUID());
		CaptureThing(_VukodlakRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		vukodlakGUID = _Vukodlak.GetGUID();
		anezkaGUID = _Anezka.GetGUID();
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_Christof = new CodexActor(christofGUID);
		_Lily = new CodexActor(lilyGUID);
		_Samuel = new CodexActor(samuelGUID);
		_Wilhem = new CodexActor(wilhemGUID);

		_PitDoor.SetCollideType(THING_COLLIDE_NONE);

		_Anezka.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		// this is played so the rotation is correct
		_PitDoor.PlayMotionSetMode(MOTION_STAND, false, (float)30.0);

		if(!CodexSequence.GetChronicleFlag(chronScript.FLS2_MEETVUK))
		{
			_Vukodlak.SetActorFlags(THING_AF_AIPAUSED);
			_Vukodlak.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.FLS3_LIBUSSAHELP))
		{
			// remove everyone
			_Vukodlak.Remove();
			_Anezka.Remove();
		}
	}
	
	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!IsPlayerGuid(causeGuid))
			return;

		if(guid == _VukodlakRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.FLS2_MEETVUK))
		{
			CodexSequence.SetChronicleFlag(chronScript.FLS2_MEETVUK);

			CodexActor Christof = new CodexActor(christofGUID);
			_humanity = Christof.GetActorStat(ACTOR_STAT_HUMANITY);

			bVukConversation = true;
			AIOff();
			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);

			CodexSound.PushMusic("MD_Boss_2.mp3", 50);

			if(_humanity < 25)
			{
				// auto diablerize conversation here
				ExecuteConversation(causeGuid, 0, "40_1_MeetVukodlakDiablerize", "40_1_MeetVukodlakDiablerize.nco", CONV_XFLAG_WANTFEEDBACK);
			}
			else if(_humanity < 50)
			{
				// "normal" conversation, with choices
				ExecuteConversation(causeGuid, 0, "40_1_MeetVukodlak", "40_1_MeetVukodlak.nco", CONV_XFLAG_WANTFEEDBACK);
			}
			else
			{
				// auto "good" scene
				ExecuteConversation(causeGuid, 0, "40_1_MeetVukodlakFight", "40_1_MeetVukodlakFight.nco", CONV_XFLAG_WANTFEEDBACK);
			}
		}
	}
	
	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Vukodlak.GetGUID())
		{
			CodexActor Christof = new CodexActor(christofGUID);
			_humanity = Christof.GetActorStat(ACTOR_STAT_HUMANITY);

			_Vukodlak.SetActorFlags(THING_AF_AIPAUSED);

			if(_humanity < 25)
			{
				// play ending cinematic
				PlayVideo("Finale3.bik");	
			}
			else
			{
				// make him not dead
				_Vukodlak.SetActorHealth((float)100.0);
				_Vukodlak.ClearActorFlags(THING_AF_DEAD);

				SetTimer(2, TIMER_ID_DROPCONVERSATION, christofGUID);
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_DROPCONVERSATION:

				DropConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_DROP:

				_Christof.StopActorAction();
				_Lily.StopActorAction();
				_Samuel.StopActorAction();
				_Wilhem.StopActorAction();

				CodexSound.PopMusic();

				CodexSequence.Jump("CathedralOfFlesh3", 0);
				break;
		}
	}

	public void videoended(int id)
	{
		// roll credits, back to main menu, etc.
		CodexSequence.EndGame();
	}

	public void DropConversation(int starterGuid, int npcGuid)
	{
		bDropConversation = true;
		AIOff();
		_Vukodlak.SetPosition(_Vukodlak.GetFramePosition(1));
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "40_1_Drop", "40_1_Drop.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bVukConversation)
		{
			AIOn();
			bVukConversation = false;
			CodexCamera.Release(starterGuid);

			_Vukodlak.ClearActorFlags(THING_AF_AIPAUSED);
			_Vukodlak.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

			// they had a choice if their humanity was "mid-range"
			if((_humanity >= 25) && (_humanity < 50))
			{
				if(returnValue == 1)
				{
					// instant fade out to cover game coming back to gameplay mode before the credits
					CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.0, false);

					// play ending cinematic
					PlayVideo("Finale2.bik");
				}
				else if(returnValue == 2)
				{
					// instant fade out to cover game coming back to gameplay mode before the credits
					CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.0, false);

					// play ending cinematic
					PlayVideo("Finale3.bik");
				}
			}
		}
		
		if(bDropConversation)
		{
			//AIOn();
			bDropConversation = false;
			CodexCamera.Release(starterGuid);

			// open pit door
			_PitDoor.PlayMotionSetMode(MOTION_ACTION9, false, (float)30.0);
			//_PitDoor.Trigger(0, 0, 0, 0, 0, 0);

			// Fade out
		   	//CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			_Christof.PlayMotionSetMode(MOTION_ACTION9, false, (float)30.0);
			_Lily.PlayMotionSetMode(MOTION_ACTION9, false, (float)30.0);
			_Samuel.PlayMotionSetMode(MOTION_ACTION9, false, (float)30.0);
			_Wilhem.PlayMotionSetMode(MOTION_ACTION9, false, (float)30.0);

			SetTimer(2, TIMER_ID_DROP);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bVukConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVuk.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

						case 5:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 6:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 8:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVuk.ncp", 30);
							break;

						case 9:

							break;

						case 10:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							// Vukodlak gestures to Anezka
							_Vukodlak.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							_Vukodlak.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, anezkaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							_Vukodlak.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 15:

							_Vukodlak.StopActorAction();
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

						case 16:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 17:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 18:

							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 19:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 20:

							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 21:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 22:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 23:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

						case 24:

							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

						case 25:

							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bDropConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVuk.ncp", 30);
							break;

						case 1:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetVukOverhead.ncp", 30);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, vukodlakGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

