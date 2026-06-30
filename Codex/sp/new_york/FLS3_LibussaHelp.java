/**
 * Libussa Help 40.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FLS3_LibussaHelp extends Codex
{
	private NewYorkChronicle	chronScript;

	//****************************************************************************
	// FLS3_LIBUSSAHELP IS CHECKED IN FLS1_Main TO PLAY THE FINAL CUTSCENE/FIGHT
	//****************************************************************************

	private static final int TIMER_ID_HEARTDOOR		= 1;

	private CodexActor		_Libussa;
	private CodexActor		_Christof;
	private CodexThing		_HeartDoor;

	private int				christofGUID;
	private int				libussaGUID;

	private boolean			bLibussaHelpConversation	= false;
	private boolean			bLibussaGoConversation	= false;

	public static String _params[] = {"Libussa", "Door"};

	public FLS3_LibussaHelp(CodexThing Libussa, CodexThing HeartDoor)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Libussa = new CodexActor(Libussa.GetGUID());
		_HeartDoor = new CodexThing(HeartDoor.GetGUID());

		CaptureThing(_Libussa.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		libussaGUID = _Libussa.GetGUID();

		_Christof = new CodexActor(christofGUID);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Libussa.GetGUID())
		{
			CodexSequence.SetChronicleFlag(chronScript.FLS3_LIBUSSAHELP);

			LibussaHelpConversation(clickerGuid, 0);
		}
	}
	
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_HEARTDOOR:
				// hides door into heart room and makes it no collide
				_HeartDoor.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_HeartDoor.SetCollideType(THING_COLLIDE_NONE);
				break;
		}
	}		

	public void LibussaHelpConversation(int starterGuid, int npcGuid)
	{
		bLibussaHelpConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "40_1_LibussaHelp", "40_1_LibussaHelp.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LibussaGoConversation(int starterGuid, int npcGuid)
	{
		bLibussaGoConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "40_1_LibussaGo", "40_1_LibussaGo.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bLibussaHelpConversation)
		{
			bLibussaHelpConversation = false;

			if(returnValue == 1)
			{
				LibussaGoConversation(christofGUID, 0);
	
				_HeartDoor.SetShell("red", 0x80, .8f, 0, 5, 1);
				SetTimer(5, TIMER_ID_HEARTDOOR);
			}
			else
			{
				AIOn();
				CodexCamera.Release(starterGuid);
			}
		}
		else if(bLibussaGoConversation)
		{
			AIOn();
			bLibussaGoConversation = false;
			CodexCamera.Release(starterGuid);
			
			_Libussa.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// open the exit from 1 down to 3
			CodexSequence.OpenExit("CathedralOfFlesh1", 2);
		}

	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bLibussaHelpConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.LookAtThing(libussaGUID);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, libussaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;
			} // switch(curEvent)
		}

		if(bLibussaGoConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, libussaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 6);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

