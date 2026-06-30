/**
 * Petrin Hill  main script 2.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HILL_Main2 extends Codex
{
	/////////////////////////////////////////////////////////////////////////////////////////
	// ALERT!!! THIS SCRIPT IS NOT JUST USED IN P1_HILL BUT ALSO IN P1_HAVN, SO IF THERE IS 
	// A CHANGE HERE MAKE SURE YOU THINK THROUGH WHETHER IT WILL WORK IN THE HAVEN AS WELL
	/////////////////////////////////////////////////////////////////////////////////////////

	private PragueChronicle	chronScript;

	private CodexActor		_Christof;
	private CodexActor		_Wilhem;
	
	private int				christofGUID;
	private int				wilhemGUID;

	private boolean			bHillConversation		= false;


	public HILL_Main2()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_Christof = new CodexActor(christofGUID);
		_Wilhem = new CodexActor(wilhemGUID);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.MON3_NODFRAGMENTRECOVERED) &&
			!CodexSequence.GetChronicleFlag(chronScript.HILL_REGION2))
		{
			CodexSequence.SetChronicleFlag(chronScript.HILL_REGION2);

			HillConversation(causeGUID, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexSequence.Jump("AnezkaRoom", 0);
	}
	
	public void HillConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bHillConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Return", "8_2_Return.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bHillConversation)
		{
			CodexSound.PopMusic();
			bHillConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			CodexSequence.ChangeScene("AnezkaRoom", "ANZK_8_4.nsd");

			SetTimer((float)1.5);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bHillConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Wilhem.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
					break;

				case 5:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Wilhem.StopActorAction();
					break;

				case 6:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Wilhem.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
					break;

				case 7:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Wilhem.StopActorAction();
					break;

				case 8:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 9:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 10:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}	
}

