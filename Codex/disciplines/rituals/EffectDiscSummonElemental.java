/**
 * Effect DiscSummonElemental script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscSummonElemental extends Codex
{
	public EffectDiscSummonElemental()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator = new CodexActor(creatorGuid);

		// placeholder effect
		_Creator.SpawnThing("invshockwave");
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}