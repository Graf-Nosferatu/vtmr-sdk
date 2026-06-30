/**
 * Vysehrad3 25.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class VYC3_25_1 extends Codex
{
	private Prague2Chronicle chronScript;

	private int				_frameNum = 1;
	private float			_duration = (float)3.0;

	private int				pathNum = 0;

	private CodexActor		_vozhd;

	private CodexThing		_doorHalf;
	private CodexThing		_doorHalf2;

	private boolean			bActive = false;

	public static String _params[] = {"Vozhd", "Second half of door", "Rotate around frame;1", "Duration;3.0"};

	public VYC3_25_1(CodexActor vozhd, CodexThing doorHalf2, int frameNum, float duration)
	{
		chronScript = (Prague2Chronicle)GetChronicleScript(0);

		_vozhd = new CodexActor(vozhd.GetGUID());
		_doorHalf2 = new CodexThing(doorHalf2.GetGUID());

		CaptureThing(_vozhd.GetGUID());
		CaptureThing(_doorHalf2.GetGUID());

		_frameNum = frameNum;
		_duration = duration;

		_doorHalf = new CodexThing(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.VYC3_VOZHDINTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.VYC3_VOZHDINTRO);

			_doorHalf.SetThingFlags(THING_FLAG_VISBLOCK);
			_doorHalf2.SetThingFlags(THING_FLAG_VISBLOCK);

			_vozhd.SetActorFlags(THING_AF_AIPAUSED);

			// close this exit until after they've killed the vozhd
			CodexSequence.CloseExit("VysCastle3", 1);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// open the exit to Vysehrad4
		CodexSequence.OpenExit("VysCastle3", 1);

		// open up animalism for christof
		CodexActor Christof = new CodexActor(CodexThing.GuidFromCastID("Christof"));
		Christof.SetActorDisciplineLevel("FeralWhispers", -1);

		//String aFormat = "%A" + Christof.GetName() + "%g" + "DGRP_ANIMALISM";
		//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(bActive)
			return;

		bActive = true;

		AIOff();

		_doorHalf.RotatePivot(_frameNum, _duration);
		_doorHalf2.RotatePivot(_frameNum, _duration);

		_doorHalf.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_doorHalf2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

		//_doorHalf.ClearThingFlags(THING_FLAG_VISBLOCK);
		//_doorHalf2.ClearThingFlags(THING_FLAG_VISBLOCK);

		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VYC3VozhdIntro.ncp", 150);
		pathNum++;
	}

	public void pathended(int clientGuid)
	{
		switch(pathNum)
		{
			case 1:

				_vozhd.PlayMotionSetMode(MOTION_CLAW, false, (float)30.0);
				new CodexSound("vozhd_idle_02.WAV", 2000, 4000, 100, 0, 0, _vozhd.GetGUID());

				SetTimer(3);

				pathNum++;
				break;
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		_vozhd.StopActorAction();

		_vozhd.ClearActorFlags(THING_AF_AIPAUSED);

		CodexCamera.Release(CodexPlayer.GetCurrentPlayer());

		AIOn();
	}
}

