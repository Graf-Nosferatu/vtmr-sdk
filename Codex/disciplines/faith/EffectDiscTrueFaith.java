/**
 * Effect DiscTrueFaith script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB/JS
*/


public class EffectDiscTrueFaith extends Codex
{
	private int			_explosionGuid;
	private int			domeGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscTrueFaith()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor caster = new CodexActor(actorGuid);
		_explosionGuid = caster.GetActorEffectIntParam(effectGuid);

		domeGuid = caster.SpawnThing("holyHalfdome");

		CodexThing Dome = new CodexThing(domeGuid);
		Dome.SetAlpha(0.05f);

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		caster.AttachThing(domeGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		CaptureThing(actorGuid);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EffectEnded(actorGuid);
	}


	public void killed(int guid, int causeID, int captureID)
	{
		EffectEnded(guid);
	}


	void EffectEnded(int casterGuid)
	{
		CodexThing explosion = new CodexThing(_explosionGuid);
		explosion.Remove();

		// Remove dome
		CodexThing dome	= new CodexThing(domeGuid);
		dome.Remove();
	}


	public void save(int flags)
	{
		CodexSequence.SaveInt(_explosionGuid);
		CodexSequence.SaveInt(domeGuid);
	}

	public void restore(int flags)
	{
		_explosionGuid = CodexSequence.RestoreInt();
		domeGuid = CodexSequence.RestoreInt();
	}

}