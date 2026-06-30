/**
 * Click exit script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ClickExit extends Codex
{
	private CodexThing	exitThing;
	private int			_exitNumber;

	private String		_locationName = "";

	private String		originalDescID = "";
	private boolean     bGotID = false;

	public static String _params[] = {"Exit number;0"};

	public ClickExit(int exitNumber)
	{
		exitThing = new CodexThing(GetClassThing());

		_exitNumber = exitNumber;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		exitThing.SetThingFlags(THING_FLAG_NOSTDELETE);

		CodexThing playerThing = new CodexThing(clientGuid);
		int locationNum = playerThing.GetLocationNum();
		_locationName = CodexSequence.GetLocationName(locationNum);

		if(!bGotID)
		{
			originalDescID = exitThing.GetDescriptionID();

			bGotID = true;
		}

		// check to see if the exit is valid - if not, set it to not highlight
		// if it is, make sure it's highlightable
		if(!CodexSequence.IsExitValid(_locationName, _exitNumber))
		{
			exitThing.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else
		{
			exitThing.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		// check to see if the exit is closed
		if(CodexSequence.IsExitClosed(_locationName, _exitNumber))
		{
			exitThing.SetDescriptionID(originalDescID + "_L");	
		}
		else
		{
			exitThing.SetDescriptionID(originalDescID);
		}
	}

	public void exitstatechanged()
	{
		// if we haven't gotten the original id, just get out, because none of this
		// will be valid
		if(!bGotID)
			return;

		// somewhere, sometime an exit has either been opened or closed so we re-check 
		// to see if this exit is closed and set the descriptionID accordingly
		if(CodexSequence.IsExitClosed(_locationName, _exitNumber))
		{
			exitThing.SetDescriptionID(originalDescID + "_L");	
		}
		else
		{
			exitThing.SetDescriptionID(originalDescID);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		// if we haven't gotten the original id, just get out, because none of this
		// will be valid
		if(!bGotID)
			return;

		// check to see if the exit is open
		if(!CodexSequence.IsExitClosed(_locationName, _exitNumber))
		{
			// if it's open, take it
			CodexSequence.TakeExit(_exitNumber, clickerGuid);
		}
		else
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, clickerGuid);

			// display locked message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_DOORLOCKED");
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bGotID);
		CodexSequence.SaveString(originalDescID);
		CodexSequence.SaveString(_locationName);
	}

	public void restore(int flags)
	{
		bGotID = CodexSequence.RestoreBoolean();
		originalDescID = CodexSequence.RestoreString();
		_locationName = CodexSequence.RestoreString();
	}
	
	public void stcommand(String blah, int id, float p0, float p1, float p2, float p3)
	{
		CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "Param: " + blah, 0xFFFFFF);
	}
	
	public void stcommand2(String blah)
	{
		CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "Param: " + blah, 0xFFFFFF);	
	}
}
