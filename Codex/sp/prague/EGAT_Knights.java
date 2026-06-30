/**
 * EGAT_Knights script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EGAT_Knights extends Codex
{
	private PragueChronicle	chronScript;

	private final static int	TIMER_ID_GREETKNIGHTS = 0;

	private int				christofGUID;
	private int				heckler1GUID;
	private int				heckler2GUID;

	public boolean			bGateKnightsConversation = false;
	public boolean			bGateKnights2Conversation = false;
	public boolean			bGateKnights3Conversation = false;

	public EGAT_Knights()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		heckler1GUID = CodexThing.GuidFromCastID("StJohn3");
		heckler2GUID = CodexThing.GuidFromCastID("StJohn4");
		
		if(!CodexSequence.GetChronicleFlag(chronScript.EGAT_FIRSTKNIGHTS))
		{
			CodexSequence.SetChronicleFlag(chronScript.EGAT_FIRSTKNIGHTS);

			SetTimer(1, TIMER_ID_GREETKNIGHTS);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_GREETKNIGHTS:

				GateKnightsConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.SIM1_STENCHREGION) &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD) &&
			!CodexSequence.GetChronicleFlag(chronScript.EGAT_KNIGHTSCHIDE))
		{
			CodexSequence.SetChronicleFlag(chronScript.EGAT_KNIGHTSCHIDE);

			GateKnights2Conversation(causeGUID, 0);
		}
		else if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD) &&
			!CodexSequence.GetChronicleFlag(chronScript.EGAT_KNIGHTSAHZRA))
		{
			CodexSequence.SetChronicleFlag(chronScript.EGAT_KNIGHTSAHZRA);

			GateKnights3Conversation(causeGUID, 0);
		}
	}

	public void GateKnightsConversation(int starterGuid, int npcGuid)
	{
		bGateKnightsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_GateKnights", "4_1_GateKnights.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GateKnights2Conversation(int starterGuid, int npcGuid)
	{
		bGateKnights2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "5_1_GateKnights", "5_1_GateKnights.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GateKnights3Conversation(int starterGuid, int npcGuid)
	{
		bGateKnights3Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_GateKnights", "6_1_GateKnights.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bGateKnightsConversation)
		{
			AIOn();
			bGateKnightsConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bGateKnights2Conversation)
		{
			AIOn();
			bGateKnights2Conversation = false;
			CodexCamera.Release(starterGuid);
		}
		
		if(bGateKnights3Conversation)
		{
			AIOn();
			bGateKnights3Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bGateKnightsConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, heckler2GUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, heckler2GUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
		
		if(bGateKnights2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, heckler2GUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bGateKnights3Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, heckler2GUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}