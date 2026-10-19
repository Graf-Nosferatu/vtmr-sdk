/**
 * Discipline Fortitude script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class DisciplineFortitude extends CodexDiscipline
{
	public DisciplineFortitude()
	{
		DisciplineName = "Fortitude";
	}


	public int cast( int level, int casterGuid )
	{
		InitializeCaster( casterGuid );

		// chance of discipline failure
		if( Fizzled( level ) ) {
			return PrintFizzle();
		}

		int effectGuid = Caster.FindActorEffect( CodexEffect.EF_DISC_FORTITUDE );
		if( effectGuid != 0 ) {
			Caster.RemoveActorEffect( effectGuid );
		}

		// start the real effect
		Caster.AddActorEffectByLevel( CodexEffect.EF_DISC_FORTITUDE, DURATION, level, casterGuid, EI_FLAG_DISCIPLINE );

		return PrintCast();
	}
}