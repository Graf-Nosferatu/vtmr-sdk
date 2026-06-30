/**
 * Effect DiscFeralClaws script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscFeralClaws extends Discipline
{
	public EffectDiscFeralClaws()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor caster	= new CodexActor(actorGuid);

		CaptureThing(actorGuid);

		caster.OverrideActorWeapon(FERALCLAWS_TEMPLATES[caster.GetActorEffectLevel(effectGuid)], true, true);
		caster.SetActorFlags(THING_AF_NOSHAPEDISC);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect(actorGuid);
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect(guid);
	}

	public void EndEffect(int casterGuid)
	{
		CodexActor caster = new CodexActor(casterGuid);

		caster.CancelOverrideActorWeapon();
		caster.ClearActorFlags(THING_AF_NOSHAPEDISC);
	}

}