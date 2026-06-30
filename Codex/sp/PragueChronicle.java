/**
 * Prague Chronicle script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class PragueChronicle extends Codex
{
	//////////////////////////////////////////////////////////////////////////////////////////////
	// NOTE!!!! THE CHRON FLAG 1023 IS "RESERVED" FOR WEATHER.CLASS DO NOT USE!!!!!
	//////////////////////////////////////////////////////////////////////////////////////////////

	// chronicle constants
	public static final int CNVT_OTHERROOMREGION				= 10;
	public static final int CNVT_FIRSTSZLACHTADEAD				= 11;
	public static final int CNVT_GEZACONVERSATION				= 12;
	public static final int CNVT_REEQUIPCHRISTOF				= 13;
	public static final int CNVT_ATTACKCONVERSATION				= 14;
	public static final int CNVT_OPENINGSCENE					= 15;

	public static final int CTDY_ANEZKATALK						= 16;
	public static final int CTDY_AFTERMINES						= 17;
	public static final int CTDY_FIRSTTIME						= 18;

	public static final int ANZK_LOCKETSCENE					= 19;


	public static final int OLDT_ECATERINAREGION				= 20;
	public static final int OLDT_ATTACKREGION					= 21;
	public static final int OTDY_RESIDENTS						= 22;
	public static final int OLDT_PREMYSLATTACK					= 23;
	public static final int OLDT_TOOLATE						= 24;
	public static final int OLDT_ATTACK							= 25;
	public static final int OLDT_FLYTHROUGH						= 26;
	public static final int OLDT_WILHEMCONVERSE					= 27;
	public static final int OLDT_TOVIENNA						= 28;
	public static final int OLDT_HAVEN							= 29;
	public static final int OLDT_FINALCONVERSATION				= 30;
	public static final int OLDT_LIBUSSAXP						= 31;
	public static final int OLDT_SHOWWILHEM						= 32;

	public static final int OTDY_OUTSMITHYREGION				= 40;
	public static final int OTDY_KNIGHT							= 41;
	public static final int OTDY_STRANGER						= 42;
	public static final int OTDY_FLYTHROUGH						= 43;

	public static final int GOLDD_REGION						= 50;
	public static final int UNORD_TALKEDONCE					= 51;
	public static final int UNORD_FLYTHROUGH					= 52;
	public static final int UNORD_LETTER						= 53;
	public static final int UNORD_FIRSTGOODBYE					= 54;
	public static final int UNOR_ASK							= 55;
	public static final int UNORD_VAMPIREGREETING				= 56;

	public static final int INN4_MINECONGRATS					= 60;
	public static final int INN4_FIRSTBARKEEP					= 61;

	public static final int STHM_GEZABLESSING					= 70;
	public static final int STHM_HERORETURN						= 71;

	public static final int HAVN_ANEZKASCENE					= 80;
	public static final int HAVN_SERENASCENE					= 81;

	public static final int UNIV_ECATERINAREGION				= 100;
	public static final int UNIV_ECATERINAVITAE					= 101;
	public static final int UNIV_ECATERINAGOLEM					= 102;
	public static final int UNIV_READLETTER						= 103;
	
	public static final int UNIV_CHRISTOFDESTROY				= 104;
	public static final int UNIV_SHUNANSWER						= 105;
	public static final int UNIV_CONVDONE						= 106;
	public static final int UNIV_DERAIL							= 107;
	public static final int UNIV_VIENNA							= 108;

	public static final int PRIN_REGION							= 110;
	public static final int PRIN_RELIQUARY						= 111;

	public static final int JIRI_TALKEDONCE						= 120;
	public static final int SMTY_MINECONGRATS					= 121;
	public static final int SMYD_FLYTHROUGH						= 122;
	public static final int JIRI_TALKEDAFTERUNORNA				= 123;

	public static final int JUDB_TRAINFEED						= 130;
	public static final int JUDB_AFTERFEEDCONV					= 131;
	public static final int JUDB_VISITCONVERSATION				= 132;
	
	// East gate and Silver Mines
	public static final int EGAT_OUTSIDEREGION					= 500;
	public static final int EGAT_EXITREGION						= 501;
	public static final int EGAT_FIRSTKNIGHTS					= 502;
	public static final int EGAT_KNIGHTSCHIDE					= 503;
	public static final int EGAT_KNIGHTSAHZRA					= 504;

	public static final int SIM1_STENCHREGION					= 510;
	public static final int SIM1_BODYREGION						= 511;
	public static final int SIM1_RATATTACK						= 512;
	public static final int SIM2_SHIELDREMARKS					= 513;

	public static final int SIM2_ALTEXITTAKEN					= 520;
	public static final int SIM3_ALTEXITTAKEN					= 521;
	public static final int SIM4_ALTEXITTAKEN					= 522;

	public static final int SIM4_AHZRAREGION					= 530;
	public static final int SIM4_AHZRADEAD						= 531;
	public static final int SIM4_LOCKETRECOVERED				= 532;

	// Petrin
	public static final int HILL_REGION							= 600;
	public static final int HILL_GARINOLREGION					= 601;
	public static final int HILL_REGION2						= 602;

	public static final int MON1_OURMISSIONREGION				= 610;
	public static final int MON1_STRANGECLANREGION				= 611;
	public static final int MON1_MURALREGION					= 612;
	public static final int MON1_FINALDEATHREGION				= 613;
	public static final int MON1_CAPPREGION						= 614;
	public static final int MON1_EXPERIMENTREGION				= 615;
	public static final int MON1_FIGHTREGION					= 616;
	
	public static final int MON2_DARKWORSHIPREGION				= 620;
	public static final int MON1_EARTHENFLOORREGION				= 621;

	public static final int MON3_GARINOLDOORREGION				= 630;
	public static final int MON3_GARINOLKEYRECOVERED			= 631;
	public static final int MON3_GARINOLDOOROPENED				= 632;
	public static final int MON3_LAMIAKEYRECOVERED				= 633;
	public static final int MON3_MERCURIODOOROPENED				= 634;
	public static final int MON3_MERCURIOREGION					= 635;
	public static final int MON3_MERCURIODEAD					= 636;
	public static final int MON3_NODFRAGMENTRECOVERED			= 637;
	public static final int MON3_JOURNALRECOVERED				= 638;
	public static final int MON3_MERCURIOJOURNAL				= 639;
	public static final int MON3_APOLOGY						= 640;

	// Josef
	public static final int NQTR_MENDELREGION					= 700;
	public static final int NQTR_GOLEMDEAD						= 701;
	public static final int NQTR_SHEMRECOVERED					= 702;
	public static final int NQTR_MENDELREGION2					= 703;
	public static final int NQTR_JOSEFREGION					= 704;
	public static final int NQTR_JOSEFVITAE						= 705;
	public static final int NQTR_JOSEFTUNNELSOPEN				= 706;
	public static final int NQTR_GOLEMSCENE						= 707;
	public static final int NQTR_TOOLATE						= 708;

	public static final int JOS2_GOLDENLANEEXIT					= 719;
	public static final int JOS3_VACLAVREGION					= 720;
	public static final int JOS3_VACLAVDEAD						= 721;
	public static final int JOS3_RELIQUARYRECOVERED				= 722;

	// Ardan's chantry
	public static final int ARC1_SHOPBUTAMASK					= 800;
	public static final int ARC1_TREE1REGION					= 801;
	public static final int ARC1_TREE2REGION					= 802;
	public static final int ARC1_TREE3REGION					= 803;

	public static final int ARC2_PRISON							= 810;
	public static final int ARC2_MANIFESTRECOVERED				= 811;

	public static final int ARC3_GARGOYLE						= 820;
	public static final int ARC3_GARGOYLEDEAD					= 821;
	public static final int ARC3_ERIK							= 822;

	public static final int ARC4_ARDANREGION					= 830;
	public static final int ARC4_ARDANDEAD						= 831;


	public PragueChronicle()
	{
	}

	public void Step(int stepId)
	{
	}

	public void advance(int scene, int subscene)
	{
	}


}