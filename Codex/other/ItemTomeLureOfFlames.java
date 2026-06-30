/**
 *  ItemTomeLureOfFlames script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeLureOfFlames extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "Torch";

	public ItemTomeLureOfFlames()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Thaumaturgy - Lure of Flames."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_LUREOFFLAMES");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Thaumaturgy - Lure of Flames."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_LUREOFFLAMES");


		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
