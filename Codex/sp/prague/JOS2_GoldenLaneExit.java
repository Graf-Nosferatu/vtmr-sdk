/**
 * Josefs Tunnels Region script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class JOS2_GoldenLaneExit extends Codex
{
	private PragueChronicle	chronScript;

	public JOS2_GoldenLaneExit()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.JOS2_GOLDENLANEEXIT))
		{
			CodexSequence.SetChronicleFlag(chronScript.JOS2_GOLDENLANEEXIT);

			CodexSequence.OpenExit("GoldenLane", 3);
		}
	}
}