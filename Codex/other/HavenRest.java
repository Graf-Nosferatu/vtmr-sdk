/**
 * HavenRest script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HavenRest extends Codex
{
	public HavenRest()
	{
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		new CodexSound("generic_pickup_creaky.WAV", 300, 600, 100, 0, 0, guid);

		// remove this message if existing
		MessageRemoveBySystemId(GAMEMESSAGE_SYSID_RESTTOADVANCE);

		CodexSequence.Advance(clickerGuid);
	}
}
