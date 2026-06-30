/**
 * University 14.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_14_1 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexRegion		_EcaterinaRegion;
	private CodexActor		_Ecaterina;
	private CodexActor		_Cosmas;
	private CodexThing		_anezkaLetterSpot;

	private CodexActor		_Christof;

	private int				christofGUID;
	private int				cosmasGUID;
	private int				ecaterinaGUID;

	private boolean			bMissingConversation = false;
	private boolean			bMissing2Conversation = false;

	public static String _params[] = {"Ecaterina region", "Ecaterina", "Cosmas", "Anezka Letter spot"};

	public UNIV_14_1(CodexRegion EcaterinaRegion, CodexActor Ecaterina, CodexActor Cosmas, CodexThing anezkaLetterSpot)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_EcaterinaRegion = new CodexRegion(EcaterinaRegion.GetGUID());
		_Ecaterina = new CodexActor(Ecaterina.GetGUID());
		_Cosmas = new CodexActor(Cosmas.GetGUID());
		_anezkaLetterSpot = new CodexThing(anezkaLetterSpot.GetGUID());

		CaptureThing(_EcaterinaRegion.GetGUID());
		CaptureThing(_Ecaterina.GetGUID());
		CaptureThing(_Cosmas.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		cosmasGUID = _Cosmas.GetGUID();
		ecaterinaGUID = _Ecaterina.GetGUID();

		// Fade in
		CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)2.0, false);

		// SHOW WILHEM AND SERENA (IF THEY"RE NOT DEAD) FROM ANEZKA ROOM SCENE (ANZK_14_1)
		CodexPlayer Wilhem = new CodexPlayer(CodexThing.GuidFromCastID("Wilhem"));
		if((Wilhem.GetActorFlags() & THING_AF_DEAD) == 0)
			Wilhem.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

		CodexPlayer Serena = new CodexPlayer(CodexThing.GuidFromCastID("Serena"));
		if((Serena.GetActorFlags() & THING_AF_DEAD) == 0)
			Serena.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.UNIV_ECATERINAREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_ECATERINAREGION);

			MissingConversation(christofGUID, 0);
		}
	}

	public void textended(int readerGuid)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.UNIV_READLETTER))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_READLETTER);

			Missing2Conversation(readerGuid, 0);	
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexSequence.Jump("PrinceBrandl", 1);
	}

	public void MissingConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bMissingConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "14_1_Missing", "14_1_Missing.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Missing2Conversation(int starterGuid, int npcGuid)
	{
		bMissing2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "14_1_Missing2", "14_1_Missing2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMissingConversation)
		{
			AIOn();
			bMissingConversation = false;
			CodexCamera.Release(starterGuid);

			// spawn the letter in and show it  anezka in read interface
			_anezkaLetterSpot.SpawnThing("AnezkaLetter_2");

			ExecuteText(starterGuid, 0, "AnezkaLetter2");
		}

		if(bMissing2Conversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bMissing2Conversation = false;
			//CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// open link to prague castle
			CodexSequence.OpenExit("JudithBridge", 2);
			CodexSequence.OpenExit("PragueCastle", 0);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			SetTimer(2.2f);		
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMissingConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 7:

					CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bMissing2Conversation)
		{
			switch(curLine)
			{

				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					_Cosmas.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					_Cosmas.StopActorAction();
					_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					_Christof.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					_Christof.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}

