/**
 * Sample door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/
 

public class Door extends Codex
{
	private CodexThing	door;

	private float		_speed = (float)10.0;
	private boolean		bOpen = false;
	private boolean		bActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Movement speed;150.0"};

	// --------------------------------------------------------------------------------------------

	public Door(float speed)
	{
		_speed = speed;

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
			door.MoveToFrame(1, _speed);
			//door.ClearThingFlags(THING_FLAG_VISBLOCK);
			bOpen = true;
		}
		else
		{
			door.MoveToFrame(0, _speed);
			//door.SetThingFlags(THING_FLAG_VISBLOCK);
			bOpen = false;
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
