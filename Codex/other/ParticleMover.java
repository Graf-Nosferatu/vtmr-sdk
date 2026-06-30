/**
 * Particle Mover script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 

public class ParticleMover extends Codex
{
	private CodexThing	_mover;
	private CodexThing	_particleThing;
	private float		_speed = (float)1200.0;
	private float[]		offset = new float[3];


	public static String _params[] = {"Particle Emitter", "Speed:1200.0"};

	public ParticleMover(CodexThing particleThing, float speed)
	{
		_particleThing = new CodexThing(particleThing.GetGUID());
		_speed = speed;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_mover = new CodexThing(GetClassThing());

		offset[0] = 0; offset[1] = 0; offset[2] = 0;
		_mover.AttachThing(_particleThing.GetGUID(), -1, offset, ATTACH_FLAG_AUTOREMOVE);

		_mover.MoveToFrame(1, _speed);
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(frameNum + 1 < _mover.GetNumFrames())
		{
			_mover.MoveToFrame(frameNum + 1, _speed);
		}
		else
		{
			_mover.MoveToFrame(0, _speed);
		}
	}	

}



