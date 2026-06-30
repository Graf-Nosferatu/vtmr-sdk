/**
 * Josef 1 15.1 Derelict script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class JOS1_15_1_Derelict extends Codex
{
	public boolean		b15_1_DerelictConversation = false;

	public static String _params[] =	{};

	public JOS1_15_1_Derelict()
	{
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		c15_1_DerelictConversation(clickerGuid, 0);
	}

	public void c15_1_DerelictConversation(int starterGuid, int npcGuid)
	{
		b15_1_DerelictConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "15_1_Derelict", "15_1_Derelict.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b15_1_DerelictConversation)
		{
			AIOn();
			b15_1_DerelictConversation = false;
			CodexCamera.Release(starterGuid);
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
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}