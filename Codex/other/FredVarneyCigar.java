/**
 * FredVarneyCigar script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class FredVarneyCigar extends Codex
{
	int			smokeGuid = 0;
	CodexActor	bearer;

	public FredVarneyCigar()
	{
		bearer = new CodexActor(GetClassThing());
	}

	public void created(int guid)
	{	
		CreateSmoke();
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

		if(smokeGuid == 0)
			CreateSmoke();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(smokeGuid != 0)
		{
			bearer.DetachThing(smokeGuid);
			smokeGuid = 0;
		}
	}

	public void CreateSmoke()
	{
		if(bearer != null)
		{
			smokeGuid = bearer.SpawnThing("cigarSmoke");
			int boneNum = bearer.FindBone(MOTIONTAG_LFINGERS);

			float[] offset = new float[3];
			offset = bearer.FindBoneOffset(MOTIONTAG_LFINGERS);

			bearer.AttachThing(smokeGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE);
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(smokeGuid);
	}
 
	public void restore(int flags)
	{
		smokeGuid = CodexSequence.RestoreInt();
	}
}