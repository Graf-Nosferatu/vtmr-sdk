/**
 * Prince 14.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class PRIN_14_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_LINES		= 1;
	private static final int TIMER_ID_ENDSCENE	= 2;

	private CodexActor		_Christof;
	private CodexActor		_Brandl;
	private CodexActor		_Ecaterina;
	private CodexActor		_Cosmas;

	private int				christofGUID;
	private int				brandlGUID;
	private int				ecaterinaGUID;
	private int				cosmasGUID;

	private boolean			bBrandlConversation = false;
	private boolean			bBrandlReturnConversation = false;

	public static String _params[] = {"Brandl", "Ecaterina", "Cosmas"};

	public PRIN_14_1(CodexActor Brandl, CodexActor Ecaterina, CodexActor Cosmas)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Brandl = new CodexActor(Brandl.GetGUID());
		_Ecaterina = new CodexActor(Ecaterina.GetGUID());
		_Cosmas = new CodexActor(Cosmas.GetGUID());

		CaptureThing(_Brandl.GetGUID());
		CaptureThing(Ecaterina.GetGUID());
		CaptureThing(_Cosmas.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		brandlGUID = _Brandl.GetGUID();
		ecaterinaGUID = _Ecaterina.GetGUID();
		cosmasGUID = _Cosmas.GetGUID();
		
		_Christof = new CodexActor(christofGUID);

		// Fade in from University scene
		CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
		
		SetTimer(0.1f, TIMER_ID_LINES, clientGuid);
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.PRIN_REGION))
		{
			// hide ecaterina and cosmas
			_Cosmas.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Cosmas.SetCollideType(THING_COLLIDE_NONE);
			_Cosmas.SetActorFlags(THING_AF_AIPAUSED);

			_Ecaterina.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Ecaterina.SetCollideType(THING_COLLIDE_NONE);
			_Ecaterina.SetActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Brandl.GetGUID())
		{
			// play default line here
			BrandlReturnConversation(clickerGuid, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_LINES:
				if(!CodexSequence.GetChronicleFlag(chronScript.PRIN_REGION))
				{
					CodexSequence.SetChronicleFlag(chronScript.PRIN_REGION);

					BrandlConversation(CodexPlayer.GetCurrentPlayer(), 0);
				}
				break;
		}
	}

	public void BrandlReturnConversation(int starterGuid, int npcGuid)
	{
		bBrandlReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "14_1_BrandlReturn", "14_1_BrandlReturn.nco", CONV_XFLAG_WANTFEEDBACK);	
	}

	public void BrandlConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bBrandlConversation = true;
		AIOff();
		//CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "14_1_Brandl", "14_1_Brandl.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bBrandlConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bBrandlConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_Reliquary"));

			// show tunnels on map
			CodexSequence.SetLocationFlags("JosefTunnels", LOCATION_FLAG_SHOWINMAP);

			CodexSequence.ChangeScene("NQuarter", "NQTR_14_3.nsd");

			// if he didn't lip off
			if(returnValue == 1)
			{
				// award conversation XP
				CodexPlayer.AwardPartyExperience(50);
			}
		}

		if(bBrandlReturnConversation)
		{
			AIOn();
			bBrandlReturnConversation = false;
			CodexCamera.Release(starterGuid);		
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bBrandlConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, cosmasGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 1:
							CodexCamera.SetupCutscene(starterGuid, cosmasGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;
			} // switch(curEvent)
		}

		if(bBrandlReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, brandlGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;
					}
			}		
		}
	}
}

