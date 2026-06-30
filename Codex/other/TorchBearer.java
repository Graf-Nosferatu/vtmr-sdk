/**
 * TorchBearer script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class TorchBearer extends Codex
{
	int			flameGuid = 0;
	CodexActor	bearer;

	public TorchBearer()
	{
		bearer = new CodexActor(GetClassThing());
	}

	public void created(int guid)
	{	
		CreateTorch();
	}	


	public void beginscene(int clientGuid, int captureID)
	{
		if((bearer.GetActorFlags() & THING_AF_DEAD) != 0)
			bearer.ReviveActor(100, 100);
		else
		{
			// add random blood to the pedestrian
			bearer.SetActorBaseStat(ACTOR_STAT_BLOOD, bearer.GetActorBaseStat(ACTOR_STAT_BLOOD) + (float)(Math.random() * 33.0f));
			bearer.SetActorStat(ACTOR_STAT_BLOOD, bearer.GetActorBaseStat(ACTOR_STAT_BLOOD));
		}

		if(flameGuid == 0)
			CreateTorch();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(flameGuid != 0)
		{
			bearer.DetachThing(flameGuid);
			flameGuid = 0;
		}
	}

	public void CreateTorch()
	{
		if(bearer != null)
		{
			flameGuid = bearer.SpawnThing("torchFlame");
			int boneNum = bearer.FindBone(MOTIONTAG_USER0);

			float[] offset = new float[3];
			offset = bearer.FindBoneOffset(MOTIONTAG_USER0);

			bearer.AttachThing(flameGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE);
		}
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
