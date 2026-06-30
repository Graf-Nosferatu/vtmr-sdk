/**
 * Handles the link from Codex to game sequencing. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexSequence extends Codex
{
	// this is just FYI, changing this will NOT change the actual number of flags in the engine!!!
	// who would want more anyway ? :-)
	public static final int NUM_CHRONICLE_FLAGS					= 1024;


/**
 * Changes the chronicle (for chronicle chaining).
 * <BR>This function has no return value, because it is delayed to the end of the frame.
 * 
 * @param       chronicleName the filename of the chronicle
 * <!-- 09/03/99 [YB] Original Programmer -->
*/	
	public native static void ChangeChronicle(String chronicleName);

/**
 * Changes the scene in a location.
 * <BR>This function has no return value, because it is delayed to the end of the frame.
 * 
 * @param       location the name of the location
 * @param       scene the (file)name of the scene
 * <!-- 06/17/99 [YB] Original Programmer -->
*/	
	public native static void ChangeScene(String location, String scene);

/**
 * Changes an exit in the chronicle.
 * 
 * @param       location the name of the location
 * @param       exitNum the number of the exit
 * @param       toLocation the name of the destination location
 * @param       entranceNum the number of the entrance
 * <!-- 06/17/99 [YB] Original Programmer -->
*/	
	public native static boolean ChangeExit(String location, int exitNum, String toLocation, int entranceNum);

/**
 * Opens an exit in the chronicle.
 * 
 * @param       location the name of the location
 * @param       exitNum the number of the exit
 * <!-- 06/17/99 [YB] Original Programmer -->
*/	
	public native static void OpenExit(String location, int exitNum);

/**
 * Closes an exit in the chronicle.
 * 
 * @param       location the name of the location
 * @param       exitNum the number of the exit
 * <!-- 06/17/99 [YB] Original Programmer -->
*/	
	public native static void CloseExit(String location, int exitNum);


/**
 * Checks if an exit is closed.
 * 
 * @param       location the name of the location
 * @param       exitNum the number of the exit
 * @return      true if the exit is closed, false if it is open or in error
 * <!-- 01/24/00 [YB] Original Programmer -->
*/	
	public native static boolean IsExitClosed(String location, int exitNum);

/**
 * Checks if an exit is valid (linked).
 * 
 * @param       location the name of the location
 * @param       exitNum the number of the exit
 * @return      true if the exit is valid, false otherwise
 * <!-- 01/24/00 [YB] Original Programmer -->
*/	
	public native static boolean IsExitValid(String location, int exitNum);

	public native static int	 GetNumLocations();
	public native static int	 GetCurrentLocation();
	public native static String  GetLocationName(int locNum);

	public native static int	 GetLocationFlags(String location);
	public native static int	 SetLocationFlags(String location, int flags);
	public native static int	 ClearLocationFlags(String location, int flags);



	public native static int	 GetPortalLocation();
	public native static boolean SetPortalLocation(int location, int entrance);
	public native static int	 GetReviveLocation();
	public native static boolean SetReviveLocation(int location, int entrance);


/**
 * Jumps to a location/entrance.
 * <BR>This function has no return value, because it is delayed to the end of the frame.
 * 
 * @param       location the location name
 * @param       entranceNum the num of the entrance
 * <!-- 06/21/99 [YB] Original Programmer -->
*/	
	public native static void Jump(String location, int entranceNum);

/**
 * Jump an player to a location/entrance (NETWORK GAME only).
 * <BR>This function has no return value, because it is delayed to the end of the frame.
 * 
 * @param       location the location name
 * @param       entranceNum the num of the entrance
 * @param       actorGuid the guid of the player to jump
 * <!-- 30/01/00 [YB] Original Programmer -->
*/	
	public native static void PlayerJump(String location, int entranceNum, int actorGuid);

/**
 * Exits the level through a certain exit.
 * <BR>This function has no return value, because it is delayed to the end of the frame.
 * 
 * @param       exitNum the num of the exit
 * @param       actorGuid the guid of the actor taking the exit
 * <!-- 01/13/99 [YB] Original Programmer -->
*/	
	public native static void TakeExit(int exitNum, int actorGuid);


/**
 * Ends the game. Cutscenes and stuff should already have been played at this time...
 *
 * <!-- 01/30/00 [YB] Original Programmer -->
*/
	public native static void EndGame();


/**
 * Calls the save game UI.
 *
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public native static void SaveGame();

/**
 * Calls the advance UI.
 *
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public native static void Advance(int playerGuid);

/**
 * Sets up a complex advancement
 *
 * <!-- 11/15/99 [YB] Original Programmer -->
*/
	public native static void SetupAdvance(String location, String scene, int entranceNum);

/**
 * Calls the vault UI.
 *
 * @param       clientGuid the person opening his Vault
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public native static void Vault(int clientGuid);





/**
 * Gets the state of one of the chronicle info flags (a combination of CHRON_FLAG_ constants)
 * 
 * @return      the value of the flags
 * <!-- 03/09/00 [YB] Original Programmer -->
*/
	public native static int	GetChronicleInfoFlags();


	public native static int	GetNSSIndex();
	public native static void	SetNSSIndex(int index);

	
// CHRONICLE FLAG METHODS

/**
 * Gets the state of one of the chronicle flags.
 * 
 * @param       flagNum the flag number
 * @return      the state of the flag
 * <!-- 06/15/99 [YB] Original Programmer -->
*/	
	public native static boolean GetChronicleFlag(int flagNum);

/**
 * Sets one of the chronicle flags.
 * 
 * @param       flagNum the flag number
 * @return      the previous state of the flag
 * <!-- 06/15/99 [YB] Original Programmer -->
*/	
	public native static boolean SetChronicleFlag(int flagNum);

/**
 * Clears one of the chronicle flags.
 * 
 * @param       flagNum the flag number
 * @return      the previous state of the flag
 * <!-- 06/15/99 [YB] Original Programmer -->
*/	
	public native static boolean ClearChronicleFlag(int flagNum);

/**
 * Toggles one of the chronicle flags.
 * 
 * @param       flagNum the flag number
 * @return      the previous state of the flag
 * <!-- 06/15/99 [YB] Original Programmer -->
*/	
	public native static boolean ToggleChronicleFlag(int flagNum);

/**
 * Clears all the chronicle flags in one go (suggested use is: only at chronicle start).
 * 
 * <!-- 06/15/99 [YB] Original Programmer -->
*/	
	public native static void ClearAllChronicleFlags();


	// SAVE RESTORE METHODS

/**
 * Saves a boolean (use only in a save handler!).
 * 
 * @param       value the value to save
 * @return      success or failure
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static boolean SaveBoolean(boolean value);

/**
 * Restores a boolean (use only in a restore handler!).
 * 
 * @return      the value to restore
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static boolean RestoreBoolean();

/**
 * Saves a int (use only in a save handler!).
 * 
 * @param       value the value to save
 * @return      success or failure
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static boolean SaveInt(int value);

/**
 * Restores a int (use only in a restore handler!).
 * 
 * @return      the value to restore
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static int RestoreInt();

/**
 * Saves a float (use only in a save handler!).
 * 
 * @param       value the value to save
 * @return      success or failure
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static boolean SaveFloat(float value);

/**
 * Restores a float (use only in a restore handler!).
 * 
 * @return      the value to restore
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static float RestoreFloat();

/**
 * Saves a string (use only in a save handler!).
 * <BR> Note: only ASCII chars are saved, the string will lose UNICODE on restore.
 * 
 * @param       value the value to save
 * @return      the number of chars saved, or -1 in error
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static int SaveString(String value);

/**
 * Restores a string (use only in a restore handler!).
 * 
 * @return      the value to restore
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static String RestoreString();



/**
 * Creates a teleporter link...
 * 
 * @param       creatorGuid the owner of the teleport (must be an actor)
 * @param       inTemplate the template to use "in town"
 * @param       outTemplate	the template to use out of town (in the dungeon)
 * @param       expireTime how long the teleport should live
 * @param       flags flags
 * @return      success or failure
 * <!-- 05/21/99 [YB] Original Programmer -->
*/	
	public native static boolean CreateTeleporter(int creatorGuid, String inTemplate, String outTemplate, int expireTime, int flags);

	public native static boolean ActivateTeleporter(int teleporterGuid, int playerGuid);
}

