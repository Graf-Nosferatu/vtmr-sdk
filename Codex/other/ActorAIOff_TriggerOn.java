/**
 * Starts an actor's AI off, when the region is entered, their AI is turned on
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ActorAIOff_TriggerOn extends Codex
{
	private CodexActor			_Actor;
	private int					_TriggererGuid;

	private boolean				bTriggered = false;

	private float				_Delay;

	public static String _params[] = {"Actor", "Delay (seconds before AI is turned on);0"};

	public ActorAIOff_TriggerOn(CodexActor Actor, float Delay)
	{
		_Actor = new CodexActor(Actor.GetGUID());
		_Delay = Delay;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!bTriggered)
			_Actor.SetActorFlags(THING_AF_AIPAUSED);
	}	

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!IsPlayerGuid(causeGuid) || bTriggered)
			return;

		bTriggered = true;

		_TriggererGuid = causeGuid;

		SetTimer(_Delay);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_Actor.ClearActorFlags(THING_AF_AIPAUSED);

		_Actor.AISetTarget(_TriggererGuid);
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bTriggered);
	}
 
	public void restore(int flags)
	{
		bTriggered = CodexSequence.RestoreBoolean();
	}
}

