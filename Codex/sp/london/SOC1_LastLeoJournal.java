/**	
 * Society 1 Last Leo Journal entry script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC1_LastLeoJournal extends Codex
{
	private LondonChronicle	chronScript;

	public SOC1_LastLeoJournal()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		CodexSequence.SetChronicleFlag(chronScript.SOC3_LEOJOURNAL);

		return(true);
	}
}
