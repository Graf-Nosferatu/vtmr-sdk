/**
 *  ItemTomeBloodPath script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeBloodPath extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "BloodRage";

	public ItemTomeBloodPath()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Thaumaturgy - Blood Path."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_BLOODPATH");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Thaumaturgy - Blood Path."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_BLOODPATH");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
