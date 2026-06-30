/**
 * Discipline ShapeOfTheBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineShapeOfTheBeast extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "ShapeOfTheBeast";
	
	// --------------------------------------------------------------------------------------------

	public DisciplineShapeOfTheBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

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
			int effectGuid = casterThing.FindActorEffect("ef_disc_shapeofthebeast");
			if(effectGuid != 0)
			{
				casterThing.ExpandActorEffect(effectGuid, SHAPEOFTHEBEAST_DURATIONS[level], false);

				int effectGuid2 = casterThing.FindActorEffect("ef_disc_shapeofthebeast2");
				if(effectGuid2 != 0)
					casterThing.ExpandActorEffect(effectGuid2, SHAPEOFTHEBEAST_DURATIONS[level], false);

				int effectGuid3 = casterThing.FindActorEffect("ef_disc_shapeofthebeast3");
				if(effectGuid3 != 0)
					casterThing.ExpandActorEffect(effectGuid3, SHAPEOFTHEBEAST_DURATIONS[level], false);

				// recast text
				DisplayRecast(DISCIPLINE_NAME, level);
			}
			else
			{	
				// start the real effect
				casterThing.AddActorEffectByLevel("ef_disc_shapeofthebeast", SHAPEOFTHEBEAST_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);
				casterThing.AddActorEffectByLevel("ef_disc_shapeofthebeast2", SHAPEOFTHEBEAST_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);
				casterThing.AddActorEffectByLevel("ef_disc_shapeofthebeast3", SHAPEOFTHEBEAST_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

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

