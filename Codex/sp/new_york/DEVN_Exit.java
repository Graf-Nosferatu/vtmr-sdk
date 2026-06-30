/**
 * dev/null Exit script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class DEVN_Exit extends Codex
{
	private CodexThing			_WindowExit;
	
	private int					_exitNumber;
	private float				_speed;

	private String		_locationName = "";

	private String		originalDescID = "";
	private boolean     bGotID = false;

	private int			_exiterGuid;

	public static String _params[] = {"Window exit", "Exit number;0", "Movement speed;150.0"};

	public DEVN_Exit(CodexThing WindowExit, int exitNumber, float speed)
	{
		_WindowExit = new CodexThing(WindowExit.GetGUID());

		_exitNumber = exitNumber;
		_speed = speed;

		CaptureThing(_WindowExit.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// reset window exit
		_WindowExit.MoveToFrame(0, 1000);

		_WindowExit.SetThingFlags(THING_FLAG_NOSTDELETE);

		CodexThing playerThing = new CodexThing(clientGuid);
		int locationNum = playerThing.GetLocationNum();
		_locationName = CodexSequence.GetLocationName(locationNum);

		if(!bGotID)
		{
			originalDescID = _WindowExit.GetDescriptionID();

			bGotID = true;
		}

		// check to see if the exit is valid - if not, set it to not highlight
		// if it is, make sure it's highlightable
		if(!CodexSequence.IsExitValid(_locationName, _exitNumber))
		{
			_WindowExit.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else
		{
			_WindowExit.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		// check to see if the exit is closed
		if(CodexSequence.IsExitClosed(_locationName, _exitNumber))
		{
			_WindowExit.SetDescriptionID(originalDescID + "_L");	
		}
		else
		{
			_WindowExit.SetDescriptionID(originalDescID);
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
			_WindowExit.SetDescriptionID(originalDescID + "_L");	
		}
		else
		{
			_WindowExit.SetDescriptionID(originalDescID);
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
			_exiterGuid = clickerGuid;

			new CodexSound("devNull_openWindow.WAV", 500, 1000, 100, 0, 0, _WindowExit.GetGUID());

			_WindowExit.MoveToFrame(1, _speed);
		}
		else
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, clickerGuid);

			// display locked message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_DOORLOCKED");
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _WindowExit.GetGUID() && frameNum == 1)
		{
			CodexSequence.TakeExit(_exitNumber, _exiterGuid);

			// reset window exit
			_WindowExit.MoveToFrame(0, 1000);
		}
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		// reset window exit
		_WindowExit.MoveToFrame(0, 1000);
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
}

