/**
 * Judith Bridge, scene 7.8 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class JUDB_7_8 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexRegion		_FeedRegion;
	private CodexActor		_PedestrianFeedee;
	private CodexRegion		_VisitRegion;

	private int				christofGUID;
	private int				wilhemGUID;

	private CodexPlayer		_Christof;
	private CodexPlayer		_Wilhem;

	private static final int TIMER_ID_STOPFEEDING	= 3;
	private static final int TIMER_ID_AFTERFEED		= 4;

	public boolean		bTrainFeedConversation = false;
	public boolean		bTrainFeedAfterConversation = false;
	public boolean		bVisitConversation = false;

	public static String _params[] = {"Training Feed region", "Pedestrian to feed on", "Visit region"};

	public JUDB_7_8(CodexRegion FeedRegion, CodexActor PedestrianFeedee, CodexRegion VisitRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_FeedRegion = new CodexRegion(FeedRegion.GetGUID());
		_PedestrianFeedee = new CodexActor(PedestrianFeedee.GetGUID());
		_VisitRegion = new CodexRegion(VisitRegion.GetGUID());

		CaptureThing(_FeedRegion.GetGUID());
		CaptureThing(_PedestrianFeedee.GetGUID());
		CaptureThing(_VisitRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
	
		_Christof = new CodexPlayer(christofGUID);
		_Wilhem = new CodexPlayer(wilhemGUID);

		CaptureThing(christofGUID);
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.JUDB_VISITCONVERSATION))
		{
			// remove him from the scene - if they skip the feeding of the old man
			// he'll just stand there for the rest of prague, this will simply
			// remove him on endscene, but only if they've had the visit conversation
			// at the end of the bridge - don't want to remove him early
			_PedestrianFeedee.Remove();
		}
	}

	public void actorfeed(int actorGUID, int targetGUID, int status, int captureID)
	{
		if((actorGUID == christofGUID) &&
			(targetGUID == _PedestrianFeedee.GetGUID()))
		{
			//ACTORFEED_STATUS_START
			if(status == 0)
			{
				// set a timer to force him to stop 5 seconds later
				SetTimer(5, TIMER_ID_STOPFEEDING);
			}
			//ACTORFEED_STATUS_STOPPED
			else if(status == 2)
			{
				SetTimer(4, TIMER_ID_AFTERFEED, christofGUID);
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_STOPFEEDING:

				CodexActor Christof = new CodexActor(christofGUID);
				Christof.StopFeeding();

				SetTimer(4, TIMER_ID_AFTERFEED, christofGUID);
				break;

			case TIMER_ID_AFTERFEED:

				// play second half of conversation if we haven't yet
				if(!CodexSequence.GetChronicleFlag(chronScript.JUDB_AFTERFEEDCONV))
				{
					CodexSequence.SetChronicleFlag(chronScript.JUDB_AFTERFEEDCONV);
				
					TrainFeedAfterConversation(CodexPlayer.GetCurrentPlayer(), 0);

					// walk the ped away from wilhem and christof
					_PedestrianFeedee.SendActorToPos(_PedestrianFeedee.GetFramePosition(2), (float)40.0);
				}
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(thingGuid == _PedestrianFeedee.GetGUID() && 
			CodexSequence.GetChronicleFlag(chronScript.JUDB_AFTERFEEDCONV))
		{
			// remove him from the scene
			_PedestrianFeedee.Remove();
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _FeedRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.JUDB_TRAINFEED))
		{
			CodexSequence.SetChronicleFlag(chronScript.JUDB_TRAINFEED);

			// start the ped walking toward wilhem and christof
			_PedestrianFeedee.SendActorToPos(_PedestrianFeedee.GetFramePosition(1), (float)40.0);

			TrainFeedConversation(causeGUID, 0);
		}

		if(guid == _VisitRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.JUDB_VISITCONVERSATION))
		{
			CodexSequence.SetChronicleFlag(chronScript.JUDB_VISITCONVERSATION);

			VisitConversation(causeGUID, 0);
		}
	}

	public void TrainFeedConversation(int starterGuid, int npcGuid)
	{
		bTrainFeedConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "7_8_TrainFeed", "7_8_TrainFeed.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void TrainFeedAfterConversation(int starterGuid, int npcGuid)
	{
		bTrainFeedAfterConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "7_8_TrainFeedAfter", "7_8_TrainFeedAfter.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void VisitConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bVisitConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "7_8_Visit", "7_8_Visit.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bTrainFeedConversation)
		{
			AIOn();
			bTrainFeedConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);

			// make them control christof to let them feed
			_Christof.SetCurrentPlayer();
		}

		if(bTrainFeedAfterConversation)
		{
			AIOn();
			bTrainFeedAfterConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}

		if(bVisitConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bVisitConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bTrainFeedConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		}

		if(bTrainFeedAfterConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		}

		if(bVisitConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)		
		}
	}
}
