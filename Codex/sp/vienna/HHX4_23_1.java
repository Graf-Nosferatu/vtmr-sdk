/**
 *  Haus de Hexe4, 23.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HHX4_23_1 extends Codex
{
	private ViennaChronicle	chronScript;

	private CodexThing			_arcPiece2;
	private CodexRegion			_teleportRegion;

	private CodexActor			_Tremere1;
	private CodexActor			_Tremere2;
	private CodexRegion			_LibraryRegion;

	private int					christofGUID;

	private boolean				bLibraryConversation = false;
	
	public static String _params[] =  {"Arcanulum piece 2", "Teleport region", 
										"Tremere 1", "Tremere 2", "Library region"};

	public HHX4_23_1(CodexThing arcPiece2, CodexRegion teleportRegion, 
						CodexActor Tremere1, CodexActor Tremere2, CodexRegion LibraryRegion)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_arcPiece2 = new CodexThing(arcPiece2.GetGUID());
		_teleportRegion = new CodexRegion(teleportRegion.GetGUID());
		_Tremere1 = new CodexActor(Tremere1.GetGUID());
		_Tremere2 = new CodexActor(Tremere2.GetGUID());
		_LibraryRegion = new CodexRegion(LibraryRegion.GetGUID());

		CaptureThing(_arcPiece2.GetGUID());
		CaptureThing(_teleportRegion.GetGUID());
		CaptureThing(_Tremere1.GetGUID());
		CaptureThing(_Tremere2.GetGUID());
		CaptureThing(_LibraryRegion.GetGUID());
	}
	
	// --------------------------------------------------------------------------------------------
	
	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
	}
	
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// take them back to hausdehexe1 and drop them right at the arcanulum		
		CodexSequence.Jump("HausDeHexe1", 10);	
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		// take them back to hausdehexe1 and drop them right at the arcanulum
		if(guid == _teleportRegion.GetGUID())
		{
			// set flag saying they're teleporting in
			CodexSequence.SetChronicleFlag(chronScript.HHX1_TELEPORTIN);

			// play the teleporter sound, teleport them in 1.1 seconds
			CodexSound snd1 = new CodexSound("teleport_02.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _teleportRegion.GetGUID());
			SetTimer(1.3f);
		}
		else if(guid == _LibraryRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.HHX4_LIBRARY))
		{
			CodexSequence.SetChronicleFlag(chronScript.HHX4_LIBRARY);

			LibraryConversation(causeGUID, 0);
		}

	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _arcPiece2.GetGUID())
		{
			// set a flag to specify piece 2 was recovered
			CodexSequence.SetChronicleFlag(chronScript.HHX1_ARCPIECE2RECOVERED);

			new CodexSound("teleport_03.wav", (float)2048.0, (float)4096.0, 100, 0, 0, picker);
		}

		return(true);
	}

	public void LibraryConversation(int starterGuid, int npcGuid)
	{
		bLibraryConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Library", "23_1_Library.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLibraryConversation)
		{
			AIOn();
			bLibraryConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
