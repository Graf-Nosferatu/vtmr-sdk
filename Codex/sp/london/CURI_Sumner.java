/**
 * CURI_Sumner script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class CURI_Sumner extends CodexBuySell
{
	private int christofGUID;
	private int pinkGUID;
	private int sumnerGUID;

	private boolean bSumner1Conversation		= false;
	private boolean bSumner2Conversation		= false;
	private boolean bMeetSumner2Conversation	= false;
	private boolean bSecondSumner2Conversation	= false;


	public CURI_Sumner()
	{
		greetingLine = new String[]{ "Sumner_Hello.wav" };
		numGreetingLines = 1;
		byeLine = new String[]{ "Sumner_GoodDay.wav" };
		numByeLines = 1;

		shopName = "CURIOSHOP";
		shopFileName = "CurioShop.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		pinkGUID = CodexThing.GuidFromCastID( "Pink" );
		sumnerGUID = CodexThing.GuidFromCastID( "Sumner" );
	}


	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if( CodexSequence.GetChronicleFlag( LondonChronicle.TEN1_MEETPINK ) )
		{
			if( !CodexSequence.GetChronicleFlag( LondonChronicle.CURI_SUMNER_NOPINK ) )
			{
				// see if this is his first visit
				if( !CodexSequence.GetChronicleFlag( LondonChronicle.CURI_SUMNER_TALKEDONCE ) )
				{
					CodexSequence.SetChronicleFlag( LondonChronicle.CURI_SUMNER_TALKEDONCE );

					Sumner1Conversation();

					return;
				}
				// see if this is his second visit for follow up lines
				else if( !CodexSequence.GetChronicleFlag(LondonChronicle.SUMNER_TALKEDTWICE ) &&
					CodexSequence.GetChronicleFlag( LondonChronicle.WEST_AFTERCURIO ) )
				{
					CodexSequence.SetChronicleFlag( LondonChronicle.SUMNER_TALKEDTWICE );

					Sumner2Conversation();

					return;
				}
			}
			else if( !CodexSequence.GetChronicleFlag( LondonChronicle.CURI_SUMNER_ALTERNATEPINK ) )
			{
				CodexSequence.SetChronicleFlag( LondonChronicle.CURI_SUMNER_ALTERNATEPINK );

				// sumner after meeting pink if you've already talked to him once without pink
				SecondSumner2Conversation();

				return;
			}
		}
		else
		{
			if( !CodexSequence.GetChronicleFlag(LondonChronicle.CURI_SUMNER_NOPINK ) )
			{
				CodexSequence.SetChronicleFlag( LondonChronicle.CURI_SUMNER_NOPINK );

				// first sumner conversation occuring before meeting pink
				MeetSumner2Conversation();

				return;
			}
		}

		BeginBuySell( true );
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( false );

		if( !CodexSequence.GetChronicleFlag( LondonChronicle.SUMNER_FIRSTGOODBYE ) )
		{
			CodexSequence.SetChronicleFlag( LondonChronicle.SUMNER_FIRSTGOODBYE );

			// play first bye line
			CodexSound.PlayVoice( sumnerGUID, "Sumner_28_4_1042", 100 );
		}
		else {
			PlayByeSound();
		}
	}


	public void Sumner1Conversation()
	{
		CodexSound.PushMusic( "MD_Conversation_1.mp3", 50 );
		bSumner1Conversation = BeginConversation( "28_4_MeetSumner" );
	}


	public void Sumner2Conversation()
	{
		bSumner2Conversation = BeginConversation( "28_4_SecondSumner" );
	}


	public void MeetSumner2Conversation()
	{
		bMeetSumner2Conversation = BeginConversation( "28_4_MeetSumner2" );
	}


	public void SecondSumner2Conversation()
	{
		CodexSound.PushMusic( "MD_Conversation_1.mp3", 50 );
		bSecondSumner2Conversation = BeginConversation( "28_4_SecondSumner2" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bSumner1Conversation )
		{
			CodexSound.PopMusic();
			bSumner1Conversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience( 50 );
		}

		if( bSumner2Conversation ) {
			bSumner2Conversation = EndConversation();
		}

		if( bMeetSumner2Conversation )
		{
			bMeetSumner2Conversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience(100);
		}

		if( bSecondSumner2Conversation )
		{
			CodexSound.PopMusic();
			bSecondSumner2Conversation = EndConversation();
		}

		BeginBuySell( false );
	}

	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bSumner1Conversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 1:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 2:
					CodexCamera.SetupCutscene( starterGuid, pinkGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 3:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 4:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 5:
					CodexCamera.SetupCutscene( starterGuid, pinkGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 6:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 7:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 8:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 9:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 10:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 11:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;
			}
		}

		if( bSumner2Conversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 1:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;
			}
		}

		if( bMeetSumner2Conversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 1:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 2:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 3:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;
			}
		}

		if( bSecondSumner2Conversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, pinkGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, sumnerGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 2:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 3:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0 );
					break;

				case 4:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 5:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 6:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;

				case 7:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 8:
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0 );
					break;
			}
		}
	}
}