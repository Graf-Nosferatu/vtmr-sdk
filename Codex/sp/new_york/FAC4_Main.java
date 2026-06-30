/**
 * Factory 4 Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FAC4_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int TIMER_ID_ORSITOSS			= 0;
	private static final int TIMER_ID_CHRISSCREAM		= 1;
	private static final int TIMER_ID_ORSIFALL			= 2;
	private static final int TIMER_ID_ORSIDIES			= 3;
	private static final int TIMER_ID_SPLASH			= 4;
	private static final int TIMER_ID_DOWNELEVATOR		= 5;
	private static final int TIMER_ID_RELEASEKAZI		= 6;
	private static final int TIMER_ID_KAZIRECOVERS		= 7;

	private CodexActor		_Christof;
	private CodexActor		_Orsi;
	private CodexRegion		_TripletRegion;
	private CodexThing		_kaziGhost;
	private CodexThing		_tetaGhost;
	private CodexThing		_zilGhost;
	private CodexThing		_elevator;
	private CodexThing		_splashGhost;
	private CodexThing		_window;
	private CodexThing		_windowTrigger;

	private CodexActor		_kazi;
	private CodexActor		_teta;
	private CodexActor		_zil;

	private int				christofGUID;
	private int				lilyGUID;
	private int				samuelGUID;
	private int				wilhemGUID;
	private int				orsiGUID;
	private int				kaziGUID;

	float[]					offset = new float[3];
	float[]					offset2 = new float[3];
	private float[]			pos = new float[3];
	private float[]			vector = new float[3];

	private float[]			chrisOldPos = new float[3];
	private float[]			chrisOldVector = new float[3];

	private int				humanityTrack = 0;

	private boolean			bMeetOrsiConversation	= false;
	private boolean			bTripletsConversation	= false;
	private boolean			bOrsiTossConversation	= false;
	public boolean			bTripletsReturnConversation = false;

	public static String _params[] = {"Orsi", "Triplets conversation region", "Kazi Ghost", "Teta Ghost", "Zil Ghost", "Elevator", "Splash Ghost", "Window", "Window Trigger"};

	public FAC4_Main(CodexActor Orsi, CodexRegion TripletRegion, CodexThing kaziGhost, CodexThing tetaGhost, CodexThing zilGhost, CodexThing elevator, CodexThing splashGhost, CodexThing window, CodexThing windowTrigger)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Orsi = new CodexActor(Orsi.GetGUID());
		_TripletRegion = new CodexRegion(TripletRegion.GetGUID());
		_kaziGhost = new CodexThing(kaziGhost.GetGUID());
		_tetaGhost = new CodexThing(tetaGhost.GetGUID());
		_zilGhost = new CodexThing(zilGhost.GetGUID());
		_elevator = new CodexThing(elevator.GetGUID());
		_splashGhost = new CodexThing(splashGhost.GetGUID());
		_window = new CodexThing(window.GetGUID());
		_windowTrigger = new CodexThing(windowTrigger.GetGUID());

		CaptureThing(_Orsi.GetGUID());
		CaptureThing(_TripletRegion.GetGUID());
		CaptureThing(_elevator.GetGUID());
		CaptureThing(_splashGhost.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		orsiGUID = _Orsi.GetGUID();

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.FAC4_ORSITALKED))
		{
			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_InfiltrateFactory"));
			q.Complete();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("N1_FindOrsi"));
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexActor		player;

		switch(timerID)
		{
			case TIMER_ID_ORSITOSS:

				AIOff();
				_Christof.OverrideActorWeapon("Punch", false, false);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiToss.ncp", 30);
							
				// Christof picks Orsi up and throws him out a window
				// First put them in same spot
				_Orsi.SetPosition(pos);
				_Christof.SetPosition(pos);
 
				// Then make sure they're oriented correctly
				_Orsi.SetOrientation(vector);
				_Christof.SetOrientation(vector);

				// Then play the animations on both actors for pick up and toss
				_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)15.0);
				_Christof.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)15.0);

				SetTimer((float)4.0, TIMER_ID_CHRISSCREAM);
				SetTimer((float)4.8, TIMER_ID_ORSIFALL);
				break;

			case TIMER_ID_CHRISSCREAM:

				CodexSound snd1 = new CodexSound("Christof_Scream_4.wav", (float)2048.0, (float)4096.0, 100, 0, 0, christofGUID);
				break;

			case TIMER_ID_ORSIFALL:

				CodexSound snd2 = new CodexSound("window_break.wav", (float)2048.0, (float)4096.0, 100, 0, 0, orsiGUID);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiFall.ncp", 30);

				// trigger window break
				_window.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_windowTrigger.Trigger(0, 0, 0, 0, 0, 0);
				
				// This is Orsi falling to his death
				_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)15.0);
				_Christof.StopActorAction();
				CodexSound snd3 = new CodexSound("Orsi_FallScream.wav", (float)2048.0, (float)4096.0, 100, 0, 0, orsiGUID);
				SetTimer((float)3.0, TIMER_ID_ORSIDIES);
				break;

			case TIMER_ID_ORSIDIES:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiDies.ncp", 30);
				CodexSound snd4 = new CodexSound("Orsi_AScream_9.wav", (float)2048.0, (float)4096.0, 100, 0, 0, orsiGUID);
				CodexSound snd5 = new CodexSound("orsi_LavaSplash.wav", (float)2048.0, (float)4096.0, 100, 0, 0, orsiGUID);
				SetTimer((float)0.5, TIMER_ID_SPLASH);
				SetTimer((float)4.5, TIMER_ID_DOWNELEVATOR);
				break;

			case TIMER_ID_SPLASH:

				_splashGhost.SpawnThing("moltenSpray");
				_splashGhost.SpawnThing("moltenSplash");
				_splashGhost.SpawnThing("moltenSplash");
				_Christof.CancelOverrideActorWeapon();
				_Orsi.Remove();
				break;
		
			case TIMER_ID_DOWNELEVATOR:

				///////////////////////////////////////////////////////////////////////
				// remove control from the player here
				///////////////////////////////////////////////////////////////////////

				// play cutscene here that shows them riding the lift down
				_Orsi.StopActorAction();
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiElevator.ncp", 60);

				// offset always 8 in z - or whatever half thickness of elevator is
				// X and Y will be used to distribute the party on the elevator
				offset[0] = 64; 
				offset[1] = 64; 
				offset[2] = 28;

				player = new CodexActor(christofGUID);
				player.Stop();
				// make them look out toward the ceter of the level
				player.LookAtThing(_splashGhost.GetGUID());
				_elevator.AttachThing(christofGUID, -1, offset, 0);

				// offset always 8 in z - or whatever half thickness of elevator is
				// X and Y will be used to distribute the party on the elevator
				offset[0] = -64; 
				offset[1] = 64; 
				offset[2] = 28;

				player = new CodexActor(lilyGUID);
				player.Stop();
				// make them look out toward the ceter of the level
				player.LookAtThing(_splashGhost.GetGUID());
				_elevator.AttachThing(lilyGUID, -1, offset, 0);
				
				// offset always 8 in z - or whatever half thickness of elevator is
				// X and Y will be used to distribute the party on the elevator
				offset[0] = 64; 
				offset[1] = -64; 
				offset[2] = 28;

				player = new CodexActor(wilhemGUID);
				player.Stop();
				// make them look out toward the ceter of the level
				player.LookAtThing(_splashGhost.GetGUID());
				_elevator.AttachThing(wilhemGUID, -1, offset, 0);

				// offset always 8 in z - or whatever half thickness of elevator is
				// X and Y will be used to distribute the party on the elevator
				offset[0] = -64; 
				offset[1] = -64; 
				offset[2] = 28;

				player = new CodexActor(samuelGUID);
				player.Stop();
				// make them look out toward the ceter of the level
				player.LookAtThing(_splashGhost.GetGUID());
				_elevator.AttachThing(samuelGUID, -1, offset, 0);

				// send the elevator off
				_elevator.MoveToFrame(1, 90);
				break;

			case TIMER_ID_RELEASEKAZI:

				_Christof.PlayMotionSetMode(MOTION_SPECIAL17, false, (float)30.0);
				_kazi.PlayMotionSetMode(MOTION_SPECIAL17, false, (float)30.0);

				SetTimer((float)1.0, TIMER_ID_KAZIRECOVERS);
				break;

			case TIMER_ID_KAZIRECOVERS:

				// Put Chris back in original position and orientation
				_Christof.SetPosition(chrisOldPos);
				_Christof.SetOrientation(chrisOldVector);

				_Christof.StopActorAction();

				_kazi.PlayMotionSetMode(MOTION_SPECIAL18, false, (float)30.0);
				break;
			}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(thingGuid == _elevator.GetGUID())
		{
			// detach party and put them on the walkway
			_elevator.DetachThing(christofGUID);
			_elevator.DetachThing(lilyGUID);
			_elevator.DetachThing(wilhemGUID);
			_elevator.DetachThing(samuelGUID);

			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);

			// open celerity for samuel
			CodexActor Samuel = new CodexActor(samuelGUID);
			Samuel.SetActorDisciplineLevel("Celerity", -1);

			//String aFormat = "%A" + Samuel.GetName() + "%g" + "DGRP_CELERITY";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			CodexCamera.Release(0);

			CodexSound.PopMusic();

			// auto advance
			CodexSequence.Advance(christofGUID);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Orsi.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.FAC4_ORSITALKED))
		{
			CodexSequence.SetChronicleFlag(chronScript.FAC4_ORSITALKED);

			MeetOrsiConversation(clickerGuid, 0);
		}
		else if(guid == _kazi.GetGUID())
		{
			// play default line here
			TripletsReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _TripletRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.FAC4_TRIPLETSTALKED) && 
			CodexSequence.GetChronicleFlag(chronScript.FAC4_ORSITALKED))
		{
			CodexSequence.SetChronicleFlag(chronScript.FAC4_TRIPLETSTALKED);
			TripletsConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Orsi.GetGUID())
		{
			// clear this so we can play the special animations here and not have 
			// the engine play his default death animation
			_Orsi.ClearActorFlags(THING_AF_DEAD);

			// On Orsi's death, Christof hurls him out the window

			pos = _Orsi.GetFramePosition(1);
			vector = _Orsi.GetFrameOrientation(1);
			//OrsiTossConversation(causeID, 0);
			SetTimer(0, TIMER_ID_ORSITOSS);
		}

		_kazi = new CodexActor(_kaziGhost.SpawnThing("kazi"));
		_teta = new CodexActor(_tetaGhost.SpawnThing("teta"));
		_zil = new CodexActor(_zilGhost.SpawnThing("zil"));

		CaptureThing(_kazi.GetGUID());
		CaptureThing(_teta.GetGUID());
		CaptureThing(_zil.GetGUID());

		kaziGUID = _kazi.GetGUID();
	}

	public void MeetOrsiConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Boss_4.mp3", 50);
		bMeetOrsiConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "38_1_MeetOrsi", "38_1_MeetOrsi.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void OrsiTossConversation(int starterGuid, int npcGuid)
	{
		bOrsiTossConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "38_1_OrsiToss", "38_1_OrsiToss.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void TripletsConversation(int starterGuid, int npcGuid)
	{
		bTripletsConversation = true;
		AIOff();
		kaziGUID = CodexThing.GuidFromCastID("Kazi");
		_kazi = new CodexActor(kaziGUID);
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "38_1_Triplets", "38_1_Triplets.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void TripletsReturnConversation(int starterGuid, int npcGuid)
	{
		bTripletsReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "38_1_TripletsReturn", "38_1_TripletsReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMeetOrsiConversation)
		{
			// clear his talkie flag so we can kick his butt
			_Orsi.ClearActorFlags(THING_AF_TALKTO);
			_Orsi.ClearActorFlags(THING_AF_AIPAUSED);

			AIOn();
			bMeetOrsiConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_FindOrsi"));
			q.Complete();
		}

		if(bOrsiTossConversation)
		{
			AIOn();
			bOrsiTossConversation = false;
			CodexCamera.Release(starterGuid);

			// at the right time, put them on the lift and send them down
			SetTimer(2, TIMER_ID_DOWNELEVATOR);
		}

		if(bTripletsConversation)
		{
			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_DestroyVukodlak"));

			// show cathedral on map
			CodexSequence.SetLocationFlags("CathedralOfFlesh1", LOCATION_FLAG_SHOWINMAP);

			AIOn();
			bTripletsConversation = false;
			_kazi.StopActorAction();
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			CodexSequence.ChangeScene("NewYorkUptown", "UTWN_40_1.nsd");
			CodexSequence.OpenExit("NewYorkUptown", 5);
			CodexSequence.OpenExit("CathedralOfFlesh1", 0);

			if(humanityTrack == -1)
			{
				// lose humanity here
				_Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);
			}
		}

		if(bTripletsReturnConversation)
		{
			AIOn();
			bTripletsReturnConversation = false;
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

							_Christof.LookAtThing(orsiGUID);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							_Orsi.LookAtThing(christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 15:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 17:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 18:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 19:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bOrsiTossConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiToss.ncp", 30);
							
							// Christof picks Orsi up and throws him out a window
							// First put them in same spot
							_Orsi.SetPosition(pos);
							_Christof.SetPosition(pos);
 
							// Then make sure they're oriented correctly
							_Orsi.SetOrientation(vector);
							_Christof.SetOrientation(vector);

							// Then play the animations on both actors for pick up and toss
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							_Christof.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
							
							break;

						case 3:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrsiFall.ncp", 30);
							// This is Orsi falling to his death
							_Orsi.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							_Christof.StopActorAction();
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bTripletsConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, kaziGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:

					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ChokeKazi.ncp", 30);

							humanityTrack = -1;

							chrisOldPos = _Christof.GetPosition();
							chrisOldVector = _Christof.GetOrientation();
							
							pos = _kazi.GetPosition();
							vector = _kazi.GetOrientation();

							// First put them in same spot
							_Christof.SetPosition(pos);
 
							// Then make sure they're oriented correctly
							_Christof.SetOrientation(vector);

							_teta.LookAtThing(kaziGUID);
							_zil.LookAtThing(kaziGUID);

							_Christof.PlayMotionSetMode(MOTION_SPECIAL15, false, (float)15.0);
							_kazi.PlayMotionSetMode(MOTION_SPECIAL15, false, (float)15.0);
							break;

						case 1:

							_Christof.PlayMotionSetMode(MOTION_SPECIAL16, false, (float)30.0);
							_kazi.PlayMotionSetMode(MOTION_SPECIAL16, false, (float)30.0);
							break;

						case 2:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							SetTimer((float)2.5, TIMER_ID_RELEASEKAZI);
							break;

					} // switch(curLine)
					break;

				case 2:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, kaziGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
/*
				case 3:

					switch(curLine)
					{
						case 0:

							_Christof.PlayMotionSetMode(MOTION_SPECIAL16, false, (float)30.0);
							_kazi.PlayMotionSetMode(MOTION_SPECIAL16, false, (float)30.0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							SetTimer((float)2.5, TIMER_ID_RELEASEKAZI);
							break;

					} // switch(curLine)
					break;
*/
			} // switch(curEvent)
		}

		if(bTripletsReturnConversation)
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

