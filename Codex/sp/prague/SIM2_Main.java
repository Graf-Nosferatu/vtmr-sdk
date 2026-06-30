/**
 * Silver Mines 2 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SIM2_Main extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing			_SilverShield;

	private boolean				bShieldConversation = false;

	public static String _params[] = {"Silver shield"};

	public SIM2_Main(CodexThing SilverShield)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_SilverShield = new CodexThing(SilverShield.GetGUID());

		CaptureThing(_SilverShield.GetGUID(), 1);
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if((captureId == 1) &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM2_SHIELDREMARKS))
		{
			CodexSequence.SetChronicleFlag(chronScript.SIM2_SHIELDREMARKS);

			ShieldConversation(picker, 0);
		}

		return(true);
	}

	public void ShieldConversation(int starterGuid, int npcGuid)
	{
		bShieldConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "5_1_Shield", "5_1_Shield.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bShieldConversation)
		{
			AIOn();
			bShieldConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
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
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 4);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}