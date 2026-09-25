/**
 * Old Town 7.4 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class OLDT_7_4 extends Codex
{
	public static String _params[] = { "Embrace region" };

	private CodexRegion embraceRegion;
	private CodexPlayer christof;


	public OLDT_7_4( CodexRegion EmbraceRegion )
	{
		embraceRegion = new CodexRegion( EmbraceRegion.GetGUID() );
		CaptureThing( embraceRegion.GetGUID() );
	}


	public void beginscene( int clientGuid, int captureID )
	{
		christof = new CodexPlayer( CodexThing.GuidFromCastID( "christof" ) );

		if( !CodexSequence.GetChronicleFlag( PragueChronicle.OLDT_ECATERINAREGION ) )
		{
			// close exit to convent
			CodexSequence.CloseExit( "OldTown", 4 );

			// Fade in
			CodexCamera.AddFade( CodexPlayer.GetCurrentPlayer(), 0f, 255f, 1f, false );

			// play path tracking christof running into ecaterina's region
			CodexCamera.PlayPath( CodexPlayer.GetCurrentPlayer(), GetGUID(), "Embrace.ncp", 150f );

			SetTimer( 1f );
		}
	}


	public void timer( int timerID, float arg0, float arg1, float arg2, float arg3 )
	{
		// send christof to ecaterina
		christof.SendActorToPos( embraceRegion.GetPosition(), 220f );
	}


	public void entered( int guid, int causeGUID, int captureID )
	{
		if( IsPlayerGuid( causeGUID ) &&
			guid == embraceRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag( PragueChronicle.OLDT_ECATERINAREGION ) )
		{
			// ALERT!! THIS CHRON FLAG IS CHECKED IN UNORNA SCRIPT TO SEE WHICH CONVERSATION TO PLAY
			CodexSequence.SetChronicleFlag( PragueChronicle.OLDT_ECATERINAREGION );

			// Fade out
			CodexCamera.AddFade( CodexPlayer.GetCurrentPlayer(), 255f, 0f, 2f, false );

			// remove quests on log prior to being embraced
			new CodexQuest( CodexQuest.Load( "P1_PragueByNight" ) ).Destroy();
			new CodexQuest( CodexQuest.Load( "P1_SilverMines" ) ).Destroy();
			new CodexQuest( CodexQuest.Load( "P1_Geza" ) ).Destroy();
			new CodexQuest( CodexQuest.Load( "P1_AnezkaVisit" ) ).Destroy();

			// make him a vampire
			christof.SetActorType( ACTOR_TYPE_VAMPIRE );
			christof.RemoveActorEffect( "ef_increasemanaot" );
			christof.RemoveActorEffect( "ef_increasebloodot" );
			christof.AddActorEffectByLevel( "ef_decreasefrenzy", 0, 0, 0, 0 );

			// make christof use the vampire model
			christof.SetModel( "christof.nod" );
			christof.SetPlayerHeadModel( "christofH.nod" );
			christof.SetFoley( "playerChristof.nag" );

			// give him his starting disciplines
			christof.SetActorDisciplineLevel( "Feed", 0 );
			christof.SetActorDisciplineSlot( "Feed", 0 );
			christof.SetActorDisciplineLevel( "BloodHealing", 0 );
			christof.SetActorDisciplineSlot( "BloodHealing", 1 );
			christof.SetActorDisciplineLevel( "BloodStrength", 0 );
			christof.SetActorDisciplineSlot( "BloodStrength", 2 );
			christof.SetActorDisciplineLevel( "Awe", 0 );
			christof.SetActorDisciplineSlot( "Awe", 3 );
			christof.SetActorDisciplineLevel( "Potence", 0 );
			christof.SetActorDisciplineSlot( "Potence", 4 );
			christof.SetActorDisciplineLevel( "Celerity", 0 );
			christof.SetActorDisciplineSlot( "Celerity", 5 );

			// roughly convert any XP they've spent on faith back to XP they can use
			christof.AwardPlayerExperience( ((int)christof.GetActorStat( ACTOR_STAT_FAITH ) - 50) * 275 );

			// set blood pool size and blood level
			christof.SetActorBaseStat( ACTOR_STAT_BLOOD, 45f );
			christof.SetActorStat( ACTOR_STAT_BLOOD, 45f );

			// close exit to st thomas
			CodexSequence.CloseExit( "OldTown", 5 );

			// change scene in haven to set up for awakening
			CodexSequence.ChangeScene( "Haven", "HAVN_7_4.nsd" );

			// set this so we get the proper conversation with unorna if the
			// player hasn't been to visit her yet
			CodexSequence.SetChronicleFlag( PragueChronicle.UNORD_TALKEDONCE );

			PlayVideo( "embrace.bik" );
		}
	}


	public void videoended( int id )
	{
		// jump to the haven for the recovery scene
		CodexSequence.Jump( "Haven", 1 );
	}
}