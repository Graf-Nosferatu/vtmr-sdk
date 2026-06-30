/**
 *  ItemAinkurn script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemAinkurn extends Codex
{
	public static final float AINKURNEFFECT_CHANCE	= (float)0.1;

	public ItemAinkurn()
	{
	}

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		CodexActor damager = new CodexActor(damagerGUID);
		CodexActor damaged = new CodexActor(damagedGUID);

		if((damaged.GetActorFlags() & THING_AF_NOBLEED) != 0)
			return;

		if(damaged.GetActorStat(ACTOR_STAT_BLOOD) < 1.0f)
			return;

		// random check to see if the Ainkurn effect goes off this time
		if(Math.random() <  (AINKURNEFFECT_CHANCE + damageAmount / (float)200))
		{
			
 
			// random determination of the intensity of the effect, but add the damage into the equation, 
			// so a good hit adds to the chance of getting a good drain
			float power = (float)Math.random() + damageAmount / (float)200;
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

			damager.AddActorEffectByLevel("ef_item_ainkurn1", 2000, level, damagerGUID, 0);
			damaged.AddActorEffectByLevel("ef_item_ainkurn2", 2000, level, damagerGUID, 0);
		}
	}
 
}
