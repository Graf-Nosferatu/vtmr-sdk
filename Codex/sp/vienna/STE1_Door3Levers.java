/**
 *  STE1_Door3Levers script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class STE1_Door3Levers extends Codex
{
	private static final float	LEVER_MOVE_SPEED = (float)30.0;

	private float				_duration;
	private CodexThing			_lever1;
	private CodexThing			_lever2;
	private CodexThing			_lever3;

	private CodexThing			_door;

	private boolean				bLever1Locked = false;
	private boolean				bLever2Locked = false;
	private boolean				bLever3Locked = false;
	private boolean				bLever1Down = false;
	private boolean				bLever2Down = false;
	private boolean				bLever3Down = false;

	private boolean				bPuzzleComplete = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"duration;10.0", "lever 1 (bad one)", "lever 2", "lever 3"};

	public STE1_Door3Levers(float openSpeed, CodexThing lever1, CodexThing lever2, CodexThing lever3)
	{
		_duration  = openSpeed;

		_lever1 = new CodexThing(lever1.GetGUID());
		_lever2 = new CodexThing(lever2.GetGUID());
		_lever3 = new CodexThing(lever3.GetGUID());

		_door = new CodexThing(GetClassThing());
		
		// capture our 3 levers so we get clicked messages for them
		CaptureThing(_lever1.GetGUID(), 1);
		CaptureThing(_lever2.GetGUID(), 2);
		CaptureThing(_lever3.GetGUID(), 3);

		CaptureThing(_door.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_door.SetThingFlags(THING_FLAG_SECRET);
		_door.SetThingFlags(THING_FLAG_VISBLOCK);
		_door.SetThingFlags(THING_FLAG_BLOCKSELECT);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// if we are done
		if(bPuzzleComplete)
			return;

		if(guid == _door.GetGUID())
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, clickerGuid);

			// display locked message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_DOORLOCKED");
		}
		else
		{
			switch(captureId)
			{
				case 1:
					MoveLever1(!bLever1Down);
					break;
				case 2:
					MoveLever2(!bLever2Down);
					break;
				case 3:
					MoveLever3(!bLever3Down);
					break;
			}
		}
	}

	// --------------------------------------------------------------------------------------------

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		// if we are done
		if(bPuzzleComplete)
			return;

		switch(captureId)
		{
			case 1:

				bLever1Locked = false;
				bLever1Down = frameNum == 1 ? true : false;
				if(bLever1Down) 
					ResetPuzzle();
				break;

			case 2:

				bLever2Locked = false;
				bLever2Down = frameNum == 1 ? true : false;
				if(bLever3Down)
					CompletePuzzle();
				break;

			case 3:

				bLever3Locked = false;
				bLever3Down = frameNum == 1 ? true : false;
				if(bLever2Down)
					CompletePuzzle();
				break;
		}

		if(thingGuid == _door.GetGUID())
			_door.Remove();
	}

	// --------------------------------------------------------------------------------------------

	public void CompletePuzzle()
	{
		//_door.MoveToFrame(1, _duration);
		_door.RotatePivot(1, _duration);
		_door.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		bPuzzleComplete = true;
	}

	public void ResetPuzzle()
	{
		// reset the puzzle
		MoveLever2(false);
		MoveLever3(false);

		// prevent further action on lever 1
		bLever1Locked = true;

		// prepare to bring it back up
		SetTimer((float)1.0);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// bring lever1 up as the end of the puzzle reset
		bLever1Locked = false;
		MoveLever1(false);
	}

	// --------------------------------------------------------------------------------------------

	public void MoveLever1(boolean bDown)
	{
		if(bLever1Locked)
			return;

		if(bLever1Down == bDown)
			return;

		_lever1.MoveToFrame(bDown ? 1 : 0, LEVER_MOVE_SPEED);
		bLever1Locked = true;
		bLever1Down = bDown;
	}

	public void MoveLever2(boolean bDown)
	{
		if(bLever2Locked)
			return;

		if(bLever2Down == bDown)
			return;

		_lever2.MoveToFrame(bDown ? 1 : 0, LEVER_MOVE_SPEED);
		bLever2Locked = true;
		bLever2Down = bDown;
	}

	public void MoveLever3(boolean bDown)
	{
		if(bLever3Locked)
			return;

		if(bLever3Down == bDown)
			return;

		_lever3.MoveToFrame(bDown ? 1 : 0, LEVER_MOVE_SPEED);
		bLever3Locked = true;
		bLever3Down = bDown;
	}
}
