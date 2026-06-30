/**
 * Setite Temple 1 29.2 Worship script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SET1_29_2_Worship extends Codex
{
	private LondonChronicle	chronScript;

	private int			christofGUID;
	private int			lilyGUID;
	private int			pinkGUID;

	public boolean		b29_2_WorshipConversation = false;

	public static String _params[] =	{};

	public SET1_29_2_Worship()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.SET1_WORSHIP))
		{
			CodexSequence.SetChronicleFlag(chronScript.SET1_WORSHIP);

			c29_2_WorshipConversation(causeGUID, 0);
		}
	}

	public void c29_2_WorshipConversation(int starterGuid, int npcGuid)
	{
		b29_2_WorshipConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "29_2_Worship", "29_2_Worship.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b29_2_WorshipConversation)
		{
			AIOn();
			b29_2_WorshipConversation = false;
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

						CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}