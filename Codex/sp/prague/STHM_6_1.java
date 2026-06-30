/**
 * St. Thomas, scene 6.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STHM_6_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_LINES			= 1;

	private CodexActor		_ArchBishop;

	private int				christofGUID;
	private int				archbishopGUID;

	private boolean			bGezaConversation = false;
	public boolean			bGezaNightConversation = false;

	public static String _params[] = {"ArchBishop"};

	public STHM_6_1(CodexActor ArchBishop)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_ArchBishop = new CodexActor(ArchBishop.GetGUID());

		CaptureThing(_ArchBishop.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		archbishopGUID = _ArchBishop.GetGUID();
		
		if(!CodexSequence.GetChronicleFlag(chronScript.STHM_HERORETURN))
		{
			CodexSequence.SetChronicleFlag(chronScript.STHM_HERORETURN);

			_ArchBishop.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			SetTimer(1, TIMER_ID_LINES, christofGUID);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.OLDT_FLYTHROUGH))
		{
			_ArchBishop.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		// this chron flag is set in beginscene of oldt_7_1
		// and tells this script it's now "night time", so play
		// the correct archbishop conversation/line
		if(CodexSequence.GetChronicleFlag(chronScript.OLDT_FLYTHROUGH))
		{
			GezaNightConversation(clickerGuid, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_LINES:

				CodexQuest q = new CodexQuest(CodexQuest.Load("P1_Geza"));
				q.Complete();

				CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_AnezkaVisit"));

				GezaConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}

	public void GezaConversation(int starterGuid, int npcGuid)
	{
		bGezaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_Geza1", "6_1_Geza1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GezaNightConversation(int starterGuid, int npcGuid)
	{
		bGezaNightConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "7_1_Geza", "7_1_Geza.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bGezaConversation)
		{
			AIOn();
			bGezaConversation = false;
			CodexCamera.Release(starterGuid);

			CodexSequence.ChangeScene("ConventDay", "CTDY_6_1.nsd");
			CodexSequence.ChangeScene("OldTownDay", "OTDY_6_1.nsd");

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bGezaNightConversation)
		{
			AIOn();
			bGezaConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bGezaConversation)
		{
			switch(curLine)
			{
				case 0:

					_ArchBishop.OverrideActorWeapon("weapCross", false, false);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					_ArchBishop.PlayMotionSetMode(MOTION_GESTURE3, false, (float)10.0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					_ArchBishop.StopActorAction();
					_ArchBishop.CancelOverrideActorWeapon();
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bGezaNightConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, archbishopGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}
