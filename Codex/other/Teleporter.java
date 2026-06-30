/**
 * Teleporter script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class Teleporter extends Codex
{
	public Teleporter()
	{	
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		CodexSequence.ActivateTeleporter(guid, clickerGuid);
	}	
}
