/**
 * Teutonic Knight Base 3 Door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TEU3_22_1_Door extends Codex
{
	private ViennaChronicle	chronScript;

	private	float			_openSpeed;
	private CodexThing		_door;

	private boolean			bDoorOpen = false;
	private boolean			bDoorActive = false;

	public static String _params[] =	{"open speed;20"};

	public TEU3_22_1_Door(float openSpeed)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_openSpeed = openSpeed;
		_door = new CodexThing(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_door.SetThingFlags(THING_FLAG_VISBLOCK);
	}

	public void clicked(int guid, int clickerGUID, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.TEU2_AMULETRECOVERED))
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");

			return;
		}

		if(bDoorOpen || bDoorActive)
			return;

		bDoorActive = true;

		_door.MoveToFrame(1, _openSpeed);
		//_door.ClearThingFlags(THING_FLAG_VISBLOCK);
	}

	public void arrived(int thingGUID, int frameNum, int captureID)
	{
		bDoorActive = false;
		bDoorOpen = true;

		//_door.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
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