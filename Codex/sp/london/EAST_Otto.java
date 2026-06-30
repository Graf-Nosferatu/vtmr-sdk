/**
 * EAST_Otto script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EAST_Otto extends Codex
{
	private LondonChronicle	chronScript;

	private CodexRegion	_FirstOttoRegion;

	private int			ownerGuid = 0;
	private CodexActor	owner;
	private CodexActor	_ottoVan;

	private String				descriptionID			= "OTTOVAN";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	
	private static String		greetingLine[]			= {"Otto_0_0_1920_2.wav"};
	private static final int	numGreetingLines		= 1;
	private static String		byeLine[]				= {"Otto_0_0_1921.wav"};
	private static final int	numByeLines				= 1;

	//////////////////////////////////////////////////

	private boolean				bActive					= false;

	public boolean				bBrothelLocationConversation = false;

	public static String _params[] = {"Otto Van"};

	public EAST_Otto(CodexActor ottoVan)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_ottoVan = new CodexActor(ottoVan.GetGUID());
		
		CaptureThing(_ottoVan.GetGUID());		
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		ownerGuid = GetClassThing();

		if(IsActorGuid(ownerGuid))
			owner = new CodexActor(ownerGuid);

		// to force the stand on beginscene
		owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);
		_ottoVan.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);
	}

	// --------------------------------------------------------------------------------------------

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		bActive = false;
		_ottoVan.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(bActive)
			return;

		if(IsActorGuid(_ottoVan.GetGUID()))
		{
			new CodexSound("leverLarge_02.wav", 300, 600, 100, 0, 0, _ottoVan.GetGUID());
			int duration = owner.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "EASTOttoVan.ncp", 30);
			_ottoVan.PlayActorMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

			SetTimer((float)5.0, 1, clickerGuid);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);
			int duration = owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			_ottoVan.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			SetTimer((float)(duration / 1000), 2);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void BuySellInterface(int shopperGuid)
	{
		// Release the camera from the van-opening shot
		CodexCamera.Release(shopperGuid);

		AddBuySell("OttoVan.nbs", ownerGuid, descriptionID, flags, buyRate, sellRate, sellFlags);

		ExecuteBuySell(ownerGuid, shopperGuid, BUYSELL_XFLAG_WANTFEEDBACK);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(timerID == 1)
		{
			// play the talk animation on the owner
			owner.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			_ottoVan.PlayActorMotionSetMode(MOTION_TALK, false, (float)30.0);
			bActive = true;
			_ottoVan.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// play the greeting sound
			new CodexSound(greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid);

			BuySellInterface((int)arg0);
		}
		else
		if(timerID == 2)
		{
			new CodexSound("leverLarge_02.wav", 300, 600, 100, 0, 0, _ottoVan.GetGUID());
			int duration = owner.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
			_ottoVan.PlayActorMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
			SetTimer((float)(duration / 1000), 3);	
		}
		else
		if(timerID == 3)
		{
			// stop the special animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);
			_ottoVan.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

			bActive = false;
			_ottoVan.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

			if(CodexSequence.GetChronicleFlag(chronScript.TEN1_MEETPINK) &&
				!CodexSequence.GetChronicleFlag(chronScript.EAST_BROTHELLOCATION) &&
				!CodexSequence.GetChronicleFlag(chronScript.BROT_MEETLILY))
			{
				CodexSequence.SetChronicleFlag(chronScript.EAST_BROTHELLOCATION);

				BrothelLocationConversation(CodexPlayer.GetCurrentPlayer(), 0);
			}
		}
	}

	public void BrothelLocationConversation(int starterGuid, int npcGuid)
	{
		bBrothelLocationConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "28_4_BrothelLocation", "28_4_BrothelLocation.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bBrothelLocationConversation)
		{
			AIOn();
			bBrothelLocationConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:
						CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
						break;
				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}


