/**
 * Petrin Hill monastery 3  main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class MON3_Main extends Codex
{
	private PragueChronicle	chronScript;

	private static final int	TEXT_GARINOLJOURNAL			= 1;

	private static final int	TIMER_ID_APOLOGY			= 2;

	private	CodexRegion		_GarinolDoorRegion;
	private	CodexThing		_GarinolDoor;
	private	CodexItem		_GarinolKey;
	private	CodexItem		_LamiaSkullKey;
	private	CodexThing		_MercurioDoor;
	private	CodexRegion		_MercurioRegion;
	private	CodexActor		_Mercurio;
	private CodexItem		_GarinolJournal;

	private CodexActor		_Christof;
	private CodexActor		_Wilhem;
	
	private int				christofGUID;
	private int				wilhemGUID;
	private int				mercurioGUID;

	private int				whichText = 0;

	private int				humanityTrack = 0;

	private boolean			bMercurioConversation = false;
	private boolean			bSkullKeyConversation = false;
	private boolean			bSkullKey2Conversation = false;
	private boolean			bLamiaConversation = false;
	private boolean			bStudyConversation = false;
	private boolean			bJournalConversation = false;
	private boolean			bApologyConversation = false;	
                                    
	public static String _params[] = {"Garinol door region", "Garinol door", "Garinol key",
									  "Lamia Skull key", "Mercurio Door", "Mercurio region", "Mercurio",
	                                  "Garinol's journal"};

	public MON3_Main(CodexRegion GarinolDoorRegion, CodexThing GarinolDoor, CodexItem GarinolKey, 
					 CodexItem LamiaSkullKey, CodexThing MercurioDoor, CodexRegion MercurioRegion, CodexActor Mercurio,
					 CodexItem GarinolJournal)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_GarinolDoorRegion = new CodexRegion(GarinolDoorRegion.GetGUID());
		_GarinolDoor = new CodexThing(GarinolDoor.GetGUID());
		_GarinolKey = new CodexItem(GarinolKey.GetGUID());
		_LamiaSkullKey = new CodexItem(LamiaSkullKey.GetGUID());
		_MercurioDoor = new CodexThing(MercurioDoor.GetGUID());
		_MercurioRegion = new CodexRegion(MercurioRegion.GetGUID());
		_Mercurio = new CodexActor(Mercurio.GetGUID());
		_GarinolJournal = new CodexItem(GarinolJournal.GetGUID());
		
		CaptureThing(_GarinolDoorRegion.GetGUID());
		CaptureThing(_GarinolDoor.GetGUID());
		CaptureThing(_GarinolKey.GetGUID());
		CaptureThing(_LamiaSkullKey.GetGUID());
		CaptureThing(_MercurioDoor.GetGUID());
		CaptureThing(_MercurioRegion.GetGUID());
		CaptureThing(_Mercurio.GetGUID());
		CaptureThing(_GarinolJournal.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
		mercurioGUID = _Mercurio.GetGUID();

		_Christof = new CodexActor(christofGUID);
		_Wilhem = new CodexActor(wilhemGUID);

		_GarinolDoor.SetThingFlags(THING_FLAG_VISBLOCK);
		_MercurioDoor.SetThingFlags(THING_FLAG_VISBLOCK);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _GarinolDoorRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_GARINOLDOORREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_GARINOLDOORREGION);

			StudyConversation(causeGUID, 0);
		}

		if(guid == _MercurioRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_MERCURIOREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_MERCURIOREGION);

			MercurioConversation(causeGUID, 0);
		}
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _GarinolKey.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_GARINOLKEYRECOVERED))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_GARINOLKEYRECOVERED);

			if(picker == christofGUID)
				SkullKeyConversation(picker, 0);
			else
				SkullKey2Conversation(picker, 0);
		}

		if(item == _LamiaSkullKey.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_LAMIAKEYRECOVERED))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_LAMIAKEYRECOVERED);

			LamiaConversation(picker, 0);
		}

		if(item == _GarinolJournal.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_JOURNALRECOVERED))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_JOURNALRECOVERED);

			whichText = TEXT_GARINOLJOURNAL;
			
			ExecuteText(picker, guid, "GarinolJournal");
		}

		return(true);
	}

	public void textended(int readerGuid)
	{
		switch(whichText)
		{
			case TEXT_GARINOLJOURNAL:

				JournalConversation(readerGuid, 0);		
				break;
		}
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(guid == _GarinolDoor.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.MON3_GARINOLKEYRECOVERED) &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_GARINOLDOOROPENED))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_GARINOLDOOROPENED);

			// play the unlocking sound and open the door
			new CodexSound("unlockHeavy.WAV", 300, 600, 100, 0, 0, guid);
			_GarinolDoor.RotatePivot(1, (float)3.0);

			// display unlocked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORUNLOCKED");

			//_GarinolDoor.ClearThingFlags(THING_FLAG_VISBLOCK);
			_GarinolDoor.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else if(guid == _GarinolDoor.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_GARINOLDOOROPENED))
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");
		}

		if(guid == _MercurioDoor.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.MON3_LAMIAKEYRECOVERED) &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_MERCURIODOOROPENED))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_MERCURIODOOROPENED);

			// play the unlocking sound and open the door
			new CodexSound("unlockHeavy.WAV", 300, 600, 100, 0, 0, guid);
			_MercurioDoor.RotatePivot(1, (float)3.0);

			// display unlocked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORUNLOCKED");

			//_MercurioDoor.ClearThingFlags(THING_FLAG_VISBLOCK);
			_MercurioDoor.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
		else if(guid == _MercurioDoor.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.MON3_MERCURIODOOROPENED))
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");
		}
	}


	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Mercurio.GetGUID())
		{
			CodexSound.PopMusic();

			CodexSequence.SetChronicleFlag(chronScript.MON3_MERCURIODEAD);

			if(CodexSequence.GetChronicleFlag(chronScript.MON3_APOLOGY))
			{
				SetTimer(0.5f, TIMER_ID_APOLOGY);
			}
		}
	}	

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_APOLOGY:
				ApologyConversation(christofGUID, 0);
				break;
		}
	}

	public void ApologyConversation(int starterGuid, int npcGuid)
	{
		bApologyConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_1_Apology", "8_1_Apology.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void MercurioConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Boss_2.mp3", 50);
		bMercurioConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Mercurio", "8_2_Mercurio.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void StudyConversation(int starterGuid, int npcGuid)
	{
		bStudyConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Study", "8_2_Study.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void SkullKeyConversation(int starterGuid, int npcGuid)
	{
		bSkullKeyConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Key1", "8_2_Key1.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void SkullKey2Conversation(int starterGuid, int npcGuid)
	{
		bSkullKey2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Key2", "8_2_Key2.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void LamiaConversation(int starterGuid, int npcGuid)
	{
		bLamiaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Skull", "8_2_Skull.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void JournalConversation(int starterGuid, int npcGuid)
	{
		bJournalConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 5);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Journal", "8_2_Journal.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bMercurioConversation)
		{
			if(!bAborted)
			{
				// we have to set Mercurio's flag back to a normal enemy
				_Mercurio.ClearActorFlags(THING_AF_INVUL + THING_AF_AIPAUSED + THING_AF_TALKTO);
			}

			AIOn();
			bMercurioConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			if(humanityTrack == -1)
			{
				// lose humanity here
				_Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 1, 0, 0);
			}
		}
		
		if(bApologyConversation)
		{
			AIOn();
			bApologyConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bStudyConversation)
		{
			bStudyConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
		
		if(bSkullKeyConversation)
		{
			bSkullKeyConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
		
		if(bSkullKey2Conversation)
		{
			bSkullKey2Conversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
		
		if(bLamiaConversation)
		{
			bLamiaConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}
		
		if(bJournalConversation)
		{
			AIOn();
			bJournalConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}


	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bMercurioConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, mercurioGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;
					}
					break; // event 0

				case 1:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, mercurioGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;
					}
					break; // event 1

				case 2:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, mercurioGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;
					}
					break; // event 2

				case 3:

					switch(curLine)
					{
						case 0:

							humanityTrack = -1;

							// set flag so we know to play the christof apology conversation
							CodexSequence.SetChronicleFlag(chronScript.MON3_APOLOGY);

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, mercurioGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;
					}
					break; // event 2

			} // switch(curEvent)

		} // if(bMercurioConversation)
		
		if(bStudyConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
		
		if(bSkullKeyConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
		
		if(bSkullKey2Conversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
		
		if(bLamiaConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
					break;
			}
		}
		
		if(bApologyConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bJournalConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}
