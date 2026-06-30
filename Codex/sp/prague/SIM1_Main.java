/**
 * Silver Mines 1 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SIM1_Main extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_STENCH	= 1;
	private static final int TIMER_ID_POORSOUL	= 2;
	
	private	CodexRegion		_StenchRegion;
	private	CodexRegion		_BodyRegion;
	private CodexActor		_firstSzlachta;
	private CodexRegion		_RatAttackRegion;
	private CodexActor		_Rat1;
	private CodexActor		_Rat2;
	private CodexThing		_QuickExitSIM4;

	private CodexActor		christofActor;

	private int				pathNum;

	private boolean			bRatAttackConversation = false;

	public static String _params[] = {"Stench of death region", "Body region", "First szlachta", "Rat Attack region", 
										"Rat 1", "Rat 2", "Quick exit to silvermines4"};

	public SIM1_Main(CodexRegion StenchRegion, CodexRegion BodyRegion, CodexActor firstSzlachta, CodexRegion RatAttackRegion, 
						CodexActor Rat1, CodexActor Rat2, CodexThing QuickExitSIM4)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_StenchRegion = new CodexRegion(StenchRegion.GetGUID());
		_BodyRegion = new CodexRegion(BodyRegion.GetGUID());
		_firstSzlachta = new CodexActor(firstSzlachta.GetGUID());
		_RatAttackRegion = new CodexRegion(RatAttackRegion.GetGUID());
		_Rat1 = new CodexActor(Rat1.GetGUID());
		_Rat2 = new CodexActor(Rat2.GetGUID());
		_QuickExitSIM4 = new CodexThing(QuickExitSIM4.GetGUID());

		CaptureThing(_StenchRegion.GetGUID());
		CaptureThing(_BodyRegion.GetGUID());
		CaptureThing(_RatAttackRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
		{
			// hide beams that were blocking this hall before
			_QuickExitSIM4.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_QuickExitSIM4.SetCollideType(THING_COLLIDE_NONE);
		}

		christofActor = new CodexActor(CodexThing.GuidFromCastID("Christof"));

		if(!CodexSequence.GetChronicleFlag(chronScript.SIM1_RATATTACK))
		{
			// hides rats for rat attack sequence
			_Rat1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Rat2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

			_Rat1.SetCollideType(THING_COLLIDE_NONE);
			_Rat2.SetCollideType(THING_COLLIDE_NONE);

			_Rat1.SetActorFlags(THING_AF_AIPAUSED);
			_Rat2.SetActorFlags(THING_AF_AIPAUSED);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.SIM1_BODYREGION))
		{
			// hide the szlachta for the body sequence
			_firstSzlachta.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_firstSzlachta.SetCollideType(THING_COLLIDE_NONE);
			_firstSzlachta.SetActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_STENCH:

				CodexCamera.Release(0);
				christofActor.StopActorAction();
				break;

			case TIMER_ID_POORSOUL:

				// show the szlachta for the body sequence
				_firstSzlachta.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_firstSzlachta.SetCollideType(THING_COLLIDE_CYL);

				// ai on so first szlachta will attack
				_firstSzlachta.ClearActorFlags(THING_AF_AIPAUSED);

				christofActor.StopActorAction();
				CodexCamera.Release(0);
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _StenchRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM1_STENCHREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.SIM1_STENCHREGION);

			CodexPlayer targetPlayer = new CodexPlayer(causeGUID);

			targetPlayer.Stop();
			targetPlayer.CancelActorAction();

			// do whatever happens when that region is entered
			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "stenchMine.ncp", 45);
			CodexSound.PlayVoice(christofActor.GetGUID(), "Christof_5_1_114", 40);
			christofActor.PlayMotionSetMode(MOTION_ACTION2, false, (float)15.0);

			SetTimer((float)5.5, TIMER_ID_STENCH);
		}
		else if(guid == _BodyRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM1_BODYREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.SIM1_BODYREGION);

			// miner body and szlachta sequence
			pathNum = 1;

			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "bodyRegionMine.ncp", 60);
			CodexSound.PlayVoice(christofActor.GetGUID(), "Christof_5_1_115", 40);
			christofActor.PlayMotionSetMode(MOTION_ACTION8, false, (float)15.0);

			SetTimer((float)7.5, TIMER_ID_POORSOUL);
		}
		else if(guid == _RatAttackRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SIM1_RATATTACK))
		{
			CodexSequence.SetChronicleFlag(chronScript.SIM1_RATATTACK);

			// show rats for rat attack sequence
			_Rat1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Rat2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

			_Rat1.SetCollideType(THING_COLLIDE_CYL);
			_Rat2.SetCollideType(THING_COLLIDE_CYL);

			_Rat1.ClearActorFlags(THING_AF_AIPAUSED);
			_Rat2.ClearActorFlags(THING_AF_AIPAUSED);

			RatAttackConversation(causeGUID, 0);
		}
	}

	public void RatAttackConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bRatAttackConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "5_1_RatAttack", "5_1_RatAttack.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		bRatAttackConversation = false;
		christofActor.StopActorAction();
		CodexCamera.Release(starterGuid);
		AIOn();
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						christofActor.PlayMotionSetMode(MOTION_ACTION8, false, (float)15.0);
						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 6);
						//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ratAttackMine.ncp", 50);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

