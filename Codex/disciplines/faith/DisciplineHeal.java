/**
 * Discipline Blood Healing script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineHeal extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Heal";

	// --------------------------------------------------------------------------------------------

	public DisciplineHeal()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		// Check that the caster doesn't have full health
		if(casterThing.GetActorHealth() >= casterThing.GetActorMaxHealth())
		{
			DisplayNoCast("RPG_DISC_NOHEAL");
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NOHEAL", 0xFFFF00);
			return(false);
		}

		return(true);
	}


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
			// start the real effect
			casterThing.AddActorEffectByLevel("ef_disc_heal", 0, level, casterGuid, EI_FLAG_DISCIPLINE);

			// bonus at high levels
			if(level == 2)
			{
				casterThing.AddActorEffectByLevel("ef_curepoison", 0, 1, casterGuid, EI_FLAG_DISCIPLINE);
			}
			else if(level == 3)
			{
				casterThing.AddActorEffectByLevel("ef_curepoison", 0, 2, casterGuid, EI_FLAG_DISCIPLINE);
				casterThing.AddActorEffectByLevel("ef_curedisease", 0, 1, casterGuid, EI_FLAG_DISCIPLINE);
			}
			else if(level == 4)
			{
				casterThing.AddActorEffectByLevel("ef_curepoison", 0, 3, casterGuid, EI_FLAG_DISCIPLINE);
				casterThing.AddActorEffectByLevel("ef_curedisease", 0, 2, casterGuid, EI_FLAG_DISCIPLINE);
			}


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

