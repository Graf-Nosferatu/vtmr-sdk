/**
 * Effect DiscCallLightning script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscCallLightning extends Codex
{
	public EffectDiscCallLightning()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target = new CodexActor(actorGuid);
		
		int beamGuid = _Target.SpawnThing("blueLightningOne");
		int cloudGuid = _Target.SpawnThing("lightningCloud");
		CodexThing beam = new CodexThing(beamGuid);
		CodexThing cloud = new CodexThing(cloudGuid);
		float[]	offset = new float[3];

		// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
		beam.AllocateFrames(2);
		offset = _Target.GetPosition();
		offset[VEC_Z] += 400;
		beam.SetFramePosition(1, offset);
		cloud.SetPosition(offset);

		// screen effect
		CodexCamera.AddFlash(actorGuid, (float)0.0, (float)255.0, (float)0.5, 0xffffff, true);
		CodexCamera.AddFlash(creatorGuid, (float)0.0, (float)255.0, (float)0.5, 0xffffff, true);
	}

	//public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	//{
	//
	//}

}