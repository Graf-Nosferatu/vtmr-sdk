/**
 * Handles the link from Codex to an internal game player. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexPlayer extends CodexActor
{

/**
 * Gets the GUID of the current player
 *               
 * @return      the GUID of the current player, or 0 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public static native int	GetCurrentPlayer();

/**
 * Sets the current player
 * 
 * @return      succes or failure
 * <!-- 10/08/99 [YB] Original Programmer -->
*/
	public native boolean		SetCurrentPlayer();

/**
 * Gets the name of the player
 *               
 * @return      the name of the player
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native String	GetPlayerName();

/**
 * Sets the name of the player
 *
 * @param       name the player name to set
 * @return      succes or failure
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native boolean	SetPlayerName(String name);


/**
 * Award this experience amount to the player
 *
 * @param       experienceAmount	the experience to give the player
 * @return      success or failure
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public native boolean AwardPlayerExperience(int experienceAmount);


/**
 * Get the level of this player in this discipline group
 *
 * @param       groupName the name of the discipline group
 * @return      the level, or -1 in error
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public native int	GetPlayerGroupLevel(String groupName);

/**
 * Sets the level of this player in this discipline group
 *
 * @param       groupName the name of the discipline group
 * @param       newLevel the new level to set
 * @return      the old level, or -1 in error
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public native int	SetPlayerGroupLevel(String groupName, int newLevel);


/**
 * Sets a head model on this player.
 *               
 * @param       modelName the name of the model to change to
 * @return      success or failure
 * <!-- 09/14/99 [YB] Original Programmer -->
*/
	public native boolean	SetPlayerHeadModel(String headModelName);


/**
 * Gets the number of players in the party
 *               
 * @return      the number of players in the party
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public static native int	GetNumPartyPlayers();

/**
 * Gets the guid of a player in the party
 *
 * @param       playerNum the player number in the party
 * @return      the guid of the requested player, or 0 in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public static native int	GetPartyPlayer(int playerNum);

/**
 * Adds the player to the party.
 *
 * @return      success or failure
 * <!-- 03/08/99 [YB] Original Programmer -->
*/
	public native boolean		AddToParty();

/**
 * Removes the player from the party.
 *
 * @return      success or failure
 * <!-- 09/12/99 [YB] Original Programmer -->
*/
	public native boolean		RemoveFromParty();

/**
 * Teleports the party to the specified entrance of the current location.
 *
 * @param       entranceNum the entrance number
 * @return      success or failure (at least one party memeber could NOT be teleported there, because of a bad entrance number, or not enough player starts for that entrance)
 * <!-- 03/08/99 [YB] Original Programmer -->
*/
	public static native boolean TeleportPartyToEntrance(int actorGuid, int entranceNum);


/**
 * Teleports the party to the specified conversation entrance of the current location.
 *
 * @param       entranceNum the entrance number
 * @return      success or failure (at least one party memeber could NOT be teleported there, because of a bad entrance number, or not enough player starts for that entrance)
 * <!-- 03/08/99 [YB] Original Programmer -->
*/
	public static native boolean TeleportPartyToConversation(int actorGuid, int entranceNum);

/**
 * Award this experience amount to every player currently in the party
 *
 * @param       experienceAmount	the experience to give each player
 * <!-- 09/08/99 [YB] Original Programmer -->
*/
	public static native void AwardPartyExperience(int experienceAmount);


/**
 * Gets the absolute maximum number of players in a MP game
 *               
 * @return      the max number of players in a MP game
 * <!-- 04/12/99 [YB] Original Programmer -->
*/
	public static native int	GetMaxMPPlayers();


/**
 * Gets the number of players in this MP game
 *               
 * @return      the number of players in this MP game
 * <!-- 04/12/99 [YB] Original Programmer -->
*/
	public static native int	GetNumMPPlayers();


/**
 * Gets the guid of player #n in this MP game 
 * <BR> WARNING!!! Check for a 0 result!!!
 * <BR> 1) This is a sparse array 
 * <BR> 2) the player might be in the lobby, or getting disconnected, or transitionning levels (all 0 returns!!!)
 *               
 * @return      guid of player #n in this MP game
 * <!-- 04/12/99 [YB] Original Programmer -->
*/
	public static native int	GetMPPlayer(int playerNum);


/**
 * Gets the activation state for a discipline of this player.
 * <BR>Applies only to things with ThingType == THING_TYPE_PLAYER that have disciplines...
 * 
 * @return      the activation state for a discipline of this Player.
 * <!-- 01/19/99 [YB] Original Programmer -->
*/
	public native boolean	GetPlayerDisciplineState(String disciplineName);

/**
 * Sets the activation state for a discipline of this player.
 * <BR>Applies only to things with ThingType == THING_TYPE_PLAYER that have disciplines...
 * 
 * @param       newState the new activation state for the discipline
 * @return      the old activation state for a discipline of this Player.
 * <!-- 01/19/99 [YB] Original Programmer -->
*/
	public native boolean	SetPlayerDisciplineState(String disciplineName, boolean newState);


/**
 * Gets the reload time for the current discipline of this player at the passed level.
 * <BR>Applies only to things with ThingType == THING_TYPE_PLAYER.
 * 
 * @param       level the discipline level to get the reload time for
 * @return      the reload time for the current discipline of this Player at the passed level, or -1 in case of any error.
 * <!-- 01/19/99 [YB] Original Programmer -->
*/
	public native int		GetPlayerDisciplineReloadTime(int level);


	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexPlayer(int x)
	{
		super(x);
	}

	public CodexPlayer()
	{
		super(0);
		guid = GetCurrentPlayer();
	}


}