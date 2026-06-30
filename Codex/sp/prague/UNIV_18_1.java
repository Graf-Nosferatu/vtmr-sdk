/**
 * University 18.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_18_1 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor		_Ecaterina;

	private CodexActor		_Wilhem;
	private CodexActor		_Erik;
	private CodexActor		_Serena;

	private int				christofGUID;
	private int				ecaterinaGUID;
	private int				erikGUID;
	private	int				serenaGUID;
	private int				wilhemGUID;

	private int				humanityTrack = 0;

	private boolean			bEcatConversation	= false;
	public boolean			bEcatReturn1Conversation	= false;

	public static String _params[] = {"Ecaterina"};

	public UNIV_18_1(CodexActor Ecaterina)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Ecaterina = new CodexActor(Ecaterina.GetGUID());

		CaptureThing(_Ecaterina.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_Wilhem = new CodexPlayer(CodexThing.GuidFromCastID("wilhem"));
		_Erik = new CodexPlayer(CodexThing.GuidFromCastID("erik"));
		_Serena = new CodexPlayer(CodexThing.GuidFromCastID("serena"));

		christofGUID = CodexThing.GuidFromCastID("Christof");
		ecaterinaGUID = _Ecaterina.GetGUID();
		erikGUID = _Erik.GetGUID();
		serenaGUID = _Serena.GetGUID();
		wilhemGUID = _Wilhem.GetGUID();
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ecaterina.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.UNIV_VIENNA))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_VIENNA);

			EcatConversation(clickerGuid, 0);
		}
		else
		{
			// play default line here
			EcatReturn1Conversation(clickerGuid, 0);
		}
	}

	public void EcatConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bEcatConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "18_1_Ecat", "18_1_Ecat.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EcatReturn1Conversation(int starterGuid, int npcGuid)
	{
		bEcatReturn1Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "8_1_EcatReturn1", "8_1_EcatReturn1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bEcatConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bEcatConversation = false;
			CodexCamera.Release(starterGuid);

			// leave for vienna quest
			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_LeaveVienna"));

			//!!!!!!!!!!! CHANGE SCENE IN OLD TOWN FOR FINAL PART OF 18_1 CONVERSATION - 18_1_LEAVE
			CodexSequence.ChangeScene("OldTown", "OLDT_18_1.nsd");

			// set this flag so the next time they click on the east gate
			// exit they will be taken to the vienna hub/chronicle
			CodexSequence.SetChronicleFlag(chronScript.OLDT_TOVIENNA);

			if(humanityTrack == -1)
			{
				// lose humanity here
				CodexActor Christof = new CodexActor(christofGUID);
				Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);
			}
		}

		if(bEcatReturn1Conversation)
		{
			AIOn();
			bEcatReturn1Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bEcatConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2	:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 3:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 4:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 5:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 6:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 7:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 8:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;
					}
					break;

				case 9:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 10:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 11:
					switch(curLine)
					{
						case 0:

							// award conversation XP
							CodexPlayer.AwardPartyExperience(100);

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 12:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 13:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;
				case 14:
					switch(curLine)
					{
						case 0:

							humanityTrack = -1;

							// set this so we know we took the derail jump when we get to old town
							CodexSequence.SetChronicleFlag(chronScript.UNIV_DERAIL);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;
					}
					break;
			} // switch(curEvent)
		}

		if(bEcatReturn1Conversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}

