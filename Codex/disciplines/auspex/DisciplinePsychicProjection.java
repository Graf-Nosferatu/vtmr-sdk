/**
 * Discipline PsychicProjection script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplinePsychicProjection extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "PsychicProjection";

	// --------------------------------------------------------------------------------------------

	public DisciplinePsychicProjection()
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
			int projGuid = casterThing.SpawnThingNear("astralProjection", 0.0f, 0);
			CodexActor target = new CodexActor(projGuid);

			// start the effect
			target.AddActorEffectByLevel("ef_disc_psychicprojection", PSYCHICPROJECTION_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

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

