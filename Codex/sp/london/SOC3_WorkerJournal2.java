/**
 * Society 3 Journal script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC3_WorkerJournal2 extends Codex
{
	public SOC3_WorkerJournal2()
	{

	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		ExecuteText(clickerGuid, 0, "SocietyJournal2");
	}
}
