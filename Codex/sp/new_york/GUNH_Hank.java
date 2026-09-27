/**
 * Gun Haven Hank script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class GUNH_Hank extends CodexBuySell
{
	public static String _params[] = { "Sliding door part 1", "Sliding door part 2" };

	private static final int TIMER_ID_FLIPSWITCH	= 0;
	private static final int TIMER_ID_DOOR1DOWN		= 1;
	private static final int TIMER_ID_DOOR2OVER		= 2;
	private static final int TIMER_ID_DOOR2INTOWALL	= 3;
	private static final int TIMER_ID_DOOR2DOWN		= 4;


	private	CodexThing _slideDoor1;
	private CodexThing _slideDoor2;

	private int christofGUID;
	private int hankGUID;

	private boolean bHankConversation = false;


	public GUNH_Hank( CodexThing slideDoor1, CodexThing slideDoor2 )
	{
		_slideDoor1 = new CodexThing( slideDoor1.GetGUID() );
		_slideDoor2 = new CodexThing( slideDoor2.GetGUID() );

		CaptureThing( _slideDoor1.GetGUID() );
		CaptureThing( _slideDoor2.GetGUID() );

		greetingLine = new String[]{ "Hank_0_0_1912.wav", "Hank_0_0_1911.wav" };
		numGreetingLines = 2;
		byeLine = new String[]{ "Hank_0_0_1914_2.wav", "Hank_0_0_1913.wav", "Hank_0_0_1914.wav" };
		numByeLines = 3;

		shopName = "GUNHAVEN";
		shopFileName = "GunHaven.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		christofGUID = CodexThing.GuidFromCastID( "Christof" );
		hankGUID = CodexThing.GuidFromCastID( "Hank" );
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		if( !CodexSequence.GetChronicleFlag( NewYorkChronicle.GUNH_FIRSTHANK ) )
		{
			CodexSequence.SetChronicleFlag( NewYorkChronicle.GUNH_FIRSTHANK );

			HankConversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( true );
	}


	public void timer( int timerID, float arg0, float arg1, float arg2, float arg3 )
	{
		switch( timerID )
		{
			case TIMER_ID_FLIPSWITCH:
				_slideDoor1.MoveToFrame( 1, 50 );
				SetTimer( 2, TIMER_ID_DOOR1DOWN );
				break;

			case TIMER_ID_DOOR1DOWN:
				_slideDoor1.MoveToFrame( 2, 50 );
				SetTimer( 4, TIMER_ID_DOOR2OVER );
				break;

			case TIMER_ID_DOOR2OVER:
				_slideDoor2.MoveToFrame( 1, 50 );
				SetTimer( 2, TIMER_ID_DOOR2INTOWALL );
				break;

			case TIMER_ID_DOOR2INTOWALL:
				_slideDoor2.MoveToFrame( 2, 50 );
				SetTimer( 2, TIMER_ID_DOOR2DOWN );
				break;

			case TIMER_ID_DOOR2DOWN:
				_slideDoor2.MoveToFrame( 3, 50 );
				break;
		}
	}


	public void HankConversation()
	{
		bHankConversation = BeginConversation( "35_1_Hank" );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bHankConversation )
		{
			bHankConversation = EndConversation();

			// add conversation XP
			CodexPlayer.AwardPartyExperience( 25 );
		}

		BeginBuySell( false );
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		switch( curLine )
		{
			case 0:
				CodexCamera.SetupCutscene( starterGuid, christofGUID, hankGUID );
				CodexCamera.SetShot( starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
				break;

			case 1:
				CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0 );
				break;

			case 2:
				CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "HankDoors.ncp", 30 );
				owner.PlayMotionSetMode( MOTION_SPECIAL1, false, 30f );
				SetTimer( 2, TIMER_ID_FLIPSWITCH );
				break;

			case 3:
				owner.StopActorAction();
				break;
		}
	}
}