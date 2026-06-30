/**
 * Barclay Lobby LOT 1.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_LOBY_1_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	private static final int TIMER_ID_BACKTOLOBBY		= 1;
	private static final int TIMER_ID_SHOWHAVENDOOR		= 2;

	private CodexActor		_Bill;
	private CodexActor		_Dominic;

	private int				frameBill = 0;

	private int				_clientGuid;

	private boolean			bPartyAgree = false;

	private float[]			pos;

	public boolean			bMeetDominicConversation = false;
	public boolean			bShowHavenAndExitConversation = false;

	public static String _params[] = {"Bill", "Dominic"};

	public LOT_LOBY_1_1(CodexActor Bill, CodexActor Dominic)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Bill = new CodexActor(Bill.GetGUID());
		_Dominic = new CodexActor(Dominic.GetGUID());

		CaptureThing(_Bill.GetGUID());
		CaptureThing(_Dominic.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_MEETBILL))
		{
			// close exits that need to be
			CodexSequence.CloseExit("NewYorkDocks", 2);
			CodexSequence.CloseExit("NewYorkDocks", 4);
			CodexSequence.CloseExit("NewYorkDocks", 5);
			CodexSequence.CloseExit("NewYorkDocks", 6);
			CodexSequence.CloseExit("NewYorkUptown", 3);
			CodexSequence.CloseExit("BarclayLobby", 3);
			// haven
			CodexSequence.CloseExit("BarclayLobby", 2);
		}

		// for the storyteller
		if(!CodexSequence.GetChronicleFlag(chronScript.LOT_TEXTINTRO))
		{
			chronScript.Step(chronScript.LOT_TEXTINTRO);

			ExecuteText(clientGuid, guid, "IntroTextLOT");
		}
	}

	public void joined(int clientGuid)
	{
		ExecuteText(clientGuid, guid, "IntroTextLOT");
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Bill.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.LOBY_MEETBILL))
		{
			CodexSequence.SetChronicleFlag(chronScript.LOBY_MEETBILL);

			_Bill.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			pos = _Bill.GetFramePosition(1);
			_Bill.SendActorToPos(pos, (float)90.0);
		}

		if(guid == _Dominic.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.LOBY_MEETDOMINIC))
		{
			CodexSequence.SetChronicleFlag(chronScript.LOBY_MEETDOMINIC);

			chronScript.Step(chronScript.LOBY_MEETDOMINIC);

			_clientGuid = clickerGuid;

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_GreetPrimogen"));

			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "DOMINIC" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				MeetDominicConversation(clickerGuid, guid);
			}
			else
			{
				SetTimer(2, TIMER_ID_SHOWHAVENDOOR);
				SetTimer(4, TIMER_ID_BACKTOLOBBY);
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_BACKTOLOBBY:
				
				pos = _Bill.GetFramePosition(3);
				_Bill.SendActorToPos(pos, (float)90.0);
				
				break;

			case TIMER_ID_SHOWHAVENDOOR:

				_Bill.Stop();
				_Bill.CancelActorAction();

				_Bill.SetPosition(_Bill.GetFramePosition(2));

				// look at the player
				_Bill.LookAtThing(_clientGuid);

				CodexThing initiatorThing = new CodexThing(_clientGuid);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "BILL" + "%t" + "ARRIVED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					ShowHavenAndExitConversation(_clientGuid, _Bill.GetGUID());
				}
				else
				{
					chronScript.Step(chronScript.LOBY_OPENHAVEN);
				}

				break;
		}
	}

	public void MeetDominicConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "1_1_MeetDominic", "1_1_MeetDominic.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMeetDominicConversation = true;
			AIOff();
		}
	}

	public void ShowHavenAndExitConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "1_1_ShowHavenAndExit", "1_1_ShowHavenAndExit.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bShowHavenAndExitConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMeetDominicConversation)
		{
			if(!bPartyAgree)
			{
				MeetDominicConversation(starterGuid, _Dominic.GetGUID());
			}
			else
			{
				AIOn();
				bMeetDominicConversation = false;
				CodexCamera.Release(starterGuid);

				/////////////////////////////////////////////////////////////////////////
				// at convend bill escorts the party to their suite
				/////////////////////////////////////////////////////////////////////////

				// time enough for him to get to the door - using a timer rather than
				// the arrived event because Bill might never make it to the frame
				SetTimer(8, TIMER_ID_SHOWHAVENDOOR);

				pos = _Bill.GetFramePosition(2);
				_Bill.SendActorToPos(pos, (float)90.0);
				/////////////////////////////////////////////////////////////////////////
			}
		}

		if(bShowHavenAndExitConversation)
		{
			AIOn();
			bShowHavenAndExitConversation = false;
			CodexCamera.Release(starterGuid);

			chronScript.Step(chronScript.LOBY_OPENHAVEN);
			
			// open haven
			//CodexSequence.OpenExit("BarclayLobby", 2);

			SetTimer(1, TIMER_ID_BACKTOLOBBY);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMeetDominicConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTMeetDominic.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
/*
						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
				case 1:
					switch(curLine)
					{
						case 0:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
				case 2:
					switch(curLine)
					{
						case 0:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
				case 3:
					switch(curLine)
					{
						case 0:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
*/
				case 4:
					switch(curLine)
					{
						case 0:
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);

							// this is set to get out of this 'looped conversation'
							bPartyAgree = true;

							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bShowHavenAndExitConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTShowHaven.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}
	}
}

