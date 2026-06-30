/**
 *  STE2_FiveLevers script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/

public class STE2_FiveLevers extends Codex
{
	private static final float	LEVER_MOVE_SPEED = (float)15.0;

	private float				_openSpeed;
	private CodexThing			_lever1;
	private CodexThing			_lever2;
	private CodexThing			_lever3;
	private CodexThing			_lever4;
	private CodexThing			_lever5;

	private CodexThing			_doorPiece1;
	private CodexThing			_doorPiece2;

	private boolean				bLever1Down = false;
	private boolean				bLever2Down = false;
	private boolean				bLever3Down = false;
	private boolean				bLever4Down = false;
	private boolean				bLever5Down = false;

	private boolean				bPuzzleComplete = false;

	private boolean				bActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Door speed;20.0", "lever 1", "lever 2", "lever 3 (bad one)", 
										"lever 4 (bad one)", "lever 5 (bad one - reset)", "Door piece 1", "Door piece 2"};

	public STE2_FiveLevers(float openSpeed, CodexThing lever1, CodexThing lever2, CodexThing lever3, 
							CodexThing lever4, CodexThing lever5, CodexThing doorPiece1, CodexThing doorPiece2)
	{
		_openSpeed  = openSpeed;

		_lever1 = new CodexThing(lever1.GetGUID());
		_lever2 = new CodexThing(lever2.GetGUID());
		_lever3 = new CodexThing(lever3.GetGUID());
		_lever4 = new CodexThing(lever4.GetGUID());
		_lever5 = new CodexThing(lever5.GetGUID());

		_doorPiece1 = new CodexThing(doorPiece1.GetGUID());
		_doorPiece2 = new CodexThing(doorPiece2.GetGUID());
		
		// capture our levers so we get clicked messages for them
		CaptureThing(_lever1.GetGUID(), 1);
		CaptureThing(_lever2.GetGUID(), 2);
		CaptureThing(_lever3.GetGUID(), 3);
		CaptureThing(_lever4.GetGUID(), 4);
		CaptureThing(_lever5.GetGUID(), 5);

		CaptureThing(_doorPiece1.GetGUID());
		CaptureThing(_doorPiece2.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_lever1.SetThingFlags(THING_FLAG_NOREFLECTION);
		_lever2.SetThingFlags(THING_FLAG_NOREFLECTION);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// if we are done or busy moving stuff
		if(bActive || bPuzzleComplete)
			return;

		if(guid == _doorPiece1.GetGUID() || guid == _doorPiece2.GetGUID())
		{
			// hint for puzzle
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "CODEX_FIVEBUTTONDOOR");
			return;
		}

		bActive = true;

		switch(captureId)
		{
			case 1:

				if(bLever1Down)
					_lever1.MoveToFrame(0, LEVER_MOVE_SPEED);
				else
					_lever1.MoveToFrame(1, LEVER_MOVE_SPEED);

				bLever1Down = !bLever1Down;
				break;

			case 2:

				if(bLever2Down)
					_lever2.MoveToFrame(0, LEVER_MOVE_SPEED);
				else
					_lever2.MoveToFrame(1, LEVER_MOVE_SPEED);

				bLever2Down = !bLever2Down;
				break;

			case 3:

				if(bLever3Down)
					_lever3.MoveToFrame(0, LEVER_MOVE_SPEED);
				else
					_lever3.MoveToFrame(1, LEVER_MOVE_SPEED);

				bLever3Down = !bLever3Down;				
				break;

			case 4:

				if(bLever4Down)
					_lever4.MoveToFrame(0, LEVER_MOVE_SPEED);
				else
					_lever4.MoveToFrame(1, LEVER_MOVE_SPEED);

				bLever4Down = !bLever4Down;
				break;

			case 5:

				if(bLever5Down)
					_lever5.MoveToFrame(0, LEVER_MOVE_SPEED);
				else
					_lever5.MoveToFrame(1, LEVER_MOVE_SPEED);

				bLever5Down = !bLever5Down;
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		// if we are done
		if(bPuzzleComplete)
			return;

		switch(captureId)
		{
			case 1:

				if(bLever2Down && !bLever3Down && !bLever4Down)
					CompletePuzzle();
				bActive = false;
				break;

			case 2:

				if(bLever1Down && !bLever3Down && !bLever4Down)
					CompletePuzzle();
				bActive = false;
				break;

			case 3:

				if(!bLever3Down && !bLever4Down && bLever1Down && bLever2Down)
					CompletePuzzle();
				bActive = false;
				break;

			case 4:

				if(!bLever3Down && !bLever4Down && bLever1Down && bLever2Down)
					CompletePuzzle();
				bActive = false;
				break;

			case 5:

				SetTimer(1);
				break;
		}
	}

	// --------------------------------------------------------------------------------------------

	public void CompletePuzzle()
	{
		_doorPiece1.MoveToFrame(1, _openSpeed);
		_doorPiece2.MoveToFrame(1, _openSpeed);

		_doorPiece1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_doorPiece2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		_lever3.MoveToFrame(1, LEVER_MOVE_SPEED);
		_lever4.MoveToFrame(1, LEVER_MOVE_SPEED);
		_lever5.MoveToFrame(1, LEVER_MOVE_SPEED);

		bPuzzleComplete = true;

		bActive = false;
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		ResetPuzzle();
	}

	public void ResetPuzzle()
	{
		_lever1.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever1Down = false;

		_lever2.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever2Down = false;

		_lever3.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever3Down = false;

		_lever4.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever4Down = false;

		_lever5.MoveToFrame(0, LEVER_MOVE_SPEED);
		bLever5Down = false;

		bActive = false;
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bPuzzleComplete);
		CodexSequence.SaveBoolean(bLever1Down);
		CodexSequence.SaveBoolean(bLever2Down);
		CodexSequence.SaveBoolean(bLever3Down);
		CodexSequence.SaveBoolean(bLever4Down);
		CodexSequence.SaveBoolean(bLever5Down);
	}

	public void restore(int flags)
	{
		bPuzzleComplete = CodexSequence.RestoreBoolean();
		bLever1Down = CodexSequence.RestoreBoolean();
		bLever2Down = CodexSequence.RestoreBoolean();
		bLever3Down = CodexSequence.RestoreBoolean();
		bLever4Down = CodexSequence.RestoreBoolean();
		bLever5Down = CodexSequence.RestoreBoolean();
	}
}
