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

		int effectGuid = Caster.FindActorEffect( "ef_disc_bloodpower" );
		if( effectGuid != 0 ) {
			Caster.RemoveActorEffect( effectGuid );
		}

		Caster.AddActorEffectByLevel( "ef_disc_bloodpower", DURATION, level, casterGuid, EI_FLAG_DISCIPLINE );

		return PrintCast();
	}
}