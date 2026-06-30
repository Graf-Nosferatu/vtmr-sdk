/**
 * Handles the link from Codex to an internal game world. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexWorld extends Codex
{

/**
 * Gets the filename for this world.
 *               
 * @return      the filename for this world
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native String GetName();

/**
 * Gets the world flags.
 *               
 * @return      the world flags
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int GetFlags();

/**
 * Sets flags in the world.
 *               
 * @param       flags one or more flags to set
 * @return      the old world flags
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int SetFlags(int flags);

/**
 * Clears flags in the world.
 *               
 * @param       flags one or more flags to clear
 * @return      the old world flags
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int ClearFlags(int flags);

/**
 * Gets the number of sectors for this world.
 *               
 * @return      the number of sectors for this world
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int GetNumSectors();

/**
 * Gets the GUID of the sector number sectorNum.
 *
 * @param       sectorNum the index in the world's array of sectors
 * @return      the GUID of the sector number sectorNum.
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int GetSector(int sectorNum);

/**
 * Gets the number of materials for this world.
 *               
 * @return      the number of materials for this world
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int GetNumMaterials();

/**
 * Gets the GUID of the first thing in this world.
 *               
 * @return      the GUID of the first thing in this world, 0 if no things, -1 in case of error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int GetFirstThing();


/**
 * Creates a thing in this world.
 *               
 * @param       templateName the template to base the new thing on
 * @param       origin the position to create the new thing at
 * @param       orient the orientation of the new thing
 * @return      the guid of the new thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int CreateThing(String templateName, float origin[], float orient[]);

/**
 * Creates a treasure in the world.
 *               
 * @param       treasureClass the treasureClass to base the new thing on
 * @param       origin the position to create the new thing at
 * @param       orient the orientation of the new thing
 * @param       bForceSuccess disregards the % chance, and always return a treasure of the first class item
 * @return      the guid of the new thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int CreateTreasure(int treasureClass, float origin[], float orient[], boolean bForceSuccess);


/**
 * Gets the last number of particles rendered.
 *               
 * @return      the last number of particles rendered
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public native int GetLastEmitterRenderCount();

	//public native int CreateThing(String templateName, CodexThing placeholder);

	// ------------------------------------------------------------------------

	public CodexWorld(int x) 
	{
		if(x == WORLD_CURRENT)
			guid = WORLD_CURRENT;
		else
			if(x < 0) 
				guid = 0;
			else
				guid = x;
	}

}
