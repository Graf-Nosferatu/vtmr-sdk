/**
 * Society 3 Main 27.2 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC3_Main extends Codex
{
	private LondonChronicle	chronScript;

	private static final int TIMER_ID_FIRSTCONV		= 0;
	private static final int TIMER_ID_GUNFIGHT		= 1;
	private static final int TIMER_ID_KILLMEMBER	= 2;
	private static final int TIMER_ID_WALKTOMEMBER	= 3;
	private static final int TIMER_ID_DEATHMOTION	= 4;

	private CodexRegion		_RelicsRegion;
	private CodexRegion		_BasementRegion;
	private CodexRegion		_MeetMembersRegion;

	private CodexActor		_SocMember1;
	private CodexActor		_SocMember2;
	private CodexActor		_SocMember3;

	private CodexActor		_firstMember;
	private CodexPlayer		_Christof;

	private int				christofGUID;
	private int				leoworkerGUID;

	private boolean			bFirstMemberConversation = false;
	private boolean			bBasementConversation = false;
	private boolean			bMeetMembersConversation = false;
	private boolean			bRelicsConversation = false;

	public static String _params[] = {"Basement region", "Meet members region", "Relics region",
										"Society member 1", "Society member 2", "Society member 3"};

	public SOC3_Main(CodexRegion BasementRegion, CodexRegion MeetMembersRegion, CodexRegion RelicsRegion,
						CodexActor SocMember1, CodexActor SocMember2, CodexActor SocMember3)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		_BasementRegion = new CodexRegion(BasementRegion.GetGUID());
		_MeetMembersRegion = new CodexRegion(MeetMembersRegion.GetGUID());
		_RelicsRegion = new CodexRegion(RelicsRegion.GetGUID());
		_SocMember1 = new CodexActor(SocMember1.GetGUID());
		_SocMember2 = new CodexActor(SocMember2.GetGUID());
		_SocMember3 = new CodexActor(SocMember3.GetGUID());

		CaptureThing(_BasementRegion.GetGUID());
		CaptureThing(_MeetMembersRegion.GetGUID());
		CaptureThing(_RelicsRegion.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		leoworkerGUID = CodexThing.GuidFromCastID("Society1");

		_firstMember = new CodexActor(leoworkerGUID);
		_Christof = new CodexPlayer(christofGUID);

		_firstMember.SetActorFlags(THING_AF_AIPAUSED);

		if(!CodexSequence.GetChronicleFlag(chronScript.SOC3_FIRSTMEMBER))
		{ 
			CodexSequence.SetChronicleFlag(chronScript.SOC3_FIRSTMEMBER);

			// set christof in tattered dark age clothing
			_Christof.SetModel("ChristofRags.nod");
			_Christof.SetPlayerHeadModel("ChristofRagsH.nod");

			// Remove whiteout and fade effect from VYC4_25_1 script
			CodexCamera.AddFlash(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)0.0, 0xffffff, false);
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)0.0, false);

			// to lower his blood level at the start of the modern part of the game
			_Christof.AddActorEffectByValue("ef_decreaseblood", 0, -50, 0, 0);
			
			// hackish way to remove the money they've gained from the DA portion
			CodexConsole.Execute("cash 0");

			// pause society members AI for the members conversation?
			_SocMember1.SetActorFlags(THING_AF_AIPAUSED);
			_SocMember2.SetActorFlags(THING_AF_AIPAUSED);
			_SocMember3.SetActorFlags(THING_AF_AIPAUSED);

			// don't start this conversation on beginscene, leave
			// this timer here, trust me
			SetTimer((float)0.01, TIMER_ID_FIRSTCONV);

			CodexQuest q = new CodexQuest(CodexQuest.Load("L1_EscapeSociety"));
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FIRSTCONV:
				
				FirstMemberConversation(christofGUID, 0);
				break;

/*			case TIMER_ID_GUNFIGHT:

				_Christof.LookAtThing(leoworkerGUID);
				_firstMember.LookAtThing(christofGUID);

				_firstMember.ClearActorFlags(THING_AF_AIPAUSED);
				_firstMember.ActorActionAttack(christofGUID, MOTION_PISTOL, 0);

				SetTimer((float)1.5, TIMER_ID_WALKTOMEMBER);
				break;

			case TIMER_ID_WALKTOMEMBER:
				_Christof.SendActorToPos(_firstMember.GetPosition(), (float)90.0);

				SetTimer((float)1.0, TIMER_ID_KILLMEMBER);
				break;

			case TIMER_ID_KILLMEMBER:

				_Christof.Stop();
				_Christof.StopActorAction();
				_Christof.PlayMotionSetMode(MOTION_PUNCH, false, (float)30.0);

				SetTimer((float)0.5, TIMER_ID_DEATHMOTION);
				break;

			case TIMER_ID_DEATHMOTION:

				_firstMember.PlayMotionSetMode(MOTION_DEATHQUICK, false, (float)30.0);
				_firstMember.DamageActor(1000, DAMAGE_TYPE_NORMAL, 0);
				_firstMember.SetActorFlags(THING_AF_DEAD);
				break;
*/		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _BasementRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SOC3_BASEMENT))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOC3_BASEMENT);

			BasementConversation(causeGUID, 0);
		}

		if(guid == _MeetMembersRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SOC3_MEETMEMBERS))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOC3_MEETMEMBERS);
		
			MeetMembersConversation(causeGUID, 0);
		}

		if(guid == _RelicsRegion.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.SOC3_RELICS))
		{
			CodexSequence.SetChronicleFlag(chronScript.SOC3_RELICS);

			RelicsConversation(causeGUID, 0);
		}
	}

	public void FirstMemberConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_ChristofTheme2.mp3", 50);
		bFirstMemberConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "27_2_FirstMember", "27_2_FirstMember.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void BasementConversation(int starterGuid, int npcGuid)
	{
		bBasementConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "27_2_Basement", "27_2_Basement.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void RelicsConversation(int starterGuid, int npcGuid)
	{
		bRelicsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 2);
		ExecuteConversation(starterGuid, npcGuid, "27_2_Relics", "27_2_Relics.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void MeetMembersConversation(int starterGuid, int npcGuid)
	{
		bMeetMembersConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 3);
		ExecuteConversation(starterGuid, npcGuid, "27_2_MeetMembers", "27_2_MeetMembers.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bFirstMemberConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			bFirstMemberConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);

			// auto advance - mostly for XP gained during final stages of DA
			// portion of the game
			CodexSequence.Advance(starterGuid);
		}

		if(bBasementConversation)
		{
			AIOn();
			bBasementConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bRelicsConversation)
		{
			AIOn();
			bRelicsConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bMeetMembersConversation)
		{
			AIOn();
			bMeetMembersConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);

			// turn their individual AI back on so they'll fight after conversation
			_SocMember1.ClearActorFlags(THING_AF_AIPAUSED);
			_SocMember2.ClearActorFlags(THING_AF_AIPAUSED);
			_SocMember3.ClearActorFlags(THING_AF_AIPAUSED);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bFirstMemberConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, leoworkerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, leoworkerGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 1, 12);
							break;

						case 4:

							//CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 1, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 6:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
							break;

						case 9:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							//SetTimer(0, TIMER_ID_GUNFIGHT);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 1, 0);

							// Let's get it on!
							_Christof.LookAtThing(leoworkerGUID);
							_firstMember.LookAtThing(christofGUID);

							_firstMember.ClearActorFlags(THING_AF_AIPAUSED);
							_firstMember.ActorActionAttack(christofGUID, MOTION_PISTOL, 0);
							break;

						case 11:

							_Christof.SendActorToPos(_firstMember.GetPosition(), (float)90.0);
							break;

						case 12:

							_Christof.Stop();
							_Christof.StopActorAction();
							_Christof.PlayMotionSetMode(MOTION_PUNCH, false, (float)30.0);
							break;

						case 13:

							_firstMember.PlayMotionSetMode(MOTION_DEATHQUICK, false, (float)30.0);
							_firstMember.DamageActor(1000, DAMAGE_TYPE_NORMAL, 0);
							_firstMember.SetActorFlags(THING_AF_DEAD);
							break;

						case 14:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 25);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)

		} // if(bFirstMemberConversation)

		if(bRelicsConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bBasementConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bBasementConversation)

		if(bMeetMembersConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMLOW, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		} // if(bMeetMembersConversation)
	}
}

