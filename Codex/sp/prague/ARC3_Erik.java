/**
 * Ardan Chantry 3 Erik script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC3_Erik extends Codex
{
	private PragueChronicle	chronScript;

	private CodexPlayer		_Erik;
	private CodexActor		_Ardan;
	private CodexThing		_magicDoor;

	private int				christofGUID;
	private int				erikGUID;

	public static String _params[] = {"Erik", "Ardan", "Magic Door"};

	private boolean			bErikConversation		= false;
	
	public ARC3_Erik(CodexPlayer Erik, CodexActor Ardan, CodexThing magicDoor)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Erik = new CodexPlayer(Erik.GetGUID());
		_Ardan = new CodexActor(Ardan.GetGUID());
		_magicDoor = new CodexThing(magicDoor.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = _Erik.GetGUID();

		if(!CodexSequence.GetChronicleFlag(chronScript.ARC3_ERIK))
		{
			_Erik.SetActorFlags(THING_AF_INVUL);
			_Erik.SetActorFlags(THING_AF_NEUTRAL);
			_Erik.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		}

		_Ardan.Remove();
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		int duration;

		switch(timerID)
		{
/*			case 1:

				CodexCamera.SetupCutscene((int)arg0, christofGUID, erikGUID);
				CodexCamera.SetShot((int)arg0, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);

				SetTimer(0.1f, 2, arg0);
				break;

			case 2:

				CodexCamera.SetupCutscene((int)arg0, ardanGUID, erikGUID);
				CodexCamera.SetShot((int)arg0, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 5);

				SetTimer(5.0f, 3, arg0);
				break;

			case 3:

				duration = _Ardan.PlayMotionSetMode(MOTION_SPELLTHROW, false, 30.0f);

				SetTimer((float)(duration / 1000), 4, arg0);
				break;

			case 4:

				// particles
				SetTimer(2.0f, 5, arg0);
				break;

			case 5:

				CodexCamera.SetupCutscene((int)arg0, ardanGUID, christofGUID);
				CodexCamera.SetShot((int)arg0, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 1, 2);
				SetTimer(4.0f, 6, arg0);
				break;

			case 6:

				CodexCamera.SetupCutscene((int)arg0, ardanGUID, 0);
				CodexCamera.SetShot((int)arg0, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);

				duration = _Ardan.PlayMotionSetMode(MOTION_SPELL, false, 30.0f);
				SetTimer((float)(duration / 1000), 7, arg0);
				break;

			case 7:

				_Ardan.Remove();
				SetTimer(1.5f, 8, arg0);
				break;

			case 8:

				CodexCamera.Release((int)arg0);
				break;
*/
			case 9:

				ErikConversation((int)arg0, 0);
				break;

		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.ARC3_GARGOYLEDEAD) &&
			!CodexSequence.GetChronicleFlag(chronScript.ARC3_ERIK))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC3_ERIK);

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "ARC3ErikFreedom.ncp", 30);

			_Erik.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);

			SetTimer(3, 9, causeGUID);
		}
	}

	public void ErikConversation(int starterGuid, int npcGuid)
	{
		bErikConversation = true;
		AIOff();
		_Erik.StopActorAction();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "17_1_Erik", "17_1_Erik.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bErikConversation)
		{
			AIOn();
			bErikConversation = false;

			// award conversation XP
			CodexPlayer.AwardPartyExperience(100);
			
			// Add Erik to the party
			_Erik.ClearActorFlags(THING_AF_INVUL);
			_Erik.ClearActorFlags(THING_AF_NEUTRAL);

			_Erik.AddToParty();
			CodexCamera.Release(starterGuid);

			// remove blocking door
			_magicDoor.Remove();
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bErikConversation)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, erikGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 0);
					break;

				case 2:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 3:

					CodexCamera.SetupCutscene(starterGuid, erikGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 4:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, erikGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 1, 0);
					break;
			}
		}
	}
}

