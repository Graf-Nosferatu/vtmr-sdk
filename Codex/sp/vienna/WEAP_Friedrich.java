/**
 * Weapon's Smith 20.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WEAP_Friedrich extends Codex
{
	private ViennaChronicle	chronScript;

	private int			ownerGuid = 0;
	private CodexActor	owner;

	private CodexActor	_wheel;

	private int			christofGUID;
	private int			erikGUID;
	private int			friedrichGUID;
	private int			serenaGUID;

	private String				descriptionID			= "WEAPONSMITHSHOP";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	private	int					numItems				= 20;

	private static String		greetingLine[]			= {"Friedrich_0_0_1902.wav"};
	private static final int	numGreetingLines		= 1;
	private static String		byeLine[]				= {"Friedrich_0_0_1903.wav", "Friedrich_0_0_1924.wav"};
	private static final int	numByeLines				= 2;

	//////////////////////////////////////////////////

	private boolean				bActive					= false;

	private boolean				bFriedrichFirstConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Wheel"};

	public WEAP_Friedrich(CodexActor wheel)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_wheel = new CodexActor(wheel.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		friedrichGUID = CodexThing.GuidFromCastID("Friedrich");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		
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
		AddBuySell("WeaponSmith.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

		ExecuteBuySell(ownerGuid, shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		if(IsActorGuid(ownerGuid))
		{
			// play the talk animation on the owner
			int duration = owner.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
			// play the "slow down" anim on the wheel
			_wheel.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

			bActive = true;
			owner.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// see if this is his first visit
			if(!CodexSequence.GetChronicleFlag(chronScript.WEAP_TALKEDONCE))
			{
				CodexSequence.SetChronicleFlag(chronScript.WEAP_TALKEDONCE);

				FriedrichFirstConversation(clickerGuid, 0);

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
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
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
			// stop the special animation on the owner
			int duration = owner.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
			// play the "start the wheel" anim on the wheel
			_wheel.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
			SetTimer((float)(duration / 1000), 3);	
		}
		else
		if(timerID == 3)
		{
			// stop the special animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);
			// play the "start the wheel" anim on the wheel
			_wheel.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void FriedrichFirstConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("DA_Conversation_1.mp3", 50);
		AIOff();
		bFriedrichFirstConversation = true;
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "20_1_Friedrich", "20_1_Friedrich.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bFriedrichFirstConversation)
		{
			CodexSound.PopMusic();
			CodexCamera.Release(starterGuid);
			bFriedrichFirstConversation = false;
			AIOn();

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bFriedrichFirstConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, friedrichGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, friedrichGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, friedrichGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

}


