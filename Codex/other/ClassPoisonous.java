/**
 *  Poisonous script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ClassPoisonous extends Codex
{
	public static final float POISON_CHANCE	= (float)0.05;

	public ClassPoisonous()
	{
	}

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		CodexActor damaged = new CodexActor(damagedGUID);

		if(Math.random() < (POISON_CHANCE /* - damaged.GetSoak(ACTOR_SOAK_POISON) / 100 */ + damageAmount / (float)100))
		{	
			int extraTime = 0;
			int effectGuid = damaged.FindActorEffect("ef_poison");
			if(effectGuid != 0)
			{
				damaged.RemoveActorEffect(effectGuid);

				// since he was already poisoned, add a little extra time to the duration
				extraTime = (int)(1000 + Math.random() * 9000);
			}

			damaged.AddActorEffectByLevel("ef_poison", extraTime + (int)(5000 + Math.random() * 55000), 0, damagerGUID, 0);
		}
	}
}
