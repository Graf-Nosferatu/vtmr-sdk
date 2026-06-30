/**
 * Effect DiscAtrophy script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscAtrophy extends Codex
{	
	private int				padGuid;
	
	// --------------------------------------------------------------------------------------------

	public EffectDiscAtrophy()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);
		
		// initial effect
		_Target.SpawnThing("invshockwave_y");
		_Target.SetShell("redCloudShell_y", 0x1000, 0, 1, 1, 1);

		// running pad effect
		padGuid = _Target.SpawnThing("swirlingSkullsYellow");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Target.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

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
		// Remove pad and shell
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();

		CodexActor _Target = new CodexActor(targetGuid);
		_Target.EndShell();
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