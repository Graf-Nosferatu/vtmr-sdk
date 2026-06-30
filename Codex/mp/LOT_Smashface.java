/**
 * Smashface LOT 11.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_Smashface extends Codex
{
	private MP_LOTChronicle	chronScript;

	public boolean		bSmashfaceConversation = false;

	public LOT_Smashface()
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.WAR_TALKEDSMASHFACE))
		{
			chronScript.Step(chronScript.WAR_TALKEDSMASHFACE);

			//CodexSequence.SetChronicleFlag(chronScript.WAR_TALKEDSMASHFACE);

			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "SMASHFACE" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				SmashfaceConversation(clickerGuid, guid);
			}
		}
	}

	public void SmashfaceConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "11_1_Smashface", "11_1_Smashface.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bSmashfaceConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bSmashfaceConversation)
		{
			AIOn();
			bSmashfaceConversation = false;
			CodexCamera.Release(starterGuid);

			//CodexQuest q2 = new CodexQuest(CodexQuest.Load("LOT_SewerPassage"));

			// open the sewer entrance
			//CodexSequence.OpenExit("NewYorkDocks", 2);

			// change the scene in the lobby 
			//CodexSequence.ChangeScene("BarclayLobby", "LOBY_LOT_12_1.nsd");

			// check if the ST is handling advancement, if not auto-advance
			if(!NetIsNoAutoAdvance())
			{
				CodexSequence.Advance(0);
			}
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTSmashface.ncp", 30);
						//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

