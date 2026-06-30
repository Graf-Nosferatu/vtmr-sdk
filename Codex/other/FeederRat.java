/**
 * FeederRat script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class FeederRat extends Codex
{
	public FeederRat()
	{	
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		CodexThing ratThing = new CodexThing(guid);

		if(ratThing != null)
		{
			CodexPlayer player = new CodexPlayer(clickerGuid);

			int ratItemGuid = ratThing.SpawnThing("vitaeRat");
			player.AddToActorInventory(ratItemGuid);

			// finally, remove the rat actor...
			ratThing.Remove();
		}
	}	
}
