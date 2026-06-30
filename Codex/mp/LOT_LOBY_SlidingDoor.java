/**
 * Lobby Sliding Doors script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author Sthoms
*/
 

public class LOT_LOBY_SlidingDoor extends Codex
{
	private MP_LOTChronicle	chronScript;

	private float			_speed = (float)10.0;
	private int				_numPlayersInRegion = 0;

	private CodexThing		_doorPart1;
	private CodexThing		_doorPart2;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Door part 1", "Door part 2", "Movement speed;100.0"};

	// --------------------------------------------------------------------------------------------

	public LOT_LOBY_SlidingDoor(CodexThing doorPart1, CodexThing doorPart2, float speed)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_speed = speed;

		_doorPart1 = new CodexThing(doorPart1.GetGUID());
		_doorPart2 = new CodexThing(doorPart2.GetGUID());

		CaptureThing(_doorPart1.GetGUID());
		CaptureThing(_doorPart2.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void entered(int guid, int causeGUID, int captureID)
	{
		// if the thing entering the region is not an actor, return
		if(!IsActorGuid(causeGUID))
			return;

		// disallow opening of door until party has talked to Bill Campbell.
		// this prevents meeting Domonic without talking to Bill first.
		if (!CodexSequence.GetChronicleFlag(chronScript.LOBY_MEETBILL))
			return;


		// increment number of players in the region so we know if we can close the door later
		_numPlayersInRegion++;

	    _doorPart1.MoveToFrame(1, _speed);
		_doorPart2.MoveToFrame(1, _speed);
	}

	public void exited(int guid, int causeGUID, int captureID)
	{
		if(!IsActorGuid(causeGUID))
			return;

		// decrement number of players in the region so we know if we can close the door later
		_numPlayersInRegion--;

		// try to close the door
		SetTimer(2);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(_numPlayersInRegion == 0)
		{
			// close the sliding door
			_doorPart1.MoveToFrame(0, _speed);
			_doorPart2.MoveToFrame(0, _speed);
		}
//		else
//		{
//			// try to close it a little later
//			SetTimer(2);
//		}
	}
}