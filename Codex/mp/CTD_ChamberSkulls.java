/**
 * CTD Chamber of Skulls script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_ChamberSkulls extends Codex
{
	private MP_CTDChronicle	chronScript;

	private CodexThing			_reliquary;
	private CodexThing			_theoJournal;
	private CodexThing			_pivotDoor;

	public static String _params[] = {"Reliquary", "Thelonius' Journal", "Pivot Door"};

	public CTD_ChamberSkulls(CodexThing reliquary, CodexThing theoJournal, CodexThing pivotDoor)
	{
		// used to know when to start hiding rebecca in temesvar main
		CodexSequence.SetChronicleFlag(chronScript.CATACOMBS_INDUNGEON);

		chronScript = (MP_CTDChronicle)GetChronicleScript(0);

		_reliquary = new CodexThing(reliquary.GetGUID());
		_theoJournal = new CodexThing(theoJournal.GetGUID());
		_pivotDoor = new CodexThing(pivotDoor.GetGUID());

		CaptureThing(_reliquary.GetGUID());
		CaptureThing(_theoJournal.GetGUID());
		CaptureThing(_pivotDoor.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_pivotDoor.SetThingFlags(THING_FLAG_TOPSECRET);
		_pivotDoor.SetThingFlags(THING_FLAG_BLOCKSELECT);

		if(_pivotDoor.GetDescriptionID().equalsIgnoreCase("PROP"))
			_pivotDoor.SetDescriptionID("GEN_HIDDENDOOR");
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _pivotDoor.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.LECOMTE_MONOCLERETRIEVED) &&
			!CodexSequence.GetChronicleFlag(chronScript.CATACOMBS_DOOROPEN))
		{
			CodexSequence.SetChronicleFlag(chronScript.CATACOMBS_DOOROPEN);

			_pivotDoor.RotatePivot(1, 3);

			_pivotDoor.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else
		{
			// sound indicating this door is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, clickerGuid);

			// display locked message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_DOORLOCKED");
		}
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _reliquary.GetGUID())
		{
			CodexSequence.SetChronicleFlag(chronScript.CATACOMBS_RELIQUARYRECOVERED);
		}

		return(true);
	}
}

