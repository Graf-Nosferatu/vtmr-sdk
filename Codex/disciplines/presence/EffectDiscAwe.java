/**
 * Effect DiscAwe script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscAwe extends Codex
{
	private int				padGuid;
	private int				secondaryGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscAwe()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("yellowMagic");
		
		// running pad effect
		padGuid = _Target.SpawnThing("ringOfEyesYellow");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Target.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		secondaryGuid = _Target.SpawnThing("yellowMagic");

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
		// Remove pad and flame
		CodexThing pad	= new CodexThing(padGuid);
		CodexThing flame = new CodexThing(secondaryGuid);
		pad.Remove();
		flame.Remove();
	}


	public void save(int flags)
	{
		CodexSequence.SaveInt(padGuid);
		CodexSequence.SaveInt(secondaryGuid);
	}

	public void restore(int flags)
	{
		padGuid = CodexSequence.RestoreInt();
		secondaryGuid = CodexSequence.RestoreInt();
	}
}