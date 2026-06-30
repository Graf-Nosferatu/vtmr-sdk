/**
 * Elevator doors script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class DoorElevator extends Codex
{
	private float		_speed = (float)50.0;

	private	CodexThing	_switch;
	private CodexThing	_door1;
	private CodexThing	_door2;

	private int			_frameNum = 1;
	
	private boolean		bOpen = false;
	private boolean		bActive = false;

	public static String _params[] = {"Speed;50.0", "First half door", "Second half door"};

	public DoorElevator(float speed, CodexThing door1, CodexThing door2)
	{
		_switch = new CodexThing(GetClassThing());

		_speed = speed;

		_door1 = new CodexThing(door1.GetGUID());
		_door2 = new CodexThing(door2.GetGUID());

		if(_door1.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door1.SetDescriptionID("GEN_ELEVATORDOOR");

		if(_door2.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door2.SetDescriptionID("GEN_ELEVATORDOOR");

		CaptureThing(_door1.GetGUID());
		CaptureThing(_door2.GetGUID());
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
			return;

		bActive = true;

		if(!bOpen)
		{
			new CodexSound("elevator_open.WAV", 300, 600, 50, 0, 0, _door1.GetGUID());

			_door1.MoveToFrame(_frameNum, _speed);
			_door2.MoveToFrame(_frameNum, _speed);
			bOpen = true;

			//_door1.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door2.ClearThingFlags(THING_FLAG_VISBLOCK);

			_frameNum = 0;
		}
		else
		{
			new CodexSound("elevator_close.WAV", 300, 600, 50, 0, 0, _door1.GetGUID());

			//_door1.MoveToFrame(_frameNum, _speed);
			//_door2.MoveToFrame(_frameNum, _speed);
			bOpen = false;

			_door1.SetThingFlags(THING_FLAG_VISBLOCK);
			_door2.SetThingFlags(THING_FLAG_VISBLOCK);

			_frameNum = 1;
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
