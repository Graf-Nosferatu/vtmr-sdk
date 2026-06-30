/**
 * Factory4 LOT 6.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_FAC4_6_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	private CodexActor		_Luke;
	private CodexThing		_sunRoomDoor;

	private int				_clientGuid;

	public boolean			bLukeConversation = false;	
	
	public static String _params[] = {"Luke", "Sun Room door"};

	public LOT_FAC4_6_1(CodexActor Luke, CodexThing sunRoomDoor)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Luke = new CodexActor(Luke.GetGUID());
		_sunRoomDoor = new CodexThing(sunRoomDoor.GetGUID());

		CaptureThing(_Luke.GetGUID());
		CaptureThing(_sunRoomDoor.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.FAC4_LUKESAVE))
		{
			CodexSequence.SetChronicleFlag(chronScript.FAC4_LUKESAVE);

			CodexThing initiatorThing = new CodexThing(clientGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "LUKE" + "%t" + "BEGINSCENE" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			SetTimer(5);
		}

		_clientGuid = clientGuid;
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.FAC4_LUKESAVE))
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_DOORLOCKED");
		}
	}
	
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_sunRoomDoor.RotatePivot(1, 5);
	}
	
	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _sunRoomDoor.GetGUID())
		{
			chronScript.Step(chronScript.FAC4_LUKESAVE);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				LukeConversation(_clientGuid, _Luke.GetGUID());
			}
		}
	}

	public void LukeConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "6_1_Luke", "6_1_Luke.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bLukeConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLukeConversation)
		{
			AIOn();
			bLukeConversation = false;
			CodexCamera.Release(starterGuid);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_FindDominic"));

			// open the factory entrance and slum room entrance
			//CodexSequence.OpenExit("NewYorkUptown", 3);
			//CodexSequence.OpenExit("NewYorkDocks", 6);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bLukeConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTLukeSave.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
/*
						case 1:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 3:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 4:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 5:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 6:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

