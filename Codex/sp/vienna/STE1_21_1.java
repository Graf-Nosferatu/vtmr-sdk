/**
 *  Stephansdom1, 21.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STE1_21_1 extends Codex
{
	private ViennaChronicle	chronScript;

	private static final int TIMER_ID_FADEIN	= 0;
	private static final int TIMER_ID_GEAR		= 1;

	private CodexThing		_gear;
	private int				_frameNum = 1;
	private float			_duration = (float)2.0;
	private int				rotateNum = 0;
	
	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Gear", "Rotate around frame;1", "Duration;2.0"};

	public STE1_21_1(CodexThing gear, int frameNum, float duration)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_gear = new CodexThing(gear.GetGUID());

		CaptureThing(_gear.GetGUID());

		_frameNum = frameNum;
		_duration  = duration;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.STE1_GEARMOVED))
		{
			CodexSequence.SetChronicleFlag(chronScript.STE1_GEARMOVED);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.0, false);

			// play sunrise video
			PlayVideo("Sunrise.bik");
			//SetTimer(0, TIMER_ID_FADEIN);
		}
	}

	public void videoended(int id)
	{
		// Fade in
		CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)0.0, false);

		// fade in from white
		CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)8.0, 0xffffff, false);

		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "gearMove.ncp", 60);

		// they can't get back out to the main hub
		CodexSequence.CloseExit("Stephansdom1", 0);
		CodexSequence.CloseExit("InnerStradt", 4);

		SetTimer(2, TIMER_ID_GEAR);

		//SetTimer(0, TIMER_ID_FADEIN);
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(++rotateNum < 6)
		{
			SetTimer(2, TIMER_ID_GEAR);
		}
		else if(rotateNum == 6)
		{
			// the gear is done moving
			CodexCamera.Release(CodexPlayer.GetCurrentPlayer());

			// make the gear no collide so player can easily walk past it
			_gear.SetCollideType(THING_COLLIDE_NONE);

			// mark 'find secret entrance to clock' quest complete
			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_Infiltrate"));
			q.Complete();
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FADEIN:

				// fade in from white
				CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)8.0, 0xffffff, false);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "gearMove.ncp", 60);

				// they can't get back out to the main hub
				CodexSequence.CloseExit("Stephansdom1", 0);
				CodexSequence.CloseExit("InnerStradt", 4);

				SetTimer(2, TIMER_ID_GEAR);
				break;

			case TIMER_ID_GEAR:

				_gear.RotatePivot(_frameNum, _duration);
				break;				
		}
	}
}
