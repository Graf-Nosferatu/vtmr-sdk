/**
 * ORDR 22.5 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class ORDR_Orvus extends CodexBuySell
{
	public static String _params[] = { "Lure of Flames Tome" };


	private CodexThing _lureFlamesTome;

	private int christofGUID;
	private int erikGUID;
	private int orvusGUID;
	private int wilhemGUID;

	private boolean bFirstOrvusConversation		= false;
	private boolean bOrvusAmuletConversation	= false;
	private boolean bReturnJournalConversation	= false;


	public ORDR_Orvus( CodexThing lureFlamesTome )
	{
		_lureFlamesTome = new CodexThing( lureFlamesTome.GetGUID() );
		CaptureThing( _lureFlamesTome.GetGUID() );

		greetingLine = new String[]{ "Orvus_0_0_1922.wav" };
		numGreetingLines = 1;
		byeLine = new String[]{ "Orvus_0_0_1923.wav" };
		numByeLines = 1;

		shopName = "ORDEROFHERMES";
		shopFileName = "Orvus.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;
	}


	public void beginscene(int clientGuid, int captureID)
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		erikGUID = CodexThing.GuidFromCastID( "Erik" );
		orvusGUID = CodexThing.GuidFromCastID( "Orvus" );
		wilhemGUID = CodexThing.GuidFromCastID( "Wilhem" );

		if( !CodexSequence.GetChronicleFlag( ViennaChronicle.ORDR_JOURNALCONV ) )
		{
			_lureFlamesTome.SetThingFlags( THING_RENDERFLAG_DONTRENDER );
			_lureFlamesTome.SetThingFlags( THING_FLAG_NOHIGHLIGHT );
		}
	}


	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// see if this is his first visit
		if( !CodexSequence.GetChronicleFlag( ViennaChronicle.ORDR_TALKEDONCE ) )
		{
			CodexSequence.SetChronicleFlag( ViennaChronicle.ORDR_TALKEDONCE );

			FirstOrvusConversation();
		}
		else if( CodexSequence.GetChronicleFlag( ViennaChronicle.TEU2_AMULETRECOVERED ) &&
			!CodexSequence.GetChronicleFlag( ViennaChronicle.ORDR_AMULETCHARGED ) )
		{
			CodexSequence.SetChronicleFlag( ViennaChronicle.ORDR_AMULETCHARGED );

			new CodexQuest( CodexQuest.Load( "V1_ReturnAmulet" ) ).Complete();

			OrvusAmuletConversation();
		}
		else if( CodexSequence.GetChronicleFlag( ViennaChronicle.ORDR_ETRIUSJOURNAL ) &&
			!CodexSequence.GetChronicleFlag( ViennaChronicle.ORDR_JOURNALCONV ) )
		{
			CodexSequence.SetChronicleFlag( ViennaChronicle.ORDR_JOURNALCONV );

			ReturnJournalConversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended(boolean bAborted)
	{
		EndBuySell( true );
	}


	public void FirstOrvusConversation()
	{
		bFirstOrvusConversation = BeginConversation( "20_1_Orvus" );
	}


	public void OrvusAmuletConversation()
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		bOrvusAmuletConversation = BeginConversation( "22_5_Orvus" );
	}


	public void ReturnJournalConversation()
	{
		bReturnJournalConversation = BeginConversation( "23_1_ReturnJournal" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bFirstOrvusConversation )
		{
			bFirstOrvusConversation = EndConversation();

			if( CodexSequence.GetChronicleFlag( ViennaChronicle.TEU2_AMULETRECOVERED ) &&
				!CodexSequence.GetChronicleFlag( ViennaChronicle.ORDR_AMULETCHARGED ) )
			{
				CodexSequence.SetChronicleFlag( ViennaChronicle.ORDR_AMULETCHARGED );

				new CodexQuest( CodexQuest.Load( "V1_ReturnAmulet" ) ).Complete();

				OrvusAmuletConversation();

				// skip buy/sell
				return;
			}
		}

		if( bOrvusAmuletConversation )
		{
			CodexSound.PopMusic();
			bOrvusAmuletConversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience( 50 );

			new CodexQuest( CodexQuest.Load( "V1_EtriusJournal" ) );

			// show southern ring and haus on map
			CodexSequence.SetLocationFlags( "HausDeHexe1", LOCATION_FLAG_SHOWINMAP );
			CodexSequence.SetLocationFlags( "SouthernRingStrasse", LOCATION_FLAG_SHOWINMAP );
		}

		if( bReturnJournalConversation )
		{
			bReturnJournalConversation = EndConversation();

			_lureFlamesTome.ClearThingFlags( THING_RENDERFLAG_DONTRENDER );
			_lureFlamesTome.ClearThingFlags( THING_FLAG_NOHIGHLIGHT );
		}

		BeginBuySell( false );
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bFirstOrvusConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, erikGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 2:
					CodexCamera.SetupCutscene( starterGuid, wilhemGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 3:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30 );
					break;

				case 4:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, orvusGUID);
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;
			}
		}

		if( bOrvusAmuletConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, orvusGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 1:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 2:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 3:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 4:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 5:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 6:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 7:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 8:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 9:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 10:
					owner.PlayMotionSetMode( MOTION_SPELL, false, 30f );
					owner.SpawnThing( "blueMagic" );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 11:
					owner.StopActorAction();
					break;

				case 12:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30 );
					break;
			}
		}

		if( bReturnJournalConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, orvusGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 1:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "OrderOfHermes.ncp", 30 );
					break;
			}
		}
	}
}