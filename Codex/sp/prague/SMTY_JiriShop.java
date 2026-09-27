/**
 * SMTY_JiriShop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class SMTY_JiriShop extends CodexBuySell
{
	private int christofGUID;
	private int jiriGUID;

	private boolean bSmithyCongratsConversation		= false;
	private boolean bSmithyConversation				= false;
	private boolean bSmithyAfterUnornaConversation	= false;


	public SMTY_JiriShop()
	{
		greetingLine = new String[]{ "Jiri_0_0_1930.wav", "Jiri_0_0_84.wav" };
		numGreetingLines = 2;
		byeLine = new String[]{ "Jiri_0_0_85.wav", "Jiri_0_0_86.wav" };
		numByeLines = 2;

		shopName = "SMITHY";
		shopFileName = "PragueSmithy.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_WEAPON;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		jiriGUID = CodexThing.GuidFromCastID( "Jiri" );
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		// see if this is his frist visit - if for some reason they didn't talk to the
		// smithy and went right to the silvermines, skip the initial conversation, if they went
		// to unorna's before coming to the smithy, play the appropriate version of the first
		// conversation
		if( CodexSequence.GetChronicleFlag( PragueChronicle.UNORD_TALKEDONCE ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.JIRI_TALKEDAFTERUNORNA ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.SIM4_AHZRADEAD ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.JIRI_TALKEDAFTERUNORNA );
			CodexSequence.SetChronicleFlag( PragueChronicle.JIRI_TALKEDONCE );

			SmithyAfterUnornaConversation();
		}
		else if( !CodexSequence.GetChronicleFlag( PragueChronicle.JIRI_TALKEDONCE ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.SIM4_AHZRADEAD ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.JIRI_TALKEDONCE );
			CodexSequence.SetChronicleFlag( PragueChronicle.JIRI_TALKEDAFTERUNORNA );

			SmithyConversation();
		}
		else if( CodexSequence.GetChronicleFlag( PragueChronicle.SIM4_AHZRADEAD ) &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.SMTY_MINECONGRATS ) )
		{
			CodexSequence.SetChronicleFlag( PragueChronicle.SMTY_MINECONGRATS );

			SmithyCongratsConversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( true );
	}


	public void SmithyConversation()
	{
		CodexSound.PushMusic( "DA_Conversation_1.mp3", 50 );
		bSmithyConversation = BeginConversation( "4_1_Smithy" );
	}


	public void SmithyAfterUnornaConversation()
	{
		bSmithyAfterUnornaConversation = BeginConversation( "4_1_SmithyAfterUnorna" );
	}


	public void SmithyCongratsConversation()
	{
		bSmithyCongratsConversation = BeginConversation( "6_1_Smithy" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bSmithyConversation )
		{
			CodexSound.PopMusic();
			bSmithyConversation = EndConversation();
		}

		if( bSmithyAfterUnornaConversation ) {
			bSmithyAfterUnornaConversation = EndConversation();
		}

		if( bSmithyCongratsConversation ) {
			bSmithyCongratsConversation = EndConversation();
		}

		BeginBuySell( false );
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bSmithyConversation )
		{
			switch( curEvent )
			{
				case 0:
					// ev_0
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 2:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 3:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;
					}
					break;

				case 1:
					// ev_snotty
					CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
					break;

				case 2:
					// ev_honest
					switch( curLine )
					{
						case 0:
							CodexPlayer.AwardPartyExperience( 50 );
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 2:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 3:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;
					}
					break;

				case 3:
					// ev_saul
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;
					}
					break;

				case 4:
					// ev_stubborn
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;
					}
					break;

				case 5:
					// ev_apology
					CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID);
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
					break;

				case 6:
					// ev_promise
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
							break;
					}
					break;

				case 7:
					// ev_refuse
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
							break;
					}
					break;

				case 8:
					// ev_golden
					CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );

					// show unorna's on the map
					CodexSequence.SetLocationFlags( "UnornaDay", LOCATION_FLAG_SHOWINMAP );
					break;
			}
		}

		if( bSmithyAfterUnornaConversation )
		{
			switch( curEvent )
			{
				case 0:
					// ev_0
					switch( curLine )
					{
						case 0:
							CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 1:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 2:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;

						case 3:
							CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
							break;
					}
					break;

				case 1:
					// ev_snotty
					CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
					break;

				case 2:
					// ev_honest
					CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
					break;
			}
		}

		if( bSmithyCongratsConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, jiriGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
					break;

				case 1:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0 );
					break;
			}
		}
	}
}