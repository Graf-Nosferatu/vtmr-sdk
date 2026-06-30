/**
 * Setite Temple 2 29.2 Setites script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SET2_29_2_Setites extends Codex
{
	private LondonChronicle	chronScript;

	private int			christofGUID;

	public boolean		b29_2_SetitesConversation = false;

	public CodexActor	_setite1;
	public CodexActor	_setite2;
	public CodexRegion	_setiteRegion;

	public static String _params[] =	{"Setite 1", "Setite 2", "Setite Region"};

	public SET2_29_2_Setites(CodexActor setite1, CodexActor setite2, CodexRegion setiteRegion)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_setite1 = new CodexActor(setite1.GetGUID());
		_setite2 = new CodexActor(setite2.GetGUID());
		_setiteRegion = new CodexRegion(setiteRegion.GetGUID());

		CaptureThing(_setite1.GetGUID());
		CaptureThing(_setite2.GetGUID());
		CaptureThing(_setiteRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");

		if(!CodexSequence.GetChronicleFlag(chronScript.SET2_SETITES))
		{
			_setite1.SetActorFlags(THING_AF_AIPAUSED);
			_setite1.SetActorFlags(THING_AF_INVUL);
			_setite1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			_setite2.SetActorFlags(THING_AF_AIPAUSED);
			_setite2.SetActorFlags(THING_AF_INVUL);
			_setite2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.SET2_SETITES))
		{
			CodexSequence.SetChronicleFlag(chronScript.SET2_SETITES);

			c29_2_SetitesConversation(causeGUID, 0);
		}
	}

	public void c29_2_SetitesConversation(int starterGuid, int npcGuid)
	{
		b29_2_SetitesConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "29_2_Setites", "29_2_Setites.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b29_2_SetitesConversation)
		{
			AIOn();
			b29_2_SetitesConversation = false;
			CodexCamera.Release(starterGuid);

			_setite1.ClearActorFlags(THING_AF_AIPAUSED);
			_setite1.ClearActorFlags(THING_AF_INVUL);
			_setite1.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

			_setite2.ClearActorFlags(THING_AF_AIPAUSED);
			_setite2.ClearActorFlags(THING_AF_INVUL);
			_setite2.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}