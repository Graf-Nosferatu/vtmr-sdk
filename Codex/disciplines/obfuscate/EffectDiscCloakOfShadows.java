/**
 * Effect DiscCloakOfShadows script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscCloakOfShadows extends Codex
{
	// --------------------------------------------------------------------------------------------

	public EffectDiscCloakOfShadows()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		// Running effect
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		// Remove running effect

		// make sure the other effects are cancelled too
		CodexActor actor = new CodexActor(actorGuid);
		actor.RemoveActorEffect("ef_disc_cloakofshadows2");
	}

}