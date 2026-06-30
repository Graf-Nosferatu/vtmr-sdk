/**
 *  Infectious script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ClassInfectious extends Codex
{
	public static final float DISEASE_CHANCE	= (float)0.05;

	public ClassInfectious()
	{
	}

	void hit(int damagerGUID, int damagedGUID, float damageAmount, int captureID)
	{
		if(Math.random() < (DISEASE_CHANCE + damageAmount / (float)100))
		{
			CodexActor damaged = new CodexActor(damagedGUID);

			int extraTime = 0;
			int effectGuid = damaged.FindActorEffect("ef_disease");
			if(effectGuid != 0)
			{
				damaged.RemoveActorEffect(effectGuid);

				// since he was already diseased, add a little extra time to the duration
				extraTime = (int)(1000 + Math.random() * 59000);
			}

			damaged.AddActorEffectByLevel("ef_disease", extraTime + (int)(15000 + Math.random() * 585000), 0, damagerGUID, 0);
		}
	}
}
