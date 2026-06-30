/**
 * RegionTrigger script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class RegionTrigger extends Codex
{
	private CodexThing _triggeree;

	public static String _params[] = {"Triggeree"};

	public RegionTrigger(CodexThing triggeree)
	{	
		_triggeree = new CodexThing(triggeree.GetGUID());

		CaptureThing(_triggeree.GetGUID());
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		_triggeree.Trigger(0, 0, 0, 0, 0, 0);
	}
}
