/**
 * ClassCappadocian script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ClassCappadocian extends Codex
{
	public ClassCappadocian()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void awaken(int guid, int causeID, int captureID)
	{
		CodexActor cappadocianThing = new CodexActor(guid);
		cappadocianThing.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
		cappadocianThing.PlayActorMotionSetMode(MOTION_AWAKEN, false, (float)30.0);
	}

}
