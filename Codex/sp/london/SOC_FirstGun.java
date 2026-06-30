/**
 * SOC_FirstGun 27.2 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC_FirstGun extends Codex
{
	private LondonChronicle	chronScript;

	public boolean			bGunConversation = false;

	public SOC_FirstGun()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.SOC_FIRSTGUN))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOC_FIRSTGUN);

			GunConversation(picker, 0);
		}

		return(true);
	}
	
	public void GunConversation(int starterGuid, int npcGuid)
	{
		bGunConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "27_2_Gun", "27_2_Gun.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bGunConversation)
		{
			AIOn();
			bGunConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bGunConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

