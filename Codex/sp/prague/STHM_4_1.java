/**
 * St. Thomas, scene 4.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STHM_4_1 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor		_ArchBishop;
	private CodexActor		_Christof;

	private int				christofGUID;
	private int				archbishopGUID;

	private boolean			bGezaBlessingConversation = false;
	public boolean			bGeza2Conversation = false;

	public static String _params[] = {"ArchBishop"};

	public STHM_4_1(CodexActor ArchBishop)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_ArchBishop = new CodexActor(ArchBishop.GetGUID());

		CaptureThing(_ArchBishop.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		archbishopGUID = _ArchBishop.GetGUID();

		_Christof = new CodexActor(christofGUID);		
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _ArchBishop.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.STHM_GEZABLESSING))
		{
			CodexSequence.SetChronicleFlag(chronScript.STHM_GEZABLESSING);

			GezaBlessingConversation(clickerGuid, 0);
		}
		else
		{
			// play default line
			Geza2Conversation(clickerGuid, 0);
		}
	}

	public void GezaBlessingConversation(int starterGuid, int npcGuid)
	{
		bGezaBlessingConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Geza", "4_1_Geza.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Geza2Conversation(int starterGuid, int npcGuid)
	{
		bGeza2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Geza2", "4_1_Geza2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bGezaBlessingConversation)
		{
			AIOn();
			bGezaBlessingConversation = false;
			_Christof.StopActorAction();
			_ArchBishop.StopActorAction();
			CodexCamera.Release(starterGuid);

			CodexPlayer Christof = new CodexPlayer(christofGUID);
			
			// award conversation XP
			CodexPlayer.AwardPartyExperience(100);
		}

		if(bGeza2Conversation)
		{
			AIOn();
			bGeza2Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bGezaBlessingConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					_Christof.PlayMotionSetMode(MOTION_ACTION3, false, (float)30.0);
					_ArchBishop.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					break;

				case 1:

					_Christof.PlayMotionSetMode(MOTION_ACTION4, false, (float)30.0);
					_ArchBishop.StopActorAction();
					break;
			}
		}

		if(bGeza2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

}
