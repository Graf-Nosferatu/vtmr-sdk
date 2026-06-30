/**
 * Club Tenebrae Bartender script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TEN1_Bartender extends Codex
{
	private LondonChronicle	chronScript;

	private int			ownerGuid = 0;
	private CodexActor	owner;

	private String				descriptionID			= "BARTENDER";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_BLOODITEM + BUYSELL_ITEM_POTION;
	
	private static String		greetingLine[]			= {"MDBart1_0_0_1915.wav", "MDBart1_0_0_1917"};
	private static final int	numGreetingLines		= 2;

	private boolean			bActive	= false;

	public boolean			bBartenderMenuConversation = false;

	public TEN1_Bartender()
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
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
		AddBuySell("ClubTen.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

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

			if(!CodexSequence.GetChronicleFlag(chronScript.TEN1_MENUCONVERSATION))
			{
				CodexSequence.SetChronicleFlag(chronScript.TEN1_MENUCONVERSATION);

				BartenderMenuConversation(clickerGuid, 0);
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
			SetTimer((float)(2.0), 2);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(timerID == 1)
		{
			// play the talk animation on the owner
			owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);

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

	public void BartenderMenuConversation(int starterGuid, int npcGuid)
	{
		bBartenderMenuConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "28_1_BartenderMenu", "28_1_BartenderMenu.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bBartenderMenuConversation)
		{
			AIOn();
			bBartenderMenuConversation = false;
			CodexCamera.Release(starterGuid);
		}

		BuySellInterface(starterGuid);
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bBartenderMenuConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
					break;
			}
		}
	}
}


