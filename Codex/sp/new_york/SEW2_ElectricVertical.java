/**
 * NY Sewer 2 Electric Vertical 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 
public class SEW2_ElectricVertical extends Codex
{
	private static final int		TIMER_ID_ARC_START		= 1;
	private static final int		TIMER_ID_ARC_STOP		= 2;

	private CodexThing	_mover1;
	private CodexThing	_mover2;
	private CodexThing	_mover3;
	private CodexThing	_mover4;

	private CodexThing	_ghost;

	private float		_moverSpeed = (float)100.0;

	private float[]		_offset = new float[3];

	private int			_beam1Guid = 0;
	private CodexThing	_beam1;
	private int			_beam2Guid = 0;
	private CodexThing	_beam2;
	private int			_beam3Guid = 0;
	private CodexThing	_beam3;
	private int			_beam4Guid = 0;
	private CodexThing	_beam4;

	private boolean		_bTrapStarted = false;

	public static String _params[] = {"mover 1", "mover 2", "mover 3", "mover 4", "mover speed;100.0"};

	public SEW2_ElectricVertical(CodexThing mover1, CodexThing mover2, CodexThing mover3, CodexThing mover4, float moverSpeed)
	{
		_mover1 = new CodexThing(mover1.GetGUID());
		_mover2 = new CodexThing(mover2.GetGUID());
		_mover3 = new CodexThing(mover3.GetGUID());
		_mover4 = new CodexThing(mover4.GetGUID());

		_moverSpeed = moverSpeed;

		CaptureThing(_mover1.GetGUID());
		CaptureThing(_mover2.GetGUID());
		CaptureThing(_mover3.GetGUID());
		CaptureThing(_mover4.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_ghost = new CodexThing(GetClassThing());

		if(!_bTrapStarted)
		{
			_bTrapStarted = true;

			// spawn a beam at each mover's position
			_beam1Guid = _mover1.SpawnThing("bluelightning");
			_beam1 = new CodexThing(_beam1Guid);
			_beam2Guid = _mover2.SpawnThing("bluelightning");
			_beam2 = new CodexThing(_beam2Guid);
			_beam3Guid = _mover3.SpawnThing("bluelightning");
			_beam3 = new CodexThing(_beam3Guid);
			_beam4Guid = _mover4.SpawnThing("bluelightning");
			_beam4 = new CodexThing(_beam4Guid);

			// allocate two frames for each beam (0th frame is its "origin" and the 1st is its target)
			// then set the target of the beam to the mover "ahead" of it that was linked in the script
			_beam1.AllocateFrames(2);
			_beam1.SetFramePosition(1, _mover2.GetPosition());
			_beam2.AllocateFrames(2);
			_beam2.SetFramePosition(1, _mover3.GetPosition());
			_beam3.AllocateFrames(2);
			_beam3.SetFramePosition(1, _mover4.GetPosition());
			_beam4.AllocateFrames(2);
			_beam4.SetFramePosition(1, _mover1.GetPosition());

			_offset[0] = 0; 
			_offset[1] = 0; 
			_offset[2] = 0;

			// attach each beam to its mover
			_mover1.AttachThing(_beam1Guid, -1, _offset, ATTACH_FLAG_AUTOREMOVE);
			_mover2.AttachThing(_beam2Guid, -1, _offset, ATTACH_FLAG_AUTOREMOVE);
			_mover3.AttachThing(_beam3Guid, -1, _offset, ATTACH_FLAG_AUTOREMOVE);
			_mover4.AttachThing(_beam4Guid, -1, _offset, ATTACH_FLAG_AUTOREMOVE);
		}

		_mover1.MoveToFrame(1, _moverSpeed);
		_mover2.MoveToFrame(1, _moverSpeed);
		_mover3.MoveToFrame(1, _moverSpeed);
		_mover4.MoveToFrame(1, _moverSpeed);

		SetTimer(1, TIMER_ID_ARC_START);
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		CodexThing _mover = new CodexThing(thingGuid);
	
		if(frameNum + 1 < _mover.GetNumFrames())
		{
			_mover.MoveToFrame(frameNum + 1, _moverSpeed);

			if(thingGuid == _mover1.GetGUID())
			{
				_beam4.SetFramePosition(1, _mover1.GetPosition());
			}
			if(thingGuid == _mover2.GetGUID())
			{
				_beam1.SetFramePosition(1, _mover2.GetPosition());
			}
			if(thingGuid == _mover3.GetGUID())
			{
				_beam2.SetFramePosition(1, _mover3.GetPosition());
			}
			if(thingGuid == _mover4.GetGUID())
			{
				_beam3.SetFramePosition(1, _mover4.GetPosition());
			}
		}
		else
		{
			// hide the beams for the quick trip back down to frame 0
			_beam1.DisableEmitter();
			_beam2.DisableEmitter();
			_beam3.DisableEmitter();
			_beam4.DisableEmitter();

			_mover1.MoveToFrame(0, 10000);
			_beam1.SetFramePosition(1, _mover2.GetPosition());
			_mover2.MoveToFrame(0, 10000);
			_beam2.SetFramePosition(1, _mover3.GetPosition());
			_mover3.MoveToFrame(0, 10000);
			_beam3.SetFramePosition(1, _mover4.GetPosition());
			_mover4.MoveToFrame(0, 10000);
			_beam4.SetFramePosition(1, _mover1.GetPosition());

			// show the beams again
			_beam1.EnableEmitter();
			_beam2.EnableEmitter();
			_beam3.EnableEmitter();
			_beam4.EnableEmitter();
		}
	}	

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(_bTrapStarted);
	}
 
	public void restore(int flags)
	{
		_bTrapStarted = CodexSequence.RestoreBoolean();
	}
}



