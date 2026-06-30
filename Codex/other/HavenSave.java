/**
 * HavenSave script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HavenSave extends Codex
{
	public HavenSave()
	{
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		new CodexSound("ui_hum.wav", 300, 600, 100, 0, 0, guid);

		CodexSequence.SaveGame();
	}
}
