/**
 * Cathedral of Flesh 1 40.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FLS1_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int TIMER_ID_VUKFULLSHOT	= 0;
	private static final int TIMER_ID_VUKCHANGESFX  = 1;
	private static final int TIMER_ID_VUKHEADSHOT	= 2;
	private static final int TIMER_ID_VUKSCREAM		= 3;
	private static final int TIMER_ID_PARTYREACTION = 4;
	private static final int TIMER_ID_SWAPMODELS	= 5;
	private static final int TIMER_ID_ZULOHEADSHOT	= 6;
	private static final int TIMER_ID_ZULOFULLSHOT	= 7;
	private static final int TIMER_ID_RELEASE		= 8;

	private CodexRegion		_VukodlakRegion;
	private CodexActor		_Christof;
	private CodexActor		_Vukodlak;
	private CodexActor		_VukStunt;
	private CodexActor		_ZuloStunt;
	private CodexActor		_Zulo;
	private CodexThing		_Vozhd;
	private	CodexThing		_IrisDoor;

	private int				_zuloGuid;

	private int				christofGUID;
	private int				vukodlakGUID;

	private boolean			bFinalVukConversation = false;

	public static String _params[] = {"Vukodlak", "Vukodlak Region","Vozhd to kill to open door", "Iris door"};

	public FLS1_Main(CodexActor Vukodlak, CodexRegion VukodlakRegion, CodexThing Vozhd, CodexThing IrisDoor)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Vukodlak = new CodexActor(Vukodlak.GetGUID());
		_VukodlakRegion = new CodexRegion(VukodlakRegion.GetGUID());
		_Vozhd = new CodexThing(Vozhd.GetGUID());
		_IrisDoor = new CodexThing(IrisDoor.GetGUID());
		
		CaptureThing(_Vukodlak.GetGUID());
		CaptureThing(_VukodlakRegion.GetGUID());
		CaptureThing(_Vozhd.GetGUID());
		CaptureThing(_IrisDoor.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		vukodlakGUID = _Vukodlak.GetGUID();

		_Christof = new CodexActor(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.FLS3_LIBUSSAHELP))
		{
			_Vukodlak.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Vukodlak.SetCollideType(THING_COLLIDE_NONE);
			_Vukodlak.SetActorFlags(THING_AF_AIPAUSED);
		}
		else
		{
			_Vukodlak.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Vukodlak.SetCollideType(THING_COLLIDE_CYL);
			_Vukodlak.ClearActorFlags(THING_AF_AIPAUSED);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.FLS1_INTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.FLS1_INTRO);

			// close the exit from 1 down to 3
			CodexSequence.CloseExit("CathedralOfFlesh1", 2);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_VUKFULLSHOT:

				_VukStunt.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
				SetTimer((float)6.0, TIMER_ID_VUKCHANGESFX);
				SetTimer((float)12.0, TIMER_ID_VUKHEADSHOT);
				break;

			case TIMER_ID_VUKCHANGESFX:

				CodexSound snd1 = new CodexSound("vukodlakTransform_01.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _VukStunt.GetGUID());
				break;

			case TIMER_ID_VUKHEADSHOT:

				// closeup on Vukodlak's head as it elongates
				SetTimer(0, TIMER_ID_VUKCHANGESFX);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VukHeadShot.ncp", 20);
				SetTimer((float)2.5, TIMER_ID_VUKSCREAM);
				SetTimer((float)4.0, TIMER_ID_PARTYREACTION);
				break;

			case TIMER_ID_VUKSCREAM:

				CodexSound snd3 = new CodexSound("zulo_roar_01.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _VukStunt.GetGUID());
				break;

			case TIMER_ID_PARTYREACTION:

				// the party is duly horrified
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "FleshPartyReaction.ncp", 30);
				CodexSound.PlayVoice(christofGUID, "Christof_39_1_1487", 100);
				_Christof.PlayMotionSetMode(MOTION_ACTION8, false, (float)30.0);
				SetTimer((float)0.3, TIMER_ID_SWAPMODELS);
				break;

			case TIMER_ID_SWAPMODELS:

				// remove VukStunt, spawn ZuloStunt in his spot
				_Christof.StopActorAction();
				_ZuloStunt = new CodexActor(_VukStunt.SpawnThing("ZuloTransform"));
				CaptureThing(_ZuloStunt.GetGUID());

				_VukStunt.Remove();

				SetTimer((float)0.0, TIMER_ID_ZULOHEADSHOT);
				break;

			case TIMER_ID_ZULOHEADSHOT:

				// closeup on Zulo's head as it elongates some more
				CodexSound snd4 = new CodexSound("vukodlakTransform_01.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _ZuloStunt.GetGUID());
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ZuloFullShot.ncp", 30);
				_ZuloStunt.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
				SetTimer((float)1.0, TIMER_ID_ZULOFULLSHOT);
				break;

			case TIMER_ID_ZULOFULLSHOT:

				// full shot of Zulo stretching its wings
				CodexSound snd5 = new CodexSound("zulo_roar_02.wav", (float)2048.0, (float)4096.0, 100, 0, 0, _ZuloStunt.GetGUID());
				SetTimer((float)4.0, TIMER_ID_RELEASE);
				break;

			case TIMER_ID_RELEASE:

				// remove ZuloStunt, spawn Zulo in his spot
				_Zulo = new CodexActor(_ZuloStunt.SpawnThing("Zulo"));
				_zuloGuid = _Zulo.GetGUID();
				CaptureThing(_Zulo.GetGUID());

				_ZuloStunt.Remove();
				
				AIOn();
				CodexCamera.Release(0);
				break;
		}
	}
	
	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!IsPlayerGuid(causeGuid))
			return;

		if(guid == _VukodlakRegion.GetGUID() &&
			CodexSequence.GetChronicleFlag(chronScript.FLS3_LIBUSSAHELP) &&
			!CodexSequence.GetChronicleFlag(chronScript.FLS1_VUK))
		{
			CodexSequence.SetChronicleFlag(chronScript.FLS1_VUK);

			FinalVukConversation(causeGuid, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Vozhd.GetGUID())
		{
			// open door to flesh2
			_IrisDoor.Trigger(0, 0, 0, 0, 0, 0);
		}

		if(guid == _zuloGuid)
		{
			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_DestroyVukodlak"));
			q.Complete();

			// instant fade out to cover game coming back to gameplay mode before the credits
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.0, false);

			// play "good end" cut scene here
			// play ending cinematic
			PlayVideo("Finale1.bik"); 
		}
	}

	public void videoended(int id)
	{
		// roll credits, back to main menu, etc.
		CodexSequence.EndGame();
	}

	public void FinalVukConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("RedemptionTheme_Modern.mp3", 50);
		bFinalVukConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "40_1_Zulu", "40_1_Zulu.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		bFinalVukConversation = false;

		// change Vuk into Zulo shape here

		_VukStunt = new CodexActor(_Vukodlak.SpawnThing("VukodlakTransform"));
		CaptureThing(_VukStunt.GetGUID());

		_Vukodlak.Remove();
		
		CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VukFullShot.ncp", 30);

		SetTimer(0, TIMER_ID_VUKFULLSHOT);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, vukodlakGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 15);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}

	public void save(int flags)
	{
	   CodexSequence.SaveInt(_zuloGuid);
	}

	public void restore(int flags)
	{
	   _zuloGuid = CodexSequence.RestoreInt();
	}
}

