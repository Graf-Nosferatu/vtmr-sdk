/**
 * Old Town Day 6.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OTDY_6_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_RESIDENTS		= 0;
	private static final int TIMER_ID_CAMERARELEASE = 1;

	private CodexActor			_Pedestrian1;
	private CodexActor			_Pedestrian2;
	private CodexActor			_Pedestrian3;
	private CodexActor			_Pedestrian4;

	private int					christofGUID;
	private int					oldmancheerGUID;
	private int					merchantcheerGUID;
	private int					milkmaidcheerGUID;
	private int					youngladycheerGUID;

	private boolean				bResidentsConversation = false;

	public static String _params[] = {"Pedestrian 1", "Pedestrian 2", "Pedestrian 3", "Pedestrian 4"};

	public OTDY_6_1(CodexActor Pedestrian1, CodexActor Pedestrian2, CodexActor Pedestrian3, CodexActor Pedestrian4)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Pedestrian1 = new CodexActor(Pedestrian1.GetGUID());
		_Pedestrian2 = new CodexActor(Pedestrian2.GetGUID());
		_Pedestrian3 = new CodexActor(Pedestrian3.GetGUID());
		_Pedestrian4 = new CodexActor(Pedestrian4.GetGUID());

		CaptureThing(_Pedestrian1.GetGUID());
		CaptureThing(_Pedestrian2.GetGUID());
		CaptureThing(_Pedestrian3.GetGUID());
		CaptureThing(_Pedestrian4.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.OTDY_RESIDENTS))
		{
			christofGUID = CodexThing.GuidFromCastID("Christof");
			milkmaidcheerGUID = _Pedestrian1.GetGUID();
			oldmancheerGUID = _Pedestrian2.GetGUID();
			youngladycheerGUID = _Pedestrian3.GetGUID();
			merchantcheerGUID = _Pedestrian4.GetGUID();
			
			// pause the "crowd's" AI
			_Pedestrian1.SetActorFlags(THING_AF_AIPAUSED);
			_Pedestrian2.SetActorFlags(THING_AF_AIPAUSED);
			_Pedestrian3.SetActorFlags(THING_AF_AIPAUSED);
			_Pedestrian4.SetActorFlags(THING_AF_AIPAUSED);

			CodexSequence.SetChronicleFlag(chronScript.OTDY_RESIDENTS);

			SetTimer(2, TIMER_ID_RESIDENTS, clientGuid);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_RESIDENTS:

				ResidentsConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_CAMERARELEASE:

				CodexCamera.Release(christofGUID);
				break;
		}
	}

	public void ResidentsConversation(int starterGuid, int npcGuid)
	{
		bResidentsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_Residents", "6_1_Residents.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bResidentsConversation)
		{
			AIOn();
			bResidentsConversation = false;
			//CodexCamera.Release(starterGuid);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OTDYResidents.ncp", 30);
			SetTimer(11, TIMER_ID_CAMERARELEASE);
			CodexSound.PlayVoice(christofGUID, "Christof_6_5_167", 40);

			// stop cheering, you fools!
			_Pedestrian1.StopActorAction();
			_Pedestrian2.StopActorAction();
			_Pedestrian3.StopActorAction();
			_Pedestrian4.StopActorAction();

			// release the peds to wander normally
			_Pedestrian1.ClearActorFlags(THING_AF_AIPAUSED);
			_Pedestrian2.ClearActorFlags(THING_AF_AIPAUSED);
			_Pedestrian3.ClearActorFlags(THING_AF_AIPAUSED);
			_Pedestrian4.ClearActorFlags(THING_AF_AIPAUSED);
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

						CodexCamera.SetupCutscene(starterGuid, merchantcheerGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						_Pedestrian1.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						_Pedestrian2.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						_Pedestrian3.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						_Pedestrian4.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
						_Pedestrian1.StopActorAction();
						break;

					case 3:

						CodexCamera.SetupCutscene(starterGuid, oldmancheerGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						_Pedestrian1.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						break;

					case 4:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, youngladycheerGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

					case 5:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
						_Pedestrian1.StopActorAction();
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}