/**
 * Effect DiscSummonSoul script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscSummonSoul extends Codex
{
	public EffectDiscSummonSoul()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator = new CodexActor(creatorGuid);

		// initial effect
		_Creator.SpawnThing("invshockwave");
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}