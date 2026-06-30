/**
 * New York Uptown 38.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UTWN_38_1_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	public CodexActor			_goon1;
	public CodexActor			_goon2;
	public CodexRegion			_triggerRegion;

	public boolean		b38_1_WherePaintingConversation = false;
	public boolean		b38_1_EnterFactoryConversation = false;
	public boolean		b38_1_NoInviteConversation = false;

	public static String _params[] =	{"Ventrue Goon 1", "Ventrue Goon 2", "Trigger Region"};

	public UTWN_38_1_Main(CodexActor goon1, CodexActor goon2, CodexRegion triggerRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_goon1 = new CodexActor(goon1.GetGUID());
		_goon2 = new CodexActor(goon2.GetGUID());
		_triggerRegion = new CodexRegion(triggerRegion.GetGUID());

		CaptureThing(_triggerRegion.GetGUID());
		CaptureThing(_goon1.GetGUID());
		CaptureThing(_goon2.GetGUID());
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.STOR_HAVEPAINTING))
		{
			c38_1_EnterFactoryConversation(clickerGuid, 0);
		}
		else
		{
			c38_1_NoInviteConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.UTWN_GOONREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.UTWN_GOONREGION);

			c38_1_WherePaintingConversation(causeGUID, 0);
		}
	}

	public void c38_1_WherePaintingConversation(int starterGuid, int npcGuid)
	{
		b38_1_WherePaintingConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "38_1_WherePainting", "38_1_WherePainting.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c38_1_EnterFactoryConversation(int starterGuid, int npcGuid)
	{
		b38_1_EnterFactoryConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "38_1_EnterFactory", "38_1_EnterFactory.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c38_1_NoInviteConversation(int starterGuid, int npcGuid)
	{
		b38_1_NoInviteConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "38_1_NoInvite", "38_1_NoInvite.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b38_1_WherePaintingConversation)
		{
			AIOn();
			b38_1_WherePaintingConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b38_1_EnterFactoryConversation)
		{
			AIOn();
			b38_1_EnterFactoryConversation = false;
			CodexCamera.Release(starterGuid);

			CodexSequence.OpenExit("NewYorkUptown", 3);
			CodexSequence.OpenExit("OrsiFactory4", 0);
		}

		if(b38_1_NoInviteConversation)
		{
			AIOn();
			b38_1_NoInviteConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b38_1_WherePaintingConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, _goon1.GetGUID(), _goon2.GetGUID());
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, _goon1.GetGUID(), _goon2.GetGUID());
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b38_1_EnterFactoryConversation)
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

		if(b38_1_NoInviteConversation)
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
}


