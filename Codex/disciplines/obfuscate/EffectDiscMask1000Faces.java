/**
 * Effect DiscMask1000Faces script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB/JS
*/


public class EffectDiscMask1000Faces extends Codex
{
	private static final int	TIMER_ID_MASK	= 0;

	private int				_shifterGuid;
	private int				_maskGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscMask1000Faces()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_shifterGuid = actorGuid;
		CodexActor shifter = new CodexActor(_shifterGuid);
		CaptureThing(_shifterGuid);

		_maskGuid = shifter.GetActorEffectIntParam(effectGuid);
		
		shifter.SetShell("shellSprite_white", 0x5000, 0, 3, 1, 1);
		shifter.SetAlpha((float)0.0, (float)1500.0);

		shifter.SetActorFlags(THING_AF_NODISC);

		SetTimer(1, TIMER_ID_MASK);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EffectEnded();
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_MASK:
				CodexActor shifter = new CodexActor(_shifterGuid);
				shifter.SetShell("shellSprite_white", 0x5000, 0, 2, 1, 1);

				CodexActor mask= new CodexActor(_maskGuid);
				shifter.ActorShapeShift(mask.GetTemplateName(), ACTOR_SHAPESHIFT_NOFOLEY + ACTOR_SHAPESHIFT_WEAPONLESS);
				shifter.SetAlpha(0);
				shifter.SetAlpha((float)1.0, (float)2000.0);
				shifter.StopActorAction();
				break;
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EffectEnded();
	}


	void EffectEnded()
	{
		CodexActor shifter = new CodexActor(_shifterGuid);

		shifter.ActorEndShapeShift();
		shifter.SetShell("shellSprite_white", 0x5000, 0, 2, 1, 1);
		shifter.SetAlpha(0);
		shifter.SetAlpha((float)1.0, (float)2000.0);
		shifter.StopActorAction();

		shifter.ClearActorFlags(THING_AF_NODISC);
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(_shifterGuid);
		CodexSequence.SaveInt(_maskGuid);
	}

	public void restore(int flags)
	{
		_shifterGuid = CodexSequence.RestoreInt();
		_maskGuid = CodexSequence.RestoreInt();
	}

}