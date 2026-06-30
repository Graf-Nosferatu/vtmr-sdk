/**
 * NRTH 23.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class NRTH_23_1 extends Codex
{
	private ViennaChronicle	chronScript;

	public NRTH_23_1()
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.NRTH_EXITSCLOSED))
		{
			CodexSequence.SetChronicleFlag(chronScript.NRTH_EXITSCLOSED);

			// close all vienna links we need closed
			CodexSequence.CloseExit("WesternRingStrasse", 1);
			CodexSequence.CloseExit("InnerStradt", 2);
			CodexSequence.CloseExit("InnerStradt", 3);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.ORDR_ETRIUSJOURNAL))
		{
			CodexSequence.ChangeChronicle("prague2.nsc");
		}
		else
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, GetClassThing());

			// display specific locked message for gate back to Prague
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "BACKTOPRAGUE_LOCKED");
		}
	}
}
