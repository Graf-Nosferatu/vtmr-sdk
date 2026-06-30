/**
 * London West 28.4 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WEST_28_4_Main extends Codex
{
	private LondonChronicle	chronScript;

	private CodexActor	_Christof;

	private int			christofGUID;
	private int			pinkGUID;

	public boolean		b28_4_PinkModernConversation = false;
	public boolean		b28_4_AfterFirstCurioConversation = false;

	public static String _params[] =	{};

	public WEST_28_4_Main()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);		
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		pinkGUID = CodexThing.GuidFromCastID("Pink");

		_Christof = new CodexActor(christofGUID);
		
		if(!CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.WEST_PINKMODERN))
		{
			CodexSequence.SetChronicleFlag(chronScript.WEST_PINKMODERN);

			c28_4_PinkModernConversation(clientGuid, 0);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.CURI_SUMNER_TALKEDONCE) &&
			!CodexSequence.GetChronicleFlag(chronScript.WEST_AFTERCURIO))
		{
			CodexSequence.SetChronicleFlag(chronScript.WEST_AFTERCURIO);

			c28_4_AfterFirstCurioConversation(clientGuid, 0);
		}
	}

	public void c28_4_PinkModernConversation(int starterGuid, int npcGuid)
	{
		b28_4_PinkModernConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "28_4_PinkModern", "28_4_PinkModern.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c28_4_AfterFirstCurioConversation(int starterGuid, int npcGuid)
	{
		b28_4_AfterFirstCurioConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "28_4_AfterFirstCurio", "28_4_AfterFirstCurio.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b28_4_PinkModernConversation)
		{
			AIOn();
			b28_4_PinkModernConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(b28_4_AfterFirstCurioConversation)
		{
			AIOn();
			b28_4_AfterFirstCurioConversation = false;
			_Christof.StopActorAction();
			_Christof.CancelOverrideActorWeapon();
			CodexCamera.Release(starterGuid);
		}	
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b28_4_PinkModernConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "PinkModern.ncp", 30);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 1:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;

				case 2:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
			} // switch(curEvent)
		}

		if(b28_4_AfterFirstCurioConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.OverrideActorWeapon("weapLocket", false, false);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, pinkGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							_Christof.PlayMotionSetMode(MOTION_SPECIAL23, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

}
