/**
 *  ItemTomeHandsOfDestruction script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeHandsOfDestruction extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Decay";

	public ItemTomeHandsOfDestruction()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Thaumaturgy - Hands of Destruction."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_HANDSDESTRUCT");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Thaumaturgy - Hands of Destruction."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_HANDSDESTRUCT");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
