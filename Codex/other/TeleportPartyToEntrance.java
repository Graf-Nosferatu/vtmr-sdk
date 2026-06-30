/**
 * TeleportPartyToEntrance script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class TeleportPartyToEntrance extends Codex
{
	private int _entranceNum = 0;

	public static String _params[] = {"Entrance num;0"};

	public TeleportPartyToEntrance(int entranceNum)
	{
		_entranceNum = entranceNum;
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		CodexPlayer.TeleportPartyToEntrance(causeGUID, _entranceNum);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		CodexPlayer.TeleportPartyToEntrance(clickerGuid, _entranceNum);
	}
}
