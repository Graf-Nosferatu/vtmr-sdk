/**
 * CURI_Sumner script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CURI_Sumner extends Codex
{
	private LondonChronicle	chronScript;

	private int			ownerGuid = 0;
	private CodexActor	owner;

	private String				descriptionID			= "CURIOSHOP";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;
	private	int					numItems				= 20;

	private int					christofGUID;
	private int					pinkGUID;
	private int					sumnerGUID;

	private static String		greetingLine[]			= {"Sumner_Hello.wav"};
	private static final int	numGreetingLines		= 1;
	private static String		byeLine[]				= {"Sumner_GoodDay.wav"};
	private static final int	numByeLines				= 1;

	private static String		firstByeLine[]			= {"Sumner_28_4_1042.mp3"};
	private static final int	numFirstByeLine			= 1;

	//////////////////////////////////////////////////

	private boolean				bActive					= false;

	private boolean				bSumner1Conversation = false;
	private boolean				bSumner2Conversation = false;
	public boolean				bMeetSumner2Conversation = false;
	public boolean				bSecondSumner2Conversation = false;

	// --------------------------------------------------------------------------------------------

	public CURI_Sumner()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		sumnerGUID = CodexThing.GuidFromCastID("Sumner");
		
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
		AddBuySell("CurioShop.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

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

			if(CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK))
			{
				if(!CodexSequence.GetChronicleFlag(chronScript.CURI_SUMNER_NOPINK))
				{
					// see if this is his first visit
					if(!CodexSequence.GetChronicleFlag(chronScript.CURI_SUMNER_TALKEDONCE))
					{
						CodexSequence.SetChronicleFlag(chronScript.CURI_SUMNER_TALKEDONCE);

						Sumner1Conversation(clickerGuid, 0);

						return;
					}
					// see if this is his second visit for follow up lines
					else if(!CodexSequence.GetChronicleFlag(chronScript.SUMNER_TALKEDTWICE) &&
						CodexSequence.GetChronicleFlag(chronScript.WEST_AFTERCURIO))
					{
						CodexSequence.SetChronicleFlag(chronScript.SUMNER_TALKEDTWICE);

						Sumner2Conversation(clickerGuid, 0);

						return;
					}
					else
					{
						SetTimer((float)(duration / 1000), 1, clickerGuid);
					}
				}
				else if(!CodexSequence.GetChronicleFlag(chronScript.CURI_SUMNER_ALTERNATEPINK))
				{
					CodexSequence.SetChronicleFlag(chronScript.CURI_SUMNER_ALTERNATEPINK);

					// sumner after meeting pink if you've already talked to him once without pink
					SecondSumner2Conversation(clickerGuid, 0);
				}
				else
				{
					SetTimer((float)0.5, 1, clickerGuid);
				}
			}
			else
			{
				if(!CodexSequence.GetChronicleFlag(chronScript.CURI_SUMNER_NOPINK))
				{
					CodexSequence.SetChronicleFlag(chronScript.CURI_SUMNER_NOPINK);

					// first sumner conversation occuring before meeting pink
					MeetSumner2Conversation(clickerGuid, 0);
				}
				else
				{
					SetTimer((float)0.5, 1, clickerGuid);
				}
			}
		}

	}

	// --------------------------------------------------------------------------------------------

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.SUMNER_FIRSTGOODBYE))
			{
				CodexSequence.SetChronicleFlag(chronScript.SUMNER_FIRSTGOODBYE);

				// play first bye line
				CodexSound.PlayVoice(sumnerGUID, "Sumner_28_4_1042", 100);
			}
			else
			{
				// play generic bye line
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

	public void Sumner1Conversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		AIOff();
		bSumner1Conversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_4_MeetSumner", "28_4_MeetSumner.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Sumner2Conversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bSumner2Conversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_4_SecondSumner", "28_4_SecondSumner.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void MeetSumner2Conversation(int starterGuid, int npcGuid)
	{
		bMeetSumner2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_4_MeetSumner2", "28_4_MeetSumner2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void SecondSumner2Conversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_1.mp3", 50);
		bSecondSumner2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "28_4_SecondSumner2", "28_4_SecondSumner2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bSumner1Conversation)
		{
			CodexSound.PopMusic();
			CodexCamera.Release(starterGuid);
			bSumner1Conversation = false;
			AIOn();

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bSumner2Conversation)
		{
			CodexCamera.Release(starterGuid);
			bSumner2Conversation = false;
			AIOn();
		}

		if(bMeetSumner2Conversation)
		{
			AIOn();
			bMeetSumner2Conversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(100);
		}

		if(bSecondSumner2Conversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bSecondSumner2Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bSumner1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bSumner2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bMeetSumner2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bSecondSumner2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, pinkGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, sumnerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

}


