/**
 * Discipline PrisonOfIce script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplinePrisonOfIce extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "PrisonOfIce";

	// --------------------------------------------------------------------------------------------

	public DisciplinePrisonOfIce()
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
			targetThing.Stop();
			targetThing.CancelActorAction();

			// start the damaging effect
			targetThing.AddActorEffectByLevel("ef_disc_prisonofice1", PRISONOFICE_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);
			targetThing.AddActorEffectByLevel("ef_disc_prisonofice2", PRISONOFICE_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

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
