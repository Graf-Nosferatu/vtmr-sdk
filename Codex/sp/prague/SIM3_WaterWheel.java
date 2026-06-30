/**
 *  Silver Mines 3 water wheel script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 

public class SIM3_WaterWheel extends Codex
{
	private static final int	TIMER_ID_MOVEWATER		= 1;

	private int				_frameNumBucket1 = 1;
	private int				_frameNumBucket2 = 1;
	private int				_frameNumBucket3 = 1;
	private boolean			bWaterStopped;
	private boolean			bActive = false;

	private CodexThing		_waterWheelLever;
	private CodexThing		_waterWheelLatch;
	private CodexThing		_waterWheel;
	private CodexThing		_bucket1;
	private CodexThing		_bucket2;
	private CodexThing		_bucket3;
	
	private CodexThing		_water;

	public static String _params[] = {"Water wheel lever", "Water wheel latch", "Water wheel", "Bucket 1", "Bucket 2", "Bucket 3"};

	public SIM3_WaterWheel(CodexThing waterWheelLever, CodexThing waterWheelLatch, CodexThing waterWheel, CodexThing bucket1, CodexThing bucket2, CodexThing bucket3)
	{
		_waterWheelLever = new CodexThing(waterWheelLever.GetGUID());
		_waterWheelLatch = new CodexThing(waterWheelLatch.GetGUID());
		_waterWheel = new CodexThing(waterWheel.GetGUID());
		_bucket1 = new CodexThing(bucket1.GetGUID());
		_bucket2 = new CodexThing(bucket2.GetGUID());
		_bucket3 = new CodexThing(bucket3.GetGUID());
 
		CaptureThing(_waterWheelLever.GetGUID());
		CaptureThing(_waterWheelLatch.GetGUID());
		CaptureThing(_waterWheel.GetGUID());
		CaptureThing(_bucket1.GetGUID());
		CaptureThing(_bucket2.GetGUID());
		CaptureThing(_bucket3.GetGUID());

		_water = new CodexThing(GetClassThing());
	}

	// --------------------------------------------------------------------------------------------

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		if(guid == _waterWheelLever.GetGUID())
		{
			_waterWheelLever.RotatePivot(1, 1);		

			bActive = true;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _waterWheelLever.GetGUID())
		{
			_waterWheelLatch.MoveToFrame(1, 50);

			SetTimer(1, TIMER_ID_MOVEWATER);
		}

		if(thingGuid == _water.GetGUID())
		{
			bWaterStopped = true;

			_water.SetCollideType(THING_COLLIDE_NONE);
		}

		if(bWaterStopped)
			return;

		if(thingGuid == _waterWheel.GetGUID())
		{
			_waterWheel.RotatePivot(1, -8);
			return;
		}

		if(thingGuid == _bucket1.GetGUID())
		{
			if(frameNum == 3)
				_frameNumBucket1 = 0;
			else
				_frameNumBucket1 = frameNum + 1;

			_bucket1.MoveToFrame(_frameNumBucket1, 40);
		}
		else if(thingGuid == _bucket2.GetGUID())
		{
			if(frameNum == 3)
				_frameNumBucket2 = 0;
			else
				_frameNumBucket2 = frameNum + 1;

			_bucket2.MoveToFrame(_frameNumBucket2, 40);
		}
		else if(thingGuid == _bucket3.GetGUID())
		{
			if(frameNum == 3)
				_frameNumBucket3 = 0;
			else
				_frameNumBucket3 = frameNum + 1;

			_bucket3.MoveToFrame(_frameNumBucket3, 40);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_MOVEWATER:

				_waterWheel.RotatePivot(1, -7);

				_bucket1.MoveToFrame(_frameNumBucket1, 40);
				_bucket2.MoveToFrame(_frameNumBucket2, 40);
				_bucket3.MoveToFrame(_frameNumBucket3, 40);

				_water.MoveToFrame(1, 1);
				break;
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bActive);
	}

	public void restore(int flags)
	{
		bActive = CodexSequence.RestoreBoolean();
	}
}