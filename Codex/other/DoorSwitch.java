/**
 * DoorSwitch script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/
 

public class DoorSwitch extends Codex
{

	private float			_speed = (float)10.0;
	private float			_openTime = (float)0.0;
	private CodexThing		_switchThing;

	private CodexThing		door;
	private int				doorGuid = 0;

	private boolean			bOpen = false;
	private boolean			bActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Movement speed;150.0", "Open Time;0", "Switch"};

	// --------------------------------------------------------------------------------------------

	public DoorSwitch(float speed, float openTime, CodexThing switchThing)
	{
		_speed			= speed;
		_openTime		= openTime;
		_switchThing	= new CodexThing(switchThing.GetGUID());

		CaptureThing(_switchThing.GetGUID());

		doorGuid		= GetClassThing();
		door			= new CodexThing(doorGuid);

		if(door.GetDescriptionID().equalsIgnoreCase("PROP"))
			door.SetDescriptionID("GEN_DOOR");
		if(_switchThing.GetDescriptionID().equalsIgnoreCase("PROP"))
			_switchThing.SetDescriptionID("GEN_SWITCH");
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		//if(!bOpen)
		//	door.SetThingFlags(THING_FLAG_VISBLOCK);

		door.SetThingFlags(THING_FLAG_VISBLOCK);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(bActive)
			return;

		// we don't want to activate the door directly
		if(guid == doorGuid)
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
			door.MoveToFrame(1, _speed);
			bOpen = true;

			//door.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			door.MoveToFrame(0, _speed);
			bOpen = false;

			//door.SetThingFlags(THING_FLAG_VISBLOCK);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		bOpen = frameNum == 0 ? false : true;

		if(bOpen)
		{
			if(_openTime > 0.0)
				SetTimer(_openTime);
			else
				bActive = false;

		}

		if(!bOpen)
			bActive = false;
	}


	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(bOpen)
		{
			door.MoveToFrame(0, _speed);
			bOpen = false;

			//door.SetThingFlags(THING_FLAG_VISBLOCK);
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



