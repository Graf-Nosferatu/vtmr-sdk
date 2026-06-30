/**
 * Handles the link from Codex to some internal game and platform data. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexSystem extends Codex
{
	// ------------------------------------------------------------------------
/**
 * Gets the current game time (in milliseconds).
 *               
 * @return      the current game time in milliseconds
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public static native int	 GetGameTime();

/**
 * Gets the current platform (operating system) date.
 *               
 * @return      the current operating system date in an array of floats (Year, Month, Day)
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public static native float[] GetPlatformDate();

/**
 * Gets the current platform (operating system) time.
 *               
 * @return      the current operating system time in an array of floats (Hour24, Minutes, Seconds.Milliseconds)
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public static native float[] GetPlatformTime();

/**
 * Gets the current time scale.
 *               
 * @return      the current time scale, 
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native float GetTimeScale();

/**
 * Sets the current time scale.
 * 
 * @param		newScale the new time scale to set, 1.0 being normal time flow
 * @return      the old time scale
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native float SetTimeScale(float newScale);

/**
 * Gets the current state of the inventory pane.
 * 
 * @return      the state of the inventory pane
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean GetInventoryPaneState();

/**
 * Sets the current state of the inventory pane.
 * 
 * @param		bShow true to show the inventory pane, false to hide it
 * @return      the old state of the inventory pane
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean SetInventoryPaneState(boolean bShow);

/**
 * Gets the current state of the character pane.
 * 
 * @return      the state of the character pane
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean GetCharacterPaneState();

/**
 * Sets the current state of the character pane.
 * 
 * @param		bShow true to show the character pane, false to hide it
 * @return      the old state of the character pane
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean SetCharacterPaneState(boolean bShow);

/**
 * Gets the current state of the status pane.
 * 
 * @return      the state of the status pane
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean GetStatusPaneState();

/**
 * Sets the current state of the status pane.
 * 
 * @param		bShow true to show the status pane, false to hide it
 * @return      the old state of the status pane
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean SetStatusPaneState(boolean bShow);

/**
 * Puts up a message box with the specified message
 * 
 * @param		message the message to display
 * @return      success or failure
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean MessageBox(String message);

	// ------------------------------------------------------------------------

/**
 * Sets the current time scale back to normal.
 * 
 * @return      the old time scale
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static float SetTimeScale()
	{
		return(SetTimeScale((float)1.0));
	}

}
