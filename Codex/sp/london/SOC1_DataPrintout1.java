/**
 * Society 3 Journal script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC1_DataPrintout1 extends Codex
{
	public SOC1_DataPrintout1()
	{

	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		ExecuteText(clickerGuid, 0, "SocietyDocument1");
	}
}
