/**
 * Effect DiscMistForm script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscMistForm extends Codex
{
	private int				mistGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscMistForm()
	{
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		CodexActor _Creator	= new CodexActor(creatorGuid);

		// Set actor flags
		_Creator.SetActorFlags(THING_AF_ETHEREAL);
		
		// create the effect and attach it to the caster
		_Creator.SetAlpha((float)0.0, (float)1500.0);
		mistGuid = _Creator.SpawnThing("greenMist");
		float[] offset = new float[3];
		offset[0] = 0; offset[1] = 0; offset[2] = 0; 
		_Creator.AttachThing(mistGuid, 0, offset, ATTACH_FLAG_AUTOREMOVE);

		_Creator.SetThingFlags(THING_FLAG_SILENT);
		_Creator.SetActorFlags(THING_AF_NOSHAPEDISC);

		CaptureThing(creatorGuid);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect(actorGuid);
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect(guid);
	}

	public void EndEffect(int creatorGuid)
	{
		CodexActor _Creator = new CodexActor(creatorGuid);

		// Clear actor flags
		_Creator.ClearActorFlags(THING_AF_ETHEREAL); 
		// Remove pad and shell
		_Creator.SetAlpha(0);
		_Creator.SetAlpha((float)1.0, (float)2000.0);
		_Creator.ClearThingFlags(THING_FLAG_SILENT);
		_Creator.ClearActorFlags(THING_AF_NOSHAPEDISC);	

		CodexThing mist = new CodexThing(mistGuid);
		mist.Remove();

		// make sure the other effects are cancelled too
		_Creator.RemoveActorEffect("ef_disc_mistform2");
		_Creator.RemoveActorEffect("ef_disc_mistform3");
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(mistGuid);
	}

	public void restore(int flags)
	{
		mistGuid = CodexSequence.RestoreInt();
	}
}
