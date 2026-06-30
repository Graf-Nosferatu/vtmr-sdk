/**
 * Discipline SpiritsTouch script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineSpiritsTouch extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "SpiritsTouch";

	// --------------------------------------------------------------------------------------------

	public DisciplineSpiritsTouch()
	{
	}

	// --------------------------------------------------------------------------------------------


	public boolean cancast(int level, int casterGuid, int targetGuid, float x, float y, float z, boolean bIsPos)
	{
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(false);

		// Check that the target is an item
		if(!IsItemGuid(targetGuid))
			return(false);

		// need a special target here... a CodexItem!
		CodexItem target = new CodexItem(targetGuid);

		if((target.GetItemFlags() & ITEM_FLAG_IDENTIFIED) != 0)
		{
			DisplayNoCast("RPG_DISC_NOSPIRITSTOUCH");
			//CodexConsole.PrintNLS(casterGuid, 0, "RPG_DISC_NOSPIRITSTOUCH", 0xFFFF00);
			return(false);
		}

		return(true);
	}

	public int cast(int level, int casterGuid, int targetGuid)
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
			CodexItem target = new CodexItem(targetGuid);
			target.SetItemFlags(ITEM_FLAG_IDENTIFIED);

			casterThing.AddActorEffectByLevel("ef_disc_spiritstouch", 1, 0, casterGuid, EI_FLAG_DISCIPLINE);

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

