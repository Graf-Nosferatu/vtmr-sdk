/**
 * Tower of London 1 30.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TOL1_30_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	private int			christofGUID;
	private int			lilyGUID;
	private int			pinkGUID;

	public boolean		b30_1_TowerIntroConversation = false;

	public static String _params[] =	{};

	public TOL1_30_1_Main()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		if(!CodexSequence.GetChronicleFlag(chronScript.TOL1_TOWERINTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.TOL1_TOWERINTRO);

			c30_1_TowerIntroConversation(clientGuid, 0);
		}
	}

	public void c30_1_TowerIntroConversation(int starterGuid, int npcGuid)
	{
		b30_1_TowerIntroConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "30_1_TowerIntro", "30_1_TowerIntro.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b30_1_TowerIntroConversation)
		{
			AIOn();
			b30_1_TowerIntroConversation = false;
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						break;

					case 1:

						//CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 1, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

