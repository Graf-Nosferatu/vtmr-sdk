/**
 * Effect DiscBeckoning script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscBeckoning extends Codex
{
	public EffectDiscBeckoning()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Caster	= new CodexActor(creatorGuid);
		
		// initial effect
		_Caster.SpawnThing("yellowMagic");
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}