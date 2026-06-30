/**
 * Effect DiscCloakTheGathering script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscCloakTheGathering extends Codex
{
	public EffectDiscCloakTheGathering()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator = new CodexActor(creatorGuid);

		// initial effect
		_Creator.SpawnThing("yellowMagic");
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		// make sure the other effects are cancelled too
		CodexActor actor = new CodexActor(actorGuid);
		actor.RemoveActorEffect("ef_disc_cloakthegathering2");
	}

}