/**
 * ORDR 22.5 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ORDR_Orvus extends Codex
{
//	private CodexActor	_Orvus;
	private ViennaChronicle	chronScript;

	private int			christofGUID;
	private int			erikGUID;
	private int			orvusGUID;
	private int			wilhemGUID;
	
	private int			ownerGuid = 0;
	private CodexActor	owner;

	private CodexThing	_lureFlamesTome;

	private String				descriptionID			= "ORDEROFHERMES";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;

	private static String		greetingLine[]			= {"Orvus_0_0_1922.wav"};
	private static final int	numGreetingLines		= 1;
	private static String		byeLine[]				= {"Orvus_0_0_1923.wav"};
	private static final int	numByeLines				= 1;

	//////////////////////////////////////////////////

	private boolean				bActive					= false;

	private boolean		bFirstOrvusConversation = false;
	private boolean		bOrvusAmuletConversation = false;
	private boolean		bReturnJournalConversation = false;

	public static String _params[] = {"Lure of Flames Tome"};

	public ORDR_Orvus(CodexThing lureFlamesTome)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_lureFlamesTome = new CodexThing(lureFlamesTome.GetGUID());

		CaptureThing(_lureFlamesTome.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		ownerGuid = GetClassThing();

		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		orvusGUID = CodexThing.GuidFromCastID("Orvus");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		if(IsActorGuid(ownerGuid))
			owner = new CodexActor(ownerGuid);

		if(!CodexSequence.GetChronicleFlag(chronScript.ORDR_JOURNALCONV))
		{
			_lureFlamesTome.SetThingFlags(THING_RENDERFLAG_DONTRENDER);
			_lureFlamesTome.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		bActive = false;
		owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void BuySellInterface(int shopperGuid)
	{
		AddBuySell("Orvus.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

		ExecuteBuySell(ownerGuid, shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(bActive)
			return;

		if(IsActorGuid(ownerGuid))
		{
			// play the talk animation on the owner
			int duration = owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			bActive = true;
			owner.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// see if this is his first visit
			if(!CodexSequence.GetChronicleFlag(chronScript.ORDR_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.ORDR_TALKEDONCE);

				FirstOrvusConversation(clickerGuid, 0);
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.TEU2_AMULETRECOVERED) &&
				!CodexSequence.GetChronicleFlag(chronScript.ORDR_AMULETCHARGED))
			{
				CodexSequence.SetChronicleFlag(chronScript.ORDR_AMULETCHARGED);

				CodexQuest q = new CodexQuest(CodexQuest.Load("V1_ReturnAmulet"));
				q.Complete();

				OrvusAmuletConversation(clickerGuid, 0);
			}
			else if((CodexSequence.GetChronicleFlag(chronScript.ORDR_ETRIUSJOURNAL)) &&
				(!CodexSequence.GetChronicleFlag(chronScript.ORDR_JOURNALCONV)))
			{
				CodexSequence.SetChronicleFlag(chronScript.ORDR_JOURNALCONV);

				ReturnJournalConversation(clickerGuid, 0);
			}
			else
			{
				SetTimer((float)0.5, 1, clickerGuid);
			}
		}
	}


	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
			SetTimer((float)(2.0), 2);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(timerID == 1)
		{
			// play the greeting sound
			new CodexSound(greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid);

			BuySellInterface((int)arg0);
		}
		else
		if(timerID == 2)
		{
			int duration = owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			//SetTimer((float)(duration / 1000), 3);
			SetTimer((float)0.5, 3);
		}
		else
		if(timerID == 3)
		{
			// stop the special animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);
			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}


	public void FirstOrvusConversation(int starterGuid, int npcGuid)
	{
		bFirstOrvusConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "20_1_Orvus", "20_1_Orvus.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void OrvusAmuletConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bOrvusAmuletConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "22_5_Orvus", "22_5_Orvus.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void ReturnJournalConversation(int starterGuid, int npcGuid)
	{
		bReturnJournalConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "23_1_ReturnJournal", "23_1_ReturnJournal.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bFirstOrvusConversation)
		{
			bFirstOrvusConversation = false;

			if(CodexSequence.GetChronicleFlag(chronScript.TEU2_AMULETRECOVERED) &&
				!CodexSequence.GetChronicleFlag(chronScript.ORDR_AMULETCHARGED))
			{
				CodexSequence.SetChronicleFlag(chronScript.ORDR_AMULETCHARGED);

				CodexQuest q = new CodexQuest(CodexQuest.Load("V1_ReturnAmulet"));
				q.Complete();

				OrvusAmuletConversation(starterGuid, 0);

				// skip buy/sell
				bActive = false;
				owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
				return;
			}
			else
			{
				AIOn();
				CodexCamera.Release(starterGuid);
			}
		}

		if(bOrvusAmuletConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bOrvusAmuletConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_EtriusJournal"));

			// show southern ring and haus on map
			CodexSequence.SetLocationFlags("HausDeHexe1", LOCATION_FLAG_SHOWINMAP);
			CodexSequence.SetLocationFlags("SouthernRingStrasse", LOCATION_FLAG_SHOWINMAP);
		}

		if(bReturnJournalConversation)
		{
			AIOn();
			bReturnJournalConversation = false;
			CodexCamera.Release(starterGuid);

			_lureFlamesTome.ClearThingFlags(THING_RENDERFLAG_DONTRENDER);
			_lureFlamesTome.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bFirstOrvusConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orvusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bOrvusAmuletConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orvusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 10:

							owner.PlayMotionSetMode(MOTION_SPELL, false, (float)30.0);
							owner.SpawnThing("blueMagic");
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 11:

							owner.StopActorAction();
							break;

						case 12:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bReturnJournalConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orvusGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}
