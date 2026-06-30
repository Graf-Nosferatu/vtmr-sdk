/**
 * ClassMDWeapShop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author Sthoms
*/

public class ClassMDWeapShop extends Codex
{
	private CodexActor	owner;

	private String				descriptionID			= "GEN_MD_WEAPSHOP";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;

	private boolean				bActive					= false;

	public ClassMDWeapShop()
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
		AddBuySell("generic_MD_weapon.nbs", GetClassThing(), descriptionID, flags, buyRate, sellRate, sellFlags);

		ExecuteBuySell(GetClassThing(), shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}
}


