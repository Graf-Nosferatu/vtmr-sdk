/**
 * HavenVault script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HavenVault extends Codex
{
	public HavenVault()
	{
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		new CodexSound("woodenChest_open.wav", 300, 600, 100, 0, 0, guid);

		CodexSequence.Vault(clickerGuid);
	}
}
