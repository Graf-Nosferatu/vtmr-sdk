/**
 * Josef 3 15.1 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class JOS3_15_1 extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexActor		_Vaclav;
	private	CodexRegion		_VaclavRegion;
	private	CodexItem		_Reliquary;

	private int				christofGUID;
	private int				vaclavGUID;
	private int				wilhemGUID;

	private boolean			bVaclavConversation = false;
	private boolean			bAfterVaclavConversation = false;

	public static String _params[] = {"Vaclav", "Vaclav region", "Reliquary"};

	public JOS3_15_1(CodexActor Vaclav, CodexRegion VaclavRegion, CodexItem Reliquary)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Vaclav = new CodexActor(Vaclav.GetGUID());
		_VaclavRegion = new CodexRegion(VaclavRegion.GetGUID());
		_Reliquary = new CodexItem(Reliquary.GetGUID());

		CaptureThing(_Vaclav.GetGUID());
		CaptureThing(_VaclavRegion.GetGUID());
		CaptureThing(_Reliquary.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		vaclavGUID = _Vaclav.GetGUID();
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		if(!CodexSequence.GetChronicleFlag(chronScript.JOS3_VACLAVREGION))
		{
			_Vaclav.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Vaclav.SetActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _VaclavRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.JOS3_VACLAVREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.JOS3_VACLAVREGION);

			_Vaclav.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Vaclav.ClearActorFlags(THING_AF_AIPAUSED);

			VaclavConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Vaclav.GetGUID())
		{
			CodexSequence.SetChronicleFlag(chronScript.JOS3_VACLAVDEAD);

			CodexSound.PopMusic();

			AfterVaclavConversation(causeID, 0);
		}
	}

	public boolean pickup(int item, int picker, int captureID)
	{
		CodexSequence.SetChronicleFlag(chronScript.JOS3_RELIQUARYRECOVERED);

		CodexSequence.ChangeScene("PrinceBrandl", "PRIN_16_1.nsd");

		return(true);
	}

	public void VaclavConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_3.wav", 80);
		bVaclavConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "15_1_Vaclav", "15_1_Vaclav.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AfterVaclavConversation(int starterGuid, int npcGuid)
	{
		bAfterVaclavConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "15_1_AfterVaclav", "15_1_AfterVaclav.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bVaclavConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bVaclavConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bAfterVaclavConversation)
		{
			AIOn();
			bAfterVaclavConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bVaclavConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VaclavPOV.ncp", 30);
					break;

				case 1:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VaclavPOV.ncp", 30);
					break;
			}
		}
			
		if(bAfterVaclavConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}

}
