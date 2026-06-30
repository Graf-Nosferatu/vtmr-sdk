/**
 * Pivot door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/
 

public class PivotDoor extends Codex
{
	private CodexThing	door;

	private int			_frameNum = 1;
	private float		_duration = (float)3.0;

	private boolean		bOpen = false;
	private boolean		bActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Rotate around frame;1", "Duration;3.0"};

	// --------------------------------------------------------------------------------------------

	public PivotDoor(int frameNum, float duration)
	{
		_frameNum = frameNum;
		_duration = duration;

		door = new CodexThing(GetClassThing());
		if(door.GetDescriptionID().equalsIgnoreCase("PROP"))
			door.SetDescriptionID("GEN_DOOR");
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

		bActive = true;

		if(!bOpen) 
		{
			door.RotatePivot(_frameNum, _duration);
			bOpen = true;

			//door.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			door.RotatePivot(_frameNum, -_duration);
			bOpen = false;

			//door.SetThingFlags(THING_FLAG_VISBLOCK);
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



