/**
 * Convent, scene 3.3 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CNVT_3_3 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int	TIMER_ID_FADEIN			= 1;
	private static final int	TIMER_ID_FADEOUT		= 2;
	private static final int	TIMER_ID_NEXTDAY		= 3;

	private CodexActor		_ArchBishop;
	private CodexPlayer		_Christof;
	private CodexActor		_Anezka;

	private int				humanityTrack = 0;

	private float[]			pos;

	private int				christofGUID;
	private int				anezkaGUID;
	private int				archbishopGUID;

	public static String _params[] = {"ArchBishop"};

	public CNVT_3_3(CodexActor ArchBishop)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
		
		_ArchBishop = new CodexActor(ArchBishop.GetGUID());
		
		CaptureThing(_ArchBishop.GetGUID());
		
		pos = new float[3];
	}

	// Beginscene handler
	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.CNVT_GEZACONVERSATION))
		{
			CodexSequence.SetChronicleFlag(chronScript.CNVT_GEZACONVERSATION);

			christofGUID = CodexThing.GuidFromCastID("Christof");
			anezkaGUID = CodexThing.GuidFromCastID("Anezka");
			archbishopGUID = _ArchBishop.GetGUID();

			_Christof = new CodexPlayer(christofGUID);
			_Anezka = new CodexActor(anezkaGUID);
			
			// Fade effect
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);

			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);

			CodexCamera.SetupCutscene(clientGuid, anezkaGUID, 0);
			CodexCamera.SetShot(clientGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
			
			// Play lying in bed anim
			_Christof.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
			_Christof.EnableActorWeapon(false);

			SetTimer(1, TIMER_ID_FADEIN, clientGuid);
		}
	} // beginscene

	// Timer handler
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FADEIN:

				// start Geza conversation
				Geza((int)arg0, 0);
				break;
		
			case TIMER_ID_FADEOUT:

				CodexCamera.Release((int)arg0);
				SetTimer((float)0.01, TIMER_ID_NEXTDAY);
				break;

			case TIMER_ID_NEXTDAY:

				// Dress Christof
				_Christof.SetActorNoItemArmor("");
				_Christof.EnableActorWeapon(true);
				_Christof.PlayActorMotionSetMode(MOTION_STAND, true, (float)30.0);

				// make christof use the human model
				_Christof.SetModel("christof_human.nod");
				_Christof.SetPlayerHeadModel("christof_humanH.nod");
				
				// Get Christof up
				_Christof.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_STAND, true, (float)30.0);
				_Christof.EnableActorWeapon(true);

				// Jump to the next day
				CodexSequence.Jump("ConventDay", 2);
				break;
		}
	} // timer

	// Conversation ended handler
	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{	
		if(humanityTrack == 1)
		{
			// gain humanity here
			_Christof.AddActorEffectByLevel("ef_increasehumanity", 0, 1, 0, 0);
		}
		else if(humanityTrack == -1)
		{
			// lose humanity here
			_Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);
		}

		// open convent exits
		CodexSequence.OpenExit("ConventDay", 0);
		CodexSequence.OpenExit("Convent", 1);

		CodexQuest q = new CodexQuest(CodexQuest.Load("P1_SilverMines"));;

		// show east gate and silvermines on 
		CodexSequence.SetLocationFlags("EastGate", LOCATION_FLAG_SHOWINMAP);
		CodexSequence.SetLocationFlags("SilverMines", LOCATION_FLAG_SHOWINMAP);

		// award conversation XP
		CodexPlayer.AwardPartyExperience(50);

		// Fade out
		CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

		SetTimer(3, TIMER_ID_FADEOUT, starterGuid);
	}

	// Conversation reached handler
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					// ev_0

				   case 0:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 18);	
						break;

					case 2:

						_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

					case 3:

						_Anezka.StopActorAction();
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 4:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;
				}
				break;

			case 1:
				switch(curLine)
				{
					// ev_brave

					case 0:

						humanityTrack = 1;

						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						break;

					case 2:

						_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						pos = _ArchBishop.GetFramePosition(1);
						_ArchBishop.SendActorToPos(pos, (float)90.0);
						break;

					case 3:

						_Anezka.StopActorAction();
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						break;

					case 4:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						break;

					case 5:

						// this is Anezka delay
						CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						AnezkaLeaves();
						break;

					case 6:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 7:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
						_Christof.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						break;
				}
				break;

			case 2:
				switch(curLine)
				{
					// ev_coward

					case 0:

						humanityTrack = -1;

						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 1, 0);
						pos = _ArchBishop.GetFramePosition(1);
						_ArchBishop.SendActorToPos(pos, (float)90.0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						break;

					case 2:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						break;

					case 3:

						// this is Anezka delay
						CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						AnezkaLeaves();
						break;

					case 4:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 5:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
						_Christof.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						break;
				}
				break;
		}
	}

	public void endscene(int clientGuid, int captureID, int exit)
	{
		EndConversation(clientGuid, CONV_BEFLAG_UI);
		CodexCamera.Release(clientGuid);
	}
	
	// Christof and Anezka chat, Geza interrupts
	public void Geza(int starterGuid, int npcGuid)
	{
		ExecuteConversation(starterGuid, npcGuid, "3_3_C&A", "3_3_C&A.nco", CONV_XFLAG_WANTFEEDBACK + CONV_XFLAG_NORESTOREWEAPONS);
	}

	// Anezka leaves
	public void AnezkaLeaves()
	{
		if(IsActorGuid(anezkaGUID))
		{
			pos = _Anezka.GetFramePosition(1);
			_Anezka.SendActorToPos(pos, (float)90.0);
		}
	}
}
