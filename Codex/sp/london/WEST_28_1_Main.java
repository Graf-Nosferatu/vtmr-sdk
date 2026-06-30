/**
 * London West 28.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WEST_28_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	public CodexRegion		_muggerRegion;
	public CodexActor		_muggerActor;
	private CodexActor		_Christof;

	private int				christofGUID;
	private int				muggerGUID;

	private float[]			pos;

	public boolean		b28_1_LondonIntroConversation = false;
	public boolean		b28_1_MuggerConversation = false;

	public static String _params[] =	{"Mugger", "Mugger Region"};

	public WEST_28_1_Main(CodexActor muggerActor, CodexRegion muggerRegion)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_muggerActor = new CodexActor(muggerActor.GetGUID());
		_muggerRegion = new CodexRegion(muggerRegion.GetGUID());

		pos = new float[3];

		CaptureThing(_muggerActor.GetGUID());
		CaptureThing(_muggerRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		muggerGUID = CodexThing.GuidFromCastID("Mugger");

		_Christof = new CodexActor(christofGUID);
		
		if(!CodexSequence.GetChronicleFlag(chronScript.WEST_LONDONINTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.WEST_LONDONINTRO);

			c28_1_LondonIntroConversation(clientGuid, 0);
			SetWeatherEffect("drizzle");
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.WEST_MUGGERREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.WEST_MUGGERREGION);

			// mugger scene here
			c28_1_MuggerConversation(causeGUID, 0);
		}
	}

	public void c28_1_LondonIntroConversation(int starterGuid, int npcGuid)
	{
		b28_1_LondonIntroConversation = true;
		AIOff();
		//CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_1_LondonIntro", "28_1_LondonIntro.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c28_1_MuggerConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_2.mp3", 50);
		b28_1_MuggerConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_1_Mugger", "28_1_Mugger.nco", CONV_XFLAG_WANTFEEDBACK);
	}


	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b28_1_LondonIntroConversation)
		{
			AIOn();
			b28_1_LondonIntroConversation = false;
			CodexCamera.Release(starterGuid);
		}

		// modern day garb switch somewhere in here
		if(b28_1_MuggerConversation)
		{
			CodexSound.PopMusic();
			CodexPlayer player = new CodexPlayer(CodexThing.GuidFromCastID("Christof"));
			player.SetModel("christofMod.nod");
			player.SetPlayerHeadModel("christofModH.nod");

			// In case of space-thru, clear effect then fade in
			CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1.0, false);

			AIOn();
			b28_1_MuggerConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b28_1_LondonIntroConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b28_1_MuggerConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.LookAtThing(muggerGUID);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, muggerGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, muggerGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, muggerGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, muggerGUID, 0);
							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Mugger.ncp", 30);
							_Christof.SendActorToPos(_muggerActor.GetFramePosition(2), (float)100);
							break;

						case 6:
							
							_muggerActor.SendActorToPos(_muggerActor.GetFramePosition(2), (float)210);
							break;

						case 7:

							_muggerActor.Remove();
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							// fade out
							CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
							break;

					} // switch(curLine)
					break;
			}
		} // switch(curEvent)
	}
}

