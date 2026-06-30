/**
 * Discipline Awaken script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineAwaken extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Awaken";

	// --------------------------------------------------------------------------------------------

	public DisciplineAwaken()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(false);

		if((targetThing.GetActorFlags() & THING_AF_DEAD) == 0)
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

		if(!targetThing.IsActor())
			return(0);

		SetupWorld(DISCIPLINE_NAME);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			targetThing.ReviveActor(AWAKEN_HEALTHS[level], AWAKEN_BLOODS[level]);

			// cast text
			DisplayCast(DISCIPLINE_NAME, level);

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

	// --------------------------------------------------------------------------------------------



}

