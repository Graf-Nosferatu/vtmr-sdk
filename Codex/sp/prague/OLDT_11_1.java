/**
 * Old Town 11.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_11_1 extends Codex
{
	private PragueChronicle	chronScript;

	public boolean		bTooLateConversation = false;

	public OLDT_11_1()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_TOOLATE))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_TOOLATE);

			SetTimer(1, 0, clientGuid);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		TooLateConversation(CodexPlayer.GetCurrentPlayer(), 0);
	}

	public void TooLateConversation(int starterGuid, int npcGuid)
	{
		bTooLateConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "11_1_TooLate", "11_1_TooLate.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bTooLateConversation)
		{
			AIOn();
			bTooLateConversation = false;
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

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}