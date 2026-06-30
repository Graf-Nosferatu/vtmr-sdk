/**
 * Setite Temple 29.2 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SET4_29_2_Main extends Codex
{
	private LondonChronicle	chronScript;

	private static final int	TIMER_ID_HIDELUCRETIA	= 0;
	private static final int	TIMER_ID_REMOVELUCRETIA	= 1;
	private static final int	TIMER_ID_SPAWNVIPERS	= 2;
	private static final int	TIMER_ID_REMOVEVIPERS	= 3;

	private int			christofGUID;
	private int			lilyGUID;
	private int			lucretiaGUID;
	private int			pinkGUID;

	private CodexActor	viper1;
	private CodexActor	viper2;
	private CodexActor	viper3;
	private CodexActor	viper4;
	private CodexActor	viper5;
	private CodexActor	viper6;

	private int			humanityTrack = 0;

	private CodexActor	_Pink;

	public boolean		b29_2_LucretiaConversation = false;
	public boolean		b29_2_NotDeadConversation = false;

	public CodexRegion	_lucretiaRegion;
	public CodexActor	_lucretia;

	private float[]		pos = new float[3];

	public static String _params[] =	{"Lucretia Region", "Lucretia"};

	public SET4_29_2_Main(CodexRegion lucretiaRegion, CodexActor lucretia)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_lucretiaRegion = new CodexRegion(lucretiaRegion.GetGUID());
		_lucretia = new CodexActor(lucretia.GetGUID());

		CaptureThing(_lucretiaRegion.GetGUID());
		CaptureThing(_lucretia.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		lucretiaGUID = _lucretia.GetGUID();
		_Pink = new CodexActor(pinkGUID);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.SET4_MAIN))
		{
			CodexSequence.SetChronicleFlag(chronScript.SET4_MAIN);

			c29_2_LucretiaConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// set lucretia's health back up greater than 0 and clear her DEAD flag
		_lucretia.SetActorHealth((float)25.0);
		_lucretia.ClearActorFlags(THING_AF_DEAD);
		_lucretia.SetActorFlags(THING_AF_INVUL);

		c29_2_NotDeadConversation(christofGUID, 0);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_HIDELUCRETIA:

				_lucretia.PlayMotionSetMode(MOTION_DEATHSLOW, false, (float)30.0);
				_lucretia.SetAlpha(0, 2000);

				SetTimer(2, TIMER_ID_REMOVELUCRETIA);
				break;

			case TIMER_ID_REMOVELUCRETIA:

				_lucretia.Remove();
				break;

			case TIMER_ID_SPAWNVIPERS:

				new CodexSound("viper_hurt_02.WAV", 1000, 2000, 100, 0, 0, christofGUID);

				CodexActor chris = new CodexActor(christofGUID);

				float[] targetPos = new float[3];
				float[] tempPos = new float[3];

				targetPos = chris.GetPosition();

				tempPos[0] = targetPos[0] - 128; 
				tempPos[1] = targetPos[1];
				tempPos[2] = targetPos[2];

				viper1 = new CodexActor(_lucretia.SpawnThing("viper"));
				viper1.SetCollideType(THING_COLLIDE_NONE);
				viper1.SendActorToPos(tempPos, (float)150.0);
				viper1.SetAlpha(0, 3000);

				tempPos[0] = targetPos[0] - 128; 
				tempPos[1] = targetPos[1] + 256;
				tempPos[2] = targetPos[2];

				viper2 = new CodexActor(_lucretia.SpawnThing("viper"));
				viper2.SetCollideType(THING_COLLIDE_NONE);
				viper2.SendActorToPos(tempPos, (float)150.0);
				viper2.SetAlpha(0, 3000);

				tempPos[0] = targetPos[0] - 128; 
				tempPos[1] = targetPos[1] - 256;
				tempPos[2] = targetPos[2];

				viper3 = new CodexActor(_lucretia.SpawnThing("viper"));
				viper3.SetCollideType(THING_COLLIDE_NONE);
				viper3.SendActorToPos(tempPos, (float)150.0);
				viper3.SetAlpha(0, 3000);

				tempPos[0] = targetPos[0] - 128; 
				tempPos[1] = targetPos[1] - 512;
				tempPos[2] = targetPos[2];

				viper4 = new CodexActor(_lucretia.SpawnThing("viper"));
				viper4.SetCollideType(THING_COLLIDE_NONE);
				viper4.SendActorToPos(tempPos, (float)150.0);
				viper4.SetAlpha(0, 3000);

				tempPos[0] = targetPos[0] - 128; 
				tempPos[1] = targetPos[1] + 512;
				tempPos[2] = targetPos[2];

				viper5 = new CodexActor(_lucretia.SpawnThing("viper"));
				viper5.SetCollideType(THING_COLLIDE_NONE);
				viper5.SendActorToPos(tempPos, (float)150.0);
				viper5.SetAlpha(0, 3000);

				tempPos[0] = targetPos[0] - 128; 
				tempPos[1] = targetPos[1] - 128;
				tempPos[2] = targetPos[2];

				viper6 = new CodexActor(_lucretia.SpawnThing("viper"));
				viper6.SetCollideType(THING_COLLIDE_NONE);
				viper6.SendActorToPos(tempPos, (float)150.0);
				viper6.SetAlpha(0, 3000);
				break;

			case TIMER_ID_REMOVEVIPERS:

				viper1.Remove();
				viper2.Remove();
				viper3.Remove();
				viper4.Remove();
				viper5.Remove();
				viper6.Remove();
				break;
		}
	}

	public void c29_2_LucretiaConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Boss_2.mp3", 50);
		b29_2_LucretiaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "29_2_Lucretia", "29_2_Lucretia.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c29_2_NotDeadConversation(int starterGuid, int npcGuid)
	{
		b29_2_NotDeadConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "29_2_NotDead", "29_2_NotDead.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b29_2_LucretiaConversation)
		{
			AIOn();
			b29_2_LucretiaConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_InfiltrateSetite"));
			q.Complete();

			if(humanityTrack == 1)
			{
				// add humanity here
				CodexActor Christof = new CodexActor(christofGUID);
				Christof.AddActorEffectByLevel("ef_increasehumanity", 0, 1, 0, 0);
			}
		}

		if(b29_2_NotDeadConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b29_2_NotDeadConversation = false;
			CodexCamera.Release(starterGuid);

			// add find heart quest to log
			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_FindHeart"));

			// show tower on map
			CodexSequence.SetLocationFlags("TowerOfLondon1", LOCATION_FLAG_SHOWINMAP);
			
			// open dominate for christof and lily
			CodexActor Christof = new CodexActor(christofGUID);
			CodexActor Lily = new CodexActor(lilyGUID);
			Christof.SetActorDisciplineLevel("Command", -1);
			Lily.SetActorDisciplineLevel("Command", -1);

			//String aFormat = "%A" + Christof.GetName() + "%g" + "DGRP_DOMINATE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			//String aFormat2 = "%A" + Lily.GetName() + "%g" + "DGRP_DOMINATE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat2));

			CodexSequence.OpenExit("EastLondon", 1);
			CodexSequence.OpenExit("TowerOfLondon1", 0);

			SetWeatherEffect("heavyRain");
			CodexSequence.ChangeScene("EastLondon", "EAST_32_1.nsd");

			// auto advance
			CodexSequence.Advance(christofGUID);
		}	
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b29_2_LucretiaConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							pos = _lucretia.GetFramePosition(1);
							_lucretia.SendActorToPos(pos, (float)90.0);

							CodexCamera.SetupCutscene(starterGuid, lucretiaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							CodexCamera.SetupCutscene(starterGuid, lilyGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 8);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
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

							humanityTrack = 1;

							// add conversation XP
							CodexPlayer.AwardPartyExperience(100);

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lucretiaGUID);
							_Pink.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							_Pink.StopActorAction();
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b29_2_NotDeadConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "SET4LucretiaVipers.ncp", 30);

							_lucretia.SetPosition(_lucretia.GetFramePosition(1));
							_lucretia.LookAtThing(christofGUID);

							SetTimer(1, TIMER_ID_HIDELUCRETIA);
							SetTimer((float)2.5, TIMER_ID_SPAWNVIPERS);
							SetTimer(7, TIMER_ID_REMOVEVIPERS);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}