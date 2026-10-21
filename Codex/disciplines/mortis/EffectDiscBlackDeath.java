/**
 * Effect DiscBlackDeath script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscBlackDeath extends Codex
{
	public EffectDiscBlackDeath()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);		

		// initial effect
		_Target.SpawnThing(CodexTemplate.BLACKMAGIC);
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}