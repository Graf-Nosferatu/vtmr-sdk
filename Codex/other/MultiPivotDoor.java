/**
 * Multi pivot door script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class MultiPivotDoor extends Codex
{
	private int			_frameNum = 1;
	private float		_duration = (float)3.0;

	private CodexThing  _door;
	private CodexThing	_door2;
	private CodexThing	_door3;
	private CodexThing	_door4;
	private CodexThing	_door5;
	private CodexThing	_door6;
	private CodexThing	_door7;
	private CodexThing	_door8;

	private int         _OpenOnce = 0;	
	private int			_AllowClicked = 1;

	private boolean		bOpen = false;
	private boolean		bActive = false;

	public static String _params[] = {"Rotate around frame;1", "Duration;3.0", "Open Once;0", "Allow clicked event; 1", "Second part of door", "Third part of door", "Fourth part of door", "Fifth part of door", "Sixth part of door", "Seventh part of door", "Eighth part of door"};

	public MultiPivotDoor(int frameNum, float duration, int OpenOnce, int AllowClicked, CodexThing door2, CodexThing door3, CodexThing door4, CodexThing door5, CodexThing door6, CodexThing door7, CodexThing door8)
	{
		_door = new CodexThing(GetClassThing());
		_door.SetDescriptionID("GEN_DOOR");

		_frameNum = frameNum;
		_duration = duration;

        _OpenOnce = OpenOnce;
		_AllowClicked = AllowClicked;

		_door2 = new CodexThing(door2.GetGUID());
		if(_door2.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door2.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door2.GetGUID());

		_door3 = new CodexThing(door3.GetGUID());
		if(_door3.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door3.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door3.GetGUID());

		_door4 = new CodexThing(door4.GetGUID());
		if(_door4.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door4.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door4.GetGUID());

		_door5 = new CodexThing(door5.GetGUID());
		if(_door5.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door5.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door5.GetGUID());

		_door6 = new CodexThing(door6.GetGUID());
		if(_door6.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door6.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door6.GetGUID());

		_door7 = new CodexThing(door7.GetGUID());
		if(_door7.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door7.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door7.GetGUID());

		_door8 = new CodexThing(door8.GetGUID());
		if(_door8.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door8.SetDescriptionID("GEN_DOOR");
		CaptureThing(_door8.GetGUID());

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
		if(_AllowClicked == 1)
			MoveParts();
	}

	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		MoveParts();
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(_OpenOnce == 0)
			bActive = false;
		else
		{
			_door.SetCollideType(THING_COLLIDE_NONE);
			_door2.SetCollideType(THING_COLLIDE_NONE);
			_door3.SetCollideType(THING_COLLIDE_NONE);
			_door4.SetCollideType(THING_COLLIDE_NONE);
			_door5.SetCollideType(THING_COLLIDE_NONE);
			_door6.SetCollideType(THING_COLLIDE_NONE);
			_door7.SetCollideType(THING_COLLIDE_NONE);
			_door8.SetCollideType(THING_COLLIDE_NONE);

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

	public void MoveParts()
	{
		if(bActive)
			return;

		bActive = true;

		if(!bOpen)
		{
			_door.RotatePivot(_frameNum, _duration);
			_door2.RotatePivot(_frameNum, _duration);
			_door3.RotatePivot(_frameNum, _duration);
			_door4.RotatePivot(_frameNum, _duration);
			_door5.RotatePivot(_frameNum, _duration);
			_door6.RotatePivot(_frameNum, _duration);
			_door7.RotatePivot(_frameNum, _duration);
			_door8.RotatePivot(_frameNum, _duration);

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
			_door.RotatePivot(_frameNum, -_duration);
			_door2.RotatePivot(_frameNum, -_duration);
			_door3.RotatePivot(_frameNum, -_duration);
			_door4.RotatePivot(_frameNum, -_duration);
			_door5.RotatePivot(_frameNum, -_duration);
			_door6.RotatePivot(_frameNum, -_duration);
			_door7.RotatePivot(_frameNum, -_duration);
			_door8.RotatePivot(_frameNum, -_duration);

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

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bOpen);
	}
 
	public void restore(int flags)
	{
		bOpen = CodexSequence.RestoreBoolean();
	}
}
