/**
 * LOT Gun Haven Hank script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_GUNH_Hank extends Codex
{
	private MP_LOTChronicle	chronScript;

	private int					ownerGuid = 0;
	private CodexActor			owner;

	private String				descriptionID			= "GUNHAVEN";
	private int					flags					= 0;
	private float				buyRate					= (float)1.25;
	private float				sellRate				= (float)0.80;
	private	int					sellFlags				= BUYSELL_ITEM_MUNDANE + BUYSELL_ITEM_TREASURE + BUYSELL_ITEM_ARMOR + BUYSELL_ITEM_MAGICARMOR + BUYSELL_ITEM_WEAPON + BUYSELL_ITEM_MAGICWEAPON;
	private	int					numItems				= 20;
	
	private static String		greetingLine[]			= {"Hank_0_0_1912.wav"};
	private static final int	numGreetingLines		= 1;
	private static String		byeLine[]				= {"Hank_0_0_1914_2.wav"};
	private static final int	numByeLines				= 1;

	private boolean				bActive					= false;


	public LOT_GUNH_Hank()
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);
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

			// play the greeting sound
			new CodexSound(greetingLine[(int)(Math.random() * numGreetingLines)], 300, 600, 100, 0, 0, ownerGuid);

			BuySellInterface(clickerGuid);
		}
	}

	public void buysellended(boolean bAborted)
	{
		if(IsActorGuid(ownerGuid))
		{
			// play bye line
			new CodexSound(byeLine[(int)(Math.random() * numByeLines)], 300, 600, 100, 0, 0, ownerGuid);

			// stop the special animation on the owner
			owner.PlayActorMotionSetMode(MOTION_STAND, false, (float)30.0);

			bActive = false;
			owner.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}
}

