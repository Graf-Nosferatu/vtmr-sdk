/**
 * ORSI, scene 20.5 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ORSI_20_5_MeetOrsi extends Codex
{
	private ViennaChronicle	chronScript;
	
	private static final int	TIMER_ID_ORSIINTERVENES		= 0;
	private static final int	TIMER_ID_CHILDER			= 1;
	private static final int	TIMER_ID_ZILBOWS			= 2;
	private static final int	TIMER_ID_TETABOWS			= 3;
	private static final int	TIMER_ID_HARSHMASTER		= 4;
	private static final int	TIMER_ID_KAZINODS			= 5;
	private static final int	TIMER_ID_MOVEPARTY			= 6;
	private static final int	TIMER_ID_GIVESHACKLES		= 7;

	private static final int	PATH_ORSIPARTY				= 0;
	private static final int	PATH_CHILDER				= 1;
	private static final int	PATH_ORSI					= 2;
	private static final int	PATH_KAZINODS				= 3;
	private static final int	PATH_ORSIINTERVENES			= 4;
	private static final int	PATH_GIVESHACKLES			= 5;

	private CodexRegion		_OrsiRegion;
	private CodexThing		_Shackles;
	private CodexActor		_Kazi;
	private CodexActor		_Teta;
	private CodexActor		_Zil;
	private CodexActor		_Orsi;
	private CodexActor		_Christof;
	private CodexActor		_Ragwick;

	private int				christofGUID;
	private int				erikGUID;
	private int				orsiGUID;
	private int				ragwickGUID;
	private int				kaziGUID;

	private int				pathnum;

	private float[]			pos;

	public boolean		bMeetOrsiConversation = false;
	public boolean		bOrsiMissionConversation = false;
	public boolean		bKaziReturnConversation = false;
	public boolean		bOrsiReturnConversation = false;

	public static String _params[] = {"Orsi region", "Kazi", "Teta", "Zil", "Orsi", "Ragwick", "Shackles"};

	public ORSI_20_5_MeetOrsi(CodexRegion OrsiRegion, CodexActor Kazi, CodexActor Teta, CodexActor Zil, CodexActor Orsi, CodexActor Ragwick, CodexThing Shackles)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_OrsiRegion = new CodexRegion(OrsiRegion.GetGUID());
		_Kazi = new CodexActor(Kazi.GetGUID());
		_Teta = new CodexActor(Teta.GetGUID());
		_Zil = new CodexActor(Zil.GetGUID());
		_Orsi = new CodexActor(Orsi.GetGUID());
		_Ragwick = new CodexActor(Ragwick.GetGUID());
		_Shackles = new CodexThing(Shackles.GetGUID());

		CaptureThing(_OrsiRegion.GetGUID());
		CaptureThing(_Kazi.GetGUID());
		CaptureThing(_Teta.GetGUID());
		CaptureThing(_Zil.GetGUID());
		CaptureThing(_Orsi.GetGUID());
		CaptureThing(_Ragwick.GetGUID());
		CaptureThing(_Shackles.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		orsiGUID = _Orsi.GetGUID();
		ragwickGUID = _Ragwick.GetGUID();
		kaziGUID = _Kazi.GetGUID();

		_Christof = new CodexActor(christofGUID);
		
		_Ragwick.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
		_Ragwick.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		if(!CodexSequence.GetChronicleFlag(chronScript.ORSI_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.ORSI_FLYTHROUGH);

			pathnum = PATH_ORSIPARTY;

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "V1_OrsiParty.ncp", 100);

			_Orsi.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		}
	}
	
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ORSIINTERVENES:

				MeetOrsiConversation(christofGUID, 0);
				break;

			case TIMER_ID_CHILDER:

				pathnum = PATH_CHILDER;
				_Kazi.PlayMotionSetMode(MOTION_GESTURE7, false, (float)15.0);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "KaziTetaAndZil.ncp", 20);

				SetTimer((float)1.0, TIMER_ID_ZILBOWS);
				break;

			case TIMER_ID_ZILBOWS:

				_Zil.PlayMotionSetMode(MOTION_GESTURE7, false, (float)15.0);
				SetTimer((float)1.0, TIMER_ID_TETABOWS);
				break;

			case TIMER_ID_TETABOWS:

				_Teta.PlayMotionSetMode(MOTION_GESTURE7, false, (float)15.0);
				SetTimer((float)4.0, TIMER_ID_HARSHMASTER);
				break;

			case TIMER_ID_HARSHMASTER:

				pathnum = PATH_ORSI;
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Orsi.ncp", 30);

				SetTimer((float)30.0, TIMER_ID_KAZINODS);
				break;

			case TIMER_ID_KAZINODS:

				pathnum = PATH_KAZINODS;
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "KaziNods.ncp", 30);
				_Kazi.PlayMotionSetMode(MOTION_GESTURE7, false, (float)15.0);
				break;

			case TIMER_ID_MOVEPARTY:

				// Fade in
				_Orsi.CancelOverrideActorWeapon();
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)2.0, false);
				OrsiMissionConversation(christofGUID, 0);
				break;

			case TIMER_ID_GIVESHACKLES:

				pathnum = PATH_GIVESHACKLES;
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiMission.ncp", 30);
				_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
				break;
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Orsi.GetGUID())
		{
			// play default line here
			OrsiReturnConversation(clickerGuid, 0);
		}
		else if(guid == _Kazi.GetGUID() ||
			guid == _Teta.GetGUID() ||
			guid == _Zil.GetGUID())
		{
			// play triplets default line here
			KaziReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _OrsiRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.ORSI_ORSICONVERSATION))
		{
			CodexSequence.SetChronicleFlag(chronScript.ORSI_ORSICONVERSATION);

			// start Orsi moving
			pos = _Orsi.GetFramePosition(1);
			_Orsi.SendActorToPos(pos, (float)90.0);

			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);

			// set up camera
			pathnum = PATH_ORSIINTERVENES;
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MeetOrsi.ncp", 30);

			SetTimer((float)3.0, TIMER_ID_ORSIINTERVENES);
		}
	}

	public void pathended(int clientGuid)
	{
		switch(pathnum)
		{
			case PATH_ORSIPARTY:

				CodexCamera.Release(0);
				_Orsi.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				break;

			case PATH_CHILDER:

				break;

			case PATH_ORSIINTERVENES:

				break;

			case PATH_GIVESHACKLES:

				break;
		}
	}

	public void MeetOrsiConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bMeetOrsiConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "20_5_MeetOrsi", "20_5_MeetOrsi.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void OrsiMissionConversation(int starterGuid, int npcGuid)
	{
		bOrsiMissionConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		pos = _Orsi.GetFramePosition(2);
		_Orsi.SetPosition(pos);
		ExecuteConversation(starterGuid, npcGuid, "20_5_OrsiMission", "20_5_OrsiMission.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void KaziReturnConversation(int starterGuid, int npcGuid)
	{
		bKaziReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "20_5_KaziReturn", "20_5_KaziReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void OrsiReturnConversation(int starterGuid, int npcGuid)
	{
		bOrsiReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "20_5_OrsiReturn", "20_5_OrsiReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMeetOrsiConversation)
		{
			bMeetOrsiConversation = false;
			KillTimer(TIMER_ID_CHILDER);
			KillTimer(TIMER_ID_ZILBOWS);
			KillTimer(TIMER_ID_TETABOWS);
			KillTimer(TIMER_ID_HARSHMASTER);
			KillTimer(TIMER_ID_KAZINODS);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			SetTimer((float)2.0, TIMER_ID_MOVEPARTY);
		}
	
		if(bOrsiMissionConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bOrsiMissionConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);

			KillTimer(TIMER_ID_GIVESHACKLES);
			_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_OrsiParty"));
			q.Complete();

			CodexQuest q1 = new CodexQuest(CodexQuest.Load("V1_Infiltrate"));
			CodexQuest q2 = new CodexQuest(CodexQuest.Load("V1_DestroyLuther"));

			//show inner stradt, inner house, and stephansdom on map
			CodexSequence.SetLocationFlags("Stephansdom1", LOCATION_FLAG_SHOWINMAP);
			CodexSequence.SetLocationFlags("InnerStradt", LOCATION_FLAG_SHOWINMAP);
			CodexSequence.SetLocationFlags("InnerHouse", LOCATION_FLAG_SHOWINMAP);

			// open stephansdom secret entrance
			CodexSequence.OpenExit("InnerStradt", 2);
		}

		if(bKaziReturnConversation)
		{
			AIOn();
			bKaziReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bOrsiReturnConversation)
		{
			AIOn();
			bOrsiReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMeetOrsiConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Orsi.OverrideActorWeapon("weapChaliceEmpty", false, false);
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

							SetTimer((float)9.0, TIMER_ID_CHILDER);
							break;

						case 1:

							KillTimer(TIMER_ID_CHILDER);
							KillTimer(TIMER_ID_ZILBOWS);
							KillTimer(TIMER_ID_TETABOWS);
							KillTimer(TIMER_ID_HARSHMASTER);
							KillTimer(TIMER_ID_KAZINODS);
							_Kazi.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							// add conversation XP
							CodexPlayer.AwardPartyExperience(50);

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

			} // switch(curEvent)
		}

		if(bOrsiMissionConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Orsi.LookAtThing(christofGUID);
							_Orsi.OverrideActorWeapon("weapChaliceShackles", false, false);
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 9);
							break;

						case 1:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 2:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 3:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 5:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 7:

							// Orsi gives shackles to bind Luther with
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
							_Shackles.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							
							SetTimer((float)5.0, TIMER_ID_GIVESHACKLES);
							break;

						case 8:

							KillTimer(TIMER_ID_GIVESHACKLES);
							_Shackles.StopAction();
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
							_Ragwick.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
							break;

						case 9:

							_Ragwick.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							break;

						case 10:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 11:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bKaziReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
			}
		}

		if(bOrsiReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
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
}
