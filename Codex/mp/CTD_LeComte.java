/**
 * CTD LeComte script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_LeComte extends Codex
{
	private MP_CTDChronicle	chronScript;

	private CodexActor			_LeComte;
	private CodexActor			_Ragwick;
	private CodexThing			_monocleSpot;

	private int					ragwickGUID;
	private int					lecomteGUID;

	public boolean				bRagwickConversation = false;
	public boolean				bRagwickRetConversation = false;
	public boolean				bLeComte1Conversation = false;
	public boolean				bLeComteRetConversation = false;

	public static String _params[] = {"Ragwick", "Monocle spawn spot (ghost)"};

	public CTD_LeComte(CodexActor Ragwick, CodexThing monocleSpot)
	{
		chronScript = (MP_CTDChronicle)GetChronicleScript(0);

		_LeComte = new CodexActor(GetClassThing());
		_Ragwick = new CodexActor(Ragwick.GetGUID());
		_monocleSpot = new CodexThing(monocleSpot.GetGUID());

		CaptureThing(_Ragwick.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		ragwickGUID = _Ragwick.GetGUID();
		lecomteGUID = _LeComte.GetGUID();

		if(!CodexSequence.SetChronicleFlag(chronScript.LECOMTE_ATTACKABLE))
		{
			// if lecomte hasn't been set to 'attackable mode', make him a talkie
			_LeComte.SetActorFlags(THING_AF_TALKTO);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ragwick.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "RAGWICK" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(!CodexSequence.GetChronicleFlag(chronScript.RAGWICK_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.RAGWICK_TALKEDONCE);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					RagwickConversation(clickerGuid, 2);
				}
			}
			else
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					RagwickRetConversation(clickerGuid, 2);
				}
			}
		}

		if(guid == _LeComte.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "LECOMTE" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(!CodexSequence.GetChronicleFlag(chronScript.LECOMTE_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.LECOMTE_TALKEDONCE);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					LeComte1Conversation(clickerGuid, 2);
				}
				else
				{
					////////////////////////////////////////////////////////////////////
					// at the end of the conversation check the OUBLIETTE_LECOMTELETTER
					// flag, it tells whether they've retrieved the letter from melmoth
					// or not, if they have le comte hands over the monacle, if they  
					// haven't, they have to fight him for it
					////////////////////////////////////////////////////////////////////
					if(CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_LECOMTELETTER))
					{
						RetrieveMonocle(clickerGuid);
					}
					else
					{
						// make him attackable
						_LeComte.ClearActorFlags(THING_AF_TALKTO);
						_LeComte.ClearActorFlags(THING_AF_NEUTRAL);
						_LeComte.ClearActorFlags(THING_AF_INVUL);
						_LeComte.ClearActorFlags(THING_AF_AIPAUSED);
					}
					////////////////////////////////////////////////////////////////////
				}
			}
			else
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					LeComteRetConversation(clickerGuid, 2);
				}
			}
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _LeComte.GetGUID())
		{
			RetrieveMonocle(causeID);
		}
	}

	public void RagwickConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "3_1_Ragwick", "3_1_Ragwick.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bRagwickConversation = true;
			AIOff();	
		}
	}

	public void RagwickRetConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "3_1_RagwickRet", "3_1_RagwickRet.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bRagwickRetConversation = true;
			AIOff();		
		}
	}

	public void LeComte1Conversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "3_1_LeComte1", "3_1_LeComte1.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bLeComte1Conversation = true;
			AIOff();	
		}
	}

	public void LeComteRetConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "3_1_LeComteRet", "3_1_LeComteRet.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bLeComteRetConversation = true;
			AIOff();	
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bRagwickConversation)
		{
			AIOn();
			bRagwickConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bRagwickRetConversation)
		{
			AIOn();
			bRagwickRetConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bLeComte1Conversation)
		{
			AIOn();
			bLeComte1Conversation = false;
			CodexCamera.Release(starterGuid);

			////////////////////////////////////////////////////////////////////
			// at the end of the conversation check the OUBLIETTE_LECOMTELETTER
			// flag, it tells whether they've retrieved the letter from melmoth
			// or not, if they have le comte hands over the monacle, if they  
			// haven't, they have to fight him for it
			////////////////////////////////////////////////////////////////////
			if(CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_LECOMTELETTER))
			{
				_monocleSpot.SpawnThing("monacleclarity");

				RetrieveMonocle(starterGuid);
			}
			else
			{
				// make him attackable
				_LeComte.ClearActorFlags(THING_AF_TALKTO);
				_LeComte.ClearActorFlags(THING_AF_NEUTRAL);
				_LeComte.ClearActorFlags(THING_AF_INVUL);
				_LeComte.ClearActorFlags(THING_AF_AIPAUSED);

				// set chronicle flag to indicate lecomte has entered 'attackable mode'
				CodexSequence.SetChronicleFlag(chronScript.LECOMTE_ATTACKABLE);
			}
			////////////////////////////////////////////////////////////////////
		}

		if(bLeComteRetConversation)
		{
			AIOn();
			bLeComteRetConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bRagwickConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDRagwick.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, ragwickGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

/*						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bRagwickRetConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDRagwick.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, ragwickGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bLeComte1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDLeComte.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, lecomteGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							_LeComte.LookAtThing(starterGuid);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
/*
						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bLeComteRetConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDLeComte.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, lecomteGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void RetrieveMonocle(int clientGuid)
	{
		chronScript.Step(chronScript.LECOMTE_MONOCLERETRIEVED);
		//CodexSequence.SetChronicleFlag(chronScript.LECOMTE_MONOCLERETRIEVED);

		//CodexQuest q = new CodexQuest(CodexQuest.Load("CTD_RetrieveMonocle"));
		//q.Complete();

		// check if the ST is handling advancement, if not auto-advance
		if(!NetIsNoAutoAdvance())
		{
			CodexSequence.Advance(0);
		}	

		//CodexQuest q2 = new CodexQuest(CodexQuest.Load("CTD_ReturnToMelmoth"));

		// hide ragwick after monocle is retrieved
		_Ragwick.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		_Ragwick.SetCollideType(THING_COLLIDE_NONE);
		_Ragwick.SetActorFlags(THING_AF_AIPAUSED);
	}
}

