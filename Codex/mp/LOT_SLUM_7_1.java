/**
 * Slum Room LOT 7.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_SLUM_7_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	private static final int	TIMER_ID_TRIPLETDOMINIC			= 1;
	private static final int	TIMER_ID_TRIPLETCONVERSATION	= 2;

	private CodexActor		_Dominic;
	private CodexActor		_Kazi;
	private CodexActor		_Teta;
	private CodexActor		_Zil;

	private float[]			pos;

	public boolean			bDomTripConversation = false;
	public boolean			bThreeSisConversation = false;
	public boolean			bBargainConversation = false;
	public boolean			bGivePactConversation = false;

	public static String _params[] = {"Dominic"};

	public LOT_SLUM_7_1(CodexActor Dominic)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Dominic = new CodexActor(Dominic.GetGUID());

		CaptureThing(_Dominic.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		//_Dominic = new CodexActor(CodexThing.GuidFromCastID("Dominic"));
		_Kazi = new CodexActor(CodexThing.GuidFromCastID("Kazi"));
		_Zil = new CodexActor(CodexThing.GuidFromCastID("Zil"));
		_Teta = new CodexActor(CodexThing.GuidFromCastID("Teta"));

		//CaptureThing(_Dominic.GetGUID());
		CaptureThing(_Kazi.GetGUID());
		CaptureThing(_Zil.GetGUID());
		CaptureThing(_Teta.GetGUID());

		if(!CodexSequence.GetChronicleFlag(chronScript.SLUM_DOMINIC))
		{
			chronScript.Step(chronScript.SLUM_DOMINIC);
			//chronScript.Step(chronScript.UTWN_CHANGESCENE_9_1);
			//CodexSequence.SetChronicleFlag(chronScript.SLUM_DOMINIC);

			CodexThing initiatorThing = new CodexThing(clientGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "DOMINIC" + "%t" + "BEGINSCENE" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			SetTimer(1, clientGuid, TIMER_ID_TRIPLETDOMINIC);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// NOTE: I'm using the timerID as the clientGuid that is passed into this timer
		// because clientGuids can be a large enough int that a float won't be precise
		// enough to be accurate - the first float parm is being used as the switch, so
		// a call to this timer would look like: SetTimer(1, clientGuid, TIMER_ID)

		// cast the first parm as an int so we can switch on it
		int intSwitch = (int)arg0;

		switch(intSwitch)
		{
			case TIMER_ID_TRIPLETDOMINIC:
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					// so they can't all bolt mid-conversation/mid-scenes
					CodexSequence.CloseExit("SlumRoom", 0);

					DomTripConversation(timerID, _Kazi.GetGUID());
				}
				break;
			case TIMER_ID_TRIPLETCONVERSATION:
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					ThreeSisConversation(timerID, _Kazi.GetGUID());
				}
				break;
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Kazi.GetGUID() || 
			guid == _Zil.GetGUID() || 
			guid == _Teta.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "KAZI" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			if(CodexSequence.GetChronicleFlag(chronScript.WAR_FOUNDPACT))
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					GivePactConversation(clickerGuid, guid);
				}
			}	
			else if(CodexSequence.GetChronicleFlag(chronScript.UTWN_TALKEDBILL))
			{
				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					BargainConversation(clickerGuid, guid);
				}
			}
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _Dominic.GetGUID())
		{
			_Dominic.Remove();
		}
	}

	public void DomTripConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "7_1_DomTrip", "7_1_DomTrip.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bDomTripConversation = true;
			AIOff();
		}
	}

	public void ThreeSisConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "8_1_ThreeSis", "8_1_ThreeSis.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bThreeSisConversation = true;
			AIOff();
		}
	}

	public void BargainConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "10_1_Bargain", "10_1_Bargain.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bBargainConversation = true;
			AIOff();
		}
	}

	public void GivePactConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "12_1_GivePact", "12_1_GivePact.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bGivePactConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bDomTripConversation)
		{
			AIOn();
			bDomTripConversation = false;
			CodexCamera.Release(starterGuid);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_FindDominic"));
			//q.Complete();

			/////////////////////////////////////////////////////////////////
			// at convended dominic leaves
			/////////////////////////////////////////////////////////////////
			pos = _Dominic.GetFramePosition(1);
			_Dominic.SendActorToPos(pos, (float)90.0);

			CodexThing initiatorThing = new CodexThing(starterGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "KAZI" + "%t" + "CONVENDED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// start triplets first conversation in a few seconds
			SetTimer(3, starterGuid, TIMER_ID_TRIPLETCONVERSATION);
		}

		if(bThreeSisConversation)
		{
			AIOn();
			bThreeSisConversation = false;
			CodexCamera.Release(starterGuid);

			// so they can get back out again
			CodexSequence.OpenExit("SlumRoom", 0);

			//CodexQuest q2 = new CodexQuest(CodexQuest.Load("LOT_BloodPact"));

			// change scene uptown to prepare for fight scene
			//CodexSequence.ChangeScene("NewYorkUptown", "UTWN_LOT_9_1.nsd");
		}

		if(bBargainConversation)
		{
			AIOn();
			bBargainConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bGivePactConversation)
		{
			AIOn();
			bGivePactConversation = false;
			CodexCamera.Release(starterGuid);

			if(returnValue != 1)
			{
				// they can either give the triplets the pact or keep it, a returnValue
				// of 1 means they kept it, so if it's not 1, clear this flag
				CodexSequence.ClearChronicleFlag(chronScript.WAR_KEEPPACT);
			}
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bDomTripConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTDomTrip.ncp", 30);
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
*/
						case 4:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 5:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTDomTrip.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
/*
						case 8:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bThreeSisConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTSlumTriplets.ncp", 30);
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
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bBargainConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTSlumTriplets.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bGivePactConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTSlumTriplets.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:
							
							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTSlumTriplets.ncp", 30);
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
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

