/**
 * CTD Temesvar Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_Temesvar_Main extends Codex
{
	private MP_CTDChronicle	chronScript;

	private static final int TIMER_ID_REMOVEBEGGAR	= 1;

	private CodexActor		_Beggar;
	private CodexRegion		_BeggarRegion;
	private CodexActor		_Rebecca;
	private CodexActor		_MotherEglantine;
	private CodexActor		_Blazej;
	private CodexRegion		_EgBlazejRegion;
	private CodexActor		_Udolpho;

	private int				melmothGUID;
	private int				rebeccaGUID;
	private int				eglantineGUID;
	private int				blazejGUID;
	private int				udolphoGUID;

	public boolean			bMelmothConversation = false;
	public boolean			bRebeccaConversation = false;
	public boolean			bRebeccaRetConversation = false;
	public boolean			bEglanSher1Conversation = false;
	public boolean			bEglanSher2Conversation = false;
	public boolean			bSherRetConversation = false;
	public boolean			bEglanRetConversation = false;
	public boolean			bUldophoConversation = false;
	public boolean			bUldophoRetConversation = false;
	public boolean			bEglantineConversation = false;

	public static String _params[] = {"Beggar", "Beggar Region", "Rebecca", "Mother Eglantine", "Blazej", "Eglantine/Blazej conversation region", "Udolpho"};

	public CTD_Temesvar_Main(CodexActor Beggar, CodexRegion BeggarRegion, CodexActor Rebecca, CodexActor MotherEglantine, CodexActor Blazej, CodexRegion EgBlazejRegion, CodexActor Udolpho)
	{
		chronScript = (MP_CTDChronicle)GetChronicleScript(0);

		_Beggar = new CodexActor(Beggar.GetGUID());
		_BeggarRegion = new CodexRegion(BeggarRegion.GetGUID());
		_Rebecca = new CodexActor(Rebecca.GetGUID());
		_MotherEglantine = new CodexActor(MotherEglantine.GetGUID());
		_Blazej = new CodexActor(Blazej.GetGUID());
		_EgBlazejRegion = new CodexRegion(EgBlazejRegion.GetGUID());
		_Udolpho = new CodexActor(Udolpho.GetGUID());

		CaptureThing(_BeggarRegion.GetGUID());
		CaptureThing(_Rebecca.GetGUID());
		CaptureThing(_MotherEglantine.GetGUID());
		CaptureThing(_Blazej.GetGUID());
		CaptureThing(_EgBlazejRegion.GetGUID());
		CaptureThing(_Udolpho.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		melmothGUID = _Beggar.GetGUID();
		rebeccaGUID = _Rebecca.GetGUID();
		eglantineGUID = _MotherEglantine.GetGUID();
		blazejGUID = _Blazej.GetGUID();
		udolphoGUID = _Udolpho.GetGUID();
		
		_Beggar.SetActorFlags(THING_AF_AIPAUSED);
		_Beggar.SetActorFlags(THING_AF_INVUL);
		_Beggar.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		if(CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDBEGGAR))
		{
			// if the ST advanced to this scene, this will hide the beggar
			// when the players exit the inn
			_Beggar.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Beggar.SetCollideType(THING_COLLIDE_NONE);
			_Beggar.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		// this is set in CTD_ChambersSkulls
		if(CodexSequence.GetChronicleFlag(chronScript.CATACOMBS_INDUNGEON))
		{
			_Rebecca.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Rebecca.SetActorFlags(THING_AF_AIPAUSED);
			_Rebecca.SetCollideType(THING_COLLIDE_NONE);
			_Rebecca.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Rebecca.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "REBECCA" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDREBECCA))
			{
				CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_TALKEDREBECCA);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					RebeccaConversation(clickerGuid, 1);
				}
			}
			else
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					RebeccaRetConversation(clickerGuid, 1);
				}
			}
		}

		if(guid == _Udolpho.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "UDOLPHO" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDUDOLPHO))
			{
				CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_TALKEDUDOLPHO);
	
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					UldophoConversation(clickerGuid, 1);
				}
			}
			else
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					UldophoRetConversation(clickerGuid, 1);
				}
			}
		}

		if(guid == _MotherEglantine.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "EGLANTINE" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDREBECCA) &&
				!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_EGSECONDHALFDONE) &&
				!CodexSequence.GetChronicleFlag(chronScript.CATACOMBS_RELIQUARYRECOVERED))
			{
				CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_EGSECONDHALFDONE);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					EglanSher2Conversation(clickerGuid, 1);
				}
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.CATACOMBS_RELIQUARYRECOVERED) &&
				!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDRELIQUARY))
			{
				CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_TALKEDRELIQUARY);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					EglantineConversation(clickerGuid, 1);
				}
			}
			else
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					EglanRetConversation(clickerGuid, 1);
				}
			}
		}

		if(guid == _Blazej.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "BLAZEJ" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDREBECCA) &&
				!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_EGSECONDHALFDONE) &&
				!CodexSequence.GetChronicleFlag(chronScript.CATACOMBS_RELIQUARYRECOVERED))
			{
				CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_EGSECONDHALFDONE);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					EglanSher2Conversation(clickerGuid, 1);
				}
			}
			else
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					SherRetConversation(clickerGuid, 1);
				}
			}
		}

	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _BeggarRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDBEGGAR))
		{
			//chronScript.Step(chronScript.TEMESVAR_TALKEDBEGGAR);
			//CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_TALKEDBEGGAR);

			// close exit to catacombs
			//CodexSequence.CloseExit("Temesvar", 1);

			CodexThing initiatorThing = new CodexThing(causeGUID);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "MELMOTH" + "%t" + "ENTERED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				MelmothConversation(causeGUID, 1);
			}
			else
			{
				chronScript.Step(chronScript.TEMESVAR_TALKEDBEGGAR);
			}
		}

		if(guid == _EgBlazejRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDEGBLAZEJ))
		{
			CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_TALKEDEGBLAZEJ);

			CodexThing initiatorThing = new CodexThing(causeGUID);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "BLAZEJ" + "%t" + "ENTERED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				EglanSher1Conversation(causeGUID, 1);
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{	
		switch(timerID)
		{
			case TIMER_ID_REMOVEBEGGAR:

				_Beggar.SpawnThing("blueMagic");

				_Beggar.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_Beggar.SetCollideType(THING_COLLIDE_NONE);
				break;
		}
	}

	public void MelmothConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "1_1_Melmoth", "1_1_Melmoth.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bMelmothConversation = true;
			AIOff();
		}
	}

	public void RebeccaConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_Rebecca", "2_1_Rebecca.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bRebeccaConversation = true;
			AIOff();
		}
	}

	public void RebeccaRetConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_RebeccaRet", "2_1_RebeccaRet.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bRebeccaRetConversation = true;
			AIOff();
		}
	}

	public void EglanSher1Conversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_EglanSher1", "2_1_EglanSher1.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bEglanSher1Conversation = true;
			AIOff();
		}
	}

	public void EglanSher2Conversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_EglanSher2", "2_1_EglanSher2.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bEglanSher2Conversation = true;
			AIOff();
		}
	}

	public void SherRetConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_SherRet", "2_1_SherRet.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bSherRetConversation = true;
			AIOff();
		}
	}

	public void EglanRetConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_EglanRet", "2_1_EglanRet.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bEglanRetConversation = true;
			AIOff();
		}
	}

	public void UldophoConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_Uldopho", "2_1_Uldopho.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bUldophoConversation = true;
			AIOff();
		}
	}

	public void UldophoRetConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_UldophoRet", "2_1_UldophoRet.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bUldophoRetConversation = true;
			AIOff();
		}
	}

	public void EglantineConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "4_1_Eglantine", "4_1_Eglantine.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bEglantineConversation = true;
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

			chronScript.Step(chronScript.TEMESVAR_TALKEDBEGGAR);
			////////////////////////////////////////////////////////////////////
			// at convended, 'meet melmoth' added to quest log
			////////////////////////////////////////////////////////////////////
			//CodexQuest q = new CodexQuest(CodexQuest.Load("CTD_MeetMelmoth"));

			_Beggar.SetAlpha(.1f, 5000f);
			SetTimer(5, TIMER_ID_REMOVEBEGGAR);
		}
		else if(bRebeccaConversation)
		{
			AIOn();
			bRebeccaConversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bRebeccaRetConversation)
		{
			AIOn();
			bRebeccaRetConversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bEglanSher1Conversation)
		{
			bEglanSher1Conversation = false;

			////////////////////////////////////////////////////////////////////
			// at convended, check whether to play second half of conversation
			////////////////////////////////////////////////////////////////////
//			if(CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDREBECCA))
//			{
//				CodexSequence.SetChronicleFlag(chronScript.TEMESVAR_EGSECONDHALFDONE);
//
//				EglanSher2Conversation(starterGuid, 1);
//			}
//			else
//			{
				AIOn();
				CodexCamera.Release(starterGuid);
//			}
		}
		else if(bEglanSher2Conversation)
		{
			AIOn();
			bEglanSher2Conversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bEglanRetConversation)
		{
			AIOn();
			bEglanRetConversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bUldophoConversation)
		{
			AIOn();
			bUldophoConversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bSherRetConversation)
		{
			AIOn();
			bSherRetConversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bUldophoRetConversation)
		{
			AIOn();
			bUldophoRetConversation = false;
			CodexCamera.Release(starterGuid);
		}
		else if(bEglantineConversation)
		{
			AIOn();
			bEglantineConversation = false;
			CodexCamera.Release(starterGuid);

			if(returnValue != 1)
			{
				// give humanity here because they choose to return the reliquary
				CodexActor actor = new CodexPlayer(starterGuid);
				actor.AddActorEffectByLevel("ef_increasehumanity", 0, 1, 0, 0);
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

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDBeggar.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, melmothGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

/*						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bRebeccaConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDRebecca.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, rebeccaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

/*						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}

		if(bRebeccaRetConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDRebecca.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, rebeccaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bEglanSher1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, eglantineGUID, blazejGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriffWatch.ncp", 30);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriffWatch.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, blazejGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, eglantineGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							//CodexCamera.SetupCutscene(starterGuid, starterGuid, blazejGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bEglanSher2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, eglantineGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

/*						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, starterGuid, blazejGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bUldophoConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDUdolpho.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, udolphoGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

/*						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bSherRetConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, blazejGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bEglanRetConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, eglantineGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bUldophoRetConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDUdolpho.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, udolphoGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bEglantineConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, eglantineGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, eglantineGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void convchosen(int starterGuid, int eventNum, int lineChosen, int lineDuration, int speakerGuid)
	{
		if(bEglantineConversation)
		{
			CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDEglanSheriff.ncp", 30);
		}
	}
}

