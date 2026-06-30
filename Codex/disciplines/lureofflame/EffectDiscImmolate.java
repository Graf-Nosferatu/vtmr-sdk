/**
 * Effect DiscImmolate script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscImmolate extends Codex
{
	public EffectDiscImmolate()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);

		_Target.SpawnThing("ringTowerFlame");
	}
}