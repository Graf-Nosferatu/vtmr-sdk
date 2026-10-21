/**
 * Effect DiscAcidicTouch script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscAcidicTouch extends Codex
{
	public EffectDiscAcidicTouch()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);
		
		// initial effect
		_Target.SpawnThing(CodexTemplate.INVSHOCKWAVE);
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//	
	//}

}