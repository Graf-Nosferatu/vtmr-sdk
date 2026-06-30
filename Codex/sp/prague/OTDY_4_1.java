/**
 * Old Town Day 4.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OTDY_4_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int	TIMER_ID_FOURSTAGS		= 0;
	private static final int	TIMER_ID_STABLE			= 1;
	private static final int	TIMER_ID_STOPCAMERA		= 2;

	private CodexRegion			_OutsideSmithyRegion;
	private CodexActor			_Knight;
	private CodexActor			_Stranger;
	private CodexActor			_Christof;

	private boolean				bOutsideSmithyConversation = false;
	private boolean				bStrangerConversation = false;
	private boolean				bKnightConversation = false;

	private int					christofGUID;
	private int					strangerGUID;
	private int					knightGUID;

	public static String _params[] = {"Outside Smithy region", "Stranger", "Knight"};

	public OTDY_4_1(CodexRegion OutsideSmithyRegion, CodexActor Stranger, CodexActor Knight)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_OutsideSmithyRegion = new CodexRegion(OutsideSmithyRegion.GetGUID());
		_Stranger = new CodexActor(Stranger.GetGUID());
		_Knight = new CodexActor(Knight.GetGUID());

		CaptureThing(_OutsideSmithyRegion.GetGUID());
		CaptureThing(_Stranger.GetGUID());
		CaptureThing(_Knight.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		strangerGUID = _Stranger.GetGUID();
		knightGUID = _Knight.GetGUID();

		_Christof = new CodexActor(christofGUID);
		
		if(!CodexSequence.GetChronicleFlag(chronScript.OTDY_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.OTDY_FLYTHROUGH);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OTDYFourStags.ncp", 30);
			SetTimer((float)2.0, TIMER_ID_FOURSTAGS);
		}

		// make knight and stranger clickable as talkies if they haven't been talked to yet
		if(!CodexSequence.GetChronicleFlag(chronScript.OTDY_KNIGHT))
		{
			_Knight.SetActorFlags(THING_AF_AIPAUSED);
			_Knight.SetActorFlags(THING_AF_TALKTO);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.OTDY_STRANGER))
		{
			_Stranger.SetActorFlags(THING_AF_AIPAUSED);
			_Stranger.SetActorFlags(THING_AF_TALKTO);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FOURSTAGS:

				CodexSound.PlayVoice(_Christof.GetGUID(), "Christof_4_1_52", 40);
				SetTimer((float)4.0, TIMER_ID_STABLE);
				break;

			case TIMER_ID_STABLE:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "OTDYStable.ncp", 30);
				SetTimer((float)7.0, TIMER_ID_STOPCAMERA);
				break;

			case TIMER_ID_STOPCAMERA:

				CodexCamera.Release(0);
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(IsPlayerGuid(causeGUID) &&
			guid == _OutsideSmithyRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.OTDY_OUTSMITHYREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.OTDY_OUTSMITHYREGION);

			CodexPlayer targetPlayer = new CodexPlayer(causeGUID);

			targetPlayer.Stop();
			targetPlayer.CancelActorAction();

			OutsideSmithyConversation(causeGUID, 0);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Knight.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.OTDY_KNIGHT))
		{
			CodexSequence.SetChronicleFlag(chronScript.OTDY_KNIGHT);

			KnightConversation(clickerGuid, 0);
		}
		else if(guid == _Stranger.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.OTDY_STRANGER))
		{
			CodexSequence.SetChronicleFlag(chronScript.OTDY_STRANGER);

			StrangerConversation(clickerGuid, 0);
		}
	}

	public void OutsideSmithyConversation(int starterGuid, int npcGuid)
	{
		bOutsideSmithyConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_OutSmithy", "4_1_OutSmithy.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void KnightConversation(int starterGuid, int npcGuid)
	{
		bKnightConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Knights", "4_1_Knights.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void StrangerConversation(int starterGuid, int npcGuid)
	{
		bStrangerConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Stranger", "4_1_Stranger.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bOutsideSmithyConversation)
		{
			AIOn();
			bOutsideSmithyConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bKnightConversation)
		{
			AIOn();
			bKnightConversation = false;
			CodexCamera.Release(starterGuid);

			// make the knight no longer "talkable"
			_Knight.ClearActorFlags(THING_AF_TALKTO);
		}
		
		if(bStrangerConversation)
		{
			AIOn();
			bStrangerConversation = false;
			CodexCamera.Release(starterGuid);

			// make the stranger no longer "talkable" and restart his AI
			_Stranger.ClearActorFlags(THING_AF_AIPAUSED);
			_Stranger.ClearActorFlags(THING_AF_TALKTO);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bOutsideSmithyConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bOutsideSmithyConversation)

		if(bKnightConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Knights.ncp", 30);
							_Knight.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 1:

							_Knight.StopActorAction();
							_Christof.PlayMotionSetMode(MOTION_GESTURE4, false, (float)30.0);
							break;

						case 2:

							//_Christof.StopActorAction();
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, knightGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							_Christof.StopActorAction();
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bKnightConversation)

		if(bStrangerConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, strangerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bStrangerConversation)
	}
}