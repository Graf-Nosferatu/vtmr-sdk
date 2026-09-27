/**
 * Club Tenebrae Bartender script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class TEN1_Bartender extends CodexBuySell
{
	public boolean bBartenderMenuConversation = false;


	public TEN1_Bartender()
	{
		greetingLine = new String[]{ "MDBart1_0_0_1915.wav", "MDBart1_0_0_1917" };
		numGreetingLines = 2;

		shopName = "BARTENDER";
		shopFileName = "ClubTen.nbs";
		sellFlags = BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION;
	}


	public void beginscene( int clientGuid, int captureID )
	{
		InitializeOwner();
	}


	public void clicked( int guid, int clickerGuid, int captureID )
	{
		if( !CodexSequence.GetChronicleFlag( LondonChronicle.TEN1_MENUCONVERSATION ) )
		{
			CodexSequence.SetChronicleFlag( LondonChronicle.TEN1_MENUCONVERSATION );

			BartenderMenuConversation();
		}
		else {
			BeginBuySell( true );
		}
	}


	public void buysellended( boolean bAborted )
	{
		EndBuySell( false );
	}


	public void BartenderMenuConversation()
	{
		AIOff();
		bBartenderMenuConversation = true;
		ExecuteConversation( CodexPlayer.GetCurrentPlayer(), 0, "28_1_BartenderMenu", "28_1_BartenderMenu.nco", CONV_XFLAG_WANTFEEDBACK );
	}


	public void convended( int starterGuid, boolean bAborted, int returnValue )
	{
		if( bBartenderMenuConversation ) {
			bBartenderMenuConversation = EndConversation();
		}

		BeginBuySell( false );
	}


	public void convreached( int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid )
	{
		if( bBartenderMenuConversation )
		{
			CodexCamera.SetupCutscene( starterGuid, speakerGuid, 0 );
			CodexCamera.SetShot( starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0 );
		}
	}
}