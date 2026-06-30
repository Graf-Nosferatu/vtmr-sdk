/**
 * Handles the link from Codex to the game console. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexConsole extends Codex
{

	private static boolean bEnableDebugMessages = true;
	private static boolean bEnableErrorMessages = true;
	private static boolean bEnableExceptionMessages = true;

/**
 * Clears the game console
 *
 * <!-- 01/10/99 [YB] Original Programmer -->
*/
	public native static void Clear(int clientGuid, int flags);

/**
 * Prints a string to the game console
 *
 * <!-- 01/10/99 [YB] Original Programmer -->
*/
	public native static void Print(int clientGuid, int flags, String message, int color);

	public static void Print(int clientGuid, int flags, String message)
	{
		Print(clientGuid, flags, message, 0xFFFFFF);
	}

/**
 * Prints a localized string to the game console
 *
 * <!-- 03/10/99 [YB] Original Programmer -->
*/
	public native static void PrintNLS(int clientGuid, int flags, String messageID, int color);

	public static void PrintNLS(int clientGuid, int flags, String messageID)
	{
		PrintNLS(clientGuid, flags, messageID, 0xFFFFFF);
	}

/**
 * Prints a formatted NLS string to the game console
 *
 * <!-- 05/22/00 [YB] Original Programmer -->
*/
	public native static void PrintFormatNLS(int clientGuid, int flags, String messageID, String format, int color);

	public static void PrintFormatNLS(int clientGuid, int flags, String messageID, String format)
	{
		PrintFormatNLS(clientGuid, flags, messageID, format, 0xFFFFFF);
	}


/**
 * Executes the passed console command
 *
 * @param		aCommand a console command to execute
 * @return      success or failure
 * <!-- 01/22/99 [YB] Original Programmer -->
*/
	public static native boolean Execute(String aCommand);


/**
 * Prints a debug string to the game console
 *
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public static void PrintDebug(int clientGuid, int flags, String message)
	{
		if(bEnableDebugMessages)
			Print(clientGuid, flags, "<DEBUG>: " + message, 0x00FF00);
	}

/**
 * Prints an error string to the game console
 *
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public static void PrintError(int clientGuid, int flags, String message)
	{
		if(bEnableErrorMessages)
			Print(clientGuid, flags, "<ERROR>: " + message, 0xFF0000);
	}

/**
 * Prints an exception string to the game console
 *
 * <!-- 02/12/99 [YB] Original Programmer -->
*/
	public static void PrintException(int clientGuid, int flags, String message)
	{
		if(bEnableErrorMessages)
			Print(clientGuid, flags, "<EXCPT>: " + message, 0xFF0000);
	}


/**
 * Sets the state of debug messages output.
 *
 * @param		bNewState the debug messages output new state
 * @return		the old debug messages output state
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public static boolean EnableDebugMessages(boolean bNewState)
	{
		boolean bOldState = bEnableDebugMessages;
		bEnableDebugMessages = bNewState;
		return(bOldState);
	}

/**
 * Sets the state of error messages output.
 *
 * @param		bNewState the error messages output new state
 * @return		the old error messages output state
 * <!-- 01/18/99 [YB] Original Programmer -->
*/
	public static boolean EnableErrorMessages(boolean bNewState)
	{
		boolean bOldState = bEnableErrorMessages;
		bEnableErrorMessages = bNewState;
		return(bOldState);
	}

/**
 * Sets the state of exception messages output.
 *
 * @param		bNewState the exception messages output new state
 * @return		the old exception messages output state
 * <!-- 02/12/99 [YB] Original Programmer -->
*/
	public static boolean EnableExceptMessages(boolean bNewState)
	{
		boolean bOldState = bEnableExceptionMessages;
		bEnableExceptionMessages = bNewState;
		return(bOldState);
	}


	public static void PrintDebug(String message)
	{
		PrintDebug(0, 0, message);
	}

	public static void PrintError(String message)
	{
		PrintError(0, 0, message);
	}

	public static void PrintException(String message)
	{
		PrintException(0, 0, message);
	}


}

