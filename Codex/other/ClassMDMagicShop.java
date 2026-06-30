/**
 * ClassMDMagicShop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author Sthoms
*/

public class ClassMDMagicShop extends Codex
{
	private CodexActor	owner;

	private String				descriptionID			= "GEN_MD_MAGICSHOP";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;

	private boolean				bActive					= false;

	public ClassMDMagicShop()
	{

	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		owner = new CodexActor(GetClassThing());

		bActive = true;
		owner.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		BuySellInterface(clickerGuid);
	}

	public void buysellended(boolean bAborted)
	{
		bActive = false;
		owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void BuySellInterface(int shopperGuid)
	{
		AddBuySell("generic_MD_magic.nbs", GetClassThing(), descriptionID, flags, buyRate, sellRate, sellFlags);

		ExecuteBuySell(GetClassThing(), shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}
}


