/**
 *  SEW3_Main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 *
*/

public class SEW3_Main extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_CONVSPOT0		= 0;
	private static final int	TIMER_ID_CONVSPOT1		= 1;
	private static final int	TIMER_ID_FADEIN			= 2;

	private CodexThing			_Skiff;
	private CodexActor			_AlligatorSwim;
	private CodexActor			_AlligatorWalk;

	private CodexActor			_Pink;
	private CodexActor			_Samuel;
	private CodexActor			_Lily;
	private CodexActor			_Christof;

	float[]						offset = new float[3];
	private float[]				pos = new float[3];

	private int					christofGUID;
	private int					lilyGUID;
	private int					pinkGUID;
	private int					samuelGUID;

	private int					alligatorFrame = 1;
	
	private boolean				bOnGatorSide = false;
	private boolean				bGatorSceneDone = false;

	private boolean				bLakeConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Lake region", "Skiff", "AlligatorSwim"};

	public SEW3_Main(CodexRegion LakeRegion, CodexThing Skiff, CodexActor AlligatorSwim)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Skiff = new CodexThing(Skiff.GetGUID());
		_AlligatorSwim = new CodexActor(AlligatorSwim.GetGUID());

		CaptureThing(_Skiff.GetGUID());
		CaptureThing(_AlligatorSwim.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");

		_Christof = new CodexActor(christofGUID);
		_Lily = new CodexActor(lilyGUID);
		_Pink = new CodexActor(pinkGUID);
		_Samuel = new CodexActor(samuelGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.SEW3_LAKESCENE))
		{
			// make him so player can't target him too early
			_AlligatorSwim.SetCollideType(THING_COLLIDE_NONE);
			_AlligatorSwim.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_AlligatorSwim.SetActorFlags(THING_AF_AIPAUSED);
		}

		_Skiff.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Skiff.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SEW3_LAKESCENE))
		{
			CodexSequence.SetChronicleFlag(chronScript.SEW3_LAKESCENE);

			CodexSound.PushMusic("MD_Conversation_2.mp3", 50);

			_Skiff.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			///////////////////////////////////////////////////////////////////////
			// remove control from the player here
			///////////////////////////////////////////////////////////////////////

			CodexActor		player;

			AIOff();

			// play camera shot here that watches them floating across the lake
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "SewerLakeSkiff.ncp", 45);

			// offset always in z whatever half thickness of skiff is
			// X and Y will be used to distribute the party on the skiff
			offset[0] = 64; 
			offset[1] = 64; 
			offset[2] = 60;

			player = new CodexActor(christofGUID);
			player.Stop();
			// look toward their destination
			player.LookAtPos(_Skiff.GetFramePosition(7));
			_Skiff.AttachThing(christofGUID, -1, offset, 0);

			// offset always in z whatever half thickness of skiff is
			// X and Y will be used to distribute the party on the skiff
			offset[0] = -64; 
			offset[1] = 64; 
			offset[2] = 60;

			player = new CodexActor(lilyGUID);
			player.Stop();
			// look toward their destination
			player.LookAtPos(_Skiff.GetFramePosition(7));
			_Skiff.AttachThing(lilyGUID, -1, offset, 0);
			
			// offset always in z whatever half thickness of skiff is
			// X and Y will be used to distribute the party on the skiff
			offset[0] = 64; 
			offset[1] = -64; 
			offset[2] = 60;

			player = new CodexActor(pinkGUID);
			player.Stop();
			// look toward their destination
			player.LookAtPos(_Skiff.GetFramePosition(7));
			_Skiff.AttachThing(pinkGUID, -1, offset, 0);

			// offset always in z whatever half thickness of skiff is
			// X and Y will be used to distribute the party on the skiff
			offset[0] = -64; 
			offset[1] = -64; 
			offset[2] = 60;

			player = new CodexActor(samuelGUID);
			player.Stop();
			// look toward their destination
			player.LookAtPos(_Skiff.GetFramePosition(7));
			_Skiff.AttachThing(samuelGUID, -1, offset, 0);

			// send the skiff off
			_Skiff.MoveToFrame(1, 45);

			// make the players look like they're doing something on the skiff
			_Pink.PlayMotionSetMode(MOTION_TALK, false, (float)20.0);
			_Lily.PlayMotionSetMode(MOTION_TALK, false, (float)20.0);
			_Samuel.PlayMotionSetMode(MOTION_TALK, false, (float)20.0);
			_Christof.PlayMotionSetMode(MOTION_ACTION2, false, (float)20.0);
		}
		else if(CodexSequence.GetChronicleFlag(chronScript.SEW3_LAKESCENE))
		{
			if(guid == _Skiff.GetGUID())
			{
				// Fade out
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)1, false);

				if(bOnGatorSide)
				{
					SetTimer((float)2, TIMER_ID_CONVSPOT1);
				}
				else
				{
					SetTimer((float)2, TIMER_ID_CONVSPOT0);
				}
			}
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_CONVSPOT0:

				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
				_Skiff.SetPosition(_Skiff.GetFramePosition(8));
				bOnGatorSide = true;

				SetTimer((float)0.5, TIMER_ID_FADEIN);
				break;

			case TIMER_ID_CONVSPOT1:

				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
				_Skiff.SetPosition(_Skiff.GetFramePosition(0));
				bOnGatorSide = false;

				SetTimer((float)0.5, TIMER_ID_FADEIN);
				break;

			case TIMER_ID_FADEIN:

				// Fade in
				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1, false);
				break;
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(bGatorSceneDone)
			return;

		if(thingGuid == _Skiff.GetGUID())
		{
			new CodexSound("dirgePaddle_01.WAV", 1000, 1500, 100, 0, 0, _Skiff.GetGUID());

			if(frameNum + 1 == _Skiff.GetNumFrames())
			{
				_Skiff.DetachThing(christofGUID);
				_Skiff.DetachThing(lilyGUID);
				_Skiff.DetachThing(pinkGUID);
				_Skiff.DetachThing(samuelGUID);

				LakeConversation(thingGuid, 0);

				_AlligatorWalk = new CodexActor(_AlligatorSwim.SpawnThing("alligator"));
				_AlligatorSwim.Remove();

				_Pink.StopActorAction();
				_Samuel.StopActorAction();
				_Lily.StopActorAction();
				_Christof.StopActorAction();
			}
			else
			{
				_Skiff.MoveToFrame(frameNum + 1, 45);
			}

			if(frameNum == 4)
			{
				// alligator swim sequence
				_AlligatorSwim.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				pos = _AlligatorSwim.GetFramePosition(alligatorFrame);
				_AlligatorSwim.SendActorToPos(pos, (float)70.0);

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "SewerLakeAlligator.ncp", 45);
			}
		}
		else if(thingGuid == _AlligatorSwim.GetGUID())
		{
			new CodexSound("dirgePaddle_03.WAV", 1000, 1500, 100, 0, 0, _Skiff.GetGUID());

			alligatorFrame++;

			if(alligatorFrame < _AlligatorSwim.GetNumFrames())
			{
				pos = _AlligatorSwim.GetFramePosition(alligatorFrame);
				_AlligatorSwim.SendActorToPos(pos, (float)70.0);
			}
		}
	}
	
	public void LakeConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bLakeConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "35_1_Lake", "35_1_Lake.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		CodexSound.PopMusic();
		bLakeConversation = false;
		CodexCamera.Release(starterGuid);
		AIOn();

		// he's reached the edge of the water, sick him on the player
		_AlligatorWalk.AISetTarget(CodexPlayer.GetCurrentPlayer());

		_Skiff.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		bOnGatorSide = true;
		bGatorSceneDone = true;
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
						break;

					case 1:

						//CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
						//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
