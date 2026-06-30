/**
 * SMTY_JiriShop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class SMTY_JiriShop extends Codex
{
	private PragueChronicle	chronScript;

	private int			ownerGuid = 0;
	private int			christofGUID;
	private int			jiriGUID;

	private CodexActor	owner;

	// --------------------------------------------------------------------------------------------

	private String				descriptionID			= "SMITHY";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_WEAPON;

	private static String		greetingLine[]			= {"Jiri_0_0_1930.wav", "Jiri_0_0_84.wav"};
	private static final int	numGreetingLines		= 2;
	private static String		byeLine[]				= {"Jiri_0_0_85.wav", "Jiri_0_0_86.wav"};
	private static final int	numByeLines				= 2;

	private boolean				bActive					= false;

	private boolean				bSmithyCongratsConversation		= false;
	private boolean				bSmithyConversation				= false;
	private boolean				bSmithyAfterUnornaConversation	= false;


	// --------------------------------------------------------------------------------------------

	public SMTY_JiriShop()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		ownerGuid = GetClassThing();

		christofGUID = CodexThing.GuidFromCastID("Christof");
		jiriGUID = CodexThing.GuidFromCastID("Jiri");

		if(IsActorGuid(ownerGuid))
			owner = new CodexActor(ownerGuid);
	}

	// --------------------------------------------------------------------------------------------

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		if(IsActorGuid(ownerGuid))
		{
			// play the talk animation on the owner
			int duration = owner.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
			bActive = true;
			owner.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// see if this is his frist visit - if for some reason they didn't talk to the
			// smithy and went right to the silvermines, skip the initial conversation, if they went
			// to unorna's before coming to the smithy, play the appropriate version of the first
			// conversation
			if(CodexSequence.GetChronicleFlag(chronScript.UNORD_TALKEDONCE) &&
				!CodexSequence.GetChronicleFlag(chronScript.JIRI_TALKEDAFTERUNORNA) &&
				!CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
			{
				CodexSequence.SetChronicleFlag(chronScript.JIRI_TALKEDAFTERUNORNA);
				CodexSequence.SetChronicleFlag(chronScript.JIRI_TALKEDONCE);

				SmithyAfterUnornaConversation(clickerGuid, 0);
			}
			else if(!CodexSequence.GetChronicleFlag(chronScript.JIRI_TALKEDONCE) &&
				!CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
			{
				CodexSequence.SetChronicleFlag(chronScript.JIRI_TALKEDONCE);
				CodexSequence.SetChronicleFlag(chronScript.JIRI_TALKEDAFTERUNORNA);

				SmithyConversation(clickerGuid, 0);

				return;
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD) &&
					!CodexSequence.GetChronicleFlag(chronScript.SMTY_MINECONGRATS))
			{
				CodexSequence.SetChronicleFlag(chronScript.SMTY_MINECONGRATS);

				SmithyCongratsConversation(clickerGuid, 0);
			}
			else
			{
				SetTimer((float)0.5, 1, clickerGuid);
			}
		}
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		bActive = false;
		owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}
	
	// --------------------------------------------------------------------------------------------

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
			SetTimer((float)(2.0), 2);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void BuySellInterface(int shopperGuid)
	{
		AddBuySell("PragueSmithy.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

		ExecuteBuySell(ownerGuid, shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}

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
			int duration = owner.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
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

	public void SmithyConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		AIOff();
		bSmithyConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Smithy", "4_1_Smithy.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void SmithyAfterUnornaConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bSmithyAfterUnornaConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_SmithyAfterUnorna", "4_1_SmithyAfterUnorna.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void SmithyCongratsConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bSmithyCongratsConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_Smithy", "6_1_Smithy.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bSmithyConversation)
		{
			CodexSound.PopMusic();
			CodexCamera.Release(starterGuid);
			AIOn();
			bSmithyConversation = false;
		}
		
		if(bSmithyAfterUnornaConversation)
		{
			CodexCamera.Release(starterGuid);
			AIOn();
			bSmithyAfterUnornaConversation = false;
		}
		
		if(bSmithyCongratsConversation)
		{
			CodexCamera.Release(starterGuid);
			AIOn();
			bSmithyCongratsConversation = false;
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bSmithyConversation)
		{
			switch(curEvent)
			{
				case 0:

					// ev_0
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 1:

					// ev_snotty
					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 2:

					// ev_honest
					switch(curLine)

					{
						case 0:

							// award conversation XP
							CodexPlayer.AwardPartyExperience(50);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

					}
					break;

				case 3:

					// ev_saul
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 4:

					// ev_stubborn
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 5:

					// ev_apology
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 6:

					// ev_promise
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 7:

					// ev_refuse
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
					}
					break;

				case 8:

					// ev_golden
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);

							// show unorna's on the map
							CodexSequence.SetLocationFlags("UnornaDay", LOCATION_FLAG_SHOWINMAP);
							break;
					}
					break;
			}
		}

		if(bSmithyAfterUnornaConversation)
		{
			switch(curEvent)
			{
				case 0:

					// ev_0
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 1:

					// ev_snotty
					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;

				case 2:

					// ev_honest
					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;
					}
					break;
			}
		}

		if(bSmithyCongratsConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, jiriGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}


