/**
 *  ItemTomePresence script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomePresence extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Awe";

	public ItemTomePresence()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Presence."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_PRESENCE");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Presence."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_PRESENCE");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
