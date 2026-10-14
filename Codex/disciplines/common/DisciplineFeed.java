/**
 * Discipline Feed script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class DisciplineFeed extends CodexDiscipline
{
	public DisciplineFeed()
	{
		DisciplineName = "Feed";
	}


	public boolean cancast( int level, int casterGuid, int targetGuid, float x, float y, float z, boolean isPos )
	{
		InitializeCaster( casterGuid );
		InitializeTargetActor( targetGuid );

		// Check that the target can be fed on
		if( (TargetActor.GetActorFlags() & THING_AF_NOFEED) != 0 ) {
			return false;
		}

		// ghouls can only wristfeed from vampires in their team
		if( Caster.GetActorType() == ACTOR_TYPE_GHOUL && (TargetActor.GetActorType() != ACTOR_TYPE_VAMPIRE || Caster.GetActorTeam() != TargetActor.GetActorTeam()) ) {
			return false;
		}

		return true;
	}


	public int cast( int level, int casterGuid, int targetGuid )
	{
		Caster.Feed( targetGuid );

		return 1;
	}
}