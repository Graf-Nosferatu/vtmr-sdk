/**
 * Item WeaponTaser script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class ItemWeaponTaser extends Codex
{
	// --------------------------------------------------------------------------------------------

	public ItemWeaponTaser()
	{
	}

	// --------------------------------------------------------------------------------------------

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		CodexActor damaged = new CodexActor(damagedGUID);

		damaged.AddActorEffectByLevel("ef_item_taser", 0, 0, damagerGUID, 0);

	}

}
