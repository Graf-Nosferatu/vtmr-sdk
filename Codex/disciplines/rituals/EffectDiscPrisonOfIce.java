/**
 * Effect DiscPrisonOfIce script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscPrisonOfIce extends Codex
{
	CodexActor	target;

	boolean		bIsNoFeed;
	boolean		bIsNoBleed;

	// --------------------------------------------------------------------------------------------

	public EffectDiscPrisonOfIce()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		target = new CodexActor(actorGuid);

		// initial effect
		target.SpawnThing("blueMagic");
//		target.SetShell("redCloudShell_b", 0x1000, 0, .3f, 1, 1);
		target.SetShell("iceShell", 0x1000, 0, .3f, 1, 1);
		
		target.FreezeAnimations();

		bIsNoFeed  = ((target.GetActorFlags() & THING_AF_NOFEED) != 0);
		bIsNoBleed = ((target.GetActorFlags() & THING_AF_NOBLEED) != 0);
		
		target.SetActorFlags(THING_AF_NOFEED + THING_AF_NOBLEED);

		// capture the target
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

	public void EndEffect(int targetGuid)
	{
		CodexActor target = new CodexActor(targetGuid);
		target.UnfreezeAnimations();
		target.EndShell();

		if(!bIsNoFeed && !bIsNoBleed)
			target.ClearActorFlags(THING_AF_NOFEED + THING_AF_NOBLEED);
		else
		{
			if(!bIsNoFeed)
				target.ClearActorFlags(THING_AF_NOFEED);

			if(!bIsNoBleed)
				target.ClearActorFlags(THING_AF_NOBLEED);
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bIsNoFeed);
		CodexSequence.SaveBoolean(bIsNoBleed);
	}

	public void restore(int flags)
	{
		bIsNoFeed = CodexSequence.RestoreBoolean();
		bIsNoBleed = CodexSequence.RestoreBoolean();
	}
	
}