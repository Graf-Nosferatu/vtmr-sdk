/**
 *  Stephansdom1, Serena Burn script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STE1_SerenaBurn extends Codex
{
	private ViennaChronicle	chronScript;

	private CodexPlayer		_Serena;
	private CodexThing		_targetSpot;

	private float[]				pos;

	public static String _params[] =  {"Target spot"};

	public STE1_SerenaBurn(CodexThing targetSpot)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_targetSpot = new CodexThing(targetSpot.GetGUID());

		CaptureThing(_targetSpot.GetGUID());

		pos = new float[3];
	}
	
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.STE1_SERENABURN))
		{
			CodexSequence.SetChronicleFlag(chronScript.STE1_SERENABURN);

			_Serena = new CodexPlayer(CodexThing.GuidFromCastID("Serena"));

			pos = _targetSpot.GetPosition();

			// send serena into 'sunlight' and play 'on-fire' animation
			_Serena.SendActorToPos(pos, (float)90.0);
		}
	}
}
