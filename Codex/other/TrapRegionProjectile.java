/**
 *  TrapRegionProjectile script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class TrapRegionProjectile extends Codex
{
	private static final int	TIMER_ID_INACTIVE = 1;
	private static final int	TIMER_ID_MULTIPLE = 2;
	
	private CodexThing			_emitter;
	private String				_templateName;
	private int					_numProjectiles;
	private float				_delayBetween;
	private int					_scatter;
	private float				_delayInactive;

	private boolean				bActive = false;
	private float[]				offset;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Emitter thing", "Template to fire", "Number of projectiles;1", "Delay in between;0.0", "Scatter;0", "Delay Inactive;5.0"};

	public TrapRegionProjectile(CodexThing emitter, String templateName, int numProjectiles, float delayBetween, int scatter, float delayInactive)
	{
		_emitter		= new CodexThing(emitter.GetGUID());
		_templateName	= templateName;
		_numProjectiles	= numProjectiles;
		_delayBetween	= delayBetween;
		_scatter		= scatter;
		_delayInactive	= delayInactive;
		
		offset			= new float[3];
		offset[0] = 0;
		offset[1] = 0;
		offset[2] = 0;
	}

	// --------------------------------------------------------------------------------------------

	public void entered(int guid, int causeGUID, int captureID)
	{
		// if the trap is active, return
		if(bActive)
			return;

		// if the thing entering the region is not an actor, return
		if(!IsActorGuid(causeGUID))
			return;

		_emitter.FireProjectileAtThing(_templateName, causeGUID, offset);
		bActive = true;
		SetTimer(_delayInactive, TIMER_ID_INACTIVE);
		if(_numProjectiles > 1)
			SetTimer(_delayBetween, TIMER_ID_MULTIPLE, (float)causeGUID, 1);
	}

	// --------------------------------------------------------------------------------------------

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(timerID == TIMER_ID_INACTIVE)
		{
			bActive = false;
		}
		else
		{
			_emitter.FireProjectileAtThing(_templateName, (int)arg0, offset);

			if((int)arg1 + 1 < _numProjectiles)
				SetTimer(_delayBetween, TIMER_ID_MULTIPLE, (int)arg0, (float)((int)arg1 + 1));
		}
	}

}
