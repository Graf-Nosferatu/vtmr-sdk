/**
 * Discipline Celerity script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineCelerity extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Celerity";

	// --------------------------------------------------------------------------------------------

	public DisciplineCelerity()
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
			int effectGuid = casterThing.FindActorEffect(CodexEffect.EF_DISC_CELERITY1);
			if(effectGuid != 0)
			{
				casterThing.ExpandActorEffect(effectGuid, CELERITY_DURATIONS[level], false);

				int effectGuid2 = casterThing.FindActorEffect(CodexEffect.EF_DISC_CELERITY2);
				if(effectGuid2 != 0)
					casterThing.ExpandActorEffect(effectGuid2, CELERITY_DURATIONS[level], false);

				// recast text
				DisplayRecast(DISCIPLINE_NAME, level);
			}
			else
			{
				casterThing.AddActorEffectByLevel(CodexEffect.EF_DISC_CELERITY1, CELERITY_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);
				casterThing.AddActorEffectByLevel(CodexEffect.EF_DISC_CELERITY2, CELERITY_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

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

