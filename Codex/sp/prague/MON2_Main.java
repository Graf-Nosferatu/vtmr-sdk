/**
 * Petrin Hill monastery 2  main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class MON2_Main extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexRegion		_DarkWorshipRegion;
	private	CodexRegion		_EarthenFloorRegion;

	private int				christofGUID;
	private int				wilhemGUID;
	
	private boolean			bDarkConversation		= false;
    private boolean			bFloorConversation		= false;

	public static String _params[] = {"Dark Worship region", "Earthen floor region"};

	public MON2_Main(CodexRegion DarkWorshipRegion, CodexRegion EarthenFloorRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_DarkWorshipRegion = new CodexRegion(DarkWorshipRegion.GetGUID());
		_EarthenFloorRegion = new CodexRegion(EarthenFloorRegion.GetGUID());

		CaptureThing(_DarkWorshipRegion.GetGUID());
		CaptureThing(_EarthenFloorRegion.GetGUID());
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

		if(guid == _DarkWorshipRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON2_DARKWORSHIPREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON2_DARKWORSHIPREGION);

			DarkConversation(causeGUID, 0);
		}


		if(guid == _EarthenFloorRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON1_EARTHENFLOORREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON1_EARTHENFLOORREGION);

			FloorConversation(causeGUID, 0);
		}
 	}
	
	public void DarkConversation(int starterGuid, int npcGuid)
	{
		bDarkConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Cainite1", "8_2_Cainite1.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void FloorConversation(int starterGuid, int npcGuid)
	{
		bFloorConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Floor", "8_2_Floor.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bDarkConversation)
		{
			bDarkConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
		
		if(bFloorConversation)
		{
			bFloorConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
	}
		
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bDarkConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
		
		if(bFloorConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}

