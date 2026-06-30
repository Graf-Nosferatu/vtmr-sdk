/**
 * Petrin Hill 13.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HILL_13_1 extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexRegion		_GarinolRegion;
	private CodexActor		_Garinol;
	private CodexPlayer		_Serena;

	private int				christofGUID;
	private int				garinolGUID;
	private int				serenaGUID;
	private int				wilhemGUID;
	
	private boolean			bGarinolConversation		= false;
	public boolean			bGarinolReturnConversation = false;

	public static String _params[] = {"Garinol region", "Garinol", "Serena"};

	public HILL_13_1(CodexRegion GarinolRegion, CodexActor Garinol, CodexPlayer Serena)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_GarinolRegion = new CodexRegion(GarinolRegion.GetGUID());
		_Garinol = new CodexActor(Garinol.GetGUID());
		_Serena = new CodexPlayer(Serena.GetGUID());

		CaptureThing(_GarinolRegion.GetGUID());
		CaptureThing(_Garinol.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		garinolGUID = _Garinol.GetGUID();
		serenaGUID = _Serena.GetGUID();
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Garinol.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.HILL_GARINOLREGION))
		{
			// say default line here
			GarinolReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.HILL_GARINOLREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.HILL_GARINOLREGION);

			GarinolConversation(causeGUID, 0);
		}
	}
	
	public void GarinolConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bGarinolConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "15_1_Serena", "15_1_Serena.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void GarinolReturnConversation(int starterGuid, int npcGuid)
	{
		bGarinolReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "11_1_GarinolReturn", "11_1_GarinolReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bGarinolConversation)
		{
			CodexSound.PopMusic();
			bGarinolConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_Shem"));
			q.Complete();

			if(!CodexSequence.GetChronicleFlag(chronScript.HAVN_ANEZKASCENE))
			{
				CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_RestToAdvance"));
			}

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// add Serena to the party
			_Serena.AddToParty();
		}
	
		if(bGarinolReturnConversation)
		{
			AIOn();
			bGarinolReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}
	
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bGarinolConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, garinolGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, garinolGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, garinolGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetupCutscene(starterGuid, garinolGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 6:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, garinolGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 7:

					CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;
				
			}
		}

		if(bGarinolReturnConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, garinolGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}	
}


