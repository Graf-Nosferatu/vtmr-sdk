/**
 * Lawmen script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class Lawmen extends Codex
{
	CodexActor	lawmen;

	public Lawmen()
	{
		lawmen = new CodexActor(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if((lawmen.GetActorFlags() & THING_AF_DEAD) != 0)
		{
			lawmen.SetCollideType(THING_COLLIDE_CYL);

			lawmen.ReviveActor(100, 100);
		}
		else
		{
			// add random blood to the lawmen
			lawmen.AddActorEffectByValue("ef_increaseblood", 0, (float)(Math.random() * 33.0f), lawmen.GetGUID(), 0, 0, 0.0f);

			// heal the lawmen a random amount
			lawmen.HealActor((float)(Math.random() * 50.0f));
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		lawmen.SetCollideType(THING_COLLIDE_NONE);
	}
}
