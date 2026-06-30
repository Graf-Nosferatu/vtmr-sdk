/**
 * Discipline Eyes Of Chaos script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineEyesOfChaos extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "EyesOfChaos";

	// --------------------------------------------------------------------------------------------

	private float	duration = 0;

	// --------------------------------------------------------------------------------------------

	public DisciplineEyesOfChaos()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid, int targetGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		if(!CheckCastTarget(targetGuid, DISCIPLINE_NAME))
			return(0);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			targetThing.AddActorEffectByLevel("ef_disc_eyesofchaos", 0, 0, casterGuid, EI_FLAG_DISCIPLINE);
			
			// cast text
			DisplayCast(DISCIPLINE_NAME, level);


			String strFormat;
			String strValue;
			String strType;
			String strAura;
			String strClan;
			String strHealth;
			String strBlood;

			switch(targetThing.GetActorType())
			{
				case ACTOR_TYPE_VAMPIRE: 
					strType = "ACTOR_TYPE_VAMPIRE";
					break;
				case ACTOR_TYPE_GHOUL:
					strType = "ACTOR_TYPE_GHOUL";
					break;
				case ACTOR_TYPE_HUMAN:
					strType = "ACTOR_TYPE_HUMAN";
					break;
				case ACTOR_TYPE_MONSTER:
					if((targetThing.GetActorFlags() & THING_AF_LUPINE) != 0)
						strType = "ACTOR_TYPE_LUPINE";
					else
						strType = "ACTOR_TYPE_MONSTER";
					break;
				default:
					strType = "";
					break;
			}

			strAura = targetThing.GetActorAura();
			strClan = targetThing.GetActorClan();
			strHealth = "" + (int)(targetThing.GetActorHealth());
			strBlood = "" + (int)(targetThing.GetActorStat(ACTOR_STAT_BLOOD));

			switch(level)
			{
				case 0: 
					strValue = "RPG_DISC_AURAPERC0";
					strFormat = "%t" + strType;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);
					break;
				case 1:
					strValue = "RPG_DISC_AURAPERC1";
					strFormat = "%t" + strType + "%A" + strAura;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);
					break;
				case 2:
					strValue = "RPG_DISC_AURAPERC2";
					strFormat = "%t" + strType + "%A" + strAura + "%C" + strClan;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);
					break;
				case 3:
					strValue = "RPG_DISC_AURAPERC2";
					strFormat = "%t" + strType + "%A" + strAura + "%C" + strClan;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);

					strValue = "RPG_DISC_AURAPERC3";
					strFormat = "%H" + strHealth;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);
					break;
				case 4:
					strValue = "RPG_DISC_AURAPERC2";
					strFormat = "%t" + strType + "%A" + strAura + "%C" + strClan;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);

					strValue = "RPG_DISC_AURAPERC4";
					strFormat = "%H" + strHealth + "%B" + strBlood;
					//CodexConsole.Print(casterThing.GetGUID(), 0, FormatNLS(strValue, strFormat), 0xFFFF00);
					CodexConsole.PrintFormatNLS(casterThing.GetGUID(), 0, strValue, strFormat, 0xFFFF00);
					break;
			}
			return(1);
		}
		catch(Exception e)
		{
			CodexConsole.PrintException(e.getMessage() + " in " + DISCIPLINE_NAME + " [cast]");
			return(0);
		}
		catch(Error e)
		{
			CodexConsole.PrintError(e.getMessage() + " in " + DISCIPLINE_NAME + " [cast]");
			return(0);
		}
	}
}

