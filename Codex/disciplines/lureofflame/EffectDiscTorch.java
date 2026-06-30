/**
 * Effect DiscTorch script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscTorch extends Codex
{	
	private int				flameGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscTorch()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator	= new CodexActor(creatorGuid);

		// create a dynamic light and attach it to the caster
		flameGuid = _Creator.SpawnThing("disciplineTorch");
		//int boneNum = _Creator.FindBone(MOTIONTAG_USER0);
		float[] offset = new float[3];
		offset[0] = 32; offset[1] = 32; offset[2] = 64; 
		//offset = _Creator.FindBoneOffset(MOTIONTAG_USER0);
		_Creator.AttachThing(flameGuid, 0, offset, ATTACH_FLAG_AUTOREMOVE);

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

	public void EndEffect(int creatorGuid)
	{
		// Remove pad and shell
		CodexThing flame = new CodexThing(flameGuid);
		flame.Remove();
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(flameGuid);
	}

	public void restore(int flags)
	{
		flameGuid = CodexSequence.RestoreInt();
	}
}