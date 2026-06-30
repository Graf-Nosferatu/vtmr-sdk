/**
 *  Region trigger rotate thing script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class RegionTriggerRotate extends Codex
{
	private CodexThing		_rotateObject;
	private int				_frameNum = 1;
	private float			_duration = (float)3.0;
	private boolean			bRotated = false;
	
	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Rotate object", "Rotate around frame;1", "Duration;3.0"};

	public RegionTriggerRotate(CodexThing rotateObject, int frameNum, float duration)
	{
		_rotateObject = new CodexThing(rotateObject.GetGUID());
		_frameNum = frameNum;
		_duration  = duration;
	}

	// --------------------------------------------------------------------------------------------

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!bRotated)
		{
			CodexThing enterThing = new CodexThing(causeGUID);
			if(enterThing.IsPlayer())
			{
				_rotateObject.RotatePivot(_frameNum, _duration);
				bRotated = true;
			}
		}
	}

	// --------------------------------------------------------------------------------------------

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bRotated);
	}
 
	public void restore(int flags)
	{
		bRotated = CodexSequence.RestoreBoolean();
	}
}
