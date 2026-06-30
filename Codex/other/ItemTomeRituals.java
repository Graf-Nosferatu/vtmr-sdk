/**
 *  ItemTomeRituals script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeRituals extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "HeartOfStone";

	public ItemTomeRituals()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Rituals."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_RITUALS");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Rituals."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_RITUALS");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
