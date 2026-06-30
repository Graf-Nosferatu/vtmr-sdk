/**
 * dev/null 36.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class DEVN_36_1 extends Codex
{
	private NewYorkChronicle	chronScript;

	private 	CodexActor		_devNull;

	private int					christofGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;
	private int					devnullGUID;

	private boolean			bdevNullConversation	= false;
	public boolean			bDevReturnConversation = false;

	public static String _params[] = {"dev/null"};

	public DEVN_36_1(CodexActor devNull)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_devNull = new CodexActor(devNull.GetGUID());

		CaptureThing(_devNull.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		devnullGUID = _devNull.GetGUID();
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _devNull.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.DEVN_GETCODES))
		{
			CodexSequence.SetChronicleFlag(chronScript.DEVN_GETCODES);

			devNullConversation(clickerGuid, 0);
		}
		else
		{
			// play default line here
			DevReturnConversation(clickerGuid, 0);
		}
	}

	public void devNullConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		bdevNullConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "36_1_GetCodes", "36_1_GetCodes.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void DevReturnConversation(int starterGuid, int npcGuid)
	{
		bDevReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "36_1_DevReturn", "36_1_DevReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bdevNullConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bdevNullConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_GetAccessCodes.nqd"));
			q.Complete();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("N1_UseAccessCodes"));

			// show warehouse on map
			CodexSequence.SetLocationFlags("GiovanniWarehouse1", LOCATION_FLAG_SHOWINMAP);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// open warehouse entrance
			CodexSequence.OpenExit("NewYorkDocks", 5);
			CodexSequence.OpenExit("GiovanniWarehouse1", 0);
		}

		if(bDevReturnConversation)
		{
			AIOn();
			bDevReturnConversation = false;
			CodexCamera.Release(starterGuid);	
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bdevNullConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 8:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 12:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullSide.ncp", 30);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 17:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 18:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 19:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 20:

							//CodexCamera.SetupCutscene(starterGuid, christofGUID, devnullGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 21:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 22:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bDevReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;
					}
			}		
		}
	}
}

