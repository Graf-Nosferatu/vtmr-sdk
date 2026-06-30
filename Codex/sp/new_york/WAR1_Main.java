/**
 * Warehouse 1 Main 36.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WAR1_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int TIMER_ID_ENTRYLINES	= 1;

	private CodexRegion		_SmackRegion;

	private int				christofGUID;
	private int				lilyGUID;
	private int				pinkGUID;
	private int				samuelGUID;

	private boolean			bEntryConversation	= false;
	private boolean			bSmackConversation = false;

	public static String _params[] = {"Smack region"};

	public WAR1_Main(CodexRegion SmackRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_SmackRegion = new CodexRegion(SmackRegion.GetGUID());

		CaptureThing(_SmackRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.WAR1_ENTRYLINES))
		{
			CodexSequence.SetChronicleFlag(chronScript.WAR1_ENTRYLINES);

			SetTimer(2, TIMER_ID_ENTRYLINES, clientGuid);
		}

		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _SmackRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.WAR1_SMACK))
		{
			CodexSequence.SetChronicleFlag(chronScript.WAR1_SMACK);

			SmackConversation(causeGUID, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ENTRYLINES:

				EntryConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}

	public void EntryConversation(int starterGuid, int npcGuid)
	{
		bEntryConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "36_1_Entry", "36_1_Entry.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void SmackConversation(int starterGuid, int npcGuid)
	{
		bSmackConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "36_1_Smack", "36_1_Smack.nco", CONV_XFLAG_WANTFEEDBACK);
	}


	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bEntryConversation)
		{
			AIOn();
			bEntryConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bSmackConversation)
		{
			AIOn();
			bSmackConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bEntryConversation)
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

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bEntryConversation)

		if(bSmackConversation)
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

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, samuelGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bSmackConversation)
	}
}

