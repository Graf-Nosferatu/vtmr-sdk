/**
 *  ItemTomeCelerity script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeCelerity extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Celerity";

	public ItemTomeCelerity()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Celerity."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_CELERITY");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Celerity."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_CELERITY");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
