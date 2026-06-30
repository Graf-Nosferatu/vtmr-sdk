/**
 * ClassWander script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 

public class ClassWander extends Codex
{
	private static final int	TIMER_ID_WANDER		= 0;

	private static final int	THINK_TIME = 2;
	private static final int	RESTART_BUFFER = 10;

	private CodexActor	_wanderThing;
	private	float		_minSearch;
	private float		_maxSearch;
	private float		_minDelay;
	private float		_maxDelay;
	private float		_speed;
	private float		_stepHeight;

	private float[]		pos = new float[3];

	public static String _params[] = {"Min search radius;64", "Max search radius;320", "Min delay;2", "Max delay; 10", "Speed;210", "Step height to try; 16"};

	public ClassWander(float minSearch, float maxSearch, float minDelay, float maxDelay, float speed, float stepHeight)
	{
		_wanderThing = new CodexActor(GetClassThing());

		_minSearch = minSearch;
		_maxSearch = maxSearch;
		_minDelay = minDelay;
		_maxDelay = maxDelay;
		_speed = speed;
		_stepHeight = stepHeight;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		SendThing();
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		SetTimer((float)(_minDelay + Math.random() * (_maxDelay - _minDelay)), TIMER_ID_WANDER);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_WANDER:

				KillAllTimers();

				_wanderThing.Stop();

				SendThing();

				break;
		}
	}

	public void SendThing()
	{
		boolean bSuccess = false;

		pos = _wanderThing.GetPosition();

		pos[VEC_X] = pos[VEC_X] + (float)((_minSearch + Math.random() * (_maxSearch - _minSearch)) * ((Math.random() < 0.5) ? -1 : 1));
		pos[VEC_Y] = pos[VEC_Y] + (float)((_minSearch + Math.random() * (_maxSearch - _minSearch)) * ((Math.random() < 0.5) ? -1 : 1));

		while(!bSuccess)
		{
			if(_wanderThing.SendActorToPos(pos, _speed))
			{
				bSuccess = true;

				// even if we're successful, it could get hung up on its way
				// to the destination, in that case, this timer will stop it and
				// pick another spot to send it to - we wait the maxDelay plus
				// the buffer defined above
				SetTimer((float)(_maxDelay + RESTART_BUFFER), TIMER_ID_WANDER);
			}
			else
			{
				pos[VEC_Z] = pos[VEC_Z] + _stepHeight;

				if(_wanderThing.SendActorToPos(pos, _speed))
				{
					bSuccess = true;

					// even if we're successful, it could get hung up on its way
					// to the destination, in that case, this timer will stop it and
					// pick another spot to send it to - we wait the maxDelay plus
					// the buffer defined above
					SetTimer((float)(_maxDelay + RESTART_BUFFER), TIMER_ID_WANDER);
				}
				else
				{
					// we've failed, so wait a couple seconds to try again
					SetTimer(THINK_TIME, TIMER_ID_WANDER);

					break;
				}
			}
		}
	}
}



