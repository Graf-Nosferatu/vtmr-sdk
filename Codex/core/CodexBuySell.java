public class CodexBuySell extends Codex
{
	private static boolean buySellState = false;

	private static CodexPlayer[] players;
	private static float[] backupPartyManipulations;

	private static final int flags = 0;
	protected static final float buyRate = 1.25f;
	protected static final float sellRate = 0.6731f;

	protected int ownerGuid;
	protected CodexActor owner;

	protected String[] greetingLine;
	protected int numGreetingLines;
	protected String[] byeLine;
	protected int numByeLines;

	protected String shopName;
	protected String shopFileName;
	protected int sellFlags;


	protected void InitializeOwner()
	{
		if( IsActorGuid( ownerGuid = GetClassThing() ) ) {
			owner = new CodexActor( ownerGuid );
		}
	}


	protected boolean BeginConversation( String conversation )
	{
		AIOff();
		CodexPlayer.TeleportPartyToConversation( CodexPlayer.GetCurrentPlayer(), 0 );
		ExecuteConversation( CodexPlayer.GetCurrentPlayer(), 0, conversation, conversation + ".nco", CONV_XFLAG_WANTFEEDBACK );

		return true;
	}


	protected boolean EndConversation()
	{
		AIOn();
		CodexCamera.Release( CodexPlayer.GetCurrentPlayer() );

		return false;
	}


	protected void BeginBuySell( boolean playGreetingSound )
	{
		if( playGreetingSound ) {
			PlayGreetingSound();
		}

		buySellState = true;

		players = CodexPlayer.GetPartyPlayers();
		backupPartyManipulations = new float[players.length];

		SetupPartyManipulationsByMaximum();

		AddBuySell( shopFileName, ownerGuid, shopName, flags, buyRate, sellRate, sellFlags );
		ExecuteBuySell( ownerGuid, CodexPlayer.GetCurrentPlayer(), BUYSELL_XFLAG_WANTFEEDBACK );
	}


	protected void EndBuySell( boolean playByeSound )
	{
		if( playByeSound ) {
			PlayByeSound();
		}

		RestorePartyManipulations();

		players = null;
		backupPartyManipulations = null;

		buySellState = false;
	}


	protected void PlayGreetingSound()
	{
		new CodexSound( greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid );
	}


	protected void PlayByeSound()
	{
		new CodexSound( byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid );
	}


	public static void SetupPartyManipulationsByMaximum()
	{
		if( !buySellState ) {
			return;
		}

		float maximumManipulation = 0f;

		for( int playerNum = 0; playerNum < players.length; playerNum++ )
		{
			float currentManipulation = players[playerNum].GetActorStat( ACTOR_STAT_MANIPULATION );
			backupPartyManipulations[playerNum] = currentManipulation;

			if( currentManipulation > maximumManipulation ) {
				maximumManipulation = currentManipulation;
			}
		}

		for( int playerNum = 0; playerNum < players.length; playerNum++ ) {
			players[ playerNum ].SetActorStat( ACTOR_STAT_MANIPULATION, maximumManipulation );
		}
	}


	public static void RestorePartyManipulations()
	{
		if( !buySellState ) {
			return;
		}

		for( int playerNum = 0; playerNum < players.length; playerNum++ ) {
			players[playerNum].SetActorStat( ACTOR_STAT_MANIPULATION, backupPartyManipulations[playerNum] );
		}
	}
}