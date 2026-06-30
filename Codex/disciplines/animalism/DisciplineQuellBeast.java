/**
 * Discipline QuellBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineQuellBeast extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "QuellBeast";

	// --------------------------------------------------------------------------------------------

	public DisciplineQuellBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		return(cast(level, casterGuid, casterGuid));
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
			if(casterThing.GetActorTeam() == targetThing.GetActorTeam())
			{
				targetThing.AddActorEffectByLevel("ef_disc_quellbeast", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);

				DisplayCast(DISCIPLINE_NAME, level);
			}
			else
			{
				float clanFactor = 1.0f;
				if(targetThing.GetActorClanId() == ACTOR_CLAN_MALKAVIAN)
					clanFactor = 0.50f;

				if( ((targetThing.AIGetMindFlags() & AIMIND_FLAG_NODAZED) == 0) &&
				    (Math.random() * targetThing.GetActorStat(ACTOR_STAT_WITS) * clanFactor) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_MANIPULATION))
				  )
				{
					// if the discipline is already running on the target remove and recreate it (could be another caster)
					int effectGuid = targetThing.FindActorEffect("ef_disc_quellbeast2");
					if(effectGuid != 0)
						targetThing.RemoveActorEffect(effectGuid);

					targetThing.AddActorEffectByLevel("ef_disc_quellbeast2", QUELLTHEBEAST_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

					DisplayCast(DISCIPLINE_NAME, level);
				}
				else
				{
					DisplayResist(DISCIPLINE_NAME, level);
				}
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

