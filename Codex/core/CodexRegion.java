/**
 * Handles the link from Codex to an internal game region. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexRegion extends CodexThing
{

/**
 * Gets the prop flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_REGION
 *               
 * @return      the prop flags of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetRegionFlags();

/**
 * Sets the prop flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_REGION
 *
 * @param       flags one or more flags to set
 * @return      the old prop flags of the thing, or -1 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		SetRegionFlags(int flags);

/**
 * Clears the prop flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_REGION
 *
 * @param       flags one or more flags to clear
 * @return      the old prop flags of the thing, or -1 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		ClearRegionFlags(int flags);

	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexRegion(int x)
	{
		super(x);
	}

}