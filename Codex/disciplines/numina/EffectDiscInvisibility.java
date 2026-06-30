/**
 * Effect DiscInvisibility script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscInvisibility extends Codex
{
	// --------------------------------------------------------------------------------------------

	public EffectDiscInvisibility()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		// placeholder effect
		//_Creator.SpawnThing("yellowMagic");
		
		// Running effect
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		// make sure the other effects are cancelled too
		CodexActor actor = new CodexActor(actorGuid);
		actor.RemoveActorEffect("ef_disc_invisibility2");
	}

}