/**
 * Cargo Ship 32.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CARG_32_1 extends Codex
{
	private LondonChronicle	chronScript;

	private static final int TIMER_ID_LILYIDENT			= 1;
	private static final int TIMER_ID_NIGHTMARE			= 2;
	private static final int TIMER_ID_CHANGECHRONICLE	= 3;
	private static final int TIMER_ID_NIGHTMARECONV		= 4;
	private static final int TIMER_ID_KILLAGENTS		= 5;
	private static final int TIMER_ID_FADEIN			= 6;
	private static final int TIMER_ID_INTERPOLDIECONV	= 7;
	private static final int TIMER_ID_HEARTBEAT			= 8;
	private static final int TIMER_ID_MOVEPARTY			= 9;
	private static final int TIMER_ID_CAMERARELEASE		= 10;
	private static final int TIMER_ID_PANSHIP			= 11;
	private static final int TIMER_ID_FADEOUT			= 12;
	private static final int TIMER_ID_PLAYVIDEO			= 13;
	private static final int TIMER_ID_HARBORHORN		= 14;
	private static final int TIMER_ID_REMOVEAGENTS		= 15;

	private static final int PATH_ANEZKAPAN		= 1;

	private CodexActor		_InterpolAgent1;
	private CodexActor		_InterpolAgent2;
	private CodexActor		_InterpolAgent3;
	private CodexActor		_Vampire1;
	private CodexActor		_Vampire2;

	private CodexActor		_Christof;
	private CodexActor		_Lily;
	private CodexActor		_Pink;

	private CodexRegion		_StowawayRegion;

	private int				christofGUID;
	private int				lilyGUID;
	private int				pinkGUID;

	private int				pathNum;

	private boolean			bStowawayConversation		= false;
	private boolean			bInterpolDieConversation	= false;
	private boolean			bLilyIdentConversation		= false;
	private boolean			bNightmareConversation		= false;

	private int				deadEnemies = 0;

	public static String _params[] = {"Interpol Agent 1", "Interpol Agent 2 (sitting)", "Interpol Agent 3", 
										"Stowaway region", "Vampire 1", "Vampire 2"};

	public CARG_32_1(CodexActor InterpolAgent1, CodexActor InterpolAgent2, CodexActor InterpolAgent3, 
					CodexRegion StowawayRegion,	CodexActor Vampire1, CodexActor Vampire2)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_InterpolAgent1 = new CodexActor(InterpolAgent1.GetGUID());
		_InterpolAgent2 = new CodexActor(InterpolAgent2.GetGUID());
		_InterpolAgent3 = new CodexActor(InterpolAgent3.GetGUID());
		_StowawayRegion = new CodexRegion(StowawayRegion.GetGUID());
		_Vampire1 = new CodexActor(Vampire1.GetGUID());
		_Vampire2 = new CodexActor(Vampire2.GetGUID());

		CaptureThing(_InterpolAgent1.GetGUID());
		CaptureThing(_InterpolAgent2.GetGUID());
		CaptureThing(_InterpolAgent3.GetGUID());
		CaptureThing(_StowawayRegion.GetGUID());
		CaptureThing(_Vampire1.GetGUID());
		CaptureThing(_Vampire2.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		_Christof = new CodexActor(christofGUID);
		_Lily = new CodexActor(lilyGUID);
		_Pink = new CodexActor(pinkGUID);

		_InterpolAgent2.ClearActorFlags(THING_AF_TALKTO);

		if(!CodexSequence.GetChronicleFlag(chronScript.CARG_STOWAWAY))
		{
			_InterpolAgent1.SetActorFlags(THING_AF_AIPAUSED);
			_InterpolAgent2.SetActorFlags(THING_AF_AIPAUSED);
			_InterpolAgent3.SetActorFlags(THING_AF_AIPAUSED);
			_Vampire1.SetActorFlags(THING_AF_AIPAUSED);
			_Vampire2.SetActorFlags(THING_AF_AIPAUSED);

			// guys set to invul so you can't kill them before the scene starts
			_InterpolAgent1.SetActorFlags(THING_AF_INVUL);
			_InterpolAgent3.SetActorFlags(THING_AF_INVUL);
			_Vampire1.SetActorFlags(THING_AF_INVUL);
			_Vampire2.SetActorFlags(THING_AF_INVUL);

			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_BoardMagdelena"));
			q.Complete();
		}
		else
		{
			// sick the vampires on the player - in case they left the ship during the battle
			// and came back on board
			_Vampire1.AISetTarget(CodexPlayer.GetCurrentPlayer());
			_Vampire2.AISetTarget(CodexPlayer.GetCurrentPlayer());	
		}
	}
	
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _StowawayRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.CARG_STOWAWAY))
		{
			CodexSequence.SetChronicleFlag(chronScript.CARG_STOWAWAY);

			// clear invul flags so they can die
			_InterpolAgent1.ClearActorFlags(THING_AF_INVUL);
			_InterpolAgent3.ClearActorFlags(THING_AF_INVUL);
			_Vampire1.ClearActorFlags(THING_AF_INVUL);
			_Vampire2.ClearActorFlags(THING_AF_INVUL);

			StowawayConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// see if all enemies are dead
		if(++deadEnemies == 4)
		{
			SetTimer(2, TIMER_ID_INTERPOLDIECONV, causeID);
		}
		else if(deadEnemies == 2)
		{
			// sick vampires on player
			_Vampire1.AISetTarget(CodexPlayer.GetCurrentPlayer());
			_Vampire2.AISetTarget(CodexPlayer.GetCurrentPlayer());

			SetTimer(2, TIMER_ID_REMOVEAGENTS);

			SetTimer(2, TIMER_ID_CAMERARELEASE);
		}
		else if(deadEnemies == 1)
		{
			if(guid == _InterpolAgent1.GetGUID())
			{
				_Vampire1.AISetTarget(_InterpolAgent3.GetGUID());
			}
			else if(guid == _InterpolAgent3.GetGUID())
			{
				_Vampire2.AISetTarget(_InterpolAgent1.GetGUID());
			}
		}
	}	

	public void pathended(int clientGuid)
	{
		switch(pathNum)
		{
			case PATH_ANEZKAPAN:

				CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)2.0, 0xffffff, true);
				new CodexSound("whoosh_pickup.wav", (float)2048.0, (float)4096.0, 100, 0, 0, christofGUID);

				SetTimer(1, TIMER_ID_NIGHTMARECONV);

				pathNum++;
				break;
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_LILYIDENT:

				LilyIdentConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_CHANGECHRONICLE:

				CodexSequence.ChangeChronicle("new_york.nsc");		
				break;

			case TIMER_ID_NIGHTMARE:

				// so he stays put while we move christof and lily
				_Pink.SetActorFlags(THING_AF_AIPAUSED);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "cargPanAnezka.ncp", 60);

				pathNum = PATH_ANEZKAPAN;

				SetTimer(0, TIMER_ID_HEARTBEAT);

				// set "dreamy" filter
				//CodexCamera.AddFilter(CodexPlayer.GetCurrentPlayer(), (float)13.5, 0x8080ff);

				// fade back in
				SetTimer(1, TIMER_ID_FADEIN);
				break;

			case TIMER_ID_NIGHTMARECONV:

				new CodexSound("Christof_Scream_7.wav", 300, 600, 100, 0, 0, christofGUID);

				NightmareConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_KILLAGENTS:

				// make it easier for the vampires to kill these dudes
				_InterpolAgent1.SetActorHealth((float)1.0);
				_InterpolAgent3.SetActorHealth((float)1.0);
				// make this guy invul so he doesn't die
				_InterpolAgent2.SetActorFlags(THING_AF_INVUL);
				break;

			case TIMER_ID_FADEIN:

				// Fade in
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
				break;

			case TIMER_ID_INTERPOLDIECONV:

				InterpolDieConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_HEARTBEAT:

				new CodexSound("blood_drain_fast_lp.wav", (float)2048.0, (float)4096.0, 100, 0, 0, christofGUID);
				SetTimer((float)1.0, TIMER_ID_HEARTBEAT);
				break;

			case TIMER_ID_MOVEPARTY:

				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CARGLilyIdent.ncp", 30);
				break;

			case TIMER_ID_CAMERARELEASE:

				// from stowaway conversations
				CodexCamera.Release(christofGUID);
				break;

			case TIMER_ID_PANSHIP:

				// Fade in
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1.0, false);

				CodexCamera.Release(christofGUID);

				SetTimer(3, TIMER_ID_HARBORHORN);
				SetTimer(14, TIMER_ID_HARBORHORN);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CARGPanShip.ncp", 40);

				SetTimer(15, TIMER_ID_FADEOUT);
				break;

			case TIMER_ID_FADEOUT:

				// fade out for nightmare sequence
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);

				SetTimer(3, TIMER_ID_NIGHTMARE, christofGUID);
				break;

			case TIMER_ID_PLAYVIDEO:

				// play NYC video
				PlayVideo("NYC.bik");
				break;

			case TIMER_ID_HARBORHORN:

				new CodexSound("harbor_horn_02.WAV", 5000, 7000, 100, 0, 0, christofGUID);
				break;

			case TIMER_ID_REMOVEAGENTS:
				
				_InterpolAgent1.Remove();
				_InterpolAgent3.Remove();
				break;
		}
	}

	public void videoended(int id)
	{
		SetTimer(0, TIMER_ID_CHANGECHRONICLE);
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		// from nightmare conversation
		CodexCamera.Release(christofGUID);

		// turn his ai back on
		_Pink.ClearActorFlags(THING_AF_AIPAUSED);

		// from the nightmare conversation
		_Christof.StopActorAction();
		_Lily.StopActorAction();
	}

	public void StowawayConversation(int starterGuid, int npcGuid)
	{
		bStowawayConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "32_1_Stowaways", "32_1_Stowaways.nco", CONV_XFLAG_WANTFEEDBACK + CONV_XFLAG_NOAUTOEND);
	}

	public void InterpolDieConversation(int starterGuid, int npcGuid)
	{
		bInterpolDieConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "32_1_InterpolDie", "32_1_InterpolDie.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LilyIdentConversation(int starterGuid, int npcGuid)
	{
		bLilyIdentConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "32_1_LilyIdent", "32_1_LilyIdent.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void NightmareConversation(int starterGuid, int npcGuid)
	{
		bNightmareConversation = true;
		AIOff();
		//CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "32_1_Nightmare", "32_1_Nightmare.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bStowawayConversation)
		{
			AIOn();
			bStowawayConversation = false;
			//CodexCamera.Release(starterGuid);

			//_InterpolAgent1.ClearActorFlags(THING_AF_AIPAUSED);
			//_InterpolAgent2.ClearActorFlags(THING_AF_AIPAUSED);
			//_InterpolAgent3.ClearActorFlags(THING_AF_AIPAUSED);
			_Vampire1.ClearActorFlags(THING_AF_AIPAUSED);
			_Vampire2.ClearActorFlags(THING_AF_AIPAUSED);

			// set vampires to attack agents
			_Vampire1.AISetTarget(_InterpolAgent1.GetGUID());
			_Vampire2.AISetTarget(_InterpolAgent3.GetGUID());

			// pause the agent's AI so they don't win
			_InterpolAgent1.SetActorFlags(THING_AF_AIPAUSED);
			_InterpolAgent3.SetActorFlags(THING_AF_AIPAUSED);

			// time for them to walk over to the agents
			SetTimer(3, TIMER_ID_KILLAGENTS);
		}

		if(bInterpolDieConversation)
		{
			bInterpolDieConversation = false;

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
	
			// in 3 seconds we will fade back in
			SetTimer(3, TIMER_ID_FADEIN);

			SetTimer(3, TIMER_ID_MOVEPARTY);

			// then when we've faded back in, start the lily ID conversation
			SetTimer(4, TIMER_ID_LILYIDENT, starterGuid);

			// close the exit - can't get off the ship anymore
			CodexSequence.CloseExit("CargoShip", 0);
		}

		if(bLilyIdentConversation)
		{
			AIOn();
			bLilyIdentConversation = false;
			//CodexCamera.Release(starterGuid);

			// fade out for panning the ship sequence
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);

			//SetTimer(3, TIMER_ID_NIGHTMARE, starterGuid);
			SetTimer(3, TIMER_ID_PANSHIP);
		}

		if(bNightmareConversation)
		{
			AIOn();
			bNightmareConversation = false;
			//CodexCamera.Release(starterGuid);

			SetTimer(1, TIMER_ID_PLAYVIDEO);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bStowawayConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "cargStowaway.ncp", 75);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bInterpolDieConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 1:

							_InterpolAgent2.PlayMotionSetMode(MOTION_DEATHSLOW, false, (float)30.0);

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CARGInterpolDie.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, pinkGUID, _InterpolAgent2.GetGUID());
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 2:
							
							//CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bLilyIdentConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CARGLilyIdent.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bNightmareConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.SetPosition(_InterpolAgent2.GetFramePosition(1));
							_Christof.SetOrientation(_InterpolAgent2.GetFrameOrientation(1));

							_Lily.SetPosition(_InterpolAgent2.GetFramePosition(1));
							_Lily.SetOrientation(_InterpolAgent2.GetFrameOrientation(1));

							_Christof.PlayMotionSetMode(MOTION_SPECIAL25, false, (float)30.0);
							_Lily.PlayMotionSetMode(MOTION_SPECIAL25, false, (float)30.0);

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CARGLilyHoldChris.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 1:
							
							// fade out for change chronicle
							CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(deadEnemies);
	}

	public void restore(int flags)
	{
		deadEnemies = CodexSequence.RestoreInt();
	}
}

