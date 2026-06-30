/**
 * Handles the link from Codex to an internal game camera. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexCamera extends Codex
{


/**
 * Sets the camera into cutscene mode
 *
 * @param		target0 the first main camera focus
 * @param		target1 the second main camera focus
 *
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void SetupCutscene(int clientGuid, int target0, int target1);


/**
 * Sets the next camera shot
 *
 * @param		shot the type of shot (CAM_SHOT_ constant)
 * @param		angle the angle of the shot (CAM_ANGLE_ constant)
 * @param		distance the distance of the shot (CAM_DIST_ constant)
 * @param		side the side of the target0 to target1 axe (0 or 1)
 * @param		lerpTime the time in seconds for the camera to lerp from the previous shot position to this one
 *
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void SetShot(int clientGuid, int shot, int angle, int distance, int side, float lerpTime);


/**
 * Returns the camera controls back to the game
 *
 * <!-- 07/08/99 [YB] Original Programmer -->
*/
	public static native void Release(int clientGuid);


/**
 * Plays a precomputed camera path...
 *
 * @param		scriptGuid the guid of the calling script (to know where to send the pathended event)
 * @param		pathName the filename containing the path
 * @param		speed the speed the path should be played at
 *
 * <!-- 07/08/99 [YB] Original Programmer -->
*/
	public static native boolean PlayPath(int clientGuid, int scriptGuid, String pathName, float speed);

	
/**
 * Starts a fade
 *
 * @param		clientGuid the guid of the calling script (to know who to affect)
 * @param		initial the initial intensity (0-255.0)
 * @param		target the target intensity
 * @param		fadeTime how long to go from initial to target
 *
 * <!-- 07/08/99 [YB] Original Programmer -->
*/	
	
	public static native int	AddFade(int clientGuid, float initial, float target, float fadeTime, boolean bBounce);


/**
 * Starts a Flash
 *
 * @param		clientGuid the guid of the calling script (to know who to affect)
 * @param		initial the initial flash intensity
 * @param		target the target flash intensity
 * @param		fadeTime how long to go from initial to target
 * @param		color for flash
 *
 * <!-- 07/08/99 [YB] Original Programmer -->
*/	
	
	public static native int	AddFlash(int clientGuid, float initial, float target, float fadeTime, int color, boolean bBounce);

/**
 * Starts a Filter
 *
 * @param		clientGuid the guid of the calling script (to know who to affect)
 * @param		flashTime how long to go from initial to target
 * @param		color for flash
 *
 * <!-- 07/08/99 [YB] Original Programmer -->
*/	
	
	public static native int	AddFilter(int clientGuid, float filterTime, int color);


/**
 * Clears all running Effects
 *
 * @param		clientGuid the guid of the calling script (to know who to affect)
 *
 * <!-- 07/08/99 [YB] Original Programmer -->
*/	
	
	public static native void	ClearAllEffects(int clientGuid);


// FIXME!! old, obsolete verbs, delete when all scripts changed to the new system
/**
 * Points the camera at a specific thing. Defaults for this are MS() and Med().
 *
 * @param		targetGuid the guid of the thing the camera must be pointed at
 *
 * <!-- 07/07/99 [YB] Original Programmer -->
*/
	public static native void SetupOneShot(int targetGuid);

/**
 * Set the camera shot type
 *
 * @param		shotType the type of shot (one of the CAMERA_SHOT_ constants)
 *
 * <!-- 07/07/99 [YB] Original Programmer -->
*/
	public static native void ShotType(int shotType);

/**
 * Set the camera shot angle
 *
 * @param		shotAngle the angle of the shot (one of the CAMERA_ANGLE_ constants)
 *
 * <!-- 07/07/99 [YB] Original Programmer -->
*/
	public static native void ShotAngle(int shotAngle);
// FIXME!! old, obsolete verbs, delete when all scripts changed to the new system


	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	// inherit it...
	public CodexCamera(int x)
	{
	}

	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

}

