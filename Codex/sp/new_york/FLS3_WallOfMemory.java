/**
 * Wall of Memory 40.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FLS3_WallOfMemory extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_FLESHSOUND		= 1;
	private static final int	TIMER_ID_MOVEHEADBACK	= 2;
	private static final int	TIMER_ID_CHRISFALL		= 3;
	private static final int	TIMER_ID_LILYFALL		= 4;
	private static final int	TIMER_ID_SAMUELFALL		= 5;
	private static final int	TIMER_ID_WILHEMFALL		= 6;
	private static final int	TIMER_ID_SHOWCHRIS		= 7;
	private static final int	TIMER_ID_SHOWLILY		= 8;
	private static final int	TIMER_ID_SHOWSAMUEL		= 9;
	private static final int	TIMER_ID_SHOWWILHEM		= 10;
	private static final int	TIMER_ID_STOPMOTION		= 11;
	private static final int	TIMER_ID_CAMERARELEASE	= 12;

	private CodexRegion		_wallRegion;
	private CodexActor		_Memory1;
	private CodexActor		_Memory2;
	private CodexActor		_Memory3;
	private CodexActor		_Memory4;
	private CodexActor		_Memory5;
	private CodexActor		_Memory6;
	private CodexActor		_Memory7;
	private CodexActor		_Memory8;

	private CodexActor		_Christof;
	private CodexActor		_Lily;
	private CodexActor		_Samuel;
	private CodexActor		_Wilhem;

	private int				christofGUID;
	private int				lilyGUID;
	private int				samuelGUID;
	private int				wilhemGUID;

	private int				currentWallGUID;
	private int				currentCaptureID;

	private	CodexThing		_IrisDoor;

	public boolean			bWall1Conversation = false;
	public boolean			bWall2Conversation = false;
	public boolean			bWall5Conversation = false;
	public boolean			bWall6Conversation = false;
	public boolean			bWall7Conversation = false;
	public boolean			bWall8Conversation = false;
	public boolean			bWall10Conversation = false;
	public boolean			bWall11Conversation = false;

	private int				MemoryConversationNumber = 0;

	public static String _params[] = {"Wall region", "Memory1 Statue", "Memory2 Statue", "Memory3 Statue", "Memory4 Statue", 
										"Memory5 Statue", "Memory6 Statue", "Memory7 Statue", "Memory8 Statue", 
										"IrisDoor"};

	public FLS3_WallOfMemory(CodexRegion wallRegion, CodexActor Memory1, CodexActor Memory2, CodexActor Memory3, CodexActor Memory4, 
							CodexActor Memory5, CodexActor Memory6, CodexActor Memory7, CodexActor Memory8, 
							CodexThing IrisDoor)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_wallRegion = new CodexRegion(wallRegion.GetGUID());
		_Memory1 = new CodexActor(Memory1.GetGUID());
		_Memory2 = new CodexActor(Memory2.GetGUID());
		_Memory3 = new CodexActor(Memory3.GetGUID());
		_Memory4 = new CodexActor(Memory4.GetGUID());
		_Memory5 = new CodexActor(Memory5.GetGUID());
		_Memory6 = new CodexActor(Memory6.GetGUID());
		_Memory7 = new CodexActor(Memory7.GetGUID());
		_Memory8 = new CodexActor(Memory8.GetGUID());

		_IrisDoor = new CodexThing(IrisDoor.GetGUID());

		CaptureThing(_wallRegion.GetGUID());
		CaptureThing(_Memory1.GetGUID(), 1);
		CaptureThing(_Memory2.GetGUID(), 2);
		CaptureThing(_Memory3.GetGUID(), 3);
		CaptureThing(_Memory4.GetGUID(), 4);
		CaptureThing(_Memory5.GetGUID(), 5);
		CaptureThing(_Memory6.GetGUID(), 6);
		CaptureThing(_Memory7.GetGUID(), 7);
		CaptureThing(_Memory8.GetGUID(), 8);

		CaptureThing(_IrisDoor.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_Christof = new CodexActor(christofGUID);
		_Lily = new CodexActor(lilyGUID);
		_Samuel = new CodexActor(samuelGUID);
		_Wilhem = new CodexActor(wilhemGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.FLS3_WALLINTRO))
		{
			// leftover from drop, add animations and camera shot here
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "FLS3Fall.ncp", 30);

			AIOn();

			// release the camera after they've all fallen
			SetTimer(5, TIMER_ID_CAMERARELEASE);

			if((_Christof.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				_Christof.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

				SetTimer((float)0.3, TIMER_ID_CHRISFALL);
			}

			if((_Lily.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				_Lily.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

				SetTimer((float)0.9, TIMER_ID_LILYFALL);
			}

			if((_Samuel.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				_Samuel.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

				SetTimer((float)1.5, TIMER_ID_SAMUELFALL);
			}

			if((_Wilhem.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				_Wilhem.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

				SetTimer((float)2.1, TIMER_ID_WILHEMFALL);
			}
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.FLS3_DOOROPEN))
		{
			_Memory1.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory2.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory3.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory4.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory5.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory6.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory7.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Memory8.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}


	public void entered(int guid, int causeGuid, int captureID)
	{
		if(guid == _wallRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.FLS3_WALLINTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.FLS3_WALLINTRO);

			CodexSound.PlayVoice(_wallRegion.GetGUID(), "MDVict1_40_4_1551", 100);

			CodexSound.PushMusic("Anezka_Theme.mp3", 50);
		}
	}
	
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FLESHSOUND:
				switch((int)arg0)
				{
					case 1:
						new CodexSound("wetFlesh_01.WAV", 300, 600, 100, 0, 0, (int)arg1); 
						break;

					case 2:
						new CodexSound("wetFlesh_02.WAV", 300, 600, 100, 0, 0, (int)arg1); 
						break;

					case 3:
						new CodexSound("wetFlesh_03.WAV", 300, 600, 100, 0, 0, (int)arg1); 
						break;

					case 4:
						new CodexSound("wetFlesh_04.WAV", 300, 600, 100, 0, 0, (int)arg1); 
						break;
				}
				break;

			case TIMER_ID_MOVEHEADBACK:

				new CodexSound("wetFlesh_02.WAV", 300, 600, 100, 0, 0, (int)arg0); 

				CodexActor head = new CodexActor(currentWallGUID);
				head.PlayActorMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
				break;

			case TIMER_ID_CHRISFALL:

				_Christof.PlayMotionSetMode(MOTION_SPECIAL22, false, (float)30.0);

				SetTimer((float)0.1, TIMER_ID_SHOWCHRIS);

				SetTimer((float)2.2, TIMER_ID_STOPMOTION, christofGUID);
				break;

			case TIMER_ID_LILYFALL:

				_Lily.PlayMotionSetMode(MOTION_SPECIAL22, false, (float)30.0);

				new CodexSound("Lily_Scream_6.wav", 500, 1000, 100, 0, 0, _Lily.GetGUID());

				SetTimer((float)0.1, TIMER_ID_SHOWLILY);

				SetTimer((float)2.2, TIMER_ID_STOPMOTION, lilyGUID);
				break;

			case TIMER_ID_SAMUELFALL:

				_Samuel.PlayMotionSetMode(MOTION_SPECIAL22, false, (float)30.0);

				new CodexSound("Samuel_FScream_2.wav", 500, 1000, 100, 0, 0, _Lily.GetGUID());

				SetTimer((float)0.1, TIMER_ID_SHOWSAMUEL);

				SetTimer((float)2.2, TIMER_ID_STOPMOTION, samuelGUID);
				break;
			
			case TIMER_ID_WILHEMFALL:

				_Wilhem.PlayMotionSetMode(MOTION_SPECIAL22, false, (float)30.0);

				SetTimer((float)0.1, TIMER_ID_SHOWWILHEM);
				//_Wilhem.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

				SetTimer((float)2.2, TIMER_ID_STOPMOTION, wilhemGUID);
				break;

			case TIMER_ID_SHOWCHRIS:

				_Christof.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				break;

			case TIMER_ID_SHOWLILY:

				_Lily.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				break;

			case TIMER_ID_SHOWSAMUEL:

				_Samuel.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				break;
			
			case TIMER_ID_SHOWWILHEM:

				_Wilhem.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				break;

			case TIMER_ID_STOPMOTION:

				CodexPlayer fallee = new CodexPlayer((int)arg0);

				fallee.StopActorAction();
				break;

			case TIMER_ID_CAMERARELEASE:

				CodexCamera.Release(CodexPlayer.GetCurrentPlayer());
				break;
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		currentWallGUID = guid;
		currentCaptureID = captureID;

		if(captureID <= 8 && captureID >= 1)
		{
			// these lines are played sequentially without regard to the order
			// the player clicks them 

			switch(MemoryConversationNumber)
			{
				case 0:
					Wall1Conversation(clickerGuid, 0);
					break;
				case 1:
					Wall2Conversation(clickerGuid, 0);
					break;
				case 2:
					Wall5Conversation(clickerGuid, 0);
					break;
				case 3:
					Wall6Conversation(clickerGuid, 0);
					break;
				case 4:
					Wall7Conversation(clickerGuid, 0);
					break;
				case 5:
					Wall8Conversation(clickerGuid, 0);
					break;
				case 6:
					Wall10Conversation(clickerGuid, 0);
					break;
				case 7:
					Wall11Conversation(clickerGuid, 0);
					break;
			}
			
			// after each text, add 1 to MemoryConversationNumber to prepare to play the next line
			MemoryConversationNumber++;

			switch(captureID)
			{
				case 1:
					_Memory1.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 1, _Memory1.GetGUID());

					_Memory1.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
					break;
				case 2:
					_Memory2.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 2, _Memory2.GetGUID());

					_Memory2.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
					break;
				case 3:
					_Memory3.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 3, _Memory3.GetGUID());

					_Memory3.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
					break;
				case 4:
					_Memory4.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 3, _Memory4.GetGUID());

					_Memory4.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 4);
					break;
				case 5:
					_Memory5.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 1, _Memory5.GetGUID());

					_Memory5.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 5);
					break;
				case 6:
					_Memory6.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 4, _Memory6.GetGUID());

					_Memory6.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 6);
					break;
				case 7:
					_Memory7.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 2, _Memory7.GetGUID());

					_Memory7.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 7);
					break;
				case 8:
					_Memory8.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
					SetTimer(2, TIMER_ID_FLESHSOUND, 3, _Memory8.GetGUID());

					_Memory8.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

					CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 8);
					break;
			}
		}
	}

	public void Wall1Conversation(int starterGuid, int npcGuid)
	{
		bWall1Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall1", "40_1_Wall1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall2Conversation(int starterGuid, int npcGuid)
	{
		bWall2Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall2", "40_1_Wall2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall5Conversation(int starterGuid, int npcGuid)
	{
		bWall5Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall5", "40_1_Wall5.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall6Conversation(int starterGuid, int npcGuid)
	{
		bWall6Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall6", "40_1_Wall6.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall7Conversation(int starterGuid, int npcGuid)
	{
		bWall7Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall7", "40_1_Wall7.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall8Conversation(int starterGuid, int npcGuid)
	{
		bWall8Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall8", "40_1_Wall8.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall10Conversation(int starterGuid, int npcGuid)
	{
		bWall10Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall10", "40_1_Wall10.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Wall11Conversation(int starterGuid, int npcGuid)
	{
		bWall11Conversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "40_1_Wall11", "40_1_Wall11.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		// move the head that was clicked back into the wall
		SetTimer(1, TIMER_ID_MOVEHEADBACK, starterGuid);

		if(bWall1Conversation)
		{
			AIOn();
			bWall1Conversation = false;
			CodexCamera.Release(0);
		}

		if(bWall2Conversation)
		{
			AIOn();
			bWall2Conversation = false;
			CodexCamera.Release(starterGuid);
		}
		
		if(bWall5Conversation)
		{
			AIOn();
			bWall5Conversation = false;
			CodexCamera.Release(starterGuid);
		}
		
		if(bWall6Conversation)
		{
			AIOn();
			bWall6Conversation = false;
			CodexCamera.Release(starterGuid);
		}
		
		if(bWall7Conversation)
		{
			AIOn();
			bWall7Conversation = false;
			CodexCamera.Release(starterGuid);
		}
		
		if(bWall8Conversation)
		{
			AIOn();
			bWall8Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bWall10Conversation)
		{
			AIOn();
			bWall10Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bWall11Conversation)
		{
			AIOn();
			bWall11Conversation = false;
			CodexCamera.Release(starterGuid);

			CodexSound.PopMusic();

			if(!CodexSequence.GetChronicleFlag(chronScript.FLS3_DOOROPEN))
			{
				CodexSequence.SetChronicleFlag(chronScript.FLS3_DOOROPEN);

				// open iris door
				_IrisDoor.Trigger(0, 0, 0, 0, 0, 0);
			}
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bWall1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bWall2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
		
		if(bWall5Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
				case 1:
					switch(curLine)
					{
						case 0:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
		
		if(bWall6Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
		
		if(bWall7Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bWall8Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bWall10Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bWall11Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							// delay for head to come out
							PlayAnezkaPath();
							break;

						case 1:
							CodexActor head = new CodexActor(currentWallGUID);
							head.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void PlayAnezkaPath()
	{
		switch(currentCaptureID)
		{
			case 1:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall1.ncp", 20);
				break;
			case 2:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall2.ncp", 20);
				break;
			case 3:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall3.ncp", 20);
				break;
			case 4:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall4.ncp", 20);
				break;
			case 5:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall5.ncp", 20);
				break;
			case 6:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall6.ncp", 20);
				break;
			case 7:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall7.ncp", 20);
				break;
			case 8:
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "wall8.ncp", 20);
				break;
		}	
	}
}

