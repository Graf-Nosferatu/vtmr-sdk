/**
 *  ItemAmulet script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemAmulet extends Codex
{
	private ViennaChronicle	chronScript;

	public ItemAmulet()
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);
	}

	public boolean pickup(int item, int picker, int captureID)
	{
		CodexSequence.SetChronicleFlag(chronScript.TEU2_AMULETRECOVERED);

		CodexQuest q = new CodexQuest(CodexQuest.Load("V1_ReturnAmulet"));

		// show orvus on map 
		CodexSequence.SetLocationFlags("OrderOfHermes", LOCATION_FLAG_SHOWINMAP);

		SetTimer(2);
		return(false);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexThing amulet = new CodexThing(GetClassThing());
		amulet.Remove();
	}
}
