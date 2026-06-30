/**
 * Multiplayer Leaves of Three Chronicle script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class MP_LOTChronicle extends Codex
{
	//////////////////////////////////////////////////////////////////////////////////////////////
	// NOTE!!!! THE CHRON FLAG 1023 IS "RESERVED" FOR WEATHER.CLASS DO NOT USE!!!!!
	//////////////////////////////////////////////////////////////////////////////////////////////

	// chronicle constants
	public static final int LOBY_MEETBILL				= 1;
	public static final int LOBY_MEETDOMINIC			= 2;
	public static final int LOBY_PRIMOGEN				= 3;
	public static final int LOBY_PRIMOGENGONE			= 4;
	public static final int LOBY_CONCLAVE				= 5;
	public static final int LOBY_CONCLAVE_JUMP			= 6;
	public static final int	LOBY_BILLRING				= 7;
	public static final int	LOBY_BILLPLANTRING			= 8;
	public static final int	LOBY_END					= 9;
	public static final int	LOBY_SCAPEGOATS				= 10;
	public static final int	LOBY_SCAPEGOATS_JUMP		= 11;
	public static final int	LOBY_JUMPTOSUNROOM			= 12;
	public static final int	LOBY_PRIMOGENHIDDEN			= 13;
	public static final int	LOBY_ENDCHRON				= 14;

	public static final int FAC4_LUKESAVE				= 18;

	public static final int SLUM_DOMINIC				= 20;

	public static final int UTWN_BILLSPAWNEDIN			= 30;
	public static final int UTWN_BLOODHUNT				= 31;
	public static final int	UTWN_TALKEDBILL				= 32;
	public static final int	UTWN_ADVANCE				= 33;
	public static final int	UTWN_JUMPTOLOBBY			= 34;
	public static final int UTWN_CHANGESCENE_9_1		= 35;
	public static final int UTWN_AFTERFACTORY_JUMP		= 36;

	public static final int	WAR_TALKEDSMASHFACE			= 40;
	public static final int	WAR_FOUNDPACT				= 41;
	public static final int	WAR_KEEPPACT				= 42;
	public static final int	WAR_QUESTCOMPLETE			= 43;

	public static final int	SUIT_DONECHANGING			= 50;
	public static final int	SUIT_HITTEXT				= 51;

	public static final int LOBY_OPENHAVEN				= 90;
	public static final int LOBY_CHANGESCENE_2_1		= 100;
	public static final int LOBY_CHANGESCENE_3_1		= 101;
	public static final int LOBY_CHANGESCENE_5_1		= 102;

	public static final int	LOT_JUMPTOAPARTMENT			= 110;

	public static final int LOT_TEXTINTRO				= 200;


	public MP_LOTChronicle()
	{
	}

	public void Step(int stepId)
	{
		switch(stepId)
		{
			case LOT_TEXTINTRO:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_MEETBILL:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_MEETDOMINIC:

				CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_GreetPrimogen"));
				break;

			case LOBY_OPENHAVEN:

				// open haven
				CodexSequence.OpenExit("BarclayLobby", 2);
				break;

			case LOBY_CHANGESCENE_2_1:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 0
				CodexSequence.SetNSSIndex(0);

				CodexSequence.Jump("SuiteHaven", 0);
				CodexSequence.ChangeScene("BarclayLobby", "LOBY_LOT_2_1.nsd");
				break;

			case LOBY_PRIMOGEN:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_PRIMOGENGONE:

				CodexSequence.SetChronicleFlag(stepId);

				// quest completed after all Primogen have retired for the night
				CodexQuest q2 = new CodexQuest(CodexQuest.Load("LOT_GreetPrimogen"));
				q2.Complete();
				break;

			case LOBY_CHANGESCENE_3_1:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 1
				CodexSequence.SetNSSIndex(1);

				CodexSequence.Jump("SuiteHaven", 0);
				CodexSequence.ChangeScene("BarclayLobby", "LOBY_LOT_3_1.nsd");
				break;

			case LOBY_CONCLAVE:

				CodexSequence.SetChronicleFlag(stepId);

				
				break;

			case LOBY_CONCLAVE_JUMP:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_CHANGESCENE_5_1:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 2
				CodexSequence.SetNSSIndex(2);

				CodexSequence.Jump("SuiteHaven", 0);
				CodexSequence.ChangeScene("BarclayLobby", "LOBY_LOT_5_1.nsd");
				//CodexSequence.SetChronicleFlag(SUIT_DONECHANGING);
				break;

			case SUIT_DONECHANGING:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_SCAPEGOATS:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_SCAPEGOATS_JUMP:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case LOBY_JUMPTOSUNROOM:

				// set current NSS index to 3
				CodexSequence.SetNSSIndex(3);

				CodexSequence.Jump("Factory4", 10);
				break;

			case FAC4_LUKESAVE:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 4
				CodexSequence.SetNSSIndex(4);

				CodexSequence.SetReviveLocation(6, 10);

				CodexQuest q5 = new CodexQuest(CodexQuest.Load("LOT_FindDominic"));

				// open the factory entrance and slum room entrance
				CodexSequence.OpenExit("NewYorkUptown", 3);
				CodexSequence.OpenExit("NewYorkDocks", 6);

				// close the entrance to the barclay lobby from uptown
				CodexSequence.CloseExit("NewYorkUptown", 2);

				// show the slumroom on the map
				CodexSequence.SetLocationFlags("Slumroom", LOCATION_FLAG_SHOWINMAP);
				break;

			case UTWN_AFTERFACTORY_JUMP:

				CodexSequence.Jump("NewYorkUptown",3);
				break;

			case UTWN_JUMPTOLOBBY:

				CodexSequence.Jump("BarclayLobby", 0);
				break;

			case UTWN_CHANGESCENE_9_1:

				// change scene uptown to prepare for fight scene
				CodexSequence.ChangeScene("NewYorkUptown", "UTWN_LOT_9_1.nsd");
				break;

			case SLUM_DOMINIC:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 5
				CodexSequence.SetNSSIndex(5);

				CodexSequence.SetReviveLocation(16, 0);

				CodexQuest q6 = new CodexQuest(CodexQuest.Load("LOT_FindDominic"));
				q6.Complete();

				CodexQuest q7 = new CodexQuest(CodexQuest.Load("LOT_BloodPact"));

				// open the apartment haven
				CodexSequence.OpenExit("NewYorkDocks", 4);
				break;

			case UTWN_BILLSPAWNEDIN:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case UTWN_TALKEDBILL:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 6
				CodexSequence.SetNSSIndex(6);

				CodexSequence.SetChronicleFlag(UTWN_BLOODHUNT);

				CodexQuest q8 = new CodexQuest(CodexQuest.Load("LOT_InvadeWarehouse"));

				// open the warehouse
				CodexSequence.OpenExit("NewYorkDocks", 5);

				// show the warehouse on the map
				CodexSequence.SetLocationFlags("Warehouse1", LOCATION_FLAG_SHOWINMAP);
				break;

			case LOT_JUMPTOAPARTMENT:

				// jump them to the apartment haven, a "safe" place for the scene change
				// and close to the entry to the sewers
				CodexSequence.Jump("Apartment", 0);
				break;

			case WAR_TALKEDSMASHFACE:

				CodexSequence.SetChronicleFlag(stepId);

				CodexQuest q9 = new CodexQuest(CodexQuest.Load("LOT_SewerPassage"));

				// show the sewers on the map
				CodexSequence.SetLocationFlags("Sewers1", LOCATION_FLAG_SHOWINMAP);

				// open the sewer entrance
				CodexSequence.OpenExit("NewYorkDocks", 2);

				// change the scene in the lobby 
				CodexSequence.ChangeScene("BarclayLobby", "LOBY_LOT_12_1.nsd");
				break;

			case WAR_FOUNDPACT:

				// set chronicle flag that is checked in SLUM_7_1.java
				CodexSequence.SetChronicleFlag(stepId);

				CodexQuest q10 = new CodexQuest(CodexQuest.Load("LOT_BloodPact"));
				q10.Complete();
				break;

			case WAR_QUESTCOMPLETE:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 7
				CodexSequence.SetNSSIndex(7);

				CodexQuest q11= new CodexQuest(CodexQuest.Load("LOT_InvadeWarehouse"));
				q11.Complete();
				break;

			case LOBY_BILLRING:

				CodexSequence.SetChronicleFlag(stepId);

				// open the entrance to the barclay lobby from uptown and the exit from 
				// the lobby to the sewers
				CodexSequence.OpenExit("NewYorkUptown", 2);
				CodexSequence.OpenExit("BarclayLobby", 3);

				CodexQuest q12 = new CodexQuest(CodexQuest.Load("LOT_SewerPassage"));
				q12.Complete();
				break;
		}
	}

	public void advance(int scene, int subscene)
	{
		/*

		I'm using case 0 and case 1 in the subscene here so you can still "advance 5 1" for example and
		not have it change the scene in the lobby to 3.1, then to 2.1 all from the same advance.

		The "case 1" holds the actual changescene, and the "case 0" holds the other flags
		that should be changed for that advance (case 1 will "call" case 0, and case 0 calls the next
		advance, skipping the changescene part of the earlier advances.

		*/

		switch(scene)
		{
			case 2:
				switch(subscene)
				{
					case 0:
						Step(LOBY_MEETBILL);
						Step(LOBY_MEETDOMINIC);
						Step(LOBY_OPENHAVEN);
						break;

					case 1:
						advance(2, 0);
						Step(LOBY_CHANGESCENE_2_1);
						break;
				}
				break;
			case 3:
				switch(subscene)
				{
					case 0:
						advance(2, 0);
						Step(LOBY_PRIMOGEN);
						Step(LOBY_PRIMOGENGONE);
						break;
					case 1:
						advance(3, 0);
						Step(LOBY_CHANGESCENE_3_1);
						break;
				}
				break;
			case 5:
				switch(subscene)
				{
					case 0:
						advance(3, 0);
						Step(LOBY_CONCLAVE);
						Step(LOBY_CONCLAVE_JUMP);
						Step(SUIT_DONECHANGING);
						break;
					case 1:
						advance(5, 0);
						Step(LOBY_CHANGESCENE_5_1);
						break;
				}
				break;
			case 6:
				switch(subscene)
				{
					case 0:
						advance(5, 0);
						Step(LOBY_SCAPEGOATS);
						Step(LOBY_SCAPEGOATS_JUMP);
						break;
					case 1:
						advance(6, 0);
						Step(LOBY_JUMPTOSUNROOM);						
						break;
				}
				break;
			case 8:
				switch(subscene)
				{
					case 1:
						advance(6, 0);
						Step(FAC4_LUKESAVE);
						Step(UTWN_AFTERFACTORY_JUMP);
						break;
				}
				break;
			case 9:
				switch(subscene)
				{
					case 0:
						advance(8, 1);
						Step(SLUM_DOMINIC);
						break;
					case 1:
						// this is set here so beginscene will work if they're jumping right to
						// the 9.1 scene - this flag has to be set for Bill to be spawned in
						CodexSequence.SetChronicleFlag(SLUM_DOMINIC);
						advance(9, 0);
						//Step(UTWN_JUMPTOLOBBY);
						//Step(UTWN_CHANGESCENE_9_1);
						break;
				}
				break;
			case 11:
				switch(subscene)
				{
					case 1:
						advance(9, 0);
						Step(UTWN_TALKEDBILL);
						break;
				}
				break;
			case 12:
				switch(subscene)
				{
					case 1:
						advance(11, 1);
						Step(LOT_JUMPTOAPARTMENT);
						Step(WAR_TALKEDSMASHFACE);
						Step(WAR_FOUNDPACT);
						Step(WAR_QUESTCOMPLETE);
						break;
				}
				break;
		}
	}
}