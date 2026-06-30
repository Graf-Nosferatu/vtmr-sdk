/**
 * CTD Oubliette Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_Oubliette_Main extends Codex
{
	private MP_CTDChronicle	chronScript;

	private static final int TIMER_ID_HIDEMELMOTH		= 0;

	private static final int CHRON_ENDING_INDEX_BAD		= 0;
	private static final int CHRON_ENDING_INDEX_GOOD	= 1;

	private CodexActor			_Melmoth;
	private CodexActor			_Rebecca;
	private CodexActor			_Ragwick;
	private CodexThing			_lecomteLetter;

	private CodexItem			_monocle;

	private int					melmothGUID;
	private int					rebeccaGUID;
	private int					ragwickGUID;

	public boolean				bMelmothConversation = false;
	public boolean				bMelmothFinaleConversation = false;
	public boolean				bNoMonocleConversation = false;
	public boolean				bBadConversation = false;
	public boolean				bGoodConversation = false;

	private float[]				pos;

	public static String _params[] = {"Melmoth", "Rebecca", "Ragwick", "LeComte letter"};

	public CTD_Oubliette_Main(CodexActor Melmoth, CodexActor Rebecca, CodexActor Ragwick, CodexThing lecomteLetter)
	{
		chronScript = (MP_CTDChronicle)GetChronicleScript(0);

		_Melmoth = new CodexActor(Melmoth.GetGUID());
		_Rebecca = new CodexActor(Rebecca.GetGUID());
		_Ragwick = new CodexActor(Ragwick.GetGUID());
		_lecomteLetter = new CodexThing(lecomteLetter.GetGUID());

		CaptureThing(_Melmoth.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_Rebecca.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		_Rebecca.SetCollideType(THING_COLLIDE_NONE);
		_Rebecca.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Rebecca.SetActorFlags(THING_AF_AIPAUSED);

		_Ragwick.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		_Ragwick.SetCollideType(THING_COLLIDE_NONE);
		_Ragwick.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Ragwick.SetActorFlags(THING_AF_AIPAUSED);

		melmothGUID = CodexThing.GuidFromCastID("Melmoth");
		rebeccaGUID = CodexThing.GuidFromCastID("Rebecca");
		ragwickGUID = CodexThing.GuidFromCastID("Ragwick");

		if(CodexSequence.GetChronicleFlag(chronScript.LECOMTE_MONOCLERETRIEVED))
		{
			_Melmoth.SetAlpha(1);

			_Melmoth.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Melmoth.SetCollideType(THING_COLLIDE_CYL);
			_Melmoth.ClearActorFlags(THING_AF_AIPAUSED);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_BATTLE))
		{
			_Melmoth.SetActorFlags(THING_AF_TALKTO);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_LECOMTELETTER))
		{
			// hide letter initially
			_lecomteLetter.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_lecomteLetter.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		}
		else
		{
			// show the letter so they can pick it up
			_lecomteLetter.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_lecomteLetter.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Melmoth.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "MELMOTH" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_MELMOTHMEETING))
			{
				chronScript.Step(chronScript.OUBLIETTE_MELMOTHMEETING);
				
				//CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_MELMOTHMEETING);

				//CodexQuest q = new CodexQuest(CodexQuest.Load("CTD_MeetMelmoth"));
				//q.Complete();

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					MelmothConversation(clickerGuid, guid);
				}
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.LECOMTE_MONOCLERETRIEVED) &&
				!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_ENDMELMOTHCONV))
			{
				////////////////////////////////////////////////////////////////////////////
				// the following chunk checks to see if the person who clicked on melmoth
				// has the monocle, if not, then play the conversation where he says
				// he'll only talk to the person who has the monocle
				////////////////////////////////////////////////////////////////////////////
				CodexItem item;
				int itemGuid;
				CodexActor clicker	= new CodexActor(clickerGuid);
				boolean bHaveMonocle = false;

				// walk through the inventory
				itemGuid = clicker.GetActorFirstInventoryItem();

				while((itemGuid > 0))
				{
					item = new CodexItem(itemGuid);

					String templateName = item.GetTemplateName();

					if(templateName.equalsIgnoreCase("monacleclarity"))
					{
						// save for later, in case they choose to "give" melmoth
						// the monocle
						_monocle = new CodexItem(itemGuid);

						bHaveMonocle = true;
					}

					// get next in inventory to prepare for next loop
					itemGuid = item.GetNextInventoryItem();	
				}

				if(bHaveMonocle)
				{
					// play Melmoth finale conversation
					CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_ENDMELMOTHCONV);

					CodexQuest q2 = new CodexQuest(CodexQuest.Load("CTD_ReturnToMelmoth"));
					q2.Complete();

					// check here if ST is handling the conversation, if not, call conversation
					if(!NetIsNoAutoConversations())
					{
						MelmothFinaleConversation(clickerGuid, guid);
					}
				}
				else
				{
					// play Melmoth "only talk to guy with the monocle" conversation
					NoMonocleConversation(clickerGuid, guid);
				}
			}
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Melmoth.GetGUID())
		{
			// causeID of 0 means he killed himself or the ST used the "Kill Actor" button
			// so we need to find a player in the area to show the conversation to
			if(causeID == 0)
			{
				CodexCollision query = new CodexCollision();

				query.IgnoreAll();
				query.AcceptType(THING_TYPE_PLAYER);

				int numResults = query.ThingsInSphere(_Melmoth.GetPosition(), 800.0f, _Melmoth.GetGUID());

				// if no players are around, the ST killed Melmoth himself with his player in another level
				// and we'll just advance
				if(numResults == 0)
				{
					if(!NetIsNoAutoAdvance())
					{
						CodexSequence.Advance(0);
					}
				}
				else
				{
					// grab the first player in the query array for the conversation
					int killerGuid = query.GetResult(0);

					CodexThing initiatorThing = new CodexThing(killerGuid);
					int locationNum = initiatorThing.GetLocationNum();

					String aFormat = "%a" + "MELMOTH" + "%t" + "KILLED" + "%L" + CodexSequence.GetLocationName(locationNum);
					//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
					CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

					if(!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_GOODEND))
					{
						// check here if ST is handling the conversation, if not, call conversation
						if(!NetIsNoAutoConversations())
						{
							BadConversation(killerGuid, guid);
						}
					}
					else if(CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_GOODEND))
					{
						// check here if ST is handling the conversation, if not, call conversation
						if(!NetIsNoAutoConversations())
						{
							GoodConversation(killerGuid, guid);
						}
					}
				}
			}
			else
			{
				// we have a valud causeID so use that for the conversations

				CodexThing initiatorThing = new CodexThing(causeID);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "MELMOTH" + "%t" + "KILLED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				if(!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_GOODEND))
				{
					// check here if ST is handling the conversation, if not, call conversation
					if(!NetIsNoAutoConversations())
					{
						BadConversation(causeID, guid);
					}
				}
				else if(CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_GOODEND))
				{
					// check here if ST is handling the conversation, if not, call conversation
					if(!NetIsNoAutoConversations())
					{
						GoodConversation(causeID, guid);
					}
				}
			}
		}
	}

	public void textended(int readerGuid)
	{
		// ENDCHRON is set when the end of the chron conversations are ended
		if(CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_ENDCHRON))
		{
			CodexSequence.Advance(readerGuid);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{	
		switch(timerID)
		{
			case TIMER_ID_HIDEMELMOTH:

				_Melmoth.SpawnThing("blueMagic");
				_Melmoth.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				break;
		}
	}

	public void MelmothConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_Melmoth", "2_1_Melmoth.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMelmothConversation = true;
			AIOff();
		}
	}

	public void MelmothFinaleConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "5_1_Melmoth", "5_1_Melmoth.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMelmothFinaleConversation = true;
			AIOff();
		}
	}

	public void NoMonocleConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "5_1_NoMonocle", "5_1_NoMonocle.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bNoMonocleConversation = true;
			AIOff();
		}
	}

	public void BadConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "5_1_Bad", "5_1_Bad.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bBadConversation = true;
			AIOff();
		}
	}

	public void GoodConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "5_1_Good", "5_1_Good.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bGoodConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMelmothConversation)
		{
			AIOn();

			bMelmothConversation = false;
			CodexCamera.Release(starterGuid);

			////////////////////////////////////////////////////////////////////
			// at convended, retrieve monacle added to quest log, melmoth vanishes again
			////////////////////////////////////////////////////////////////////
			//CodexQuest q3 = new CodexQuest(CodexQuest.Load("CTD_RetrieveMonocle"));

			// melmoth gives them access to the catacombs
				
			// open the exit to catacombs
			//CodexSequence.OpenExit("Temesvar", 1);

			_Melmoth.SetAlpha(.1f, 5000f);
			SetTimer(5, TIMER_ID_HIDEMELMOTH);

			_Melmoth.SetCollideType(THING_COLLIDE_NONE);
			_Melmoth.SetActorFlags(THING_AF_AIPAUSED);

			if(CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_LECOMTELETTER))
			{
				//ExecuteText(starterGuid, guid, "LeComteLetter");

				// so they can pick the letter up
				_lecomteLetter.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_lecomteLetter.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			}
		}

		if(bMelmothFinaleConversation)
		{
			AIOn();
			bMelmothFinaleConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bNoMonocleConversation)
		{
			AIOn();
			bNoMonocleConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bBadConversation)
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

		if(bGoodConversation)
		{
			AIOn();
			bGoodConversation = false;
			CodexCamera.Release(starterGuid);

			// check if the ST is handling advancement, if not auto-advance
			if(!NetIsNoAutoAdvance())
			{
				EndChronicle(CHRON_ENDING_INDEX_GOOD);
				//CodexSequence.Advance(starterGuid);
			}	
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMelmothConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDOubliette.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
/*
						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							//CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

							////////////////////////////////////////////////////////////////////
							// at one point in the conversation they can get a letter, depending
							// on their answer, set this flag to indicate they've retrieved the letter
							////////////////////////////////////////////////////////////////////
							CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_LECOMTELETTER);
							////////////////////////////////////////////////////////////////////
							break;
					}

/*				case 2:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
*/
			} // switch(curEvent)
		}

		if(bNoMonocleConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDOubliette.ncp", 30);
							break;
					}
			}
		}
		
		if(bMelmothFinaleConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDOubliette.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
/*
						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;

				case 1:

					switch(curLine)
					{
						case 0:

							//CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

							// if they give him the monacle, he fights them - bad ending
							// clear this flag so they can attack him
							_Melmoth.ClearActorFlags(THING_AF_TALKTO);
							_Melmoth.ClearActorFlags(THING_AF_INVUL);
							_Melmoth.ClearActorFlags(THING_AF_AIPAUSED);

							// they "give" him the monocle
							_monocle.RemoveItemFromInventory();

							CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_BATTLE);

							break;
					} // switch(curLine)
					break;

				case 2:

					switch(curLine)
					{
						case 0:
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

							// if they keep it, they fight him with its advantages - good ending
							CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_GOODEND);

							// clear this flag so they can attack him
							_Melmoth.ClearActorFlags(THING_AF_INVUL);
							_Melmoth.ClearActorFlags(THING_AF_AIPAUSED);
							_Melmoth.ClearActorFlags(THING_AF_TALKTO);

							CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_BATTLE);

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

							_Rebecca.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_Rebecca.SetCollideType(THING_COLLIDE_CYL);
							_Rebecca.ClearActorFlags(THING_AF_AIPAUSED);

							pos = _Rebecca.GetFramePosition(1);
							_Rebecca.SendActorToPos(pos, (float)90.0);
							
							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDOubliette.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, rebeccaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, rebeccaGUID, ragwickGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							// rebecca suicide
							_Rebecca.PlayMotionSetMode(MOTION_DEATHQUICK, false, (float)30.0);

							_Ragwick.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_Ragwick.SetCollideType(THING_COLLIDE_CYL);
							_Ragwick.ClearActorFlags(THING_AF_AIPAUSED);		

							pos = _Ragwick.GetFramePosition(1);
							_Ragwick.SendActorToPos(pos, (float)90.0);

							//CodexCamera.SetupCutscene(starterGuid, starterGuid, ragwickGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
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

							_Rebecca.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_Rebecca.SetCollideType(THING_COLLIDE_CYL);
							_Rebecca.ClearActorFlags(THING_AF_AIPAUSED);

							pos = _Rebecca.GetFramePosition(1);
							_Rebecca.SendActorToPos(pos, (float)90.0);

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDOubliette.ncp", 30);

							//CodexCamera.SetupCutscene(starterGuid, starterGuid, rebeccaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							_Ragwick.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_Ragwick.SetCollideType(THING_COLLIDE_CYL);
							_Ragwick.ClearActorFlags(THING_AF_AIPAUSED);		

							pos = _Ragwick.GetFramePosition(1);
							_Ragwick.SendActorToPos(pos, (float)90.0);

							//CodexCamera.SetupCutscene(starterGuid, starterGuid, ragwickGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void EndChronicle(int endingIndex)
	{
		//int numPlayers = CodexPlayer.GetNumMPPlayers();
		int numPlayers = CodexPlayer.GetMaxMPPlayers();
		int i;

		// this flag is set so the text ended messages can trigger advances
		CodexSequence.SetChronicleFlag(chronScript.OUBLIETTE_ENDCHRON);

		for(i = 0;i < numPlayers;i++)
		{
			if(CodexPlayer.GetMPPlayer(i) != 0)
			{
				CodexPlayer player = new CodexPlayer(CodexPlayer.GetMPPlayer(i));

				switch(endingIndex)
				{
					case CHRON_ENDING_INDEX_BAD:

						player.AwardPlayerExperience(2000);

						if(!ExecuteText(CodexPlayer.GetMPPlayer(i), guid, "EndTextBadCTD"))
							CodexSequence.Advance(CodexPlayer.GetMPPlayer(i));
						break;

					case CHRON_ENDING_INDEX_GOOD:

						player.AwardPlayerExperience(5000);

						if(!ExecuteText(CodexPlayer.GetMPPlayer(i), guid, "EndTextGoodCTD"))
							CodexSequence.Advance(CodexPlayer.GetMPPlayer(i));
						break;
				}
			}
		}
	}
}

