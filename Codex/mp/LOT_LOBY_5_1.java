/**
 * Barclay Lobby LOT 5.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_LOBY_5_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	public static final int TIMER_ID_ACCUSATIONCONV	= 1;
	public static final int TIMER_ID_SUNROOM		= 2;

	private CodexActor		_Johnston;

	private float[]			pos;

	public boolean			bAccusationConversation = false;

	public static String _params[] = {"Johnston"};

	public LOT_LOBY_5_1(CodexActor Johnston)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_Johnston = new CodexActor(Johnston.GetGUID());

		CaptureThing(_Johnston.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		//_Johnston = new CodexActor(CodexThing.GuidFromCastID("Johnston"));

		//CaptureThing(_Johnston.GetGUID());

		if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_SCAPEGOATS_JUMP))
		{
			chronScript.Step(chronScript.LOBY_SCAPEGOATS_JUMP);
			//CodexSequence.SetChronicleFlag(chronScript.LOBY_SCAPEGOATS);

			// teleport the party into the boardroom
			//CodexPlayer.TeleportPartyToConversation(0);
			CodexSequence.Jump("BarclayLobby", 6);
		}
		else if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_SCAPEGOATS))
		{
			chronScript.Step(chronScript.LOBY_SCAPEGOATS);

			CodexThing initiatorThing = new CodexThing(clientGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "MICHAELA" + "%t" + "BEGINSCENE" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				SetTimer(1.5f, clientGuid, TIMER_ID_ACCUSATIONCONV);
			}
			else
			{
				chronScript.Step(chronScript.LOBY_SCAPEGOATS);
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
			case TIMER_ID_ACCUSATIONCONV:
				AccusationConversation(timerID, _Johnston.GetGUID());
				break;
			case TIMER_ID_SUNROOM:

				// teleport party to factory sun room - '10' is the entrance in factory4 for the sunroom

				chronScript.Step(chronScript.LOBY_JUMPTOSUNROOM);
				//CodexSequence.Jump("Factory4", 10);
				break;
		}
	}

	public void AccusationConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "5_1_Accusation", "5_1_Accusation.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bAccusationConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bAccusationConversation)
		{
			AIOn();
			bAccusationConversation = false;
			CodexCamera.Release(starterGuid);

			// at convended, teleport party to factory sun room
			SetTimer(2, starterGuid, TIMER_ID_SUNROOM);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bAccusationConversation)
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
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 7:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave2.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);

							// at some point in this conversation johnston storms in
							pos = _Johnston.GetFramePosition(1);
							_Johnston.SendActorToPos(pos, (float)90.0);

							break;

						case 8:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
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

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave1.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 14:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 17:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTConclave2.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 18:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

