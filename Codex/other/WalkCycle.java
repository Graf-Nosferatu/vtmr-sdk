/**
 * Walk cycle script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class WalkCycle extends Codex
{
	private int			actorGuid;
	private CodexActor	actor;
	private int			numFrames = 0;
	private int			currentFrame = 0;
	private int			goalFrame = 0;

	private float[]		pos;
	private float		speed = (float)90.0;


	public WalkCycle()
	{
		// init our position array
		pos = new float[3];
	}

	public boolean Init()
	{
		actorGuid = GetClassThing();

		if(!IsActorGuid(actorGuid))
		{
			CodexConsole.PrintError("No class thing in WalkCycle!");
			return(false);
		}

		actor = new CodexActor(actorGuid);

		numFrames = actor.GetNumFrames();

		// if no frames, nothing to do
		if(numFrames == 0)
		{
			CodexConsole.PrintError("Object w/o frames in WalkCycle!");
			return(false);
		}

		pos = actor.GetFramePosition(goalFrame);

		// override the AI behavior to return home on losing target
		actor.SetActorFlags(THING_AF_AINORETURNHOME);

		return(true);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		currentFrame = 0;
		goalFrame = 1;

		if(Init())
			SetTimer(5);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		actor.SendActorToPos(pos, (float)90.0);
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		currentFrame = goalFrame;

		if(++goalFrame >= numFrames)
			goalFrame = 0;

		pos = actor.GetFramePosition(goalFrame);
		actor.SendActorToPos(pos, (float)90.0);
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(goalFrame);
		CodexSequence.SaveInt(currentFrame);
	}

	public void restore(int flags)
	{
		goalFrame = CodexSequence.RestoreInt();
		currentFrame = CodexSequence.RestoreInt();
	}

	public void restored(int flags)
	{
		// kill any pending timer from the beginscene
		KillTimer();

		if(Init())
			actor.SendActorToPos(pos, (float)90.0);
	}

}
