/**
 * Discipline TheForgetfulMind script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineTheForgetfulMind extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "TheForgetfulMind";

	// --------------------------------------------------------------------------------------------

	public DisciplineTheForgetfulMind()
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
			// cast text
			DisplayCast(DISCIPLINE_NAME, level);

			float clanFactor = 1.0f;
			if(targetThing.GetActorClanId() == ACTOR_CLAN_TREMERE)
				clanFactor = 0.50f;

			if(targetThing.GetActorClanId() == ACTOR_CLAN_MALKAVIAN)
				clanFactor = 0.50f;

			if(	((targetThing.AIGetMindFlags() & AIMIND_FLAG_NODAZED) == 0) &&
				(targetThing.GetActorStat(ACTOR_STAT_GENERATION) >= casterThing.GetActorStat(ACTOR_STAT_GENERATION)) &&
				(Math.random() * targetThing.GetActorStat(ACTOR_STAT_WITS) * clanFactor) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_WITS))
				)
			{
				// if the discipline is already running on the target, refresh it
				int effectGuid = targetThing.FindActorEffect("ef_disc_theforgetfulmind");
				if(effectGuid != 0)
					targetThing.RemoveActorEffect(effectGuid);
				
				// start the effect
				targetThing.AddActorEffectByLevel("ef_disc_theforgetfulmind", THEFORGETFULMIND_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);
			}
			else
			{
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

