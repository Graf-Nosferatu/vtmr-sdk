/**
 * Rotate Always script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 

public class RotateAlways extends Codex
{
	private CodexThing	_mover;

	private int			_frameNum = 1;
	private float		_duration = (float)3.0;
	private int			_delay = 0;

	public static String _params[] = {"Rotate around frame;1", "Duration;3.0", "Delay; 0"};


	public RotateAlways(int frameNum, float duration, int delay)
	{
		_frameNum = frameNum;
		_duration = duration;
		_delay = delay;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_mover = new CodexThing(GetClassThing());

		_mover.RotatePivot(_frameNum, _duration);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_mover.RotatePivot(_frameNum, _duration);
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		SetTimer(_delay);
	}

}



