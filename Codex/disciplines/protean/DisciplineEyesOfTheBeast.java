/**
 * Discipline EyesOfTheBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineEyesOfTheBeast extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "EyesOfTheBeast";

	// --------------------------------------------------------------------------------------------

	public DisciplineEyesOfTheBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			int effectGuid = casterThing.FindActorEffect("ef_disc_eyesofthebeast");
			if(effectGuid != 0)
			{
				casterThing.ExpandActorEffect(effectGuid, EYESOFTHEBEAST_DURATIONS[level], false);

				// recast text
				DisplayRecast(DISCIPLINE_NAME, level);
			}
			else
			{
				// placeholder effect
				int gleamGuid = casterThing.SpawnThing(EYESOFTHEBEAST_TEMPLATES[level]);

				float[] offset = new float[3];
				offset[0] = offset[1] = offset[2] = 0.0f;
				casterThing.AttachThing(gleamGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);
				
				// start the real effect
				casterThing.AddActorEffectByLevel("ef_disc_eyesofthebeast", EYESOFTHEBEAST_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE);

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

