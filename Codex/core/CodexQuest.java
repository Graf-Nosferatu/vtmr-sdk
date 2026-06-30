/**
 * Handles the link from Codex to an internal game quest. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexQuest extends Codex
{

/**
 * Loads the Quest by name (this is a static method).
 *
 * @param       name the filename of the quest
 * @return      the guid of the quest, or -1 in error
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public static native int Load(String name);


/**
 * Destroys the Quest
 *
 * @return      success or failure
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native boolean Destroy();


/**
 * Gets the quest description (long or short).
 *
 * @param       bLong true for long description, false for short description
 * @return      the quest description
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native String GetDescription(boolean bLong);

/**
 * Gets the quest flags.
 *               
 * @return      the quest flags
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int GetFlags();

/**
 * Sets flags in the quest.
 *               
 * @param       flags one or more flags to set
 * @return      the old quest flags
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int SetFlags(int flags);

/**
 * Clears flags in the quest.
 *               
 * @param       flags one or more flags to clear
 * @return      the old quest flags
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int ClearFlags(int flags);


/**
 * Gets the quest number of items.
 *               
 * @return      the quest number of items
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int GetNumItems();

/**
 * Gets the quest item description (long or short).
 *
 * @param       itemNum the index of the item
 * @param       bLong true for long description, false for short description
 * @return      the quest item description
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native String GetItemDescription(int itemNum, boolean bLong);


/**
 * Gets the quest item flags.
 *
 * @param       itemNum the index of the item
 * @return      the quest item flags
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int GetItemFlags(int itemNum);

/**
 * Sets flags in the quest item.
 *
 * @param       itemNum the index of the item
 * @param       flags one or more flags to set
 * @return      the old quest item flags
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int SetItemFlags(int itemNum, int flags);

/**
 * Clears flags in the quest item.
 *
 * @param       itemNum the index of the item
 * @param       flags one or more flags to clear
 * @return      the old quest item flags
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int ClearItemFlags(int itemNum, int flags);

/**
 * Gets the quest item number of steps. Note that if the number of steps is reduced the current step could be reduced to keep it valid too.
 *
 * @param       itemNum the index of the item
 * @return      the quest item number of steps
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int GetItemNumSteps(int itemNum);

/**
 * Sets the quest item number of steps.
 *
 * @param       itemNum the index of the item
 * @param       numSteps the number of steps to set
 * @return      the quest item old number of steps
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int SetItemNumSteps(int itemNum, int numSteps);

/**
 * Gets the quest item current step.
 *
 * @param       itemNum the index of the item
 * @return      the quest item current step
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int GetItemCurStep(int itemNum);

/**
 * Sets the quest item current step.
 *
 * @param       itemNum the index of the item
 * @param       curStep the current step to set
 * @return      the quest item old current step
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public native int SetItemCurStep(int itemNum, int curStep);

	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexQuest(int x)
	{
		if(x < 0) 
			guid = -1;
		else
			guid = x;
	}

	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

/**
 * Returns the completion status of a quest.
 *               
 * @return      complete or not
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public boolean IsComplete()
	{
		int flags = GetFlags();

		// handle a possible error
		if(flags == -1)
			return(false);

		return((flags & QUEST_FLAG_COMPLETE) != 0);
	}


/**
 * Returns true if all the items are complete.
 *               
 * @return      complete or not
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public boolean AreAllItemsComplete()
	{
		int numItems = GetNumItems();

		for(int i = 0; i < numItems; i++)
		{
			if(!IsItemComplete(i))
				return(false);
		}
		
		return(true);
	}


/**
 * Marks the quest completed
 *               
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public void Complete()
	{
		SetFlags(QUEST_FLAG_COMPLETE);
	}


/**
 * Returns the completion status of a quest item.
 *
 * @param       itemNum the index of the item
 * @return      complete or not
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public boolean IsItemComplete(int itemNum)
	{
		int flags = GetItemFlags(itemNum);

		// handle a possible error
		if(flags == -1)
			return(false);

		return((flags & QUESTITEM_FLAG_COMPLETE) != 0);
	}


/**
 * Returns the hidden status of a quest item.
 *
 * @param       itemNum the index of the item
 * @return      hidden or not
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public boolean IsItemHidden(int itemNum)
	{
		int flags = GetItemFlags(itemNum);

		// handle a possible error
		if(flags == -1)
			return(false);

		return((flags & QUESTITEM_FLAG_HIDDEN) != 0);
	}


/**
 * Marks the quest item completed
 *
 * @param       itemNum the index of the item
 * <!-- 06/16/99 [YB] Original Programmer -->
*/
	public void CompleteItem(int itemNum)
	{
		SetItemFlags(itemNum, QUESTITEM_FLAG_COMPLETE);
	}



}

