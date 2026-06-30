/**
 * FROG_Bartender script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FROG_Bartender extends Codex
{
	private CodexActor		_barKeep;
	private boolean			_bActive = false;

	public FROG_Bartender()
	{
		_barKeep = new CodexActor(GetClassThing());
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(_bActive)
			return;

		_bActive = true;

		// set inactive in 6 seconds (about the length of the line)
		SetTimer(6);

		// say barkeep's line
		CodexSound.PlayVoice(_barKeep.GetGUID(), "DABart2_20_3_706", 75);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_bActive = false;
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		_bActive = false;
	}
}