/**
 * University scene 9.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UNIV_9_1 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor		_Christof;
	private	CodexActor		_Cosmas;
	private	CodexActor		_Ecaterina;
	private CodexActor		_Wilhem;
	
	private CodexRegion     _EcaterinaRegion;

	private int				christofGUID;
	private int				cosmasGUID;
	private int				ecaterinaGUID;
	private int				wilhemGUID;

	private boolean			bGolemConversation = false;
	public boolean			bCosmasReturnConversation	= false;
	public boolean			bEcatReturn3Conversation	= false;

	public static String _params[] = {"Ecaterina", "Cosmas", "Ecaterina Region"};

	public UNIV_9_1(CodexActor Ecaterina, CodexActor Cosmas, CodexRegion EcaterinaRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Ecaterina = new CodexActor(Ecaterina.GetGUID());
		_Cosmas = new CodexActor(Cosmas.GetGUID());
		_EcaterinaRegion = new CodexRegion(EcaterinaRegion.GetGUID());

		CaptureThing(_Ecaterina.GetGUID());
		CaptureThing(_Cosmas.GetGUID());
		CaptureThing(_EcaterinaRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		cosmasGUID = _Cosmas.GetGUID();
		ecaterinaGUID = _Ecaterina.GetGUID();
		wilhemGUID	= CodexThing.GuidFromCastID("Wilhem");
		
		_Christof = new CodexActor(christofGUID);
		_Wilhem = new CodexActor(wilhemGUID);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Ecaterina.GetGUID())
		{
			// play default line here
			EcatReturn3Conversation(clickerGuid, 0);
		}
		else if(guid == _Cosmas.GetGUID())
		{
			// play default line here
			CosmasReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _EcaterinaRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.UNIV_ECATERINAGOLEM))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNIV_ECATERINAGOLEM);

			GolemConversation(causeGUID, 0);
		}
	}

	public void GolemConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		bGolemConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "9_1_Golem", "9_1_Golem.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void CosmasReturnConversation(int starterGuid, int npcGuid)
	{
		bCosmasReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "7_7_CosmasReturn", "7_7_CosmasReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EcatReturn3Conversation(int starterGuid, int npcGuid)
	{
		bEcatReturn3Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "8_1_EcatReturn3", "8_1_EcatReturn3.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bGolemConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bGolemConversation = false;
			CodexCamera.Release(starterGuid);

			if(returnValue == 1)
			{
				// christof is being lazy
				// lose humanity here
				_Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);

				// change scene in old town so the "too late" line can be played
				CodexSequence.ChangeScene("OldTown", "OLDT_11_1.nsd");
			}
			else
			{
				// award conversation XP
				CodexPlayer.AwardPartyExperience(50);
			}

			// open the protean group for christof
			_Christof.SetActorDisciplineLevel("EyesOfTheBeast", -1);

			//String aFormat = "%A" + _Christof.GetName() + "%g" + "DGRP_PROTEAN";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_NodFragment"));
			q.Complete();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_Golem"));

			// show north quarter on map
			CodexSequence.SetLocationFlags("DummyNorthQuarter", LOCATION_FLAG_SHOWINMAP);
			CodexSequence.SetLocationFlags("DummyNorthGate", LOCATION_FLAG_SHOWINMAP);

			// open north quarter exit
			CodexSequence.OpenExit("OldTown", 11);
			CodexSequence.OpenExit("NQuarter", 0);

			// auto advance
			CodexSequence.Advance(christofGUID);
		}

		if(bCosmasReturnConversation)
		{
			AIOn();
			bCosmasReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bEcatReturn3Conversation)
		{
			AIOn();
			bEcatReturn3Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bGolemConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, cosmasGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Cosmas.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							_Cosmas.StopActorAction();
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, cosmasGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Cosmas.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Cosmas.StopActorAction();
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Wilhem.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 13:

							CodexCamera.SetupCutscene(starterGuid, cosmasGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Wilhem.StopActorAction();
							break;

						case 14:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 17:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;
			} // switch(curEvent)
		}

		if(bCosmasReturnConversation)
		{

			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

					break;
			}
		}

		if(bEcatReturn3Conversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}
