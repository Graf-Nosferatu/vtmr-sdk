/**
 * Warehouse 3 Al's Ledger script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WAR3_AlLedger extends Codex
{
	private NewYorkChronicle	chronScript;

	private CodexItem		_AlLedger;

	public WAR3_AlLedger()
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_AlLedger = new CodexItem(GetClassThing());
		
		CaptureThing(_AlLedger.GetGUID());
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _AlLedger.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.WAR3_ALLEDGER))
		{
			CodexSequence.SetChronicleFlag(chronScript.WAR3_ALLEDGER);

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_OrsiPenthouse"));

			// change scene in the barclay lobby so fred shows up
			CodexSequence.ChangeScene("BarclayLobby", "LOBY_37_4.nsd");
		}

		return(true);
	}
}
