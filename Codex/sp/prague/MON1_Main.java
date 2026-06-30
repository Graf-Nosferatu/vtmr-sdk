/**
 * Petrin Mission Monastery 1 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class MON1_Main extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexRegion		_OurMissionRegion;
	private	CodexRegion		_StrangeClanRegion;
	private	CodexRegion		_MuralRegion;
	private	CodexRegion		_FinalDeathRegion;

	private CodexRegion		_CappRegion;
	private CodexActor		_Capp1;
	private CodexActor		_Capp2;

	//private CodexRegion		_ExperimentsRegion;
	//private CodexActor		_Victim1;
	//private CodexActor		_Victim2;

	private CodexRegion		_FightConversationRegion;

	private CodexActor		_Christof;
	private CodexActor		_Wilhem;
	
	private int				christofGUID;
	private int				wilhemGUID;
	private int				cappadocian1GUID;
	private int				cappadocian2GUID;
	
	private boolean			bMissionConversation		= false;
    private boolean			bStrangeConversation		= false;
	private boolean			bMuralConversation			= false;
	private boolean			bFinalConversation			= false;
	private boolean			bCappConversation			= false;
	//private boolean			bExperimentsConversation	= false;
	private boolean			bFightConversation			= false;
	
	public static String _params[] = {"Our mission region", "Strange clan region", "Mural region", 
										"Final Death region", "Cappadocian region", "Cappadocian 1", "Cappadocian 2",
										"Experiments region", "Victim 1", "Victim 2",
										"Fight conversation region"};

	public MON1_Main(CodexRegion OurMissionRegion, CodexRegion StrangeClanRegion, CodexRegion MuralRegion, 
					CodexRegion FinalDeathRegion, CodexRegion CappRegion, CodexActor Capp1, CodexActor Capp2,
					CodexRegion ExperimentsRegion, CodexActor Victim1, CodexActor Victim2,
					CodexRegion FightConversationRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_OurMissionRegion = new CodexRegion(OurMissionRegion.GetGUID());
		_StrangeClanRegion = new CodexRegion(StrangeClanRegion.GetGUID());
		_MuralRegion = new CodexRegion(MuralRegion.GetGUID());
		_FinalDeathRegion = new CodexRegion(FinalDeathRegion.GetGUID());

		_CappRegion = new CodexRegion(CappRegion.GetGUID());
		_Capp1 = new CodexActor(Capp1.GetGUID());
		_Capp2 = new CodexActor(Capp2.GetGUID());

		//_ExperimentsRegion = new CodexRegion(ExperimentsRegion.GetGUID());
		//_Victim1 = new CodexActor(Victim1.GetGUID());
		//_Victim2 = new CodexActor(Victim2.GetGUID());

		_FightConversationRegion = new CodexRegion(FightConversationRegion.GetGUID());

		CaptureThing(_OurMissionRegion.GetGUID());
		CaptureThing(_StrangeClanRegion.GetGUID());
		CaptureThing(_MuralRegion.GetGUID());
		CaptureThing(_FinalDeathRegion.GetGUID());

		//CaptureThing(_ExperimentsRegion.GetGUID());

		CaptureThing(_FightConversationRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
		cappadocian1GUID = _Capp1.GetGUID();
		cappadocian2GUID = _Capp2.GetGUID();

		_Christof = new CodexActor(christofGUID);
		_Wilhem = new CodexActor(wilhemGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.MON1_OURMISSIONREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON1_OURMISSIONREGION);

			MissionConversation(clientGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _StrangeClanRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON1_STRANGECLANREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON1_STRANGECLANREGION);

			StrangeConversation(causeGUID, 0);
		}

		if(guid == _MuralRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON1_MURALREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON1_MURALREGION);

			MuralConversation(causeGUID, 0);
		}

		if(guid == _FinalDeathRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON1_FINALDEATHREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON1_FINALDEATHREGION);
			
			CodexSound.PlayVoice(_Wilhem.GetGUID(), "Wilhem_8_2_352", 40);
			
		}

		//if(guid == _ExperimentsRegion.GetGUID() &&
		//	!CodexSequence.GetChronicleFlag(chronScript.MON1_EXPERIMENTREGION))
		//{
		//	CodexSequence.SetChronicleFlag(chronScript.MON1_EXPERIMENTREGION);

		//	ExperimentsConversation(causeGUID, 0);
		//}

		if(guid == _FightConversationRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON1_FIGHTREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON1_FIGHTREGION);

			FightConversation(causeGUID, 0);
		}
	}

	public void MissionConversation(int starterGuid, int npcGuid)
	{
		bMissionConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_1_Mission2", "8_1_Mission2.nco", CONV_XFLAG_WANTFEEDBACK);
	}
		
	public void StrangeConversation(int starterGuid, int npcGuid)
	{
		bStrangeConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Strange", "8_2_Strange.nco", CONV_XFLAG_WANTFEEDBACK);

	}

	public void MuralConversation(int starterGuid, int npcGuid)
	{
		bMuralConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Cainite2", "8_2_Cainite2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void CappConversation(int starterGuid, int npcGuid)
	{
		bCappConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Capp", "8_2_Capp.nco", CONV_XFLAG_WANTFEEDBACK);
	}
/*
	public void ExperimentsConversation(int starterGuid, int npcGuid)
	{
		bExperimentsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 4);
		ExecuteConversation(starterGuid, npcGuid, "8_1_Experiments", "8_1_Experiments.nco", CONV_XFLAG_WANTFEEDBACK);
	}
*/
	public void FightConversation(int starterGuid, int npcGuid)
	{
		bFightConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 5);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Fight", "8_2_Fight.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMissionConversation)
		{
			bMissionConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
			
		if(bStrangeConversation)
		{
			bStrangeConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
		    
		if(bMuralConversation)
		{
			bMuralConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}

		if(bCappConversation)
		{
			AIOn();
			bCappConversation = false;
			CodexCamera.Release(starterGuid);
		}
/*
		if(bExperimentsConversation)
		{
			AIOn();
			bExperimentsConversation = false;
			CodexCamera.Release(starterGuid);
		}
*/
		if(bFightConversation)
		{
			AIOn();
			bFightConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}
	
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMissionConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					//_Christof.PlayMotionSetMode(MOTION_ACTION2, false, (float)15.0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					//_Christof.StopActorAction();
					//CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					//CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
			
		if(bStrangeConversation)
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

				case 2:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;			
			}
		}
		
		if(bMuralConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bCappConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, cappadocian1GUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, cappadocian2GUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, cappadocian1GUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, cappadocian2GUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)	
		}
/*
		if(bExperimentsConversation)
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

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)	
		}
*/
		if(bFightConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "MON1Fight.ncp", 30);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)	
		}
	}
}