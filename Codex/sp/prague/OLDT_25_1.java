/**
 * Old Town, 25.1 scene script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_25_1 extends Codex
{
	private Prague2Chronicle chronScript;

	public OLDT_25_1()
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// prague2 fly through intro
		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_FLYTHROUGH);
		}
	}
}