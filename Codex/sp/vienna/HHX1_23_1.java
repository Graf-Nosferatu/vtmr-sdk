/**
 *  Haus de Hexe1, 23.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HHX1_23_1 extends Codex
{
	private ViennaChronicle	chronScript;

	private static final int TIMER_ID_ENTRYWAY			= 0;
	private static final int TIMER_ID_ARCPATH			= 1;
	private static final int TIMER_ID_DOOREFFECT		= 2;
	private static final int TIMER_ID_HIDEDOOR			= 3;
	private static final int TIMER_ID_CAMERARELEASE		= 4;

	private CodexActor			_tremere1;
	private CodexActor			_tremere2;

	private int					christofGUID;
	private int					serenaGUID;
	private int					wilhemGUID;

	private CodexThing			_etriusDoor;
	
	private CodexThing			_floorArcanulum;
	private CodexThing			_arcanulumPiece1;
	private CodexThing			_arcanulumPiece2;
	private CodexThing			_arcanulumPiece3;

	private CodexThing			_arcDoorEffect;
	private int					_arcDoorEffectGuid;

	private CodexRegion			_findArcanulumRegion;

	private boolean				bEntrywayConversation = false;
	private boolean				bArcanulumConversation = false;
	
	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Tremere1", "Tremere2", "Door to Etrius", "Floor Arcanulum", 
										"Arcanulum Piece1", "Arcanulum Piece2", "Arcanulum Piece3",
										"Find Arcanulum conversation region"};

	public HHX1_23_1(CodexActor tremere1, CodexActor tremere2, CodexThing etriusDoor, CodexThing floorArcanulum, 
					CodexThing arcanulumPiece1, CodexThing arcanulumPiece2, CodexThing arcanulumPiece3,
					CodexRegion findArcanulumRegion)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_tremere1 = new CodexActor(tremere1.GetGUID());
		_tremere2 = new CodexActor(tremere2.GetGUID());

		_etriusDoor = new CodexThing(etriusDoor.GetGUID());

		_floorArcanulum = new CodexThing(floorArcanulum.GetGUID());
		_arcanulumPiece1 = new CodexThing(arcanulumPiece1.GetGUID());
		_arcanulumPiece2 = new CodexThing(arcanulumPiece2.GetGUID());
		_arcanulumPiece3 = new CodexThing(arcanulumPiece3.GetGUID());

		_findArcanulumRegion = new CodexRegion(findArcanulumRegion.GetGUID());

		CaptureThing(_floorArcanulum.GetGUID());
		CaptureThing(_findArcanulumRegion.GetGUID());
	}
	
	// --------------------------------------------------------------------------------------------
	
	public void beginscene(int clientGuid, int captureID)
	{
		float[]		offset = new float[3];

		christofGUID = CodexThing.GuidFromCastID("Christof");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
		
		if(CodexSequence.GetChronicleFlag(chronScript.HHX1_TELEPORTIN))
		{
			// clear this chron flag for next time they teleport
			CodexSequence.ClearChronicleFlag(chronScript.HHX1_TELEPORTIN);

			// they've teleported in, play sound
			new CodexSound("teleport_04.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _floorArcanulum.GetGUID());
		}

		// hide the arcanulum pieces that haven't been placed yet
		if(!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE1PLACED))
			_arcanulumPiece1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

		if(!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE2PLACED))
			_arcanulumPiece2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

		if(!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE3PLACED))
			_arcanulumPiece3.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

		// initial entryway/tremere scene played out the first time the party enters HHX1
		if(!CodexSequence.GetChronicleFlag(chronScript.HHX1_ENTRYWAY))
		{
			// effect on arc door
			_etriusDoor.SetShell("shellSprite_blue", 0x1000, .2f, .2f, 1, 1);

			// spawn effect
			_arcDoorEffectGuid = _etriusDoor.SpawnThing("ArcanulumDoorEffect");

			offset[0] = -160; 
			offset[1] = 0; 
			offset[2] = -200;

			_etriusDoor.AttachThing(_arcDoorEffectGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

			// this may not be necessary as it's turned off in the conversation
			AIOff();

			CodexSequence.SetChronicleFlag(chronScript.HHX1_ENTRYWAY);
			SetTimer(1, TIMER_ID_ENTRYWAY, clientGuid);
		}

		// if any of the three pieces haven't been placed, make the floor piece no walk collide, but selectable
		// and make the pieces nocollide
		if(!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE1PLACED) ||
		   !CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE2PLACED) ||
		   !CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE3PLACED))	
		{
			_floorArcanulum.SetThingFlags(THING_FLAG_NOBLOCK);

			_arcanulumPiece1.SetCollideType(THING_COLLIDE_NONE);
			_arcanulumPiece2.SetCollideType(THING_COLLIDE_NONE);
			_arcanulumPiece3.SetCollideType(THING_COLLIDE_NONE);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _findArcanulumRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.HHX1_FINDARCANULUM))
		{
			CodexSequence.SetChronicleFlag(chronScript.HHX1_FINDARCANULUM);

			ArcanulumConversation(causeGUID, 0);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(guid == _floorArcanulum.GetGUID())
		{
			// if the piece has been recovered and placed, clear the render flags to show it 
			// and set the chron flag indicating its been placed
			// hopefully along with showing the piece in the floor, we will have some
			// particle effect to throw around to indicate something happaned
			if(CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE1RECOVERED) &&
				!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE1PLACED))
			{
				CodexSequence.SetChronicleFlag(chronScript.HHX1_ARCPIECE1PLACED);

				_arcanulumPiece1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "HHX1_ARCPIECE1PLACED");

				// placeholder effect
				_arcanulumPiece1.SpawnThing("redMagic");

				new CodexSound("teleport_03.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _floorArcanulum.GetGUID());
			}

			if(CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE2RECOVERED) &&
				!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE2PLACED))
			{
				CodexSequence.SetChronicleFlag(chronScript.HHX1_ARCPIECE2PLACED);

				_arcanulumPiece2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "HHX1_ARCPIECE2PLACED");

				// placeholder effect
				_arcanulumPiece2.SpawnThing("greenMagic");

				new CodexSound("teleport_03.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _floorArcanulum.GetGUID());
			}

			if(CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE3RECOVERED) &&
				!CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE3PLACED))
			{
				CodexSequence.SetChronicleFlag(chronScript.HHX1_ARCPIECE3PLACED);

				_arcanulumPiece3.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "HHX1_ARCPIECE3PLACED");

				// placeholder effect
				_arcanulumPiece3.SpawnThing("blueMagic");

				new CodexSound("teleport_03.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _floorArcanulum.GetGUID());
			}

			// if all three pieces have been placed
			if(CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE1PLACED) &&
			   CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE2PLACED) &&
			   CodexSequence.GetChronicleFlag(chronScript.HHX1_ARCPIECE3PLACED))	
			{
				// set this flag so the findArcanulum conversation doesn't happen
				CodexSequence.SetChronicleFlag(chronScript.HHX1_FINDARCANULUM);

				_floorArcanulum.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
				_floorArcanulum.SetCollideType(THING_COLLIDE_NONE);

				_arcanulumPiece1.SetCollideType(THING_COLLIDE_NONE);
				_arcanulumPiece2.SetCollideType(THING_COLLIDE_NONE);
				_arcanulumPiece3.SetCollideType(THING_COLLIDE_NONE);

				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "HHX1_ARCDOOROPENED");

				SetTimer(0, TIMER_ID_ARCPATH);
				SetTimer(5, TIMER_ID_DOOREFFECT);
			}
			else
			{
				CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "HHX1_ARCDOORSTILLLOCKED");
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ENTRYWAY:

				EntrywayConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_ARCPATH:

				CodexCamera.PlayPath(christofGUID, GetGUID(), "HHX1Arcanulum.ncp", 75);				
				break;

			case TIMER_ID_DOOREFFECT:

				// fade out the door
				_etriusDoor.SetShell("shellSprite_blue", 0x1080, .8f, 0, 3, 1);

				new CodexSound("teleport_01.WAV", 1000, 2000, 100, 0, 0, christofGUID);

				SetTimer(3, TIMER_ID_HIDEDOOR);
				break;

			case TIMER_ID_HIDEDOOR:

				// disable the emitter effect
				_arcDoorEffect = new CodexThing(_arcDoorEffectGuid);
				_arcDoorEffect.DisableEmitter();

				// remove the door
				_etriusDoor.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_etriusDoor.SetCollideType(THING_COLLIDE_NONE);

				SetTimer(1, TIMER_ID_CAMERARELEASE);
				break;

			case TIMER_ID_CAMERARELEASE:

				CodexCamera.Release(christofGUID);
				break;
		}
	}

	public void EntrywayConversation(int starterGuid, int npcGuid)
	{
		bEntrywayConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "23_1_Entryway", "23_1_Entryway.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void ArcanulumConversation(int starterGuid, int npcGuid)
	{
		bArcanulumConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "23_1_FindArcanulum", "23_1_FindArcanulum.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bEntrywayConversation)
		{
			AIOn();
			bEntrywayConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bArcanulumConversation)
		{
			AIOn();
			bArcanulumConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		
		if(bEntrywayConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					_tremere1.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					_tremere2.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					_tremere1.StopActorAction();
					_tremere2.StopActorAction();
					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bArcanulumConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, serenaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(_arcDoorEffectGuid);
	}

	public void restore(int flags)
	{
		_arcDoorEffectGuid = CodexSequence.RestoreInt();
	}
}
