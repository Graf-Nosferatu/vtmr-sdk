/**
 * Discipline CauldronOfBlood script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineCauldronOfBlood extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "CauldronOfBlood";

	// --------------------------------------------------------------------------------------------

	public DisciplineCauldronOfBlood()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(false);

		if((targetThing.GetActorFlags() & THING_AF_NOBLEED) != 0)
		{
			DisplayNoCast("RPG_DISC_NOBLOOD");
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NOBLOOD", 0xFFFF00);
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

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			// start the real effect
			targetThing.AddActorEffectByLevel("ef_disc_cauldronofblood1", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);
			targetThing.AddActorEffectByLevel("ef_disc_cauldronofblood2", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);

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

