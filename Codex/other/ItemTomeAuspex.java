/**
 *  ItemTomeAuspex script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/

public class ItemTomeAuspex extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "HeightenedSenses";

	public ItemTomeAuspex()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Auspex."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_AUSPEX");

			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Auspex."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_AUSPEX");

		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);

		return(true);
	}
}
