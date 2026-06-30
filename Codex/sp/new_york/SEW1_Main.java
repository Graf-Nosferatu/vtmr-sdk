/**
 *  SEW1_Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 *
*/

public class SEW1_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private CodexRegion			_EnterSewerRegion;
	private CodexThing			_lever1;
	private CodexThing			_lever2;
	private CodexThing			_door;

	private CodexActor			_Nosferatu1;
	private CodexActor			_Nosferatu2;
	private CodexActor			_Nosferatu3;

	private int					christofGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;

	private CodexRegion			_NosferatuRegion;

	private boolean				bLever1Done = false;
	private boolean				bLever2Done = false;

	private boolean				bDoorOpen = false;

	private boolean				bEnterConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Enter sewer region", "Lever 1", "Lever 2", "Door", 
										"Nosferatu 1", "Nosferatu 2", "Nosferatu 3", "Nosferatu region"};

	public SEW1_Main(CodexRegion EnterSewerRegion, CodexThing lever1, CodexThing lever2, CodexThing door, 
					CodexActor Nosferatu1, CodexActor Nosferatu2, CodexActor Nosferatu3, CodexRegion NosferatuRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_EnterSewerRegion = new CodexRegion(EnterSewerRegion.GetGUID());
		_lever1 = new CodexThing(lever1.GetGUID());
		_lever2 = new CodexThing(lever2.GetGUID());
		_Nosferatu1 = new CodexActor(Nosferatu1.GetGUID());
		_Nosferatu2 = new CodexActor(Nosferatu2.GetGUID());
		_Nosferatu3 = new CodexActor(Nosferatu3.GetGUID());
		_NosferatuRegion = new CodexRegion(NosferatuRegion.GetGUID());

		_door = new CodexThing(door.GetGUID());


		CaptureThing(_EnterSewerRegion.GetGUID());

		// capture the levers so we get clicked messages for them
		CaptureThing(_lever1.GetGUID(), 1);
		CaptureThing(_lever2.GetGUID(), 2);

		CaptureThing(_NosferatuRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// if door is open already
		if(bDoorOpen)
			return;

		// move the lever that was clicked
		switch(captureId)
		{
			case 1:

				_lever1.RotatePivot(1, 4);
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW1_LEVER1OPENED");

				bLever1Done = true;

				if(bLever2Done)
				{
					bDoorOpen = true;
					_door.MoveToFrame(1, 100);
					CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW1_DOOROPENED");
				}
				break;

			case 2:

				_lever2.RotatePivot(1, 4);
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW1_LEVER2OPENED");

				bLever2Done = true;

				if(bLever1Done)
				{
					bDoorOpen = true;
					_door.MoveToFrame(1, 100);
					CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "SEW1_DOOROPENED");
				}
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _EnterSewerRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SEW1_ENTERSEWER))
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW1_ENTERSEWER);

			EnterConversation(causeGUID, 0);
		}
		else if(guid == _NosferatuRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SEW1_NOSFERATU))
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW1_NOSFERATU);
		}
	}

	public void EnterConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bEnterConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "35_1_EnterSewers", "35_1_EnterSewers.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		bEnterConversation = false;
		CodexCamera.Release(starterGuid);
		AIOn();
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "EnterSewers.ncp", 30);
						break;

					case 1:

						CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "EnterSewers.ncp", 30);
						break;

					case 3:
						CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

					case 4:

						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "EnterSewers.ncp", 30);
						break;

					case 5:

						//CodexCamera.SetupCutscene(starterGuid, pinkGUID, samuelGUID);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bLever1Done);
		CodexSequence.SaveBoolean(bLever2Done);
		CodexSequence.SaveBoolean(bDoorOpen);
	}

	public void restore(int flags)
	{
		bLever1Done = CodexSequence.RestoreBoolean();
		bLever2Done = CodexSequence.RestoreBoolean();
		bDoorOpen = CodexSequence.RestoreBoolean();
	}
}
