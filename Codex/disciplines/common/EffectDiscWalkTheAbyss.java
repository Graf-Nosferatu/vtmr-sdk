/**
 * Effect DiscWalkTheAbyss script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscWalkTheAbyss extends Codex
{
	public EffectDiscWalkTheAbyss()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(creatorGuid);

		// initial effect
		_Target.SpawnThing("blueMagic");
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}
}