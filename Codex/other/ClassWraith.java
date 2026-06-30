/**
 * ClassWraith script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ClassWraith extends Codex
{
	public ClassWraith()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void created(int guid)
	{
		CodexThing spectralThing = new CodexThing(GetClassThing());

		if(spectralThing != null)
		{
			int trailGuid = spectralThing.SpawnThing("trail_spectral");
			int rfingersGuid = spectralThing.SpawnThing("trail_spectralHands");
			int lfingersGuid = spectralThing.SpawnThing("trail_spectralHands");

			int boneNum0 = spectralThing.FindBone(MOTIONTAG_USER1);
			int boneNum1 = spectralThing.FindBone(MOTIONTAG_RFINGERS);
			int boneNum2 = spectralThing.FindBone(MOTIONTAG_LFINGERS);

			float[] offset0 = new float[3];
			float[] offset1 = new float[3];
			float[] offset2 = new float[3];

			offset0 = spectralThing.FindBoneOffset(MOTIONTAG_USER1);
			offset1 = spectralThing.FindBoneOffset(MOTIONTAG_RFINGERS);
			offset2 = spectralThing.FindBoneOffset(MOTIONTAG_LFINGERS);

			spectralThing.AttachThing(trailGuid, boneNum0, offset0, ATTACH_FLAG_AUTOREMOVE);
			spectralThing.AttachThing(rfingersGuid, boneNum1, offset1, ATTACH_FLAG_AUTOREMOVE);
			spectralThing.AttachThing(lfingersGuid, boneNum2, offset2, ATTACH_FLAG_AUTOREMOVE);
		}
	}	
}