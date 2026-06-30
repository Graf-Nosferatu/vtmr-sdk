/**
 * Effect DiscQuellBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscQuellBeast extends Codex
{
	public EffectDiscQuellBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);

		// placeholder effect
		_Target.SpawnThing("blueMagic");
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}
}