/**
 * BatFlying script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * This script plays the bat flying anim repeating randomly somewhere
 * between 2 and 30 seconds
*/


public class BatFlying extends Codex
{
	private CodexActor		bat;

	private float			_minTime;
	private float			_maxTime;
	private boolean			bTimerFired = false;

	public static String _params[] = {"Min time;5", "Max time;25"};

	public BatFlying(float minTime, float maxTime)
	{
		bat = new CodexActor(GetClassThing());

		// bat animation is 1.3 second, don't have min time less than that
		if(minTime < 2)
			_minTime = 2;
		else
			_minTime = minTime;

		// if max is less than min, bump it up
		if(maxTime <= _minTime)
			_maxTime = _minTime + 5;
		else
			_maxTime = maxTime;
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		if(!bTimerFired)
		{
			KillAllTimers();
			SetTimer((float)(_minTime + Math.random() * (_maxTime - _minTime)));

			bTimerFired = true;
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		bat.PlayMotionSetMode(MOTION_RUN, false, (float)30.0);

		SetTimer((float)(_minTime + Math.random() * (_maxTime - _minTime)));
	}
}
