/**
 * DoubleDoorPivotSwitchPivot script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class DoubleDoorPivotSwitchPivot extends Codex
{
	private float		_duration = (float)3.0;

	private	CodexThing	_switch;
	private CodexThing	_door1;
	private CodexThing	_door2;

	private int			_frameNum = 1;
	
	private boolean		bOpen = false;
	private boolean		bActive = false;

	public static String _params[] = {"Duration;3.0", "First half door", "Second half door"};

	public DoubleDoorPivotSwitchPivot(float duration, CodexThing door1, CodexThing door2)
	{
		_switch = new CodexThing(GetClassThing());

		_duration = duration;

		_door1 = new CodexThing(door1.GetGUID());
		_door2 = new CodexThing(door2.GetGUID());

		CaptureThing(_door1.GetGUID());
		CaptureThing(_door2.GetGUID());

		if(_door1.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door1.SetDescriptionID("GEN_DOOR");
		if(_door2.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door2.SetDescriptionID("GEN_DOOR");
		if(_switch.GetDescriptionID().equalsIgnoreCase("PROP"))
			_switch.SetDescriptionID("GEN_SWITCH");
	}

	public void beginscene(int clientGuid, int captureID)
	{
		//if(!bOpen)
		//{
		//	_door1.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door2.SetThingFlags(THING_FLAG_VISBLOCK);
		//}

		_door1.SetThingFlags(THING_FLAG_VISBLOCK);
		_door2.SetThingFlags(THING_FLAG_VISBLOCK);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(guid != _switch.GetGUID())
		{
			// sound indicating the door is opened somewhere else
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, clickerGuid);

			// display "opened elsewhere" message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_OPENELSEWHERE");

			return;
		}

		bActive = true;

		if(!bOpen)
		{	
			_switch.RotatePivot(_frameNum, _duration);
			_door1.RotatePivot(_frameNum, _duration);
			_door2.RotatePivot(_frameNum, _duration);
			bOpen = true;

			//_door1.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door2.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			_switch.RotatePivot(_frameNum, -_duration);
			_door1.RotatePivot(_frameNum, -_duration);
			_door2.RotatePivot(_frameNum, -_duration);
			bOpen = false;

			//_door1.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door2.SetThingFlags(THING_FLAG_VISBLOCK);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		bActive = false;
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bOpen);
	}
 
	public void restore(int flags)
	{
		bOpen = CodexSequence.RestoreBoolean();
	}
}
