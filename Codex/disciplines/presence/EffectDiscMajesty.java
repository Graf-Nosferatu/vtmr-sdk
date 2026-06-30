/**
 * Effect Disc Majesty script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/


public class EffectDiscMajesty extends Codex
{
	private static final float	TICK_RATE		= 2.0f;
	private static final int	TICK_DURATION	= 5000;
	private static final float	RADIUS			= 384.0f;

	private float []			position;

	private int					padGuid;
	private int					_casterGuid;

	// --------------------------------------------------------------------------------------------

	public EffectDiscMajesty()
	{
		position = new float[3];
	}

	// --------------------------------------------------------------------------------------------

	public void effectstarted(int actorGuid, int effectGuid, int creatorGuid, int duration)
	{
		_casterGuid	= actorGuid;
		CodexActor caster	= new CodexActor(_casterGuid);

		// initial effect
		caster.SpawnThing("yellowMagic");
		
		// Running effect
		padGuid = caster.SpawnThing("ankhsPadYellow");

		float[] offset = new float[3];
		offset[0] = offset[1] = offset[2] = 0.0f;

		caster.AttachThing(padGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);

		CaptureThing(actorGuid);

		// start ticking
		SetTimer(0.1f);
	}

	public void effectended(int actorGuid, int effectGuid, int creatorGuid, int reason)
	{
		EndEffect();
	}

	public void killed(int guid, int causeID, int captureID)
	{
		EndEffect();
	}

	public void EndEffect()
	{
		CodexThing pad	= new CodexThing(padGuid);
		pad.Remove();

		KillAllTimers();
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexActor caster	= new CodexActor(_casterGuid);

		// retrieve the caster's position
		position = caster.GetPosition();

		CodexCollision query = new CodexCollision();

		query.IgnoreAll();
		query.AcceptType(THING_TYPE_ACTOR);

		if(!IsPlayerGuid(caster.GetGUID()))
			query.AcceptType(THING_TYPE_PLAYER);

		int numResults = query.ThingsInSphere(position, RADIUS, caster.GetGUID());

		for(int i = 0; i < numResults; i++)
		{
			int result = query.GetResult(i);
			if(result == caster.GetGUID())
				continue;

			CodexActor victim = new CodexActor(result);
			int effectGuid = victim.FindActorEffect("ef_disc_majesty2");
			
			if(effectGuid != 0)
				victim.ExpandActorEffect(effectGuid, TICK_DURATION, false);
			else
				victim.AddActorEffectByLevel("ef_disc_majesty2", TICK_DURATION, 0, caster.GetGUID(), 4);
		}

		query.Free();

		KillAllTimers();
		// prepare the next tick
		SetTimer(TICK_RATE);
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(padGuid);
		CodexSequence.SaveInt(_casterGuid);
	}

	public void restore(int flags)
	{
		padGuid = CodexSequence.RestoreInt();
		_casterGuid = CodexSequence.RestoreInt();
	}

}