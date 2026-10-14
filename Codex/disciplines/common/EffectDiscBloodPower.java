/**
 * Effect DiscBloodPower script.
*/
public class EffectDiscBloodPower extends Codex
{
	private int strengthEffectGuid, dexterityEffectGuid, staminaEffectGuid;


	public void effectstarted( int actorGuid, int effectGuid, int creatorGuid, int duration )
	{
		CodexActor caster = new CodexActor( creatorGuid );

		// initial effect
		caster.SetShell( "redCloudShell", 0x5000, 0, 1, 1, 1 );
		caster.SpawnThing( "redMagic" );

		int level = caster.GetActorEffectLevel( effectGuid );

		strengthEffectGuid = caster.AddActorEffectByLevel( "ef_stat_strength", duration, level, creatorGuid, EI_FLAG_DISCIPLINE );
		dexterityEffectGuid = caster.AddActorEffectByLevel( "ef_stat_dexterity", duration, level, creatorGuid, EI_FLAG_DISCIPLINE );
		staminaEffectGuid = caster.AddActorEffectByLevel( "ef_stat_stamina", duration, level, creatorGuid, EI_FLAG_DISCIPLINE );
	}


	public void effectended( int actorGuid, int effectGuid, int creatorGuid, int reason )
	{
		CodexActor caster = new CodexActor( creatorGuid );

		caster.RemoveActorEffect( strengthEffectGuid );
		caster.RemoveActorEffect( dexterityEffectGuid );
		caster.RemoveActorEffect( staminaEffectGuid );
	}


	public void save( int flags )
	{
		CodexSequence.SaveInt( strengthEffectGuid );
		CodexSequence.SaveInt( dexterityEffectGuid );
		CodexSequence.SaveInt( staminaEffectGuid );
	}


	public void restore( int flags )
	{
		strengthEffectGuid = CodexSequence.RestoreInt();
		dexterityEffectGuid = CodexSequence.RestoreInt();
		staminaEffectGuid = CodexSequence.RestoreInt();
	}
}