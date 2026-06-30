/**
 * Teutonic Knight Base 4 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TEU4_22_1_Main extends Codex
{
	private ViennaChronicle	chronScript;

	private CodexRegion		_KnightRegion;
	private CodexActor		_Knight1;
	private CodexActor		_Knight2;
	private CodexActor		_Orsi;
	private CodexActor		_Corpse;
	private CodexThing		_Switch;

	private CodexActor		_serena;
	private CodexActor		_christof;
	private CodexActor		_wilhem;
	private CodexActor		_erik;

	private int				christofGUID;
	private int				erikGUID;
	private int				orsiGUID;
	private int				serenaGUID;
	private int				wilhemGUID;

	private float[]			pos;
	private float[]			corpsePos;
	public int				bOrsiPos;

	public boolean			b22_1_OrsiConversation = false;
	public boolean			b22_1_SerenaCorpseConversation = false;
	public boolean			bTeutonicConversation = false;
	public boolean			b22_1_FreedomConversation = false;

	private static final int TIMER_ID_SERENACORPSE = 1;
	private static final int TIMER_ID_CORPSEGETUP = 2;
	private static final int TIMER_ID_CORPSEWALK = 3;
	private static final int TIMER_ID_CORPSESWITCH = 4;
	private static final int TIMER_ID_DOOROPEN = 5;
	private static final int TIMER_ID_CORPSECOLAPSE = 6;
	private static final int TIMER_ID_FREEDOMCONV = 7;
	private static final int TIMER_ID_FADEOUT = 8;
	private static final int TIMER_ID_FADEIN = 9;


	public static String _params[] =	{"Teutonic Knight region", "Teutonic Knight 1", "Teutonic Knight 2", "Orsi", "Corpse", "Switch"};

	public TEU4_22_1_Main(CodexRegion KnightRegion, CodexActor Knight1, CodexActor Knight2, CodexActor Orsi, CodexActor Corpse, CodexThing Switch)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_KnightRegion = new CodexRegion(KnightRegion.GetGUID());
		_Knight1 = new CodexActor(Knight1.GetGUID());
		_Knight2 = new CodexActor(Knight2.GetGUID());
		_Orsi = new CodexActor(Orsi.GetGUID());
		_Corpse = new CodexActor(Corpse.GetGUID());
		_Switch = new CodexThing(Switch.GetGUID());

		CaptureThing(_KnightRegion.GetGUID());
		CaptureThing(_Knight1.GetGUID());
		CaptureThing(_Knight2.GetGUID());
		CaptureThing(_Orsi.GetGUID());
		CaptureThing(_Corpse.GetGUID());

		pos = new float[3];
		corpsePos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_Corpse.SetActorFlags(THING_AF_AIPAUSED);

		if(!CodexSequence.GetChronicleFlag(chronScript.TEU4_ORSIFAREWELL))
		{
			christofGUID = CodexThing.GuidFromCastID("Christof");
			erikGUID = CodexThing.GuidFromCastID("Erik");
			orsiGUID = CodexThing.GuidFromCastID("Orsi");
			serenaGUID = CodexThing.GuidFromCastID("Serena");
			wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

			_serena = new CodexActor(serenaGUID);
			_christof = new CodexActor(christofGUID);
			_wilhem = new CodexActor(wilhemGUID);
			_erik = new CodexActor(erikGUID);


			_Corpse.PlayActorMotionSetMode(MOTION_SPECIAL2, false, 30f);
			
			CodexSequence.SetChronicleFlag(chronScript.TEU4_ORSIFAREWELL);

			c22_1_OrsiConversation(clientGuid, 0);
		}

	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _KnightRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.TEU4_KNIGHTREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.TEU4_KNIGHTREGION);

			TeutonicConversation(causeGUID, 0);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _Orsi.GetGUID())
		{
			if(bOrsiPos < (_Orsi.GetNumFrames() - 1))
			{
				bOrsiPos++;
				pos = _Orsi.GetFramePosition(bOrsiPos);

				_Orsi.SendActorToPos(pos, (float)90.0);

			}
		}
		else if(thingGuid == _Corpse.GetGUID())
		{
			// new camera shot?
			_Corpse.LookAtThing(_Switch.GetGUID());
			int duration = _Corpse.PlayActorMotionSetMode(MOTION_PUNCH, false, 30f);
			SetTimer((float) duration / 1800, TIMER_ID_CORPSESWITCH);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		int duration;

		switch(timerID)
		{
			case TIMER_ID_FADEOUT:
				SetTimer(2, TIMER_ID_SERENACORPSE);
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)4.0, true);
				break;

			case TIMER_ID_SERENACORPSE:
				_Orsi.Remove();
				c22_1_SerenaCorpseConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_CORPSEGETUP:
				BeginCutscene(CodexPlayer.GetCurrentPlayer(), 0);

				float[]	offset = new float[3];

				offset[0] = 0; offset[1] = 0; offset[2] = 0;

				int effectGuid = _Corpse.SpawnThing("neckBlood");
				_Corpse.AttachThing(effectGuid, 17, offset, ATTACH_FLAG_AUTOREMOVE);

//				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CorpseGetUp.ncp", 30);
				duration = _Corpse.PlayActorMotionSetMode(MOTION_SPECIAL1, false, 30f);
				SetTimer((float) duration / 1000, TIMER_ID_CORPSEWALK);
				break;

			case TIMER_ID_CORPSEWALK:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CorpseWalk.ncp", 30);
				corpsePos = _Corpse.GetFramePosition(1);
				_Corpse.StopActorAction();
				_Corpse.SendActorToPos(corpsePos, (float)30.0);
				break;

			case TIMER_ID_CORPSESWITCH:
				// trigger switch
				_Corpse.ActorActionActivate(_Switch.GetGUID(), 0);
				SetTimer(1, TIMER_ID_DOOROPEN);
				break;

			case TIMER_ID_DOOROPEN:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "DoorOpen.ncp", 30);
				SetTimer(3, TIMER_ID_CORPSECOLAPSE);
				break;

			case TIMER_ID_CORPSECOLAPSE:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CorpseWalk.ncp", 30);
				duration = _Corpse.PlayActorMotionSetMode(MOTION_DEATHSLOW, false, 20f);
				SetTimer((float) duration / 1000, TIMER_ID_FREEDOMCONV);
				break;

			case TIMER_ID_FREEDOMCONV:
				_Corpse.SetActorFlags(THING_AF_DEAD);
				c22_1_FreedomConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

		}
	}

	public void c22_1_OrsiConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
		b22_1_OrsiConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "22_1_Orsi", "22_1_Orsi.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c22_1_SerenaCorpseConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		b22_1_SerenaCorpseConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "22_1_SerenaCorpse", "22_1_SerenaCorpse.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c22_1_FreedomConversation(int starterGuid, int npcGuid)
	{
		b22_1_FreedomConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "22_1_Freedom", "22_1_Freedom.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void TeutonicConversation(int starterGuid, int npcGuid)
	{
		bTeutonicConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "22_1_Teutonic", "22_1_Teutonic.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b22_1_OrsiConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b22_1_OrsiConversation = false;
//			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// open link to DK
			CodexSequence.OpenExit("InnerStradt", 3);

			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_EscapeKnightBase"));

			OrsiLeaves();
		}

		if(b22_1_SerenaCorpseConversation)
		{
			AIOn();
			b22_1_SerenaCorpseConversation = false;
//			CodexCamera.Release(starterGuid);
			SetTimer(1, TIMER_ID_CORPSEGETUP);
		}

		if(b22_1_FreedomConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b22_1_FreedomConversation = false;
			CodexCamera.Release(starterGuid);

			// open up obfuscate for christof, dominate for wilhem and clerity for erik
			CodexActor Christof = new CodexActor(christofGUID);
			CodexActor Wilhem = new CodexActor(CodexThing.GuidFromCastID("Wilhem"));
			CodexActor Erik = new CodexActor(CodexThing.GuidFromCastID("Erik"));
			Christof.SetActorDisciplineLevel("CloakOfShadows", -1);
			Wilhem.SetActorDisciplineLevel("Command", -1);
			Erik.SetActorDisciplineLevel("Celerity", -1);

			//String aFormat = "%A" + Christof.GetName() + "%g" + "DGRP_OBFUSCATE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat));
 
			//String aFormat2 = "%A" + Wilhem.GetName() + "%g" + "DGRP_DOMINATE";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat2));
 
			//String aFormat3 = "%A" + Erik.GetName() + "%g" + "DGRP_CELERITY";
			//CodexConsole.Print(CodexPlayer.GetCurrentPlayer(), 0, FormatNLS("RPG_GROUPS", aFormat3));
			
			CodexSequence.Advance(christofGUID);
		}

		if(bTeutonicConversation)
		{
			AIOn();
			bTeutonicConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b22_1_OrsiConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, orsiGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b22_1_SerenaCorpseConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, erikGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, serenaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_christof.LookAtThing(serenaGUID);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:
							
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "SerenaCast.ncp", 30);


							_serena.LookAtThing(_Corpse.GetGUID());
							_christof.LookAtThing(_Corpse.GetGUID());
							_wilhem.LookAtThing(_Corpse.GetGUID());
							_erik.LookAtThing(_Corpse.GetGUID());

							_serena.PlayActorMotionSetMode(MOTION_SPELLTHROW, false, 30f);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b22_1_FreedomConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, erikGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bTeutonicConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 1:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void OrsiLeaves()
	{
		SetTimer(1, TIMER_ID_FADEOUT);
		pos = _Orsi.GetFramePosition(1);
		bOrsiPos = 1;

		_Orsi.SendActorToPos(pos, (float)90.0);
	}
}


