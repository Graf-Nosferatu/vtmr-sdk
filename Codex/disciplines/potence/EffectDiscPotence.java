/**
 * Effect DiscPotence script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscPotence extends Codex
{
	private int				padGuid;
	
	// --------------------------------------------------------------------------------------------

	public EffectDiscPotence()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator	 = new CodexActor(actorGuid);
		
		// initial effect
		_Creator.SpawnThing("ringTower");

		// running shell effect
		_Creator.SetShell("redCloudShell_b", 0x1000, 0, .3f, 1, 1);

		// running pad effect
		padGuid = _Creator.SpawnThing("sawToothBlue");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Creator.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

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
		// Remove pad and shell
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();

		CodexActor _Creator	 = new CodexActor(actorGuid);
		_Creator.EndShell();
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