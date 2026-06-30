/**
 * Effect DiscEyesOfChaos script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscEyesOfChaos extends Codex
{
	public EffectDiscEyesOfChaos()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("yellowMagic");
		
		// Running effect
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}