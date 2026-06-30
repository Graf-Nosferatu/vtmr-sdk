/**
 * Vysehrad1 25.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class VYC1_25_1 extends Codex
{
	private Prague2Chronicle chronScript;

	private CodexActor		_Revenant1;
	private CodexActor		_Revenant2;
	private CodexActor		_Revenant3;
	private CodexActor		_Revenant4;

	private int				christofGUID;
	private int				revenant1GUID;
	private int				revenant4GUID;

	private boolean			bRevenantConversation = false;

	public static String _params[] = {"Revenant1", "Revenant2", "Revenant3", "Revenant4"};

	public VYC1_25_1(CodexActor Revenant1, CodexActor Revenant2, CodexActor Revenant3, CodexActor Revenant4)
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);

		_Revenant1 = new CodexActor(Revenant1.GetGUID());
		_Revenant2 = new CodexActor(Revenant2.GetGUID());
		_Revenant3 = new CodexActor(Revenant3.GetGUID());
		_Revenant4 = new CodexActor(Revenant4.GetGUID());

		revenant1GUID = _Revenant1.GetGUID();
		revenant4GUID = _Revenant4.GetGUID();
				
		CaptureThing(_Revenant1.GetGUID());
		CaptureThing(_Revenant2.GetGUID());
		CaptureThing(_Revenant3.GetGUID());
		CaptureThing(_Revenant4.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");

		if(!CodexSequence.GetChronicleFlag(chronScript.VYC1_REVENANT))
		{
			_Revenant1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant3.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant4.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			_Revenant1.SetActorFlags(THING_AF_AIPAUSED);
			_Revenant2.SetActorFlags(THING_AF_AIPAUSED);
			_Revenant3.SetActorFlags(THING_AF_AIPAUSED);
			_Revenant4.SetActorFlags(THING_AF_AIPAUSED);

			_Revenant1.SetActorFlags(THING_AF_INVUL);
			_Revenant2.SetActorFlags(THING_AF_INVUL);
			_Revenant3.SetActorFlags(THING_AF_INVUL);
			_Revenant4.SetActorFlags(THING_AF_INVUL);
		}
	}
	
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.VYC1_REVENANT))
		{
			CodexSequence.SetChronicleFlag(chronScript.VYC1_REVENANT);
			
			RevenantConversation(causeGUID, 0);
		}
	}

	public void RevenantConversation(int starterGuid, int npcGuid)
	{
		bRevenantConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "25_1_Revenants", "25_1_Revenants.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		AIOn();
		bRevenantConversation = false;
		CodexCamera.Release(starterGuid);

		_Revenant1.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Revenant2.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Revenant3.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Revenant4.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

		_Revenant1.ClearActorFlags(THING_AF_AIPAUSED);
		_Revenant2.ClearActorFlags(THING_AF_AIPAUSED);
		_Revenant3.ClearActorFlags(THING_AF_AIPAUSED);
		_Revenant4.ClearActorFlags(THING_AF_AIPAUSED);

		_Revenant1.ClearActorFlags(THING_AF_INVUL);
		_Revenant2.ClearActorFlags(THING_AF_INVUL);
		_Revenant3.ClearActorFlags(THING_AF_INVUL);
		_Revenant4.ClearActorFlags(THING_AF_INVUL);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

					case 1:

						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, revenant4GUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, revenant1GUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 18);
						break;

					case 3:

						//CodexCamera.SetupCutscene(starterGuid, christofGUID, revenant4GUID);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 4:

						//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

					case 5:

						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

					case 6:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, revenant4GUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

					case 7:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

