/**
 * Handles the link from Codex to an internal game sector. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexSector extends Codex
{

/**
 * Finds the sector at the passed coordinates, using the hint guid to potentially speed up search.
 *
 * @param       position the coordinates to find a sector from
 * @param       hintSectorGuid the guid of a hint sector that could speed up search, or 0 
 * @return      the guid of the sector at the passed coordinates, or 0 if out of the world
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public static native int FindAtPos(float[] position, int hintSectorGuid);

/**
 * Gets the sector flags.
 *               
 * @return      the sector flags
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int GetFlags();

/**
 * Sets flags in the sector.
 *               
 * @param       flags one or more flags to set
 * @return      the old sector flags
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int SetFlags(int flags);

/**
 * Clears flags in the sector.
 *               
 * @param       flags one or more flags to clear
 * @return      the old sector flags
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int ClearFlags(int flags);

/**
 * Gets the number of vertices in the sector
 *               
 * @return      the number of vertices in the sector, -1 in case of error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int GetNumVertices();

/**
 * Gets the number of surfaces in the sector
 *               
 * @return      the number of surfaces in the sector, -1 in case of error
 * <!-- 01/10/99 [YB] Original Programmer -->
*/
	public native int GetNumSurfaces();

/**
 * Gets the number of portals in the sector
 *               
 * @return      the number of portals in the sector, -1 in case of error
 * <!-- 01/10/99 [YB] Original Programmer -->
*/
	public native int GetNumPortals();

/**
 * Gets the number of regions in the sector
 *               
 * @return      the number of regions in the sector, -1 in case of error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int GetNumRegions();

/**
 * Gets the GUID of the first thing in this sector
 *               
 * @return      the GUID of the first thing in this sector, 0 if no things, -1 in case of error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int GetFirstThing();

/**
 * Enables (opens) all portals in this sector.
 *               
 * @return      success or failure
 * <!-- 02/09/99 [YB] Original Programmer -->
*/
	public native boolean EnablePortals();

/**
 * Disable (close) all portals in this sector.
 *               
 * @return      success or failure
 * <!-- 02/09/99 [YB] Original Programmer -->
*/
	public native boolean DisablePortals();


	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexSector(int x) 
	{
		if(x < 0) 
			guid = 0;
		else
			guid = x;
	}

	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

/**
 * Finds the sector at the passed coordinates.
 *
 * @param       position the coordinates to find a sector from
 * @return      the guid of the sector at the passed coordinates, or 0 if out of the world
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public int FindAtPos(float[] position)
	{
		return(FindAtPos(position, 0));
	}

/**
 * Finds the sector at the passed coordinates, using the hint guid to potentially speed up search.
 *
 * @param       x the x coordinate to find a sector from
 * @param       y the y coordinate to find a sector from
 * @param       z the z coordinate to find a sector from
 * @param       hintSectorGuid the guid of a hint sector that could speed up search, or 0 
 * @return      the guid of the sector at the passed coordinates, or 0 if out of the world
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public int FindAtPos(float x, float y, float z, int hintSectorGuid)
	{
		float[] pos = new float[3];

		pos[0] = x;
		pos[1] = y;
		pos[2] = z;

		return(FindAtPos(pos, hintSectorGuid));
	}

/**
 * Finds the sector at the passed coordinates.
 *
 * @param       x the x coordinate to find a sector from
 * @param       y the y coordinate to find a sector from
 * @param       z the z coordinate to find a sector from
 * @return      the guid of the sector at the passed coordinates, or 0 if out of the world
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public int FindAtPos(float x, float y, float z)
	{
		float[] pos = new float[3];

		pos[0] = x;
		pos[1] = y;
		pos[2] = z;

		return(FindAtPos(pos, 0));
	}

/**
 * Finds the sector at the passed CodexVector, using the hint guid to potentially speed up search.
 *
 * @param       vec the CodexVector specifying coordinates to find a sector from
 * @param       hintSectorGuid the guid of a hint sector that could speed up search, or 0 
 * @return      the guid of the sector at the passed coordinates, or 0 if out of the world
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public int FindAtPos(CodexVector vec, int hintSectorGuid)
	{
		return(FindAtPos(vec.AsArray(), hintSectorGuid));
	}

/**
 * Finds the sector at the passed CodexVector.
 *
 * @param       vec the CodexVector specifying coordinates to find a sector from
 * @return      the guid of the sector at the passed coordinates, or 0 if out of the world
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public int FindAtPos(CodexVector vec)
	{
		return(FindAtPos(vec.AsArray(), 0));
	}



}

