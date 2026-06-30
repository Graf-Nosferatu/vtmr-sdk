/**
 * OUTR Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OUTR_Main extends Codex
{
	private ViennaChronicle	chronScript;

	public OUTR_Main()
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.OUTR_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.OUTR_FLYTHROUGH);
		}
	}
}
