/**
 * CTD Sorvena's Shop script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_SorvenaShop extends Codex
{
	private MP_CTDChronicle	chronScript;

	private int			sorvenaGUID;

	private int			ownerGuid = 0;
	private CodexActor	owner;

	// --------------------------------------------------------------------------------------------

	private String				descriptionID			= "SORVENA";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_MAGICWEAPON + BUYSELL_ITEM_SCROLLBOOK + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_WEAPON;

	private static String		greetingLine[]			= {"Unorna_0_0_1904.wav", "Unorna_0_0_1925"};
	private static final int	numGreetingLines		= 2;
	private static String		byeLine[]				= {"Unorna_0_0_1905.wav", "Unorna_0_0_1928"};
	private static final int	numByeLines				= 2;

	private boolean				bActive					= false;

	public boolean				bSorvena1Conversation = false;
	public boolean				bSorvena2Conversation = false;

	// --------------------------------------------------------------------------------------------

	public CTD_SorvenaShop()
	{
		chronScript = (MP_CTDChronicle)GetChronicleScript(0);
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		ownerGuid = GetClassThing();
		sorvenaGUID = CodexThing.GuidFromCastID("Sorvena");

		if(IsActorGuid(ownerGuid))
			owner = new CodexActor(ownerGuid);
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		bActive = false;
		owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void BuySellInterface(int shopperGuid)
	{
		AddBuySell("SorvenaShop.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

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

			// first see if we have to converse
			if(!CodexSequence.GetChronicleFlag(chronScript.SORVENA_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.SORVENA_TALKEDONCE);

				CodexThing initiatorThing = new CodexThing(clickerGuid);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "SORVENA" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					Sorvena1Conversation(clickerGuid, guid);
				}
				else
				{
					// the ST is controlling the conversations, just pull up the buy/sell
					BuySellInterface(clickerGuid);
				}
			}
			else if(!CodexSequence.GetChronicleFlag(chronScript.SORVENA_DONE) &&
				CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDUDOLPHO))
			{
				CodexSequence.SetChronicleFlag(chronScript.SORVENA_DONE);

				CodexThing initiatorThing = new CodexThing(clickerGuid);
				int locationNum = initiatorThing.GetLocationNum();

				String aFormat = "%a" + "SORVENA" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
				//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
				CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

				// check here if ST is handling the conversation, if not, call conversation
				if(!NetIsNoAutoConversations())
				{
					Sorvena2Conversation(clickerGuid, guid);
				}
				else
				{
					// the ST is controlling the conversations, just pull up the buy/sell
					BuySellInterface(clickerGuid);
				}
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
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);

			// stop the talk animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void Sorvena1Conversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_Sorvena1", "2_1_Sorvena1.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bSorvena1Conversation = true;
			AIOff();
		}
	}

	public void Sorvena2Conversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "2_1_Sorvena2", "2_1_Sorvena2.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bSorvena2Conversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bSorvena1Conversation)
		{
			AIOn();
			bSorvena1Conversation = false;
			CodexCamera.Release(starterGuid);

			// if they've talked to udolpho, continue conversation
			if(CodexSequence.GetChronicleFlag(chronScript.TEMESVAR_TALKEDUDOLPHO))
			{
				CodexSequence.SetChronicleFlag(chronScript.SORVENA_DONE);

				Sorvena2Conversation(starterGuid, ownerGuid);

				// skip the buy/sell in this case
				return;
			}
		}

		if(bSorvena2Conversation)
		{
			AIOn();
			bSorvena2Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bSorvena1Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDSorvena.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, sorvenaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
/*
						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;
*/
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bSorvena2Conversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.PlayPath(starterGuid, GetGUID(), "CTDSorvena.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, starterGuid, sorvenaGUID);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}
