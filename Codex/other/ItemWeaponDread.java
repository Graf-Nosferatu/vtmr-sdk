/**
 *  ItemWeaponDread script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS/BM
*/

public class ItemWeaponDread extends Codex
{
	public static final int[] DREAD_DURATIONS = {  5000,  8000,  15000, 30000, 45000 };
	public static final float EFFECT_CHANCE	= (float)0.1;

	public ItemWeaponDread()
	{
	}

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		// random check to see if the Dread effect goes off this time
		if(Math.random() <  (EFFECT_CHANCE + damageAmount / (float)100))
		{
			CodexActor damager = new CodexActor(damagerGUID);
			CodexActor damaged = new CodexActor(damagedGUID);
 
			// random determination of the intensity of the effect, but add the damage into the equation, 
			// so a good hit adds to the chance of making the enemy afraid
			float power = (float)Math.random() + damageAmount / (float)100;
			int level;

			if(power < (float)0.50)
				level = 0;
			else if(power < (float)0.75)
				level = 1;
			else if(power < (float)0.90)
				level = 2;
			else if(power < (float)0.96)
				level = 3;
			else if(power < (float)0.99)
				level = 4;
			else
				level = 5;

			damaged.AddActorEffectByLevel("ef_item_dread", DREAD_DURATIONS[level], level, damagedGUID, 0);
 
			// do visuals
			damaged.SpawnThing("redMagic");
		}
	}
 
}
