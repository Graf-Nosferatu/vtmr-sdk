/**
 * Ardan Chantry 4 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC4_Main extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexActor		_Ardan;
	private	CodexRegion		_ArdanRegion;

	private int				ardanGUID;
	private int				christofGUID;
	private int				erikGUID;
	private int				serenaGUID;
	private int				wilhemGUID;

	private boolean			bArdanConversation	= false;
	private boolean			bChristofLine		= false;

	public static String _params[] = {"Ardan", "Ardan region"};

	public ARC4_Main(CodexActor Ardan, CodexRegion ArdanRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Ardan = new CodexActor(Ardan.GetGUID());
		_ArdanRegion = new CodexRegion(ArdanRegion.GetGUID());

		CaptureThing(_Ardan.GetGUID());
		CaptureThing(_ArdanRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		ardanGUID = _Ardan.GetGUID();
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		if(!CodexSequence.GetChronicleFlag(chronScript.ARC4_ARDANREGION))
		{
			_Ardan.SetActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _ArdanRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.ARC4_ARDANREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC4_ARDANREGION);

			ArdanConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// make sure Ardan was killed
		if(guid == _Ardan.GetGUID())
		{
			// set a chronicle flag to specify Ardan died
			CodexSequence.SetChronicleFlag(chronScript.ARC4_ARDANDEAD);

			CodexSound.PopMusic();

			ChristofLine(causeID, 0);

			CodexSequence.SetupAdvance("University", "UNIV_18_1.nsd", 0);
		}
	}

	public void ArdanConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_1.mp3", 50);
		bArdanConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "17_1_Ardan", "17_1_Ardan.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void ChristofLine(int starterGuid, int npcGuid)
	{
		bChristofLine = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "17_1_ArdanDeath", "17_1_ArdanDeath.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bArdanConversation)
		{
			AIOn();
			bArdanConversation = false;
			CodexCamera.Release(starterGuid);

			_Ardan.ClearActorFlags(THING_AF_AIPAUSED);
		}

		if(bChristofLine)
		{
			AIOn();
			bChristofLine = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_TremereChantry"));
			q.Complete();

			// open up auspex for christof and lure of flames for serena
			CodexActor Christof = new CodexActor(christofGUID);
			Christof.SetActorDisciplineLevel("HeightenedSenses", -1);
			CodexActor Serena = new CodexActor(serenaGUID);
			Serena.SetActorDisciplineLevel("Torch", -1);

			//String aFormat = "%A" + Christof.GetName() + "%g" + "DGRP_AUSPEX";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			//String aFormat2 = "%A" + Serena.GetName() + "%g" + "DGRP_THAU_LUREOFFLAMES";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat2));

			// auto advance
			CodexSequence.Advance(christofGUID);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bArdanConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ardanGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 4:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 5:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
					break;

				case 6:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 7:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 8:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 9:

					CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 10:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ardanGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 11:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 12:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 13:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 14:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 15:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 16:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 17:

					CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 18:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, ardanGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 19:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 20:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 21:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 22:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 23:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 24:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 25:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
					break;

				case 26:

					CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bChristofLine)
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
