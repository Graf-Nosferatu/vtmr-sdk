/**
 * Discipline Walk The Abyss script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineWalkTheAbyss extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "WalkTheAbyss";

	// --------------------------------------------------------------------------------------------

	private CodexSound			sound;

	// --------------------------------------------------------------------------------------------

	public DisciplineWalkTheAbyss()
	{
	}

	// --------------------------------------------------------------------------------------------

	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		// Check that the location has portals enabled
		int locNum = casterThing.GetLocationNum();
		if(locNum != -1)
		{

			String locName = CodexSequence.GetLocationName(locNum);
			if((CodexSequence.GetLocationFlags(locName) & LOCATION_FLAG_NOTELEPORTS) != 0)
			{
				DisplayNoCast("RPG_DISC_NOWALKTHEABYSS");
				//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NOWALKTHEABYSS", 0xFFFF00);
				return(false);
			}
		}
		else
		{
			// yuck... :-(
			return(false);
		}

		return(true);
	}

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
			switch(level)
			{
				case 0: 
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
			}

			// cast text
			DisplayCast(DISCIPLINE_NAME, level);

			//SetTimer(1);
			CodexSequence.CreateTeleporter(casterThing.GetGUID(), "havenPortal", "havenPortal", 0, 0);

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

	// --------------------------------------------------------------------------------------------

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		//CodexSequence.CreateTeleporter(casterThing.GetGUID(), "havenPortal", "havenPortal", 0, 0);		
	}

}

