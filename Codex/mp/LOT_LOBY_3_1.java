/**
 * Barclay Lobby LOT 3.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_LOBY_3_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	public static final int TIMER_ID_CONCLAVECONV	= 1;

	private CodexActor		_Mirabilis;
	private CodexActor		_Johnston;

	private float[]			pos;

	public boolean			bConclaveConversation = false;

	public static String _params[] = {"Mirabilis", "Johnston"};

	public LOT_LOBY_3_1(CodexActor Mirabilis, CodexActor Johnston)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Mirabilis = new CodexActor(Mirabilis.GetGUID());
		_Johnston = new CodexActor(Johnston.GetGUID());

		CaptureThing(_Mirabilis.GetGUID());
		CaptureThing(_Johnston.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		CodexActor luke = new CodexActor(CodexThing.GuidFromCastID("Luke"));
		CodexActor michaela = new CodexActor(CodexThing.GuidFromCastID("Michaela"));
		CodexActor heavyg = new CodexActor(CodexThing.GuidFromCastID("HeavyG"));
		CodexActor arianna = new CodexActor(CodexThing.GuidFromCastID("Arianna"));

		luke.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		michaela.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		heavyg.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		arianna.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Mirabilis.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Johnston.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		// temp so he doesn't kill us
		_Mirabilis.SetActorFlags(THING_AF_AIPAUSED);

		if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_CONCLAVE_JUMP))
		{
			chronScript.Step(chronScript.LOBY_CONCLAVE_JUMP);

			// take them right into the board room
			//CodexPlayer.TeleportPartyToConversation(0);
			CodexSequence.Jump("BarclayLobby", 6);
		}
		else if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_CONCLAVE))
		{
			//CodexSequence.SetChronicleFlag(chronScript.LOBY_CONCLAVE);

			CodexThing initiatorThing = new CodexThing(clientGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "MICHAELA" + "%t" + "BEGINSCENE" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_ReturnToHaven"));

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				SetTimer(1.5f, clientGuid, TIMER_ID_CONCLAVECONV);
			}
			else
			{
				chronScript.Step(chronScript.LOBY_CONCLAVE);
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// NOTE: I'm using the timerID as the clientGuid that is passed into this timer
		// because clientGuids can be a large enough int that a float won't be precise
		// enough to be accurate - the first float parm is being used as the switch, so
		// a call to this timer would look like: SetTimer(1, clientGuid, TIMER_ID)

		int intSwitch = (int)arg0;

		switch(intSwitch)
		{
			case TIMER_ID_CONCLAVECONV:
				ConclaveConversation(timerID, _Mirabilis.GetGUID());
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _Mirabilis.GetGUID() ||
			thingGuid == _Johnston.GetGUID())
		{
			_Mirabilis.Remove();
			_Johnston.Remove();
		}
	}

	public void ConclaveConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "3_1_Conclave", "3_1_Conclave.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bConclaveConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bConclaveConversation)
		{
			AIOn();
			bConclaveConversation = false;
			CodexCamera.Release(starterGuid);

			chronScript.Step(chronScript.LOBY_CONCLAVE);

			// the party is moved out to the hallway outside the boardroom?
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bConclaveConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
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
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
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

							/////////////////////////////////////////////////////////////
							// at the appropriate time, mirabilis and johnston walk out
							/////////////////////////////////////////////////////////////
							pos = _Mirabilis.GetFramePosition(1);
							_Mirabilis.SendActorToPos(pos, (float)90.0);

							pos = _Johnston.GetFramePosition(1);
							_Johnston.SendActorToPos(pos, (float)90.0);
							/////////////////////////////////////////////////////////////

							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 12:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 13:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

