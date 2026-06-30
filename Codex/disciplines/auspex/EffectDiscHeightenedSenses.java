/**
 * Effect DiscHeightenedSenses script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscHeightenedSenses extends Codex
{
	private int				padGuid;
	
	// --------------------------------------------------------------------------------------------

	public EffectDiscHeightenedSenses()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		// flash shell at startup
		CodexActor _Actor = new CodexActor(actorGuid);
		
		// placeholder effect
		_Actor.SetShell("redCloudShell_b", 0x5000, 0, .9f, 1, 1);

		CaptureThing(actorGuid);

		// running pad effect
		padGuid = _Actor.SpawnThing("rosettePad");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Actor.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);
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
		// Remove pad and shell
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