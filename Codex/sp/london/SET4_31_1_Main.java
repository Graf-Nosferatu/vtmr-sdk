/**
 * Setite 4 31.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SET4_31_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	private static final int	TIMER_ID_COBRACHANGE		= 0;
	private static final int	TIMER_ID_CHANGED			= 1;

	private int			christofGUID;
	private int			lilyGUID;
	private int			lucretiaGUID;
	private int			pinkGUID;
	private int			servantGUID;

	private int			stuntCobraGuid;
	private int			cobraGuid;
	private int			frameServant = 0;

	private int			humanityTrack = 0;

	private	CodexActor	_Pink;
	private CodexActor	_Christof;
	private CodexActor	_stuntCobra;
	private CodexActor	_Cobra;

	public boolean		b31_1_BarterConversation = false;
	public boolean		bHeartChoiceConversation = false;
	public boolean		bAfterFight1Conversation = false;

	public CodexRegion	_lucretiaRegion;
	public CodexActor	_lucretia;
	public CodexActor	_servant;
	public CodexItem	_lucretiaHeart;

	private float[]		pos;

	public static String _params[] =	{"Lucretia Region", "Lucretia", "Servant"};

	public SET4_31_1_Main(CodexRegion lucretiaRegion, CodexActor lucretia, CodexActor servant)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_lucretiaRegion = new CodexRegion(lucretiaRegion.GetGUID());
		_lucretia		= new CodexActor(lucretia.GetGUID());
		_servant		= new CodexActor(servant.GetGUID());

		CaptureThing(_lucretiaRegion.GetGUID());
		CaptureThing(_lucretia.GetGUID());
		CaptureThing(_servant.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		_Pink = new CodexActor(pinkGUID);
		_Christof = new CodexActor(christofGUID);

		lucretiaGUID = _lucretia.GetGUID();
		servantGUID = _servant.GetGUID();

		if(!CodexSequence.GetChronicleFlag(chronScript.SET4_LUCRETIANOTDIE))
		{
			// make the servant invulnerable
			_servant.SetActorFlags(THING_AF_INVUL);
			_servant.SetActorFlags(THING_AF_AIPAUSED);
			_servant.SetActorFlags(THING_AF_NEUTRAL);
			_servant.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_COBRACHANGE:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "LucretiaBarter.ncp", 30);

				// Lucretia turns into a Giant Cobra
				stuntCobraGuid = _lucretia.SpawnThing("giantCobra_stuntDouble");
				_stuntCobra = new CodexActor(stuntCobraGuid);

				_lucretia.SetShell("redCloudShell_g", 0x5080, 0, 1, 5, 1);
				_stuntCobra.SetShell("redCloudShell_g", 0x5000, 0, 1, 5, 1);
				
				_lucretia.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)15.0);
				_stuntCobra.PlayMotionSetMode(MOTION_AWAKEN, false, (float)15.0);

				CodexSound snd1 = new CodexSound("lucretiaTransform.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _lucretia.GetGUID());

				SetTimer((float)10.0, TIMER_ID_CHANGED);
				break;

			case TIMER_ID_CHANGED:

				cobraGuid = _stuntCobra.SpawnThing("giantCobra");
				_Cobra = new CodexActor(cobraGuid);
				CaptureThing(cobraGuid);
				
				_stuntCobra.Remove();
				_lucretia.Remove();

				AIOn();
				CodexCamera.Release(0);

				CodexQuest q2 = new CodexQuest(CodexQuest.Load("L1_FindShipping"));
				q2.Complete();
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == servantGUID)
		{
			if(frameServant == 0)
			{
				_servant.LookAtThing(christofGUID);
				_servant.SetCollideType(THING_COLLIDE_NONE);
				_servant.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			}
			else if (frameServant == 1)
			{
				// execute WTF conversation here
			}

			frameServant++;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.SET4_BARTER))
		{
			CodexSequence.SetChronicleFlag(chronScript.SET4_BARTER);

			c31_1_BarterConversation(causeGUID, 0);
		}
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		HeartChoiceConversation(picker, 0);

		return(true);
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// the first time lucretia "dies", we trigger the lily/servant/heart scene and don't let her die

		if((guid == cobraGuid) && 
			!CodexSequence.GetChronicleFlag(chronScript.SET4_LUCRETIANOTDIE))
		{
			CodexSequence.SetChronicleFlag(chronScript.SET4_LUCRETIANOTDIE);
			
			// Remember to put in "killed" for Pink conversation
			_Christof.CancelOverrideActorWeapon();

			_Cobra = new CodexActor(cobraGuid);
			_servant = new CodexActor(servantGUID);

			// set lucretia's health back up greater than 0 and clear her DEAD flag
			_Cobra.SetActorHealth((float)25.0);
			_Cobra.ClearActorFlags(THING_AF_DEAD);
			
			// make lucretia invulnerable and pause her AI
			_Cobra.SetActorFlags(THING_AF_INVUL);
			_Cobra.SetActorFlags(THING_AF_AIPAUSED);
			_Cobra.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// make the snake look defeated
			_Cobra.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

			// make the servant come running out so the party can retrieve the heart
			_servant.ClearActorFlags(THING_AF_INVUL);
			_servant.ClearActorFlags(THING_AF_NEUTRAL);
			_servant.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_servant.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_servant.SetCollideType(THING_COLLIDE_CYL);
			_servant.ClearActorFlags(THING_AF_AIPAUSED);
			
			pos = _servant.GetFramePosition(2);
			_servant.SendActorToPos(pos, (float)210.0);
		}
		else if(guid == _servant.GetGUID())
		{
			_lucretiaHeart = new CodexItem(_servant.SpawnThing("heartlucretia"));

			_lucretiaHeart.SetShell("shellSprite_white", 0x9000, 0, .7f, 1, 1);

			CaptureThing(_lucretiaHeart.GetGUID());
		}
	}

	public void c31_1_BarterConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Boss_2.mp3", 50);
		b31_1_BarterConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "31_1_Barter", "31_1_Barter.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void HeartChoiceConversation(int starterGuid, int npcGuid)
	{
		bHeartChoiceConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "31_1_HeartChoice", "31_1_HeartChoice.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void AfterFight1Conversation(int starterGuid, int npcGuid)
	{
		bAfterFight1Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "31_1_AfterFight1", "31_1_AfterFight1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b31_1_BarterConversation)
		{
			b31_1_BarterConversation = false;

			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_ReturnHeart"));
			q.Complete();

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			SetTimer((float)0.5, TIMER_ID_COBRACHANGE);
		}

		if(bAfterFight1Conversation)
		{
			AIOn();
			bAfterFight1Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bHeartChoiceConversation)
		{
			//CodexSound.PopMusic();
			//AIOn();
			bHeartChoiceConversation = false;
			//CodexCamera.Release(starterGuid);

			_Christof.CancelOverrideActorWeapon();

			CodexSequence.SetChronicleFlag(chronScript.SET4_LUCRETIADEAD);

			AfterFight1Conversation(starterGuid, 0);
		}

		if(bAfterFight1Conversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bAfterFight1Conversation = false;
			CodexCamera.Release(starterGuid);

			CodexSequence.OpenExit("EastLondon", 3);
			CodexSequence.OpenExit("CargoShip", 0);					

			CodexQuest q3 = new CodexQuest(CodexQuest.Load("L1_BoardMagdelena"));

			// show cargo ship on map
			CodexSequence.SetLocationFlags("CargoShip", LOCATION_FLAG_SHOWINMAP);

			// open serpentis for christof
			CodexActor Christof = new CodexActor(christofGUID);
			Christof.SetActorDisciplineLevel("EyesOfTheSerpent", -1);

			//String aFormat = "%A" + Christof.GetName() + "%g" + "DGRP_SERPENTIS";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));

			SetWeatherEffect("lightRain");

			if(humanityTrack == -1)
			{
				// big loss of humanity here
				_Christof.AddActorEffectByLevel("ef_decreasehumanity", 0, 3, 0, 0);
			}

			// auto advance
			CodexSequence.Advance(christofGUID);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b31_1_BarterConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Pink.OverrideActorWeapon("weapHeart", false, false);
							_lucretia.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							_lucretia.StopActorAction();
							_Pink.PlayMotionSetMode(MOTION_GESTURE7, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							_Pink.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, christofGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;
						
						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_LONG, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 5:

							// Move Lucretia off dias for transformation
							pos = _lucretia.GetFramePosition(1);
							_lucretia.SendActorToPos(pos, (float)90.0);

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 6);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 7:

							_Pink.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							break;

						case 8:

							_Pink.StopActorAction();
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 11:

							// Pink throws the heart here
							_Pink.PlayMotionSetMode(MOTION_SPECIAL9, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, pinkGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;

						case 12:

							// servant catches the heart...
							_Pink.CancelOverrideActorWeapon();
							CodexCamera.SetupCutscene(starterGuid, servantGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_servant.OverrideActorWeapon("weapCatchHeart", false, false);
							_servant.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							break;

						case 13:

							// ...walks away
							_servant.CancelOverrideActorWeapon();
							pos = _servant.GetFramePosition(1);
							_servant.SendActorToPos(pos, (float)90.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 14:

							_lucretia.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							_Pink.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, christofGUID, lucretiaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;
					}
			}
		}

		if(bAfterFight1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_LONG, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bHeartChoiceConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.OverrideActorWeapon("weapHeart", false, false);
							_Christof.PlayMotionSetMode(MOTION_SPECIAL24, false, (float)30.0);
							
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);

							// diablerie branch
							// christof drains the heart
							_Christof.PlayMotionSetMode(MOTION_SPECIAL12, false, (float)30.0);

							// add in here lowering of generation for choosing to diablerize lucretia's heart
							_Christof.AddActorEffectByLevel("ef_lowergeneration", 0, 0, 0, 0);

							humanityTrack = -1;

							// give big XP boost
							CodexPlayer christofPlayer = new CodexPlayer(christofGUID);
							//christofPlayer.AwardPlayerExperience(300);
							CodexPlayer.AwardPartyExperience(300);

							// increase bloodpool size
							_Christof.SetActorBaseStat(ACTOR_STAT_BLOODPOOL, (float)(_Christof.GetActorBaseStat(ACTOR_STAT_BLOODPOOL) * 1.5));
							_Christof.SetActorStat(ACTOR_STAT_BLOODPOOL, _Christof.GetActorBaseStat(ACTOR_STAT_BLOODPOOL));
							//float bloodpoolsize = _Christof.GetActorStat(ACTOR_STAT_BLOODPOOL);
							//_Christof.AddActorEffectByValue("ef_increasebloodpoolsize", 0, (float)(bloodpoolsize/2), 0, 0);
							break;

						case 1:

							// kill cobra
							CodexCamera.SetupCutscene(starterGuid, cobraGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							_Cobra.ClearActorFlags(THING_AF_INVUL);
							_Cobra.DamageActor(1000, DAMAGE_TYPE_NORMAL, 0);	
							break;

					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							// christof destroys the heart
							// Shred animation
							_Christof.PlayMotionSetMode(MOTION_SPECIAL11, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 1:

							// kill cobra
							CodexCamera.SetupCutscene(starterGuid, cobraGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							_Cobra.ClearActorFlags(THING_AF_INVUL);
							_Cobra.DamageActor(1000, DAMAGE_TYPE_NORMAL, 0);	
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)		
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(cobraGuid);
		CodexSequence.SaveInt(servantGUID);
		CodexSequence.SaveInt(frameServant);
	}

	public void restore(int flags)
	{
		cobraGuid = CodexSequence.RestoreInt();
		servantGUID = CodexSequence.RestoreInt();
		frameServant = CodexSequence.RestoreInt();
	}
}