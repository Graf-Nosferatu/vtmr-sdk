/**
 *  SOUT Haus Look 20.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class SOUT_20_1_HausLook extends Codex
{
	private ViennaChronicle	chronScript;
	private CodexRegion		_LookRegion;
	private int				christofGUID;
	//public boolean		b20_1_HausLookConversation = false;

	public static String _params[] = {"Region"};

	public SOUT_20_1_HausLook(CodexRegion LookRegion)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_LookRegion = new CodexRegion(LookRegion.GetGUID());
		CaptureThing(_LookRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
	}
	
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(IsPlayerGuid(causeGUID) &&
			!CodexSequence.GetChronicleFlag(chronScript.SOUT_LOOKREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOUT_LOOKREGION);

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "V1_HausLook.ncp", 150);

			CodexActor player = new CodexActor(causeGUID);
			player.CancelActorAction();
			player.Stop();
		}
	}

	public void pathended(int clientGuid)
	{
		//AIOn();
		//b20_1_HausLookConversation = false;
		CodexCamera.Release(0);
	}
/*
	public void c20_1_HausLookConversation(int starterGuid, int npcGuid)
	{
		b20_1_HausLookConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "20_1_HausLook", "20_1_HausLook.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b20_1_HausLookConversation)
		{
			AIOn();
			b20_1_HausLookConversation = false;
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

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "V1_HausLook.ncp", 150);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
*/
}
