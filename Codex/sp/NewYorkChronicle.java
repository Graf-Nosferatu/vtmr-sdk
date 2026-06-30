/**
 * New York Chronicle script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class NewYorkChronicle extends Codex
{
	//////////////////////////////////////////////////////////////////////////////////////////////
	// NOTE!!!! THE CHRON FLAG 1023 IS "RESERVED" FOR WEATHER.CLASS DO NOT USE!!!!!
	//////////////////////////////////////////////////////////////////////////////////////////////

	// chronicle constants
	public static final int DOCK_MEETGEORGE			= 10;
	public static final int DOCK_SABBATATTACK		= 11;
	public static final int DOCK_FLYTHROUGH			= 12;
	public static final int DOCK_HOMELESS			= 13;

	public static final int STOR_MEETALEXANDRA		= 20;
	public static final int STOR_HAVEPAINTING		= 21;
	public static final int STOR_SPAWNGOONS			= 22;

	public static final int GUNH_FIRSTHANK			= 450;
	public static final int EMELIA_TALKEDONCE		= 451;
	public static final int	EMELIA_TALKEDTWICE		= 452;
	public static final int	EMELIA_FIRSTGOODBYE		= 453;

	public static final int	ORSI_PALETTERECOVERED	= 460;

	public static final int	SEW1_ENTERSEWER			= 470;
	public static final int	SEW1_SUBWAY				= 471;
	public static final int	SEW4_CHAMBER			= 472;

	public static final int DEVN_MEETDEV			= 480;
	public static final int	DEVN_GETCODES			= 481;

	public static final int	LOBY_FREDVARNEY			= 490;

	public static final int	FLS2_LIBUSSACRAZED		= 500;
	public static final int	FLS2_MEETVUK			= 501;
	public static final int	FLS3_LIBUSSAHELP		= 502;
	public static final int	FLS1_VUK				= 503;
	public static final int	FLS1_INTRO				= 504;
	public static final int	FLS3_CHILDTOUCH			= 505;
	public static final int	FLS3_WALLINTRO			= 506;
	public static final int	FLS3_DOOROPEN			= 507;

	public static final int	SEW3_LAKESCENE			= 510;
	public static final int	SEW1_NOSFERATU			= 511;
	public static final int	SEW2_ARTWORK			= 512;
	public static final int	SEW4_ATTACHTRANS		= 513;

	public static final int	WAR1_SMACK				= 520;
	public static final int	WAR1_THORNEGUNFIRE		= 521;
	public static final int	WAR3_BIGAL				= 522;
	public static final int	WAR1_THORNEGONE			= 523;
	public static final int	WAR3_ALLEDGER			= 524;
	public static final int	WAR1_ENTRYLINES			= 525;

	public static final int	FAC4_ORSITALKED			= 530;
	public static final int	FAC4_TRIPLETSTALKED		= 531;

	public static final int UTWN_GOONREGION			= 540;
	public static final int	UTWN_OUTSIDECATHEDRAL	= 541;
	public static final int UTWN_GETUPVUK			= 542;
	public static final int UTWN_TOOLATE			= 543;



	public NewYorkChronicle()
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