/**
 *  ItemShem script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemShem extends Codex
{
	private PragueChronicle	chronScript;
	private boolean			bShemLine		= false;

	public ItemShem()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public boolean pickup(int item, int picker, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.NQTR_SHEMRECOVERED))
		{
			CodexSequence.SetChronicleFlag(chronScript.NQTR_SHEMRECOVERED);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_ShemMendel"));

			CodexSequence.ChangeScene("PetrinHill", "HILL_13_1.nsd");

			ShemLine(picker, 0);
		}

		return(false);
	}

	public void ShemLine(int starterGuid, int npcGuid)
	{
		bShemLine = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "11_1_Shem", "11_1_Shem.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bShemLine)
		{
			AIOn();
			bShemLine = false;
			CodexCamera.Release(starterGuid);

			CodexThing shem = new CodexThing(GetClassThing());
			shem.Remove();
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bShemLine)
		{
			switch(curLine)
			{
				case 0:
					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}
