/**
 * ClassSkeletonWraith script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ClassSkeletonWraith extends Codex
{
	public ClassSkeletonWraith()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void awaken(int guid, int causeID, int captureID)
	{
		CodexActor skeletonThing = new CodexActor(guid);
		skeletonThing.SetActorFlags(THING_AF_AIPAUSED);

		CodexWorld	world = new CodexWorld(WORLD_CURRENT);
		CodexVector position = new CodexVector(skeletonThing.GetPosition());
		CodexVector orientation = new CodexVector(skeletonThing.GetPosition());

		int wraithGuid = world.CreateThing("Szlachta", position.AsArray(), orientation.AsArray());
	}	
}
