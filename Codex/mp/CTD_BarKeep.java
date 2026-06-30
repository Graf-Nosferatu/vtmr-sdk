/**
 * CTD BarKeep script scene 4.1+ script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_BarKeep extends Codex
{
	private MP_CTDChronicle	chronScript;

	private CodexActor		_barKeep;
	private boolean			_bActive = false;

	public CTD_BarKeep()
	{
		chronScript = (MP_CTDChronicle)GetChronicleScript(0);

		_barKeep = new CodexActor(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// for the storyteller
		if(!CodexSequence.GetChronicleFlag(chronScript.CTD_TEXTINTRO))
		{
			chronScript.Step(chronScript.CTD_TEXTINTRO);

			ExecuteText(clientGuid, guid, "IntroTextCTD");
		}

		_barKeep.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void joined(int clientGuid)
	{
		ExecuteText(clientGuid, guid, "IntroTextCTD");
	}
}