/**
 * Effect DiscBloodHealing script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscBloodHealing extends Codex
{
	public EffectDiscBloodHealing()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator = new CodexActor(creatorGuid);
		
		// initial effect
		_Creator.SetShell("redCloudShell", 0x5000, 0, 1, 1, 1);
		_Creator.SpawnThing("redMagic");
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}