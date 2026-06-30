/**
 *  STE3_LutherGlass script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 *
*/

public class STE3_LutherGlass extends Codex
{
	private ViennaChronicle chronScript;

	private static final int	TIMER_ID_RESETPUZZLE	= 1;
	private static final int	TIMER_ID_JUMP			= 2;
	private static final int	TIMER_ID_LUTHERDEATH	= 3;
	private static final int	TIMER_ID_LUTHERDEAD		= 4;

	private static final float	LEVER_MOVE_SPEED = (float)50.0;

	private CodexActor			_Orsi;
	private	CodexRegion			_OrsiRegion;

	private CodexActor			Luther;

	private CodexActor			_TeutonicKnight1;
	private CodexActor			_TeutonicKnight2;
	
	private float				_openSpeed;
	private CodexThing			_lever1;
	private CodexThing			_lever2;
	private CodexThing			_secondRoofHalf;

	private int					christofGUID;
	private int					orsiGUID;

	private boolean				bLever1Down = false;
	private boolean				bLever2Down = false;

	private boolean				bPuzzleComplete = false;
	private boolean				bPuzzleInProgress = false;

	private boolean				bCapturedConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"open speed;20.0", "lever 1", "lever 2", "Second roof half", "Orsi", "Orsi region", 
																"Teutonic Knight1", "Teutonic Knight2"};

	public STE3_LutherGlass(float openSpeed, CodexThing lever1, CodexThing lever2, CodexThing secondRoofHalf, CodexActor Orsi, 
												CodexRegion OrsiRegion, CodexActor TeutonicKnight1, CodexActor TeutonicKnight2)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_openSpeed  = openSpeed;

		_lever1 = new CodexThing(lever1.GetGUID());
		_lever2 = new CodexThing(lever2.GetGUID());
				
		_Orsi = new CodexActor(Orsi.GetGUID());
		_OrsiRegion = new CodexRegion(OrsiRegion.GetGUID());	

		_TeutonicKnight1 = new CodexActor(TeutonicKnight1.GetGUID());
		_TeutonicKnight2 = new CodexActor(TeutonicKnight2.GetGUID());

		_secondRoofHalf = new CodexThing(secondRoofHalf.GetGUID());

		// capture the levers so we get clicked messages for them
		CaptureThing(_lever1.GetGUID(), 1);
		CaptureThing(_lever2.GetGUID(), 2);

		CaptureThing(_OrsiRegion.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		orsiGUID = CodexThing.GuidFromCastID("Orsi");

		Luther = new CodexActor(CodexThing.GuidFromCastID("Luther"));
		
		if(!CodexSequence.GetChronicleFlag(chronScript.STE3_LUTHERDEAD))
		{
			// hides orsi and his knights, makes them non-collide, and pauses their AI
			_Orsi.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_TeutonicKnight1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_TeutonicKnight2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

			_Orsi.SetCollideType(THING_COLLIDE_NONE);
			_TeutonicKnight1.SetCollideType(THING_COLLIDE_NONE);
			_TeutonicKnight2.SetCollideType(THING_COLLIDE_NONE);

			_Orsi.SetActorFlags(THING_AF_AIPAUSED);
			_TeutonicKnight1.SetActorFlags(THING_AF_AIPAUSED);
			_TeutonicKnight2.SetActorFlags(THING_AF_AIPAUSED);

			_lever1.SetDescriptionID("GEN_SWITCH");
			_lever2.SetDescriptionID("GEN_SWITCH");

			// turn off the luther room sunlight
			DisableLightStyle(16);
		}
	}

	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		// this is the trigger from STE3_21_1 - in case luther opens the door himself
		CompletePuzzle();
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// if roof section is moved already
		if(bPuzzleComplete)
			return;

		// move the lever that was clicked
		switch(captureId)
		{
			case 1:
				_lever1.MoveToFrame(!bLever1Down ? 1 : 0, LEVER_MOVE_SPEED);
				break;
			case 2:
				_lever2.MoveToFrame(!bLever2Down ? 1 : 0, LEVER_MOVE_SPEED);
				break;
		}
	}

	// --------------------------------------------------------------------------------------------

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		// if it's not either lever, just get out right now, the actors 
		// are sending events out as well and we don't want arrived messages 
		// coming from them, if the puzzle is complete, get out
		if((captureId != 1 && captureId != 2) || bPuzzleComplete)
			return;

		// if they are both clicked within five seconds the puzzle will be completed
		// if not, they will both be reset
		switch(captureId)
		{
			case 1:
				bLever1Down = frameNum == 1 ? true : false;
				if(bLever2Down)
					CompletePuzzle();
				break;
			case 2:
				bLever2Down = frameNum == 1 ? true : false;
				if(bLever1Down)
					CompletePuzzle();
				break;
		}

		if((bLever1Down || bLever2Down) && !bPuzzleInProgress)
		{
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "STE3_TIMER_5_SECONDS");

			bPuzzleInProgress = true;
			SetTimer(5, TIMER_ID_RESETPUZZLE);
		}		
	}

	// --------------------------------------------------------------------------------------------

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _OrsiRegion.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.STE3_LUTHERDEAD))
		{
			// change scene in orsi mansion so orsi is gone
			CodexSequence.ChangeScene("OrsiMansion", "ORSI_20_6.nsd");

			CapturedConversation(causeGUID, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_RESETPUZZLE:

				if(!bPuzzleComplete)
					ResetPuzzle();
				break;

			case TIMER_ID_JUMP:

				// take the party to DK
				CodexSequence.Jump("TeutonicKnightBase4", 1);
				break;

			case TIMER_ID_LUTHERDEATH:

				// camera death shot of luther here
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LutherDeath.ncp", 30);

				// luther line
				CodexSound.PlayVoice(Luther.GetGUID(), "Luther_21_1_776", 100);

				Luther.SetActorFlags(THING_AF_INVUL);

				// effect of sun hitting luther
				Luther.AddActorEffectByValue("ef_dmg_sun", 0, 2.5f, 0, 0);

				// spawn in some smoke from luther
				Luther.SpawnThing("smokePlume");

				// start playing luther's death animation
				Luther.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);

				// start sun whiteout effect
				CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, 0xffffff, false);
				
				SetTimer((float)3.0, TIMER_ID_LUTHERDEAD);
				break;

			case TIMER_ID_LUTHERDEAD:

				Luther.Remove();

				// turn whiteout off
				CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.0, 0x000000, false);
				CodexCamera.Release(0);
				break;
		}
	}

	public void CapturedConversation(int starterGuid, int npcGuid)
	{
		bCapturedConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "21_1_Captured", "21_1_Captured.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bCapturedConversation)
		{
			AIOn();
			bCapturedConversation = false;
			CodexCamera.Release(starterGuid);

			SetTimer(1, TIMER_ID_JUMP);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}

	public void CompletePuzzle()
	{
		CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "STE3_PUZZLE_COMPLETE");

		bPuzzleComplete = true;

		CodexSequence.SetChronicleFlag(chronScript.STE3_LUTHERDEAD);

		// mark 'destroy luther' quest complete
		CodexQuest q = new CodexQuest(CodexQuest.Load("V1_DestroyLuther"));
		q.Complete();

		CodexThing roofSection = new CodexThing(GetClassThing());
		roofSection.MoveToFrame(1, _openSpeed);
		_secondRoofHalf.MoveToFrame(1, _openSpeed);

		// camera death shot of doors opening here
		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OpenDoors.ncp", 30);

		SetTimer((float)1.0, TIMER_ID_LUTHERDEATH);

		CodexSequence.SetChronicleFlag(chronScript.STE3_SKYLIGHTOPEN);

		// turn on the luther room sunlight
		EnableLightStyle(16);

		// show, set collide type back to cylinder, 
		// and turn the ai back on for oris and knights
		_Orsi.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
		_TeutonicKnight1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
		_TeutonicKnight2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

		_Orsi.SetCollideType(THING_COLLIDE_CYL);
		_TeutonicKnight1.SetCollideType(THING_COLLIDE_CYL);
		_TeutonicKnight2.SetCollideType(THING_COLLIDE_CYL);

		_Orsi.ClearActorFlags(THING_AF_AIPAUSED);
		_TeutonicKnight1.ClearActorFlags(THING_AF_AIPAUSED);
		_TeutonicKnight2.ClearActorFlags(THING_AF_AIPAUSED);
	}

	public void ResetPuzzle()
	{
		CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "STE3_PUZZLE_RESET");

		_lever1.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever1Down = false;

		_lever2.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever2Down = false;

		bPuzzleInProgress = false;
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bPuzzleComplete);
	}

	public void restore(int flags)
	{
		bPuzzleComplete = CodexSequence.RestoreBoolean();
	}
}
