/**
 * Silver Mines 2 Alternate exit back to Silver Mines 1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SIM1_SIM2Blockade extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing		_silvermines2block;
	private CodexThing		_silvermines2rollover;
	private CodexThing		_silvermines3rollover;

	public static String _params[] = {"Silver mines 2 block", "Silver mines 2 rollover", "Silver mines 3 rollover"};

	public SIM1_SIM2Blockade(CodexThing silvermines2block, CodexThing silvermines2rollover, CodexThing silvermines3rollover)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_silvermines2block = new CodexThing(silvermines2block.GetGUID());
		_silvermines2rollover = new CodexThing(silvermines2rollover.GetGUID());
		_silvermines3rollover = new CodexThing(silvermines3rollover.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.SIM2_ALTEXITTAKEN))
		{
			// remove the blockade from silvermines 1 to 2
			_silvermines2block.Remove();

			// show the silvermines 2 rollover
			_silvermines2rollover.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else
		{
			// hide the silvermines 2 rollover
			_silvermines2rollover.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
		{
			// show the silvermines 3 rollover
			_silvermines3rollover.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else
		{
			// hide the silvermines 3 rollover
			_silvermines3rollover.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}
}