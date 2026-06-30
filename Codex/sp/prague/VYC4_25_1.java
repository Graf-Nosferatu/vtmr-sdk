/**
 * Vysehrad4 25.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class VYC4_25_1 extends Codex
{
	private Prague2Chronicle chronScript;

	private static final int TIMER_ID_COLUMNFALL		= 0;
	private static final int TIMER_ID_RUMBLE			= 1;
	private static final int TIMER_ID_LIBUSSACHANNEL	= 2;
	private static final int TIMER_ID_WHITEOUT			= 3;
	private static final int TIMER_ID_BLACKOUT			= 4;

	private CodexRegion		_finalRegion;

	private CodexActor		_Libussa;
	private CodexActor		_Anezka;

	private CodexActor		_Christof;
	private CodexPlayer		_Wilhem;
	private CodexPlayer		_Serena;

	private CodexThing		_fallingColumn;
	private CodexThing		_sarcophagus;

	private CodexThing		beam;
	private int				beamGuid;
	private float[]			offset = new float[3];

	private int				anezkaGUID;
	private int				christofGUID;
	private int				vukodlakGUID;
	private int				libussaGUID;

	private float[]			pos;
	
	private boolean			bVukConversation = false;

	public static String _params[] = {"Final region", "Libussa", "Anezka", "Falling column", "Sarcophagus"};

	public VYC4_25_1(CodexRegion finalRegion, CodexActor Libussa, CodexActor Anezka, CodexThing fallingColumn, CodexThing sarcophagus)
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);

		_finalRegion = new CodexRegion(finalRegion.GetGUID());
		_Libussa = new CodexActor(Libussa.GetGUID());
		_Anezka = new CodexActor(Anezka.GetGUID());
		_fallingColumn = new CodexThing(fallingColumn.GetGUID());	
		_sarcophagus = new CodexThing(sarcophagus.GetGUID());

		CaptureThing(_finalRegion.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_Wilhem = new CodexPlayer(CodexThing.GuidFromCastID("wilhem"));
		_Serena = new CodexPlayer(CodexThing.GuidFromCastID("serena"));
		
		christofGUID = CodexThing.GuidFromCastID("Christof");
		anezkaGUID = _Anezka.GetGUID();
		libussaGUID = _Libussa.GetGUID();
		vukodlakGUID = CodexThing.GuidFromCastID("Vukodlak");

		_Christof = new CodexActor(christofGUID);

		// some initial sound effects
		SetTimer(2, TIMER_ID_RUMBLE);
		new CodexSound("explosion_firey.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _fallingColumn.GetGUID());
	}	

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(guid == _finalRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.VYC4_FINAL))
		{
			CodexSequence.SetChronicleFlag(chronScript.VYC4_FINAL);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P2_Infiltrate"));
			q.Complete();

			VukConversation(christofGUID, 0);

		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_COLUMNFALL:

				CodexSound snd1 = new CodexSound("explosion_firey.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _fallingColumn.GetGUID());
				_fallingColumn.RotatePivot(1, 7);
				break;

			case TIMER_ID_RUMBLE:

				CodexSound snd2 = new CodexSound("rumble_earth_02.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());
				break;

			case TIMER_ID_LIBUSSACHANNEL:

				_Libussa.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
				break;

			case TIMER_ID_WHITEOUT:

				CodexSound snd3 = new CodexSound("explosion_huge_stereo.WAV", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
				SetTimer((float)6.0, TIMER_ID_BLACKOUT);
				break;

			case TIMER_ID_BLACKOUT:

				CodexSound.PopMusic();
				AIOn();
				bVukConversation = false;
				CodexCamera.Release(0);

				// add conversation XP
				CodexPlayer.AwardPartyExperience(50);

				_Christof.StopActorAction();
				_Anezka.StopActorAction();

				// remove Wilhem and Serena from the party and delete them
				_Wilhem = new CodexPlayer(CodexThing.GuidFromCastID("wilhem"));
				_Serena = new CodexPlayer(CodexThing.GuidFromCastID("serena"));

				_Wilhem.RemoveFromParty();
				_Serena.RemoveFromParty();

				_Wilhem.Remove();
				_Serena.Remove();
				PlayVideo("Awakening.bik");
				break;
		}
	}

	public void VukConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("Christof_Theme.mp3", 50);
		bVukConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);

		// free revive of christof here because he'll be the sole party member
		if((_Christof.GetActorFlags() & THING_AF_DEAD) != 0)
		{
			_Christof.ReviveActor(100, 100);
		}
		
		// move christof close to anezka
		pos = _Anezka.GetFramePosition(1);
		_Christof.SetPosition(pos);

		ExecuteConversation(starterGuid, npcGuid, "25_1_Final", "25_1_Final.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1.0, 0xffffff, false);
		SetTimer((float)1.0, TIMER_ID_WHITEOUT);
	}

	public void videoended(int id)
	{
		// change to the london chronicle
		CodexSequence.ChangeChronicle("london.nsc");
	}
		
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VysAnezkaLookAtChris.ncp", 30);
						break;

					case 1:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VysChrisLookAtAnezka.ncp", 30);
						SetTimer(0, TIMER_ID_RUMBLE);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
						break;

					case 3:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
						SetTimer(0, TIMER_ID_RUMBLE);
						break;

					case 4:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 12);
						break;

					case 5:

						break;

					case 6:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
						SetTimer(0, TIMER_ID_RUMBLE);
						break;

					case 7:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
						break;

					case 8:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 15);
						break;

					case 9:

						_Libussa.LookAtThing(christofGUID);

						_Libussa.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
						SetTimer(1, TIMER_ID_LIBUSSACHANNEL);

						// cue the sarcophagus
						_sarcophagus.SetShell("redCloudShell_g", 0x1000, 0, 1, 1, 1);
						//_Libussa.SetShell("LensFlare1_green", 0x1000, 0, 1, 1, 1);
/*					
						// spawn a beam from the sarcophagus
						beamGuid = _sarcophagus.SpawnThing("fogStream");
						beam = new CodexThing(beamGuid);

						// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
						// then set the target of the beam to Libussa's head
						beam.AllocateFrames(2);
						beam.SetFramePosition(1, _sarcophagus.GetPosition());

						// attach thing needs an array of floats as a parm, using no offset essentially
						offset[0] = 0; 
						offset[1] = 0; 
						offset[2] = 0;

						// attach the beam to their jaw bone - if FindBone fails, it will attach it to the player's origin
						_Libussa.AttachThing(beamGuid, _Libussa.FindBone(MOTIONTAG_HELMET), offset, ATTACH_FLAG_AUTOREMOVE);
*/
						CodexCamera.SetupCutscene(starterGuid, christofGUID, libussaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 12);
						break;

					case 10:

						_Libussa.DetachThing(beamGuid);
						_sarcophagus.EndShell();
						_Libussa.EndShell();

						_Libussa.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
						
						// move christof on to anezka yeah
						pos = _Anezka.GetPosition();
						_Christof.SetPosition(pos);

						pos = _Anezka.GetOrientation();
						_Christof.SetOrientation(pos);
						
						// christof grabs anezka here
						_Christof.PlayMotionSetMode(MOTION_SPECIAL19, false, (float)30.0);
						_Anezka.PlayMotionSetMode(MOTION_SPECIAL19, false, (float)30.0);

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VysRotateCam.ncp", 35);

						break;

					case 11:

						_Libussa.StopActorAction();
						_Christof.PlayMotionSetMode(MOTION_SPECIAL20, false, (float)30.0);
						_Anezka.PlayMotionSetMode(MOTION_SPECIAL20, false, (float)30.0);
						break;

/*					case 12:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
						break;

					case 13:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
						break;

					case 14:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
						break;
*/
					case 15:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VysWallsFall.ncp", 20);
						SetTimer(0, TIMER_ID_RUMBLE);
						break;

					case 16:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VysChrisAndAnezka.ncp", 30);
						break;

					case 17:

						// column falls here
						SetTimer(0.1f, TIMER_ID_COLUMNFALL);
						break;

					case 18:

						// Chris pushes Anezka outta the way
						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VysWallsFall.ncp", 30);

						_Christof.PlayMotionSetMode(MOTION_ACTION10, false, (float)30.0);
						_Anezka.PlayMotionSetMode(MOTION_ACTION10, false, (float)30.0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

