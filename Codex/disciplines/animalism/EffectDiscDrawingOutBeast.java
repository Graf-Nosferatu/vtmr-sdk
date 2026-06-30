/**
 * Effect DiscDrawingOutBeast script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author JS
*/


public class EffectDiscDrawingOutBeast extends Codex
{
	public EffectDiscDrawingOutBeast()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Target	= new CodexActor(actorGuid);

		// initial effect
		_Target.SpawnThing("redMagic");
		_Target.SetShell("redCloudShell", 0x5000, 0, 1, 1, 1);

	}
}