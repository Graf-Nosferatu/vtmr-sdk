/**
 * OLDT_EastGate Click exit script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_EastGateClickExit extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing	door;
	private int			_exitNumber;

	public static String _params[] = {"Exit number;0"};

	public OLDT_EastGateClickExit(int exitNumber)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		door = new CodexThing(GetClassThing());
		_exitNumber = exitNumber;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.OLDT_TOVIENNA))
		{
			door.SetDescriptionID("OLDT_EXIT_DESC_3b");
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.OLDT_TOVIENNA))
		{
			// leave for vienna quest
			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_LeaveVienna"));
			q.Complete();

			CodexSequence.ChangeChronicle("vienna.nsc");
		}
		// check to see if the exit is open
		else if(!CodexSequence.IsExitClosed(CodexSequence.GetLocationName(CodexSequence.GetCurrentLocation()), _exitNumber))
		{
			// if it's open, take it
			CodexSequence.TakeExit(_exitNumber, clickerGuid);
		}
		else
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, GetClassThing());
		}
	}
}
