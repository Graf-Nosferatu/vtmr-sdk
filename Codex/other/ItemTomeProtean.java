/**
 *  ItemTomeProtean script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeProtean extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "EyesOfTheBeast";

	public ItemTomeProtean()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Protean."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_PROTEAN");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Protean."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_PROTEAN");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
