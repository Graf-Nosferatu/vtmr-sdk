/**
 * Discipline Mask1000Faces script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineMask1000Faces extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Mask1000Faces";

	// --------------------------------------------------------------------------------------------

	public DisciplineMask1000Faces()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(false);

		// Check that the target is valid for shape changing
		if((targetThing.GetActorFlags() & THING_AF_VALIDSHAPE) == 0)
		{
			DisplayNoCast("RPG_DISC_NOMASK1000");
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NOMASK1000", 0xFFFF00);
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

		// pretty special case, if the discipline is running, CANCEL it...
		int effectGuid = casterThing.FindActorEffect("ef_disc_mask1000faces");
		if(effectGuid != 0)
			casterThing.RemoveActorEffect(effectGuid);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			// start the effect... (use the full effect parameters here, we want to keep the target guid in intParam)
			casterThing.AddActorEffectByValue("ef_disc_mask1000faces", MASK1000FACES_DURATIONS[level], targetThing.GetActorOriTeam(), casterGuid, EI_FLAG_DISCIPLINE, targetThing.GetGUID(), 0.0f);

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

