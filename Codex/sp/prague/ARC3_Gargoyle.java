/**
 * Ardan Chantry 3 gargoyle script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC3_Gargoyle extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing	_ghost;
	private CodexActor	_gargoyle;
	private int			_gargoyleGuid;
		
	public static String _params[] = {"Gargoyle ghost"};

	public ARC3_Gargoyle(CodexThing ghost)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_ghost = new CodexThing(ghost.GetGUID());
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.ARC3_GARGOYLE))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC3_GARGOYLE);

			// spawn the gargoyle
			_gargoyle = new CodexActor(_ghost.SpawnThing("gargoyle2"));

			_gargoyleGuid = _gargoyle.GetGUID();

			CaptureThing(_gargoyle.GetGUID());

			_gargoyle.AISetTarget(causeGUID);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _gargoyleGuid)
			CodexSequence.SetChronicleFlag(chronScript.ARC3_GARGOYLEDEAD);
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(_gargoyleGuid);
	}

	public void restore(int flags)
	{
		_gargoyleGuid = CodexSequence.RestoreInt();
	}
}

