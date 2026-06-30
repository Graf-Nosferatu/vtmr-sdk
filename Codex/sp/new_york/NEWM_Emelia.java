/**
 * NEWM_Emelia script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class NEWM_Emelia extends Codex
{
	private NewYorkChronicle	chronScript;

	private int					ownerGuid = 0;
	private CodexActor			owner;

	private int					christofGUID;
	private int					emeliaGUID;
	private int					pinkGUID;

	private String				descriptionID			= "NEWMOON";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;
	private	int					numItems				= 20;
	private	int					treasureClass0			= 50;
	private	int					treasureClass1			= 51;
	private	int					treasureClass2			= 52;
	private	int					treasureClass3			= 0;
	private	int					treasureClass4			= 0;

	private static String		greetingLine[]			= {"Emelia_BlessHello.wav"};
	private static final int	numGreetingLines		= 1;
	private static String		byeLine[]				= {"Emelia_BlessBye.wav"};
	private static final int	numByeLines				= 1;

	//////////////////////////////////////////////////

	private boolean				bActive					= false;

	private boolean				bEmelia1Conversation = false;
	private boolean				bMeetEmeliaNoPinkConversation = false;
	private boolean				bEmeliaGoodbyeConversation = false;
	private boolean				bEmelia2Conversation = false;

	// --------------------------------------------------------------------------------------------

	public NEWM_Emelia()
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		emeliaGUID = CodexThing.GuidFromCastID("Emelia");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		
		ownerGuid = GetClassThing();

		if(IsActorGuid(ownerGuid))
			owner = new CodexActor(ownerGuid);
	}

	// --------------------------------------------------------------------------------------------

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		bActive = false;
		owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void BuySellInterface(int shopperGuid)
	{
		AddBuySell("NewMoon.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);
			
		ExecuteBuySell(ownerGuid, shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		if(IsActorGuid(ownerGuid))
		{
			// play the talk animation on the owner
			int duration = owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			bActive = true;
			owner.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// see if this is his first visit
			if(!CodexSequence.GetChronicleFlag(chronScript.EMELIA_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.EMELIA_TALKEDONCE);
				
				if(!CodexSequence.GetChronicleFlag(chronScript.WAR3_BIGAL))
					Emelia1Conversation(clickerGuid, 0);
				else
					MeetEmeliaNoPinkConversation(clickerGuid, 0);

				return;
			}
			// see if this is his second visit for follow up lines
			else if(!CodexSequence.GetChronicleFlag(chronScript.EMELIA_TALKEDTWICE))
			{
				CodexSequence.SetChronicleFlag(chronScript.EMELIA_TALKEDTWICE);
				
				Emelia2Conversation(clickerGuid, 0);

				return;
			}
			else
			{
				SetTimer((float)0.5, 1, clickerGuid);
			}
		}

	}

	// --------------------------------------------------------------------------------------------

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.EMELIA_FIRSTGOODBYE))
			{
				CodexSequence.SetChronicleFlag(chronScript.EMELIA_FIRSTGOODBYE);
				
				EmeliaGoodbyeConversation(christofGUID, 0);
			}
			else
			{
				// play bye line
				new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
			}

			SetTimer((float)(2.0), 2);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(timerID == 1)
		{
			// play the greeting sound
			new CodexSound(greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid);

			BuySellInterface((int)arg0);
		}
		else
		if(timerID == 2)
		{
			int duration = owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			SetTimer((float)(duration / 1000), 3);	
		}
		else
		if(timerID == 3)
		{
			// stop the special animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void Emelia1Conversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		AIOff();
		bEmelia1Conversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_MeetEmelia", "37_4_MeetEmelia.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void MeetEmeliaNoPinkConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		AIOff();
		bMeetEmeliaNoPinkConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_MeetEmeliaNoPink", "37_4_MeetEmeliaNoPink.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EmeliaGoodbyeConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bEmeliaGoodbyeConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_EmeliaGoodbye", "37_4_EmeliaGoodbye.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Emelia2Conversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bEmelia2Conversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "37_4_SecondEmelia", "37_4_SecondEmelia.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bEmelia1Conversation)
		{
			CodexSound.PopMusic();
			CodexCamera.Release(starterGuid);
			bEmelia1Conversation = false;
			AIOn();

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
		else if(bMeetEmeliaNoPinkConversation)
		{
			CodexSound.PopMusic();
			CodexCamera.Release(starterGuid);
			bMeetEmeliaNoPinkConversation = false;
			AIOn();

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
		else if(bEmeliaGoodbyeConversation)
		{
			CodexCamera.Release(starterGuid);
			bEmeliaGoodbyeConversation = false;
			AIOn();

			// skip buy/sell
			// stop the special animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			return;
		}
		else if(bEmelia2Conversation)
		{
			CodexCamera.Release(starterGuid);
			bEmelia2Conversation = false;
			AIOn();
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bEmelia1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 6:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							//CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
			}
		}

		if(bMeetEmeliaNoPinkConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 3:

							//CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
			}
		}

		if(bEmeliaGoodbyeConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bEmelia2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "NewMoonRegister.ncp", 30);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, emeliaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

}


