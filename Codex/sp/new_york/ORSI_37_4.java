/**
 * Orsi 37.4 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ORSI_37_4 extends Codex
{
	private NewYorkChronicle	chronScript;

	private CodexThing		_Palette;

	private int				christofGUID;
	private int				lilyGUID;
	private int				samuelGUID;
	private int				wilhemGUID;

	private boolean			bFindPaletteConversation	= false;

	public static String _params[] = {"Palette"};

	public ORSI_37_4(CodexThing Palette)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Palette = new CodexThing(Palette.GetGUID());

		CaptureThing(Palette.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		// change scene in the lobby to remove fred varney
		CodexSequence.ChangeScene("BarclayLobby", "LOBY_38_1.nsd");
	}
	
	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _Palette.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.ORSI_PALETTERECOVERED))
		{
			CodexSequence.SetChronicleFlag(chronScript.ORSI_PALETTERECOVERED);

			FindPaletteConversation(picker, 0);
		}

		return(true);
	}

	public void FindPaletteConversation(int starterGuid, int npcGuid)
	{
		bFindPaletteConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_FindPalette", "37_4_FindPalette.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		AIOn();
		bFindPaletteConversation = false;
		CodexCamera.Release(starterGuid);

		CodexQuest q = new CodexQuest(CodexQuest.Load("N1_OrsiPenthouse"));
		q.Complete();

		CodexQuest q2 = new CodexQuest(CodexQuest.Load("N1_FindAlexandra"));

		// open storage room entrance
		CodexSequence.OpenExit("NewYorkUptown", 6);
		CodexSequence.OpenExit("BarclayStorageRoom", 0);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, wilhemGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 2:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 3:

						CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 4:

						CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 5:

						CodexCamera.SetupCutscene(starterGuid, wilhemGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 6:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
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

