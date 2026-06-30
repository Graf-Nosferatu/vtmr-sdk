/**
 * Northen Quarter 11.1 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class NQTR_11_1 extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexActor		_Mendel;
	private	CodexRegion		_MendelRegion;
	private	CodexActor		_Golem;
	private CodexRegion		_GolemRegion;
	private	CodexThing		_DebrisTrigger;
	private	CodexActor		_Josef;
	private	CodexRegion		_JosefRegion;
	private CodexActor		_RunPed1;

	private CodexActor		_Christof;
	private CodexActor		_Wilhem;
	
	private int				christofGUID;
	private int				josefGUID;
	private int				mendelGUID;
	private int				wilhemGUID;

	private boolean			bMendelConversation1	= false;
	private boolean			bMendelConversation2	= false;
	private boolean			bJosefConversation1		= false;
	private boolean			bJosefConversation2		= false;
	private boolean			bJosefConversation3		= false;
	private boolean			bMendel1ReturnConversation = false;
	private boolean			bTooLateConversation    = false;

	private static final int	TIMER_ID_GOLEM1 = 10;
	private static final int	TIMER_ID_GOLEM2 = 11;
	private static final int	TIMER_ID_GOLEM3 = 12;
	private static final int	TIMER_ID_GOLEM4 = 13;
	
	public static String _params[] = {"Mendel", "Mendel region", "Golem", "Golem Region", "Debris Trigger", "Josef", "Josef region", "Running Ped 1"};

	public NQTR_11_1(CodexActor Mendel, CodexRegion MendelRegion, CodexActor Golem, CodexRegion GolemRegion, CodexThing DebrisTrigger, CodexActor Josef, CodexRegion JosefRegion, CodexActor RunPed1)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Mendel = new CodexActor(Mendel.GetGUID());
		_MendelRegion = new CodexRegion(MendelRegion.GetGUID());
		_Golem = new CodexActor(Golem.GetGUID());
		_GolemRegion = new CodexRegion(GolemRegion.GetGUID());
		_DebrisTrigger = new CodexThing(DebrisTrigger.GetGUID());
		_Josef = new CodexActor(Josef.GetGUID());
		_JosefRegion = new CodexRegion(JosefRegion.GetGUID());
		_RunPed1 = new CodexActor(RunPed1.GetGUID());

		CaptureThing(_Mendel.GetGUID());
		CaptureThing(_MendelRegion.GetGUID());
		CaptureThing(_Golem.GetGUID());
		CaptureThing(_GolemRegion.GetGUID());
		CaptureThing(_DebrisTrigger.GetGUID());
		CaptureThing(_Josef.GetGUID());
		CaptureThing(_JosefRegion.GetGUID());
		CaptureThing(_RunPed1.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		josefGUID = _Josef.GetGUID();
		mendelGUID = _Mendel.GetGUID();
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_Christof = new CodexActor(christofGUID);
		_Wilhem = new CodexActor(wilhemGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFREGION))
		{
			_Josef.SetAlpha(0);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.OLDT_TOOLATE) && !CodexSequence.GetChronicleFlag(chronScript.NQTR_TOOLATE))
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_TOOLATE);
			TooLateConversation(clientGuid, 0);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Josef.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFREGION))
		{
			JosefConversation3(clickerGuid, 0);
		}

		if(guid == _Mendel.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.NQTR_SHEMRECOVERED))
		{
			Mendel1ReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _JosefRegion.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.NQTR_GOLEMDEAD) &&
			!CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_JOSEFREGION);

			// show tunnels on map
			CodexSequence.SetLocationFlags("JosefTunnels", LOCATION_FLAG_SHOWINMAP);

			JosefConversation1(causeGUID, 0);
		}

		if(guid == _MendelRegion.GetGUID())
		{
			if(guid == _MendelRegion.GetGUID() &&
				!CodexSequence.GetChronicleFlag(chronScript.NQTR_MENDELREGION))
			{
				CodexSequence.SetChronicleFlag(chronScript.NQTR_MENDELREGION);

				MendelConversation1(causeGUID, 0);
			}

			if(CodexSequence.GetChronicleFlag(chronScript.NQTR_SHEMRECOVERED) &&
				!CodexSequence.SetChronicleFlag(chronScript.NQTR_MENDELREGION2))
			{
				CodexSequence.SetChronicleFlag(chronScript.NQTR_MENDELREGION2);

				CodexSound.PushMusic("DA_Conversation_1.wav", 80);

				MendelConversation2(causeGUID, 0);
			}
		}

		if(guid == _GolemRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.NQTR_GOLEMSCENE))
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_GOLEMSCENE);

			GolemCutscene(causeGUID);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// make sure the golem was killed
		if(guid == _Golem.GetGUID())
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_GOLEMDEAD);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_Golem"));
			q.Complete();
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case 1:
				//Candle Flicker Sequence Here
				break;

			case 2:
				JosefConversation2((int)arg0, 0);
				break;

			case TIMER_ID_GOLEM1:
				int duration = _Golem.PlayActorMotionSetMode(MOTION_PUNCH, false, 20f);
				SetTimer((float) (duration / 3) / 1000, TIMER_ID_GOLEM2);
				SetTimer((float) duration / 1000, TIMER_ID_GOLEM3);
				break;

			case TIMER_ID_GOLEM2:
				_DebrisTrigger.Trigger(0, 0, 0, 0, 0, 0);
				break;

			case TIMER_ID_GOLEM3:
				_Golem.SendActorToPos(_Golem.GetFramePosition(1), 90f);
				SetTimer(3f, TIMER_ID_GOLEM4);
				break;

			case TIMER_ID_GOLEM4:
				EndCutscene((int)arg0, 0);
				AIOn();
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _RunPed1.GetGUID())
			_RunPed1.Remove();
	}

	public void Mendel1ReturnConversation(int starterGuid, int npcGuid)
	{
		bMendel1ReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "11_1_Mendel1Return", "11_1_Mendel1Return.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void MendelConversation1(int starterGuid, int npcGuid)
	{
		bMendelConversation1 = true;
		AIOff();
		_RunPed1.SendActorToPos(_RunPed1.GetFramePosition(3), 120f);
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "11_1_Mendel1", "11_1_Mendel1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void MendelConversation2(int starterGuid, int npcGuid)
	{
		bMendelConversation2 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "11_1_Mendel2", "11_1_Mendel2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void JosefConversation1(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.wav", 80);
		bJosefConversation1 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "11_1_Josef1", "11_1_Josef1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void JosefConversation2(int starterGuid, int npcGuid)
	{
		bJosefConversation2 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "11_1_Josef2", "11_1_Josef2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void JosefConversation3(int starterGuid, int npcGuid)
	{
		bJosefConversation3 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "11_1_Josef3", "11_1_Josef3.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void TooLateConversation(int starterGuid, int npcGuid)
	{
		bTooLateConversation = true;
		AIOff();
//		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "11_1_TooLate2", "11_1_TooLate2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GolemCutscene(int starterGuid)
	{
		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "golem1.ncp", 50);
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		
		SetTimer(5, TIMER_ID_GOLEM1, starterGuid);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMendel1ReturnConversation)
		{
			AIOn();
			bMendel1ReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bMendelConversation1)
		{
			AIOn();
			bMendelConversation1 = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}

		if(bMendelConversation2)
		{
			CodexSound.PopMusic();
			AIOn();
			bMendelConversation2 = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_ShemMendel"));
			q.Complete();

			CodexSequence.ChangeScene("Haven", "HAVN_11_1.nsd");

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_Shem"));

			// auto advance
			CodexSequence.Advance(christofGUID);
		}

		if(bJosefConversation1)
		{
			//AIOn();
			bJosefConversation1 = false;
			
			SetTimer((float)0.1, 1);
			SetTimer(1, 2, starterGuid);

			//CodexCamera.Release(starterGuid);
		}

		if(bJosefConversation2)
		{
			CodexSound.PopMusic();
			AIOn();
			bJosefConversation2 = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bJosefConversation3)
		{
			AIOn();
			bJosefConversation3 = false;
			CodexCamera.Release(starterGuid);
		}

		if(bTooLateConversation)
		{
			AIOn();
			bTooLateConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}
	
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMendel1ReturnConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}		
		}
	
		if(bMendelConversation1)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "RunPed.ncp", 30);
					break;

				case 1:

					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 4:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 7:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bMendelConversation2)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
					break;

				case 5:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					_Christof.StopActorAction();
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
					break;

				case 7:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Christof.StopActorAction();
					break;

				//case 8:

				//	CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				//	break;

				//case 9:

				//	CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
				//	break;

				case 8:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 9:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 10:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 11:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				//case 14:

				//	CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
				//	_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
				//	break;

				//case 15:

				//	CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
				//	_Christof.StopActorAction();
				//	break;

				//case 16:

				//	CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				//	break;

				//case 17:

				//	CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				//	break;

				case 12:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 13:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, mendelGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 14:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 15:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;
			}
		}

		if(bJosefConversation1)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bJosefConversation2)
		{
			switch(curLine)
			{
				case 0:

					_Josef.SetAlpha(1, 2);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					_Wilhem.PlayMotionSetMode(MOTION_GESTURE4, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					_Wilhem.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					break;

				case 5:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 7:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 8:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					break;

				case 9:

					_Josef.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 10:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 11:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bJosefConversation3)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bTooLateConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, christofGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, christofGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;
			}

		}
	}

}
