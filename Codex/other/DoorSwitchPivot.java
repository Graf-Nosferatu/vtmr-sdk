/**
 * DoorSwitch script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/
 

public class DoorSwitchPivot extends Codex
{

	private float			_speed = (float)10.0;
	private float			_openTime = (float)0.0;
	private CodexThing		_switchThing;

	private CodexThing		door;
	private int				doorGuid = 0;

	private boolean			bOpen = false;
	private boolean			bActive = false;
	private boolean			bSwitchActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Movement speed;150.0", "Switch Time;0", "Switch"};

	// --------------------------------------------------------------------------------------------

	public DoorSwitchPivot(float speed, float openTime, CodexThing switchThing)
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
		if(bActive || bSwitchActive)
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
		bSwitchActive = true;

		if(!bOpen) 
		{
			_switchThing.RotatePivot(1, _openTime);
			door.MoveToFrame(1, _speed);
			bOpen = true;

			//door.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			_switchThing.RotatePivot(1, -_openTime);
			door.MoveToFrame(0, _speed);
			bOpen = false;

			//door.SetThingFlags(THING_FLAG_VISBLOCK);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == door.GetGUID())
			bActive = false;

		if(thingGuid == _switchThing.GetGUID())
			bSwitchActive = false;
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



