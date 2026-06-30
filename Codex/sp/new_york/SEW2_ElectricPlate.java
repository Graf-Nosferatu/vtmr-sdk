/**
 * NY Sewer 2 Electric Plate
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 
public class SEW2_ElectricPlate extends Codex
{
	private static final int		TIMER_ID_ARC_ON		= 1;
	private static final int		TIMER_ID_ARC_OFF	= 2;

	private CodexThing	_floorMover;
	private CodexThing	_wallMover;

	private CodexThing	_ghost;

	private float		_floorMoverSpeed = (float)100.0;
	private float		_wallMoverSpeed = (float)90.0;

	private float[]		_offset = new float[3];

	private int			_beamGuid = 0;
	private CodexThing	_beam;

	private boolean		_bTrapOff = false;
	private boolean		_bTrapStarted = false;

	public static String _params[] = {"Floor mover", "Floor mover speed;100.0", "Wall mover", "Wall mover speed;90.0"};

	public SEW2_ElectricPlate(CodexThing floorMover, float floorMoverSpeed, CodexThing wallMover, float wallMoverSpeed)
	{
		_floorMover = new CodexThing(floorMover.GetGUID());
		_floorMoverSpeed = floorMoverSpeed;

		_wallMover = new CodexThing(wallMover.GetGUID());
		_wallMoverSpeed = wallMoverSpeed;

		CaptureThing(_floorMover.GetGUID());
		CaptureThing(_wallMover.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_ghost = new CodexThing(GetClassThing());

		if(!_bTrapStarted)
		{
			_bTrapStarted = true;

			// spawn a beam at the floor mover's position
			_beamGuid = _floorMover.SpawnThing("bluelightning");
			_beam = new CodexThing(_beamGuid);

			// hide beam immediately
			_beam.DisableEmitter();

			// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
			// then set the target of the beam to the wall mover that was linked in the script
			_beam.AllocateFrames(2);
			_beam.SetFramePosition(1, _wallMover.GetPosition());

			_offset[0] = 0; 
			_offset[1] = 0; 
			_offset[2] = 0;

			_floorMover.AttachThing(_beamGuid, -1, _offset, ATTACH_FLAG_AUTOREMOVE);
		}

		_floorMover.MoveToFrame(1, _floorMoverSpeed);
		_wallMover.MoveToFrame(1, _wallMoverSpeed);

		SetTimer(1, TIMER_ID_ARC_ON);
	}

	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		if(p0 == 1)
		{
			_beam.DisableEmitter();
			_bTrapOff = true;
		}
		else
		{
			_bTrapOff = false;
			SetTimer(1, TIMER_ID_ARC_ON);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(_bTrapOff)
			return;

		switch(timerID)
		{
			case TIMER_ID_ARC_ON:
				new CodexSound("electrical_arc_03.WAV", 300, 600, 100, 0, 0, _ghost.GetGUID());

				// show arc beam here
				_beam.EnableEmitter();

				SetTimer((float)3.5, TIMER_ID_ARC_OFF);
				break;
			case TIMER_ID_ARC_OFF:
				// hide arc beam here
				_beam.DisableEmitter();

				SetTimer((float)(5 + Math.random() * (5)), TIMER_ID_ARC_ON);
				break;
		}
	}


	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		CodexThing		_mover;
		float			_speed = 0;

		if(thingGuid == _floorMover.GetGUID())
		{
			_mover = new CodexThing(_floorMover.GetGUID());
			_speed = _floorMoverSpeed;
		}
		else if(thingGuid == _wallMover.GetGUID())
		{
			_beam.SetFramePosition(1, _wallMover.GetPosition());

			_mover = new CodexThing(_wallMover.GetGUID());
			_speed = _wallMoverSpeed;		
		}
		else
		{
			// something is arriving we don't care about
			return;
		}

		if(frameNum + 1 < _mover.GetNumFrames())
		{
			_mover.MoveToFrame(frameNum + 1, _speed);
		}
		else
		{
			_mover.MoveToFrame(0, _speed);
		}
	}	

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(_bTrapOff);
		CodexSequence.SaveBoolean(_bTrapStarted);
	}
 
	public void restore(int flags)
	{
		_bTrapOff = CodexSequence.RestoreBoolean();
		_bTrapStarted = CodexSequence.RestoreBoolean();
	}
}



