/**
 * Effect DiscFortitude script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
*/
public class EffectDiscFortitude extends Codex
{
	private int casterGuid;
	private int padGuid;

	private float soak;


	public void effectstarted( int actorGuid, int effectGuid, int creatorGuid, int duration )
	{
		casterGuid = actorGuid;
		CodexActor caster = new CodexActor( casterGuid );

		// initial effect
		caster.SpawnThing( CodexTemplate.INVSHOCKWAVE );

		// flash shell at startup
		caster.SetShell( "redCloudShell_b", 0x5000, 0, 0.9f, 1, 1 );

		// running pad effect
		padGuid = caster.SpawnThing( CodexTemplate.SAWTOOTHBLUE );
		caster.AttachThing( padGuid, -1, ZERO_OFFSET, ATTACH_FLAG_AUTOREMOVE );

		soak = caster.GetActorEffectValue( effectGuid ) * 100f;

		CaptureThing( casterGuid );
	}


	public void effectended( int actorGuid, int effectGuid, int creatorGuid, int reason )
	{
		EndEffect();
	}


	public void killed( int guid, int causeID, int captureID )
	{
		EndEffect();
	}


	public float soaked( int guid, int causeGUID, float damageAmount, int damageType, float soakAmount, int captureID )
	{
		soakAmount += soak;
		if( soakAmount >= damageAmount )
		{
			CodexActor caster = new CodexActor( casterGuid );
			caster.SetShell( "white", 0x5000, 0, 1, 0.5f, 1 );
		}

		return soakAmount;
	}


	private void EndEffect()
	{
		// Remove pad and shell
		CodexThing pad = new CodexThing( padGuid );
		pad.Remove();

		CodexActor caster = new CodexActor( casterGuid );
		caster.EndShell();
	}


	public void save( int flags )
	{
		CodexSequence.SaveInt( padGuid );
		CodexSequence.SaveInt( casterGuid );
		CodexSequence.SaveFloat( soak );
	}


	public void restore( int flags )
	{
		padGuid = CodexSequence.RestoreInt();
		casterGuid = CodexSequence.RestoreInt();
		soak = CodexSequence.RestoreFloat();
	}
}