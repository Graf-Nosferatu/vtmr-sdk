/**
 * Effect DiscPossession script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscPossession extends Codex
{
	private int				padGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscPossession()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);
		CodexActor _Creator = new CodexActor(creatorGuid);

		// initial effect
		_Target.SpawnThing("yellowMagic");

		// Running pad effect
		padGuid = _Target.SpawnThing("ankhs2PadYellow");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Target.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

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
		// Remove pad effect
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();

		CodexActor _Target = new CodexActor(targetGuid);
		_Target.SetActorTeam(_Target.GetActorOriTeam());
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