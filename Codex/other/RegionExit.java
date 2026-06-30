/**
 * Region exit script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class RegionExit extends Codex
{
	private int _exitNumber;
	public static String _params[] = {"Exit number;0"};

	public RegionExit(int exitNumber)
	{
		_exitNumber = exitNumber;
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(IsPlayerGuid(causeGUID))
		{
			CodexSequence.TakeExit(_exitNumber, causeGUID);
		}
	}
}
