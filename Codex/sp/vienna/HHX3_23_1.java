/**
 *  Haus de Hexe3, 23.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HHX3_23_1 extends Codex
{
	private ViennaChronicle	chronScript;

	private CodexThing			_arcPiece3;
	private CodexRegion			_teleportRegion;
	private CodexRegion			_LabsRegion;

	private int					erikGUID;
	private int					wilhemGUID;
	private int					serenaGUID;
	
	private boolean				bLabsConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Arcanulum piece 3", "Teleport region", "Labs region"};

	public HHX3_23_1(CodexThing arcPiece3, CodexRegion teleportRegion, CodexRegion LabsRegion)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_arcPiece3 = new CodexThing(arcPiece3.GetGUID());
		_teleportRegion = new CodexRegion(teleportRegion.GetGUID());
		_LabsRegion = new CodexRegion(LabsRegion.GetGUID());

		CaptureThing(_arcPiece3.GetGUID());
		CaptureThing(_teleportRegion.GetGUID());
		CaptureThing(_LabsRegion.GetGUID());
	}
	
	// --------------------------------------------------------------------------------------------
	
	public void beginscene(int clientGuid, int captureID)
	{
		erikGUID = CodexThing.GuidFromCastID("Erik");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
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
		// labs conversation
		else if(guid == _LabsRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.HHX3_LABS))
		{
			CodexSequence.SetChronicleFlag(chronScript.HHX3_LABS);

			LabsConversation(causeGUID, 0);
		}

	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _arcPiece3.GetGUID())
		{
			// set a flag to specify piece 3 was recovered
			CodexSequence.SetChronicleFlag(chronScript.HHX1_ARCPIECE3RECOVERED);

			new CodexSound("teleport_03.wav", (float)2048.0, (float)4096.0, 100, 0, 0, picker);
		}

		return(true);
	}

	public void LabsConversation(int starterGuid, int npcGuid)
	{
		bLabsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Labs", "23_1_Labs.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLabsConversation)
		{
			AIOn();
			bLabsConversation = false;
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

						CodexCamera.SetupCutscene(starterGuid, wilhemGUID, serenaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
						break;

					case 1:

						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
