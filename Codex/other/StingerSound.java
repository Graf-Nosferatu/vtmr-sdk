/**
 * Stinger Sound script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class StingerSound extends Codex
{
	private	String		_soundName;
	private float		_minDist;
	private float		_maxDist;
	private	int			_volume;
	private	int			_pan;
	private	int			_flags;
	private float		_minFrequency;
	private float		_maxFrequency;
	private boolean		bTimerFired = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Sound name", "Min Distance;300", "MaxDistance;600", "Volume;75", "Pan;0", "Flags;0", "Min Frequency;5", "Max Frequency;10"};

	public StingerSound(String soundName, float minDist, float maxDist, int volume, int pan, int flags, float minFrequency, float maxFrequency)
	{	
		_soundName		= soundName;
		_minDist		= minDist;
		_maxDist		= maxDist;
		_volume			= volume;
		_pan			= pan;
		_flags			= flags;
		_minFrequency	= minFrequency;
		_maxFrequency	= maxFrequency;
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		if(!bTimerFired)
		{
			KillAllTimers();
			SetTimer((float)(_minFrequency + Math.random() * (_maxFrequency - _minFrequency)));

			bTimerFired = true;
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// play the sound
		new CodexSound(_soundName, _minDist, _maxDist, _volume, _pan, _flags, GetClassThing());

		// prepare for the next one
		SetTimer((float)(_minFrequency + Math.random() * (_maxFrequency - _minFrequency)));
	}
}
