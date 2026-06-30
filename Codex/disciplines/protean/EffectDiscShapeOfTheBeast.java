/**
 * Effect DiscShapeOfTheBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB/JS
*/


public class EffectDiscShapeOfTheBeast extends Codex
{
	private static final int	TIMER_ID_WOLF			= 0;

	private int _shifterGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscShapeOfTheBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_shifterGuid = actorGuid;
		CodexActor shifter = new CodexActor(_shifterGuid);
		CaptureThing(_shifterGuid);
		
		shifter.SetAlpha((float)0.0, (float)1500.0);
		float len = shifter.PlayMotionSetMode(MOTION_CROUCH, false, (float)30.0) / 1000;
		SetTimer(len, TIMER_ID_WOLF);

		shifter.SetActorFlags(THING_AF_NODISC + THING_AF_NOFEED);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EffectEnded();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EffectEnded();
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_WOLF:

				CodexActor shifter = new CodexActor(_shifterGuid);
				shifter.ActorShapeShift(shifter.GetActorClanShapeTemplate(), 0);
				shifter.SetAlpha(0);
				shifter.SetAlpha((float)1.0, (float)2000.0);
				shifter.StopActorAction();
				break;
		}
	}

	void EffectEnded()
	{
		CodexActor shifter = new CodexActor(_shifterGuid);

		shifter.ActorEndShapeShift();
		shifter.SetShell("shellSprite_white", 0x5000, 0, 2, 1, 1);
		shifter.SetAlpha(0);
		shifter.SetAlpha((float)1.0, (float)2000.0);
		shifter.StopActorAction();

		shifter.ClearActorFlags(THING_AF_NODISC + THING_AF_NOFEED);

		// make sure the other effects are cancelled too
		shifter.RemoveActorEffect("ef_disc_shapeofthebeast2");
		shifter.RemoveActorEffect("ef_disc_shapeofthebeast3");
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(_shifterGuid);
	}

	public void restore(int flags)
	{
		_shifterGuid = CodexSequence.RestoreInt();
	}

}