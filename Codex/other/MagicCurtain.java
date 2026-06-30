/**
 * Magic Curtain script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class MagicCurtain extends Codex
{
	public CodexActor	magicCurtain;
	public boolean	bOpen = false;

	public MagicCurtain()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		magicCurtain = new CodexActor(GetClassThing());
	}

	// --------------------------------------------------------------------------------------------

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bOpen)
			return;

		bOpen = true;
		magicCurtain.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		magicCurtain.SetCollideType(THING_COLLIDE_NONE);

	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bOpen);
	}
 
	public void restore(int flags)
	{
		bOpen = CodexSequence.RestoreBoolean();
	}
}


