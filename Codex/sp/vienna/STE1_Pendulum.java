/**
 *  Stephansdom1, Pendulum script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STE1_Pendulum extends Codex
{
	private int				_frameNum = 1;
	private float			_duration = (float)1.0;

	private CodexPlayer		riskTaker;

	private CodexThing		_damageRegion;
	private CodexThing		_deactivatingSwitch;
	
	private CodexThing		pendulum;
	private int				pendulumGuid = 0;
	
	private boolean			bTrapActive = true;
	private boolean			bPendulumPosition = false;
	private boolean			bDamaging = false;
	private boolean			bFinalMoveDone = false;
	
	// --------------------------------------------------------------------------------------------

	public static String _params[] =  {"Damage region", "Deactivating switch"};

	public STE1_Pendulum(CodexThing damageRegion, CodexThing deactivatingSwitch)
	{
		_damageRegion = new CodexThing(damageRegion.GetGUID());
		_deactivatingSwitch = new CodexThing(deactivatingSwitch.GetGUID());

		CaptureThing(_damageRegion.GetGUID());
		CaptureThing(_deactivatingSwitch.GetGUID());

		pendulumGuid = GetClassThing();
		pendulum = new CodexThing(pendulumGuid);
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		if(bTrapActive)
		{
			pendulum.RotatePivot(_frameNum, _duration);
		}

		_deactivatingSwitch.SetDescriptionID("GEN_SWITCH");
	}

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!IsPlayerGuid(causeGuid) || !bTrapActive)
			return;
 
		riskTaker = new CodexPlayer(causeGuid);

		int effectGuid = riskTaker.FindActorEffect("ef_disc_mistform");

		if(effectGuid == 0)
		{
			bDamaging = true;
			SetTimer((float)(.5));
		}
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		// move switch
		_deactivatingSwitch.MoveToFrame(_frameNum, 50);

		// stop pendulum from swinging here
		bTrapActive = false;
	}

	public void exited(int guid, int causeGUID, int captureID)
	{
		bDamaging = false; 
	}
 

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid != pendulumGuid)
			return;

		if(!bTrapActive && !bPendulumPosition && !bFinalMoveDone)
		{
			_duration = -_duration;
			pendulum.RotatePivot(_frameNum, _duration);

			bFinalMoveDone = true;
		}

		if(!bFinalMoveDone)
		{
			_duration = -_duration;
			pendulum.RotatePivot(_frameNum, _duration);

			bPendulumPosition = !bPendulumPosition;
		}

	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(!bDamaging)
			return;
 
		// apply damage here...  
		riskTaker.DamageActor(100, DAMAGE_TYPE_NORMAL, riskTaker.GetGUID());
 
		if(riskTaker.GetActorHealth() > 0)
			SetTimer((float)(.5)); 
		else
		{
			bDamaging = false;
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bTrapActive);
	}

	public void restore(int flags)
	{
		bTrapActive = CodexSequence.RestoreBoolean();
	}
}
