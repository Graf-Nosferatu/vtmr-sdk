/**
 * Josefs Tunnels 3 Door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class JOS3_15_1_Door extends Codex
{
	private PragueChronicle	chronScript;

	private	float			_openSpeed;
	private CodexThing		_door;

	private boolean			bDoorOpen = false;
	private boolean			bDoorActive = false;

	public static String _params[] =	{"open time;3"};

	public JOS3_15_1_Door(float openSpeed)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_openSpeed = openSpeed;
		_door = new CodexThing(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_door.SetThingFlags(THING_FLAG_VISBLOCK);
		if(_door.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door.SetDescriptionID("GEN_DOOR");
	}

	public void clicked(int guid, int clickerGUID, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.JOS3_VACLAVDEAD))
			return;

		if(bDoorOpen || bDoorActive)
			return;

		bDoorActive = true;

		_door.RotatePivot(1, _openSpeed);

		//_door.ClearThingFlags(THING_FLAG_VISBLOCK);
	}

	public void arrived(int thingGUID, int frameNum, int captureID)
	{
		bDoorActive = false;
		bDoorOpen = true;
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bDoorOpen);
	}
 
	public void restore(int flags)
	{
		bDoorOpen = CodexSequence.RestoreBoolean();
	}
}