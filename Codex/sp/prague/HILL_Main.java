/**
 * Petrin Hill  main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HILL_Main extends Codex
{
	private PragueChronicle	chronScript;

	private int				christofGUID;
	private int				wilhemGUID;
	
	private boolean			bHillConversation		= false;

	public HILL_Main()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.HILL_REGION) &&
			CodexSequence.GetChronicleFlag(chronScript.UNIV_CONVDONE))
		{
			CodexSequence.SetChronicleFlag(chronScript.HILL_REGION);

			// change scene in university to 8_1
			CodexSequence.ChangeScene("University", "UNIV_8_1.nsd");

			HillConversation(causeGUID, 0);
		}
	}
	
	public void HillConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bHillConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_1_Mission1", "8_1_Mission1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bHillConversation)
		{
			AIOn();
			bHillConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bHillConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HILLBehold.ncp", 45);
					//CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 1, 0);
					break;

				case 1:

					//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 9);
					break;

				case 2:

					//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
					break;
			}
		}
	}


	
	
	
}

