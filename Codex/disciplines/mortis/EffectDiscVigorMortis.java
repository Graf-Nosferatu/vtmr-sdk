/**
 * Effect DiscVigorMortis script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscVigorMortis extends Codex
{
	public EffectDiscVigorMortis()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);
		CodexActor _Creator = new CodexActor(creatorGuid);

		// initial effect
		_Target.SpawnThing("blackMagic");

		// assign the creator's team to the possessed actor
		_Target.SetActorTeam(_Creator.GetActorTeam());

		CaptureThing(actorGuid);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect(actorGuid);
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect(guid);
	}

	public void EndEffect(int targetGuid)
	{
		CodexActor _Target = new CodexActor(targetGuid);
		_Target.SetActorTeam(_Target.GetActorOriTeam());
		_Target.DamageActor(1000.0f, DAMAGE_TYPE_AGGRAVATED, 0);
	}

}