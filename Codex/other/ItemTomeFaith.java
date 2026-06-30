/**
 *  ItemTomeFaith script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeFaith extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Heal";

	public ItemTomeFaith()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Faith."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_FAITH");


			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Faith."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_FAITH");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
