/**
 * New York Dock 33.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class DOCK_33_1_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_STANDANDPEEK		= 0;
	private static final int	TIMER_ID_REGULARIDLE		= 1;
	
	private CodexRegion			_georgeRegion;
	private CodexRegion			_samuelRegion;
	private CodexActor			_georgeThorne;
	private CodexActor			_sabbat1;
	private CodexActor			_sabbat2;
	private CodexActor			_sabbat3;
	private CodexPlayer			_samuel;
	private CodexRegion			_HomelessRegion;
	private CodexActor			_homelessLady;

	private CodexPlayer			_Pink;
	private CodexPlayer			_Christof;

	private int					deadSabbat					= 0;

	private int					christofGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;
	private int					georgeGUID;

	private int					gogglesGuid;
	private int					warehouseGuid;

	private float				minFrequency				= 15;
	private float				maxFrequency				= 60;

	private int				convCounter = 0;

	public boolean			bHomeless1Conversation = false;
	public boolean			bHomeless2Conversation = false;
	public boolean			bHomeless3Conversation = false;
	public boolean			bHomeless4Conversation = false;


	public boolean				b33_1_MeetGeorgeConversation = false;
	public boolean				b33_2_SabbatAttackConversation = false;
	public boolean				b33_2_SaveSamuelConversation = false;
	public boolean				bHomelessConversation = false;
	public boolean				bGeorgeReturnConversation = false;

	public static String _params[] =	{"George Region", "Samuel Region", "George Thorne", 
										"Sabbat1", "Sabbat2", "Sabbat3", "Samuel", "Homeless region", "Homless Lady", "Warehouse"};

	public DOCK_33_1_Main(CodexRegion georgeRegion, CodexRegion samuelRegion, CodexActor georgeThorne, 
						CodexActor sabbat1, CodexActor sabbat2, CodexActor sabbat3, CodexPlayer samuel, CodexRegion HomelessRegion, CodexActor homelessLady, 
						CodexThing Warehouse)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_georgeRegion = new CodexRegion(georgeRegion.GetGUID());
		_samuelRegion = new CodexRegion(samuelRegion.GetGUID());
		_georgeThorne = new CodexActor(georgeThorne.GetGUID());
		_sabbat1 = new CodexActor(sabbat1.GetGUID());
		_sabbat2 = new CodexActor(sabbat2.GetGUID());
		_sabbat3 = new CodexActor(sabbat3.GetGUID());
		_samuel = new CodexPlayer(samuel.GetGUID());
		_HomelessRegion = new CodexRegion(HomelessRegion.GetGUID());
		_homelessLady = new CodexActor(homelessLady.GetGUID());
		
		warehouseGuid = Warehouse.GetGUID();

		CaptureThing(_georgeRegion.GetGUID());
		CaptureThing(_samuelRegion.GetGUID());
		CaptureThing(_georgeThorne.GetGUID());
		CaptureThing(_sabbat1.GetGUID());
		CaptureThing(_sabbat2.GetGUID());
		CaptureThing(_sabbat3.GetGUID());
		CaptureThing(_HomelessRegion.GetGUID());
		CaptureThing(_homelessLady.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		georgeGUID = _georgeThorne.GetGUID();

		_Pink = new CodexPlayer(pinkGUID);
		_Christof = new CodexPlayer(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.DEVN_GETCODES))
		{
			_georgeThorne.LookAtThing(warehouseGuid);
			_georgeThorne.OverrideActorWeapon("weapGoggles", false, false);
			_georgeThorne.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);

			SetTimer((float)(minFrequency + Math.random() * (maxFrequency - minFrequency)), TIMER_ID_STANDANDPEEK);
		}
		else		
		{
			// remove thorne from the level if they've gotten the warehouse quest
			_georgeThorne.Remove();
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.DOCK_SABBATATTACK))
		{
			// disable cop/enemy search until this fight scene is over
			AISetMainFlags(AIMAIN_FLAG_NOGUARDSEARCH);

			_sabbat1.SetActorFlags(THING_AF_INVUL);
			_sabbat1.SetActorFlags(THING_AF_AIPAUSED);
			_sabbat1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_sabbat2.SetActorFlags(THING_AF_INVUL);
			_sabbat2.SetActorFlags(THING_AF_AIPAUSED);
			_sabbat2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_sabbat3.SetActorFlags(THING_AF_INVUL);
			_sabbat3.SetActorFlags(THING_AF_AIPAUSED);
			_sabbat3.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		
		if(!CodexSequence.GetChronicleFlag(chronScript.DOCK_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.DOCK_FLYTHROUGH);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1.0, false);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_STANDANDPEEK:

				_georgeThorne.PlayMotionSetMode(MOTION_SPECIAL6, false, (float)30.0);
				SetTimer((float)8.6, TIMER_ID_REGULARIDLE);
				break;

			case TIMER_ID_REGULARIDLE:

				_georgeThorne.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
				SetTimer((float)(minFrequency + Math.random() * (maxFrequency - minFrequency)), TIMER_ID_STANDANDPEEK);
				break;
		}
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _georgeThorne.GetGUID())
		{
			// play default line here
			GeorgeReturnConversation(clickerGuid, 0);
		}

		if(guid == _homelessLady.GetGUID() && !CodexSequence.GetChronicleFlag(chronScript.DOCK_HOMELESS))
		{
//			CodexSequence.SetChronicleFlag(chronScript.DOCK_HOMELESS);
//			HomelessConversation(clickerGuid, 0);

			// convCounter is just used to cycle through each of the
			// four lines that homeless woman can say
			convCounter++;

			// if they've clicked on her and convCounter is more than 4, reset it to 1 
			if(convCounter > 4)
				convCounter = 1;

			switch(convCounter)
			{
				case 1:
					Homeless1Conversation(clickerGuid, 0);
					break;
				case 2:
					Homeless2Conversation(clickerGuid, 0);
					break;
				case 3:
					Homeless3Conversation(clickerGuid, 0);
					break;
				case 4:
					Homeless4Conversation(clickerGuid, 0);
					break;
			}
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _georgeRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.DOCK_MEETGEORGE))
		{
			CodexSequence.SetChronicleFlag(chronScript.DOCK_MEETGEORGE);

			c33_1_MeetGeorgeConversation(causeGUID, 0);
		}

		if(guid == _samuelRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.DOCK_SABBATATTACK))
		{
			CodexSequence.SetChronicleFlag(chronScript.DOCK_SABBATATTACK);

			c33_2_SabbatAttackConversation(causeGUID, 0);
		}

/*		if(guid == _HomelessRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.DOCK_HOMELESS))
		{
			CodexSequence.SetChronicleFlag(chronScript.DOCK_HOMELESS);

			HomelessConversation(causeGUID, 0);
		}
*/
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(++deadSabbat == 3)
		{
			// so samuel doesn't have invul set for the rest of the game
			// that'd be a very very bad thing
			_samuel.ClearActorFlags(THING_AF_INVUL);

			c33_2_SaveSamuelConversation(causeID, 0);
		}
	}

	public void Homeless1Conversation(int starterGuid, int npcGuid)
	{
		bHomeless1Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "33_1_Homeless1", "33_1_Homeless1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Homeless2Conversation(int starterGuid, int npcGuid)
	{
		bHomeless2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "33_1_Homeless2", "33_1_Homeless2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Homeless3Conversation(int starterGuid, int npcGuid)
	{
		bHomeless3Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "33_1_Homeless3", "33_1_Homeless3.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Homeless4Conversation(int starterGuid, int npcGuid)
	{
		bHomeless4Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "33_1_Homeless4", "33_1_Homeless4.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c33_1_MeetGeorgeConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		// Stop George's stakeout routine
		_georgeThorne.StopActorAction();
		KillTimer(TIMER_ID_STANDANDPEEK);
		KillTimer(TIMER_ID_REGULARIDLE);

		b33_1_MeetGeorgeConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "33_1_MeetGeorge", "33_1_MeetGeorge.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GeorgeReturnConversation(int starterGuid, int npcGuid)
	{
		bGeorgeReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "33_1_GeorgeReturn", "33_1_GeorgeReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c33_2_SabbatAttackConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_2.mp3", 50);
		b33_2_SabbatAttackConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "33_2_SabbatAttack", "33_2_SabbatAttack.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c33_2_SaveSamuelConversation(int starterGuid, int npcGuid)
	{
		b33_2_SaveSamuelConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "33_2_SaveSamuel", "33_2_SaveSamuel.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void HomelessConversation(int starterGuid, int npcGuid)
	{
		bHomelessConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "33_1_Homeless", "33_1_Homeless.nco", CONV_XFLAG_WANTFEEDBACK);
	}


	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b33_1_MeetGeorgeConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b33_1_MeetGeorgeConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_GetAccessCodes.nqd"));

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bGeorgeReturnConversation)
		{
			AIOn();
			bGeorgeReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b33_2_SabbatAttackConversation)
		{
			// so the sabbat can't kill him before he joins your party
			// that'd be a very bad thing
			_samuel.SetActorFlags(THING_AF_INVUL);

			_sabbat1.ClearActorFlags(THING_AF_INVUL);
			_sabbat1.ClearActorFlags(THING_AF_AIPAUSED);
			_sabbat1.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_sabbat2.ClearActorFlags(THING_AF_INVUL);
			_sabbat2.ClearActorFlags(THING_AF_AIPAUSED);
			_sabbat2.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_sabbat3.ClearActorFlags(THING_AF_INVUL);
			_sabbat3.ClearActorFlags(THING_AF_AIPAUSED);
			_sabbat3.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_samuel.StopActorAction();

			_sabbat1.AISetTarget(CodexPlayer.GetCurrentPlayer());
			_sabbat2.AISetTarget(CodexPlayer.GetCurrentPlayer());
			_sabbat3.AISetTarget(CodexPlayer.GetCurrentPlayer());

			AIOn();
			b33_2_SabbatAttackConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(b33_2_SaveSamuelConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b33_2_SaveSamuelConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);

			_samuel.AddToParty();

			// re-enable cop/enemy search
			AIClearMainFlags(AIMAIN_FLAG_NOGUARDSEARCH);

			CodexSequence.OpenExit("NewYorkDocks", 4);
			CodexSequence.OpenExit("DevNullApartment", 0);
		}

		if(bHomeless1Conversation)
		{
			AIOn();
			bHomeless1Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bHomeless2Conversation)
		{
			AIOn();
			bHomeless2Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bHomeless3Conversation)
		{
			AIOn();
			bHomeless3Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bHomeless4Conversation)
		{
			AIOn();
			bHomeless4Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b33_1_MeetGeorgeConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_georgeThorne.LookAtThing(christofGUID);
							CodexCamera.SetupCutscene(starterGuid, georgeGUID, pinkGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							// Delay while Christof shows ID
							_Christof.PlayMotionSetMode(MOTION_GESTURE3, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							_Christof.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, georgeGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, georgeGUID, pinkGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							// Get George ducking and peeking again
							_georgeThorne.LookAtThing(warehouseGuid);
							_georgeThorne.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
							SetTimer((float)(minFrequency + Math.random() * (maxFrequency - minFrequency)), TIMER_ID_STANDANDPEEK);
							
							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							//CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b33_2_SabbatAttackConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "SaveSamuelCloseUp.ncp", 30);
							break;

						case 1:

							_samuel.PlayMotionSetMode(MOTION_ACTION1, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "SaveSamuelOTS.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b33_2_SaveSamuelConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 12:

							//CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							_Pink.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							//CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							_Pink.StopActorAction();
							//CodexCamera.SetupCutscene(starterGuid, samuelGUID, pinkGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 17:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 18:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 19:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 20:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 21:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 22:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 23:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 24:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 25:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bHomeless1Conversation || bHomeless2Conversation || bHomeless3Conversation || bHomeless4Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bGeorgeReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(deadSabbat);
		CodexSequence.SaveInt(convCounter);
	}

	public void restore(int flags)
	{
		deadSabbat = CodexSequence.RestoreInt();
		convCounter = CodexSequence.RestoreInt();
	}
}
