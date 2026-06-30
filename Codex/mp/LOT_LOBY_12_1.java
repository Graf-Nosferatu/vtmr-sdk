/**
 * Barclay Lobby LOT 12.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_LOBY_12_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	private static final int CHRON_ENDING_INDEX_BAD		= 0;
	private static final int CHRON_ENDING_INDEX_GOOD	= 1;
	private static final int CHRON_ENDING_INDEX_MEDIUM	= 2;
	private static final int CHRON_ENDING_INDEX_NEUTRAL	= 3;

	private CodexActor		_Bill;
	private int				frameBill = 0;

	private float[]			pos;

	public boolean			bBillConversation = false;
	public boolean			bMeetingConversation = false;
	public boolean			bMediumConversation = false;
	public boolean			bBadConversation = false;
	public boolean			bGoodConversation = false;

	public static String _params[] = {"Bill"};

	public LOT_LOBY_12_1(CodexActor Bill)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Bill = new CodexActor(Bill.GetGUID());

		CaptureThing(_Bill.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		CodexActor mrschreck = new CodexActor(CodexThing.GuidFromCastID("Shreck"));
		CodexActor arianna = new CodexActor(CodexThing.GuidFromCastID("Arianna"));
		CodexActor michaela = new CodexActor(CodexThing.GuidFromCastID("Michaela"));
		CodexActor lucinde = new CodexActor(CodexThing.GuidFromCastID("Lucinde"));
		CodexActor dominic = new CodexActor(CodexThing.GuidFromCastID("Dominic"));
		CodexActor heavyg = new CodexActor(CodexThing.GuidFromCastID("HeavyG"));
		CodexActor bill = new CodexActor(CodexThing.GuidFromCastID("Bill"));

		mrschreck.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		arianna.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		michaela.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		lucinde.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		dominic.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		heavyg.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		bill.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_BILLRING))
		{
			chronScript.Step(chronScript.LOBY_BILLRING);
			//CodexSequence.SetChronicleFlag(chronScript.LOBY_BILLRING);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_SewerPassage"));
			//q.Complete();

			CodexThing initiatorThing = new CodexThing(clientGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "BILL" + "%t" + "BEGINSCENE" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				BillConversation(clientGuid, _Bill.GetGUID());
			}
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(thingGuid == _Bill.GetGUID())
		{
			if(frameBill == 1)
				_Bill.LookAtThing(CodexThing.GuidFromCastID("HeavyG"));
			else if(frameBill == 2)
				_Bill.Remove();
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_END))
		{
			CodexSequence.SetChronicleFlag(chronScript.LOBY_END);

			CodexThing initiatorThing = new CodexThing(causeGUID);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "DOMINIC" + "%t" + "ENTERED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				MeetingConversation(causeGUID, 1);
			}
		}
	}

	public void textended(int readerGuid)
	{
		// ENDCHRON is set when the end of the chron conversations are ended
		if(CodexSequence.GetChronicleFlag(chronScript.LOBY_ENDCHRON))
		{
			CodexSequence.Advance(readerGuid);
		}
	}

	public void BillConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "12_1_Bill", "12_1_Bill.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bBillConversation = true;
			AIOff();
		}
	}

	public void MeetingConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "13_1_Meeting", "13_1_Meeting.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMeetingConversation = true;
			AIOff();
		}
	}

	public void MediumConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "13_1_Medium", "13_1_Medium.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMediumConversation = true;
			AIOff();
		}
	}

	public void BadConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "13_1_Bad", "13_1_Bad.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bBadConversation = true;
			AIOff();
		}
	}

	public void GoodConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "13_1_Good", "13_1_Good.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bGoodConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bBillConversation)
		{
			AIOn();
			bBillConversation = false;
			CodexCamera.Release(starterGuid);

			if(returnValue == 2)
			{
				// returnValue of 2 means they agreed to let him plant the ring
				CodexSequence.SetChronicleFlag(chronScript.LOBY_BILLPLANTRING);

				// send bill into the boardroom
				frameBill = 1;
				pos = _Bill.GetFramePosition(1);
				_Bill.SendActorToPos(pos, (float)210.0);
			}
			else
			{
				// send bill out into the lobby
				frameBill = 2;
				pos = _Bill.GetFramePosition(2);
				_Bill.SendActorToPos(pos, (float)210.0);

				// so they can't trigger the ending conversations after this
				CodexSequence.SetChronicleFlag(chronScript.LOBY_END);

				// check if the ST is handling advancement, if not auto-advance
				if(!NetIsNoAutoAdvance())
				{
					EndChronicle(CHRON_ENDING_INDEX_NEUTRAL);
					//CodexSequence.Advance(starterGuid);
				}

			}
		}
		else if(bMeetingConversation)
		{
			bMeetingConversation = false;

			CodexThing initiatorThing = new CodexThing(starterGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "DOMINIC" + "%t" + "CONVENDED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(CodexSequence.GetChronicleFlag(chronScript.LOBY_BILLPLANTRING) &&
				!CodexSequence.GetChronicleFlag(chronScript.WAR_KEEPPACT))
			{
				BadConversation(starterGuid, 0);
			}
			else if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_BILLPLANTRING) &&
				CodexSequence.GetChronicleFlag(chronScript.WAR_KEEPPACT))
			{
				MediumConversation(starterGuid, 0);
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.LOBY_BILLPLANTRING) &&
				CodexSequence.GetChronicleFlag(chronScript.WAR_KEEPPACT))
			{
				GoodConversation(starterGuid, 0);
			}

		}
		else if(bMediumConversation)
		{
			AIOn();
			bMediumConversation = false;
			CodexCamera.Release(starterGuid);

			// check if the ST is handling advancement, if not auto-advance
			if(!NetIsNoAutoAdvance())
			{
				EndChronicle(CHRON_ENDING_INDEX_MEDIUM);
				//CodexSequence.Advance(starterGuid);
			}
		}
		else if(bBadConversation)
		{
			AIOn();
			bBadConversation = false;
			CodexCamera.Release(starterGuid);

			// check if the ST is handling advancement, if not auto-advance
			if(!NetIsNoAutoAdvance())
			{
				EndChronicle(CHRON_ENDING_INDEX_BAD);
				//CodexSequence.Advance(starterGuid);
			}
		}
		else if(bGoodConversation)
		{
			bGoodConversation = false;

			if(returnValue == 1)
			{
				// goto bad ending
				BadConversation(starterGuid, 0);
			}
			else
			{
				AIOn();
				CodexCamera.Release(starterGuid);

				// check if the ST is handling advancement, if not auto-advance
				if(!NetIsNoAutoAdvance())
				{
					EndChronicle(CHRON_ENDING_INDEX_GOOD);
					//CodexSequence.Advance(starterGuid);
				}
			}
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bBillConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bMeetingConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave2.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 7:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bMediumConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave2.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 10:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bBadConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave2.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bGoodConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave2.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void EndChronicle(int endingIndex)
	{
		int numPlayers = CodexPlayer.GetNumMPPlayers();
		int i;

		// this flag is set so the text ended messages can trigger advances
		CodexSequence.SetChronicleFlag(chronScript.LOBY_ENDCHRON);

		for(i = 0;i < numPlayers;i++)
		{
			if(CodexPlayer.GetMPPlayer(i) != 0)
			{
				CodexPlayer player = new CodexPlayer(CodexPlayer.GetMPPlayer(i));

				switch(endingIndex)
				{
					case CHRON_ENDING_INDEX_BAD:

						player.AwardPlayerExperience(1000);

						if(!ExecuteText(CodexPlayer.GetMPPlayer(i), guid, "EndTextBadLOT"))
							CodexSequence.Advance(CodexPlayer.GetMPPlayer(i));
						break;

					case CHRON_ENDING_INDEX_GOOD:

						player.AwardPlayerExperience(5000);

						if(!ExecuteText(CodexPlayer.GetMPPlayer(i), guid, "EndTextGoodLOT"))
							CodexSequence.Advance(CodexPlayer.GetMPPlayer(i));
						break;

					case CHRON_ENDING_INDEX_MEDIUM:

						player.AwardPlayerExperience(2500);

						if(!ExecuteText(CodexPlayer.GetMPPlayer(i), guid, "EndTextMediumLOT"))
							CodexSequence.Advance(CodexPlayer.GetMPPlayer(i));
						break;

					case CHRON_ENDING_INDEX_NEUTRAL:

						if(!ExecuteText(CodexPlayer.GetMPPlayer(i), guid, "EndTextNeutralLOT"))
							CodexSequence.Advance(CodexPlayer.GetMPPlayer(i));
						break;
				}
			}
			else
			{
				return;
			}
		}
	}
}

