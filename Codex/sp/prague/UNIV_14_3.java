/**
 * University 14.3 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_14_3 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int	TIMER_ID_ENDCONVERSATION	= 1;

	private CodexActor		_Ecaterina;

	private int				christofGUID;
	private int				ecaterinaGUID;

	private boolean			bEcatBloodConversation	= false;
	public boolean			bEcatReturn1Conversation	= false;

	public static String _params[] = {"Ecaterina"};

	public UNIV_14_3(CodexActor Ecaterina)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
		_Ecaterina = new CodexActor(Ecaterina.GetGUID());

		CaptureThing(_Ecaterina.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		ecaterinaGUID = _Ecaterina.GetGUID();
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ecaterina.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.UNIV_ECATERINAVITAE))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_ECATERINAVITAE);

			EcatBloodConversation(clickerGuid, 0);
		}
		else
		{
			// play default lines here
			EcatReturn1Conversation(clickerGuid, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ENDCONVERSATION:

				AIOn();
				bEcatBloodConversation = false;
				CodexCamera.Release(christofGUID);

				_Ecaterina.CancelOverrideActorWeapon();
				_Ecaterina.StopActorAction();
				break;
		}
	}

	public void EcatBloodConversation(int starterGuid, int npcGuid)
	{
		bEcatBloodConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "15_1_EcatBlood", "15_1_EcatBlood.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EcatReturn1Conversation(int starterGuid, int npcGuid)
	{
		bEcatReturn1Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_1_EcatReturn1", "8_1_EcatReturn1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		//if(bEcatBloodConversation)
		//{
			//AIOn();
			//bEcatBloodConversation = false;
			//CodexCamera.Release(starterGuid);

			//_Ecaterina.StopActorMotion();
		//}
	
		if(bEcatReturn1Conversation)
		{
			AIOn();
			bEcatReturn1Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bEcatBloodConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					_Ecaterina.OverrideActorWeapon("weapChalice", false, false);
					_Ecaterina.PlayMotionSetMode(MOTION_GESTURE4, false, (float)30.0);

					SetTimer(7, TIMER_ID_ENDCONVERSATION);

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	
		if(bEcatReturn1Conversation)
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

