/**
 * Discipline ShamblingHordes script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineShamblingHordes extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "ShamblingHordes";

	// --------------------------------------------------------------------------------------------

	public DisciplineShamblingHordes()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(casterThing.AIGetNumPets() >= AI_MAXPETS)
		{
			DisplayNoCast("RPG_DISC_TOOMANYPETS");
			return(false);
		}

		if(IsPlayerGuid(targetGuid))
		{
			DisplayNoCast("RPG_DISC_NOPOSSESSION");
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

		if(!targetThing.IsActor())
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
			// first revive the dead actor, and give him some health and blood
			targetThing.ReviveActor(SHAMBLINGHORDES_HEALTHS[level], SHAMBLINGHORDES_BLOODS[level]);

			// now, make it a pet of the caster of the discipline
			targetThing.AddActorEffectByLevel("ef_disc_shamblinghordes", SHAMBLINGHORDES_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

			// assign the caster's team to the pet
			targetThing.SetActorTeam(casterThing.GetActorTeam());

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

