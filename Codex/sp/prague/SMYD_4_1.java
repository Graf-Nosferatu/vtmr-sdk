/**
 * Smithy Day 4.1 scene script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SMYD_4_1 extends Codex
{
	private PragueChronicle	chronScript;

	public SMYD_4_1()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.SMYD_FLYTHROUGH))
		{
			CodexSequence.GetChronicleFlag(chronScript.SMYD_FLYTHROUGH);
			
			// smithy fly through intro?
		}
	}
}