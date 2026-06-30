/**
 * Discipline FireStorm script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineFireStorm extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "FireStorm";

	private static final int	TIMER_ID_DISCIPLINE = 1;
	private int					projectileGuid = 0;

	// --------------------------------------------------------------------------------------------

	private CodexSound			sound;

	// --------------------------------------------------------------------------------------------

	public DisciplineFireStorm()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid, int targetGuid)
	{
		// do sanity checks
		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(0);

		CodexVector targetPos = new CodexVector(targetThing.GetPosition());
		return(cast(level, casterGuid, targetPos.GetX(), targetPos.GetY(), targetPos.GetZ()));
	}


	public int cast(int level, int casterGuid, float x, float y, float z)
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
			// create an explosion
			CodexThing storm = new CodexThing(casterThing.SpawnThing("firestorm"));
			CodexThing explosion = new CodexThing(casterThing.SpawnThing("exp_firestorm"));

			// place it on the target
			float [] pos = new float[3];

			pos[VEC_X] = x;
			pos[VEC_Y] = y;
			pos[VEC_Z] = z + 300;

			storm.SetPosition(pos);

			pos[VEC_Z] = z + 20;

			explosion.SetPosition(pos);

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

