/**
 * Discipline base class.
*/
public class CodexDiscipline extends Codex
{
	protected static final int CONSOLE_FLAG_DISCIPLINE = 0x8;

	protected static final int DURATION = 60000;


	protected String DisciplineName;

	protected int CasterGuid;
	protected CodexActor Caster;

	protected int TargetGuid;
	protected CodexActor TargetActor;


	protected void InitializeCaster( int casterGuid )
	{
		CasterGuid = casterGuid;
		Caster = new CodexActor( casterGuid );
	}


	protected void InitializeTargetActor( int targetGuid )
	{
		TargetGuid = targetGuid;
		TargetActor = new CodexActor( targetGuid );
	}


	protected int PrintCast()
	{
		String casterName = Caster.GetName();

		String aFormat = "%C" + casterName + "%d" + DisciplineName;
		CodexConsole.Print( CasterGuid, CONSOLE_FLAG_DISCIPLINE, FormatNLS( "RPG_DISCMSG_CASTS", aFormat ) );

		return 1;
	}


	protected boolean PrintNoCast( String stringId )
	{
		// only display these messages for real players
		if( IsPlayerGuid( CasterGuid ) ) {
			CodexConsole.PrintNLS( CasterGuid, CONSOLE_FLAG_DISCIPLINE, stringId, 0xFFFF00 );
		}

		return false;
	}
}