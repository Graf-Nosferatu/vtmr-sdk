/**
 * ItemBlackGloves script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ItemBlackGloves extends Codex
{
	public ItemBlackGloves()
	{
	}

	public boolean canuse(int guid, int wearerGuid, int captureId)
	{
		CodexActor wearer = new CodexActor(wearerGuid);
		CodexItem item = new CodexItem(guid);

		if(((wearer.GetActorFlags() & THING_AF_NOSHAPEDISC) == 0) || ((item.GetItemFlags() & ITEM_FLAG_BEINGWORN) != 0))
			return(true);
		else
			return(false);
	}

	public void worn(int guid, int wearerGuid, int captureId)
	{
		CodexActor wearer = new CodexActor(wearerGuid);

		wearer.AddActorEffectByLevel("ef_item_blackgloves", 0, 0, guid, EI_FLAG_WORN);
	}

	public boolean unworn(int guid, int wearerGuid, int captureId)
	{
		CodexActor wearer = new CodexActor(wearerGuid);

		wearer.RemoveActorEffect("ef_item_blackgloves");
		
		return(true);
	}
}
