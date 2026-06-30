/**
 * Discipline Beckoning script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineBeckoning extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Beckoning";

	// --------------------------------------------------------------------------------------------

	public DisciplineBeckoning()
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
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_TOOMANYPETS", 0xFFFF00);
			return(false);
		}

		return(true);
	}

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
			// get the template to create for beckoning...
			String beckonTemplate = casterThing.GetActorClanBeckoningTemplate();

			//int petGuid = casterThing.SpawnThing(beckonTemplate);
			int petGuid = casterThing.SpawnThingNear(beckonTemplate, 0, 0);
			if(petGuid != 0)
			{
				CodexActor petActor = new CodexActor(petGuid);
				petActor.SetActorFlags(THING_AF_SUMMONED);
				
				// start the effect
				petActor.AddActorEffectByLevel("ef_disc_beckoning", BECKONING_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);	

				// assign the caster's team to the pet
				petActor.SetActorTeam(casterThing.GetActorTeam());

				// cast text
				DisplayCast(DISCIPLINE_NAME, level);
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

