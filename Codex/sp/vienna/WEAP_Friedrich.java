/**
 * Weapon's Smith 20.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class WEAP_Friedrich extends CodexBuySell
{
	public static String _params[] = { "Wheel" };


	private CodexActor _wheel;

	private int christofGUID;
	private int erikGUID;
	private int friedrichGUID;
	private int serenaGUID;

	private boolean bFriedrichFirstConversation = false;


	public WEAP_Friedrich( CodexActor wheel )
	{
		_wheel = new CodexActor( wheel.GetGUID() );

		greetingLine = new String[]{ "Friedrich_0_0_1902.wav" };
		numGreetingLines = 1;
		byeLine = new String[]{ "Friedrich_0_0_1903.wav", "Friedrich_0_0_1924.wav" };
		numByeLines = 2;

		shopName = "WEAPONSMITHSHOP";
		shopFileName = "WeaponSmith.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		erikGUID = CodexThing.GuidFromCastID( "Erik" );
		friedrichGUID = CodexThing.GuidFromCastID( "Friedrich" );
		serenaGUID = CodexThing.GuidFromCastID( "Serena" );
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		// see if this is his first visit
		if( !CodexSequence.GetChronicleFlag( ViennaChronicle.WEAP_TALKEDONCE ) )
		{
			CodexSequence.SetChronicleFlag( ViennaChronicle.WEAP_TALKEDONCE );

			FriedrichFirstConversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( true );
	}


	public void FriedrichFirstConversation()
	{
		// play the "slow down" anim on the wheel
		_wheel.PlayActorMotionSetMode( MOTION_SPECIAL1, false, 30f );

		CodexSound.PushMusic( "DA_Conversation_1.mp3", 50 );
		bFriedrichFirstConversation = BeginConversation( "20_1_Friedrich" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bFriedrichFirstConversation )
		{
			// play the "start the wheel" anim on the wheel
			_wheel.PlayActorMotionSetMode( MOTION_STAND, false, 30f );

			CodexSound.PopMusic();
			bFriedrichFirstConversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience( 50 );
		}

		BeginBuySell( false );
	}

	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bFriedrichFirstConversation )
		{
			switch( curLine )
			{
				case 0:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, friedrichGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 1:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 2:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, friedrichGUID );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0 );
					break;

				case 3:
					CodexCamera.SetupCutscene( starterGuid, erikGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 4:
					CodexCamera.SetupCutscene( starterGuid, christofGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 5:
					CodexCamera.SetupCutscene( starterGuid, serenaGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;

				case 6:
					CodexCamera.SetupCutscene( starterGuid, friedrichGUID, 0 );
					CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
					break;
			}
		}
	}
}