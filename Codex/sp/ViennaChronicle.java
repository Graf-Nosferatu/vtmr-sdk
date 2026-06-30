/**
 * Vienna Chronicle script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ViennaChronicle extends Codex
{
	//////////////////////////////////////////////////////////////////////////////////////////////
	// NOTE!!!! THE CHRON FLAG 1023 IS "RESERVED" FOR WEATHER.CLASS DO NOT USE!!!!!
	//////////////////////////////////////////////////////////////////////////////////////////////

	// chronicle constants
	public static final int OUTR_FLYTHROUGH				= 10;

	public static final int NRTH_EXITSCLOSED			= 20;

	public static final int TEU4_ORSIFAREWELL			= 50;
	public static final int TEU4_KNIGHTREGION			= 51;
	
	public static final int TEU2_AINKURNREGION			= 60;
	public static final int TEU2_WARROOMREGION			= 61;
	public static final int TEU2_AINKURNVISION			= 62;
	public static final int TEU1_ADVANCE				= 63;

	public static final int TEU2_AMULETRECOVERED		= 70;
	public static final int SOUT_GATELINESAID			= 71;
	public static final int ORDR_AMULETCHARGED			= 72;
	public static final int ORDR_JOURNALCONV			= 73;

	public static final int WEAP_TALKEDONCE				= 75;

	public static final int WEST_PARTYREGION			= 78;
	public static final int ORSI_ORSICONVERSATION		= 80;
	public static final int ORSI_FLYTHROUGH				= 81;

	public static final int ORDR_TALKEDONCE				= 90;

	public static final int FROG_TRIPLETSCONVERSATION	= 100;
	public static final int FROG_INTRO					= 101;

	public static final int ORSIINVITATION				= 150;
	public static final int SOUT_LOOKREGION				= 160;

	// Stephansdom
	public static final int STE3_LUTHERTALKED			= 200;
	public static final int STE3_LUTHERDEAD				= 201;
	public static final int STE3_PORTRAIT				= 202;
	public static final int STE3_SKYLIGHTOPEN			= 203;
	public static final int STE1_GEARMOVED				= 204;
	public static final int STE3_DARKHUNTER				= 205;
	public static final int STE1_SERENABURN				= 206;
	public static final int STE3_SHADOWS				= 207;

	// Haus de Hexe
	public static final int HHX1_ARCPIECE1RECOVERED		= 300;
	public static final int HHX1_ARCPIECE2RECOVERED		= 301;
	public static final int HHX1_ARCPIECE3RECOVERED		= 302;
	public static final int HHX1_ARCPIECE1PLACED		= 303;
	public static final int HHX1_ARCPIECE2PLACED		= 304;
	public static final int HHX1_ARCPIECE3PLACED		= 305;
	public static final int HHX1_FINDARCANULUM			= 306;
	public static final int HHX1_ENTRYWAY				= 307;
	public static final int ORDR_ETRIUSJOURNAL			= 308;
	public static final int HHX1_VIRSTANIA				= 309;
	public static final int HHX1_TELEPORTIN				= 310;

	public static final int HHX3_LABS					= 320;
	public static final int HHX4_LIBRARY				= 321;

	public ViennaChronicle()
	{
	}

	public void beginscene(int clientGuid, int captureID)
	{
		SetWeatherEffect("snow");
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