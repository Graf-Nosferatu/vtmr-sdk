/**
 * Effect DiscFortitude script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscFortitude extends Codex
{
	private int				_creatorGuid;	
	private int				padGuid;
	
	// --------------------------------------------------------------------------------------------

	public EffectDiscFortitude()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_creatorGuid	= actorGuid;
		CodexActor _Creator	= new CodexActor(_creatorGuid);
		
		// initial effect
		_Creator.SpawnThing("invshockwave");
		
		// flash shell at startup
		_Creator.SetShell("redCloudShell_b", 0x5000, 0, .9f, 1, 1);

		// running pad effect
		padGuid = _Creator.SpawnThing("sawToothBlue");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Creator.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		CaptureThing(_creatorGuid);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect();
	}

	public void soaked(int guid, int causeGUID, float damageAmount, int damageType, float soakAmount, int captureID)
	{
		CodexActor _Creator	= new CodexActor(_creatorGuid);

		if((soakAmount > 0) && (soakAmount >= damageAmount))
		{
			_Creator.SetShell("white", 0x5000, 0, 1, 1, 1);
		}
	}

	public void EndEffect()
	{
		// Remove pad and shell
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();

		CodexActor _Creator	= new CodexActor(_creatorGuid);
		_Creator.EndShell();
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(padGuid);
		CodexSequence.SaveInt(_creatorGuid);
	}

	public void restore(int flags)
	{
		padGuid = CodexSequence.RestoreInt();
		_creatorGuid = CodexSequence.RestoreInt();
	}

}

