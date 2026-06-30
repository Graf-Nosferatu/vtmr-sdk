/**
 * dev/null 33.2 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class DEVN_33_2 extends Codex
{
	private NewYorkChronicle	chronScript;

	private CodexActor			_devNull;
	private CodexActor			_Samuel;

	private int					christofGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;
	private int					devnullGUID;

	private boolean				bdevNullConversation	= false;
	public boolean				bDevReturnConversation = false;

	public static String _params[] = {"dev/null"};

	public DEVN_33_2(CodexActor devNull)
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

		_Samuel = new CodexActor(samuelGUID);
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _devNull.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.DEVN_MEETDEV))
		{
			CodexSequence.SetChronicleFlag(chronScript.DEVN_MEETDEV);
			
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
		ExecuteConversation(starterGuid, npcGuid, "33_2_MeetDev", "33_2_MeetDev.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void DevReturnConversation(int starterGuid, int npcGuid)
	{
		bDevReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "33_2_DevReturn", "33_2_DevReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bdevNullConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bdevNullConversation = false;
			CodexCamera.Release(starterGuid);

			// get rid of the transponder
			_Samuel.CancelOverrideActorWeapon();

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_Transponder"));

			// show sewers on map
			CodexSequence.SetLocationFlags("Sewers1", LOCATION_FLAG_SHOWINMAP);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// open sewers entrance
			CodexSequence.OpenExit("NewYorkDocks", 2);
			CodexSequence.OpenExit("Sewers1", 0);
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

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullOverhead.ncp", 30);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullSide.ncp", 30);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 10:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullSide.ncp", 30);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 13:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 14:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullArray.ncp", 30);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 17:

							_devNull.PlayMotionSetMode(MOTION_LISTEN, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 20:

							_devNull.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 21:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullArray.ncp", 30);
							break;

						case 22:

							// give sammy the transponder to show/hold
							_Samuel.OverrideActorWeapon("weapTransmitter", false, false);

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 23:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 24:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 25:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullArray.ncp", 30);
							break;

						case 26:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 27:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullSide.ncp", 30);
							break;

						case 28:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, lilyGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 32:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullPOV.ncp", 30);
							break;

						case 34:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 35:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullArray.ncp", 30);
							break;

						case 36:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 37:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullSide.ncp", 30);
							break;

						case 38:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 39:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 40:

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

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DevNullSide.ncp", 30);
							break;
					}
			}
		}
	}
}

