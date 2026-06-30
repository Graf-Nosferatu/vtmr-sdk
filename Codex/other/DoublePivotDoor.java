/**
 * Double pivot door script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class DoublePivotDoor extends Codex
{
	private	CodexThing	_door;
	private int			_frameNum = 1;
	private float		_duration = (float)3.0;
	private CodexThing	_door2;
	
	private boolean		bOpen = false;
	private boolean		bActive = false;

	public static String _params[] = {"Rotate around frame;1", "Duration;3.0", "Second half door"};

	public DoublePivotDoor(int frameNum, float duration, CodexThing door2)
	{
		_door = new CodexThing(GetClassThing());

		_frameNum = frameNum;
		_duration = duration;

		_door2 = new CodexThing(door2.GetGUID());
		CaptureThing(_door2.GetGUID());

		if(_door.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door.SetDescriptionID("GEN_DOOR");
		if(_door2.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door2.SetDescriptionID("GEN_DOOR");
	}

	public void beginscene(int clientGuid, int captureID)
	{
		//if(!bOpen)
		//{
		//	_door.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door2.SetThingFlags(THING_FLAG_VISBLOCK);
		//}

		_door.SetThingFlags(THING_FLAG_VISBLOCK);
		_door2.SetThingFlags(THING_FLAG_VISBLOCK);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(bActive)
			return;

		bActive = true;

		if(!bOpen)
		{
			_door.RotatePivot(_frameNum, _duration);
			_door2.RotatePivot(_frameNum, _duration);
			bOpen = true;

			//_door.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door2.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			_door.RotatePivot(_frameNum, -_duration);
			_door2.RotatePivot(_frameNum, -_duration);
			bOpen = false;

			//_door.SetThingFlags(THING_FLAG_VISBLOCK);
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
