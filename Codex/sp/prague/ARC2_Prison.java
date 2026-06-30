/**
 * Ardan Chantry 2 prisoners script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC2_Prison extends Codex
{
	private PragueChronicle	chronScript;

	private float		_duration = (float)3.0;
	
	private CodexThing	_door;
	private CodexActor	_Prisoner1;
	private CodexActor	_Prisoner2;

	private int			christofGUID;
	private int			wilhemGUID;

	private boolean		bPrisonerConversation = false;

	public static String _params[] = {"Duration", "Prisoner 1", "Prisoner 2"};

	public ARC2_Prison(float duration, CodexActor Prisoner1, CodexActor Prisoner2)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_duration = duration;

		_door = new CodexThing(GetClassThing());
		_Prisoner1 = new CodexActor(Prisoner1.GetGUID());
		_Prisoner2 = new CodexActor(Prisoner2.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
	}
	
	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(guid == _door.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.ARC2_PRISON))
		{
			_door.RotatePivot(1, _duration);

			SetTimer(_duration, 0, clickerGuid);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.ARC2_PRISON))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC2_PRISON);

			PrisonerConversation(CodexPlayer.GetCurrentPlayer(), 0);
		}
	}

	public void PrisonerConversation(int starterGuid, int npcGuid)
	{
		bPrisonerConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "17_1_Prisoners", "17_1_Prisoners.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bPrisonerConversation)
		{
			AIOn();
			bPrisonerConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bPrisonerConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}

}

