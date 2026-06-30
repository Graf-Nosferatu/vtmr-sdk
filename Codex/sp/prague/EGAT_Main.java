/**
 * East Gate main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class EGAT_Main extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexRegion		_OutsideRegion;
	private	CodexRegion		_ExitRegion;

	private CodexActor		christofActor;

	public static String _params[] = {"Outside region", "Exit region"};

	public EGAT_Main(CodexRegion OutsideRegion, CodexRegion ExitRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_OutsideRegion = new CodexRegion(OutsideRegion.GetGUID());
		_ExitRegion = new CodexRegion(ExitRegion.GetGUID());

		CaptureThing(_OutsideRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofActor = new CodexActor(CodexThing.GuidFromCastID("Christof"));
	}

	public void pathended(int clientGuid)
	{
		christofActor.StopActorAction();
		CodexCamera.Release(CodexPlayer.GetCurrentPlayer());	
	}	

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _OutsideRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.EGAT_OUTSIDEREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.EGAT_OUTSIDEREGION);

			CodexPlayer targetPlayer = new CodexPlayer(causeGUID);

			targetPlayer.Stop();
			targetPlayer.CancelActorAction();

			// do whatever happens when that outside region is entered
			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
			christofActor.PlayMotionSetMode(MOTION_ACTION2, false, (float)15.0);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "outsideMine.ncp", 32);
			CodexSound.PlayVoice(christofActor.GetGUID(), "Christof_5_1_113", 40);
		}
	}
}
