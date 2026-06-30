/**
 * East Gate Night, 25.1 scene script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EGNT_25_1 extends Codex
{
	private Prague2Chronicle chronScript;

	public EGNT_25_1()
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.EGNT_PRAGUE2INTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.EGNT_PRAGUE2INTRO);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)5.0, false);

			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_ReturnToPrague"));
			q.Destroy();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("P2_Ecaterina"));
		}
	}
}