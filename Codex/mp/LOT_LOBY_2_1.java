/**
 * Barclay Lobby LOT 2.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_LOBY_2_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	private static final int	TIMER_ID_HEAVYGENTERS		= 1;
	private static final int	TIMER_ID_ARIANNAENTERS		= 2;
	private static final int	TIMER_ID_MIRABILISENTERS	= 3;
	private static final int	TIMER_ID_WALKHEAVYGBACK		= 4;
	private static final int	TIMER_ID_KILLSCENE			= 5;

	private CodexActor		_Bill;
	private CodexActor		_Dominic;

	private CodexActor		_HeavyG;
	private CodexActor		_Traci;
	private CodexActor		_Arianna;
	private CodexActor		_Schreck;
	private CodexActor		_Mirabilis;
	private CodexActor		_Johnston;

	private float[]			pos;

	private int				frameHeavyG = 0;
	private int				frameArianna = 0;
	private int				frameDominic = 0;
	private int				frameMirabilis = 0;
	private int				frameBill = 0;

	private int				_clientGuid;
	private int				numPrimogen = 6;

	public boolean			bBillIntroConversation = false;
	public boolean			bHeavyGTraciEnterConversation = false;
	public boolean			bAriannaEntersConversation = false;
	public boolean			bMirabilisEntersConversation = false;

	public static String _params[] = {"Bill", "Dominic", "Heavy G", "Traci", 
									"Arianna", "Schreck", "Mirabilis", "Johnston"};

	public LOT_LOBY_2_1(CodexActor Bill, CodexActor Dominic, CodexActor HeavyG, CodexActor Traci, 
						CodexActor Arianna, CodexActor Schreck, CodexActor Mirabilis, CodexActor Johnston)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Bill = new CodexActor(Bill.GetGUID());
		_Dominic = new CodexActor(Dominic.GetGUID());
		_HeavyG = new CodexActor(HeavyG.GetGUID());
		_Traci = new CodexActor(Traci.GetGUID());
		_Arianna = new CodexActor(Arianna.GetGUID());
		_Schreck = new CodexActor(Schreck.GetGUID());
		_Mirabilis = new CodexActor(Mirabilis.GetGUID());
		_Johnston = new CodexActor(Johnston.GetGUID());

		_Dominic.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_HeavyG.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Traci.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Arianna.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Schreck.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Mirabilis.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Johnston.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		CaptureThing(_Bill.GetGUID());
		CaptureThing(_Dominic.GetGUID());
		CaptureThing(_HeavyG.GetGUID());
		CaptureThing(_Traci.GetGUID());
		CaptureThing(_Arianna.GetGUID());
		CaptureThing(_Schreck.GetGUID());
		CaptureThing(_Mirabilis.GetGUID());
		CaptureThing(_Johnston.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_PRIMOGENHIDDEN))
		{
			CodexSequence.SetChronicleFlag(chronScript.LOBY_PRIMOGENHIDDEN);

			// hide all the primogen in preparation for their appearance
			_HeavyG.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_HeavyG.SetCollideType(THING_COLLIDE_NONE);
			_HeavyG.SetActorFlags(THING_AF_AIPAUSED);		
			_Traci.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Traci.SetCollideType(THING_COLLIDE_NONE);
			_Traci.SetActorFlags(THING_AF_AIPAUSED);
			
			_Arianna.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Arianna.SetCollideType(THING_COLLIDE_NONE);
			_Arianna.SetActorFlags(THING_AF_AIPAUSED);
			
			_Schreck.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Schreck.SetCollideType(THING_COLLIDE_NONE);
			_Schreck.SetActorFlags(THING_AF_AIPAUSED);
			
			_Mirabilis.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Mirabilis.SetCollideType(THING_COLLIDE_NONE);
			_Mirabilis.SetActorFlags(THING_AF_AIPAUSED);
			_Johnston.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Johnston.SetCollideType(THING_COLLIDE_NONE);
			_Johnston.SetActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Bill.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.LOBY_PRIMOGEN))
		{
			chronScript.Step(chronScript.LOBY_PRIMOGEN);

			//CodexSequence.SetChronicleFlag(chronScript.LOBY_PRIMOGEN);

			// use this as the client guid for the rest of the conversations
			_clientGuid = clickerGuid;

			CodexThing initiatorThing = new CodexThing(_clientGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "BILL" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// after he's been clicked, clear his highlight
			_Bill.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				// fail safe time, at the end of this, we'll release the camera
				// and delete everyone, just in case something weird happened
				// the scene plays out without help for about 3 minutes
				SetTimer(185, TIMER_ID_KILLSCENE);

				BillIntroConversation(clickerGuid, guid);
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_HEAVYGENTERS:

				CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyEntrance.ncp", 30);

				_HeavyG.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_HeavyG.SetCollideType(THING_COLLIDE_CYL);
				_HeavyG.ClearActorFlags(THING_AF_AIPAUSED);			
				_Traci.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_Traci.SetCollideType(THING_COLLIDE_CYL);
				_Traci.ClearActorFlags(THING_AF_AIPAUSED);

				pos = _HeavyG.GetFramePosition(1);
				_HeavyG.SendActorToPos(pos, (float)90.0);

				pos = _Traci.GetFramePosition(1);
				_Traci.SendActorToPos(pos, (float)90.0);
				break;

			case TIMER_ID_ARIANNAENTERS:

				_Arianna.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_Arianna.SetCollideType(THING_COLLIDE_CYL);
				_Arianna.ClearActorFlags(THING_AF_AIPAUSED);

				pos = _Arianna.GetFramePosition(1);
				_Arianna.SendActorToPos(pos, (float)90.0);
				break;

			case TIMER_ID_MIRABILISENTERS:

				_Mirabilis.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_Mirabilis.SetCollideType(THING_COLLIDE_CYL);

				// not clearing this AIPAUSED flag right now because he'll attack you
				//_Mirabilis.ClearActorFlags(THING_AF_AIPAUSED);

				_Johnston.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_Johnston.SetCollideType(THING_COLLIDE_CYL);
				_Johnston.ClearActorFlags(THING_AF_AIPAUSED);

				pos = _Mirabilis.GetFramePosition(1);
				_Mirabilis.SendActorToPos(pos, (float)90.0);

				pos = _Johnston.GetFramePosition(1);
				_Johnston.SendActorToPos(pos, (float)90.0);
				break;

			case TIMER_ID_WALKHEAVYGBACK:

				pos = _HeavyG.GetFramePosition(2);
				_HeavyG.SendActorToPos(pos, (float)90.0);

				pos = _Traci.GetFramePosition(2);
				_Traci.SendActorToPos(pos, (float)90.0);
				
				pos = _Bill.GetFramePosition(2);
				_Bill.SendActorToPos(pos, (float)90.0);

				frameBill++;
				break;

			case TIMER_ID_KILLSCENE:

				AIOn();
				CodexCamera.Release(_clientGuid);

				numPrimogen = 0;

				chronScript.Step(chronScript.LOBY_PRIMOGENGONE);

				CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_ReturnToHaven"));

				_HeavyG.Remove();
				_Traci.Remove();
				_Arianna.Remove();
				_Schreck.Remove();
				_Mirabilis.Remove();
				_Johnston.Remove();

				break;
		}
	}
	
	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _Dominic.GetGUID())
		{
			if(frameDominic == 0)
			{
				frameDominic++;

				pos = _Dominic.GetFramePosition(2);
				_Dominic.SendActorToPos(pos, (float)90.0);

				pos = _Arianna.GetFramePosition(2);
				_Arianna.SendActorToPos(pos, (float)90.0);
			}
			else if(frameDominic == 1)
			{
				_Dominic.LookAtThing(_Bill.GetGUID());
			}
		}
		else if(thingGuid == _Bill.GetGUID())
		{
			if(frameBill == 1)
			{
				// send bill back out to lobby
				pos = _Bill.GetFramePosition(1);
				_Bill.SendActorToPos(pos, (float)90.0);

				frameBill++;

				CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyEntrance.ncp", 30);

				SetTimer(0, TIMER_ID_MIRABILISENTERS);
			}
			else if(frameBill == 2)
			{
				_Bill.LookAtThing(_Dominic.GetGUID());
			}
		}
		else if(thingGuid == _HeavyG.GetGUID())
		{
			if(frameHeavyG == 0)
			{
				frameHeavyG++;

				CodexThing initiatorThing = new CodexThing(_clientGuid);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "HEAVYG" + "%t" + "ARRIVED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					HeavyGTraciEnterConversation(_clientGuid, thingGuid);
				}
			}
			else if(frameHeavyG == 1)
			{
				// check here if ST is handling the conversation, if not, remove them
				if(!NetIsNoAutoConversations())
				{
					_HeavyG.Remove();
					numPrimogen--;
					_Traci.Remove();
					numPrimogen--;
				}
			}
		}
		else if(thingGuid == _Arianna.GetGUID())
		{
			if(frameArianna == 0)
			{
				frameArianna++;

				CodexThing initiatorThing = new CodexThing(_clientGuid);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "ARIANNA" + "%t" + "ARRIVED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					AriannaEntersConversation(_clientGuid, thingGuid);			
				}
			}
			else if(frameArianna == 1)
			{
				// check here if ST is handling the conversation, if not, remove them
				if(!NetIsNoAutoConversations())
				{
					_Arianna.Remove();
					numPrimogen--;
				}
			}
		}
		else if(thingGuid == _Mirabilis.GetGUID())
		{
			if(frameMirabilis == 0)
			{
				frameMirabilis++;

				CodexThing initiatorThing = new CodexThing(_clientGuid);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "MIRABILIS" + "%t" + "ARRIVED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					MirabilisEntersConversation(_clientGuid, thingGuid);
				}
			}
			else if(frameMirabilis == 1)
			{
				// check here if ST is handling the conversation, if not, remove them
				if(!NetIsNoAutoConversations())
				{
					// from MirabilisEntersConversation
					AIOn();
					CodexCamera.Release(_clientGuid);

					_Schreck.Remove();
					numPrimogen--;
					_Mirabilis.Remove();
					numPrimogen--;
					_Johnston.Remove();
					numPrimogen--;
				}
			}
		}

		if((numPrimogen == 0) && 
			!CodexSequence.GetChronicleFlag(chronScript.LOBY_PRIMOGENGONE))
		{
			chronScript.Step(chronScript.LOBY_PRIMOGENGONE);

			CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_ReturnToHaven"));

			// kill the fail safe timer, we're at the end
			KillTimer(TIMER_ID_KILLSCENE);
		}
	}

	public void BillIntroConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_BillIntro", "2_1_BillIntro.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bBillIntroConversation = true;
			AIOff();
		}
	}

	public void HeavyGTraciEnterConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_HeavyGTraciEnter", "2_1_HeavyGTraciEnter.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bHeavyGTraciEnterConversation = true;
			AIOff();
		}
	}

	public void AriannaEntersConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_AriannaEnters", "2_1_AriannaEnters.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bAriannaEntersConversation = true;
			AIOff();
		}
	}
	
	public void MirabilisEntersConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_MirabilisEnters", "2_1_MirabilisEnters.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMirabilisEntersConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bBillIntroConversation)
		{
			//AIOn();
			bBillIntroConversation = false;
			//CodexCamera.Release(starterGuid);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_GreetPrimogen"));
			//q.Complete();

			// move party out of the way
			CodexPlayer.TeleportPartyToConversation(_clientGuid, 5);
			//CodexSequence.Jump("BarclayLobby", 5);

			SetTimer(0, TIMER_ID_HEAVYGENTERS);
		}

		if(bHeavyGTraciEnterConversation)
		{
			//AIOn();
			bHeavyGTraciEnterConversation = false;
			//CodexCamera.Release(starterGuid);

			CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyEntrance.ncp", 30);
		}

		if(bAriannaEntersConversation)
		{
			//AIOn();
			bAriannaEntersConversation = false;
			//CodexCamera.Release(starterGuid);

			///////////////////////////////////////////////////////////
			// at the appropriate point in the conversation walk heavy g 
			// and traci back escorted by bill
			///////////////////////////////////////////////////////////
			SetTimer(1, TIMER_ID_WALKHEAVYGBACK);

			CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyDesk.ncp", 30);
		}

		if(bMirabilisEntersConversation)
		{
			//AIOn();
			bMirabilisEntersConversation = false;
			//CodexCamera.Release(starterGuid);

			CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyDesk.ncp", 30);

			///////////////////////////////////////////////////////////
			// at convended group exits, escorted by bill
			///////////////////////////////////////////////////////////
			pos = _Schreck.GetFramePosition(1);
			_Schreck.SendActorToPos(pos, (float)90.0);

			pos = _Bill.GetFramePosition(2);
			_Bill.SendActorToPos(pos, (float)90.0);

			pos = _Mirabilis.GetFramePosition(2);
			_Mirabilis.SendActorToPos(pos, (float)90.0);

			pos = _Johnston.GetFramePosition(2);
			_Johnston.SendActorToPos(pos, (float)90.0);
			///////////////////////////////////////////////////////////
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bBillIntroConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTBillIntro.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bHeavyGTraciEnterConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);

							///////////////////////////////////////////////////////////
							// at the appropriate point in the conversation
							///////////////////////////////////////////////////////////
							SetTimer(0, TIMER_ID_ARIANNAENTERS);
							///////////////////////////////////////////////////////////

							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bAriannaEntersConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyDesk.ncp", 30);

							_Arianna.LookAtThing(_Dominic.GetGUID());

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
						case 3:
							// dominic approaches her during this conversation
							pos = _Dominic.GetFramePosition(1);
							_Dominic.SendActorToPos(pos, (float)90.0);

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 4:
							CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyElevator.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 5:
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 6:
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bMirabilisEntersConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							CodexCamera.PlayPath(_clientGuid, GetGUID(), "lobbyElevator.ncp", 30);

							_Bill.LookAtThing(_Mirabilis.GetGUID());

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);

							break;
						case 1:
							///////////////////////////////////////////////////////////
							// show mr. schrek at the appropriate time in the conversation
							///////////////////////////////////////////////////////////
							//schreckGuid = _SchreckSpot.SpawnThing("MrSchreck");
							//_Schreck = new CodexActor(schreckGuid);
							//CaptureThing(_Schreck.GetGUID());

							_Schreck.SpawnThing("blueMagic");
							_Schreck.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_Schreck.SetCollideType(THING_COLLIDE_CYL);
							_Schreck.ClearActorFlags(THING_AF_AIPAUSED); 
							///////////////////////////////////////////////////////////

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 2:
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 3:
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 4:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;
						case 5:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;
						case 6:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

