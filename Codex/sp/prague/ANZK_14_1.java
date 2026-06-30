/**
 * Anezka's Room scene 14.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ANZK_14_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_CHRISASLEEP	= 0;
	private static final int TIMER_ID_NIGHTMARE		= 1;
	private static final int TIMER_ID_PULSE			= 2;
	private static final int TIMER_ID_ANEZKARUN		= 3;
	private static final int TIMER_ID_FEEDATTACK	= 4;
	private static final int TIMER_ID_FEEDING		= 5;
	private static final int TIMER_ID_REVEALED		= 6;
	private static final int TIMER_ID_WAKEUP		= 7;
	private static final int TIMER_ID_SEARCH		= 8;
	private static final int TIMER_ID_LINE			= 9;
	private static final int TIMER_ID_ENDSCENE		= 10;

	private	CodexActor		_Anezka;
	public CodexActor		_Christof;

	public CodexPlayer		Wilhem;
	public CodexPlayer		Serena;

	private float[]			pos;

	public boolean			bMissingWindowConversation = false;

	public static String _params[] = {"Anezka"};

	public ANZK_14_1(CodexActor Anezka)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Anezka = new CodexActor(Anezka.GetGUID());
		CaptureThing(_Anezka.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		// HIDE WILHEM AND SERENA DURING THIS SCENE
		Wilhem = new CodexPlayer(CodexThing.GuidFromCastID("Wilhem"));
		Serena = new CodexPlayer(CodexThing.GuidFromCastID("Serena"));
		
		Wilhem.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
		Serena.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

		// set the current player to christof
		CodexPlayer christof = new CodexPlayer(CodexThing.GuidFromCastID("Christof"));
		christof.SetCurrentPlayer();

		// set to tell HAVN_11_1 not to set up that advance anymore
		CodexSequence.SetChronicleFlag(chronScript.HAVN_ANEZKASCENE);

		// Fade in
		CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
		
		_Christof = new CodexActor(CodexThing.GuidFromCastID("Christof"));
		_Christof.EnableActorWeapon(false);
		_Christof.OverrideActorWeapon("Punch", false, false);

		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		_Christof.PlayMotionSetMode(MOTION_SPECIAL6, false, (float)30.0);
		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ANZKChrisDreams.ncp", 15);
		
		SetTimer(8, TIMER_ID_NIGHTMARE, clientGuid);

		// just to get rid of this "useless" quest
		CodexQuest q = new CodexQuest(CodexQuest.Load("P1_RestToAdvance"));
		q.Destroy();

		// set "dreamy" filter
		//CodexCamera.AddFilter(CodexPlayer.GetCurrentPlayer(), (float)13.5, 0x8080ff);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_NIGHTMARE:
				
				SetTimer(0, TIMER_ID_PULSE, (int)arg0);

				_Christof.StopActorAction();

				// Someone running from something, scared breathing sounds, footsteps, etc.
				pos = _Anezka.GetFramePosition(2);
				_Anezka.SendActorToPos(pos, (float)210.0);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Nightmare.ncp", 190);

				new CodexSound("thunder_01.WAV", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());

				SetTimer(3, TIMER_ID_ANEZKARUN, (int)arg0);
				break;

			case TIMER_ID_PULSE:

				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.5, true);
				CodexSound snd1 = new CodexSound("blood_drain_fast_lp.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());
				break;

			case TIMER_ID_ANEZKARUN:

				SetTimer(0, TIMER_ID_PULSE, (int)arg0);

				_Anezka.Stop();
				_Anezka.CancelActorAction();

				_Anezka.SetPosition(pos);
				_Anezka.SetOrientation(_Anezka.GetFrameOrientation(2));

				// Camera from behind Anezka, she looks back frightened
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "AnezkaRunning.ncp", 15);
				CodexSound snd2 = new CodexSound("Anezka_Breath_4.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());
				_Anezka.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)15.0);
				SetTimer(4, TIMER_ID_FEEDATTACK, (int)arg0);
				break;

			case TIMER_ID_FEEDATTACK:

				SetTimer(0, TIMER_ID_PULSE, (int)arg0);
				
				_Anezka.StopActorAction();
				
				// Need to get Chris and Anezka in the right spot
				pos = _Anezka.GetFramePosition(2);
				_Christof.SetPosition(pos);
				_Christof.SetOrientation(_Anezka.GetFrameOrientation(2));
				pos[0] = pos[0] - 60;
				_Anezka.SetPosition(pos);
				_Anezka.SetOrientation(_Anezka.GetFrameOrientation(2));

				// A dark figure closes, and grapples with Anezka
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ChrisRevealed.ncp", 22);

				_Christof.PlayMotionSetMode(MOTION_FEED, false, (float)30.0);
				_Anezka.PlayMotionSetMode(MOTION_EMBRACED, false, (float)30.0);

				CodexSound snd3 = new CodexSound("Anezka_Scream_10.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());

				SetTimer((float)1.0, TIMER_ID_FEEDING, (int)arg0);
				break;

			case TIMER_ID_FEEDING:

				_Christof.PlayMotionSetMode(MOTION_FEEDING, false, (float)30.0);
				_Anezka.PlayMotionSetMode(MOTION_DRAINING, false, (float)30.0);

				SetTimer((float)1.0, TIMER_ID_REVEALED, (int)arg0);
				break;

			case TIMER_ID_REVEALED:

				// closeup of dark figure's face
				SetTimer(3, TIMER_ID_WAKEUP, (int)arg0);
				break;

			case TIMER_ID_WAKEUP:

				// Christof wakes up and yells
				CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, 0xffffff, true);
				CodexSound snd4 = new CodexSound("whoosh_pickup.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Anezka.GetGUID());
				SetTimer((float)1.5, TIMER_ID_SEARCH);
				break;

			case TIMER_ID_SEARCH:

				_Christof.StopActorAction();
				_Christof.SetPosition(_Anezka.GetFramePosition(1));
				_Christof.SetOrientation(_Anezka.GetFrameOrientation(1));

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ChrisSearch.ncp", 30);
				_Christof.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
				SetTimer((float)6.0, TIMER_ID_LINE);
				break;

			case TIMER_ID_LINE:

				MissingWindowConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_ENDSCENE:

				CodexCamera.Release(CodexPlayer.GetCurrentPlayer());
				_Christof.EnableActorWeapon(true);
				_Christof.CancelOverrideActorWeapon();
				_Christof.StopActorAction();

				CodexSequence.ChangeScene("University", "UNIV_14_1.nsd");
				CodexSequence.Jump("University", 0);
				break;
		}
	}

	public void MissingWindowConversation(int starterGuid, int npcGuid)
	{
		bMissingWindowConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "14_1_MissingWindow", "14_1_MissingWindow.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bMissingWindowConversation)
		{
			AIOn();
			bMissingWindowConversation = false;
			//CodexCamera.Release(starterGuid);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			SetTimer(2, TIMER_ID_ENDSCENE);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:
						
						//_Christof.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
