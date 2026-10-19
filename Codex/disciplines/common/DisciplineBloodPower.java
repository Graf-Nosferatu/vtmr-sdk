/**
 * Discipline Blood Power script.
*/
public class DisciplineBloodPower extends CodexDiscipline
{
	public DisciplineBloodPower()
	{
		DisciplineName = "BloodPower";
	}


	public int cast( int level, int casterGuid )
	{
		InitializeCaster( casterGuid );

		int effectGuid = Caster.FindActorEffect( CodexEffect.EF_DISC_BLOODPOWER );
		if( effectGuid != 0 ) {
			Caster.RemoveActorEffect( effectGuid );
		}

		Caster.AddActorEffectByLevel( CodexEffect.EF_DISC_BLOODPOWER, DURATION, level, casterGuid, EI_FLAG_DISCIPLINE );

		return PrintCast();
	}
}