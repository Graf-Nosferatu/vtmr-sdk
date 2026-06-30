/**
 * Discipline Entrancement script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineEntrancement extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Entrancement";

	// --------------------------------------------------------------------------------------------

	public DisciplineEntrancement()
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
			// cast text
			DisplayCast(DISCIPLINE_NAME, level);

			float clanFactor = 1.0f;
			if(targetThing.GetActorClanId() == ACTOR_CLAN_TOREADOR)
				clanFactor = 0.50f;

			if(targetThing.GetActorClanId() == ACTOR_CLAN_MALKAVIAN)
				clanFactor = 0.50f;

			if( ((targetThing.AIGetMindFlags() & AIMIND_FLAG_NOPOSSESSED) == 0) &&
				((Math.random() * targetThing.GetActorStat(ACTOR_STAT_WITS) * clanFactor) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_APPEARANCE))))
			{
				// if the discipline is already running on the target, refresh it
				int effectGuid = targetThing.FindActorEffect("ef_disc_entrancement");
				if(effectGuid != 0)
					targetThing.RemoveActorEffect(effectGuid);
				
				// start the effect
				targetThing.AddActorEffectByLevel("ef_disc_entrancement", ENTRANCEMENT_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);
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

