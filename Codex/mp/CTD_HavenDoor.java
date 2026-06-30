/**
 * Temesvar Haven door script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/
 

public class CTD_HavenDoor extends Codex
{

	private MP_CTDChronicle	chronScript;

	private int			_frameNum = 1;
	private float		_duration = (float)3.0;

	private boolean		bOpen = false;
	private boolean		bActive = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Rotate around frame;1", "Duration;3.0"};

	// --------------------------------------------------------------------------------------------

	public CTD_HavenDoor(int frameNum, float duration)
	{
		chronScript = (MP_CTDChronicle)GetChronicleScript(0);

		_frameNum = frameNum;
		_duration = duration;
	}

	// --------------------------------------------------------------------------------------------

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.OUBLIETTE_MELMOTHMEETING))
		{
			// sound indicating this exit is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(clickerGuid, 0, "GEN_DOORLOCKED");

			return;
		}

		if(bActive)
			return;

		bActive = true;

		CodexThing door = new CodexThing(guid);

		if(!bOpen) 
		{
			door.RotatePivot(_frameNum, _duration);
			bOpen = true;
		}
		else
		{
			door.RotatePivot(_frameNum, -_duration);
			bOpen = false;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		bActive = false;
	}

}



