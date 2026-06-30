/**
 * Handles the link from Codex to an internal game item. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexItem extends CodexThing
{

/**
 * Gets the item flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_ITEM
 *               
 * @return      the item flags of the thing
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		GetItemFlags();

/**
 * Sets the item flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_ITEM
 *
 * @param       flags one or more flags to set
 * @return      the old item flags of the thing, or -1 in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		SetItemFlags(int flags);

/**
 * Clears the item flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_ITEM
 *
 * @param       flags one or more flags to clear
 * @return      the old item flags of the thing, or -1 in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		ClearItemFlags(int flags);

/**
 * Gets the next item in the same inventory
 *               
 * @return      the guid of the next inventory item, or 0 if no more or in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		GetNextInventoryItem();

/**
 * Gets the previous item in the same inventory
 *               
 * @return      the guid of the previous inventory item, or 0 if no more or in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		GetPrevInventoryItem();

/**
 * Removes this item from the inventory it is currently in.
 *               
 * @return      the guid of the player who owned the inventory item, or 0 if not owned or in error
 * <!-- 02/12/99 [YB] Original Programmer -->
*/
	public native int		RemoveItemFromInventory();

	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexItem(int x)
	{
		super(x);
	}

}