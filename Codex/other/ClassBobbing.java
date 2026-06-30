/**
 * Bobbing object script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 
public class ClassBobbing extends Codex
{
	private CodexThing	_tracker;
	private CodexThing	_bobber;

	private float		_minSpeed;
	private float		_maxSpeed;
	private float		_delay;
	private boolean		bMoved = false;

	public static String _params[] = {"Bobbing thing", "Min speed;8", "Max speed;15", "Delay;1"};

	public ClassBobbing(CodexThing bobber, float minSpeed, float maxSpeed, float delay)
	{
		_tracker = new CodexThing(GetClassThing());

		_bobber = new CodexThing(bobber.GetGUID());
		_minSpeed = minSpeed;
		_maxSpeed = maxSpeed;
		_delay = delay;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		float[] offset = new float[3];

		offset[0] = 0; 
		offset[1] = 0; 
		offset[2] = 0;

		_tracker.AttachThing(_bobber.GetGUID(), -1, offset, 0);

		if(!bMoved)
		{
			_tracker.MoveToFrame(1, (float)(_minSpeed + Math.random() * (_maxSpeed - _minSpeed)));

			bMoved = true;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		SetTimer((frameNum == 0) ? (float)(Math.random() * _delay) : 0, 0, (frameNum == 0 ? 1 : 0));
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_tracker.MoveToFrame((int)arg0, (float)(_minSpeed + Math.random() * (_maxSpeed - _minSpeed)));
	}
}
