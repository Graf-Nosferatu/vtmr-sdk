/**
 * Discipline DrawingOutBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineDrawingOutBeast extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "DrawingOutBeast";

	// --------------------------------------------------------------------------------------------

	public DisciplineDrawingOutBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(false);

		// Check that the target is not human or monster
		if((targetThing.GetActorType() == ACTOR_TYPE_HUMAN) || (targetThing.GetActorType() == ACTOR_TYPE_MONSTER))
		{
			DisplayNoCast("RPG_DISC_NODRAWINGOUTTHEBEAST");
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NODRAWINGOUTTHEBEAST", 0xFFFF00);
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

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);

			// add a backfire effect here, caster gets some frenzy
			casterThing.AddActorEffectByLevel("ef_disc_drawingoutbeast", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);

			return(0);
		}

		try
		{
			// cast text
			DisplayCast(DISCIPLINE_NAME, level);
			
			if((targetThing.GetActorType() == ACTOR_TYPE_VAMPIRE) || (targetThing.GetActorType() == ACTOR_TYPE_GHOUL))
			{
				// start the real effect
				casterThing.AddActorEffectByLevel("ef_disc_quellbeast", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);
				targetThing.AddActorEffectByLevel("ef_disc_drawingoutbeast", 2000, level, casterGuid, EI_FLAG_DISCIPLINE);
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

