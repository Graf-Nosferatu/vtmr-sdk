/**
 * Stinger Sound script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class SteamBlast extends Codex
{
	private CodexThing	generatorThing;
	
	private	String		_soundName		= "steam_blast_01.wav";
	private float		_minDist		= 512;
	private float		_maxDist		= 1024;
	private	int			_volume			= 75;
	private	int			_pan			= 0;
	private	int			_flags			= 0;
	private float		_minFrequency	= 3;
	private float		_maxFrequency	= 10;

	// --------------------------------------------------------------------------------------------

	public SteamBlast()
	{	

	}

	// --------------------------------------------------------------------------------------------

	public void created(int guid)
	{
		generatorThing = new CodexThing(guid);

		SetTimer((float)(_minFrequency + Math.random() * (_maxFrequency - _minFrequency)));
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// create the steamburst and play the sound
		generatorThing.SpawnThing("steamBlast");
		new CodexSound(_soundName, _minDist, _maxDist, _volume, _pan, _flags, generatorThing.GetGUID());

		// prepare for the next one
		SetTimer((float)(_minFrequency + Math.random() * (_maxFrequency - _minFrequency)));
	}
}
