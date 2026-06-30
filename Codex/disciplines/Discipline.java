/**
 * Discipline base class.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class Discipline extends Codex
{
	protected	static final int		CONSOLE_FLAG_DISCIPLINECAST			= 0x8;
	protected	static final int		CONSOLE_FLAG_DISCIPLINERECAST		= 0x8;
	protected	static final int		CONSOLE_FLAG_DISCIPLINEFIZZLE		= 0x8;
	protected	static final int		CONSOLE_FLAG_DISCIPLINERESIST		= 0;
	protected	static final int		CONSOLE_FLAG_DISCIPLINENOCAST		= 0;

	// DISCIPLINE DURATIONS
	protected	static final int[]		AWE_DURATIONS						= {  5000,   10000,  15000,  20000,	 25000 };
	protected	static final int[]		ATROPHY_DURATIONS					= {  10000,  20000,  30000,  40000,  50000 };
	protected	static final int[]		BECKONING_DURATIONS					= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		BLOODHEALING_DURATIONS				= {   4000,   8000,   8000,  16000,  16000 };
	protected	static final int[]		BLOODDEXTERITY_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		BLOODSTRENGTH_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		BLOODSTAMINA_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		BLOODOFPOTENCY_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		CELERITY_DURATIONS					= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		CLOAKOFSHADOWS_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		CLOAKTHEGATHERING_DURATIONS			= {  10000,  20000,  30000,  40000,  60000 };
	protected	static final int[]		COMMAND_DURATIONS					= {   3000,   6000,   9000,  12000,  15000 };
	protected	static final int[]		DARKHUNTER_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		DREADGAZE_DURATIONS					= {   5000,  10000,  15000,  20000,  25000 };
	protected	static final int[]		ENTRANCEMENT_DURATIONS				= {   9000,  12000,  15000,  18000,  21000 };
	protected	static final int[]		EYESOFTHESERPENT_DURATIONS			= {   5000,  10000,  15000,  20000,  20000 };
	protected	static final int[]		EYESOFTHEBEAST_DURATIONS			= {  30000,  60000,  90000, 120000, 150000 };
	protected	static final int[]		EARTHMELD_DURATIONS					= {  30000,  60000,  90000, 120000, 150000 };
	protected	static final int[]		FERALWHISPERS_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		FERALCLAWS_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		FLAMETRAIL_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		FORTITUDE_DURATIONS					= {  30000,  60000,  90000, 120000, 180000 };
	protected	static final int[]		HATCHTHEVIPER_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		HEARTOFSTONE_DURATIONS				= {  30000,  60000,  90000, 120000, 150000 };
	protected	static final int[]		HSENSES_DURATIONS					= {  30000,  60000,  90000, 120000, 150000 };
	protected	static final int[]		IGNOREFLAME_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		INVISIBILITY_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		MAJESTY_DURATIONS					= {  10000,  20000,  30000,  40000,  60000 };
	protected	static final int[]		MASK1000FACES_DURATIONS				= {  15000,  30000,  45000,  60000,  75000 };
	protected	static final int[]		MESMERIZE_DURATIONS					= {   5000,  10000,  15000,  20000,  25000 };
	protected	static final int[]		MISTFORM_DURATIONS					= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		OBFUSCATE_DURATIONS					= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		POSSESSION_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		POTENCE_DURATIONS					= {  30000,  60000,  90000, 120000, 180000 };
	protected	static final int[]		PRISONOFICE_DURATIONS				= {   2000,   4000,   6000,  8000,  10000 };
	protected	static final int[]		PSYCHICPROJECTION_DURATIONS			= {   9000,  10000,  12000,  15000,  19000 };
	protected	static final int[]		QUELLTHEBEAST_DURATIONS				= {   5000,  7000,   9000,   11000,  13000 };
	protected	static final int[]		SHAMBLINGHORDES_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		SHAPEOFTHEBEAST_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		SKINOFTHEADDER_DURATIONS			= {  30000,  60000,  90000, 120000, 180000 };
	protected	static final int[]		SUBSUMETHESPIRIT_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		SUMMONELEMENTAL_DURATIONS			= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		SUMMONSOUL_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		THEFORGETFULMIND_DURATIONS			= {   2500,   5000,   7500,  10000,  12500 };
	protected	static final int[]		THEHAUNTING_DURATIONS				= {   5000,  10000,  15000,  20000,  25000 };
	protected	static final int[]		TORCH_DURATIONS						= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		TRUEFAITH_DURATIONS					= {   2500,   5000,   7500,  10000,  12500 }; // (*)
	protected	static final int[]		TRUESIGHT_DURATIONS					= {  30000,  60000,  90000, 120000, 150000 };
	protected	static final int[]		VIGORMORTIS_DURATIONS				= {  15000,  30000,  45000,  60000,  90000 };
	protected	static final int[]		VOICEOFMADNESS_DURATIONS			= {  10000,  20000,  30000,  40000,  50000 };

	// (*) do not change this duration without also changing the timers in exp_truefaith[n] templates, and truefaith[n].nex explosion files

	// OTHER DISCIPLINE CONSTANTS
	protected	static final float[]	AWAKEN_HEALTHS						= { 0.15f, 0.30f, 0.45f, 0.60f, 0.75f };
	protected	static final float[]	AWAKEN_BLOODS						= { 0.15f, 0.15f, 0.20f, 0.20f, 0.25f };

	protected	static final float[]	SHAMBLINGHORDES_BLOODS				= { 0.05f, 0.10f, 0.15f, 0.20f, 0.25f };
	protected	static final float[]	SHAMBLINGHORDES_HEALTHS				= { 0.05f, 0.10f, 0.15f, 0.20f, 0.25f };

	protected	static final float[]	VIGORMORTIS_BLOODS					= { 0.10f, 0.20f, 0.30f, 0.40f, 0.50f };
	protected	static final float[]	VIGORMORTIS_HEALTHS					= { 0.10f, 0.20f, 0.30f, 0.40f, 0.50f };

	protected	static final float		CLOAKOFSHADOWS_ALPHA_NONE			= 1.0f;
	protected	static final float[]	CLOAKOFSHADOWS_ALPHAS				= { 0.25f, 0.20f, 0.15f, 0.10f, 0.05f };

	protected	static final float		CLOAKTHEGATHERING_ALPHA_NONE		= 1.0f;
	protected	static final float[]	CLOAKTHEGATHERING_ALPHAS			= { 0.25f, 0.20f, 0.15f, 0.10f, 0.05f };

	protected	static final String[]	FERALCLAWS_TEMPLATES				= { "Claw01", "Claw02", "Claw03", "Claw04", "Claw05" };
	protected	static final String[]	FLASH_TEMPLATES						= { "exp_flash", "exp_flash", "exp_flash", "exp_flash", "exp_flash" };

	protected	static final int[]		FIREBALL_DAMAGES					= { 10, 20, 30, 40, 50 };

	protected	static final float		INVISIBILITY_ALPHA_NONE				= 1.0f;
	protected	static final float[]	INVISIBILITY_ALPHAS					= { 0.25f, 0.20f, 0.15f, 0.10f, 0.05f };

	protected	static final float		MISTFORM_ALPHA_NONE					= 1.0f;
	protected	static final float[]	MISTFORM_ALPHAS						= { 0.25f, 0.20f, 0.15f, 0.10f, 0.05f };

	protected	static final float		OBFUSCATE_ALPHA_NONE				= 1.0f;
	protected	static final float[]	OBFUSCATE_ALPHAS					= { 0.25f, 0.20f, 0.15f, 0.10f, 0.05f };

	protected	static final String[]	PLAGUEWIND_TEMPLATES				= { "proj_plaguewind0", "proj_plaguewind1", "proj_plaguewind2", "proj_plaguewind3", "proj_plaguewind4" };

	protected	static final String[]	TRUEFAITH_TEMPLATES					= { "exp_truefaith", "exp_truefaith1", "exp_truefaith2", "exp_truefaith3", "exp_truefaith4" };
	
	protected	static final String[]	EYESOFTHEBEAST_TEMPLATES			= { "exp_eyesofthebeast", "exp_eyesofthebeast1", "exp_eyesofthebeast2", "exp_eyesofthebeast3", "exp_eyesofthebeast4" };

	// --------------------------------------------------------------------------------------------

	protected	int						lastCasterGuid = 0;
	protected	int						lastTargetGuid = 0;
	protected	int						lastWorldGuid = 0;

	protected	CodexActor				casterThing;
	protected	CodexActor				targetThing;
	protected	CodexWorld				world;

	// --------------------------------------------------------------------------------------------

	public Discipline()
	{
	}

	public boolean CheckCastParameters(int level, int casterGuid, String disciplineName)
	{
		if((level < 0) || (level > 4))
		{
			CodexConsole.PrintError("Invalid level in Discipline " + disciplineName);
			return(false);
		}

		if(!IsActorGuid(casterGuid))
		{
			CodexConsole.PrintError("Invalid caster in Discipline " + disciplineName);
			return(false);
		}

		// create the instance of the caster thing if it is different than what's there already
		if(casterGuid != lastCasterGuid)
		{
			casterThing = new CodexActor(casterGuid);
			lastCasterGuid = casterGuid;
		}

		return(true);
	}


	public boolean CheckCastTarget(int targetGuid, String disciplineName)
	{
		if(!IsActorGuid(targetGuid))
		{
			CodexConsole.PrintError("Invalid target in Discipline" + disciplineName);
			return(false);
		}

		// create the instance of the target thing if it is different than what's there already
		if(targetGuid != lastTargetGuid)
		{
			targetThing = new CodexActor(targetGuid);
			lastTargetGuid = targetGuid;
		}

		return(true);
	}


	public boolean SetupWorld(String disciplineName)
	{
		//FIXME!! add world guids, etc.

		world = new CodexWorld(WORLD_CURRENT);

		if(world.GetGUID() <= 0)
		{
			CodexConsole.PrintError("Cannot find world in Discipline" + disciplineName);
			return(false);
		}

		return(true);
	}


	public boolean Fizzled(int level)
	{
		// never fizzle a scroll cast
		if((casterThing.GetActorFlags2() & THING_AF2_SCROLLCAST) != 0)
			return(false);

		if(Math.random() < ( (float)(5 - level) / 100.0f))
			return(true);

		return(false);
	}


	public void DisplayCast(String disciplineName, int level)
	{
		String casterName = casterThing.GetName();
		int clientGuid = casterThing.GetGUID();

		String aFormat = "%C" + casterName + "%d" + disciplineName;
		CodexConsole.Print(clientGuid, CONSOLE_FLAG_DISCIPLINECAST, FormatNLS("RPG_DISCMSG_CASTS", aFormat));
	}

	public void DisplayRecast(String disciplineName, int level)
	{
		String casterName = casterThing.GetName();
		int clientGuid = casterThing.GetGUID();

		String aFormat = "%C" + casterName + "%d" + disciplineName;
		CodexConsole.Print(clientGuid, CONSOLE_FLAG_DISCIPLINERECAST, FormatNLS("RPG_DISCMSG_RECASTS", aFormat));
	}

	public void DisplayFizzle(String disciplineName, int level)
	{
		String casterName = casterThing.GetName();
		int clientGuid = casterThing.GetGUID();

		String aFormat = "%C" + casterName + "%d" + disciplineName;
		CodexConsole.Print(clientGuid, CONSOLE_FLAG_DISCIPLINEFIZZLE, FormatNLS("RPG_DISCMSG_FIZZLES", aFormat), 0xFFFF00);
		
	}

	public void DisplayResist(String disciplineName, int level)
	{
		String targetName = targetThing.GetName();
		int clientGuid = casterThing.GetGUID();

		String aFormat = "%C" + targetName + "%d" + disciplineName;
		CodexConsole.Print(clientGuid, CONSOLE_FLAG_DISCIPLINERESIST, FormatNLS("RPG_DISCMSG_RESISTED", aFormat), 0xFFFF00);
		
	}

	public void DisplayNoCast(String stringId)
	{
		int clientGuid = casterThing.GetGUID();

		// only display these messages for real players
		if(IsPlayerGuid(clientGuid))
		{
			CodexConsole.PrintNLS(clientGuid, CONSOLE_FLAG_DISCIPLINENOCAST, stringId, 0xFFFF00);
		}
		
	}


	
}

