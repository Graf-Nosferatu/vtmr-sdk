/**
 * FAC4_WindowBreak script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 

public class FAC4_WindowBreak extends Codex
{
	private CodexThing	_windowEdge;
	private CodexThing	_trigger1;
	private CodexThing	_trigger2;
	private CodexThing	_trigger3;
	private CodexThing	_trigger4;
	private CodexThing	_trigger5;
	private CodexThing	_trigger6;
	private CodexThing	_trigger7;

	private boolean		bTriggered = false;

	private float[]		velocity = new float[3];

	public static String _params[] = {"Window Edge", "Trigger 1", "Trigger 2", "Trigger 3", "Trigger 4", "Trigger 5", "Trigger 6", "Trigger 7"};

	public FAC4_WindowBreak(CodexThing windowEdge, CodexThing trigger1, CodexThing trigger2, CodexThing trigger3, CodexThing trigger4, CodexThing trigger5, CodexThing trigger6, CodexThing trigger7)
	{
		_windowEdge = new CodexThing(windowEdge.GetGUID());
		_trigger1 = new CodexThing(trigger1.GetGUID());
		_trigger2 = new CodexThing(trigger2.GetGUID());
		_trigger3 = new CodexThing(trigger3.GetGUID());
		_trigger4 = new CodexThing(trigger4.GetGUID());
		_trigger5 = new CodexThing(trigger5.GetGUID());
		_trigger6 = new CodexThing(trigger6.GetGUID());
		_trigger7 = new CodexThing(trigger7.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!bTriggered)
		{
			_windowEdge.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		}
	}
	
	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		bTriggered = true;
		_windowEdge.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
		_trigger1.Trigger(0, 0, 0, 0, 0, 0);
		_trigger2.Trigger(0, 0, 0, 0, 0, 0);
		_trigger3.Trigger(0, 0, 0, 0, 0, 0);
		_trigger4.Trigger(0, 0, 0, 0, 0, 0);
		_trigger5.Trigger(0, 0, 0, 0, 0, 0);
		_trigger6.Trigger(0, 0, 0, 0, 0, 0);
		_trigger7.Trigger(0, 0, 0, 0, 0, 0);
	}
}



