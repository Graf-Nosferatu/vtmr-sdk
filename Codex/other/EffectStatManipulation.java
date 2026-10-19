public class EffectStatManipulation extends Codex
{
	private int internalEffectGuid;


	public void effectstarted( int actorGuid, int effectGuid, int creatorGuid, int duration )
	{
		effectHandler( actorGuid, effectGuid, true );
	}


	public void effectended( int actorGuid, int effectGuid, int creatorGuid, int reason )
	{
		effectHandler( actorGuid, effectGuid, false );
	}


	private void effectHandler( int actorGuid, int effectGuid, boolean isAdding )
	{
		CodexBuySell.RestorePartyManipulations();

		CodexActor actor = new CodexActor( actorGuid );
		int level = actor.GetActorEffectLevel( effectGuid );

		if( isAdding ) {
			internalEffectGuid = actor.AddActorEffectByLevel( CodexEffect.EF_STAT_MANIPULATION_INTERNAL, 0, level, actorGuid, 0 );
		}
		else {
			actor.RemoveActorEffect( internalEffectGuid );
		}

		CodexBuySell.SetupPartyManipulationsByMaximum();
	}


	public void save( int flags )
	{
		CodexSequence.SaveInt( internalEffectGuid );
	}


	public void restore( int flags )
	{
		internalEffectGuid = CodexSequence.RestoreInt();
	}
}