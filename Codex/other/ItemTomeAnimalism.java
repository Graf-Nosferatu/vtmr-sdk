/**
 *  ItemTomeAnimalism script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemTomeAnimalism extends Codex
{
	private static final String	FIRST_DISCIPLINE	= "FeralWhispers";

	public ItemTomeAnimalism()
	{
	}

	boolean used(int itemGuid, int userGuid, int captureId)
	{
		CodexActor user = new CodexActor(userGuid);

		if(user.GetActorDisciplineLevel(FIRST_DISCIPLINE) > -2)
		{
			// Printout a message here "You have already begun study in Animalism."
			CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_FAIL_ANIMALISM");
			return(false);
		}

		// Printout a message here "You have learned a new Discipline Group - Animalism."
		CodexConsole.PrintNLS(userGuid, 0, "DISCTOME_SUCCESS_ANIMALISM");
		user.SetActorDisciplineLevel(FIRST_DISCIPLINE, -1);
		return(true);
	}
}
