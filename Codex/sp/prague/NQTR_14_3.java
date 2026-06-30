/**
 * Northen Quarter 14.3 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class NQTR_14_3 extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexActor		_Josef;
	private CodexActor		_Wilhem;

	private int				christofGUID;
	private int				wilhemGUID;
	private int				josefGUID;

	public static String _params[] = {"Josef"};

	private boolean			bJosefConversation1	= false;
	private boolean			bJosefConversation2	= false;
	private boolean			bJosefConversation3	= false;
	private boolean			bJosefConversation4	= false;

	public NQTR_14_3(CodexActor Josef)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Josef = new CodexActor(Josef.GetGUID());

		CaptureThing(_Josef.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_Wilhem = new CodexActor(wilhemGUID);

		josefGUID = _Josef.GetGUID();
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Josef.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFVITAE))
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_JOSEFVITAE);

			// this check is here in case they haven't been to josef's before getting 
			// the reliquary mission from the Prince - if they haven't, we play a 
			// conversation that is a combination of lines from what would have been 
			// their first meeting and the lines where they'll get the elder vitae quest
			if(CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFREGION))
				JosefConversation1(clickerGuid, 0);
			else
				JosefConversation4(clickerGuid, 0);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_EcaterinaVitae"));

			CodexSequence.ChangeScene("University", "UNIV_14_3.nsd");
		}

		else if(guid == _Josef.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFTUNNELSOPEN) &&
			CodexSequence.GetChronicleFlag(chronScript.UNIV_ECATERINAVITAE))
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_JOSEFTUNNELSOPEN);

			JosefConversation2(clickerGuid, 0);
		}

		else if(guid == _Josef.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.NQTR_JOSEFVITAE) &&
			!CodexSequence.GetChronicleFlag(chronScript.UNIV_ECATERINAVITAE))
		{
			JosefConversation3(clickerGuid, 0);
		}

	}

	public void JosefConversation1(int starterGuid, int npcGuid)
	{
		bJosefConversation1 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "15_1_Josef1", "15_1_Josef1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void JosefConversation2(int starterGuid, int npcGuid)
	{
		bJosefConversation2 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "15_1_Josef2", "15_1_Josef2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void JosefConversation3(int starterGuid, int npcGuid)
	{
		bJosefConversation3 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "15_1_Josef3", "15_1_Josef3.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void JosefConversation4(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.wav", 80);
		bJosefConversation4 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "15_1_Josef4", "15_1_Josef4.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bJosefConversation1)
		{
			AIOn();
			bJosefConversation1 = false;
			CodexCamera.Release(starterGuid);
		}

		if(bJosefConversation2)
		{
			AIOn();
			bJosefConversation2 = false;
			_Josef.StopActorAction();
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_EcaterinaVitae"));
			q.Complete();

			// open the link to the graveyard
			CodexSequence.OpenExit("NQuarter", 1);
			CodexSequence.OpenExit("JosefTunnels", 0);
		}

		if(bJosefConversation3)
		{
			AIOn();
			bJosefConversation3 = false;
			CodexCamera.Release(starterGuid);
		}

		if(bJosefConversation4)
		{
			CodexSound.PopMusic();
			AIOn();
			bJosefConversation4 = false;
			CodexCamera.Release(starterGuid);
		}
	}
	
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bJosefConversation1)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bJosefConversation2)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					_Josef.OverrideActorWeapon("weapChaliceEmpty", false, false);
					_Josef.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					break;
			}
		}

		if(bJosefConversation3)
		{
			switch(curLine)
			{
				case 0:
					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bJosefConversation4)
		{
			switch(curLine)
			{
				case 0:

					_Wilhem.PlayMotionSetMode(MOTION_GESTURE4, false, (float)30.0);
					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					_Wilhem.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 7:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					break;

				case 8:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 9:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 10:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 11:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 12:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "JosefDrinksVitae.ncp", 30);
					break;

				case 13:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, josefGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}

}
