/**
 * Prince 16.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class PRIN_16_1 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor		_Brandl;
	private CodexActor		_Christof;
	private CodexActor		_Ecaterina;

	private int				brandlGUID;
	private int				christofGUID;
	private int				ecaterinaGUID;

	private int				humanityTrack = 0;

	private boolean			bReturnArmConversation = false;
	private boolean			bPrinceReturnConversation = false;

	public static String _params[] = {"Brandl", "Ecaterina"};

	public PRIN_16_1(CodexActor Brandl, CodexActor Ecaterina)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Brandl = new CodexActor(Brandl.GetGUID());
		_Ecaterina = new CodexActor(Ecaterina.GetGUID());

		CaptureThing(_Brandl.GetGUID());
		CaptureThing(_Ecaterina.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		brandlGUID = _Brandl.GetGUID();
		christofGUID = CodexThing.GuidFromCastID("Christof");
		ecaterinaGUID = _Ecaterina.GetGUID();

		_Christof = new CodexActor(christofGUID);
		
		SetTimer(1, 0, clientGuid);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Brandl.GetGUID())
		{
			// play default line here
			PrinceReturnConversation(clickerGuid, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.JOS3_RELIQUARYRECOVERED) &&
			!CodexSequence.GetChronicleFlag(chronScript.PRIN_RELIQUARY))

		{
			CodexSequence.SetChronicleFlag(chronScript.PRIN_RELIQUARY);

			ReturnArmConversation(CodexPlayer.GetCurrentPlayer(), 0);
		}
	}

	public void PrinceReturnConversation(int starterGuid, int npcGuid)
	{
		bPrinceReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "15_1_PrinceReturn", "15_1_PrinceReturn.nco", CONV_XFLAG_WANTFEEDBACK);	
	}

	public void ReturnArmConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bReturnArmConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "15_1_ReturnArm", "15_1_ReturnArm.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bReturnArmConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bReturnArmConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_Reliquary"));
			q.Complete();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_TremereChantry"));

			// show chantry on map
			CodexSequence.SetLocationFlags("ArdanChantry", LOCATION_FLAG_SHOWINMAP);

			// open chantry exit
			CodexSequence.ChangeExit("ArdanChantry", 0, "GoldenLane", 2);
			CodexSequence.ChangeExit("GoldenLane", 2, "ArdanChantry", 0);
			CodexSequence.OpenExit("GoldenLane", 2);
			CodexSequence.OpenExit("ArdanChantry", 0);

			// open fortitude for christof and obfuscate for wilhem
			CodexActor Wilhem = new CodexActor(CodexThing.GuidFromCastID("Wilhem"));
			_Christof.SetActorDisciplineLevel("Fortitude", -1);
			Wilhem.SetActorDisciplineLevel("CloakOfShadows", -1);

			//String aFormat = "%A" + _Christof.GetName() + "%g" + "DGRP_FORTITUDE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			//String aFormat2 = "%A" + Wilhem.GetName() + "%g" + "DGRP_OBFUSCATE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat2));

			if(humanityTrack == 1)
			{
				// add humanity here
				_Christof.AddActorEffectByLevel("ef_increasehumanity", 0, 1, 0, 0);
			}

			// auto advance
			CodexSequence.Advance(christofGUID);
		}

		if(bPrinceReturnConversation)
		{
			AIOn();
			bPrinceReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bReturnArmConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							humanityTrack = 1;

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;

						case 1:

							// Brandl casts Decay or some equivalent on Christof
							CodexSound snd1 = new CodexSound("disciplineCast_engulf.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Brandl.GetGUID());
							_Brandl.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 2:

							// Christof reacts with pain
							_Brandl.StopActorAction();
							_Christof.PlayMotionSetMode(MOTION_HURT, false, (float)30.0);
							_Christof.SpawnThing("redMagic");
							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							CodexSound snd2 = new CodexSound("Christof_Grunt_14.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Christof.GetGUID());
							break;

						case 3:

							_Christof.StopActorAction();
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, ecaterinaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
			} // switch(curEvent)
		}

		if(bPrinceReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "BrandlChair.ncp", 30);
							break;
					}
			}
		}
	}
}

