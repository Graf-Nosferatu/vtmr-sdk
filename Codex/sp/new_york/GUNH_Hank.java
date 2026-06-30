/**
 * Gun Haven Hank script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class GUNH_Hank extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_FLIPSWITCH		= 0;
	private static final int	TIMER_ID_DOOR1DOWN		= 1;
	private static final int	TIMER_ID_DOOR2OVER		= 2;
	private static final int	TIMER_ID_DOOR2INTOWALL	= 3;
	private static final int	TIMER_ID_DOOR2DOWN		= 4;

	private	CodexThing			_slideDoor1;
	private CodexThing			_slideDoor2;

	private boolean				bHankConversation	= false;

	private int					ownerGuid = 0;
	private CodexActor			owner;

	private int					christofGUID;
	private int					pinkGUID;
	private int					hankGUID;

	private String				descriptionID			= "GUNHAVEN";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	private	int					numItems				= 20;
	
	private static String		greetingLine[]			= {"Hank_0_0_1912.wav", "Hank_0_0_1911.wav"};
	private static final int	numGreetingLines		= 2;
	private static String		byeLine[]				= {"Hank_0_0_1914_2.wav", "Hank_0_0_1913.wav", "Hank_0_0_1914.wav"};
	private static final int	numByeLines				= 3;

	private boolean				bActive					= false;


	public static String _params[] = {"Sliding door part 1", "Sliding door part 2"};

	public GUNH_Hank(CodexThing slideDoor1, CodexThing slideDoor2)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_slideDoor1 = new CodexThing(slideDoor1.GetGUID());
		_slideDoor2 = new CodexThing(slideDoor2.GetGUID());

		CaptureThing(_slideDoor1.GetGUID());
		CaptureThing(_slideDoor2.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		hankGUID = CodexThing.GuidFromCastID("Hank");

		ownerGuid = GetClassThing();

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
		AddBuySell("GunHaven.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);
				
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

			if(!CodexSequence.GetChronicleFlag(chronScript.GUNH_FIRSTHANK))
			{
				CodexSequence.SetChronicleFlag(chronScript.GUNH_FIRSTHANK);

				HankConversation(clickerGuid, 0);
			}
			else
			{
				SetTimer((float)0.5, 5, clickerGuid);
			}
		}

	}

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
			SetTimer((float)(2.0), 6);
			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FLIPSWITCH:

				_slideDoor1.MoveToFrame(1, 50);
				SetTimer(2, TIMER_ID_DOOR1DOWN);
				break;

			case TIMER_ID_DOOR1DOWN:

				_slideDoor1.MoveToFrame(2, 50);
				SetTimer(4, TIMER_ID_DOOR2OVER);
				break;

			case TIMER_ID_DOOR2OVER:

				_slideDoor2.MoveToFrame(1, 50);
				SetTimer(2, TIMER_ID_DOOR2INTOWALL);
				break;

			case TIMER_ID_DOOR2INTOWALL:

				_slideDoor2.MoveToFrame(2, 50);
				SetTimer(2, TIMER_ID_DOOR2DOWN);
				break;

			case TIMER_ID_DOOR2DOWN:

				_slideDoor2.MoveToFrame(3, 50);
				break;

			case 5:

				// play the greeting sound
				new CodexSound(greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid);

				BuySellInterface((int)arg0);
				break;

			case 6:

				int duration = owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
				SetTimer((float)(duration / 1000), 7);	
				break;

			case 7:

				// stop the special animation on the owner
				owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

				bActive = false;
				break;
		}
	}

	public void HankConversation(int starterGuid, int npcGuid)
	{
		bHankConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "35_1_Hank", "35_1_Hank.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bHankConversation)
		{
			AIOn();
			bHankConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, hankGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						///////////////////////////////////////////
						// opens doors to secret stash
						///////////////////////////////////////////
						CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "HankDoors.ncp", 30);
						owner.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						SetTimer(2, TIMER_ID_FLIPSWITCH);
						///////////////////////////////////////////
						break;

					case 3:

						owner.StopActorAction();
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}

