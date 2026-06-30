/**
 * Petrin Hill monastery 3 Mercurio Journal script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class MON3_MercurioJournal extends Codex
{
	private PragueChronicle	chronScript;

	private CodexItem		_MercurioJournal;

	public MON3_MercurioJournal()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_MercurioJournal = new CodexItem(GetClassThing());
		
		CaptureThing(_MercurioJournal.GetGUID());
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _MercurioJournal.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_MERCURIOJOURNAL))
		{
			ExecuteText(picker, guid, "MercurioJournal");
		}

		return(true);
	}

	public void textended(int readerGuid)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.MON3_MERCURIOJOURNAL))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_MERCURIOJOURNAL);

			MercJournalConversation(readerGuid, 0);
		}
	}

	public void MercJournalConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 4);
		ExecuteConversation(starterGuid, npcGuid, "8_2_MercJournal", "8_2_MercJournal.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		AIOn();
		CodexCamera.Release(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
