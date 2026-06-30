/**
 * Discipline Feed script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineFeed extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Feed";

	// --------------------------------------------------------------------------------------------

	public DisciplineFeed()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(false);

		// Check that the target can be fed on
		if((targetThing.GetActorFlags() & THING_AF_NOFEED) != 0)
			return(false);

		// ghouls can only wristfeed from vampires in their team
		if((casterThing.GetActorType() == ACTOR_TYPE_GHOUL) && ((targetThing.GetActorType() != ACTOR_TYPE_VAMPIRE) || (casterThing.GetActorTeam() != targetThing.GetActorTeam())))
			return(false);

		return(true);
	}

	public int cast(int level, int casterGuid, int targetGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(0);

		SetupWorld(DISCIPLINE_NAME);

		try
		{
			casterThing.Feed(targetGuid);
			return(1);
		}
		catch(Exception e)
		{
			CodexConsole.PrintException(e.getMessage() + " in " + DISCIPLINE_NAME + " [cast]");
			return(0);
		}
		catch(Error e)
		{
			CodexConsole.PrintError(e.getMessage() + " in " + DISCIPLINE_NAME + " [cast]");
			return(0);
		}
	}

}

