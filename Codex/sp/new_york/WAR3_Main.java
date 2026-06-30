/**
 * Warehouse 3 Main 36.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WAR3_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_PINKRUNNING	= 0;
	private static final int	TIMER_ID_PINKATTACK		= 1;
	private static final int	TIMER_ID_KILLAL			= 2;

	private	CodexRegion		_BigAlRegion;
	private CodexPlayer		_Pink;
	private CodexPlayer		_Wilhem;
	private CodexActor		_BigAl;
	private CodexActor		_Christof;
	private CodexThing		_AlChair;
	private CodexActor		_Lily;
	private CodexActor		_Samuel;

	private int				alGUID;
	private int				christofGUID;
	private int				lilyGUID;
	private int				pinkGUID;
	private int				samuelGUID;
	private int				wilhemGUID;
	
	private boolean			bBigAlConversation			= false;
	private boolean			bAlSneakConversation		= false;
	private boolean			bAlSquatConversation		= false;
	public boolean			bAfterAlAttackConversation	= false;
	private boolean			bPinkExposedConversation	= false;

	private int				pinkFrame = 0;

	private float[]			pos;
	private float[]			orientation;

	public static String _params[] =  {"Big Al Region", "Wilhem", "AlChair"};

	public WAR3_Main(CodexRegion BigAlRegion, CodexPlayer Wilhem, CodexThing AlChair)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_BigAlRegion = new CodexRegion(BigAlRegion.GetGUID());
		_Wilhem = new CodexPlayer(Wilhem.GetGUID());
		_AlChair = new CodexThing(AlChair.GetGUID());

		CaptureThing(_BigAlRegion.GetGUID());
		CaptureThing(_Wilhem.GetGUID());
		CaptureThing(_AlChair.GetGUID());

		pos = new float[3];
		orientation = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_BigAl = new CodexActor(CodexThing.GuidFromCastID("Al"));
		CaptureThing(_BigAl.GetGUID());
		alGUID = _BigAl.GetGUID();

		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");

		_Christof = new CodexActor(christofGUID);
		_Lily = new CodexActor(lilyGUID);
		_Samuel = new CodexActor(samuelGUID);
		wilhemGUID = _Wilhem.GetGUID();

		if(!CodexSequence.GetChronicleFlag(chronScript.WAR3_BIGAL))
		{
			// hides wilhem, makes them non-collide, and pauses his AI 
			// for his appearance during the final conversation
			_Wilhem.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Wilhem.SetCollideType(THING_COLLIDE_NONE);
			_Wilhem.SetActorFlags(THING_AF_AIPAUSED);
		}

		_BigAl.SetActorFlags(THING_AF_TALKTO);
		_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

		_Pink = new CodexPlayer(pinkGUID);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_PINKRUNNING:
				
				_Pink.PlayMotionSetMode(MOTION_SPECIAL6, false, (float)20.0);
				SetTimer((float)1.5, TIMER_ID_PINKATTACK);
				break;

			case TIMER_ID_PINKATTACK:
				
				pos = _BigAl.GetFramePosition(1);
				_Pink.SetPosition(pos);

				orientation = _BigAl.GetOrientation();
				_Pink.SetOrientation(orientation);
				
				_BigAl.PlayMotionSetMode(MOTION_SPECIAL7, false, (float)20.0);
				_AlChair.PlayMotionSetMode(MOTION_SPECIAL7, false, (float)20.0);
				_Pink.PlayMotionSetMode(MOTION_SPECIAL7, false, (float)20.0);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "PinkAttack.ncp", 30);
				SetTimer((float)8.0, TIMER_ID_KILLAL);
				break;

			case TIMER_ID_KILLAL:

				_BigAl.Remove();

				AfterAlAttackConversation(christofGUID, 0);
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(thingGuid == _Pink.GetGUID())
		{
			pinkFrame++;

			if(pinkFrame == 1)
			{
				// move pink to his last frame - quickly cause he's got cool celerity and stuff
				pos = _Wilhem.GetFramePosition(3);
				_Pink.SendActorToPos(pos, (float)200.0);
			}
			else if(pinkFrame == 2)
			{
				_Pink.Remove();
			}
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(guid == _BigAlRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.WAR3_BIGAL))
		{
			CodexSequence.SetChronicleFlag(chronScript.WAR3_BIGAL);

			_Christof.OverrideActorWeapon("Punch", false, false);
			_Pink.OverrideActorWeapon("Punch", false, false);
			_Samuel.OverrideActorWeapon("Punch", false, false);
			_Lily.OverrideActorWeapon("Punch", false, false);

			BigAlConversation(causeGUID, 0);
		}
	}

	public void BigAlConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Boss_1.mp3", 50);
		bBigAlConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "36_1_BigAl", "36_1_BigAl.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AlSneakConversation(int starterGuid, int npcGuid)
	{
		bAlSneakConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "36_1_AlSneak", "36_1_AlSneak.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void AlSquatConversation(int starterGuid, int npcGuid)
	{
		bAlSquatConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "36_1_AlSquat", "36_1_AlSquat.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AfterAlAttackConversation(int starterGuid, int npcGuid)
	{
		bAfterAlAttackConversation = true;
		AIOff();
		//CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "36_1_AfterAlAttack", "36_1_AfterAlAttack.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void PinkExposedConversation(int starterGuid, int npcGuid)
	{
		bPinkExposedConversation = true;		
		AIOff();
		//CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "36_1_PinkExposed", "36_1_PinkExposed.nco", CONV_XFLAG_WANTFEEDBACK);

		// free revive of christof here because of Pink leaving the party, potentially the only one left alive 
		_Christof.ReviveActor(100, 100);
	}


	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bBigAlConversation)
		{
			bBigAlConversation = false;
			//CodexCamera.Release(starterGuid);

			if(returnValue == 1)
			{
				// add conversation XP
				CodexPlayer.AwardPartyExperience(100);

				// sneak branch should be taken
				AlSneakConversation(starterGuid, 0);
			}
			else
			{
				// squat branch should be taken
				AlSquatConversation(starterGuid, 0);
			}
		}
		else if(bAlSneakConversation || bAlSquatConversation)
		{
			bAlSneakConversation = false;
			bAlSquatConversation = false;

			// either choice will still take us to the AfterAlAttack conversation
			SetTimer((float)0.1, TIMER_ID_PINKRUNNING);
		}
		else if(bAfterAlAttackConversation)
		{
			bAfterAlAttackConversation = false;

			PinkExposedConversation(starterGuid, 0);
		}
		else if(bPinkExposedConversation)
		{
			CodexSound.PopMusic();
			// final conversation in this set
			bPinkExposedConversation = false;
			AIOn();
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// add Wilhem to the party
			_Wilhem.AddToParty();

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_UseAccessCodes"));
			q.Complete();

			// open mortis for christof and obfuscate for lily
			CodexActor Christof = new CodexActor(christofGUID);
			CodexActor Lily = new CodexActor(lilyGUID);
			Christof.SetActorDisciplineLevel("ShamblingHordes", -1);
			Lily.SetActorDisciplineLevel("CloakOfShadows", -1);

			//String aFormat = "%A" + Christof.GetName() + "%g" + "DGRP_MORTIS";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));
 
			//String aFormat2 = "%A" + Lily.GetName() + "%g" + "DGRP_OBFUSCATE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat2));
			
			CodexSequence.ChangeScene("BarclayLobby", "LOBY_37_4.nsd");
			CodexSequence.ChangeScene("NewYorkUptown", "UTWN_38_1.nsd");

			// auto advance
			CodexSequence.Advance(christofGUID);

			_Christof.CancelOverrideActorWeapon();
			_Samuel.CancelOverrideActorWeapon();
			_Lily.CancelOverrideActorWeapon();
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bBigAlConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 1:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 2:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPOV.ncp", 30);
							break;

						case 3:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 5:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 6:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 8:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 9:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPOV.ncp", 30);
							break;

						case 10:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 11:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							break;

						case 12:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bBigAlConversation)

		if(bAlSneakConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 1:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;
					} // switch(curLine)
					break;

			} // switch(curEvent)
		} // if(bAlSneakConversation)

		if(bAlSquatConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 1:

							_BigAl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;

			} // switch(curEvent)
		} // if(bAlSquatConversation)

		if(bAfterAlAttackConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Pink.LookAtThing(christofGUID);
							_Pink.PlayMotionSetMode(MOTION_SPECIAL8, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "PinkAttack.ncp", 30);
							break;

						case 1:

							_Pink.StopActorAction();
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WAR3AlPartyAngB.ncp", 30);
							break;

						case 2:

							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bPinkExposedConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							// show, set collide type back to cylinder, 
							// and turn the ai back on for wilhem
							_Wilhem.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_Wilhem.SetCollideType(THING_COLLIDE_CYL);
							_Wilhem.ClearActorFlags(THING_AF_AIPAUSED);

							// play door closing sound
							new CodexSound("woodenDoorMedium_close_02.WAV", 500, 1000, 100, 0, 0, wilhemGUID);

							// wilhem walks in
							pos = _Wilhem.GetFramePosition(1);
							_Wilhem.SendActorToPos(pos, (float)90.0);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							// Make Christof look at Wilhem
							_Christof.LookAtThing(wilhemGUID);
							_Lily.LookAtThing(lilyGUID);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 10:

							_Pink.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Pink.RemoveFromParty();
							break;

						case 11:

							// Fade Pink out
							_Pink.SpawnThing("blueMagic");
							_Pink.SetAlpha(.1f, 2000f);
							break;

						case 12:

							//*******************************************************
							// remove Pink from the party - and he 'escapes'
							//_Pink.RemoveFromParty();

							// or just remove him if it's problematic
							_Pink.Remove();
							
							// fake casting obfuscate
							//_Pink.SetAlpha((float)0.6);
							// move him the heck out of there - quickly cause he's got cool celerity and stuff
							//pos = _Wilhem.GetFramePosition(2);
							//_Pink.SendActorToPos(pos, (float)210.0);

							//*******************************************************

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 14:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 15:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 16:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 17:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 18:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 19:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 20:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 21:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 22:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 23:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 24:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 25:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 26:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 27:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 28:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 29:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 30:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bPinkExposedConversation)
	}
}

