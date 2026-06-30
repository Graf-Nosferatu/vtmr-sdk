/**
 * RegionTriggerSound script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class RegionTriggerSound extends Codex
{
	private	String		_soundName;
	private float		_minDist;
	private float		_maxDist;
	private	int			_volume;
	private	int			_pan;
	private	int			_flags;
	private int			_playOnce;

	private boolean		done = false;

	public static String _params[] = {"Sound name", "Min Distance;300", "MaxDistance;600", "Volume;50", "Pan;0", "Flags;0", "Play once; 1"};

	public RegionTriggerSound(String soundName, float minDist, float maxDist, int volume, int pan, int flags, int playOnce)
	{	
		_soundName	= soundName;
		_minDist	= minDist;
		_maxDist	= maxDist;
		_volume		= volume;
		_pan		= pan;
		_flags		= flags;

		_playOnce	= playOnce;
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!done)
		{
			new CodexSound(_soundName, _minDist, _maxDist, _volume, _pan, _flags, GetClassThing());

			done = true;
		}
	}

	public void save(int flags)
	{
		if(_playOnce == 1)
			CodexSequence.SaveBoolean(done);
	}
 
	public void restore(int flags)
	{
		if(_playOnce == 1)
			done = CodexSequence.RestoreBoolean();
	}
}
