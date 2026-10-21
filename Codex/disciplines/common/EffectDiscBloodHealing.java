/**
 * Effect DiscBloodHealing script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class EffectDiscBloodHealing extends Codex
{
	public void effectstarted( int actorGuid, int effectGuid, int creatorGuid, int duration )
	{
		CodexActor caster = new CodexActor( creatorGuid );
		
		// initial effect
		caster.SetShell( "redCloudShell", 0x5000, 0, 1, 1, 1 );
		caster.SpawnThing( CodexTemplate.REDMAGIC );
	}
}