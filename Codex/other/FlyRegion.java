/**
 *  FlyRegion script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class FlyRegion extends Codex
{
	private	String		_pathName;
	private float		_speed;

	private boolean		bRun = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Path name", "Speed;120"};

	public FlyRegion(String pathName, float speed)
	{
		_pathName = pathName;
		_speed = speed;
	}

	// --------------------------------------------------------------------------------------------

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(bRun)
			return;

		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), _pathName, _speed);

		bRun = true;
	}

	public void pathended(int clientGuid)
	{
		CodexCamera.Release(0);
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bRun);
	}
 
	public void restore(int flags)
	{
		bRun = CodexSequence.RestoreBoolean();
	}
}
