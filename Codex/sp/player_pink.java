/**
 * Pink script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class player_pink extends Codex
{
	private static final int	TIMER_ID_PINKFEEDING		= 0;

	public boolean		bPinkFastConversation = false;
	private boolean		bPinkFed = false;

	public player_pink()
	{

	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_PINKFEEDING:

				bPinkFed = true;

				// first time Pink feeds, play conversation
				PinkFastConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}

	public void actorfeed(int actorGUID, int targetGUID, int status, int captureID)
	{
		CodexActor feedee = new CodexActor(targetGUID);

		// if pink wasn't feeding on something that causes humanity loss
		// we're probably in a dungeon, so return without saying the lines
		if((feedee.GetActorFlags() & THING_AF_HUMANITYLOSS) != 0)
			return;

		if(status == 2)
		{
			if(!bPinkFed)
			{
				SetTimer(1, TIMER_ID_PINKFEEDING, actorGUID);
			}
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bPinkFed);
	}
 
	public void restore(int flags)
	{
		bPinkFed = CodexSequence.RestoreBoolean();
	}

	public void PinkFastConversation(int starterGuid, int npcGuid)
	{
		bPinkFastConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "28_4_PinkFast", "28_4_PinkFast.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bPinkFastConversation)
		{
			AIOn();
			bPinkFastConversation = false;
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
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;
					case 1:
						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
