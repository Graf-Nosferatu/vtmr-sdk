/**
 * Discipline TurnToDust script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineTurnToDust extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "TurnToDust";

	// --------------------------------------------------------------------------------------------

	public DisciplineTurnToDust()
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

			if((Math.random() * (targetThing.GetActorStat(ACTOR_STAT_STAMINA) + targetThing.GetActorStat(ACTOR_STAT_WITS)) / 2) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_INTELLIGENCE)))
			{
				// start the real effect
				targetThing.AddActorEffectByLevel("ef_disc_turntodust", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);
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
