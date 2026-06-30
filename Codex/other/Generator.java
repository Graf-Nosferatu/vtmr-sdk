/**
 * Generator script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class Generator extends Codex
{
	private	String		_templateName;
	private float		_delay;
	private float		_frequency;
	private int			_maxThings;
	private int			_maxAlive;

	private int			numThings = 0;
	private int			numAlive = 0;

	private int			numFrames = 0;
	private float[]		pos;

	private CodexThing	generatorThing;
	private CodexThing	generatedThing;
	private	int			generatedGuid;

	public static String _params[] = {"Template to generate", "Delay;10.0", "Frequency;60.0", "Max Things;20", "Max Alive;0"};

	public Generator(String templateName, float delay, float frequency, int maxThings, int maxAlive)
	{	
		_templateName	= templateName;
		_delay			= delay;
		_frequency		= frequency;
		_maxThings		= maxThings;
		_maxAlive		= maxAlive;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(numThings == _maxThings)
			return;

		// don't go nuts
		if(_frequency < 0.1)
			return;

		generatorThing = new CodexThing(GetClassThing());

		if(generatorThing != null)
		{
			numFrames = generatorThing.GetNumFrames();
			if(numFrames != 0)
				pos = generatorThing.GetFramePosition(1);
			
			if(_delay > 0.1)
				SetTimer(_delay);
			else
				SetTimer(_frequency);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		try
		{
			// generate one thing
			generatedGuid = generatorThing.SpawnThing(_templateName);
			generatedThing = new CodexThing(generatedGuid);

			// abort in error
			if(generatedThing == null)
				return;

			// if the generator has a frame send the thing there if it can move...
			if(numFrames != 0)
			{
				if(IsActorGuid(generatedGuid))
				{
					CodexActor actor = new CodexActor(generatedGuid);
					actor.SendActorToPos(pos, (float)150.0);
				}
			}

			// handle the number of things generated
			if(_maxThings != 0)
			{
				if(++numThings >= _maxThings)
					return;
			}

			// handle the number of things alive
			if(_maxAlive != 0)
			{
				CaptureThing(generatedGuid);
				if(++numAlive >= _maxAlive)
					return;
			}

			// get ready for the next one
			KillTimer();
			SetTimer(_frequency);

		}
		catch(Exception e)
		{
			CodexConsole.PrintException(e.getMessage() + " in Generator [timer]");
		}
		catch(Error e)
		{
			CodexConsole.PrintError(e.getMessage() + " in Generator [timer]");
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		numAlive--;

		if((numAlive < _maxAlive) && ((_maxThings == 0) || (numThings < _maxThings)))
			// get ready for the next one
			SetTimer(_frequency);
	}	

	public void save(int flags)
	{
		CodexSequence.SaveString(_templateName);
		CodexSequence.SaveFloat(_delay);
		CodexSequence.SaveFloat(_frequency);
		CodexSequence.SaveInt(_maxThings);
		CodexSequence.SaveInt(_maxAlive);
		CodexSequence.SaveInt(numThings);
		CodexSequence.SaveInt(numAlive);
	}
 
	public void restore(int flags)
	{
		_templateName = CodexSequence.RestoreString();
		_delay = CodexSequence.RestoreFloat();
		_frequency = CodexSequence.RestoreFloat();
		_maxThings = CodexSequence.RestoreInt();
		_maxAlive = CodexSequence.RestoreInt();
		numThings = CodexSequence.RestoreInt();
		numAlive = CodexSequence.RestoreInt();
	}
}
