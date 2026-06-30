/**
 * Silver Mines 2 Alternate exit back to Silver Mines 1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SIM2_AltExitToSIM1 extends Codex
{
	private PragueChronicle	chronScript;

	private int				_exitNumber;

	public static String _params[] = {"Exit number;0"};

	public SIM2_AltExitToSIM1(int exitNumber)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_exitNumber = exitNumber;
	}

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(IsPlayerGuid(causeGuid))
		{
			CodexSequence.SetChronicleFlag(chronScript.SIM2_ALTEXITTAKEN);

			CodexSequence.TakeExit(_exitNumber, causeGuid);
		}
	}
}