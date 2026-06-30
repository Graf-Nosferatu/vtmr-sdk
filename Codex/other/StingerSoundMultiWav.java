/**
 * Stinger Sound Multiple Wav script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class StingerSoundMultiWav extends Codex
{
	//private final float		FREQUENCY_MIN = (float)1;
	//private final float		FREQUENCY_MAX = (float)5;

	//private final int		MAX_SOUNDS = 10;

	//private boolean			bPlaying = false;
	private CodexSound		curSound;

	// --------------------------------------------------------------------------------------------

	private String[]		aSounds;
	//private float			randMult = FREQUENCY_MAX - FREQUENCY_MIN;
	private float			_randMult;
	private float			_minFrequency;
	private int				_NumberOfSounds;
	private float			_minDistance;
	private float			_maxDistance;
	private int				_volume;

	private boolean			bTimerFired = false;


	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Number of sounds; 1", "Sound name 1", "Sound name 2", "Sound name 3", "Sound name 4", "Sound name 5", 
										"Sound name 6", "Sound name 7", "Sound name 8", "Sound name 9", "Sound name 10", 
										"Min Distance;300", "MaxDistance;600", "Volume;75", "Min Frequency;5", "Max Frequency;10"};

	public StingerSoundMultiWav(int NumberOfSounds, String SoundName1, String SoundName2, String SoundName3, String SoundName4, String SoundName5, 
									String SoundName6, String SoundName7, String SoundName8, String SoundName9, String SoundName10,
									float minDistance, float maxDistance, int volume, float minFrequency, float maxFrequency)
	{
		_NumberOfSounds = NumberOfSounds;
		_minDistance = minDistance;
		_maxDistance = maxDistance;
		_volume = volume;
		_minFrequency = minFrequency;

		aSounds = new String[NumberOfSounds];

		_randMult = maxFrequency - minFrequency;

		if(NumberOfSounds > 0)
			aSounds[0] = SoundName1;
	
		if(NumberOfSounds > 1)
			aSounds[1] = SoundName2;
	
		if(NumberOfSounds > 2)
			aSounds[2] = SoundName3;

		if(NumberOfSounds > 3)
			aSounds[3] = SoundName4;

		if(NumberOfSounds > 4)
			aSounds[4] = SoundName5;

		if(NumberOfSounds > 5)
			aSounds[5] = SoundName6;

		if(NumberOfSounds > 6)
			aSounds[6] = SoundName7;

		if(NumberOfSounds > 7)
			aSounds[7] = SoundName8;

		if(NumberOfSounds > 8)
			aSounds[8] = SoundName9;

		if(NumberOfSounds > 9)
			aSounds[9] = SoundName10;
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		if(!bTimerFired)
		{
			KillAllTimers();
			if(_NumberOfSounds > 0)
				SetTimer((float)(_minFrequency + Math.random() * _randMult));

			bTimerFired = true;
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// play a random sound
		//if(bPlaying)
		//	curSound.Stop();

		int index = (int)(Math.random() * _NumberOfSounds);

		// just to be sure we don't try and play one out of the range of the array
		// number of sounds is 1 more than the index in the array
		if(index == _NumberOfSounds)
			index--;

		curSound = new CodexSound(aSounds[index], _minDistance, _maxDistance, 4 + (int)(Math.random() * _volume), 0, 0, GetClassThing());

		//bPlaying = true;

		// prepare for the next one
		SetTimer((float)(_minFrequency + Math.random() * _randMult));
	}

/*
	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		if(bPlaying)
		{
			curSound.Stop();
			bPlaying = false;
		}
	}
*/

}
