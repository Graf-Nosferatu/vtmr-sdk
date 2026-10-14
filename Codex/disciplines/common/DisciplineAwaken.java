/**
 * Discipline Awaken script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class DisciplineAwaken extends CodexDiscipline
{
	private static final float[] AWAKEN_HEALTHS	= { 0.15f, 0.30f, 0.45f, 0.60f, 0.75f };
	private static final float[] AWAKEN_BLOODS	= { 0.15f, 0.15f, 0.20f, 0.20f, 0.25f };


	public DisciplineAwaken()
	{
		DisciplineName = "Awaken";
	}


	public boolean cancast( int level, int casterGuid, int targetGuid, float x, float y, float z, boolean isPos )
	{
		InitializeCaster( casterGuid );
		InitializeTargetActor( targetGuid );

		if( (TargetActor.GetActorFlags() & THING_AF_DEAD) == 0 ) {
			return false;
		}

		return true;
	}

	public int cast( int level, int casterGuid, int targetGuid )
	{
		TargetActor.ReviveActor( AWAKEN_HEALTHS[level], AWAKEN_BLOODS[level] );

		return PrintCast();
	}
}