/**
 *  WEST Enter party 20.5 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class WEST_20_5_EnterParty extends Codex
{
	private ViennaChronicle	chronScript;

	private int				christofGUID;
	private int				erikGUID;

	private CodexRegion		_Region;
	public boolean		b20_5_EnterPartyConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Region"};

	public WEST_20_5_EnterParty(CodexRegion Region)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);		

		_Region = new CodexRegion(Region.GetGUID());
		CaptureThing(_Region.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
	}
	
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(IsPlayerGuid(causeGUID) &&
			!CodexSequence.GetChronicleFlag(chronScript.WEST_PARTYREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.WEST_PARTYREGION);

			c20_5_EnterPartyConversation(causeGUID, 0);
		}
	}

	public void c20_5_EnterPartyConversation(int starterGuid, int npcGuid)
	{
		b20_5_EnterPartyConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "20_5_EnterParty", "20_5_EnterParty.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b20_5_EnterPartyConversation)
		{
			AIOn();
			b20_5_EnterPartyConversation = false;
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

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WesternRingStrasse.ncp", 30);
						break;

					case 1:

						//CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
