/**
 * University scene 8.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_8_1 extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexActor		_Christof;
	private	CodexActor		_Ecaterina;
	
	private int				christofGUID;
	private int				ecaterinaGUID;

	private boolean			bEcatConversation = false;
	public boolean			bEcatReturn2Conversation	= false;

	public static String _params[] = {"Ecaterina"};

	public UNIV_8_1(CodexActor Ecaterina)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Ecaterina = new CodexActor(Ecaterina.GetGUID());

		CaptureThing(_Ecaterina.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		ecaterinaGUID = _Ecaterina.GetGUID();

		_Christof = new CodexActor(christofGUID);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ecaterina.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.UNIV_CHRISTOFDESTROY))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_CHRISTOFDESTROY);

			EcatConversation(clickerGuid, 0);
		}
		else
		{
			// say default line here
			EcatReturn2Conversation(clickerGuid, 0);
		}
	}
	
	public void EcatConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bEcatConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_1_Ecat", "8_1_Ecat.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EcatReturn2Conversation(int starterGuid, int npcGuid)
	{
		bEcatReturn2Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "8_1_EcatReturn2", "8_1_EcatReturn2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bEcatConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bEcatConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}

		if(bEcatReturn2Conversation)
		{
			AIOn();
			bEcatReturn2Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bEcatConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.StopActorAction();
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.StopActorAction();
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bEcatReturn2Conversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}
