/**
 * Discipline Passion script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplinePassion extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Passion";

	// --------------------------------------------------------------------------------------------

	public DisciplinePassion()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid, int targetGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(0);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			// cast text
			DisplayCast(DISCIPLINE_NAME, level);

			if((Math.random() * targetThing.GetActorStat(ACTOR_STAT_HUMANITY)) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_CHARISMA)))
			{
				// start the real effect
				targetThing.AddActorEffectByLevel("ef_disc_passion", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);
				targetThing.AddActorEffectByLevel("ef_disc_passion2", 5000, level, casterGuid, EI_FLAG_DISCIPLINE);
			}
			else
			{
				DisplayResist(DISCIPLINE_NAME, level);
			}

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

