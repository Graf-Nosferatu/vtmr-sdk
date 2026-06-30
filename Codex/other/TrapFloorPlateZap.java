/**
 * Floor plate activated trap
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 
public class TrapFloorPlateZap extends Codex
{
	private CodexRegion		_floorPlateRegion;

	private CodexThing		_floorPlate;
	private int				_range;
	private CodexThing		_beamAnchor;
	private String			_beamTemplate;
	private int				_damageAmount;
	private int				_damageType;
	private int				_repeatable;

	private CodexVector		_floorPlatePos;

	private boolean			bTrapOff = false;

	public static String _params[] = {"Floor plate", "Range to inflict damage;64", "Beam anchor", "Beam template", 
									"Damage amount (every half second);10", "Type of damage;0", "Repeatable;0"};

	public TrapFloorPlateZap(CodexThing floorPlate, int range, CodexThing beamAnchor, String beamTemplate, 
							int damageAmount, int damageType, int repeatable)
	{
		_floorPlateRegion = new CodexRegion(GetClassThing());

		_floorPlate = new CodexThing(floorPlate.GetGUID());
		_range = range;
		_beamAnchor = new CodexThing(beamAnchor.GetGUID());
		_beamTemplate = beamTemplate;
		_damageAmount = damageAmount;

		/*
		DAMAGE_TYPE_NORMAL			= 0
		DAMAGE_TYPE_LETHAL			= 1
		DAMAGE_TYPE_ELECTRIC		= 2
		DAMAGE_TYPE_FIRE			= 3
		DAMAGE_TYPE_SUN				= 4
		DAMAGE_TYPE_FAITH			= 5
		DAMAGE_TYPE_COLD			= 6
		DAMAGE_TYPE_POISON			= 7
		DAMAGE_TYPE_DISEASE			= 8
		*/

		//if out of range set to 0 for now
		if((damageType < DAMAGE_TYPE_NORMAL) || (damageType > DAMAGE_TYPE_COLD))
			_damageType = DAMAGE_TYPE_NORMAL;
		else
			_damageType = damageType;

		_repeatable = repeatable;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		_floorPlatePos = new CodexVector(_floorPlate.GetPosition());

		// make the floor plate no collide so they can properly walk "over it" and click "on it"
		// note: there MUST be a walkable surface underneath the plate for this to work and look right
		_floorPlate.SetCollideType(THING_COLLIDE_NONE);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!bTrapOff)
		{
			SetTimer(0, 0, (float)causeGUID);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		CodexPlayer		dudeToZap;
		CodexVector		dudeCurrentPos;
		CodexThing		beam;
		int				beamGuid;
		int				smokeGuid;
		float[]			offset = new float[3];
		boolean			wasDead = false;

		// create a new guy to zap
		dudeToZap = new CodexPlayer((int)arg0);

		// if they died as a result of the zap in the previous damage (or from some other reason), 
		// set wasDead to true
		if((dudeToZap.GetActorFlags() & THING_AF_DEAD) != 0)
		{
			wasDead = true;
		}
		else
		{
			// damage current guy
			dudeToZap.DamageActor(_damageAmount, _damageType, 0);
		}

		// get the position of the guy
		dudeCurrentPos = new CodexVector(dudeToZap.GetPosition());

		// subtract the floorplate's position from the guy's position (both vectors)
		dudeCurrentPos.Sub(_floorPlatePos);

		// if the distance between them is less the defined range and they didn't die from the previous timer,
		// attach the beam and call the timer again to damage them
		if((dudeCurrentPos.Len() < _range) && (!wasDead))
		{
			// if this is the first time into the timer for this particular player, arg1 will be 0
			if(arg1 == 0)
			{
				// move the floor plate down to indicate they've triggered the trap
				_floorPlate.MoveToFrame(1, 20);

				// spawn a beam at the player's position
				beamGuid = dudeToZap.SpawnThing(_beamTemplate);
				beam = new CodexThing(beamGuid);

				// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
				// then set the target of the beam to the anchor that was linked in the script
				beam.AllocateFrames(2);
				beam.SetFramePosition(1, _beamAnchor.GetPosition());

				// attach thing needs an array of floats as a parm, using no offset essentially
				offset[0] = 0; 
				offset[1] = 0; 
				offset[2] = 0;

				// attach the beam to their jaw bone - if FindBone fails, it will attach it to the player's origin
				dudeToZap.AttachThing(beamGuid, dudeToZap.FindBone(MOTIONTAG_HELMET), offset, ATTACH_FLAG_AUTOREMOVE);

				// create a smoke plume at their position
				smokeGuid = beam.SpawnThing("smokeplume");

				// attach that smoke to the player
				dudeToZap.AttachThing(smokeGuid, dudeToZap.FindBone(MOTIONTAG_HELMET), offset, ATTACH_FLAG_AUTOREMOVE);
			}
			// else they already have a beam - arg1 that was passed the previous time through this timer
			else
			{
				beamGuid = (int)arg1;
				smokeGuid = (int)arg2;
			}

			// call the timer to damage them again
			SetTimer((float)(0.5), 0, arg0, beamGuid, smokeGuid);
		}
		else 
		{
			// detach the beam and smoke from the player
			dudeToZap.DetachThing((int)arg1);
			dudeToZap.DetachThing((int)arg2);

			///////////////////////////////////////////////////////////////////

			if(_repeatable == 0)
			{
				bTrapOff = true;
			}
			else
			{
				// move the plate back up to its starting posision to indicate the trap is "off"
				_floorPlate.MoveToFrame(0, 20);
			}	
		}
	}

	public void save(int flags)
	{
		// only save if the trap isn't repeatable
		if(_repeatable == 0)
			CodexSequence.SaveBoolean(bTrapOff);
	}
 
	public void restore(int flags)
	{
		// only restore if the trap isn't repeatable
		if(_repeatable == 0)
			bTrapOff = CodexSequence.RestoreBoolean();
	}
}



