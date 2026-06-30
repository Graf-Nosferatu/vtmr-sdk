/**
 * Christof script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class player_christof extends Codex
{
	private CodexActor	christof;
	public boolean		bWoundsConversation = false;

	public player_christof()
	{

	}

	public void beginscene(int clientGuid, int captureID)
	{
		christof = new CodexActor(CodexThing.GuidFromCastID("Christof"));
	}

	public void actorcast(int casterGUID, String disciplineName, int level, int castNum, int captureID)
	{
		if(disciplineName.equalsIgnoreCase("FeralClaws") && (castNum == 1))
		{
			// first time casting feral claws, say ingame remark
			CodexSound.PlayVoice(christof.GetGUID(), "Christof_8_2_365", 40);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
		else if(disciplineName.equalsIgnoreCase("BloodHealing") && (castNum == 1) && (CodexThing.GuidFromCastID("Wilhem")) != 0)
		{
			// first time healing himself, play conversation with wilhem
			WoundsConversation(casterGUID, 0);
		}
	}

	public void WoundsConversation(int starterGuid, int npcGuid)
	{
		bWoundsConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "8_2_Wounds", "8_2_Wounds.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bWoundsConversation)
		{
			AIOn();
			bWoundsConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
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
						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;
					case 1:
						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
