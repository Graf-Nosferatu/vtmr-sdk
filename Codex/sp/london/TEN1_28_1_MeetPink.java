/**
 * Club Tenebrae 28.1 Meet Pink script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TEN1_28_1_MeetPink extends Codex
{
	private LondonChronicle	chronScript;

	public boolean			b28_1_MeetPink1Conversation = false;

	private CodexPlayer		_Pink;
	private CodexActor		_Christof;

	private int				christofGUID;
	private int				pinkGUID;

	public static String _params[] =	{"Pink"};

	public TEN1_28_1_MeetPink(CodexPlayer pink)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_Pink = new CodexPlayer(pink.GetGUID());

		CaptureThing(_Pink.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		_Christof		= new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK))
		{
			_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
			_Pink.SetActorFlags(THING_AF_TALKTO);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK))
		{
			CodexSequence.SetChronicleFlag(chronScript.TEN1_MEETPINK);

			c28_1_MeetPink1Conversation(clickerGuid, 0);
		}
	}

	public void c28_1_MeetPink1Conversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		b28_1_MeetPink1Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_1_MeetPink1", "28_1_MeetPink1.nco", CONV_XFLAG_WANTFEEDBACK);

		// add conversation XP
		CodexPlayer.AwardPartyExperience(100);
	}

	public void convended(int starterGuid,  boolean bAborted, int returnValue)
	{
		if(b28_1_MeetPink1Conversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b28_1_MeetPink1Conversation = false;

			// Get Pink out of the bar
			_Pink.SetPosition(_Pink.GetFramePosition(1));

			_Pink.StopActorAction();
			_Pink.CancelOverrideActorWeapon();
			CodexCamera.Release(starterGuid);
			CodexSequence.ChangeScene("WestLondon", "WEST_28_4.nsd");
			CodexSequence.ChangeScene("EastLondon", "EAST_28_4.nsd");
			CodexSequence.OpenExit("EastLondon", 4);
			CodexSequence.OpenExit("Brothel", 0);

			SetWeatherEffect("lightRain");

			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_InfiltrateSetite"));

			// show setite brothel on map
			CodexSequence.SetLocationFlags("Brothel", LOCATION_FLAG_SHOWINMAP);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						_Pink.OverrideActorWeapon("weapMug", false, false);
						_Pink.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 3:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 4:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 5:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 6:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 7:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 8:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 9:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 10:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 11:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 12:

						_Christof.StopActorAction();
						_Pink.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 13:

						_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 14:

						_Christof.StopActorAction();
						_Pink.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 15:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 16:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 17:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 18:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 19:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 20:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 21:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 22:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;

			case 1:
				switch(curLine)
				{
					case 0:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;
				}
				break;

			case 2:
				switch(curLine)
				{
					case 0:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

						// Add Pink to the party
						_Pink.AddToParty();
						_Pink.ClearActorFlags(THING_AF_TALKTO);
						break;

					case 1:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 3:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 4:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 5:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 6:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 7:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 8:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 9:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 10:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 11:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 12:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 13:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 14:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 15:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 16:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 17:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 18:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 19:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL5, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 20:

						_Pink.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;
	
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}