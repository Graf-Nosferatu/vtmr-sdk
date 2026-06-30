/**
 * Discipline Prayer script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplinePrayer extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Prayer";

	private int			 damage;

	// --------------------------------------------------------------------------------------------

	public DisciplinePrayer()
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

		SetupWorld(DISCIPLINE_NAME);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			// if the discipline is already running on the target, refresh it
			int effectGuid = targetThing.FindActorEffect("ef_disc_prayer");
			if(effectGuid != 0)
				targetThing.RemoveActorEffect(effectGuid);
			
			// start the effect
			targetThing.AddActorEffectByLevel("ef_disc_prayer", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);

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

