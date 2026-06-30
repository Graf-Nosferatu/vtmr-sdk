/**
 * University 25.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_25_1 extends Codex
{
	private Prague2Chronicle chronScript;

	private CodexActor		_Ecaterina;

	private int				christofGUID;
	private int				ecaterinaGUID;
	private int				serenaGUID;
	private int				wilhemGUID;
	
	private boolean			bEcaterinaConversation = false;

	public static String _params[] = {"Ecaterina"};

	public UNIV_25_1(CodexActor Ecaterina)
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);

		_Ecaterina = new CodexActor(Ecaterina.GetGUID());
		
		CaptureThing(_Ecaterina.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		ecaterinaGUID = _Ecaterina.GetGUID();
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ecaterina.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.UNIV_ECATERINAVYS))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_ECATERINAVYS);

			EcaterinaConversation(clickerGuid, 0);
		}
	}

	public void EcaterinaConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bEcaterinaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "25_1_Ecaterina", "25_1_Ecaterina.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		CodexSound.PopMusic();
		AIOn();
		bEcaterinaConversation = false;
		CodexCamera.Release(starterGuid);

		CodexQuest q = new CodexQuest(CodexQuest.Load("P2_Ecaterina"));
		q.Complete();

		CodexQuest q2 = new CodexQuest(CodexQuest.Load("P2_Infiltrate"));

		// show vysehrad on map
		CodexSequence.SetLocationFlags("VysMountain", LOCATION_FLAG_SHOWINMAP);

		// add conversation XP
		CodexPlayer.AwardPartyExperience(50);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

					case 2:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 3:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 4:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 5:

						CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 6:

						CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 7:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

