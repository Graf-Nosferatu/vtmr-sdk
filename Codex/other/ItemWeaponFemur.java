/**
 *  ItemWeaponFemur script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemWeaponFemur extends Codex
{
	public static final float FEMUREFFECT_CHANCE		= (float)0.33;
	public static final float FEMUREFFECT_MINDAMAGE		= (float)30.0;
	public static final float FEMUREFFECT_MAXDAMAGE		= (float)80.0;

	public ItemWeaponFemur()
	{
	}

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		CodexActor damaged = new CodexActor(damagedGUID);

		// make sure we are hitting a lupine
		if(damaged.GetActorType() != ACTOR_TYPE_VAMPIRE)
			return;

		// random check to see if the Femur effect goes off this time
		if(Math.random() <  (FEMUREFFECT_CHANCE + damageAmount / (float)100))
		{
			// add random damage up to FEMUREFFECT_MAXDAMAGE
			float value = FEMUREFFECT_MAXDAMAGE + ((float)Math.random() * (FEMUREFFECT_MAXDAMAGE - FEMUREFFECT_MAXDAMAGE));

			damaged.AddActorEffectByValue("ef_item_femur", 0, value, damagerGUID, 0);

			// do visuals
			damaged.SpawnThing("blackMagic");
		}
	}
 
}
