/**
 * DoorRegion script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author Sthoms
*/
 

public class DoorRegion extends Codex
{

	private float			_speed = (float)10.0;
	private float			_openTime = (float)0.0;
	private CodexRegion		_triggerDoorRegion;

	private CodexThing		door;
	private int				doorGuid = 0;

      private int                   _OpenOnce = 0;

	private boolean			bOpen = false;
	private boolean			bActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Movement speed;150.0", "Open Time;0", "Open Once;0", "Region"};

	// --------------------------------------------------------------------------------------------

	public DoorRegion(float speed, float openTime, int OpenOnce, CodexThing triggerDoorRegion)
	{
		_speed		      = speed;
		_openTime		      = openTime;
		_triggerDoorRegion      = new CodexRegion(triggerDoorRegion.GetGUID());
 
        _OpenOnce               = OpenOnce;

		CaptureThing(_triggerDoorRegion.GetGUID());

		doorGuid		= GetClassThing();
		door			= new CodexThing(doorGuid);

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

	public void entered(int guid, int causeGUID, int captureID)
	{
		// if the thing entering the region is not an actor, return
		if(!IsActorGuid(causeGUID))
			return;

            // if still active or if bActive never set to false because of OpenOnce, return
            if(bActive)
                  return;

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

		if((bOpen) && (_openTime > 0.0))
			SetTimer(_openTime);
        else if(_OpenOnce == 0)
            bActive = false;
		else if(_OpenOnce == 1)
			door.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

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