/**
 * Discipline PlagueWind script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplinePlagueWind extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "PlagueWind";

	private static final int	TIMER_ID_DISCIPLINE = 1;
	private int					projectileGuid = 0;

	// --------------------------------------------------------------------------------------------

	private CodexSound			sound;

	// --------------------------------------------------------------------------------------------

	public DisciplinePlagueWind()
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
			float[] pos = new float[3];

			pos[VEC_X] = x; 
			pos[VEC_Y] = y; 
			pos[VEC_Z] = z;

			float[] fireOffset = new float[3];
			fireOffset[0] = 0; fireOffset[1] = 45; fireOffset[2] = 20;

			// fire the projectile
			projectileGuid = casterThing.FireProjectileAtPos(PLAGUEWIND_TEMPLATES[level], pos, fireOffset);

			//sound = new CodexSound(SOUND, 256.0f, 512.0f, 80, 0, 0, casterThing.GetGUID());

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

