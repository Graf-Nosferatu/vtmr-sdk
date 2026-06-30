/**
 *  SpiderSac script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ClassSpiderSac extends Codex
{
	boolean	bEnabled = false;

	public ClassSpiderSac()
	{
	}

	void touched(int guid, int toucherGuid, int captureID)
	{
		if(!bEnabled)
			return;

		if(IsPlayerGuid(toucherGuid))
		{
			// get the sack thing
			CodexThing sack = new CodexThing(guid);

			// Dust as sack opens
			sack.SpawnThing("dust");

			// create the baby spiders
			sack.SpawnThing("ghoulSpiderBaby");
			sack.SpawnThing("ghoulSpiderBaby");
			sack.SpawnThing("ghoulSpiderBaby");
			sack.SpawnThing("ghoulSpiderBaby");
			sack.SpawnThing("ghoulSpiderBaby");
			
			sack.Remove();

			bEnabled = false;
		}
	}

	public void created(int guid)
	{
		SetTimer(3);
	}	

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		bEnabled = true;
	}

	public void killed(int guid, int causeID, int captureID)
	{
		bEnabled = false;
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bEnabled);
	}
 
	public void restore(int flags)
	{
		bEnabled = CodexSequence.RestoreBoolean();
	}
}
