/**
 *  ItemShippingManifest script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemShippingManifest extends Codex
{
	public PragueChronicle	chronScript;

	public ItemShippingManifest()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public boolean pickup(int item, int picker, int captureID)
	{
		CodexSequence.SetChronicleFlag(chronScript.ARC2_MANIFESTRECOVERED);
		return(true);
	}
}
