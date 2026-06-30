/**
 * Effect DiscSubsumeTheSpirit script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscSubsumeTheSpirit extends Codex
{
		private int				padGuid;
	private int				_targetGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscSubsumeTheSpirit()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_targetGuid = actorGuid;
		CodexActor _Target 	= new CodexActor(_targetGuid);
		CodexActor _Creator	= new CodexActor(creatorGuid);

		// initial effect
		_Creator.SpawnThing("yellowMagic");
		
		// running pad effect
		padGuid = _Target.SpawnThing("ringOfWolvesRed");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;
		_Target.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		// assign the creator's team to the possessed actor
		_Target.SetActorTeam(_Creator.GetActorTeam());

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

		CodexActor _Target 	= new CodexActor(_targetGuid);
		_Target.SetActorTeam(_Target.GetActorOriTeam());
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(padGuid);
		CodexSequence.SaveInt(_targetGuid);
	}

	public void restore(int flags)
	{
		padGuid = CodexSequence.RestoreInt();
		_targetGuid = CodexSequence.RestoreInt();
	}
}

