/**
 * Effect DiscBloodDexterity script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscBloodDexterity extends Codex
{
	public EffectDiscBloodDexterity()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Caster = new CodexActor(creatorGuid);

		// initial effect
		_Caster.SpawnThing("redMagic");
		_Caster.SetShell("redCloudShell", 0x5000, 0, 1, 1, 1);
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}
}