/**
 * Discipline TrueFaith script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineTrueFaith extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "TrueFaith";

	// --------------------------------------------------------------------------------------------

	public DisciplineTrueFaith()
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

		// if the discipline is already running, refresh it
		int effectGuid = casterThing.FindActorEffect("ef_disc_truefaith");
		if(effectGuid != 0)
			casterThing.RemoveActorEffect(effectGuid);

		try
		{
			// placeholder effect
			int faithGuid = casterThing.SpawnThing(TRUEFAITH_TEMPLATES[level]);

			float[] offset = new float[3];
			offset[0] = offset[1] = offset[2] = 0.0f;
			casterThing.AttachThing(faithGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

			// start the effect, passing the guid of the explosion
			casterThing.AddActorEffectByLevel("ef_disc_truefaith", TRUEFAITH_DURATIONS[level], level, casterGuid, EI_FLAG_DISCIPLINE, faithGuid, 0.0f);

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

