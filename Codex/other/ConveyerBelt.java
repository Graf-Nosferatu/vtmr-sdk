/**
 * Conveyer Belt script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ConveyerBelt extends Codex
{
	private String		_attachTemplate;
	private CodexThing	_attachThing;
	private CodexThing	moverThing;

	private int _firstFrame;
	private int _speed;

	private float[] offset = new float[3];

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Attach Template", "First Frame", "Speed"};

	public ConveyerBelt(String attachTemplate, int firstFrame, int speed)
	{	
		moverThing = new CodexThing(GetClassThing());
		_attachTemplate = attachTemplate;
		_firstFrame = firstFrame;
		_speed = speed;
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		offset[0] = 0; offset[1] = 0; offset[2] = 0;
		
		_attachThing = new CodexThing(moverThing.SpawnThing(_attachTemplate));
		moverThing.AttachThing(_attachThing.GetGUID(), -1, offset, ATTACH_FLAG_AUTOREMOVE);

		moverThing.MoveToFrame(_firstFrame, _speed);
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(frameNum < (moverThing.GetNumFrames() - 1))
			moverThing.MoveToFrame(frameNum + 1, _speed);
		else
		{
			offset = moverThing.GetFramePosition(1);
			offset[2] = 5;
			moverThing.SetPosition(offset);
			moverThing.MoveToFrame(1, _speed);
		}
	}
}
