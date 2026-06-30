/**
 *  SEW1_Subway script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 *
*/

public class SEW1_Subway extends Codex
{
	private NewYorkChronicle	chronScript;

	private 	CodexThing		_SubwayCar1;
	private 	CodexThing		_SubwayCar2;
	private 	CodexThing		_SubwayCar3;
	private 	CodexThing		_SubwayCar4;
	private 	CodexThing		_SubwayCar5;
	private 	CodexThing		_SubwayCar6;
	private 	CodexThing		_SubwayCar7;
	private 	CodexThing		_SubwayCar8;
	private 	CodexThing		_SubwayCar9;
	private 	CodexRegion		_SubwayRegion;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Subway car 1", "Subway car 2", "Subway car 3", "Subway car 4", 
							"Subway car 5", "Subway car 6", "Subway car 7", "Subway car 8", "Subway car 9",
							"Subway region"};

	public SEW1_Subway(CodexThing SubwayCar1, CodexThing SubwayCar2, CodexThing SubwayCar3, 
				CodexThing SubwayCar4, CodexThing SubwayCar5, CodexThing SubwayCar6, 
				CodexThing SubwayCar7, CodexThing SubwayCar8, CodexThing SubwayCar9, CodexRegion SubwayRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_SubwayCar1 = new CodexThing(SubwayCar1.GetGUID());
		_SubwayCar2 = new CodexThing(SubwayCar2.GetGUID());
		_SubwayCar3 = new CodexThing(SubwayCar3.GetGUID());
		_SubwayCar4 = new CodexThing(SubwayCar4.GetGUID());
		_SubwayCar5 = new CodexThing(SubwayCar5.GetGUID());
		_SubwayCar6 = new CodexThing(SubwayCar6.GetGUID());
		_SubwayCar7 = new CodexThing(SubwayCar7.GetGUID());
		_SubwayCar8 = new CodexThing(SubwayCar8.GetGUID());
		_SubwayCar9 = new CodexThing(SubwayCar9.GetGUID());

		_SubwayRegion = new CodexRegion(SubwayRegion.GetGUID());

		CaptureThing(_SubwayRegion.GetGUID());
	}

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!IsPlayerGuid(causeGuid))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.SEW1_SUBWAY))
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW1_SUBWAY);

			// play the sound
			new CodexSound("subway_01.wav", 2000, 3000, 100, 0, 0, -2656, 1056, 192);

			SetTimer(8);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_SubwayCar1.MoveToFrame(1, 3000);
		_SubwayCar2.MoveToFrame(1, 3000);
		_SubwayCar3.MoveToFrame(1, 3000);
		_SubwayCar4.MoveToFrame(1, 3000);
		_SubwayCar5.MoveToFrame(1, 3000);
		_SubwayCar6.MoveToFrame(1, 3000);
		_SubwayCar7.MoveToFrame(1, 3000);
		_SubwayCar8.MoveToFrame(1, 3000);
		_SubwayCar9.MoveToFrame(1, 3000);
	}
}
