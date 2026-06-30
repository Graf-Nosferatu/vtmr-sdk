/**
 * Party Suite LOT Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_SUIT_Main extends Codex
{
	private MP_LOTChronicle	chronScript;

	public static final int LOBY_SCENE_2_1		= 1;
	public static final int LOBY_SCENE_3_1		= 2;
	public static final int LOBY_SCENE_5_1		= 3;

	public LOT_SUIT_Main()
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.SUIT_DONECHANGING))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.LOBY_CONCLAVE))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_CHANGESCENE_5_1) &&
				!CodexSequence.GetChronicleFlag(chronScript.SUIT_HITTEXT))
			{
				CodexSequence.SetChronicleFlag(chronScript.SUIT_HITTEXT);

				// close the exit from the haven while we hop everyone in here and
				// change the scene in the lobby
				CodexSequence.CloseExit("SuiteHaven", 0);

				// remove the return to haven quest
				CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_ReturnToHaven"));
				q.Destroy();

				// ST narration of "The Hit" scene
				ExecuteText(clientGuid, guid, "MirabilisMurder");
			}
		}
		else if(CodexSequence.GetChronicleFlag(chronScript.LOBY_PRIMOGEN) &&
			    CodexSequence.GetChronicleFlag(chronScript.LOBY_PRIMOGENGONE))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_CHANGESCENE_3_1))
			{
				// close the exit from the haven while we hop everyone in here and
				// change the scene in the lobby
				CodexSequence.CloseExit("SuiteHaven", 0);

				// remove the return to haven quest
				CodexQuest q2 = new CodexQuest(CodexQuest.Load("LOT_ReturnToHaven"));
				q2.Destroy();

				// jump all players to haven for scene change.
				SetTimer(2, LOBY_SCENE_3_1);
			}
		}
		else if(CodexSequence.GetChronicleFlag(chronScript.LOBY_MEETDOMINIC))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.LOBY_CHANGESCENE_2_1))
			{
				// close the exit from the haven while we hop everyone in here and
				// change the scene in the lobby
				CodexSequence.CloseExit("SuiteHaven", 0);

				// jump all players to haven for scene change.
				SetTimer(2, LOBY_SCENE_2_1);
			}
		}		
	}
	
	public void textended(int readerGuid)
	{
		// jump all players to haven for scene change.
		SetTimer(2, LOBY_SCENE_5_1);

		//CodexSequence.ChangeScene("BarclayLobby", "LOBY_LOT_5_1.nsd");

		//CodexSequence.SetChronicleFlag(chronScript.SUIT_DONECHANGING);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case LOBY_SCENE_2_1:
				chronScript.Step(chronScript.LOBY_CHANGESCENE_2_1);
				break;

			case LOBY_SCENE_3_1:
				chronScript.Step(chronScript.LOBY_CHANGESCENE_3_1);
				break;

			case LOBY_SCENE_5_1:
				chronScript.Step(chronScript.SUIT_DONECHANGING);
				chronScript.Step(chronScript.LOBY_CHANGESCENE_5_1);
				break;
		}

		// reopen the exit from the haven into the lobby
		CodexSequence.OpenExit("SuiteHaven", 0);
	}
}

