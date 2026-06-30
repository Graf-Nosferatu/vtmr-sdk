/**
 * Brothel 29.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class BROT_29_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	private int			christofGUID;
	private int			pinkGUID;
	private int			lilyGUID;

	private int			humanityTrack = 0;

	private static final int TIMER_ID_LILYCLUE	= 1;

	private boolean		b29_1_PropositionConversation = false;
	private boolean		b29_1_MeetLilyConversation = false;
	private boolean		b29_1_LilyClueConversation = false;

	private CodexRegion	_propositionRegion;
	private CodexActor	_brothelProstitute;
	private CodexPlayer	_lily;
	private	CodexRegion	_meetLilyRegion;

	public static String _params[] =	{"Proposition Region", "Prostitute", "Lily", "Meet Lily Region"};

	public BROT_29_1_Main(CodexRegion propositionRegion, CodexActor brothelProstitute, CodexActor lily, CodexRegion meetLilyRegion)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_propositionRegion	= new CodexRegion(propositionRegion.GetGUID());
		_brothelProstitute	= new CodexActor(brothelProstitute.GetGUID());
		_lily				= new CodexPlayer(lily.GetGUID());
		_meetLilyRegion = new CodexRegion(meetLilyRegion.GetGUID());

		CaptureThing(_propositionRegion.GetGUID());
		CaptureThing(_brothelProstitute.GetGUID());
		CaptureThing(_lily.GetGUID());
		CaptureThing(_meetLilyRegion.GetGUID());

		if(!CodexSequence.GetChronicleFlag(chronScript.BROT_MEETLILY))
		{
			_lily.SetActorFlags(THING_AF_TALKTO);
		}
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _propositionRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.BROT_PROSTITUTEREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.BROT_PROSTITUTEREGION);

			c29_1_PropositionConversation(causeGUID, 0);
		}

		if(guid == _meetLilyRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.BROT_MEETLILY))
		{
			CodexSequence.SetChronicleFlag(chronScript.BROT_MEETLILY);

			c29_1_MeetLilyConversation(causeGUID, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_LILYCLUE:

				c29_1_LilyClueConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}


	public void c29_1_PropositionConversation(int starterGuid, int npcGuid)
	{
		b29_1_PropositionConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "29_1_Proposition", "29_1_Proposition.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c29_1_MeetLilyConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		b29_1_MeetLilyConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "29_1_MeetLily", "29_1_MeetLily.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c29_1_LilyClueConversation(int starterGuid, int npcGuid)
	{
		b29_1_LilyClueConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "29_1_LilyClue", "29_1_LilyClue.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b29_1_PropositionConversation)
		{
			AIOn();
			b29_1_PropositionConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b29_1_MeetLilyConversation)
		{
//			AIOn();
			b29_1_MeetLilyConversation = false;

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			_lily.StopActorAction();
//			CodexCamera.Release(starterGuid);

			SetTimer((float)0.0, TIMER_ID_LILYCLUE, starterGuid);
		}

		if(b29_1_LilyClueConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b29_1_LilyClueConversation = false;
			CodexCamera.Release(starterGuid);

			_lily.AddToParty();

			CodexSequence.OpenExit("Brothel", 1);
			CodexSequence.OpenExit("SetiteTemple1", 0);

			CodexActor Christof = new CodexActor(christofGUID);

			if(humanityTrack == -1)
			{
				// lose humanity here
				Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);
			}
			else if(humanityTrack == 1)
			{
				// give humanity here
				Christof.AddActorEffectByLevel("ef_increasehumanity", 0, 1, 0, 0);			
			}
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b29_1_PropositionConversation)
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

						case 1:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;

			} // switch(curEvent)
		}

		if(b29_1_MeetLilyConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
				case 1:
					switch(curLine)
					{
						case 0:

							humanityTrack = -1;

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_lily.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							_lily.ClearActorFlags(THING_AF_TALKTO);
							break;

					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_lily.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							_lily.ClearActorFlags(THING_AF_TALKTO);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}

		if(b29_1_LilyClueConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_lily.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							_lily.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void convchosen(int starterGuid, int eventNum, int lineChosen, int lineDuration, int speakerGuid)
	{
		if(b29_1_MeetLilyConversation)
		{
			switch(eventNum)
			{
				case 2:
					switch(lineChosen)
					{
						case 2:

							humanityTrack = 1;
							break;

					} // switch(curLine)
					break;
			}
		}
	}
}

