/**
 * Haven, scene 7.4 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HAVN_7_4 extends Codex
{
	private static final int TIMER_ID_LINES		= 0;
	private static final int TIMER_ID_DRIP		= 1;
	private static final int TIMER_ID_CARTHAGE	= 2;
	private static final int TIMER_ID_VENTRUE	= 3;
	private static final int TIMER_ID_ENDSCENE	= 4;

	private CodexActor		_Christof;
	private CodexActor		_Ecaterina;
	private CodexActor		_Cosmas;
	private CodexThing		_crossProjectile;

	private int				christofGUID;
	private int				ecaterinaGUID;
	private int				cosmasGUID;
	private int				crossGuid;

	private CodexThing		drip;
	private int				dripGuid;

	private float[]			pos;
	private float[]			christofPos;
	private float[]			offset;

	private boolean			bAwakenConversation = false;

	public static String _params[] = {"Ecaterina", "Cosmas"};

	public HAVN_7_4(CodexActor Ecaterina, CodexActor Cosmas)
	{
		_Ecaterina = new CodexActor(Ecaterina.GetGUID());
		_Cosmas = new CodexActor(Cosmas.GetGUID());

		pos = new float[3];
		offset = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		ecaterinaGUID = _Ecaterina.GetGUID();
		cosmasGUID = _Cosmas.GetGUID();	
		christofGUID = CodexThing.GuidFromCastID("Christof");

		_Christof = new CodexActor(christofGUID);
		
		// to hide the sword that mysteriously shows up
		_Christof.EnableActorWeapon(false);
		_Christof.EnableActorShield(false);

		christofPos = _Christof.GetPosition();

		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		_Christof.PlayMotionSetMode(MOTION_SPECIAL6, false, (float)30.0);
		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "AwakenWater.ncp", 15);
		
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);

		SetTimer(2, TIMER_ID_LINES, clientGuid);

		// Clear all effects, then fade in effect
		CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());
		CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)4.0, false);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_LINES:

				AwakenConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_DRIP:
				
				dripGuid = _Ecaterina.SpawnThing("blooddrip");
				drip = new CodexThing(dripGuid);
				_Ecaterina.AttachThing(dripGuid, _Ecaterina.FindBone(MOTIONTAG_RWRIST), offset, ATTACH_FLAG_AUTOREMOVE);
				break;

			case TIMER_ID_CARTHAGE:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "InFabledCarthage.ncp", 30);
				//SetTimer(11, TIMER_ID_VENTRUE, christofGUID);
				break;

			case TIMER_ID_VENTRUE:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HatedVentrue.ncp", 30);
				break;

			case TIMER_ID_ENDSCENE:

				_Christof.StopActorAction(); 
				_Christof.CancelOverrideActorWeapon();

				_Christof.EnableActorShield(true);

				CodexCamera.Release(CodexPlayer.GetCurrentPlayer());
				CodexSound.PopMusic();

				CodexSequence.ChangeScene("University", "UNIV_7_7.nsd");
				CodexSequence.ChangeScene("Inn4Stags", "INN4_7_7.nsd");
				CodexSequence.ChangeScene("GoldenLane", "GOLD_8_1.nsd");

				CodexSequence.Jump("University", 0);
				break;
		}
	}

	public void AwakenConversation(int starterGuid, int npcGuid)
	{
		bAwakenConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "7_4_Awaken", "7_4_Awaken.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		AIOn();
		bAwakenConversation = false;

		SetTimer(0, TIMER_ID_ENDSCENE);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curLine)
		{

			case 1:
				
				pos = _Ecaterina.GetFramePosition(1);
				_Ecaterina.SendActorToPos(pos, (float)90.0);

				CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, cosmasGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
				break;

			case 2:

				// In case of space-thru, clear fade effect
				CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());

				CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 8);
				break;

			case 3:

				CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
				_Christof.PlayMotionSetMode(MOTION_SPECIAL7, false, (float)30.0);
				break;

			case 4:

				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
				_Cosmas.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
				break;

			case 5:

				CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				// Ecaterina slashes wrist animation here
				_Ecaterina.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
				_Cosmas.StopActorAction();
				SetTimer((float)1.0, TIMER_ID_DRIP);
				break;

			case 6:

				// Move them together
				pos = _Ecaterina.GetFramePosition(2);
				_Christof.SetPosition(pos);

				
				// Make them look at each other
				_Ecaterina.LookAtThing(christofGUID);
				_Christof.LookAtThing(ecaterinaGUID);
				
				CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 1, 0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 15);

				// Delay while Ecaterina extends wrist, Christof kneels
				_Ecaterina.PlayMotionSetMode(MOTION_EMBRACEDWRIST, false, (float)10.0);
				_Christof.PlayMotionSetMode(MOTION_FEEDWRIST, false, (float)10.0);
				CodexSound snd2 = new CodexSound("Lily_Gasp_3.wav", (float)2048.0, (float)4096.0, 100, 0, 0, ecaterinaGUID);
				break;

			case 7:

				// Delay while Christof feeds
				CodexSound snd3 = new CodexSound("Christof_Suck_5.wav", (float)2048.0, (float)4096.0, 100, 0, 0, christofGUID);
				_Ecaterina.PlayMotionSetMode(MOTION_DRAININGWRIST, false, (float)10.0);
				_Christof.PlayMotionSetMode(MOTION_FEEDINGWRIST, false, (float)10.0);
				break;

			case 8:

				// Delay while Christof releases and stands
				CodexSound snd4 = new CodexSound("Christof_Whimper_3.wav", (float)2048.0, (float)4096.0, 100, 0, 0, christofGUID);
				drip.Remove();
				_Ecaterina.PlayMotionSetMode(MOTION_FEEDRELEASEDWRIST, false, (float)10.0);
				_Christof.PlayMotionSetMode(MOTION_FEEDRELEASEWRIST, false, (float)10.0);
				break;


			case 9:

				// Move them back apart
				pos = _Ecaterina.GetFramePosition(0);
				_Ecaterina.SetPosition(pos);

				_Christof.SetPosition(christofPos);
				
				CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
				_Ecaterina.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
				break;

			case 10:

				CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				SetTimer(25, TIMER_ID_CARTHAGE, christofGUID);
				_Christof.StopActorAction();
				break;

			case 11:

				KillTimer(TIMER_ID_CARTHAGE);
				CodexCamera.SetupCutscene(starterGuid, christofGUID, cosmasGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				break;

			case 12:

				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
				break;

			case 13:

				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				_Christof.StopActorAction();
				_Cosmas.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
				break;

			case 14:

				CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
				_Cosmas.StopActorAction();
				break;

			case 15:

				// exit Ecaterina and Cosmas
				_Ecaterina.Remove();
				_Cosmas.Remove();
				
				// arm Christof with cross
				crossGuid = _Christof.SpawnThing("crossProjectile");
				_crossProjectile = new CodexThing(crossGuid);
				_Christof.EnableActorWeapon(true);
				_Christof.OverrideActorWeapon("weapCross", false, false);

				// make him stand dejectedly
				_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
				CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
				CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 5);
				break;

			case 16:

				_Christof.PlayMotionSetMode(MOTION_SPECIAL8, false, (float)30.0);
				_crossProjectile.PlayMotionSetMode(MOTION_SPECIAL8, false, (float)30.0);
				break;

			case 17:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CrossToss.ncp", 30);
				break;

			case 18:

				// Fade out
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);
				break;
		}
	}
}
