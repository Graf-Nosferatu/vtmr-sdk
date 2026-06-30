/**
 * Teutonic Knight Base 3 Door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class GOLD_JosefTunnelsDoor extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing		_door;

	public GOLD_JosefTunnelsDoor()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_door = new CodexThing(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.JOS2_GOLDENLANEEXIT))
			return;

		_door.MoveToFrame(1, 800);
	}
}