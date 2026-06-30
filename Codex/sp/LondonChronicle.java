/**
 * London Chronicle script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LondonChronicle extends Codex
{
	//////////////////////////////////////////////////////////////////////////////////////////////
	// NOTE!!!! THE CHRON FLAG 1023 IS "RESERVED" FOR WEATHER.CLASS DO NOT USE!!!!!
	//////////////////////////////////////////////////////////////////////////////////////////////

	// chronicle constants
	public static final int WEST_LONDONINTRO				= 10;
	public static final int WEST_MUGGERREGION				= 11;
	public static final int WEST_PINKMODERN					= 12;
	public static final int CURI_SUMNER_TALKEDONCE			= 13;
	public static final int WEST_AFTERCURIO					= 14;
	public static final int EAST_UPDATE						= 15;
	public static final int EAST_FEEDINGREGION				= 16;
	public static final int EAST_OTTOREGION					= 17;
	public static final int EAST_THEATREREGION				= 18;
	public static final int EAST_OTTOONCE					= 19;

	public static final int SUMNER_FIRSTGOODBYE				= 20;
	public static final int CURI_SUMNER_NOPINK				= 21;
	public static final int CURI_SUMNER_ALTERNATEPINK		= 22;

	public static final int TOL1_INTROREGION				= 30;

	public static final int EAST_DOCKSCENEREGION			= 40;
	public static final int EAST_BROTHELLOCATION			= 41;
	public static final int EAST_AFTERFIGHT					= 42;
	
	public static final int TEN1_MEETPINK					= 50;
	public static final int TEN1_MENUCONVERSATION			= 51;

	public static final int BROT_PROSTITUTEREGION			= 60;
	public static final int BROT_MEETLILY					= 70;
	public static final int BROT_MEETGUARD					= 71;


	public static final int SET1_WORSHIP					= 80;
	public static final int SET2_SETITES					= 81;
	public static final int SET4_MAIN						= 82;
	public static final int SET4_BARTER						= 83;
	public static final int SET4_LUCRETIANOTDIE				= 84;
	public static final int SET4_LUCRETIADEAD				= 85;

	public static final int TOL1_TOWERINTRO					= 91;
	public static final int TOL4_FINDHEART					= 92;


	public static final int SUMNER_TALKEDONCE				= 400;
	public static final int SUMNER_TALKEDTWICE				= 401;

	public static final int SOC1_FATHER						= 410;
	public static final int SOC3_BASEMENT					= 411;
	public static final int SOC3_MEETMEMBERS				= 412;
	public static final int SOC3_RELICS						= 413;
	public static final int SOC1_VATSBROKEN					= 414;
	public static final int SOC1_ATTACKEDLEO				= 415;
	public static final int SOC3_SOCIETYJOURNAL				= 416;
	public static final int SOC3_SOCIETYDOCUMENTS			= 417;
	public static final int SOC3_LEOJOURNAL					= 418;
	public static final int SOC_FIRSTGUN					= 419;
	public static final int SOC3_DROPPEDINVENTORY			= 420;
	public static final int SOC3_FIRSTMEMBER				= 421;

	public static final int CARG_STOWAWAY					= 430;
	public static final int CARG_INTERPOL					= 431;


	public LondonChronicle()
	{
	}

	public void Step(int stepId)
	{
	}

	public void advance(int scene, int subscene)
	{
		switch(scene)
		{
		}
	}


}