/**
 * Discipline IgnoreFlame script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineIgnoreFlame extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "IgnoreFlame";

	// --------------------------------------------------------------------------------------------

	public DisciplineIgnoreFlame()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
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
			int effectGuid = casterThing.FindActorEffect("ef_disc_ignoreflame");
			if(effectGuid != 0)
			{
				casterThing.ExpandActorEffect(effectGuid, IGNOREFLAME_DURATIONS[level], false);

				// recast text
				DisplayRecast(DISCIPLINE_NAME, level);
			}
			else
			{
				// start the real effect
				casterThing.AddActorEffectByLevel("ef_disc_ignoreflame", IGNOREFLAME_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

				// cast text
				DisplayCast(DISCIPLINE_NAME, level);
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

