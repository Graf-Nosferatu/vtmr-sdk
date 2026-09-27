/**
 * NEWM_Emelia script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class NEWM_Emelia extends CodexBuySell
{
	private int christofGUID;
	private int emeliaGUID;
	private int pinkGUID;

	private boolean bEmelia1Conversation			= false;
	private boolean bMeetEmeliaNoPinkConversation	= false;
	private boolean bEmeliaGoodbyeConversation		= false;
	private boolean bEmelia2Conversation			= false;


	public NEWM_Emelia()
	{
		greetingLine = new String[]{ "Emelia_BlessHello.wav" };
		numGreetingLines = 1;
		byeLine = new String[]{ "Emelia_BlessBye.wav" };
		numByeLines = 1;

		shopName = "NEWMOON";
		shopFileName = "NewMoon.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		emeliaGUID = CodexThing.GuidFromCastID( "Emelia" );
		pinkGUID = CodexThing.GuidFromCastID( "Pink" );
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		// see if this is his first visit
		if( !CodexSequence.GetChronicleFlag( NewYorkChronicle.EMELIA_TALKEDONCE ) )
		{
			CodexSequence.SetChronicleFlag( NewYorkChronicle.EMELIA_TALKEDONCE );
			
			if( !CodexSequence.GetChronicleFlag( NewYorkChronicle.WAR3_BIGAL ) ) {
				Emelia1Conversation();
			}
			else {
				MeetEmeliaNoPinkConversation();
			}
		}
		// see if this is his second visit for follow up lines
		else if( !CodexSequence.GetChronicleFlag( NewYorkChronicle.EMELIA_TALKEDTWICE ) )
		{
			CodexSequence.SetChronicleFlag( NewYorkChronicle.EMELIA_TALKEDTWICE );
			
			Emelia2Conversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended(boolean bAborted)
	{
		EndBuySell( false );

		if( !CodexSequence.GetChronicleFlag( NewYorkChronicle.EMELIA_FIRSTGOODBYE ) )
		{
			CodexSequence.SetChronicleFlag( NewYorkChronicle.EMELIA_FIRSTGOODBYE );
			
			EmeliaGoodbyeConversation();
		}
		else {
			PlayByeSound();
		}
	}


	public void Emelia1Conversation()
	{
		CodexSound.PushMusic( "MD_Conversation_1.mp3", 50 );
		bEmelia1Conversation = BeginConversation( "37_4_MeetEmelia" );
	}


	public void MeetEmeliaNoPinkConversation()
	{
		CodexSound.PushMusic( "MD_Conversation_1.mp3", 50 );
		bMeetEmeliaNoPinkConversation = BeginConversation( "37_4_MeetEmeliaNoPink" );
	}


	public void EmeliaGoodbyeConversation()
	{
		bEmeliaGoodbyeConversation = BeginConversation( "37_4_EmeliaGoodbye" );
	}


	public void Emelia2Conversation()
	{
		bEmelia2Conversation = BeginConversation( "37_4_SecondEmelia" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bEmelia1Conversation )
		{
			CodexSound.PopMusic();
			bEmelia1Conversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience( 50 );
		}
		else if( bMeetEmeliaNoPinkConversation )
		{
			CodexSound.PopMusic();
			bMeetEmeliaNoPinkConversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience( 50 );
		}
		else if( bEmeliaGoodbyeConversation )
		{
			bEmeliaGoodbyeConversation = EndConversation();

			// skip buy sell
			return;
		}
		else if( bEmelia2Conversation )
		{
			bEmelia2Conversation = EndConversation();
		}

		BeginBuySell( false );
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bEmelia1Conversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, emeliaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 2:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
					break;

				case 3:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, emeliaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 4:
					CodexCamera.SetupCutscene( starterGuid, pinkGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 5:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
					break;

				case 8:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, emeliaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;
			}
		}

		if( bMeetEmeliaNoPinkConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, emeliaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 2:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
					break;

				case 5:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, emeliaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;
			}
		}

		if( bEmeliaGoodbyeConversation ) {
			CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
		}

		if( bEmelia2Conversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, emeliaGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;
			}
		}
	}
}