/**
 * Effect DiscBloodRage script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscBloodRage extends Codex
{
	public EffectDiscBloodRage()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("redMagic");
		_Target.SetShell("redCloudShell", 0x5000, 0, .9f, 1, 1);
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}