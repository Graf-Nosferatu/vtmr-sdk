/**
 * Effect ItemBlackGloves script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectItemBlackGloves extends Codex
{
	public EffectItemBlackGloves()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{ 
		CodexActor wearer = new CodexActor(actorGuid);

		wearer.OverrideActorWeapon("Claw", true, true);
		wearer.SetActorFlags(THING_AF_NOSHAPEDISC);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		CodexActor wearer = new CodexActor(actorGuid);

		wearer.CancelOverrideActorWeapon();
		wearer.ClearActorFlags(THING_AF_NOSHAPEDISC);
	}
}