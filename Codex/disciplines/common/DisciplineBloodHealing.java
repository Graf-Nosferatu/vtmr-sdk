/**
 * Discipline Blood Healing script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class DisciplineBloodHealing extends CodexDiscipline
{
	public DisciplineBloodHealing()
	{
		DisciplineName = "BloodHealing";
	}


	public boolean cancast( int level, int casterGuid, int targetGuid, float x, float y, float z, boolean isPos )
	{
		InitializeCaster( casterGuid );

		// Check that the caster doesn't have full health
		if( Caster.GetActorHealth() >= Caster.GetActorMaxHealth() ) {
			return PrintNoCast( "RPG_DISC_NOBLOODHEALING" );
		}

		return true;
	}


	public int cast( int level, int casterGuid )
	{
		// start the real effect
		Caster.AddActorEffectByLevel( CodexEffect.EF_DISC_BLOODHEALING, 0, level, casterGuid, EI_FLAG_DISCIPLINE );

		// bonus at high levels
		if( level == 2 ) {
			Caster.AddActorEffectByLevel( CodexEffect.EF_CUREPOISON, 0, 1, casterGuid, EI_FLAG_DISCIPLINE );
		}
		else if( level == 3 )
		{
			Caster.AddActorEffectByLevel( CodexEffect.EF_CUREPOISON, 0, 2, casterGuid, EI_FLAG_DISCIPLINE );
			Caster.AddActorEffectByLevel( CodexEffect.EF_CUREDISEASE, 0, 1, casterGuid, EI_FLAG_DISCIPLINE );
		}
		else if( level == 4 )
		{
			Caster.AddActorEffectByLevel( CodexEffect.EF_CUREPOISON, 0, 3, casterGuid, EI_FLAG_DISCIPLINE );
			Caster.AddActorEffectByLevel( CodexEffect.EF_CUREDISEASE, 0, 2, casterGuid, EI_FLAG_DISCIPLINE );
		}

		return PrintCast();
	}
}