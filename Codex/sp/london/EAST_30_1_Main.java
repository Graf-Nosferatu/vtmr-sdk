/**
 * London East 30.1 Main script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EAST_30_1_Main extends Codex
{
	private LondonChronicle	chronScript;

	private CodexThing		_werewolfGhost;
	private CodexThing		_fence1;
	private CodexThing		_fence2;
	private CodexThing		_fence3;

	private boolean			bTriggered = false;

	public static String _params[] =	{"Werewolf Ghost", "Fence 1", "Fence 2", "Fence 3"};

	public EAST_30_1_Main(CodexThing werewolfGhost, CodexThing fence1, CodexThing fence2, CodexThing fence3)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_werewolfGhost = new CodexThing(werewolfGhost.GetGUID());

		_fence1 = new CodexThing(fence1.GetGUID());
		_fence2 = new CodexThing(fence2.GetGUID());
		_fence3 = new CodexThing(fence3.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{

	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.TOL4_FINDHEART) && !bTriggered)
		{
			bTriggered = true;
			// bewm.
			_werewolfGhost.SpawnThing("werewolf");
			new CodexSound("explosion_firey", 1000, 3000, 100, 0, 0, _werewolfGhost.GetGUID());

			_fence1.Trigger(0, 0, 0, 0, 0, 0);
			_fence2.Trigger(0, 0, 0, 0, 0, 0);
			_fence3.Trigger(0, 0, 0, 0, 0, 0);
		}
	}

	public void save(int flags)
	{
			CodexSequence.SaveBoolean(bTriggered);
	}
 
	public void restore(int flags)
	{
			bTriggered = CodexSequence.RestoreBoolean();
	}
}