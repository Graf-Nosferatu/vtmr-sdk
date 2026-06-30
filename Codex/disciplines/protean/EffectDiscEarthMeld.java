/**
 * EffectDiscEarthMeld script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscEarthMeld extends Codex
{
	float []				vecScale;

	int						_targetGuid;
	int						_effectGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscEarthMeld()
	{
		vecScale = new float[3];
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_effectGuid = effectGuid;
		_targetGuid = actorGuid;

		CodexActor _Actor	= new CodexActor(_targetGuid);
		CaptureThing(actorGuid);

		_Actor.SetActorFlags(THING_AF_INVUL + THING_AF_NODISC + THING_AF_NEUTRAL + THING_AF_NOFEED);

		vecScale[VEC_X] = 1.0f;
		vecScale[VEC_Y] = 1.0f;
		vecScale[VEC_Z] = 0.01f;

		// scale the actor
		_Actor.SetScale(vecScale, 1.0f);

		_Actor.SetShell(_Actor.GetSurfaceMaterial(), 0x80, 0.99f, 1, 5, 1);

		// delay by a frame to solve an action system slight problem
		SetTimer(0.01f);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect();
	}

	public void actorunstill(int actorGuid, int captureID)
	{
		CodexActor _Actor	= new CodexActor(_targetGuid);
		_Actor.RemoveActorEffect(_effectGuid);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexActor _Actor	= new CodexActor(_targetGuid);

		// start watching for an unstill message
		_Actor.ActorStill();

		// and freeze the animation
		_Actor.FreezeAnimations();
	}

	public void EndEffect()
	{
		// make sure no timer is still active
		KillAllTimers();

		vecScale[VEC_X] = 1.0f;
		vecScale[VEC_Y] = 1.0f;
		vecScale[VEC_Z] = 1.0f;

		CodexActor _Actor	= new CodexActor(_targetGuid);

		// make sure the other 2 effects are gone too
		_Actor.RemoveActorEffect("ef_disc_earthmeld2");
		_Actor.RemoveActorEffect("ef_disc_earthmeld3");

		// set the normal scale again
		_Actor.SetScale(vecScale, 1.0f);

		_Actor.UnfreezeAnimations();

		// end the shell effect
		_Actor.EndShell();

		_Actor.ClearActorFlags(THING_AF_INVUL + THING_AF_NODISC + THING_AF_NEUTRAL + THING_AF_NOFEED);
	}


	public void save(int flags)
	{
		CodexSequence.SaveInt(_targetGuid);
		CodexSequence.SaveInt(_effectGuid);
	}

	public void restore(int flags)
	{
		_targetGuid = CodexSequence.RestoreInt();
		_effectGuid = CodexSequence.RestoreInt();
	}

}