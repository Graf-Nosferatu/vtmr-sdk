/**
 * GoldenLane 4.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class GOLDD_4_1 extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexRegion		_Region;

	public static String _params[] = {"Region"};

	private int					christofGUID;
	
	public GOLDD_4_1(CodexRegion Region)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Region = new CodexRegion(Region.GetGUID());

		CaptureThing(_Region.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.GOLDD_REGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.GOLDD_REGION);

			OutsideUnornaConversation(causeGUID, 0);
		}
	}


	public void OutsideUnornaConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_OutsideUnorna", "4_1_OutsideUnorna.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		CodexCamera.Release(starterGuid);
		AIOn();
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curLine)
		{
			case 0:
				
				CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				break;
		}
	}

}

