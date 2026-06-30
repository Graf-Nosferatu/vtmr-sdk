/**
 * Effect DiscCauldronOfBlood script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscCauldronOfBlood extends Codex
{
	private int				padGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscCauldronOfBlood()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator	= new CodexActor(creatorGuid);
		CodexActor _Target	= new CodexActor(actorGuid);

		// placeholder effect
		_Target.SpawnThing("redMagic");

		// running pad effect
		padGuid = _Target.SpawnThing("ankhsPadRed");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Target.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		CaptureThing(creatorGuid);

	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect();
	}

	public void EndEffect()
	{
		// Remove pad
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(padGuid);
	}

	public void restore(int flags)
	{
		padGuid = CodexSequence.RestoreInt();
	}
}