/**
 * Click exit door script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ClickExitDoor extends Codex
{
	private CodexThing	exitThing;
	private int			_exitNumber;

	public static String _params[] = {"Exit number;0"};

	public ClickExitDoor(int exitNumber)
	{
		_exitNumber = exitNumber;
		exitThing = new CodexThing(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// check to see if the exit is open
		if(!CodexSequence.IsExitClosed(CodexSequence.GetLocationName(CodexSequence.GetCurrentLocation()), _exitNumber))
		{
			exitThing.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);	
		}
		else
		{
			exitThing.SetThingFlags(THING_FLAG_NOHIGHLIGHT);	
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		// check to see if the exit is open
		if(!CodexSequence.IsExitClosed(CodexSequence.GetLocationName(CodexSequence.GetCurrentLocation()), _exitNumber))
		{
			// if it's open, take it
			CodexSequence.TakeExit(_exitNumber, clickerGuid);
		}
		else
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, GetClassThing());

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");
		}
	}
}
