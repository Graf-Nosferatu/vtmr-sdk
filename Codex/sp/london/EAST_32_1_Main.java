/**
 * London East 32.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EAST_32_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	private int				christofGUID;
	private int				lilyGUID;
	private int				pinkGUID;

	private CodexPlayer		_Pink;

	public boolean			b31_1_AfterFightConversation = false;

	public static String _params[] =	{};

	public EAST_32_1_Main()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		_Pink = new CodexPlayer(pinkGUID);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.SET4_LUCRETIADEAD) &&
			!CodexSequence.GetChronicleFlag(chronScript.EAST_AFTERFIGHT))
		{
			CodexSequence.SetChronicleFlag(chronScript.EAST_AFTERFIGHT);

			c31_1_AfterFightConversation(causeGUID, 0);
		}
	}

	public void c31_1_AfterFightConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_2.mp3", 50);
		b31_1_AfterFightConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "31_1_AfterFight", "31_1_AfterFight.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b31_1_AfterFightConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b31_1_AfterFightConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 3:

						CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 4:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 5:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 6:

						CodexCamera.SetupCutscene(starterGuid, pinkGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 7:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 8:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 9:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 10:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 11:

						_Pink.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 12:

						_Pink.StopActorAction();
						CodexCamera.SetupCutscene(starterGuid, christofGUID, lilyGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 13:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 14:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}