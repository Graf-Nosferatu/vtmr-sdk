/**
 * Effect DiscPsychicProjection script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscPsychicProjection extends Codex
{
	public EffectDiscPsychicProjection()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Projection	= new CodexActor(actorGuid);
		CodexActor _Caster		= new CodexActor(creatorGuid);

		// Set actor flags
		_Projection.SetActorFlags(THING_AF_ETHEREAL);
		_Projection.SetActorTeam(_Caster.GetActorTeam());

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

	public void EndEffect(int projGuid)
	{
		CodexActor _Projection = new CodexActor(projGuid);
		_Projection.Remove();
	}

}
