/**
 * Discipline Fireball script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class DisciplineFireball extends Discipline
{
	private static final String	DISCIPLINE_NAME	= "Fireball";

	// --------------------------------------------------------------------------------------------

	public DisciplineFireball()
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

		SetupWorld(DISCIPLINE_NAME);

		// chance of discipline failure
		if(Fizzled(level))
		{
			DisplayFizzle(DISCIPLINE_NAME, level);
			return(0);
		}

		try
		{
			// fire the projectile
			float[] fireOffset = new float[3];
			fireOffset[0] = 0; fireOffset[1] = 50; fireOffset[2] = 20;
			int projectileGuid = casterThing.FireProjectileAtThing("proj_fireball", targetGuid, fireOffset);

			// lets alter the damage of the projectile
			CodexProjectile projectile = new CodexProjectile(projectileGuid);

			int smokeTrailGuid = projectile.SpawnThing("trail_fireball");
			fireOffset[0] = 0; fireOffset[1] = 0; fireOffset[2] = 0;
			projectile.AttachThing(smokeTrailGuid, -1, fireOffset, ATTACH_FLAG_AUTOREMOVE);

			// set the damage on the projectile according to the level
			projectile.SetProjectileDamage(FIREBALL_DAMAGES[level]);
			
			// cast text
			DisplayCast(DISCIPLINE_NAME, level);

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

