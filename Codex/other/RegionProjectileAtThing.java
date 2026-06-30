/**
 *  RegionProjectileAtThing script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class RegionProjectileAtThing extends Codex
{
	private static final int	TIMER_ID_INACTIVE = 1;
	private static final int	TIMER_ID_MULTIPLE = 2;
	
	private CodexThing			_emitter;
	private CodexThing			_targetObject;
	private String				_templateName;
	private int					_numProjectiles;
	private int					_numTimesToFire;
	private float				_delayBetween;
	private int					_scatter;
	private float				_delayInactive;

	private int					i = 0;

	private boolean				bActive = false;
	private float[]				offset;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Emitter thing", "Target object", "Template to fire", "Number of projectiles;1", 
									"Number of times to fire (0 is unlimited);0", "Delay in between;0.0", "Scatter;0", 
									"Delay Inactive;5.0"};

	public RegionProjectileAtThing(CodexThing emitter, CodexThing targetObject, String templateName, int numProjectiles, 
									int numTimesToFire, float delayBetween, int scatter, 
									float delayInactive)
	{
		_emitter		= new CodexThing(emitter.GetGUID());
		_targetObject	= new CodexThing(targetObject.GetGUID());
		_templateName	= templateName;
		_numProjectiles	= numProjectiles;
		_numTimesToFire = numTimesToFire;
		_delayBetween	= delayBetween;
		_scatter		= scatter;
		_delayInactive	= delayInactive;
		
		offset			= new float[3];
		offset[0] = 0;
		offset[1] = 0;
		offset[2] = 0;
	}

	// --------------------------------------------------------------------------------------------

	public void FireProjectile()
	{
		_emitter.FireProjectileAtThing(_templateName, _targetObject.GetGUID(), offset);

		bActive = true;
		SetTimer(_delayInactive, TIMER_ID_INACTIVE);
		SetTimer(_delayBetween, TIMER_ID_MULTIPLE, (float)_targetObject.GetGUID(), 1);
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

		// if it's not unlimited fire
		if(_numTimesToFire != 0) 
		{
			// count number of times it's been fired
			if(i < _numTimesToFire)
			{
				FireProjectile();
				i++;
			}
			else
				return;
		}
		else
		{
			FireProjectile();
		}
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

	public void save(int flags)
	{
		CodexSequence.SaveInt(i);
	}
 
	public void restore(int flags)
	{
		i = CodexSequence.RestoreInt();
	}
}
