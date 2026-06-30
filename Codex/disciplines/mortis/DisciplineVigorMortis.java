/**
 * Discipline VigorMortis script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineVigorMortis extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "VigorMortis";

	// --------------------------------------------------------------------------------------------

	public DisciplineVigorMortis()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(false);

		if(IsPlayerGuid(targetGuid))
		{
			DisplayNoCast("RPG_DISC_NOPOSSESSION");
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NOPOSSESSION", 0xFFFF00);
			return(false);
		}

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

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			// first revive the dead actor, and give him some health and blood
			targetThing.ReviveActor(VIGORMORTIS_HEALTHS[level], VIGORMORTIS_BLOODS[level]);
				
			// start the effect
			targetThing.AddActorEffectByLevel("ef_disc_vigormortis", VIGORMORTIS_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

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

