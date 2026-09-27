/**
 * EAST_Otto script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class EAST_Otto extends CodexBuySell
{
	public static String _params[] = { "Otto Van" };


	private CodexActor _ottoVan;

	private boolean bWithAnim = true;

	private boolean bBrothelLocationConversation = false;


	public EAST_Otto( CodexActor ottoVan )
	{
		_ottoVan = new CodexActor( ottoVan.GetGUID() );
		CaptureThing( _ottoVan.GetGUID() );

		greetingLine = new String[]{ "Otto_0_0_1920_2.wav" };
		numGreetingLines = 1;
		byeLine = new String[]{ "Otto_0_0_1921.wav" };
		numByeLines = 1;

		shopName = "OTTOVAN";
		shopFileName = "OttoVan.nbs";
		sellFlags = BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();

		bWithAnim = true;

		owner.ClearThingFlags( THING_FLAG_NOHIGHLIGHT );
		_ottoVan.ClearThingFlags( THING_FLAG_NOHIGHLIGHT );

		// to force the stand on beginscene
		owner.PlayActorMotionSetMode( MOTION_STAND, false, 30f );
		_ottoVan.PlayActorMotionSetMode( MOTION_STAND, false, 30f );
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		if( bWithAnim )
		{
			new CodexSound( "leverLarge_02.wav", 300, 600, 100, 0, 0, _ottoVan.GetGUID() );
			CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "EASTOttoVan.ncp", 30 );
			float duration = owner.PlayActorMotionSetMode( MOTION_SPECIAL1, false, 30f );
			_ottoVan.PlayActorMotionSetMode( MOTION_SPECIAL1, false, 30f);

			SetTimer( duration / 1000f, 1 );
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( true );

		if( bWithAnim )
		{
			bWithAnim = false;
			SetTimer( 0.5f, 2 );
		}
	}


	public void timer( int timerID, float arg0, float arg1, float arg2, float arg3 )
	{
		if( timerID == 1 )
		{
			owner.SetThingFlags( THING_FLAG_NOHIGHLIGHT );
			_ottoVan.SetThingFlags( THING_FLAG_NOHIGHLIGHT );

			BeginBuySell( true/*play the greeting sound*/ );
		}
		else if( timerID == 2 )
		{
			new CodexSound( "leverLarge_02.wav", 300, 600, 100, 0, 0, _ottoVan.GetGUID() );
			float duration = owner.PlayActorMotionSetMode( MOTION_SPECIAL2, false, 30f );
			_ottoVan.PlayActorMotionSetMode( MOTION_SPECIAL2, false, 30f );
			SetTimer( duration / 1000f, 3 );
		}
		else if( timerID == 3 )
		{
			owner.ClearThingFlags( THING_FLAG_NOHIGHLIGHT );
			_ottoVan.ClearThingFlags( THING_FLAG_NOHIGHLIGHT );

			if( CodexSequence.GetChronicleFlag( LondonChronicle.TEN1_MEETPINK ) &&
				!CodexSequence.GetChronicleFlag( LondonChronicle.EAST_BROTHELLOCATION ) &&
				!CodexSequence.GetChronicleFlag( LondonChronicle.BROT_MEETLILY ) )
			{
				CodexSequence.SetChronicleFlag( LondonChronicle.EAST_BROTHELLOCATION );

				BrothelLocationConversation();
			}
		}
	}


	public void BrothelLocationConversation()
	{
		AIOff();
		bBrothelLocationConversation = true;
		ExecuteConversation( CodexPlayer.GetCurrentPlayer(), 0, "28_4_BrothelLocation", "28_4_BrothelLocation.nco", CONV_XFLAG_WANTFEEDBACK );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bBrothelLocationConversation ) {
			bBrothelLocationConversation = EndConversation();
		}
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		CodexCamera.SetupCutscene( starterGuid, speakerGuid, 0 );
		CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0 );
	}
}