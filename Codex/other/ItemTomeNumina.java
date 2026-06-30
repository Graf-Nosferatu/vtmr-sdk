/**
 *  ItemTomeNumina script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeNumina extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Invisibility";

	public ItemTomeNumina()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Numina."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_NUMINA");


			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Numina."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_NUMINA");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
