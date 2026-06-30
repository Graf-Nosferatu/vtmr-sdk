/**
 * Silver Mines 4 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SIM4_Main extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexActor		_Ahzra;
	private CodexActor		_Christof;
	private	CodexRegion		_AhzraRegion;
	private	CodexRegion		_LocketRegion;
	private CodexThing		_AltExitDoor;

	private boolean			bAhzraConversation = false;
	private boolean			bAhzraDeathConversation = false;
	private boolean			bLocketConversation = false;

	private int				christofGUID;
	private int				ahzraGUID;

	public static String _params[] = {"Ahzra", "Ahzra region", "Locket region", "Alternate exit door"};

	public SIM4_Main(CodexActor Ahzra, CodexRegion AhzraRegion, CodexRegion LocketRegion, CodexThing AltExitDoor)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Ahzra = new CodexActor(Ahzra.GetGUID());
		_AhzraRegion = new CodexRegion(AhzraRegion.GetGUID());
		_LocketRegion = new CodexRegion(LocketRegion.GetGUID());
		_AltExitDoor = new CodexThing(AltExitDoor.GetGUID());

		CaptureThing(_Ahzra.GetGUID());
		CaptureThing(_AhzraRegion.GetGUID());
		CaptureThing(_LocketRegion.GetGUID());
		CaptureThing(_AltExitDoor.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		ahzraGUID = CodexThing.GuidFromCastID("Ahzra");

		_Christof = new CodexActor(christofGUID);
		
		if(!CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRAREGION))
		{
			_Ahzra.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Ahzra.SetCollideType(THING_COLLIDE_NONE);
			_Ahzra.SetActorFlags(THING_AF_AIPAUSED);

			// preload the weapLocket model
			PreloadModel("locket_0.nod");
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(guid == _AhzraRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRAREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.SIM4_AHZRAREGION);

			AhzraConversation(causeGUID, 0);
		}

		if(guid == _LocketRegion.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD) &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM4_LOCKETRECOVERED))
		{
			// set a flag to specify the locket was recovered
			CodexSequence.SetChronicleFlag(chronScript.SIM4_LOCKETRECOVERED);

			CodexPlayer targetPlayer = new CodexPlayer(causeGUID);

			targetPlayer.Stop();
			targetPlayer.CancelActorAction();

			LocketConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// make sure Ahzra was killed
		if(guid == _Ahzra.GetGUID())
		{
			// set a chronicle flag to specify Ahzra died
			CodexSequence.SetChronicleFlag(chronScript.SIM4_AHZRADEAD);

			AhzraDeathConversation(causeID, 0);

			// open the alternate exit door back up to SIM1
			_AltExitDoor.MoveToFrame(1, 40);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _AltExitDoor.GetGUID() && frameNum == 1)
		{
			_AltExitDoor.MoveToFrame(2, 40);
		}
	}

	public void AhzraConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_1.mp3", 50);
		bAhzraConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "5_1_Ahzra", "5_1_Ahzra.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AhzraDeathConversation(int starterGuid, int npcGuid)
	{
		bAhzraDeathConversation = true;
		ExecuteConversation(starterGuid, npcGuid, "5_1_AhzraDeath", "5_1_AhzraDeath.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LocketConversation(int starterGuid, int npcGuid)
	{
		bLocketConversation = true;
		ExecuteConversation(starterGuid, npcGuid, "5_1_Locket", "5_1_Locket.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bAhzraConversation)
		{
			bAhzraConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}

		if(bAhzraDeathConversation)
		{
			CodexSound.PopMusic();
			bAhzraDeathConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_SilverMines"));
			q.Complete();

			// tell them to go back to see geza
			CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_Geza"));

			// show st thomas on map
			CodexSequence.SetLocationFlags("StThomas", LOCATION_FLAG_SHOWINMAP);

			// prepare the hero's return
			CodexSequence.ChangeScene("StThomas", "STHM_6_1.nsd");

			// auto advance
			CodexSequence.Advance(christofGUID);
		}

		if(bLocketConversation)
		{
			_Christof.StopActorAction();
			_Christof.CancelOverrideActorWeapon();
			bLocketConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bAhzraConversation)
		{
			switch(curLine)
			{
				case 0:

					_Christof.PlayMotionSetMode(MOTION_ACTION2, false, (float)15.0);
					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "AhzraLair.ncp", 30);
					break;

				case 1:

					//show ahzra
					_Ahzra.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
					_Ahzra.SetCollideType(THING_COLLIDE_CYL);
					_Ahzra.ClearActorFlags(THING_AF_AIPAUSED);
					
					CodexCamera.SetupCutscene(starterGuid, christofGUID, ahzraGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 6);
					_Ahzra.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)45.0);
					_Christof.StopActorAction();
					break;

				case 2:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "AhzraLair.ncp", 30);
					_Ahzra.StopActorAction();
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ahzraGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
					break;

				case 6:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
					break;
			}
		}

		if(bAhzraDeathConversation)
		{
			switch(curLine)
			{
				case 0:
					
					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
					break;
			}
		}

		if(bLocketConversation)
		{
			switch(curLine)
			{
				case 0:

					_Christof.PlayMotionSetMode(MOTION_ACTION5, false, (float)45.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 1:

					_Christof.OverrideActorWeapon("weapLocket", false, false);
					_Christof.PlayMotionSetMode(MOTION_ACTION6, false, (float)30.0);
					break;
			}
		}
	}
}
