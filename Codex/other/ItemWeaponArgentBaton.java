/**
 *  ItemWeaponArgentBaton script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemWeaponArgentBaton extends Codex
{
	public static final float ARGENTBATONEFFECT_CHANCE		= (float)0.33;
	public static final float ARGENTBATONEFFECT_MINDAMAGE	= (float)30.0;
	public static final float ARGENTBATONEFFECT_MAXDAMAGE	= (float)80.0;

	public ItemWeaponArgentBaton()
	{
	}

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		CodexActor damaged = new CodexActor(damagedGUID);

		// make sure we are hitting a lupine
		if((damaged.GetActorFlags() & THING_AF_LUPINE) == 0)
			return;

		// random check to see if the ArgentBaton effect goes off this time
		if(Math.random() <  (ARGENTBATONEFFECT_CHANCE + damageAmount / (float)100))
		{
			// add random damage up to ARGENTBATONEFFECT_MAXDAMAGE
			float value = ARGENTBATONEFFECT_MAXDAMAGE + ((float)Math.random() * (ARGENTBATONEFFECT_MAXDAMAGE - ARGENTBATONEFFECT_MAXDAMAGE));

			damaged.AddActorEffectByValue("ef_item_argentbaton", 0, value, damagerGUID, 0);

			// do visuals
			damaged.SpawnThing("blackMagic");
		}
	}
 
}
