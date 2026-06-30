/**
 * Pedestrian script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class Pedestrian extends Codex
{
	CodexActor	pedestrian;

	public Pedestrian()
	{
		pedestrian = new CodexActor(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if((pedestrian.GetActorFlags() & THING_AF_DEAD) != 0)
		{
			pedestrian.SetCollideType(THING_COLLIDE_CYL);

			pedestrian.ReviveActor(100, 100);
		}
		else
		{
			// add random blood to the pedestrian
			pedestrian.AddActorEffectByValue("ef_increaseblood", 0, (float)(Math.random() * 33.0f), pedestrian.GetGUID(), 0, 0, 0.0f);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		pedestrian.SetCollideType(THING_COLLIDE_NONE);
	}
}
