/**
 *  Haus de Hexe2, 23.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HHX2_23_1 extends Codex
{
	private ViennaChronicle	chronScript;

	private CodexActor			_Virstania;
	private CodexRegion			_VirstaniaRegion;

	private CodexThing			_arcPiece1;
	private CodexRegion			_teleportRegion;

	private CodexActor			_Gargoyle;

	private int					christofGUID;
	private int					erikGUID;
	private int					virstaniaGUID;

	private boolean				bVirstaniaConversation = false;
	private boolean				bGargoyleConversation = false;
	
	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Virstania", "Virstania region", "Arcanulum piece 1", 
										"Teleport Region", "Gargoyle"};

	public HHX2_23_1(CodexActor Virstania, CodexRegion VirstaniaRegion, CodexThing arcPiece1, 
						CodexThing teleportRegion, CodexActor Gargoyle)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_Virstania = new CodexActor(Virstania.GetGUID());
		_VirstaniaRegion = new CodexRegion(VirstaniaRegion.GetGUID());
		_arcPiece1 = new CodexThing(arcPiece1.GetGUID());
		_teleportRegion = new CodexRegion(teleportRegion.GetGUID());
		_Gargoyle = new CodexActor(Gargoyle.GetGUID());

		virstaniaGUID = _Virstania.GetGUID();

		CaptureThing(_Virstania.GetGUID());
		CaptureThing(_VirstaniaRegion.GetGUID());
		CaptureThing(_arcPiece1.GetGUID());
		CaptureThing(_teleportRegion.GetGUID());
		CaptureThing(_Gargoyle.GetGUID());
	}
	
	// --------------------------------------------------------------------------------------------
	
	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");

		if(!CodexSequence.GetChronicleFlag(chronScript.HHX1_VIRSTANIA))
		{
			_Virstania.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Virstania.SetActorFlags(THING_AF_AIPAUSED);
		}
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

		if(guid == _teleportRegion.GetGUID())
		{
			// set flag saying they're teleporting in
			CodexSequence.SetChronicleFlag(chronScript.HHX1_TELEPORTIN);

			// play the teleporter sound, teleport them in 1.1 seconds
			CodexSound snd1 = new CodexSound("teleport_02.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _teleportRegion.GetGUID());
			SetTimer(1.3f);
		}

		if(guid == _VirstaniaRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.HHX1_VIRSTANIA))
		{
			CodexSequence.SetChronicleFlag(chronScript.HHX1_VIRSTANIA);

			VirstaniaConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Gargoyle.GetGUID())
		{
			GargoyleConversation(erikGUID, 0);
		}
		// on Virstania's death
		else if(guid == _Virstania.GetGUID())
		{
			CodexSound.PopMusic();
		}
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _arcPiece1.GetGUID())
		{
			// set a flag to specify piece 1 was recovered
			CodexSequence.SetChronicleFlag(chronScript.HHX1_ARCPIECE1RECOVERED);

			new CodexSound("teleport_03.wav", (float)2048.0, (float)4096.0, 100, 0, 0, picker);
		}

		return(true);
	}

	public void VirstaniaConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_3.mp3", 50);
		bVirstaniaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Virstania", "23_1_Virstania.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GargoyleConversation(int starterGuid, int npcGuid)
	{
		bGargoyleConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Gargoyles", "23_1_Gargoyles.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bVirstaniaConversation)
		{
			AIOn();
			bVirstaniaConversation = false;
			CodexCamera.Release(starterGuid);

			_Virstania.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Virstania.ClearActorFlags(THING_AF_AIPAUSED);
		}

		if(bGargoyleConversation)
		{
			AIOn();
			bGargoyleConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bVirstaniaConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, virstaniaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, virstaniaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bVirstaniaConversation)

		if(bGargoyleConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bGargoylesConversation)
	}
}
