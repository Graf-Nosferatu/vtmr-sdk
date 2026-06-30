/**
 * Click exit for Ardan's Chantry from Golden Lane script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ClickExitArdan extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing	exitThing;

	private int			_exitNumber;

	private String		originalDescID = " ";
	private boolean     bGotID = false;

	public static String _params[] = {"Exit number;0"};

	public ClickExitArdan(int exitNumber)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		exitThing = new CodexThing(GetClassThing());

		_exitNumber = exitNumber;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!bGotID)
		{
			originalDescID = exitThing.GetDescriptionID();

			bGotID = true;
		}

		// check to see if this should be labeled 'ardan's chantry' or 'pharmacy'
		// if they've gotten the ardan's mission, label it properly

		// GOLD_EXIT_DESC_2                Pharmacy
		// GOLD_EXIT_DESC_2a               Ardan's Tremere Chantry

		if(CodexSequence.GetChronicleFlag(chronScript.PRIN_RELIQUARY))
		{
			exitThing.SetDescriptionID("GOLD_EXIT_DESC_2a");

			// change the "original" to this new original
			originalDescID = exitThing.GetDescriptionID();
		}

		// check to see if the exit is valid - if not, set it to not highlight
		// if it is, make sure it's highlightable
		if(!CodexSequence.IsExitValid(CodexSequence.GetLocationName(CodexSequence.GetCurrentLocation()), _exitNumber))
		{
			exitThing.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else
		{
			exitThing.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		// check to see if the exit is closed
		if(CodexSequence.IsExitClosed(CodexSequence.GetLocationName(CodexSequence.GetCurrentLocation()), _exitNumber))
		{
			exitThing.SetDescriptionID(originalDescID + "_L");	
		}
		else
		{
			exitThing.SetDescriptionID(originalDescID);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		// check to see if the exit is open
		if(!CodexSequence.IsExitClosed(CodexSequence.GetLocationName(CodexSequence.GetCurrentLocation()), _exitNumber))
		{
			// if it's open, take it
			CodexSequence.TakeExit(_exitNumber, clickerGuid);
		}
		else
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, GetClassThing());

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bGotID);
		CodexSequence.SaveString(originalDescID);
	}

	public void restore(int flags)
	{
		bGotID = CodexSequence.RestoreBoolean();
		originalDescID = CodexSequence.RestoreString();		
	}
}
