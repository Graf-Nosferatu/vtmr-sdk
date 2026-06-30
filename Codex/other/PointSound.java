/**
 * PointSound script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class PointSound extends Codex
{
	private	String		_soundName;
	private float		_minDist;
	private float		_maxDist;
	private	int			_volume;
	private	int			_pan;
	private	int			_flags;

	public static String _params[] = {"Sound name", "Min Distance;300", "MaxDistance;600", "Volume;75", "Pan;0", "Flags;0"};

	public PointSound(String soundName, float minDist, float maxDist, int volume, int pan, int flags)
	{	
		_soundName	= soundName;
		_minDist	= minDist;
		_maxDist	= maxDist;
		_volume		= volume;
		_pan		= pan;
		_flags		= flags;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		new CodexSound(_soundName, _minDist, _maxDist, _volume, _pan, _flags, GetClassThing());
	}
}
