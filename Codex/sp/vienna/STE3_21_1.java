/**
 * Stephansdom3 21.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STE3_21_1 extends Codex
{
	private ViennaChronicle	chronScript;

	private static final int TIMER_ID_SHADOWS			= 1;
	private static final int TIMER_ID_DARKHUNTER		= 2;
	private static final int TIMER_ID_SKYLIGHTDAMAGE	= 3;
	
	private CodexActor			_Luther;
	private	CodexRegion			_LutherRegion;
	private CodexThing			_door;
	private CodexRegion			_DarkHunterRegion;
	private	CodexRegion			_PortraitRegion;
	private CodexRegion			_SkylightDamageRegion;
	private	CodexThing			_SkylightDoors;

	private int					christofGUID;
	private int					erikGUID;
	private int					lutherGUID;
	private int					serenaGUID;
	private int					wilhemGUID;

	private CodexPlayer			christofPlayer;
	private CodexPlayer			erikPlayer;
	private CodexPlayer			serenaPlayer;
	private CodexPlayer			wilhemPlayer;

	private int					_duration;

	private int					humanityTrack = 0;

	private boolean				bGaveSkylightKey = false;

	private boolean				bChristofInSun = false;
	private boolean				bErikInSun = false;
	private boolean				bSerenaInSun = false;
	private boolean				bWilhemInSun = false;

	private boolean				bLutherConversation = false;
	private boolean				bPortraitConversation = false;
	private boolean				bShadowsConversation = false;
	private boolean				bDarkHunterConversation = false;
	public boolean				bLutherReturnConversation = false;

	public static String _params[] = {"Luther", "Luther Region", "Door leading to puzzle", "Darkhunter conversation region", "Portrait conversation region", 
									"Skylight damage region", "Skylight doors"};


	public STE3_21_1(CodexActor Luther, CodexRegion LutherRegion, CodexThing door, CodexRegion DarkHunterRegion, CodexRegion PortraitRegion, 
					CodexRegion SkylightDamageRegion, CodexThing SkylightDoors)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_Luther = new CodexActor(Luther.GetGUID());
		_LutherRegion = new CodexRegion(LutherRegion.GetGUID());
		_door = new CodexThing(door.GetGUID());
		_DarkHunterRegion = new CodexRegion(DarkHunterRegion.GetGUID());
		_PortraitRegion = new CodexRegion(PortraitRegion.GetGUID());
		_SkylightDamageRegion = new CodexRegion(SkylightDamageRegion.GetGUID());
		_SkylightDoors = new CodexThing(SkylightDoors.GetGUID());

		CaptureThing(_Luther.GetGUID());
		CaptureThing(_LutherRegion.GetGUID());
		CaptureThing(_door.GetGUID());
		CaptureThing(_DarkHunterRegion.GetGUID());
		CaptureThing(_PortraitRegion.GetGUID());
		CaptureThing(_SkylightDamageRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		lutherGUID = CodexThing.GuidFromCastID("Luther");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		christofPlayer = new CodexPlayer(christofGUID);
		erikPlayer = new CodexPlayer(erikGUID);
		serenaPlayer = new CodexPlayer(serenaGUID);
		wilhemPlayer = new CodexPlayer(wilhemGUID);

		_door.SetDescriptionID("GEN_DOOR");

		if(!CodexSequence.GetChronicleFlag(chronScript.STE3_SHADOWS))
		{
			// play shadows conversation right away
			SetTimer(2, TIMER_ID_SHADOWS, clientGuid);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.STE3_LUTHERTALKED))
		{
			bGaveSkylightKey = TRUE;
		}
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		// on endscene, remove the sun effect from all the party
		// members

		// if some die in the sun because of the sun (or were dead before)
		// they'll still have the effect on them when the level transition
		// takes place

		christofPlayer.RemoveActorEffect("ef_dmg_sun");	
		erikPlayer.RemoveActorEffect("ef_dmg_sun");
		serenaPlayer.RemoveActorEffect("ef_dmg_sun");
		wilhemPlayer.RemoveActorEffect("ef_dmg_sun");
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _door.GetGUID())
		{
			if(bGaveSkylightKey)
			{
				// open door that leads to the upstairs puzzle area
				// frame = 1, speed = 100
				_door.MoveToFrame(1, 100);		
			}
			else
			{
				// sound indicating this door is locked
				new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, GetClassThing());

				// display locked message
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");
			}
		}

		if(guid == _Luther.GetGUID())
		{
			// play default line here
			LutherReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _SkylightDamageRegion.GetGUID())
		{
			if(causeGUID == christofGUID)
			{
				bChristofInSun = true;
			}
			else if(causeGUID == erikGUID)
			{
				bErikInSun = true;
			}
			else if(causeGUID == serenaGUID)
			{
				bSerenaInSun = true;
			}
			else if(causeGUID == wilhemGUID)
			{
				bWilhemInSun = true;
			}

			// if the skylight is open
			if(CodexSequence.GetChronicleFlag(chronScript.STE3_SKYLIGHTOPEN))
			{
				SetTimer((float)0.0, TIMER_ID_SKYLIGHTDAMAGE);
			}				
		}
		else if(guid == _LutherRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.STE3_LUTHERTALKED))
		{
			CodexSequence.SetChronicleFlag(chronScript.STE3_LUTHERTALKED);

			LutherConversation(causeGUID, 0);
		}
		else if(guid == _PortraitRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.STE3_PORTRAIT))
		{
			CodexSequence.SetChronicleFlag(chronScript.STE3_PORTRAIT);

			PortraitConversation(causeGUID, 0);
		}
		else if(guid == _DarkHunterRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.STE3_DARKHUNTER))
		{
			CodexSequence.SetChronicleFlag(chronScript.STE3_DARKHUNTER);

			DarkHunterConversation(causeGUID, 0);
		}
	}

	public void exited(int guid, int causeGUID, int captureID)
	{
		if(guid == _SkylightDamageRegion.GetGUID())
		{
			if(causeGUID == christofGUID)
			{
				christofPlayer.RemoveActorEffect("ef_dmg_sun");
				bChristofInSun = false;
			}
			else if(causeGUID == erikGUID)
			{
				erikPlayer.RemoveActorEffect("ef_dmg_sun");
				bErikInSun = false;
			}
			else if(causeGUID == serenaGUID)
			{
				serenaPlayer.RemoveActorEffect("ef_dmg_sun");
				bSerenaInSun = false;
			}
			else if(causeGUID == wilhemGUID)
			{
				wilhemPlayer.RemoveActorEffect("ef_dmg_sun");
				bWilhemInSun = false;
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_SHADOWS:

				CodexSequence.SetChronicleFlag(chronScript.STE3_SHADOWS);

				ShadowsConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_DARKHUNTER:

				DarkHunterConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_SKYLIGHTDAMAGE:

				CodexSequence.SetChronicleFlag(chronScript.STE3_SKYLIGHTOPEN);

				if(bChristofInSun)
				{
					// damage him
					if(christofPlayer.FindActorEffect("ef_dmg_sun") == 0)
						christofPlayer.AddActorEffectByValue("ef_dmg_sun", 0, 8.0f, 0, 0);
				}

				if(bErikInSun)
				{
					// damage him
					if(erikPlayer.FindActorEffect("ef_dmg_sun") == 0)
						erikPlayer.AddActorEffectByValue("ef_dmg_sun", 0, 8.0f, 0, 0);
				}

				if(bSerenaInSun)
				{
					// damage her
					if(serenaPlayer.FindActorEffect("ef_dmg_sun") == 0)
						serenaPlayer.AddActorEffectByValue("ef_dmg_sun", 0, 8.0f, 0, 0);
				}

				if(bWilhemInSun)
				{
					// damage him
					if(wilhemPlayer.FindActorEffect("ef_dmg_sun") == 0)
						wilhemPlayer.AddActorEffectByValue("ef_dmg_sun", 0, 8.0f, 0, 0);
				}

				break;
		}
	}

	public void LutherConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_1.mp3", 50);
		bLutherConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "21_1_MeetLuther", "21_1_MeetLuther.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LutherReturnConversation(int starterGuid, int npcGuid)
	{
		bLutherReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "21_1_LutherReturn", "21_1_LutherReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void PortraitConversation(int starterGuid, int npcGuid)
	{
		bPortraitConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "21_1_Portraits", "21_1_Portraits.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void ShadowsConversation(int starterGuid, int npcGuid)
	{
		bShadowsConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "21_1_Shadows", "21_1_Shadows.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void DarkHunterConversation(int starterGuid, int npcGuid)
	{
		bDarkHunterConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "21_1_DarkHunter", "21_1_DarkHunter.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLutherConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bLutherConversation = false;
			CodexCamera.Release(starterGuid);

			// if they weren't given the key, luther opens the skylight himself so we
			// start damaging the players
			if(!bGaveSkylightKey)
			{
				// open skylight - send trigger to STE3_LutherGlass script
				_SkylightDoors.Trigger(0, 0, 0, 0, 0, 0);

				// because of the teleport to conversation, they're all in the sun here
				bChristofInSun = true;
				bErikInSun = true;
				bSerenaInSun = true;
				bWilhemInSun = true;

				SetTimer((float)8.0, TIMER_ID_SKYLIGHTDAMAGE);
			}
			else
			{
				// add conversation XP
				CodexPlayer.AwardPartyExperience(50);
			}

			if(humanityTrack == 1)
			{
				// add humanity here
				christofPlayer.AddActorEffectByLevel("ef_increasehumanity", 0, 1, 0, 0);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LutherPOV.ncp", 30);
			}
		}

		if(bLutherReturnConversation)
		{
			AIOn();
			bLutherReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bPortraitConversation)
		{
			AIOn();
			bPortraitConversation = false;
			CodexCamera.Release(starterGuid);

			//SetTimer(0.5f, TIMER_ID_DARKHUNTER);
		}

		if(bShadowsConversation)
		{
			AIOn();
			bShadowsConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bDarkHunterConversation)
		{
			AIOn();
			bDarkHunterConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bLutherConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "GlorifyCainWindow.ncp", 30);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OverLutherAtParty.ncp", 30);
							break;

						case 3:

							//CodexCamera.SetupCutscene(starterGuid, christofGUID, lutherGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							// play luther extending his arms onto the cross here
							_Luther.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LutherPOV.ncp", 30);
							break;

						case 7:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OverLutherAtParty.ncp", 30);
							break;

						case 10:

							// play new talk animation
							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);

							//CodexCamera.SetupCutscene(starterGuid, christofGUID, erikGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							//CodexCamera.SetupCutscene(starterGuid, christofGUID, lutherGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							//CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OverLutherAtParty.ncp", 30);
							break;

						case 14:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LutherPOV.ncp", 30);
							break;

						case 15:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 17:

							// play new talk animation
							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OverLutherAtParty.ncp", 30);
							break;

						case 18:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 19:

							// play new talk animation
							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							break;

						case 20:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 21:

							// play new talk animation
							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OverLutherAtParty.ncp", 30);
							break;

						case 22:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 23:

							// play new talk animation
							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OverLutherAtParty.ncp", 30);
							break;

						case 24:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 25:

							// play new talk animation
							_Luther.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							break;

						case 26:

							_Luther.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 27:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LutherPOV.ncp", 30);
							bGaveSkylightKey = true;
							break;
						
					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:
					
							humanityTrack = 1;
							break;
						
					} // switch(curLine)
					break;

			} // switch(curEvent)
		}

		if(bPortraitConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bShadowsConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bDarkHunterConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.SetupCutscene(starterGuid, serenaGUID, wilhemGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bLutherReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LutherPOV.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}






