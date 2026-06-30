/**
 * Multi door script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class MultiDoor extends Codex
{
	private CodexThing	_door;
	private float		_speed = (float)10.0;
	private CodexThing	_door2 = null;
	private CodexThing	_door3 = null;
	private CodexThing	_door4 = null;
	private CodexThing	_door5 = null;
	private CodexThing	_door6 = null;
	private CodexThing	_door7 = null;
	private CodexThing	_door8 = null;

	private int         _OpenOnce = 0;

	private boolean		bOpen = false;
	private boolean		bActive = false;


	public static String _params[] = {"Movement speed;10.0", "Open Once;0", "Second part of door", "Third part of door", "Fourth part of door", "Fifth part of door", "Sixth part of door", "Seventh part of door", "Eighth part of door"};

	public MultiDoor(float speed, int OpenOnce, CodexThing door2, CodexThing door3, CodexThing door4, CodexThing door5, CodexThing door6, CodexThing door7, CodexThing door8)
	{
		_door = new CodexThing(GetClassThing());
		_door.SetDescriptionID("GEN_DOOR");

		_speed = speed;

        _OpenOnce = OpenOnce;

		if(door2 != null)
		{
			_door2 = new CodexThing(door2.GetGUID());
			if(_door2.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door2.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door2.GetGUID());
		}

		if(door3 != null)
		{
			_door3 = new CodexThing(door3.GetGUID());
			if(_door3.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door3.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door3.GetGUID());
		}

		if(door4 != null)
		{
			_door4 = new CodexThing(door4.GetGUID());
			if(_door4.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door4.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door4.GetGUID());
		}

		if(door5 != null)
		{
			_door5 = new CodexThing(door5.GetGUID());
			if(_door5.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door5.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door5.GetGUID());
		}

		if(door6 != null)
		{
			_door6 = new CodexThing(door6.GetGUID());
			if(_door6.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door6.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door6.GetGUID());
		}

		if(door7 != null)
		{
			_door7 = new CodexThing(door7.GetGUID());
			if(_door7.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door7.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door7.GetGUID());
		}

		if(door8 != null)
		{
			_door8 = new CodexThing(door8.GetGUID());
			if(_door8.GetDescriptionID().equalsIgnoreCase("PROP"))
				_door8.SetDescriptionID("GEN_DOOR");
			CaptureThing(_door8.GetGUID());
		}
	}

	public void beginscene(int clientGuid, int captureID)
	{
		//if(!bOpen)
		//{
		//	_door.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door2.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door3.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door4.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door5.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door6.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door7.SetThingFlags(THING_FLAG_VISBLOCK);
		//	_door8.SetThingFlags(THING_FLAG_VISBLOCK);
		//}

		_door.SetThingFlags(THING_FLAG_VISBLOCK);
		_door2.SetThingFlags(THING_FLAG_VISBLOCK);
		_door3.SetThingFlags(THING_FLAG_VISBLOCK);
		_door4.SetThingFlags(THING_FLAG_VISBLOCK);
		_door5.SetThingFlags(THING_FLAG_VISBLOCK);
		_door6.SetThingFlags(THING_FLAG_VISBLOCK);
		_door7.SetThingFlags(THING_FLAG_VISBLOCK);
		_door8.SetThingFlags(THING_FLAG_VISBLOCK);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(bActive)
			return;

		bActive = true;

		if(!bOpen)
		{
			_door.MoveToFrame(1, _speed);

			if(_door2 != null)
			      _door2.MoveToFrame(1, _speed);

			if(_door3 != null)
			      _door3.MoveToFrame(1, _speed);

			if(_door4 != null)
			      _door4.MoveToFrame(1, _speed);

			if(_door5 != null)
                  _door5.MoveToFrame(1, _speed);

			if(_door6 != null)
                  _door6.MoveToFrame(1, _speed);

			if(_door7 != null)
			      _door7.MoveToFrame(1, _speed);

			if(_door8 != null)
			      _door8.MoveToFrame(1, _speed);

			bOpen = true;

			//_door.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door2.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door3.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door4.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door5.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door6.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door7.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door8.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			_door.MoveToFrame(0, _speed);

			if(_door2 != null)
			      _door2.MoveToFrame(0, _speed);

			if(_door3 != null)
			      _door3.MoveToFrame(0, _speed);

			if(_door4 != null)
			      _door4.MoveToFrame(0, _speed);

			if(_door5 != null)
			      _door5.MoveToFrame(0, _speed);

			if(_door6 != null)
			      _door6.MoveToFrame(0, _speed);

			if(_door7 != null)
			      _door7.MoveToFrame(0, _speed);

			if(_door8 != null)
			      _door8.MoveToFrame(0, _speed);

			bOpen = false;

			//_door.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door2.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door3.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door4.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door5.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door6.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door7.SetThingFlags(THING_FLAG_VISBLOCK);
			//_door8.SetThingFlags(THING_FLAG_VISBLOCK);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(_OpenOnce == 0)
			bActive = false;
		else if(_OpenOnce == 1)
		{
			_door.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door3.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door4.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door5.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door6.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door7.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_door8.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
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
