/**
 * Effect DiscCelerity script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscCelerity extends Codex
{
	private int				trailGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscCelerity()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("blueMagic");
		
		// running trail effect
		trailGuid = _Target.SpawnThing("celerityTrail");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Target.AttachThing(trailGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

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

	public void EndEffect(int actorGuid)
	{
		// Remove trail
		CodexThing trail = new CodexThing(trailGuid);
		trail.Remove();

		// make sure the other effects are cancelled too
		CodexActor actor = new CodexActor(actorGuid);
		actor.RemoveActorEffect("ef_disc_celerity2");

	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(trailGuid);
	}

	public void restore(int flags)
	{
		trailGuid = CodexSequence.RestoreInt();
	}
}