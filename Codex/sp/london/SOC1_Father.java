/**
 * Society 1 Father 27.2 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC1_Father extends Codex
{
	private LondonChronicle	chronScript;

	private float			_duration = (float)3.0;
	private float[]			pos;

	private static final int TIMER_ID_BREAKVATS		= 0;
	private static final int TIMER_ID_BROKEVATS		= 1;
	private static final int TIMER_ID_OPENDOOR		= 2;
	private static final int TIMER_ID_PLAYSPECIAL2	= 3;

	private CodexActor		_Christof;
	private CodexActor		_Leo;
	private CodexRegion		_LeoRegion;

	private CodexThing		_BloodVats;
	private CodexThing		_ExitDoorHalf1;
	private CodexThing		_ExitDoorHalf2;
	private CodexThing		_Blood;
	private CodexThing		_BloodSpawnSpot;

	private int				christofGUID;
	private int				leoGUID;

	private boolean			bOpen = false;

	private boolean			bFatherConversation = false;
	private boolean			bLap1Conversation = false;
	private boolean			bLap2Conversation = false;

	public static String _params[] = {"Leo region", "Blood vats", "Blood Spawn Spot", "Exit door half1", 
										"Exit door half2", "Door open duration;3.0"};

	public SOC1_Father(CodexRegion LeoRegion, CodexThing BloodVats, CodexThing BloodSpawnSpot, CodexThing ExitDoorHalf1, 
						CodexThing ExitDoorHalf2, float duration)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_Leo = new CodexActor(GetClassThing());

		_duration = duration;

		_LeoRegion = new CodexRegion(LeoRegion.GetGUID());
		_BloodVats = new CodexThing(BloodVats.GetGUID());
		_ExitDoorHalf1 = new CodexThing(ExitDoorHalf1.GetGUID());
		_ExitDoorHalf2 = new CodexThing(ExitDoorHalf2.GetGUID());
		_BloodSpawnSpot = new CodexThing(BloodSpawnSpot.GetGUID());

		CaptureThing(_LeoRegion.GetGUID());
		CaptureThing(_BloodVats.GetGUID());
		CaptureThing(_ExitDoorHalf1.GetGUID());
		CaptureThing(_ExitDoorHalf2.GetGUID());
		CaptureThing(_BloodSpawnSpot.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		leoGUID = CodexThing.GuidFromCastID("Leo");

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.SOC1_FATHER))
		{
			// set these so you can't attack him before the conversation
			_Leo.SetActorFlags(THING_AF_AIPAUSED);
			_Leo.SetActorFlags(THING_AF_INVUL);
			_Leo.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		_ExitDoorHalf1.SetThingFlags(THING_FLAG_VISBLOCK);
		_ExitDoorHalf2.SetThingFlags(THING_FLAG_VISBLOCK);
	}	
	
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _LeoRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SOC1_FATHER))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOC1_FATHER);

			FatherConversation(causeGUID, 0);

			// clear his ai flag so he'll fight after the conversation
			_Leo.ClearActorFlags(THING_AF_AIPAUSED);
			_Leo.ClearActorFlags(THING_AF_INVUL);
			_Leo.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(!bOpen &&
			(guid == _ExitDoorHalf1.GetGUID() ||
			guid == _ExitDoorHalf2.GetGUID()))
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");

			return;
		}

		if(guid == _BloodVats.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SOC1_VATSBROKEN))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOC1_VATSBROKEN);

			_BloodVats.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// pause his ai so he stops attacking chris from here on out
			// and so he won't die - aiOff here in general so Chris doesn't
			// get damaged
			AIOff();
			_Leo.SetActorFlags(THING_AF_AIPAUSED);
			_Leo.SetActorFlags(THING_AF_INVUL);
			_Leo.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// move him out of the way of the vats falling
			_Leo.SetPosition(_Leo.GetFramePosition(1));
			_Leo.SetOrientation(_Leo.GetFrameOrientation(1));

			_Leo.LookAtThing(christofGUID);

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LeoVitae.ncp", 30);
			
			SetTimer(0, TIMER_ID_BREAKVATS, clickerGuid);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Leo.GetGUID())
		{
			// lose humanity here
			CodexActor Christof = new CodexActor(christofGUID);
			Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);

			SetTimer(1, TIMER_ID_OPENDOOR);

			// make vats not-highlightable
			_BloodVats.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_BloodVats.SetCollideType(THING_COLLIDE_NONE);
		}
	}


	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_BREAKVATS:

				pos = _BloodVats.GetPosition();
				_Christof.SetPosition(pos);

				pos = _BloodVats.GetOrientation();
				_Christof.SetOrientation(pos);
				
				_Christof.PlayMotionSetMode(MOTION_SPECIAL13, false, (float)30.0);
				_BloodVats.PlayMotionSetMode(MOTION_SPECIAL13, false, (float)30.0);

				_Leo.PlayMotionSetMode(MOTION_SPELL, false, (float)30.0);

				SetTimer((float)10.0, TIMER_ID_BROKEVATS);
				break;
		
			case TIMER_ID_BROKEVATS:

				_Blood = new CodexThing(_BloodSpawnSpot.SpawnThing("bloodPool"));

				float vecScale[] = new float[3];

				vecScale[VEC_X] = 12.0f;
				vecScale[VEC_Y] = 12.0f;
				vecScale[VEC_Z] = 1.0f;
				_Blood.SetScale(vecScale, 10.0f);

				SetTimer(1, TIMER_ID_OPENDOOR);
				_Christof.StopActorAction();
				int duration = _Leo.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
				SetTimer((float)duration/1000, TIMER_ID_PLAYSPECIAL2);
				Lap1Conversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_OPENDOOR:

				CodexSound.PopMusic();

				// open door so player can exit
				_ExitDoorHalf1.RotatePivot(1, _duration);
				_ExitDoorHalf2.RotatePivot(1, _duration);

				//_ExitDoorHalf1.ClearThingFlags(THING_FLAG_VISBLOCK);
				//_ExitDoorHalf2.ClearThingFlags(THING_FLAG_VISBLOCK);

				_ExitDoorHalf1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
				_ExitDoorHalf2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

				bOpen = true;

				CodexQuest q = new CodexQuest(CodexQuest.Load("L1_EscapeSociety"));
				q.Complete();

				CodexQuest q2 = new CodexQuest(CodexQuest.Load("L1_FindShipping"));
				break;

			case TIMER_ID_PLAYSPECIAL2:

				_Leo.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
				break;
		}
	}

	public void FatherConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Boss_1.mp3", 50);
		bFatherConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "27_2_Father", "27_2_Father.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Lap1Conversation(int starterGuid, int npcGuid)
	{
		bLap1Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "27_2_Lap1", "27_2_Lap1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Lap2Conversation(int starterGuid, int npcGuid)
	{
		bLap2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "27_2_Lap2", "27_2_Lap2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bFatherConversation)
		{
			AIOn();
			bFatherConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bLap1Conversation)
		{
			if(CodexSequence.GetChronicleFlag(chronScript.SOC3_LEOJOURNAL))
			{
				Lap2Conversation(starterGuid, 0);
			}
			else
			{
				AIOn();
				CodexCamera.Release(starterGuid);

				// auto advance - they'll either get it here or the end of conversation2
				CodexSequence.Advance(christofGUID);
			}

			bLap1Conversation = false;
		}

		if(bLap2Conversation)
		{
			AIOn();
			bLap2Conversation = false;
			CodexCamera.Release(starterGuid);

			// auto advance - they'll either get it here or the end of conversation1
			CodexSequence.Advance(christofGUID);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bFatherConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, leoGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, leoGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, leoGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, leoGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bFatherConversation)

		if(bLap1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.LookAtThing(leoGUID);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LeoVitae.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bLap1Conversation)

		if(bLap2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LeoVitae.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bLap2Conversation)
	}
}

