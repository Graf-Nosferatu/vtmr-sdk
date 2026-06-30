/**
 * Anezka's Room scene 8.4 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ANZK_8_4 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_LINES		= 1;
	private static final int TIMER_ID_ENDSCENE	= 2;

	private	CodexActor		_Anezka;
	private CodexPlayer		_Christof;
	
	private int				anezkaGUID;
	private int				christofGUID;

	private float[]			pos;

	private boolean			bAnezkaConversation = false;

	public CodexPlayer		wil;
	public static String _params[] = {"Anezka"};

	public ANZK_8_4(CodexActor Anezka)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Anezka = new CodexActor(Anezka.GetGUID());

		CaptureThing(_Anezka.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// HIDE WILHEM DURING THIS SCENE - he's shown in OLDT_7_7
		wil = new CodexPlayer(CodexThing.GuidFromCastID("wilhem"));
		wil.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		wil.SetActorFlags(THING_AF_AIPAUSED);
		wil.SetCollideType(THING_COLLIDE_NONE);

		christofGUID = CodexThing.GuidFromCastID("Christof");
		anezkaGUID = _Anezka.GetGUID();

		_Christof = new CodexPlayer(christofGUID);

		_Christof.SetCurrentPlayer();

		SetTimer(0, TIMER_ID_LINES, clientGuid);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_LINES:
				AnezkaConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_ENDSCENE:
				// set flag so we know to trigger the 11_1_UnornaLetter sequence
				// and to show wilhem in old town after this
				CodexSequence.SetChronicleFlag(chronScript.ANZK_LOCKETSCENE);

				// close exit to convent
				CodexSequence.CloseExit("OldTown", 4);

				AIOn();
				CodexCamera.Release(christofGUID);

				// jump the player into old town outside the convent window
				CodexSequence.Jump("OldTown", 12);
				break;
		}
	}

	public void AnezkaConversation(int starterGuid, int npcGuid)
	{
		bAnezkaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_4_Visit", "8_4_Visit.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bAnezkaConversation)
		{
			bAnezkaConversation = false;
			_Christof.CancelOverrideActorWeapon();

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			SetTimer(4, TIMER_ID_ENDSCENE);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curLine)
		{
			case 0:

				CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				break;

			case 1:

				// Delay
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 7);
				break;

			case 5:

				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				pos = _Anezka.GetFramePosition(1);
				_Anezka.SendActorToPos(pos, (float)90.0);
				break;

			case 6:

				_Christof.LookAtThing(anezkaGUID);
				break;

			case 7:

				_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
				CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 9);
				break;

			case 8:

				_Anezka.StopActorAction();
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 9:

				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				break;

			case 10:

				_Anezka.StopActorAction();
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 11:

				_Anezka.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 12:

				// Delay while Christof moves toward Anezka
				_Christof.OverrideActorWeapon("Punch", false, false);
				pos = _Anezka.GetFramePosition(2);
				_Christof.SendActorToPos(pos, (float)90.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				break;

			case 13:

				_Anezka.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
				CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
				break;

			case 14:

				_Anezka.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 15:

				_Christof.StopActorAction();
				_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
				break;

			case 16:

				_Christof.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
				_Anezka.StopActorAction();
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 17:

				_Christof.StopActorAction();
				_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				break;

			case 18:

				_Anezka.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 19:

				_Christof.StopActorAction();
				_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
				break;

			case 20:

				_Anezka.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 21:

				_Christof.StopActorAction();
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
				break;

			case 22:

				_Anezka.OverrideActorWeapon("weapLocket", false, false);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
				break;

			case 23:

				_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				break;

			case 24:

				_Christof.StopActorAction();
				_Anezka.PlayMotionSetMode(MOTION_SPECIAL18, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
				break;

			case 25:

				_Anezka.StopActorAction();
				_Anezka.CancelOverrideActorWeapon();
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
				break;

			case 26:

				// Delay while Christof leaps out window
				//_Christof.PlayMotionSetMode(MOTION_SPECIALX, false, (float)30.0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 1);
				pos[0] = 0;
				pos[1] = -1000;
				pos[2] = 0;
				_Christof.SetPosition(pos);
				pos = _Anezka.GetFramePosition(3);
				_Anezka.SendActorToPos(pos, (float)90.0);
				break;

			case 27:

				_Anezka.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
				CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
				//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "AnezkaRoom.ncp", 30);
				break;
		}
	}
}
