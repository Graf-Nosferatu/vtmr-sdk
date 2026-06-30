/**
 *  Haus de Hexe Etrius, 23.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HHX1_Etrius extends Codex
{
	private ViennaChronicle	chronScript;

	private static final int TIMER_ID_ETRIUSAPPEAR	= 0;
	private static final int TIMER_ID_ERIKHURT		= 1;
	private static final int TIMER_ID_ERIKRUN		= 2;
	private static final int TIMER_ID_ETRIUSSPELL	= 3;
	private static final int TIMER_ID_ERIKCROUCH	= 4;
	private static final int TIMER_ID_ERIKSCREAM	= 5;
	private static final int TIMER_ID_PARTYREACTION = 6;
	private static final int TIMER_ID_TRANSFORM		= 7;
	private static final int TIMER_ID_GARGOYLERISES	= 8;
	private static final int TIMER_ID_GARGOYLE		= 9;
	private static final int TIMER_ID_ETRIUS2		= 10;
	private static final int TIMER_ID_ETRIUSREMOVE	= 11;

	// number of frames from Etrius that we have to drop Erik's stuff
	private static final int MAX_DROPITEMS			= 16;

	private CodexActor			_Christof;
	private CodexActor			_Etrius;
	private CodexThing			_EtriusJournal;

	private CodexPlayer			_Erik;

	private CodexThing			beam;
	private int					beamGuid;
	private int					smokeGuid;
	private float[]				offset = new float[3];
	
	private int					christofGUID;
	private int					erikGUID;
	private int					etriusGUID;
	private int					serenaGUID;
	private int					wilhemGUID;

	private CodexActor			_erikGargoyle;
	private int					_erikGargoyleGuid;

	private CodexThing			_GargoyleSpawnSpot;

	private float[]				pos;
	
	private boolean				bEtrius1Conversation = false;
	private boolean				bEtrius2Conversation = false;
	private boolean				bEtrius3Conversation = false;
	
	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Etrius", "Etrius journal", "Gargoyle spawn spot"};

	public HHX1_Etrius(CodexActor Etrius, CodexThing EtriusJournal, CodexThing GargoyleSpawnSpot)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_Etrius = new CodexActor(Etrius.GetGUID());
		_EtriusJournal = new CodexThing(EtriusJournal.GetGUID());
		_GargoyleSpawnSpot = new CodexThing(GargoyleSpawnSpot.GetGUID());

		CaptureThing(_Etrius.GetGUID());
		CaptureThing(_EtriusJournal.GetGUID());
		CaptureThing(_GargoyleSpawnSpot.GetGUID());

		pos = new float[3];
	}
	
	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		etriusGUID = CodexThing.GuidFromCastID("Etrius");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
		
		CaptureThing(erikGUID);

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.ORDR_ETRIUSJOURNAL))
		{
			_Etrius.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Etrius.SetCollideType(THING_COLLIDE_NONE);
			_Etrius.SetActorFlags(THING_AF_AIPAUSED);
			_Etrius.SetActorFlags(THING_AF_INVUL);
		}
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _EtriusJournal.GetGUID())
		{
			CodexSequence.SetChronicleFlag(chronScript.ORDR_ETRIUSJOURNAL);

			// mark 'retrieve etrius' journal' quest completed
			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_EtriusJournal"));
			q.Complete();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("V1_ReturnToPrague"));

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HexEtriusAppear.ncp", 30);
			
			_Christof.LookAtThing(etriusGUID);

			// some fancy etrius appearance here probably
			_Etrius.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Etrius.SetCollideType(THING_COLLIDE_CYL);

			_Etrius.SetShell("BlueLightning", 0x5000, 0, 1, 1, 1);
			_Etrius.SpawnThing("blueMagic");

			CodexSound snd1 = new CodexSound("teleport_02.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Etrius.GetGUID());

			SetTimer((float)2.0, TIMER_ID_ETRIUSAPPEAR, picker);
		}

		return(true);
	}
	
	public void killed(int guid, int causeID, int captureID)
	{
		// on Erik's death
		if(guid == _erikGargoyleGuid)
		{
			// again, show etrius
			_Etrius.SetAlpha((float)1.0);

			pos = _Etrius.GetFramePosition(0);
			_Etrius.SendActorToPos(pos, (float)250.0);

			_Etrius.LookAtThing(_GargoyleSpawnSpot.GetGUID());

			_Etrius.SetCollideType(THING_COLLIDE_CYL);
			_Etrius.ClearActorFlags(THING_AF_AIPAUSED);

			// time for the gargoyle's death and for etrius to walk back to the center
			SetTimer(4, TIMER_ID_ETRIUS2, causeID);
		}

		if(guid == _Etrius.GetGUID())
		{
			pos = _Etrius.GetFramePosition(0);
			_Etrius.SetPosition(pos);
			
			_Etrius.ClearActorFlags(THING_AF_DEAD);
			_Etrius.SetActorFlags(THING_AF_INVUL);

			_Etrius.SetAlpha((float)0.6);

			_Etrius.SpawnThing("blueMagic");
			_Etrius.SetShell("BlueLightning", 0x5000, 0, 1, 1, 1);

			Etrius3Conversation(causeID, 0);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ETRIUSAPPEAR:

				Etrius1Conversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_ERIKHURT:

				_Etrius.StopActorAction();
				_Erik.PlayMotionSetMode(MOTION_CONFUSED, false, (float)30.0);
				_Erik.SetShell("shellSprite_white", 0x5000, 0, 1, 1, 1);

				CodexSound snd2 = new CodexSound("Erik_0_0_1660.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Erik.GetGUID());
				SetTimer((float)2.0, TIMER_ID_ERIKRUN);
				break;
		
			case TIMER_ID_ERIKRUN:

				_Erik.StopActorAction();

				pos = _GargoyleSpawnSpot.GetPosition();
				_Erik.SendActorToPos(pos, (float)210.0);

				SetTimer((float)4.0, TIMER_ID_ETRIUSSPELL);
				break;

			case TIMER_ID_ETRIUSSPELL:

				_Etrius.PlayMotionSetMode(MOTION_SPELLTHROW, false, (float)30.0);
				SetTimer((float)0.8, TIMER_ID_ERIKCROUCH);
				break;
			
			case TIMER_ID_ERIKCROUCH:

				// empty Erik's inventory onto the floor
				DropErikInventory();

				// spawn a beam at the player's position
				beamGuid = _Etrius.SpawnThing("blueLightningTwo");
				beam = new CodexThing(beamGuid);

				_Erik.SetShell("redCloudShell_b", 0x5000, 0, 1, 1, 1);

				// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
				// then set the target of the beam through Erik's chest(-10y,+84z)
				beam.AllocateFrames(2);
				offset[0] = 0; 
				offset[1] = 0; 
				offset[2] = 0;
				offset = _Erik.GetPosition();
				offset[1] = offset[1]-10;
				offset[2] = offset[2]+84;
				beam.SetFramePosition(1, offset);

				// attach thing needs an array of floats as a parm, using no offset essentially
				offset[0] = 0; 
				offset[1] = 0; 
				offset[2] = 0;

				// attach the beam to their jaw bone - if FindBone fails, it will attach it to the player's origin
				_Etrius.AttachThing(beamGuid, _Etrius.FindBone(MOTIONTAG_RFINGERS), offset, ATTACH_FLAG_AUTOREMOVE);

				CodexSound snd3 = new CodexSound("erik_transform_whoosh.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Etrius.GetGUID());
				
				// Erik falls to his knees
				_Erik.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
				CodexSound snd4 = new CodexSound("Erik_0_0_1658.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Erik.GetGUID());
				SetTimer((float)4.0, TIMER_ID_ERIKSCREAM);
				SetTimer((float)7.0, TIMER_ID_PARTYREACTION);
				break;

			case TIMER_ID_ERIKSCREAM:

				CodexSound snd5 = new CodexSound("Erik_Scream_3.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Erik.GetGUID());
				_Etrius.DetachThing(beamGuid);
				_Erik.SetShell("shellSprite_white", 0x5000, 0, 1, 3, 1);
				_Erik.SpawnThing("blueMagic");
				break;

			case TIMER_ID_PARTYREACTION:

				// the party is duly stunned
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HexPartyReaction.ncp", 30);
				CodexSound.PlayVoice(christofGUID, "Christof_39_1_1487", 100);
				
				_Etrius.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_ACTION8, false, (float)30.0);
				CodexSound snd6 = new CodexSound("vukodlakTransform_01.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Erik.GetGUID());
				SetTimer((float)1.5, TIMER_ID_TRANSFORM);
				break;

			case TIMER_ID_TRANSFORM:
			
				// remove erik, spawn flying garoyle in his spot
				_Christof.StopActorAction();
				_Erik.Remove();
				_erikGargoyle = new CodexActor(_GargoyleSpawnSpot.SpawnThing("Gargoyle_Erik"));
				_erikGargoyleGuid = _erikGargoyle.GetGUID();
				CaptureThing(_erikGargoyle.GetGUID());

				CodexSound snd7 = new CodexSound("gargoyle_roar.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _erikGargoyle.GetGUID());
				_erikGargoyle.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);

				SetTimer((float)0.0, TIMER_ID_GARGOYLERISES);
				break;
		
			case TIMER_ID_GARGOYLERISES:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HexEtriusLookAtErik.ncp", 30);
				_erikGargoyle.SetShell("shellSprite_white", 0x5000, 0, 1, 1, 1);
				_erikGargoyle.SpawnThing("blueMagic");
				SetTimer((float)6.0, TIMER_ID_GARGOYLE);
				break;

			case TIMER_ID_GARGOYLE:

				// ai turned off from etrius1 conversation
				AIOn();

				_erikGargoyle.StopActorAction();
				
				// "remove" etrius for the fight
				_Etrius.SetAlpha((float)0.6);

				pos = _Etrius.GetFramePosition(1);
				_Etrius.SendActorToPos(pos, (float)350.0);

				_Etrius.SetCollideType(THING_COLLIDE_NONE);
				_Etrius.SetActorFlags(THING_AF_AIPAUSED);

				CodexCamera.Release(0);

				_Etrius.LookAtThing(christofGUID);
				break;

			case TIMER_ID_ETRIUS2:

				Etrius2Conversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_ETRIUSREMOVE:

				CodexSound.PopMusic();
				// etrius vanishes - cutscene and some fancy particle effects hopefully
				CodexSound snd8 = new CodexSound("teleport_02.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Etrius.GetGUID());

				//_Etrius.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				//_Etrius.SetCollideType(THING_COLLIDE_NONE);
				//_Etrius.SetActorFlags(THING_AF_AIPAUSED);
				_Etrius.Remove();
				break;
		}
	}

	public void Etrius1Conversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_2.mp3", 50);
		bEtrius1Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Etrius1", "23_1_Etrius1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Etrius2Conversation(int starterGuid, int npcGuid)
	{
		bEtrius2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Etrius2", "23_1_Etrius2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Etrius3Conversation(int starterGuid, int npcGuid)
	{
		bEtrius3Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "23_1_Etrius3", "23_1_Etrius3.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bEtrius1Conversation)
		{
			// ai left off here so etrius doesn't attack yet
			bEtrius1Conversation = false;

			// free revive of christof here because Erik leaves the party and you could be left 
			// with no live party members
			_Christof.ReviveActor(100, 100);

			_Erik = new CodexPlayer(erikGUID);

			// free revive of Erik here so he's visible for the transformation scene
			if((_Erik.GetActorFlags() & THING_AF_DEAD) != 0)
			{
				_Erik.ReviveActor(100, 100);
			}
			
			_Erik.RemoveFromParty();

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HexEtriusLookAtErik.ncp", 30);

			_Etrius.PlayMotionSetMode(MOTION_SPELLTHROW, false, (float)30.0);

			SetTimer((float)1.0, TIMER_ID_ERIKHURT);
		}

		if(bEtrius2Conversation)
		{
			AIOn();
			bEtrius2Conversation = false;
			CodexCamera.Release(starterGuid);

			// party fights him here
			_Etrius.ClearActorFlags(THING_AF_AIPAUSED);
			_Etrius.ClearActorFlags(THING_AF_INVUL);
		}

		if(bEtrius3Conversation)
		{
			AIOn();
			bEtrius3Conversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// open up hands for serena
			CodexActor Serena = new CodexActor(serenaGUID);
			Serena.SetActorDisciplineLevel("Decay", -1);

			//String aFormat = "%A" + Serena.GetName() + "%g" + "DGRP_THAU_HANDSOFDESTRUCTION";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			// auto advance
			CodexSequence.Advance(christofGUID);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bEtrius1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, etriusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Erik.LookAtThing(etriusGUID);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, etriusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, etriusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							_Erik.PlayMotionSetMode(MOTION_GESTURE8, false, (float)30.0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, etriusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} //if(bEtrius1Conversation)
		else if(bEtrius2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Etrius.LookAtThing(christofGUID);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, etriusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
		else if(bEtrius3Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Etrius.LookAtThing(christofGUID);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, etriusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							_Etrius.SetShell("BlueLightning", 0x5000, 0, 1, 1, 1);
							_Etrius.SpawnThing("blueMagic");
							SetTimer(0.1f, TIMER_ID_ETRIUSREMOVE);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, serenaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void DropErikInventory()
	{
		CodexItem	item;
		int			itemGuid;

		// first frame where we'll be dropping Erik's stuff
		// is Etrius' frame 2
		int			frameIndex = 2;

		// walk through the inventory
		itemGuid = _Erik.GetActorFirstInventoryItem();

		while((itemGuid > 0) && ((frameIndex - 2) < MAX_DROPITEMS))
		{
			item = new CodexItem(itemGuid);

			// get next in inventory to prepare for next loop
			itemGuid = item.GetNextInventoryItem();	

			// remove the item from Christof's inventory
			item.RemoveItemFromInventory();

			// test here if we only want certain items to be dropped
			if(true)
			{
				// move the item to that position and orientation
				item.SetPosition(_Etrius.GetFramePosition(frameIndex));
				item.SetOrientation(_Etrius.GetFrameOrientation(frameIndex));

				// increment the current ghost
				frameIndex++;
			}
			else
			{
				// we destroy the object
				item.Remove();
			}
		}	
	}

	public void save(int flags)
	{
	   CodexSequence.SaveInt(_erikGargoyleGuid);
	}

	public void restore(int flags)
	{
	   _erikGargoyleGuid = CodexSequence.RestoreInt();
	}
}
