/**
 * Discipline DreadGaze script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineDreadGaze extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "DreadGaze";

	// --------------------------------------------------------------------------------------------

	public DisciplineDreadGaze()
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
			if(targetThing.GetActorClanId() == ACTOR_CLAN_TOREADOR)
				clanFactor = 0.50f;

			if(targetThing.GetActorClanId() == ACTOR_CLAN_MALKAVIAN)
				clanFactor = 0.50f;

			if( ((targetThing.AIGetMindFlags() & AIMIND_FLAG_NOAFRAID) == 0) &&
				((Math.random() * targetThing.GetActorStat(ACTOR_STAT_WITS) * clanFactor) < (Math.random() * casterThing.GetActorStat(ACTOR_STAT_CHARISMA))))
			{
				// if the discipline is already running on the target, refresh it
				int effectGuid = targetThing.FindActorEffect("ef_disc_dreadgaze");
				if(effectGuid != 0)
					targetThing.RemoveActorEffect(effectGuid);
				
				// start the effect
				targetThing.AddActorEffectByLevel("ef_disc_dreadgaze", DREADGAZE_DURATIONS[level], level-1, casterGuid, EI_FLAG_DISCIPLINE);
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

