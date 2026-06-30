/**
 * Effect DiscShamblingHordes script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscShamblingHordes extends Codex
{
	public EffectDiscShamblingHordes()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("blackMagic");
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		CodexActor _Target = new CodexActor(actorGuid);

		_Target.SetActorTeam(_Target.GetActorOriTeam());
		_Target.DamageActor(1000.0f, DAMAGE_TYPE_AGGRAVATED, creatorGuid);
	}
}