/**
 * ItemNodFragment script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ItemNodFragment extends Codex
{
	private PragueChronicle	chronScript;

	private int				christofGUID;
	private int				wilhemGUID;

	private CodexActor		_Christof;
	private CodexItem		nodFragment;

	private boolean			bFragmentConversation = false;

	public ItemNodFragment()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.MON3_NODFRAGMENTRECOVERED))
		{
			CodexSequence.SetChronicleFlag(chronScript.MON3_NODFRAGMENTRECOVERED);

			christofGUID = CodexThing.GuidFromCastID("Christof");
			wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

			_Christof = new CodexActor(christofGUID);

			CodexSequence.ChangeScene("University", "UNIV_9_1.nsd");

			ExecuteText(CodexPlayer.GetCurrentPlayer(), guid, "NodFragment");

			nodFragment = new CodexItem(item);
			nodFragment.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			nodFragment.SetCollideType(THING_COLLIDE_NONE);
		}

		return(false);
	}

	public void textended(int readerGuid)
	{
		FragmentConversation(readerGuid, 0);
	}

	public void FragmentConversation(int starterGuid, int npcGuid)
	{
		bFragmentConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 4);
		ExecuteConversation(starterGuid, npcGuid, "8_2_Fragment", "8_2_Fragment.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bFragmentConversation)
		{
			bFragmentConversation = false;
			CodexCamera.Release(starterGuid);
			AIOn();
		}

		// remove the nod fragment
		nodFragment.Remove();
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bFragmentConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, wilhemGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
					break;

				case 3:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					_Christof.StopActorAction();
					break;
			}
		}
	}
}
