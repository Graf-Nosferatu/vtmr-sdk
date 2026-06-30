/**
 * Multiplayer To Curse the Darkness Chronicle script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class MP_CTDChronicle extends Codex
{
	//////////////////////////////////////////////////////////////////////////////////////////////
	// NOTE!!!! THE CHRON FLAG 1023 IS "RESERVED" FOR WEATHER.CLASS DO NOT USE!!!!!
	//////////////////////////////////////////////////////////////////////////////////////////////

	// chronicle constants
	public static final int TEMESVAR_TALKEDBEGGAR				= 1;
	public static final int TEMESVAR_TALKEDREBECCA				= 2;
	public static final int TEMESVAR_TALKEDUDOLPHO				= 3;
	public static final int TEMESVAR_EGSECONDHALFDONE			= 4;
	public static final int TEMESVAR_TALKEDEGBLAZEJ				= 5;
	public static final int TEMESVAR_REBSECONDHALFDONE			= 6;
	public static final int TEMESVAR_TALKEDRELIQUARY			= 7;

	public static final int SORVENA_TALKEDONCE					= 10;
	public static final int SORVENA_DONE						= 11;

	public static final int OUBLIETTE_MELMOTHMEETING			= 20;
	public static final int OUBLIETTE_LECOMTELETTER				= 21;
	public static final int OUBLIETTE_BADEND					= 22;
	public static final int OUBLIETTE_GOODEND					= 23;
	public static final int OUBLIETTE_BATTLE					= 24;
	public static final int OUBLIETTE_ENDMELMOTHCONV			= 25;
	public static final int OUBLIETTE_ENDCHRON					= 26;

	public static final int RAGWICK_TALKEDONCE					= 30;

	public static final int LECOMTE_TALKEDONCE					= 40;
	public static final int LECOMTE_MONOCLERETRIEVED			= 41;
	public static final int LECOMTE_ATTACKABLE					= 42;

	public static final int CATACOMBS_RELIQUARYRECOVERED		= 50;
	public static final int CATACOMBS_DOOROPEN					= 51;
	public static final int CATACOMBS_INDUNGEON					= 52;

	public static final int CTD_TEXTINTRO						= 100;


	public MP_CTDChronicle()
	{
	}

	public void Step(int stepId)
	{
		switch(stepId)
		{
			case CTD_TEXTINTRO:

				CodexSequence.SetChronicleFlag(stepId);
				break;

			case TEMESVAR_TALKEDBEGGAR:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 0
				CodexSequence.SetNSSIndex(0);

				// close exit to catacombs
				CodexSequence.CloseExit("Temesvar", 1);

				CodexQuest q = new CodexQuest(CodexQuest.Load("CTD_MeetMelmoth"));

				// show the oubliette on the map
				CodexSequence.SetLocationFlags("Oubliette", LOCATION_FLAG_SHOWINMAP);
				break;

			case OUBLIETTE_MELMOTHMEETING:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 1
				CodexSequence.SetNSSIndex(1);

				CodexQuest q2 = new CodexQuest(CodexQuest.Load("CTD_MeetMelmoth"));
				q2.Complete();

				CodexQuest q3 = new CodexQuest(CodexQuest.Load("CTD_RetrieveMonocle"));
					
				// open the exit to catacombs
				CodexSequence.OpenExit("Temesvar", 1);

				// show the catacombs on the map
				CodexSequence.SetLocationFlags("Catacombs1", LOCATION_FLAG_SHOWINMAP);
				break;

			case LECOMTE_MONOCLERETRIEVED:

				CodexSequence.SetChronicleFlag(stepId);

				// set current NSS index to 2
				CodexSequence.SetNSSIndex(2);

				CodexQuest q4 = new CodexQuest(CodexQuest.Load("CTD_RetrieveMonocle"));
				q4.Complete();

				CodexQuest q5 = new CodexQuest(CodexQuest.Load("CTD_ReturnToMelmoth"));
				break;
		}
	}

	public void advance(int scene, int subscene)
	{
		switch(scene)
		{
			case 2:
				switch(subscene)
				{
					case 1:
						Step(TEMESVAR_TALKEDBEGGAR);
						break;
				}
				break;
			case 3:
				switch(subscene)
				{
					case 1:
						advance(2, 1);
						Step(OUBLIETTE_MELMOTHMEETING);
						
						break;
				}
				break;
			case 5:
				switch(subscene)
				{
					case 1:
						advance(3, 1);
						Step(LECOMTE_MONOCLERETRIEVED);
				}
		}
	}
}