/**
 * Handles the link from Codex to an internal game prop. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexProp extends CodexThing
{

/**
 * Gets the prop flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROP
 *               
 * @return      the prop flags of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetPropFlags();

/**
 * Sets the prop flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROP
 *
 * @param       flags one or more flags to set
 * @return      the old prop flags of the thing, or -1 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		SetPropFlags(int flags);

/**
 * Clears the prop flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROP
 *
 * @param       flags one or more flags to clear
 * @return      the old prop flags of the thing, or -1 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		ClearPropFlags(int flags);

	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexProp(int x)
	{
		super(x);
	}

}