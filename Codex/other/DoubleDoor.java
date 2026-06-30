/**
 * Double door script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class DoubleDoor extends Codex
{
	private	CodexThing	_door;
	private float		_speed = (float)10.0;
	private CodexThing	_door2;

	private boolean		bOpen = false;
	private boolean		bActive = false;

	public static String _params[] = {"Movement speed;10.0", "Second half door"};

	public DoubleDoor(float speed, CodexThing door2)
	{
		_door = new CodexThing(GetClassThing());

		_speed = speed;

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
			_door.MoveToFrame(1, _speed);
			_door2.MoveToFrame(1, _speed);
			bOpen = true;

			//_door.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door2.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			_door.MoveToFrame(0, _speed);
			_door2.MoveToFrame(0, _speed);
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
