/**
 *  ItemTomeDominate script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeDominate extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Command";

	public ItemTomeDominate()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Dominate."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_DOMINATE");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Dominate."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_DOMINATE");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
