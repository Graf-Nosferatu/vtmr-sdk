/**
 * Discipline EyesOfTheSerpent script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineEyesOfTheSerpent extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "EyesOfTheSerpent";

	// --------------------------------------------------------------------------------------------

	public DisciplineEyesOfTheSerpent()
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
			float clanFactor = 1.0f;
			if(targetThing.GetActorClanId() == ACTOR_CLAN_MALKAVIAN)
				clanFactor = 0.50f;

			if( ((targetThing.AIGetMindFlags() & AIMIND_FLAG_NOMESMERIZED) == 0) &&
				((Math.random() * targetThing.GetActorStat(ACTOR_STAT_WITS) * clanFactor) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_WITS))))
			{
				int effectGuid = targetThing.FindActorEffect("ef_disc_eyesoftheserpent");
				if(effectGuid != 0)
				{
					targetThing.ExpandActorEffect(effectGuid, EYESOFTHESERPENT_DURATIONS[level], false);

					// recast text
					DisplayRecast(DISCIPLINE_NAME, level);
				}
				else
				{
					// start the effect
					targetThing.AddActorEffectByLevel("ef_disc_eyesoftheserpent", EYESOFTHESERPENT_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

					// cast text
					DisplayCast(DISCIPLINE_NAME, level);
				}
			}
			else
			{
				DisplayCast(DISCIPLINE_NAME, level);
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

