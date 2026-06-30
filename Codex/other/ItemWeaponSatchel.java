/**
 *  ItemWeaponSatchel script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class ItemWeaponSatchel extends Codex
{
	boolean	bEnabled = false;

	public ItemWeaponSatchel()
	{
	}

	void touched(int guid, int toucherGuid, int captureID)
	{
		if(!bEnabled)
			return;

		// get the satchel thing
		CodexThing satchel = new CodexThing(guid);

		// FIXME!! spawn explosion here
		satchel.SpawnThing("exp_grenade");

		// remove the satchel object
		satchel.Remove();

		bEnabled = false;
	}

	public void created(int guid)
	{
		SetTimer(3);
	}	

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		bEnabled = true;
	}
}
