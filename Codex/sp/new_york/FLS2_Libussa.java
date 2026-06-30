/**
 * Libussa Crazed 40.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FLS2_Libussa extends Codex
{
	private NewYorkChronicle	chronScript;

	private CodexActor		_Libussa;
	private CodexRegion		_LibussaRegion;

	private int				christofGUID;
	private int				libussaGUID;

	private boolean			bLibussaCrazedConversation	= false;

	public static String _params[] = {"Libussa", "LibussaRegion"};

	public FLS2_Libussa(CodexThing Libussa, CodexRegion LibussaRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Libussa = new CodexActor(Libussa.GetGUID());
		_LibussaRegion = new CodexRegion(LibussaRegion.GetGUID());

		CaptureThing(_Libussa.GetGUID());
		CaptureThing(_LibussaRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		libussaGUID = _Libussa.GetGUID();
		
		// make her a talkie
		_Libussa.SetActorFlags(THING_AF_TALKTO);

		if(CodexSequence.GetChronicleFlag(chronScript.FLS3_LIBUSSAHELP))
		{
			// remove her
			_Libussa.Remove();
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Libussa.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.FLS2_LIBUSSACRAZED))
		{
			CodexSequence.SetChronicleFlag(chronScript.FLS2_LIBUSSACRAZED);

			LibussaCrazedConversation(clickerGuid, 0);

			_Libussa.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void LibussaCrazedConversation(int starterGuid, int npcGuid)
	{
		bLibussaCrazedConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "40_1_LibussaCrazed", "40_1_LibussaCrazed.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLibussaCrazedConversation)
		{
			AIOn();
			bLibussaCrazedConversation = false;
			CodexCamera.Release(starterGuid);

			_Libussa.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
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

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LibussaCrazed.ncp", 30);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

