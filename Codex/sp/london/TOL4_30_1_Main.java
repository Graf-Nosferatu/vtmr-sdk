/**
 * Tower of London 4 30.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TOL4_30_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	private CodexActor	_Christof;
	private CodexActor	_snake1;
	private CodexActor	_snake2;
	private CodexActor	_snake3;
	private CodexActor	_snake4;
	private CodexActor	_snake5;

	private int			christofGUID;
	private int			lilyGUID;
	private int			pinkGUID;
	
	public boolean		b30_1_FindHeartConversation = false;
	public CodexItem	_lucretiasHeart;

	public static String _params[] =	{"Lucretias' Heart", "Snake 1", "Snake 2", "Snake 3", "Snake 4", "Snake 5"};

	public TOL4_30_1_Main(CodexItem lucretiasHeart, CodexActor snake1, CodexActor snake2, CodexActor snake3, CodexActor snake4, CodexActor snake5)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_lucretiasHeart = new CodexItem(lucretiasHeart.GetGUID());
		_snake1 = new CodexActor(snake1.GetGUID());
		_snake2 = new CodexActor(snake2.GetGUID());
		_snake3 = new CodexActor(snake3.GetGUID());
		_snake4 = new CodexActor(snake4.GetGUID());
		_snake5 = new CodexActor(snake5.GetGUID());

		CaptureThing(_lucretiasHeart.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.TOL4_FINDHEART))
		{
			_snake1.SetActorFlags(THING_AF_AIPAUSED);
			_snake2.SetActorFlags(THING_AF_AIPAUSED);
			_snake3.SetActorFlags(THING_AF_AIPAUSED);
			_snake4.SetActorFlags(THING_AF_AIPAUSED);
			_snake5.SetActorFlags(THING_AF_AIPAUSED);
		}
	}
	
	public boolean pickup(int item, int picker, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.TOL4_FINDHEART))
		{
			CodexSequence.SetChronicleFlag(chronScript.TOL4_FINDHEART);

			c30_1_FindHeartConversation(picker, 0);
		}

		return(true);
	}

	public void c30_1_FindHeartConversation(int starterGuid, int npcGuid)
	{
		b30_1_FindHeartConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "30_1_FindHeart", "30_1_FindHeart.nco", CONV_XFLAG_WANTFEEDBACK);

		CodexSequence.ChangeScene("Brothel", "BROT_31_1.nsd");
		CodexSequence.ChangeScene("SetiteTemple4", "SET4_31_1.nsd");
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b30_1_FindHeartConversation)
		{
			AIOn();
			b30_1_FindHeartConversation = false;
			_Christof.StopActorAction();
			_Christof.CancelOverrideActorWeapon();
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// complete find heart quest to log
			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_FindHeart"));
			q.Complete();
			// open fortitude for pink
			CodexActor Pink = new CodexActor(pinkGUID);
			Pink.SetActorDisciplineLevel("Fortitude", -1);

			//String aFormat = "%A" + Pink.GetName() + "%g" + "DGRP_FORTITUDE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("L1_ReturnHeart"));

			// auto advance
			CodexSequence.Advance(christofGUID);

			_snake1.ClearActorFlags(THING_AF_AIPAUSED);
			_snake2.ClearActorFlags(THING_AF_AIPAUSED);
			_snake3.ClearActorFlags(THING_AF_AIPAUSED);
			_snake4.ClearActorFlags(THING_AF_AIPAUSED);
			_snake5.ClearActorFlags(THING_AF_AIPAUSED);
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

						_Christof.OverrideActorWeapon("weapHeart", false, false);
						_Christof.PlayMotionSetMode(MOTION_SPECIAL24, false, (float)30.0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 3:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}