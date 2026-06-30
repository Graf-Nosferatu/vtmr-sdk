 /**
 * London East 28.4 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EAST_28_4_Main extends Codex
{
	private LondonChronicle	chronScript;

	private int			christofGUID;
	private int			pinkGUID;

	public CodexRegion	_feedingRegion;
	public CodexRegion	_ottoRegion;
	public CodexRegion	_theatreRegion;

	public boolean		b28_4_UpdateConversation = false;
	public boolean		b28_4_FeedingConversation = false;
	public boolean		b28_4_FirstOttoConversation = false;
	public boolean		b28_4_OutsideTheatreConversation = false;

	public static String _params[] =	{"Feeding Region", "Otto Region", "Theatre Region"};

	public EAST_28_4_Main(CodexRegion feedingRegion, CodexRegion ottoRegion, CodexRegion theatreRegion)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_feedingRegion = new CodexRegion(feedingRegion.GetGUID());
		_ottoRegion = new CodexRegion(ottoRegion.GetGUID());
		_theatreRegion = new CodexRegion(theatreRegion.GetGUID());

		CaptureThing(_feedingRegion.GetGUID());
		CaptureThing(_ottoRegion.GetGUID());
		CaptureThing(_theatreRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		
		if(!CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.EAST_UPDATE))
		{
			CodexSequence.SetChronicleFlag(chronScript.EAST_UPDATE);

			c28_4_UpdateConversation(clientGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK))
			return;

		if((guid == _feedingRegion.GetGUID()) && 
			!CodexSequence.GetChronicleFlag(chronScript.EAST_FEEDINGREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.EAST_FEEDINGREGION);

			c28_4_FeedingConversation(causeGUID, 0);
		}

		if((guid == _ottoRegion.GetGUID()) && 
			!CodexSequence.GetChronicleFlag(chronScript.EAST_OTTOREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.EAST_OTTOREGION);

			c28_4_FirstOttoConversation(causeGUID, 0);
		}

		if((guid == _theatreRegion.GetGUID()) && 
			!CodexSequence.GetChronicleFlag(chronScript.EAST_THEATREREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.EAST_THEATREREGION);

			c28_4_OutsideTheatreConversation(causeGUID, 0);
		}
	}

	public void c28_4_UpdateConversation(int starterGuid, int npcGuid)
	{
		b28_4_UpdateConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_4_Update", "28_4_Update.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c28_4_FeedingConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		b28_4_FeedingConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "28_4_Feeding", "28_4_Feeding.nco", CONV_XFLAG_WANTFEEDBACK);
	}


	public void c28_4_FirstOttoConversation(int starterGuid, int npcGuid)
	{
		b28_4_FirstOttoConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "28_4_FirstOtto", "28_4_FirstOtto.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void c28_4_OutsideTheatreConversation(int starterGuid, int npcGuid)
	{
		b28_4_OutsideTheatreConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "28_4_OutsideTheatre", "28_4_OutsideTheatre.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b28_4_UpdateConversation)
		{
			AIOn();
			b28_4_UpdateConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b28_4_FeedingConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b28_4_FeedingConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b28_4_FirstOttoConversation)
		{
			AIOn();
			b28_4_FirstOttoConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b28_4_OutsideTheatreConversation)
		{
			AIOn();
			b28_4_OutsideTheatreConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b28_4_UpdateConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							// add conversation XP
							CodexPlayer.AwardPartyExperience(50);

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b28_4_FeedingConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b28_4_FirstOttoConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b28_4_OutsideTheatreConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}
	}

}
