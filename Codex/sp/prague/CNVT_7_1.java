/**
 * Convent, scene 7.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CNVT_7_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_ATTACKCONVERSATION	= 1;
	private static final int TIMER_ID_LASTENEMY				= 2;
	private static final int TIMER_ID_LOVECONVERSATION		= 3;
	private static final int TIMER_ID_ENDSCENE				= 4;

	private CodexActor		_Christof;
	private CodexActor		_Anezka;
	private CodexActor		_Nun;
	private CodexActor		_ArchBishop;
	private CodexActor		_Ecaterina;
	private CodexActor		_Cosmas;

	private CodexActor		_Revenant1;
	private CodexActor		_Revenant2;
	private CodexActor		_Szlachta;

	private CodexActor		Nun;

	private float			pos[];

	private int				christofGUID;
	private int				anezkaGUID;
	private int				archbishopGUID;
	private int				revenant1GUID;
	private int				revenant2GUID;
	private int				nunGUID;
	private int				cosmasGUID;
	private int				ecaterinaGUID;

	private int				deadEnemies	= 0;

	private boolean			bConventAttackConversation = false;
	private boolean			bEcaterinaConversation = false;
	private boolean			bLoveConversation = false;

	public static String _params[] = {"Anezka", "Nun", "ArchBishop", "Ecaterina", "Cosmas", "Revenant 1", "Revenant 2", "Szlachta"};

	public CNVT_7_1(CodexActor Anezka, CodexActor Nun, CodexActor ArchBishop, CodexActor Ecaterina, CodexActor Cosmas, 
					CodexActor Revenant1, CodexActor Revenant2, CodexActor Szlachta)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Anezka		= new CodexActor(Anezka.GetGUID());
		_Nun		= new CodexActor(Nun.GetGUID());
		_ArchBishop	= new CodexActor(ArchBishop.GetGUID());
		_Ecaterina	= new CodexActor(Ecaterina.GetGUID());
		_Cosmas		= new CodexActor(Cosmas.GetGUID());
		_Revenant1	= new CodexActor(Revenant1.GetGUID());
		_Revenant2	= new CodexActor(Revenant2.GetGUID());
		_Szlachta	= new CodexActor(Szlachta.GetGUID());

		CaptureThing(_ArchBishop.GetGUID());
		CaptureThing(_Revenant1.GetGUID());
		CaptureThing(_Revenant2.GetGUID());
		CaptureThing(_Szlachta.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID	= CodexThing.GuidFromCastID("Christof");
		anezkaGUID		= _Anezka.GetGUID();
		archbishopGUID	= _ArchBishop.GetGUID();
		revenant1GUID	= _Revenant1.GetGUID();
		revenant2GUID	= _Revenant2.GetGUID();
		nunGUID			= _Nun.GetGUID();
		cosmasGUID		= _Cosmas.GetGUID();
		ecaterinaGUID	= _Ecaterina.GetGUID();
		
		_Christof		= new CodexActor(christofGUID);

		// NOTE - just to be sure this is turned back on from OLDT_7_1
		AIClearMainFlags(AIMAIN_FLAG_NOGUARDSEARCH + AIMAIN_FLAG_NOENEMYSEARCH);

		if(!CodexSequence.GetChronicleFlag(chronScript.CNVT_ATTACKCONVERSATION))
		{
			CodexSequence.SetChronicleFlag(chronScript.CNVT_ATTACKCONVERSATION);

			AIOff();
			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CNVTAttack.ncp", 30);

			_Nun.SetActorFlags(THING_AF_INVUL);
			_Nun.SetActorFlags(THING_AF_NEUTRAL);

			_Nun.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

			// so they can't get out to break the scene
			CodexSequence.CloseExit("Convent", 1);

			SetTimer((float)0.01, TIMER_ID_ATTACKCONVERSATION, christofGUID);
		}
	}

	public void arrived(int thingGuid, int frameNum, int captureID)
	{
		if(thingGuid == _ArchBishop.GetGUID())
		{
			_ArchBishop.LookAtThing(_Anezka.GetGUID());
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		// see if all enemies are dead
		if(++deadEnemies == 3)
		{
			// wait while the last one falls
			SetTimer(3, TIMER_ID_LASTENEMY, causeID);
		}
	}	

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_ATTACKCONVERSATION:

				CodexSound.PushMusic("DA_Conversation_2.mp3", 50);
				ConventAttackConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_LASTENEMY:

				CodexSound.PopMusic();

				// remove the bishop for the rest of the scene
				_ArchBishop.Remove();

				// stop the cowering nun
				_Nun.StopActorAction();

				EcaterinaConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_LOVECONVERSATION:

				LoveConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;

			case TIMER_ID_ENDSCENE:

				// reopen the convent
				CodexSequence.OpenExit("Convent", 1);

				CodexCamera.Release(0);
				CodexSequence.ChangeScene("OldTown", "OLDT_7_4.nsd");
				CodexSequence.Jump("OldTown", 4);
				break;
		}
	}

	public void ConventAttackConversation(int starterGuid, int npcGuid)
	{
		bConventAttackConversation = true;
		AIOff();
		ExecuteConversation(starterGuid, npcGuid, "7_1_ConventAttack", "7_1_ConventAttack.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void EcaterinaConversation(int starterGuid, int npcGuid)
	{
		bEcaterinaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "7_1_Ecaterina", "7_1_Ecaterina.nco", CONV_XFLAG_WANTFEEDBACK | CONV_XFLAG_NOAUTOEND);
	}

	public void LoveConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("Anezka_Theme.mp3", 50);
		bLoveConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "7_4_Love", "7_4_Love.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bConventAttackConversation)
		{
			AIOn();
			bConventAttackConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bEcaterinaConversation)
		{
			_Nun.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

			AIOn();
			bEcaterinaConversation = false;
			_Anezka.OverrideActorWeapon("weapLocket", false, false);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);
			SetTimer(2, TIMER_ID_LOVECONVERSATION, starterGuid);
		}

		if(bLoveConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bLoveConversation = false;
			//CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			SetTimer(2, TIMER_ID_ENDSCENE);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bConventAttackConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							//_Christof.PlayMotionSetMode(MOTION_ACTION8, false, (float)30.0);
							break;

						case 1:

							_ArchBishop.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 2:

							_ArchBishop.StopActorAction();
							
							// archbishop leaves
							pos = _ArchBishop.GetFramePosition(1);
							_ArchBishop.SendActorToPos(pos, (float)210.0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, revenant1GUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "CNVTAttack.ncp", 30);
							_Christof.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, anezkaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							_Christof.StopActorAction();
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		} 

		if(bEcaterinaConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)

					{
						case 0:

							_Anezka.LookAtThing(christofGUID);
							_Christof.LookAtThing(anezkaGUID);

							CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 9);
							break;

						case 6:

							// Fade out
							CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)4.0, false);
							break;

						case 7:

							// In case of space-thru, clear effect then fade in
							CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());
							CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)3.0, false);

							_Cosmas.LookAtThing(ecaterinaGUID);
							
							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, cosmasGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 8:

							// In case of space-thru, clear effect then fade in
							CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());
							CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)0.0, false);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							
							// move anezka to position for 7_4_Love conversation and remove nun from view
							pos = _Anezka.GetFramePosition(1);
							_Anezka.SetPosition(pos);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 11:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 12:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 13:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, ecaterinaGUID, cosmasGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 16:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 17:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		}

		if(bLoveConversation)
		{
			switch(curEvent)
			{
				case 0:

					switch(curLine)

					{

						case 0:

							_Anezka.LookAtThing(christofGUID);
							_Christof.LookAtThing(anezkaGUID);

							// Fade in
							CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)2.0, false);
							
							CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 1:

							// In case of space-thru, clear effect
							CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Anezka.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Anezka.StopActorAction();
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.StopActorAction();
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.StopActorAction();
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Anezka.PlayMotionSetMode(MOTION_GESTURE2, false, (float)30.0);
							break;

						case 10:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Anezka.StopActorAction();
							_Christof.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							break;

						case 11:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.StopActorAction();
							break;

						case 12:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
							break;

						case 13:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.StopActorAction();
							break;

						case 14:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							break;

						case 15:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							break;

						case 16:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Christof.StopActorAction();
							_Anezka.StopActorAction();
							break;

						case 17:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 18:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
							break;

						case 19:

							_Christof.StopActorAction();
							_Anezka.PlayMotionSetMode(MOTION_SPECIAL18, false, (float)30.0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 20:

							_Anezka.StopActorAction();
							_Anezka.CancelOverrideActorWeapon();
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 21:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Anezka.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							break;

						case 22:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Anezka.StopActorAction();
							break;

						case 23:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							_Anezka.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
							break;

						case 24:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Anezka.StopActorAction();
							_Christof.PlayMotionSetMode(MOTION_GESTURE5, false, (float)30.0);
							break;

						case 25:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Christof.StopActorAction();
							break;

						case 26:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							_Christof.PlayMotionSetMode(MOTION_GESTURE6, false, (float)30.0);
							break;

						case 27:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							_Christof.StopActorAction();

							pos = _Anezka.GetFramePosition(2);
							_Christof.SendActorToPos(pos, (float)210.0);
							break;

					} // switch(curLine)

					break;

			} // switch(curEvent)
		}
	}
}
