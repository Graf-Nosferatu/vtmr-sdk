/**
 * Warehouse 1 Thorne 36.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class WAR1_Thorne extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int TIMER_ID_VAMPIREAPPROACH	= 1;
	private static final int TIMER_ID_THORNEGUNFIRE		= 2;
	private static final int TIMER_ID_THORNELINES		= 3;

	private CodexActor		_Thorne;
	private CodexActor		_Vampire1;
	private CodexActor		_Vampire2;
	private CodexActor		_Vampire3;

	private int				christofGUID;
	private int				georgeGUID;
	private int				lilyGUID;
	private int				pinkGUID;
	private int				samuelGUID;
	private int				wilhemGUID;

	private float[]			pos;

	private CodexRegion		_ThorneGunFireRegion;
	private CodexRegion		_ThorneGoneRegion;

	private boolean			bThorneGunFireConversation	= false;
	private boolean			bThorneGoneConversation = false;
	public boolean			bGeorgeReturnConversation = false;

	public static String _params[] = {"Thorne", "Thorne Gun Fire region", "Vampire1", "Vampire2", 
										"Vampire3", "Thorne Gone region"};

	public WAR1_Thorne(CodexActor Thorne, CodexRegion ThorneGunFireRegion, CodexActor Vampire1, CodexActor Vampire2, 
						CodexActor Vampire3, CodexRegion ThorneGoneRegion)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_Thorne = new CodexActor(Thorne.GetGUID());
		_ThorneGunFireRegion = new CodexRegion(ThorneGunFireRegion.GetGUID());
		_Vampire1 = new CodexActor(Vampire1.GetGUID());
		_Vampire2 = new CodexActor(Vampire2.GetGUID());
		_Vampire3 = new CodexActor(Vampire3.GetGUID());
		_ThorneGoneRegion = new CodexRegion(ThorneGoneRegion.GetGUID());

		CaptureThing(_Thorne.GetGUID());
		CaptureThing(_ThorneGunFireRegion.GetGUID());
		CaptureThing(_Vampire1.GetGUID());
		CaptureThing(_Vampire2.GetGUID());
		CaptureThing(_Vampire3.GetGUID());
		CaptureThing(_ThorneGoneRegion.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		georgeGUID = _Thorne.GetGUID();
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		pinkGUID = CodexThing.GuidFromCastID("Pink");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		if(CodexSequence.GetChronicleFlag(chronScript.WAR1_THORNEGUNFIRE))
		{
			// remove thorne from the level if the gun fire scene has taken place already
			_Thorne.Remove();
		}
		else
		{
			_Thorne.OverrideActorWeapon("pistol", false, false);
		}
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _Thorne.GetGUID())
		{
			// play default line here
			GeorgeReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _ThorneGunFireRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.WAR1_THORNEGUNFIRE))
		{
			CodexSequence.SetChronicleFlag(chronScript.WAR1_THORNEGUNFIRE);

			// take vampire1, vampire2, vampire3 and walk them out
			pos = _Vampire1.GetFramePosition(1);
			_Vampire1.SendActorToPos(pos, (float)90.0);

			pos = _Vampire2.GetFramePosition(1);
			_Vampire2.SendActorToPos(pos, (float)90.0);

			pos = _Vampire3.GetFramePosition(1);
			_Vampire3.SendActorToPos(pos, (float)90.0);

			SetTimer(2, TIMER_ID_VAMPIREAPPROACH, causeGUID);
		}
		else if(guid == _ThorneGoneRegion.GetGUID() &&
				!CodexSequence.GetChronicleFlag(chronScript.WAR1_THORNEGONE) &&
				CodexSequence.GetChronicleFlag(chronScript.WAR3_BIGAL))
		{
			CodexSequence.SetChronicleFlag(chronScript.WAR1_THORNEGONE);

			ThorneGoneConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _Vampire1.GetGUID())
		{
			SetTimer(2, TIMER_ID_THORNEGUNFIRE, (float)_Vampire2.GetGUID());
		}
		else if(guid == _Vampire2.GetGUID())
		{
			SetTimer(2, TIMER_ID_THORNEGUNFIRE, (float)_Vampire3.GetGUID());
		}
		else if(guid == _Vampire3.GetGUID())
		{
			SetTimer(2, TIMER_ID_THORNELINES, causeID);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_VAMPIREAPPROACH:

				SetTimer(2, TIMER_ID_THORNEGUNFIRE, (float)_Vampire1.GetGUID());
				break;

			case TIMER_ID_THORNEGUNFIRE:

				// new actor based on the arg passed to timer
				CodexActor vamp = new CodexActor((int)arg0);
				
				// play thorne firing gun animation
				_Thorne.ActorActionAttack(vamp.GetGUID(), MOTION_PISTOL, 0);

				// "thorne kills" the vampire - this starts a chain as the script kills the other vamps
				vamp.DamageActor(1000, DAMAGE_TYPE_NORMAL, 0);
				break;

			case TIMER_ID_THORNELINES:

				ThorneConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
		}
	}

	public void ThorneConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_2.mp3", 50);
		bThorneGunFireConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "36_1_Thorne", "36_1_Thorne.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void GeorgeReturnConversation(int starterGuid, int npcGuid)
	{
		bGeorgeReturnConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "36_1_GeorgeReturn", "36_1_GeorgeReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void ThorneGoneConversation(int starterGuid, int npcGuid)
	{
		bThorneGoneConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "36_1_ThorneGone", "36_1_ThorneGone.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bThorneGunFireConversation)
		{
			CodexSound.PopMusic();
			_Thorne.PlayMotionSetMode(MOTION_SPECIAL4, false, (float)30.0);
			AIOn();
			bThorneGunFireConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}

		if(bGeorgeReturnConversation)
		{
			AIOn();
			bGeorgeReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bThorneGoneConversation)
		{
			AIOn();
			bThorneGoneConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bThorneGunFireConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, georgeGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, georgeGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, pinkGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, georgeGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, georgeGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, georgeGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 7:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 8:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, georgeGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 11:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 12:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, georgeGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 13:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							_Thorne.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, georgeGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bThorneGunFireConversation)

		if(bThorneGoneConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, samuelGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 2:
							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bThorneGoneConversation)

		if(bGeorgeReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} 
	}
}

