/**
 * Trigger Multiple script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 

public class triggerMult extends Codex
{
	private CodexThing	_trigger1;
	private CodexThing	_trigger2;
	private CodexThing	_trigger3;
	private CodexThing	_trigger4;

	private int			_numTriggers;

	private float[]		velocity = new float[3];

	public static String _params[] = {"Num Triggers;0", "Trigger 1", "Trigger 2", "Trigger 3", "Trigger 4"};

	public triggerMult(int numTriggers, CodexThing trigger1, CodexThing trigger2, CodexThing trigger3, CodexThing trigger4)
	{
		_numTriggers = numTriggers;

		switch(_numTriggers)
		{	
			case 4:
				_trigger4 = new CodexThing(trigger4.GetGUID());

			case 3:
				_trigger3 = new CodexThing(trigger3.GetGUID());

			case 2:
				_trigger2 = new CodexThing(trigger2.GetGUID());

			case 1:
				_trigger1 = new CodexThing(trigger1.GetGUID());
		}
	}

	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		switch(_numTriggers)
		{
			case 4:
				_trigger4.Trigger(0, 0, 0, 0, 0, 0);
			case 3:
				_trigger3.Trigger(0, 0, 0, 0, 0, 0);
			case 2:
				_trigger2.Trigger(0, 0, 0, 0, 0, 0);
			case 1:
				_trigger1.Trigger(0, 0, 0, 0, 0, 0);
		}
	}
}



