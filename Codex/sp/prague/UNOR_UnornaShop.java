/**
 * UNOR_UnornaShop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class UNOR_UnornaShop extends Codex
{
	private PragueChronicle	chronScript;

	private CodexThing	_anezkaLetter;

	private int			ownerGuid = 0;
	private CodexActor	owner;

	private int			christofGUID;
	private int			unornaGUID;

	// --------------------------------------------------------------------------------------------

	private String				descriptionID			= "UNORNASHOP";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK;

	private String		greetingLine[]			= {"Unorna_0_0_1904.wav", "Unorna_0_0_1925.wav", "Unorna_0_0_95.wav", ""};
	private int			numGreetingLines		= 3;
	private String		byeLine[]				= {"Unorna_0_0_1905.wav", "Unorna_0_0_1928.wav", "Unorna_0_0_96.wav", ""};
	private int			numByeLines				= 3;

	private boolean				bActive					= false;

	private boolean				bUnornaFirstConversation		= false;
	private boolean				bUnornaFirstGoodbyeConversation = false;
	private boolean				bQAUnornaAskConversation		= false;
	private boolean				bQAUnornaConversation			= false;
	private boolean				bUnornaVampireConversation		= false;
	private boolean				bUnornaLetterConversation		= false;

	public static String _params[] = {"Anezka's letter"};

	public UNOR_UnornaShop(CodexThing anezkaLetter)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_anezkaLetter = new CodexThing(anezkaLetter.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		unornaGUID = CodexThing.GuidFromCastID("Unorna");
		
		if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
		{
			// add in Unorna_0_0_1927 hello
			greetingLine[3] = "Unorna_0_0_1927";
			numGreetingLines = 4;
		}

		if(CodexSequence.GetChronicleFlag(chronScript.UNORD_LETTER))
		{
			// add in Unorna_0_0_1906 goodbye
			byeLine[3] = "Unorna_0_0_1906";
			numByeLines = 4;
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.UNORD_FLYTHROUGH))
		{
			CodexSequence.SetChronicleFlag(chronScript.UNORD_FLYTHROUGH);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.UNORD_LETTER))
		{
			// hide the anezka letter
			_anezkaLetter.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_anezkaLetter.SetThingFlags(THING_FLAG_NOHIGHLIGHT);		
		}

		ownerGuid = GetClassThing();

		if(IsActorGuid(ownerGuid))
			owner = new CodexActor(ownerGuid);
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		bActive = false;
		owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		UnornaLetterConversation(CodexPlayer.GetCurrentPlayer(), 0);
	}

	public void BuySellInterface(int shopperGuid)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION))
		{
			AddBuySell("UnornaVampireShop.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);
		}
		else
		{
			AddBuySell("UnornaShop.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);
		}

		ExecuteBuySell(ownerGuid, shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		if(IsActorGuid(ownerGuid))
		{
			// play the talk animation on the owner
			owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			bActive = true;
			owner.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			if(CodexSequence.GetChronicleFlag(chronScript.ANZK_LOCKETSCENE) &&
				!CodexSequence.GetChronicleFlag(chronScript.UNORD_LETTER))
			{
				CodexSequence.SetChronicleFlag(chronScript.UNORD_LETTER);

				// show the letter from anezka
				_anezkaLetter.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_anezkaLetter.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

				UnornaLetterConversation(clickerGuid, 0);
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION) &&
				!CodexSequence.GetChronicleFlag(chronScript.UNORD_VAMPIREGREETING))
			{
				CodexSequence.SetChronicleFlag(chronScript.UNORD_VAMPIREGREETING);

				UnornaVampireConversation(clickerGuid, 0);
			}
			else if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD) &&
				!CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION))
			{
				if(!CodexSequence.GetChronicleFlag(chronScript.UNOR_ASK))
				{
					CodexSequence.SetChronicleFlag(chronScript.UNOR_ASK);

					QAUnornaAskConversation(clickerGuid, 0);
				}
				else
				{
					QAUnornaConversation(clickerGuid, 0);
				}
			}
			else if(!CodexSequence.GetChronicleFlag(chronScript.UNORD_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.UNORD_TALKEDONCE);

				UnornaFirstConversation(clickerGuid, 0);
			}
			else
			{
				// play the greeting sound
				new CodexSound(greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid);

				BuySellInterface(clickerGuid);
			}
		}
	}

	// --------------------------------------------------------------------------------------------

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.UNORD_FIRSTGOODBYE) &&
				!CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION))
			{
				CodexSequence.SetChronicleFlag(chronScript.UNORD_FIRSTGOODBYE);

				// play first bye line "go with god..."
				//new CodexSound("Unorna_0_0_1928.wav", 300, 600, 100, 0, 0, ownerGuid);
				UnornaFirstGoodbyeConversation(christofGUID, 0);
			}
			else
			{
				// play random bye line
				new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
			
				// stop the talk animation on the owner
				owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);
			}

			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void UnornaFirstConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		AIOff();
		bUnornaFirstConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Unorna", "4_1_Unorna.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void UnornaVampireConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bUnornaVampireConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "8_1_Unorna", "8_1_Unorna.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void UnornaFirstGoodbyeConversation(int starterGuid, int npcGuid)
	{
		bUnornaFirstGoodbyeConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_UnornaFirstGoodbye", "4_1_UnornaFirstGoodbye.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void QAUnornaAskConversation(int starterGuid, int npcGuid)
	{
		AIOff();
		bQAUnornaAskConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_UnornaAsk", "6_1_UnornaAsk.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void QAUnornaConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		AIOff();
		bQAUnornaConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_Unorna", "6_1_Unorna.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void UnornaLetterConversation(int starterGuid, int npcGuid)
	{
		bUnornaLetterConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "11_1_UnornaLetter", "11_1_UnornaLetter.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bUnornaFirstConversation)
		{
			CodexSound.PopMusic();
			CodexCamera.Release(starterGuid);
			bUnornaFirstConversation = false;
			AIOn();

			// award conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}

		if(bUnornaVampireConversation)
		{
			CodexCamera.Release(starterGuid);
			bUnornaFirstConversation = false;
			AIOn();
		}

		if(bUnornaFirstGoodbyeConversation)
		{
			AIOn();
			bUnornaFirstGoodbyeConversation = false;
			CodexCamera.Release(starterGuid);

			// skip buy/sell
			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			return;
		}

		if(bQAUnornaAskConversation)
		{
			bQAUnornaAskConversation = false;

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			QAUnornaConversation(starterGuid, 0);

			// skip buy/sell
			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			return;
		}
		else if(bQAUnornaConversation)
		{
			CodexSound.PopMusic();

			if(returnValue == 1)
			{
				CodexCamera.Release(starterGuid);
				bQAUnornaConversation = false;
				AIOn();
			}
			else
			{
				QAUnornaConversation(starterGuid, 0);

				// skip buy/sell
				bActive = false;
				owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
				return;
			}
		}

		if(bUnornaLetterConversation)
		{
			AIOn();
			bUnornaLetterConversation = false;
			CodexCamera.Release(starterGuid);

			//ExecuteText(starterGuid, guid, "AnezkaLetter");

			// without pulling up buy/sell interface
			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
			return;
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bUnornaFirstConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
					//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
					//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
					break;
			}
		}

		if(bUnornaFirstGoodbyeConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
					//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
					break;

				case 1:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;					
			}
		}

		if(bUnornaVampireConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
					//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
					break;
			}
		}

		if(bQAUnornaAskConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
							break;
					}
			}
		}

		if(bQAUnornaConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)

					break;

				case 1:

					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
							break;

					} // switch(curLine)

					break;

				case 2:

					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
							break;

					} // switch(curLine)

					break;

				case 3:

					switch(curLine)

					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, unornaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)

					break;

				case 4:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		} // if(bQAUnornaConversation)

		if(bUnornaLetterConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, unornaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_CLOSEUP, 0, 0);
							//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Unorna.ncp", 30);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		}
	}
}
