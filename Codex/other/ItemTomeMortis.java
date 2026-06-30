/**
 *  ItemTomeMortis script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeMortis extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "ShamblingHordes";

	public ItemTomeMortis()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Mortis."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_MORTIS");


			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Mortis."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_MORTIS");


		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
