/**
 * Old Town 7.7 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_7_7 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_UNIVERSITY	= 1;
	private static final int TIMER_ID_CONVERSE		= 2;

	private	CodexRegion		_havenRegion;
	private CodexRegion		_converseRegion;
	private CodexActor		_Christof;

	private int				christofGUID;
	private int				wilhemGUID;

	public boolean			bConverseConversation = false;
	public boolean			bHavenConversation = false;

	public static String _params[] = {"Haven conversation region"};

	public OLDT_7_7(CodexRegion havenRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_havenRegion = new CodexRegion(havenRegion.GetGUID());
		_converseRegion = new CodexRegion(GetClassThing());

		CaptureThing(_havenRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		// if they came from the ANZK_8_4 scene, wilhem will be hidden, show him here once
		if(CodexSequence.GetChronicleFlag(chronScript.ANZK_LOCKETSCENE) &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_SHOWWILHEM))
		{
			CodexSequence.GetChronicleFlag(chronScript.OLDT_SHOWWILHEM);

			CodexActor wilhem = new CodexActor(wilhemGUID);

			// Fade in from Anezka 8_4_Visit scene
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);

			// check to see if he's dead, if he's not unset these flags
			if((wilhem.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				wilhem.ClearActorFlags(THING_AF_AIPAUSED);
				wilhem.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				wilhem.SetCollideType(THING_COLLIDE_CYL);
			}
		}

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_WILHEMCONVERSE))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_WILHEMCONVERSE);
		
			// change scene on bridge for vampire training
			CodexSequence.ChangeScene("JudithBridge", "JUDB_7_8.nsd");

			ConverseConversation(clientGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _havenRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_HAVEN))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_HAVEN);
		
			HavenConversation(causeGUID, 0);
		}
		else if(guid == _converseRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_WILHEMCONVERSE))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_WILHEMCONVERSE);
		
			// change scene on bridge for vampire training
			CodexSequence.ChangeScene("JudithBridge", "JUDB_7_8.nsd");

			ConverseConversation(causeGUID, 0);
		}
	}

	public void ConverseConversation(int starterGuid, int npcGuid)
	{
		bConverseConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "7_8_Converse", "7_8_Converse.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void HavenConversation(int starterGuid, int npcGuid)
	{
		bHavenConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "7_8_Haven", "7_8_Haven.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bConverseConversation)
		{
			AIOn();
			bConverseConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bHavenConversation)
		{
			AIOn();
			bHavenConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bConverseConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.StopActorAction();
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bHavenConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 1, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}