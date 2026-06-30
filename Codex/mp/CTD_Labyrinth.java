/**
 * Curse the Darkness Labyrinth script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_Labyrinth extends Codex
{
	private MP_CTDChronicle	chronScript;

	public CodexThing	_codexHint1;
	public CodexThing	_codexHint2;
	public CodexThing	_codexHint3;
	public CodexThing	_codexHint4;
	public CodexThing	_codexHint5;

	public static String _params[] =	{"Codex Hint 1", "Codex Hint 2", "Codex Hint 3", "Codex Hint 4", "Codex Hint 5"};

	public CTD_Labyrinth(CodexThing codexHint1, CodexThing codexHint2, CodexThing codexHint3, CodexThing codexHint4, CodexThing codexHint5)
	{
		_codexHint1 = new CodexThing(codexHint1.GetGUID());
		_codexHint2 = new CodexThing(codexHint2.GetGUID());
		_codexHint3 = new CodexThing(codexHint3.GetGUID());
		_codexHint4 = new CodexThing(codexHint4.GetGUID());
		_codexHint5 = new CodexThing(codexHint5.GetGUID());

		CaptureThing(_codexHint1.GetGUID());
		CaptureThing(_codexHint2.GetGUID());
		CaptureThing(_codexHint3.GetGUID());
		CaptureThing(_codexHint4.GetGUID());
		CaptureThing(_codexHint5.GetGUID());
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _codexHint1.GetGUID())
		{
			ExecuteText(clickerGuid, guid, "Narrator_15_1_560");
		}

		if(guid == _codexHint2.GetGUID())
		{
			ExecuteText(clickerGuid, guid, "Narrator_15_1_561");
		}

		if(guid == _codexHint3.GetGUID())
		{
			ExecuteText(clickerGuid, guid, "Narrator_15_1_562");
		}

		if(guid == _codexHint4.GetGUID())
		{
			ExecuteText(clickerGuid, guid, "Narrator_15_1_563");
		}

		if(guid == _codexHint5.GetGUID())
		{
			ExecuteText(clickerGuid, guid, "Narrator_15_1_564");
		}

	}

}