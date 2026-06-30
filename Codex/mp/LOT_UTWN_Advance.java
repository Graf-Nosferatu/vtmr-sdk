/**
 * New York Uptown LOT Advance script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_UTWN_Advance extends Codex
{
	private MP_LOTChronicle	chronScript;

	public LOT_UTWN_Advance()
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.FAC4_LUKESAVE) &&
			!CodexSequence.GetChronicleFlag(chronScript.UTWN_ADVANCE))
		{
			CodexSequence.SetChronicleFlag(chronScript.UTWN_ADVANCE);

			CodexSequence.SetReviveLocation(1, 5);

			// check if the ST is handling advancement, if not auto-advance
			if(!NetIsNoAutoAdvance())
			{
				CodexSequence.Advance(0);
			}
		}
	}
}

