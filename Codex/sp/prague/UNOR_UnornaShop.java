/**
 * UNOR_UnornaShop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class UNOR_UnornaShop extends CodexBuySell
{
	public static String _params[] = { "Anezka's letter" };


	private CodexThing _anezkaLetter;

	private int christofGUID;
	private int unornaGUID;

	private boolean bUnornaFirstConversation		= false;
	private boolean bUnornaFirstGoodbyeConversation	= false;
	private boolean bQAUnornaAskConversation		= false;
	private boolean bQAUnornaConversation			= false;
	private boolean bUnornaVampireConversation		= false;
	private boolean bUnornaLetterConversation		= false;


	public UNOR_UnornaShop( CodexThing anezkaLetter )
	{
		_anezkaLetter = new CodexThing( anezkaLetter.GetGUID() );

		greetingLine = new String[]{ "Unorna_0_0_1904.wav", "Unorna_0_0_1925.wav", "Unorna_0_0_95.wav", "Unorna_0_0_1927" };
		numGreetingLines = 3;
		byeLine = new String[]{ "Unorna_0_0_1905.wav", "Unorna_0_0_1928.wav", "Unorna_0_0_96.wav", "Unorna_0_0_1906" };
		numByeLines = 3;

		shopName = "UNORNASHOP";
		shopFileName = "UnornaShop.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		unornaGUID = CodexThing.GuidFromCastID( "Unorna" );
		
		if( CodexSequence.GetChronicleFlag( PragueChronicle.SIM4_AHZRADEAD ) ) {
			// add in Unorna_0_0_1927 hello
			numGreetingLines = 4;
		}

		if( CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_LETTER ) ) {
			// add in Unorna_0_0_1906 goodbye
			numByeLines = 4;
		}

		if( CodexSequence.GetChronicleFlag( PragueChronicle.OLDT_ECATERINAREGION ) ) {
			shopFileName = "UnornaVampireShop.nbs";
		}

		if( !CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_LETTER ) )
		{
			// hide the anezka letter
			_anezkaLetter.SetRenderFlags( THING_RENDERFLAG_DONTRENDER );
			_anezkaLetter.SetThingFlags( THING_FLAG_NOHIGHLIGHT );
		}
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		if( CodexSequence.GetChronicleFlag( PragueChronicle.ANZK_LOCKETSCENE ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_LETTER ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.UNORD_LETTER );

			// show the letter from anezka
			_anezkaLetter.ClearRenderFlags( THING_RENDERFLAG_DONTRENDER );
			_anezkaLetter.ClearThingFlags( THING_FLAG_NOHIGHLIGHT );

			UnornaLetterConversation();
		}
		else if( CodexSequence.GetChronicleFlag(PragueChronicle.OLDT_ECATERINAREGION ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_VAMPIREGREETING ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.UNORD_VAMPIREGREETING );

			UnornaVampireConversation();
		}
		else if( CodexSequence.GetChronicleFlag( PragueChronicle.SIM4_AHZRADEAD ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.OLDT_ECATERINAREGION ) )
		{
			if( !CodexSequence.GetChronicleFlag( PragueChronicle.UNOR_ASK ) )
			{
				CodexSequence.SetChronicleFlag( PragueChronicle.UNOR_ASK );

				QAUnornaAskConversation();
			}
			else {
				QAUnornaConversation();
			}
		}
		else if( !CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_TALKEDONCE ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.UNORD_TALKEDONCE );

			UnornaFirstConversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( false );

		if( !CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_FIRSTGOODBYE ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.OLDT_ECATERINAREGION ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.UNORD_FIRSTGOODBYE );

			// play first bye line "go with god..."
			UnornaFirstGoodbyeConversation();
		}
		else {
			PlayByeSound();
		}
	}


	public void UnornaFirstConversation()
	{
		CodexSound.PushMusic( "DA_Conversation_1.mp3", 50 );
		bUnornaFirstConversation = BeginConversation( "4_1_Unorna" );
	}


	public void UnornaVampireConversation()
	{
		bUnornaVampireConversation = BeginConversation( "8_1_Unorna" );
	}


	public void UnornaFirstGoodbyeConversation()
	{
		bUnornaFirstGoodbyeConversation = BeginConversation( "4_1_UnornaFirstGoodbye" );
	}


	public void QAUnornaAskConversation()
	{
		bQAUnornaAskConversation = BeginConversation( "6_1_UnornaAsk" );
	}


	public void QAUnornaConversation()
	{
		CodexSound.PushMusic( "DA_Conversation_1.mp3", 50 );
		bQAUnornaConversation = BeginConversation( "6_1_Unorna" );
	}


	public void UnornaLetterConversation()
	{
		bUnornaLetterConversation = BeginConversation( "11_1_UnornaLetter" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bUnornaFirstConversation )
		{
			CodexSound.PopMusic();
			bUnornaFirstConversation = EndConversation();

			// award conversation XP
			CodexPlayer.AwardPartyExperience( 25 );
		}

		if( bUnornaVampireConversation ) {
			bUnornaFirstConversation = EndConversation();
		}

		if( bUnornaFirstGoodbyeConversation )
		{
			bUnornaFirstGoodbyeConversation = EndConversation();

			// skip buy/sell
			return;
		}

		if( bQAUnornaAskConversation )
		{
			bQAUnornaAskConversation = false;

			// award conversation XP
			CodexPlayer.AwardPartyExperience( 50 );

			QAUnornaConversation();

			// skip buy/sell
			return;
		}
		else if( bQAUnornaConversation )
		{
			CodexSound.PopMusic();

			if( returnValue == 1 ) {
				bQAUnornaConversation = EndConversation();
			}
			else
			{
				QAUnornaConversation();

				// skip buy/sell
				return;
			}
		}

		if( bUnornaLetterConversation )
		{
			bUnornaLetterConversation = EndConversation();

			// without pulling up buy/sell interface
			return;
		}

		BeginBuySell( false );
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bUnornaFirstConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 2:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 3:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 4:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;
			}
		}

		if( bUnornaFirstGoodbyeConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;
			}
		}

		if( bUnornaVampireConversation )
		{
			CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
			CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
		}

		if( bQAUnornaAskConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 2:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;
			}
		}

		if( bQAUnornaConversation )
		{
			switch( curEvent )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 2:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;

				case 3:
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
							break;

						case 1:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, 0 );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0 );
							break;

						case 2:
							CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
							break;

						case 3:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, unornaGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
							break;

						case 4:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
							break;
					}
					break;

				case 4:
					CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
					break;
			}
		}

		if( bUnornaLetterConversation )
		{
			CodexCamera.SetupCutscene( starterGuid, unornaGUID, 0 );
			CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0 );
		}
	}
}