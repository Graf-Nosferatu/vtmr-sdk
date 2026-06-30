/**
 * Old Town 7.1 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_7_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int	TIMER_ID_CLOCK			= 0;
	private static final int	TIMER_ID_CATHEDRAL		= 1;
	private static final int	TIMER_ID_CROW			= 2;
	private static final int	TIMER_ID_CHRIS			= 3;
	private static final int	TIMER_ID_SCREAM			= 4;

	private CodexRegion			_PrepareAttackRegion;
	private CodexRegion			_ExecuteAttackRegion;

	private CodexActor			_Szlachta;
	private CodexActor			_Revenant1;
	private CodexActor			_Revenant2;

	private CodexActor			_Pedestrian1;
	private CodexActor			_Pedestrian2;
	private CodexActor			_Christof;

	private int					deadEnemies = 0;

	private int					christofGUID;

	private boolean				bAttackConversation = false;
	public boolean				bHearAttackConversation = false;

	public static String _params[] = {"Prepare attack region", "Execute attack region", 
										"Szlachta", "Revenant 1", "Revenant 2", "Pedestrian 1", "Pedestrian 2"};

	public OLDT_7_1(CodexRegion PrepareAttackRegion, CodexRegion ExecuteAttackRegion, CodexActor Szlachta,
					CodexActor Revenant1, CodexActor Revenant2, CodexActor Pedestrian1, CodexActor Pedestrian2)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_PrepareAttackRegion = new CodexRegion(PrepareAttackRegion.GetGUID());
		_ExecuteAttackRegion = new CodexRegion(ExecuteAttackRegion.GetGUID());
		_Szlachta = new CodexActor(Szlachta.GetGUID());
		_Revenant1 = new CodexActor(Revenant1.GetGUID());
		_Revenant2 = new CodexActor(Revenant2.GetGUID());
		_Pedestrian1 = new CodexActor(Pedestrian1.GetGUID());
		_Pedestrian2 = new CodexActor(Pedestrian2.GetGUID());

		CaptureThing(_PrepareAttackRegion.GetGUID());
		CaptureThing(_ExecuteAttackRegion.GetGUID());
		CaptureThing(_Szlachta.GetGUID());
		CaptureThing(_Revenant1.GetGUID());
		CaptureThing(_Revenant2.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		///////////////////////////////////////////////////////////////////
		// NOTE: EVEN IF THIS FLY THROUGH IS CUT OR NOT DONE, LEAVE THIS
		// CHRON FLAG, IT'S CHECKED IN STHM_6_1
		///////////////////////////////////////////////////////////////////
		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_FLYTHROUGH);

			// change the exit from silvermines2 and silvermines3 to take 
			// them to the night version of silvermines1, and add the backward links
			CodexSequence.ChangeExit("SilverMines2", 0, "SilverMinesNight", 1);
			CodexSequence.ChangeExit("SilverMines2", 2, "SilverMinesNight", 3);
			CodexSequence.ChangeExit("SilverMines3", 1, "SilverMinesNight", 2);

			CodexSequence.ChangeExit("SilverMinesNight", 1, "SilverMines2", 0);
			CodexSequence.ChangeExit("SilverMinesNight", 3, "SilverMines2", 2);
			CodexSequence.ChangeExit("SilverMinesNight", 2, "SilverMines3", 1);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ClockTowerByNight.ncp", 30);

			SetTimer(5, TIMER_ID_CLOCK);			
		}

		christofGUID = CodexThing.GuidFromCastID("Christof");
		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_ATTACK))
		{
			// disable guard/enemy search until this old town attack scene is overwith
			AISetMainFlags(AIMAIN_FLAG_NOGUARDSEARCH + AIMAIN_FLAG_NOENEMYSEARCH);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_ATTACKREGION))
		{
			// hide actors for convent attack sequence in this scene
			_Szlachta.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Revenant1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Revenant2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Pedestrian1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Pedestrian2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

			_Szlachta.SetCollideType(THING_COLLIDE_NONE);
			_Revenant1.SetCollideType(THING_COLLIDE_NONE);
			_Revenant2.SetCollideType(THING_COLLIDE_NONE);
			_Pedestrian1.SetCollideType(THING_COLLIDE_NONE);
			_Pedestrian2.SetCollideType(THING_COLLIDE_NONE);

			_Szlachta.SetActorFlags(THING_AF_AIPAUSED);
			_Revenant1.SetActorFlags(THING_AF_AIPAUSED);
			_Revenant2.SetActorFlags(THING_AF_AIPAUSED);
			_Pedestrian1.SetActorFlags(THING_AF_AIPAUSED);
			_Pedestrian2.SetActorFlags(THING_AF_AIPAUSED);

			_Szlachta.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			_Szlachta.SetActorFlags(THING_AF_INVUL);
			_Revenant1.SetActorFlags(THING_AF_INVUL);
			_Revenant2.SetActorFlags(THING_AF_INVUL);

			// change scene in the convent for the attack sequence
			CodexSequence.ChangeScene("Convent", "CNVT_7_1.nsd");
		}

		// if wilhem has joined your party without you making a fuss, CONVDONE 
		// will be set, and if they haven't been through the training yet, change
		// the scene on the bridge to the training scene
		if(CodexSequence.GetChronicleFlag(chronScript.UNIV_CONVDONE) &&
			!CodexSequence.GetChronicleFlag(chronScript.JUDB_TRAINFEED))
		{
			// change scene on bridge for vampire training
			CodexSequence.ChangeScene("JudithBridge", "JUDB_7_8.nsd");
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_CLOCK:

				// Fade out
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
				new CodexSound("bell_toll_01_lp.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Pedestrian1.GetGUID());	
				SetTimer(3, TIMER_ID_CATHEDRAL);
				break;

			case TIMER_ID_CATHEDRAL:

				// Fade in
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "StThomasByNight.ncp", 30);
				SetTimer(5, TIMER_ID_CROW);
				break;

			case TIMER_ID_CROW:

				// Fade out
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
				new CodexSound("crow_shortCaw.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Pedestrian1.GetGUID());	
				SetTimer(3, TIMER_ID_CHRIS);
				break;

			case TIMER_ID_CHRIS:

				// Fade in
				CodexCamera.Release(0);
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);
				break;
			
			case TIMER_ID_SCREAM:

				new CodexSound("Anezka_Scream_10.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _Pedestrian1.GetGUID());

				// enable guard/enemy search now
				//AIClearMainFlags(AIMAIN_FLAG_NOGUARDSEARCH + AIMAIN_FLAG_NOENEMYSEARCH);

				// set the peds to walk free again
				_Pedestrian1.ClearActorFlags(THING_AF_AIPAUSED);
				_Pedestrian2.ClearActorFlags(THING_AF_AIPAUSED);

				// christof line/conversation about convent
				HearAttackConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// see if all enemies are dead
		if(++deadEnemies == 3)
		{
			// play anezka scream
			SetTimer(2, TIMER_ID_SCREAM);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _PrepareAttackRegion.GetGUID() &&
			causeGUID == CodexPlayer.GetCurrentPlayer() &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_ATTACKREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_ATTACKREGION);

			// show actors for convent attack sequence in this scene
			_Szlachta.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Revenant1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Revenant2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Pedestrian1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Pedestrian2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

			_Szlachta.SetCollideType(THING_COLLIDE_CYL);
			_Revenant1.SetCollideType(THING_COLLIDE_CYL);
			_Revenant2.SetCollideType(THING_COLLIDE_CYL);
			_Pedestrian1.SetCollideType(THING_COLLIDE_CYL);
			_Pedestrian2.SetCollideType(THING_COLLIDE_CYL);

			// set the targets of the enemies
			_Revenant1.AISetTarget(_Pedestrian1.GetGUID());
			_Revenant2.AISetTarget(_Pedestrian2.GetGUID());
			_Szlachta.AISetTarget(_Pedestrian1.GetGUID());

			// make them easy to kill for the player and to make sure this scene doesn't drag out
			//_Revenant1.SetActorHealth((float)10.0);
			//_Revenant2.SetActorHealth((float)10.0);
			//_Szlachta.SetActorHealth((float)10.0);
		}
		else if(guid == _ExecuteAttackRegion.GetGUID() &&
			causeGUID == CodexPlayer.GetCurrentPlayer() &&
			CodexSequence.GetChronicleFlag(chronScript.OLDT_ATTACKREGION) &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_ATTACK))
		{
			CodexSequence.SetChronicleFlag(chronScript.OLDT_ATTACK);

			// close the st.thomas exit so they don't see the arch bishop there 
			// immediately before they go into the convent
			CodexSequence.CloseExit("OldTown", 5);

			AttackConversation(causeGUID, 0);
		}
	}

	public void AttackConversation(int starterGuid, int npcGuid)
	{
		bAttackConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "7_1_Attack", "7_1_Attack.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void HearAttackConversation(int starterGuid, int npcGuid)
	{
		bHearAttackConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "7_1_HearAttack", "7_1_HearAttack.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bAttackConversation)
		{
			AIOn();
			bAttackConversation = false;
			CodexCamera.Release(starterGuid);

			_Szlachta.ClearActorFlags(THING_AF_AIPAUSED);
			_Revenant1.ClearActorFlags(THING_AF_AIPAUSED);
			_Revenant2.ClearActorFlags(THING_AF_AIPAUSED);

			_Szlachta.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant1.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Revenant2.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

			_Szlachta.ClearActorFlags(THING_AF_INVUL);
			_Revenant1.ClearActorFlags(THING_AF_INVUL);
			_Revenant2.ClearActorFlags(THING_AF_INVUL);

			// sick these dudes on the player
			_Revenant1.AISetTarget(CodexPlayer.GetCurrentPlayer());
			_Revenant2.AISetTarget(CodexPlayer.GetCurrentPlayer());
			_Szlachta.AISetTarget(CodexPlayer.GetCurrentPlayer());
		}

		if(bHearAttackConversation)
		{
			AIOn();
			bHearAttackConversation = false;
			_Christof.StopActorAction();
			CodexCamera.Release(starterGuid);

			// open the convent exit so they can go in
			CodexSequence.OpenExit("OldTown", 4);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bAttackConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Revenant1.LookAtThing(christofGUID);
							_Revenant2.LookAtThing(christofGUID);
							_Szlachta.LookAtThing(christofGUID);

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, christofGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_CLOSEUP, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bHearAttackConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}
	}
}