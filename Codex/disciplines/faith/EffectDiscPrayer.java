/**
 * Effect DiscPrayer script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscPrayer extends Codex
{
	public EffectDiscPrayer()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("blueMagic");
		_Target.SetShell("redCloudShell_b", 0x5000, 0, 2, 1, 1);
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}
}