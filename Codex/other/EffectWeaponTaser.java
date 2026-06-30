/**
 * Effect WeaponTaser script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectWeaponTaser extends Codex
{
	private CodexActor		_Creator;
	private CodexActor		_Target;

	private int				beamGuid;

	// --------------------------------------------------------------------------------------------

	public EffectWeaponTaser()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_Creator	= new CodexActor(creatorGuid);
		_Target		= new CodexActor(actorGuid);

		// Running effect
		beamGuid = _Creator.SpawnThing("blueLightningOne");
		CodexThing beam = new CodexThing(beamGuid);
		float[]	offset = new float[3];

		// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
		beam.AllocateFrames(2);
		offset = _Target.GetPosition();
		offset[VEC_Z] += 64;
		beam.SetFramePosition(1, offset);

		offset[0] = offset[1] = offset[2] = 0.0f;

		// attach the beam end to the target
		//_Target.AttachThing(beamGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		// attach the beam end to the creator's hand
		_Creator.AttachThing(beamGuid, _Creator.FindBone(MOTIONTAG_RFINGERS), offset, ATTACH_FLAG_AUTOREMOVE);
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
		// Remove beam
		CodexThing beam	= new CodexThing(beamGuid);
		beam.Remove();
	}

}