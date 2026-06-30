/**
 * Old Town 18.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_18_1 extends Codex
{
	private PragueChronicle	chronScript;

	private int			christofGUID;
	private int			erikGUID;
	private	int			serenaGUID;
	private int			wilhemGUID;

	public boolean		bLeaveConversation = false;
	public boolean		bLeave2Conversation = false;

	public OLDT_18_1()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		CodexActor Wilhem = new CodexActor(wilhemGUID);
		CodexActor Erik = new CodexActor(erikGUID);
		CodexActor Serena = new CodexActor(serenaGUID);

		// show other party members for this scene, invis from UNIV_18_1
		Erik.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
		Erik.SetCollideType(THING_COLLIDE_CYL);
		Erik.ClearActorFlags(THING_AF_AIPAUSED);

		Serena.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
		Serena.SetCollideType(THING_COLLIDE_CYL);
		Serena.ClearActorFlags(THING_AF_AIPAUSED);
		
		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_FINALCONVERSATION))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_FINALCONVERSATION);

			if(!CodexSequence.GetChronicleFlag(chronScript.UNIV_DERAIL))
				LeaveConversation(clientGuid, 0);
			else
				Leave2Conversation(clientGuid, 0);
		}
	}

	public void LeaveConversation(int starterGuid, int npcGuid)
	{
		bLeaveConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "18_1_Leave", "18_1_Leave.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Leave2Conversation(int starterGuid, int npcGuid)
	{
		bLeave2Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "18_1_Leave2", "18_1_Leave2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLeaveConversation)
		{
			AIOn();
			bLeaveConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bLeave2Conversation)
		{
			AIOn();
			bLeave2Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		// award conversation XP
		CodexPlayer.AwardPartyExperience(50);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bLeaveConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bLeave2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}