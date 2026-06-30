/**
 * Teutonic Knight Base 1 Door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/
 

public class TEU1_22_1_Door extends Codex
{
	private ViennaChronicle	chronScript;

	private float			_speed = (float)10.0;
	private float			_openTime = (float)0.0;
	private CodexThing		_switchThing;

	private CodexThing		door;
	private int				advancerGuid = 0;

	private boolean			bOpen = false;
	private boolean			bSwitchActive = false;

	public static String _params[] = {"Movement speed;150.0", "Switch Time;0", "Switch"};

	public TEU1_22_1_Door(float speed, float openTime, CodexThing switchThing)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_speed			= speed;
		_openTime		= openTime;

		_switchThing	= new CodexThing(switchThing.GetGUID());
		door			= new CodexThing(GetClassThing());

		CaptureThing(_switchThing.GetGUID());
		if(_switchThing.GetDescriptionID().equalsIgnoreCase("PROP"))
			_switchThing.SetDescriptionID("GEN_SWITCH");
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		door.SetThingFlags(THING_FLAG_VISBLOCK);
		door.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		door.SetThingFlags(THING_FLAG_BLOCKSELECT);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{

		if(bSwitchActive)
			return;

		bSwitchActive = true;

		advancerGuid = clickerGuid; 

		if(!bOpen) 
		{
			_switchThing.MoveToFrame(1, _openTime);
			door.MoveToFrame(1, _speed);
			bOpen = true;

			//door.ClearThingFlags(THING_FLAG_VISBLOCK);
		}
		else
		{
			_switchThing.MoveToFrame(0, _openTime);
			door.MoveToFrame(0, _speed);
			bOpen = false;

			//door.SetThingFlags(THING_FLAG_VISBLOCK);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == door.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.TEU1_ADVANCE))
		{
			CodexSequence.SetChronicleFlag(chronScript.TEU1_ADVANCE);

			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_EscapeKnightBase"));
			q.Complete();

			CodexSequence.Advance(advancerGuid);
		}

		if(thingGuid == _switchThing.GetGUID())
		{
			bSwitchActive = false;
		}
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



