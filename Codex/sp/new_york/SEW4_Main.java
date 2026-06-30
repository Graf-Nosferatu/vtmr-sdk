/**
 *  SEW4_Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 *
*/

public class SEW4_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_REMOVETRANS		= 0;

	private CodexActor			_Underprince;
	private CodexActor			_Samuel;
	private CodexRegion			_ChamberRegion;
	private CodexThing			_ChamberExitDoor;
	private CodexThing			_JunctionBox;

	private int					christofGUID;
	private int					devnullGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;
	private int					underprinceGUID;

	private boolean				bChamberConversation = false;
	private boolean				bAttachTransConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Underprince", "Chamber region", "Chamber exit door", "Junction box"};

	public SEW4_Main(CodexActor Underprince, CodexRegion ChamberRegion, CodexThing ChamberExitDoor, CodexThing JunctionBox)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Underprince = new CodexActor(Underprince.GetGUID());
		_ChamberRegion = new CodexRegion(ChamberRegion.GetGUID());
		_ChamberExitDoor = new CodexThing(ChamberExitDoor.GetGUID());
		_JunctionBox = new CodexThing(JunctionBox.GetGUID());

		CaptureThing(_Underprince.GetGUID());
		CaptureThing(_ChamberRegion.GetGUID());
		CaptureThing(_ChamberExitDoor.GetGUID());
		CaptureThing(_JunctionBox.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		devnullGUID = CodexThing.GuidFromCastID("DevNull");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		underprinceGUID = _Underprince.GetGUID();

		_Samuel = new CodexActor(samuelGUID);

		_JunctionBox.SetDescriptionID("JUNCTIONBOX");
	}
	
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_REMOVETRANS:

				_Samuel.CancelOverrideActorWeapon();
				break;
		}
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(guid == _JunctionBox.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SEW4_ATTACHTRANS)) 
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW4_ATTACHTRANS);
			
			AttachTransConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _ChamberRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SEW4_CHAMBER))
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW4_CHAMBER);

			ChamberConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Underprince.GetGUID())
		{
			CodexSound.PopMusic();
			// open door that leads out of chamber
			_ChamberExitDoor.MoveToFrame(1, 100);
			_ChamberExitDoor.SetCollideType(THING_COLLIDE_NONE);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _ChamberExitDoor.GetGUID())
			_ChamberExitDoor.Remove();
	}

	public void ChamberConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Boss_3.mp3", 50);
		AIOff();
		bChamberConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "35_1_Chamber", "35_1_Chamber.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AttachTransConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bAttachTransConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "35_1_AttachTrans", "35_1_AttachTrans.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bChamberConversation)
		{
			bChamberConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();

			// open exit/entrance to new york uptown from sewers
			CodexSequence.OpenExit("NewYorkUptown", 1);
			CodexSequence.OpenExit("Sewers4", 1);
		}

		if(bAttachTransConversation)
		{
			bAttachTransConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_Transponder"));
			q.Complete();

			CodexSequence.ChangeScene("DevNullApartment", "DEVN_36_1.nsd");

			// open fortitude for samuel
			CodexActor Samuel = new CodexActor(samuelGUID);
			Samuel.SetActorDisciplineLevel("Fortitude", -1);

			//String aFormat = "%A" + Samuel.GetName() + "%g" + "DGRP_FORTITUDE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			// auto advance
			CodexSequence.Advance(christofGUID);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bChamberConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 1, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, underprinceGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 7);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Underprince.ncp", 30);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 1, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Underprince.ncp", 30);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 1, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 1, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Underprince.ncp", 30);
							break;

					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Underprince.ncp", 30);
							break;

					} // switch(curLine)
					break;

			} // switch(curEvent)
		}

		if(bAttachTransConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							// Plug it in, Sam
							_Samuel.OverrideActorWeapon("weapTransmitter", false, false);
							_Samuel.PlayMotionSetMode(MOTION_GESTURE3, false, (float)30.0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Transponder.ncp", 30);
							SetTimer((float)1.0, TIMER_ID_REMOVETRANS);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Samuel.StopActorAction();
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Transponder.ncp", 30);
							break;

						case 3:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}
