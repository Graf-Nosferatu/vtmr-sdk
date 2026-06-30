/**
 * Discipline Nuke script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineNuke extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Nuke";

	private float	duration = 0;

	// --------------------------------------------------------------------------------------------

	public DisciplineNuke()
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

		try
		{	
			// start the effect
			targetThing.AddActorEffectByLevel("ef_disc_nuke", 0, 0, casterGuid, EI_FLAG_DISCIPLINE);

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

