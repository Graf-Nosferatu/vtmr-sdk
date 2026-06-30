/**
 *  SEW2_Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 *
*/

public class SEW2_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int TIMER_ID_COMPLETEPUZZLE	= 1;
	private static final int TIMER_ID_RESETPUZZLE		= 2;

	private int					_damageAmount = 70;

	//DAMAGE_TYPE_ELECTRIC	= 2;
	private int					_damageType = 2;

	private int					_frameNum = 1;
	private int					_duration = 4;

	private int					christofGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;

	private CodexPlayer			playerToDamage;

	private CodexThing			_lever1;
	private CodexThing			_lever2;
	private CodexThing			_lever3;

	private CodexRegion			_floorRegion;
	private CodexRegion			_railRegion;
	private CodexRegion			_ArtworkRegion;

	private boolean				bLever1Turned = false;
	private boolean				bLever2Turned = false;
	private boolean				bLever3Turned = false;

	private boolean				bDamaging = false;
	private boolean				bPuzzleComplete = false;
	//private boolean				bActive = false;

	//private boolean				bThirdRailConversation = false;
	private boolean				bArtworkConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Lever 1", "Lever 2", "Lever3", "Floor damage region", 
										"Rail damage region", "Artwork region"};

	public SEW2_Main(CodexThing lever1, CodexThing lever2, CodexThing lever3, CodexRegion floorRegion, 
						CodexRegion railRegion, CodexRegion ArtworkRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_lever1 = new CodexThing(lever1.GetGUID());
		_lever2 = new CodexThing(lever2.GetGUID());
		_lever3 = new CodexThing(lever3.GetGUID());

		_floorRegion = new CodexRegion(floorRegion.GetGUID());
		_railRegion = new CodexRegion(railRegion.GetGUID());
		_ArtworkRegion = new CodexRegion(ArtworkRegion.GetGUID());

		// capture the levers so we get clicked messages for them
		CaptureThing(_lever1.GetGUID(), 1);
		CaptureThing(_lever2.GetGUID(), 2);
		CaptureThing(_lever3.GetGUID(), 3);

		CaptureThing(_floorRegion.GetGUID());
		CaptureThing(_railRegion.GetGUID());
		CaptureThing(_ArtworkRegion.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
	}
	
	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// if door is open already
		if(bPuzzleComplete)
			return;

		// move the lever that was clicked
		switch(captureId)
		{
			case 1:

				if(bLever1Turned)
					break;

				_lever1.RotatePivot(_frameNum, _duration);
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW2_LEVER1OPENED");

				bLever1Turned = true;
				
				// send trigger to SEW2_ElectricPlate script on this region to turn off effect
				// sending '1' (3rd parm) to tell script to turn it off
				_floorRegion.Trigger(0, 0, (float)1, 0, 0, 0);
				break;

			case 2:

				if(bLever2Turned)
					break;

				_lever2.RotatePivot(_frameNum, _duration);
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW2_LEVER2OPENED");

				bLever2Turned = true;

				if(!bLever3Turned || !bLever1Turned)
				{
					//reset puzzle
					SetTimer(5, TIMER_ID_RESETPUZZLE);
				}
				else if(bLever1Turned && bLever3Turned)
				{
					// complete puzzle
					SetTimer(5, TIMER_ID_COMPLETEPUZZLE);
				}
				break;

			case 3:

				if(bLever3Turned)
					break;

				_lever3.RotatePivot(_frameNum, _duration);
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW2_LEVER3OPENED");

				bLever3Turned = true;
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID) || bPuzzleComplete)
			return;
 
		if(guid == _ArtworkRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SEW2_ARTWORK))
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW2_ARTWORK);

			ArtworkConversation(causeGUID, 0);
		}
		else if(!bLever1Turned || guid == _railRegion.GetGUID())
		{
			playerToDamage = new CodexPlayer(causeGUID);

			// tick rate is 250, 4 damages of 8 (parameter) per second
			playerToDamage.AddActorEffectByValue("ef_dmg_electric", 0, 8, 0, 0);
		}
	}

	public void exited(int guid, int causeGUID, int captureID)
	{
		CodexActor exiter = new CodexActor(causeGUID);

		exiter.RemoveActorEffect("ef_dmg_electric");
	}
 
	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		CodexPlayer tempPlayer;
		int playerNum;


		// remove damage effects if player is still in region when level transitions
		for(playerNum = 0; playerNum < CodexPlayer.GetNumPartyPlayers(); playerNum++)
		{
			tempPlayer = new CodexPlayer(CodexPlayer.GetPartyPlayer(playerNum));

			tempPlayer.RemoveActorEffect("ef_dmg_electric");
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_COMPLETEPUZZLE:
				CompletePuzzle();
				break;
			case TIMER_ID_RESETPUZZLE:
				ResetPuzzle();
				break;
		}		
	}

	public void CompletePuzzle()
	{
		// send trigger to SEW2_ElectricPlate script on this region to turn off effect
		// sending '1' (3rd parm) to tell script to turn it off
		_railRegion.Trigger(0, 0, (float)1, 0, 0, 0);

		CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW2_RAILPUZZLESOLVED");

		bPuzzleComplete = true;
	}

	public void ResetPuzzle()
	{
		// send trigger to SEW2_ElectricPlate script on this region to turn effect back on
		// sending '0' (3rd parm) to tell script to turn back on
		_floorRegion.Trigger(0, 0, 0, 0, 0, 0);

		CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW2_RAILPUZZLERESET");

		// move all levers back to frame 0 if they've been turned
		if(bLever1Turned)
		{
			_lever1.RotatePivot(_frameNum, (int)(-_duration)/2);
			bLever1Turned = false;
		}

		if(bLever2Turned)
		{
			_lever2.RotatePivot(_frameNum, (int)(-_duration)/2);
			bLever2Turned = false;
		}
		if(bLever3Turned)
		{
			_lever3.RotatePivot(_frameNum, (int)(-_duration)/2);
			bLever3Turned = false;
		}
	}

	public void ArtworkConversation(int starterGuid, int npcGuid)
	{
		bArtworkConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "35_1_Artwork", "35_1_Artwork.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bArtworkConversation)
		{
			AIOn();
			bArtworkConversation = false;
			CodexCamera.Release(starterGuid);
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

						CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
	
/*
	public void ThirdRailConversation(int starterGuid, int npcGuid)
	{
		bThirdRailConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "35_1_ThirdRail", "35_1_ThirdRail.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bThirdRailConversation)
		{
			AIOn();
			bThirdRailConversation = false;
			CodexCamera.Release(starterGuid);
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
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;
					case 1:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
*/
	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bLever1Turned);
		CodexSequence.SaveBoolean(bLever2Turned);
		CodexSequence.SaveBoolean(bLever3Turned);
		CodexSequence.SaveBoolean(bPuzzleComplete);
	}

	public void restore(int flags)
	{
		bLever1Turned = CodexSequence.RestoreBoolean();
		bLever2Turned = CodexSequence.RestoreBoolean();
		bLever3Turned = CodexSequence.RestoreBoolean();
		bPuzzleComplete = CodexSequence.RestoreBoolean();
	}
}
