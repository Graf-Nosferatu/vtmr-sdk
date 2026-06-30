/**
 * Barclay South Lobby 37.4 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOBY_37_4 extends Codex
{
	private NewYorkChronicle	chronScript;

	private CodexActor		_Fred;

	private int				christofGUID;
	private int				fredGUID;
	private int				lilyGUID;
	private int				samuelGUID;
	private int				wilhemGUID;

	private boolean			bFredConversation	= false;
	private boolean			bFredReturnConversation	= false;

	public static String _params[] = {"Fred"};

	public LOBY_37_4(CodexActor Fred)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Fred = new CodexActor(Fred.GetGUID());

		CaptureThing(_Fred.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		fredGUID = _Fred.GetGUID();
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Fred.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.LOBY_FREDVARNEY))
		{
			CodexSequence.SetChronicleFlag(chronScript.LOBY_FREDVARNEY);

			FredConversation(clickerGuid, 0);
		}
		else
		{
			// play default line here
			FredReturnConversation(clickerGuid, 0);
		}
	}

	public void FredConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		bFredConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_MeetFred", "37_4_MeetFred.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void FredReturnConversation(int starterGuid, int npcGuid)
	{
		bFredReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_FredReturn", "37_4_FredReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bFredConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bFredConversation = false;
			CodexCamera.Release(starterGuid);

			// open penthouse entrance
			CodexSequence.OpenExit("BarclayLobby", 1);
			CodexSequence.OpenExit("OrsiPenthouse", 0);
		}

		if(bFredReturnConversation)
		{
			AIOn();
			bFredReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bFredConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, fredGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, fredGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, fredGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, fredGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, fredGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, fredGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, fredGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 13:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 14:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, fredGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 17:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bFredReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, fredGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;
					}
			}
		}
	}
}

