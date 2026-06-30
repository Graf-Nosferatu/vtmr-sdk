/**
 * University scene 7.7 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_7_7 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_SUBMISSIONCONV1		= 1;
	private static final int TIMER_ID_SUBMISSIONCONV2		= 2;
	private static final int TIMER_ID_WILHEMINTERVENE		= 5;
	private static final int TIMER_ID_MOVECHRISWILHEM		= 6;

	private CodexActor		_Christof;
	private	CodexActor		_Ecaterina;
	private	CodexActor		_Cosmas;
	private	CodexPlayer		_Wilhem;

	private int				christofGUID;
	private int				cosmasGUID;
	private int				ecaterinaGUID;
	private int				wilhemGUID;

	private float[]			pos;

	private boolean			bSubmissionConversation1	= false;
	private boolean			bSubmissionConversation2	= false;
	public boolean			bCosmasReturnConversation	= false;
	public boolean			bEcatReturn1Conversation	= false;

	public static String _params[] = {"Ecaterina", "Cosmas", "Wilhem"};

	public UNIV_7_7(CodexActor Ecaterina, CodexActor Cosmas, CodexPlayer Wilhem)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Ecaterina = new CodexActor(Ecaterina.GetGUID());
		_Cosmas = new CodexActor(Cosmas.GetGUID());
		_Wilhem = new CodexPlayer(Wilhem.GetGUID());

		CaptureThing(_Ecaterina.GetGUID());
		CaptureThing(_Cosmas.GetGUID());
		CaptureThing(_Wilhem.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// change haven to an "empty" scene
		CodexSequence.ChangeScene("Haven", "HAVN_7_7.nsd");

		// open the university, haven, and petrin hill exits
		CodexSequence.OpenExit("OldTown", 1);
		CodexSequence.OpenExit("OldTown", 2);
		
		// open monastery exits
		CodexSequence.OpenExit("PetrinHill", 1);
		CodexSequence.OpenExit("Monastery", 0);

		christofGUID = CodexThing.GuidFromCastID("Christof");
		cosmasGUID = _Cosmas.GetGUID();
		ecaterinaGUID = _Ecaterina.GetGUID();
		wilhemGUID = _Wilhem.GetGUID();

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.UNIV_CONVDONE))
		{
			// Fade in from awakening scene
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
			SetTimer((float)0.1, TIMER_ID_SUBMISSIONCONV1);
		}
	}


	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ecaterina.GetGUID())
		{
			// play default line here
			EcatReturn1Conversation(clickerGuid, 0);
		}
		else if(guid == _Cosmas.GetGUID())
		{
			// play default line here
			CosmasReturnConversation(clickerGuid, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_SUBMISSIONCONV1:

				SubmissionConversation1(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_SUBMISSIONCONV2:

				SubmissionConversation2(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_WILHEMINTERVENE:

				pos = _Wilhem.GetFramePosition(1);
				_Wilhem.SendActorToPos(pos, (float)90.0);
				break;

			case TIMER_ID_MOVECHRISWILHEM:

				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
				_Wilhem.SetPosition(_Wilhem.GetFramePosition(1));
				_Wilhem.LookAtThing(ecaterinaGUID);
				break;
		}
	}

	public void SubmissionConversation1(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bSubmissionConversation1 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "7_7_Fealty", "7_7_Fealty.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void SubmissionConversation2(int starterGuid, int npcGuid)
	{
		bSubmissionConversation2 = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "7_7_Fealty2", "7_7_Fealty2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EcatReturn1Conversation(int starterGuid, int npcGuid)
	{
		bEcatReturn1Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "8_1_EcatReturn1", "8_1_EcatReturn1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void CosmasReturnConversation(int starterGuid, int npcGuid)
	{
		bCosmasReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "7_7_CosmasReturn", "7_7_CosmasReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bSubmissionConversation1)
		{
			//AIOn();
			bSubmissionConversation1 = false;
			//CodexCamera.Release(starterGuid);

			if(CodexSequence.GetChronicleFlag(chronScript.UNIV_SHUNANSWER))
			{
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)4.0, true);
				SetTimer(2, TIMER_ID_MOVECHRISWILHEM);
				SetTimer(4, TIMER_ID_SUBMISSIONCONV2);
			}
			else
			{
				SetTimer(0, TIMER_ID_SUBMISSIONCONV2);
			}
		}

		if(bSubmissionConversation2)
		{
			CodexSound.PopMusic();
			AIOn();
			bSubmissionConversation2 = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// NOTE: do NOT get rid of this flag, it's checked in a couple other scripts
			CodexSequence.SetChronicleFlag(chronScript.UNIV_CONVDONE);

			// Add Wilhem to the party
			_Wilhem.AddToParty();

			// add nod fragment quest
			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_NodFragment"));

			// show petrin on map
			CodexSequence.SetLocationFlags("PetrinHill", LOCATION_FLAG_SHOWINMAP);

			// set up scene in old town
			CodexSequence.ChangeScene("OldTown", "OLDT_7_7.nsd");

			// change scene in petrin for this mission
			CodexSequence.ChangeScene("PetrinHill", "HILL_8_1.nsd");
		}

		if(bEcatReturn1Conversation)
		{
			AIOn();
			bEcatReturn1Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bCosmasReturnConversation)
		{
			AIOn();
			bCosmasReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bSubmissionConversation1)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Ecaterina.LookAtThing(christofGUID);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Fealty.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Fealty.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 2:

					switch(curLine)

					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Fealty.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 3:

					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Fealty.ncp", 30);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;
					}

					break;

				case 4:

					switch(curLine)

					{
						case 0:

							// set this so we know chris has chosen to shun ecaterina
							CodexSequence.SetChronicleFlag(chronScript.UNIV_SHUNANSWER);

							// move wilhem to "catch" chris
							pos = _Wilhem.GetFramePosition(4);
							_Christof.SendActorToPos(pos, (float)210.0);

							pos = _Wilhem.GetFramePosition(3);
							_Wilhem.SendActorToPos(pos, (float)210.0);

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UnivBalcony.ncp", 30);
							break;

						case 1:

							_Wilhem.LookAtThing(christofGUID);
							_Christof.LookAtThing(wilhemGUID);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}

					break;

			} // switch(curEvent)

		} // if(bSubmissionConversation1)

		if(bSubmissionConversation2)
		{

			switch(curLine)
			{
				case 0:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Fealty.ncp", 30);
					// Set timer to move Wilhem near end of Ecat line
					SetTimer((float)3.0, TIMER_ID_WILHEMINTERVENE);
					break;

				case 1:

					_Wilhem.LookAtThing(ecaterinaGUID);
					_Wilhem.PlayMotionSetMode(MOTION_GESTURE4, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 2:

					_Wilhem.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 5:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
					break;

				case 7:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UnivBalcony.ncp", 30);
					// Move Wilhem near Christof
					pos = _Wilhem.GetFramePosition(2);
					_Wilhem.SendActorToPos(pos, (float)90.0);
					break;

				case 8:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
					break;
			}

		} // if(bSubmissionConversation2)

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

		if(bCosmasReturnConversation)
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
