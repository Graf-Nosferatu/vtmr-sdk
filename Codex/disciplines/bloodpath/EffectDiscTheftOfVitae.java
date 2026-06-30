/**
 * Effect DiscTheftOfVitae script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscTheftOfVitae extends Codex
{
	private int				beamGuid = 0;
	private int				targetGuid = 0;

	// --------------------------------------------------------------------------------------------

	public EffectDiscTheftOfVitae()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator	= new CodexActor(creatorGuid);
		CodexActor _Target	= new CodexActor(actorGuid);
		targetGuid = actorGuid;

		// placeholder effect
		_Creator.SetShell("redCloudShell", 0x5000, 0, .9f, 1, 1);
		_Target.SpawnThing("redMagic");

		// Running effect
		beamGuid = _Target.SpawnThing("bloodStream");
		CodexThing beam = new CodexThing(beamGuid);
		float[]	offset = new float[3];

		// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
		beam.AllocateFrames(2);
		offset = _Creator.GetPosition();
		offset[VEC_Z] += 64;
		beam.SetFramePosition(1, offset);

		offset[0] = offset[1] = offset[2] = 0.0f;

		// Switch to chest tag once they're universally available
		_Target.AttachThing(beamGuid, _Target.FindBone(MOTIONTAG_CHEST), offset, ATTACH_FLAG_AUTOREMOVE);
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
		// Remove beam
		if(beamGuid != 0)
		{
			CodexThing beam	= new CodexThing(beamGuid);
			beam.Remove();
		}
	}

	// nuke the effect if the target transitions levels
	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		if((targetGuid == clientGuid) || !NetIsConnected())
			EndEffect(targetGuid);
	}
}