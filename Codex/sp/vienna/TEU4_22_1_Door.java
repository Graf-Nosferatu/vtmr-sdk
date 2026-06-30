/**
 * Teutonic Knight Base 4 Door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TEU4_22_1_Door extends Codex
{
	private ViennaChronicle	chronScript;

	private	float			_openSpeed;
	private CodexThing		_cellDoor1;

	private int				leverGUID;

	private boolean			bCellDoor1open = false;
	private boolean			bCellDoor1Active = false;

	public static String _params[] =	{"open speed;20", "Cell Door 1"};

	public TEU4_22_1_Door(float openSpeed, CodexThing cellDoor1)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_openSpeed = openSpeed;
		_cellDoor1 = new CodexThing(cellDoor1.GetGUID());

		_cellDoor1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		CaptureThing(_cellDoor1.GetGUID());
		leverGUID = GetClassThing();
	}

	public void clicked(int guid, int clickerGUID, int captureID)
	{
		// lever was clicked
		if(guid == leverGUID)
		{
			OpenCellDoor1();
		}
	}

	public void arrived(int thingGUID, int frameNum, int captureID)
	{
		bCellDoor1Active = false;
		bCellDoor1open = true;
	}

	public void OpenCellDoor1()
	{
		if(bCellDoor1open)
			return;

		bCellDoor1Active = true;

		_cellDoor1.RotatePivot(1, 3);
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bCellDoor1open);
	}

	public void restore(int flags)
	{
		bCellDoor1open = CodexSequence.RestoreBoolean();
	}
}