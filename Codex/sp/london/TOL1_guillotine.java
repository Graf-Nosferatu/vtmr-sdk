/**
 *  TOL1_guillotine script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class TOL1_guillotine extends Codex
{
	private CodexThing		_guillotine;
	private float			_fallSpeed;
	private String			_soundName;
	private boolean			bFallen = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {	"Guillotine object", "Falling speed;250.0" /*, "Sound name" */ };

	public TOL1_guillotine(CodexThing guillotine, float fallSpeed /*, String soundName */)
	{
		_guillotine = new CodexThing(guillotine.GetGUID());
		_fallSpeed  = fallSpeed;
		// _soundName	= soundName;
		_soundName = "crate_smash.wav";
	}

	// --------------------------------------------------------------------------------------------

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!bFallen)
		{
			CodexThing enterThing = new CodexThing(causeGUID);
			if(enterThing.IsPlayer())
			{
				_guillotine.MoveToFrame(1, _fallSpeed);
				CodexSound snd = new CodexSound(_soundName, (float)128.0, (float)512.0, 75, 0, 0, _guillotine.GetGUID());
				bFallen = true;
			}
		}
	}

}
