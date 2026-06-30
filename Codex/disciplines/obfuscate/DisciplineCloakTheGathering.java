/**
 * Discipline CloakTheGathering script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineCloakTheGathering extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "CloakTheGathering";

	// --------------------------------------------------------------------------------------------

	private CodexSound			sound;

	// --------------------------------------------------------------------------------------------

	public DisciplineCloakTheGathering()
	{
	}

	// --------------------------------------------------------------------------------------------

	public int cast(int level, int casterGuid)
	{
		// do sanity checks
		if(!CheckCastParameters(level, casterGuid, DISCIPLINE_NAME))
			return(0);

		SetupWorld(DISCIPLINE_NAME);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}
		
		try
		{
			// retrieve the caster's position
			float [] position;
			position = new float[3];

			position = casterThing.GetPosition();

			CodexCollision query = new CodexCollision();

			query.IgnoreAll();
			query.AcceptType(THING_TYPE_ACTOR);
			query.AcceptType(THING_TYPE_PLAYER);

			int numResults = query.ThingsInSphere(position, 250.0f, casterThing.GetGUID());

			for(int i = 0; i < numResults; i++)
			{
				int result = query.GetResult(i);

				CodexActor act = new CodexActor(result);

				// check the team
				if(casterThing.GetActorTeam() != act.GetActorTeam())
					continue;

				// let's see if Cloak Of Shadows is on, and do NOT override it if superior level
				int effectGuid = act.FindActorEffect("ef_disc_cloakofshadows");
				if(effectGuid != 0)
				{
					int cosLevel = act.GetActorEffectLevel(effectGuid);
					if(cosLevel > level)
						continue;

					act.RemoveActorEffect(effectGuid);
				}

				effectGuid = act.FindActorEffect("ef_disc_cloakthegathering");
				
				if(effectGuid != 0)
					act.ExpandActorEffect(effectGuid, CLOAKTHEGATHERING_DURATIONS[level], false);
				else
					act.AddActorEffectByLevel("ef_disc_cloakthegathering", CLOAKTHEGATHERING_DURATIONS[level], level, casterThing.GetGUID(), 4);
			}

			query.Free();

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

	// --------------------------------------------------------------------------------------------

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// sound.Stop();
		// casterThing.SetAlpha(CloakTheGathering_ALPHA_NONE);
	}
}

