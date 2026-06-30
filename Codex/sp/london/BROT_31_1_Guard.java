/**
 * Brothel 31.1 Guard script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class BROT_31_1_Guard extends Codex
{
	private LondonChronicle	chronScript;

	public boolean		b31_1_SetiteGuardConversation = false;

	public CodexActor	_setiteGuard;
	public CodexRegion	_guardRegion;

	public static String _params[] =	{"Setite Guard", "Guard Region"};

	public BROT_31_1_Guard(CodexActor setiteGuard, CodexRegion guardRegion)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_setiteGuard = new CodexActor(setiteGuard.GetGUID());
		_guardRegion = new CodexRegion(guardRegion.GetGUID());

		CaptureThing(_guardRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{	
		_setiteGuard.SetActorFlags(THING_AF_AIPAUSED);
		_setiteGuard.SetActorFlags(THING_AF_INVUL);
		_setiteGuard.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		if(CodexSequence.GetChronicleFlag(chronScript.SET4_LUCRETIADEAD))
		{
			_setiteGuard.SetCollideType(THING_COLLIDE_NONE);
			_setiteGuard.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.BROT_MEETGUARD))
		{
			CodexSequence.SetChronicleFlag(chronScript.BROT_MEETGUARD);

			c31_1_SetiteGuardConversation(causeGUID, 0);
		}
	}

	public void c31_1_SetiteGuardConversation(int starterGuid, int npcGuid)
	{
		b31_1_SetiteGuardConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "31_1_SetiteGuard", "31_1_SetiteGuard.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b31_1_SetiteGuardConversation)
		{
			AIOn();
			b31_1_SetiteGuardConversation = false;
			CodexCamera.Release(starterGuid);

			CodexSequence.Jump("SetiteTemple4", 0);
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





