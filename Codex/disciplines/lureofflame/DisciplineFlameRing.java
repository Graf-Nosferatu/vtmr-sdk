/**
 * Discipline FlameRing script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineFlameRing extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "FlameRing";

	// --------------------------------------------------------------------------------------------

	public DisciplineFlameRing()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			casterThing.SpawnThing("exp_flamering");

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


}

