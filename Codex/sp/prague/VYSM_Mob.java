/**
 *  Vysehrad Mountain mob script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class VYSM_Mob extends Codex
{
	private Prague2Chronicle chronScript;

	private CodexActor		_speaker;

	public boolean			bVysehradMobConversation = false;

	public static String _params[] = {"Speaker"};

	public VYSM_Mob(CodexActor speaker)
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);

		_speaker = new CodexActor(speaker.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.VYSM_PARAPET))
		{
			_speaker.SetActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!IsPlayerGuid(causeGuid))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.VYSM_PARAPET))
		{
			CodexSequence.SetChronicleFlag(chronScript.VYSM_PARAPET);

			VysehradMobConversation(CodexPlayer.GetCurrentPlayer(), 0);
		}
	}

	public void VysehradMobConversation(int starterGuid, int npcGuid)
	{
		bVysehradMobConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "25_1_VysehradMob", "25_1_VysehradMob.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bVysehradMobConversation)
		{
			AIOn();
			bVysehradMobConversation = false;
			CodexCamera.Release(starterGuid);

			_speaker.ClearActorFlags(THING_AF_AIPAUSED);
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

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VYSM_Mob.ncp", 30);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
