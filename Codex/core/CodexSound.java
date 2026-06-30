/**
 * Handles the link from Codex to an internal game sound. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexSound extends Codex
{
	private native int CreateLocal(int scriptGuid, String name, int volume, int pan, int flags, int clientGuid);
	private native int CreateAtPos(int scriptGuid, String name, float minDist, float maxDist, int volume, int pan, int flags, float[] position);
	private native int CreateAtThing(int scriptGuid, String name, float minDist, float maxDist, int volume, int pan, int flags, int thingGuid);

	// ------------------------------------------------------------------------

	public static String	aMusicName = "";
	public static int		musicVolume;

/**
 * Stops a sound.
 *               
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public native void Stop();

/**
 * Starts playing a music track.
 *
 * @param       name the name of the track to play
 * @param       volume the volume of the track to play
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native boolean StartMusic(String name, int volume);


/**
 * Stops the currently playing music.
 *               
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native void StopMusic();


/**
 * Gets the name of the currently playing music.
 *
 * @return      the name of the currently playing music
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native String GetMusicName();

/**
 * Gets a new volume for the currently playing music track.
 *
 * @return       the volume of the currently playing music
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native int GetMusicVolume();

/**
 * Sets a new volume for the currently playing music track.
 *
 * @param       volume the new volume
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native void SetMusicVolume(int volume);



/**
 * Starts playing a Ambient track.
 *
 * @param       name the name of the track to play
 * @param       volume the volume of the track to play
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native boolean StartAmbient(String name, int volume);


/**
 * Stops the currently playing Ambient.
 *               
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native void StopAmbient();


/**
 * Gets the name of the currently playing Ambient.
 *
 * @return      the name of the currently playing Ambient
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native String GetAmbientName();

/**
 * Gets a new volume for the currently playing Ambient track.
 *
 * @return       the volume of the currently playing Ambient
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native int GetAmbientVolume();

/**
 * Sets a new volume for the currently playing Ambient track.
 *
 * @param       volume the new volume
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native void SetAmbientVolume(int volume);




/**
 * Plays a voice.
 *
 * @param       sourceGuid the guid of the thing talking
 * @param       voiceId the id of the voice
 * @param       volume the volume of the voice to play
 * @return      the duration of the sound in seconds
 *
 * <!-- 07/03/99 [YB] Original Programmer -->
*/
	public static native float PlayVoice(int sourceGuid, String voiceID, int volume);


	// ------------------------------------------------------------------------

	public CodexSound(String name, int volume, int pan, int flags, int clientGuid)
	{
		guid = CreateLocal(GetGUID(), name, volume, pan, flags, clientGuid);
	}

	public CodexSound(String name, float minDist, float maxDist, int volume, int pan, int flags, int thingGuid)
	{
		guid = CreateAtThing(GetGUID(), name, minDist, maxDist, volume, pan, flags, thingGuid);
	}

	public CodexSound(String name, float minDist, float maxDist, int volume, int pan, int flags, float[] position)
	{
		guid = CreateAtPos(GetGUID(), name, minDist, maxDist, volume, pan, flags, position);
	}

	public CodexSound(String name, float minDist, float maxDist, int volume, int pan, int flags, float x, float y, float z)
	{
		float[] pos = new float[3];
		pos[0] = x; pos[1] = y; pos[2] = z;

		guid = CreateAtPos(GetGUID(), name, minDist, maxDist, volume, pan, flags, pos);
	}

	public static void PushMusic(String name, int volume)
	{
		aMusicName = GetMusicName();
		musicVolume = GetMusicVolume();

		// fade out code later?
		StopMusic();
		StartMusic(name, volume);
	}

	public static void PopMusic()
	{
		// fade out code later?
		StopMusic();

		StartMusic(aMusicName, musicVolume);
	}

}
