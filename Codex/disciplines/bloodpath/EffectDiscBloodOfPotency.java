/**
 * Effect DiscBloodOfPotency script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscBloodOfPotency extends Codex
{
	private int				padGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscBloodOfPotency()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator	= new CodexActor(creatorGuid);

		// initial effect
		_Creator.SpawnThing("redMagic");

		// running shell effect
		_Creator.SetShell("redCloudShell", 0x1000, 0, .3f, 1, 1);

		// running pad effect
		padGuid = _Creator.SpawnThing("ankhsPadRed");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Creator.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		CaptureThing(creatorGuid);

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
		// Remove pad
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();

		CodexActor _Creator	= new CodexActor(targetGuid);
		_Creator.EndShell();

		// make sure the other effects are cancelled too
		_Creator.RemoveActorEffect("ef_disc_bloodofpotency2");
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