/**
 * Discipline Walk The Abyss script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class DisciplineWalkTheAbyss extends CodexDiscipline
{
	private boolean createTeleport;


	public DisciplineWalkTheAbyss()
	{
		DisciplineName = "WalkTheAbyss";
	}


	public boolean cancast( int level, int casterGuid, int targetGuid, float x, float y, float z, boolean isPos )
	{
		InitializeCaster( casterGuid );

		// Check that the location has portals enabled
		int locationNum = Caster.GetLocationNum();
		if( locationNum != -1 )
		{
			String locationName = CodexSequence.GetLocationName( locationNum );
			int locationFlags = CodexSequence.GetLocationFlags( locationName );
			createTeleport = ((locationFlags & LOCATION_FLAG_NOTELEPORTS) == 0);

			return true;
		}
		else {
			return PrintNoCast( "RPG_DISC_NOWALKTHEABYSS" );
		}
	}


	public int cast( int level, int casterGuid )
	{
		if( createTeleport ) {
			CodexSequence.CreateTeleporter( casterGuid, "havenPortal", "havenPortal", 0, 0 );
		}
		else {
			CodexSequence.Vault( casterGuid );
		}

		return PrintCast();
	}
}