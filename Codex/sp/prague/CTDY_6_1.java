/**
 * Convent, scene 6.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTDY_6_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_ANEZKA	= 1;
	private static final int TIMER_ID_PLAYVIDEO	= 2;
	private static final int TIMER_ID_ENDSCENE	= 3;

	private CodexActor		_Christof;
	private CodexActor		_Anezka;
	private CodexActor		_ArchBishop;
	private CodexRegion		_BishopRegion;

	private int				christofGUID;
	private int				anezkaGUID;
	private int				archbishopGUID;

	private boolean			bAnezkaConversation = false;
	public boolean			bAnezkaReturnConversation = false;
	private boolean			bNightConversation = false;

	public static String _params[] = {"Anezka", "ArchBishop", "Bishop Region"};

	public CTDY_6_1(CodexActor Anezka, CodexActor ArchBishop, CodexRegion BishopRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Anezka = new CodexActor(Anezka.GetGUID());
		_ArchBishop = new CodexActor(ArchBishop.GetGUID());
		_BishopRegion = new CodexRegion(BishopRegion.GetGUID());

		CaptureThing(_Anezka.GetGUID());
		CaptureThing(_BishopRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		anezkaGUID = _Anezka.GetGUID();
		archbishopGUID = _ArchBishop.GetGUID();

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.CTDY_AFTERMINES))
		{
			CodexSequence.SetChronicleFlag(chronScript.CTDY_AFTERMINES);

			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)2.0, false);

			SetTimer((float)0.1, TIMER_ID_ANEZKA, christofGUID);

			// so they can't exit the convent day anymore
			CodexSequence.CloseExit("ConventDay", 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(guid == _BishopRegion.GetGUID())
		{
			NightConversation(causeGUID, 0);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Anezka.GetGUID())
		{
			// default lines here
			AnezkaReturnConversation(clickerGuid, 0);
		}
	}

	public void videoended(int id)
	{
		SetTimer(1, TIMER_ID_ENDSCENE);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ANEZKA:
				AnezkaConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_PLAYVIDEO:
				// play sunset video
				PlayVideo("Sunset.bik");
				//SetTimer(0, TIMER_ID_ENDSCENE);
				break;

			case TIMER_ID_ENDSCENE:
				// change the University and St. Thomas exits to take the player
				// to the night version of Old Town, and add the backward links
				CodexSequence.ChangeExit("University", 0, "OldTown", 2);
				CodexSequence.ChangeExit("StThomas", 0, "OldTown", 5);
				CodexSequence.ChangeExit("OldTown", 2, "University", 0);
				CodexSequence.ChangeExit("OldTown", 5, "StThomas", 0);
				// change the petrin hill, monastery. judith bridge, and prague castle exits);
				CodexSequence.ChangeExit("Monastery", 0, "PetrinHill", 1);
				CodexSequence.ChangeExit("PetrinHill", 1, "Monastery", 0);
				CodexSequence.ChangeExit("PragueCastle", 0, "JudithBridge", 2);
				CodexSequence.ChangeExit("JudithBridge", 2, "PragueCastle", 0);

				// close the university, convent, haven, petrin hill, 
				// north quarter, prague castle, and chantry exits
				CodexSequence.CloseExit("OldTown", 1);
				CodexSequence.CloseExit("OldTown", 2);
				CodexSequence.CloseExit("OldTown", 4);
				CodexSequence.CloseExit("OldTown", 11);
				CodexSequence.CloseExit("PetrinHill", 1);
				CodexSequence.CloseExit("JudithBridge", 2);
				CodexSequence.CloseExit("GoldenLane", 2);
				CodexSequence.CloseExit("GoldenLane", 3);

				CodexSequence.CloseExit("JudithBridge", 2);
				CodexSequence.CloseExit("PetrinHill", 1);

				CodexSequence.ChangeScene("OldTown", "OLDT_7_1.nsd");
				CodexSequence.Jump("OldTown", 4);
				break;
		}
	}

	public void AnezkaConversation(int starterGuid, int npcGuid)
	{
		bAnezkaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_Anezka", "6_1_Anezka.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AnezkaReturnConversation(int starterGuid, int npcGuid)
	{
		bAnezkaReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_AnezkaHeal", "4_1_AnezkaHeal.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void NightConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bNightConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "6_1_Geza2", "6_1_Geza2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bAnezkaConversation)
		{
			AIOn();
			bAnezkaConversation = false;
			_Christof.StopActorAction();
			_Christof.CancelOverrideActorWeapon();
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_AnezkaVisit"));
			q.Complete();

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bAnezkaReturnConversation)
		{
			AIOn();
			bAnezkaReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bNightConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bNightConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_PragueByNight"));

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			SetTimer(3, TIMER_ID_PLAYVIDEO);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bAnezkaConversation)
		{
			switch(curLine)
			{
				case 0:

					_Christof.PlayMotionSetMode(MOTION_GESTURE4, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
					break;

				case 1:
					
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
					break;

				case 2:

					_Christof.StopActorAction();
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, anezkaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					_Anezka.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
					break;

				case 7:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
					break;

				case 8:

					_Christof.OverrideActorWeapon("weapLocket", false, false);
					_Christof.PlayMotionSetMode(MOTION_SPECIAL18, false, (float)30.0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

			}
		} // if(bAnezkaConversation)

		if(bAnezkaReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bNightConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							_Christof.LookAtThing(archbishopGUID);
							_ArchBishop.LookAtThing(christofGUID);
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

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;
				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
			} // switch(curEvent)
		} // if(bNightConversation)
	}
}
