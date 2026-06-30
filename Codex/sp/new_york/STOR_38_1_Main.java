/**
 * Barclay storage 38.1 Main Script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class STOR_38_1_Main extends Codex
{
	private NewYorkChronicle	chronScript;
	
	public CodexRegion	_alexandraRegion;
	public CodexThing	_ghost1;
	public CodexThing	_ghost2;
	public CodexActor	_alexandra;
	public CodexThing	_goon1Destination;
	public CodexThing	_goon2Destination;
	private CodexThing	_easel;
	public CodexActor	_goon1;
	public CodexActor	_goon2;
	public CodexActor	_Lily;

	private int			alexandraGUID;
	private int			christofGUID;
	private int			lilyGUID;
	private int			samuelGUID;
	private int			wilhemGUID;

	private int			deadGoons = 0;

	public boolean		b38_1_MeetAlexandraConversation = false;
	public boolean		b38_1_AfterGoonsConversation = false;
	public boolean		bAlexandraReturnConversation = false;

	public static String _params[] =	{"AlexandraRegion", "Ghost1", "Ghost2", "Destination 1", "Destination 2", "Alexandra", "Easel"};

	public STOR_38_1_Main(CodexRegion alexandraRegion, CodexThing ghost1, CodexThing ghost2, CodexThing goon1Destination, CodexThing goon2Destination, CodexActor alexandra, CodexThing easel)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_alexandraRegion = new CodexRegion(alexandraRegion.GetGUID());
		_ghost1 = new CodexThing(ghost1.GetGUID());
		_ghost2 = new CodexThing(ghost2.GetGUID());
		_alexandra = new CodexActor(alexandra.GetGUID());
		_easel = new CodexThing(easel.GetGUID());

		CaptureThing(_alexandraRegion.GetGUID());
		CaptureThing(_alexandra.GetGUID());
		CaptureThing(_easel.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		alexandraGUID = _alexandra.GetGUID();
		christofGUID = CodexThing.GuidFromCastID("Christof");
		lilyGUID = CodexThing.GuidFromCastID("Lily");
		samuelGUID = CodexThing.GuidFromCastID("Samuel");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_easel.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

		_Lily = new CodexActor(lilyGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.STOR_SPAWNGOONS))
		{
			_goon1 = new CodexActor(_ghost1.SpawnThing("ventrue_bat"));
			_goon2 = new CodexActor(_ghost2.SpawnThing("ventrue_bat"));

			CaptureThing(_goon1.GetGUID());
			CaptureThing(_goon2.GetGUID());

			_goon1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_goon1.SetCollideType(THING_COLLIDE_NONE);
			_goon1.SetActorFlags(THING_AF_AIPAUSED);

			_goon2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_goon2.SetCollideType(THING_COLLIDE_NONE);
			_goon2.SetActorFlags(THING_AF_AIPAUSED);

			CodexSequence.SetChronicleFlag(chronScript.STOR_SPAWNGOONS);
		}
	}
	
	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _easel.GetGUID())
		{
			// remove the easel with the painting on it
			_easel.Remove();
			// spawn in the easel sans Vuk painting
			_alexandra.SpawnThing("easelEmpty");	

			CodexSequence.SetChronicleFlag(chronScript.STOR_HAVEPAINTING);

			// play her stand where she's not painting
			_alexandra.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		}
		else if(guid == _alexandra.GetGUID())
		{
			// play default line here
			AlexandraReturnConversation(clickerGuid, 0);
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.STOR_MEETALEXANDRA))
		{
			CodexSequence.SetChronicleFlag(chronScript.STOR_MEETALEXANDRA);

			c38_1_MeetAlexandraConversation(causeGUID, 0);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(++deadGoons == 2)
		{
			c38_1_AfterGoonsConversation(causeID, 0);
		}
	}

	public void c38_1_MeetAlexandraConversation(int starterGuid, int npcGuid)
	{
		CodexSound.PushMusic("MD_Conversation_2.mp3", 50);
		b38_1_MeetAlexandraConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "38_1_MeetAlexandra", "38_1_MeetAlexandra.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c38_1_AfterGoonsConversation(int starterGuid, int npcGuid)
	{
		b38_1_AfterGoonsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "38_1_AfterGoons", "38_1_AfterGoons.nco", CONV_XFLAG_WANTFEEDBACK);
	}
	
	public void AlexandraReturnConversation(int starterGuid, int npcGuid)
	{
		bAlexandraReturnConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "38_1_AlexandraReturn", "38_1_AlexandraReturn.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b38_1_MeetAlexandraConversation)
		{
			AIOn();
			b38_1_MeetAlexandraConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(50);

			CodexQuest q = new CodexQuest(CodexQuest.Load("N1_FindAlexandra"));
			q.Complete();

			CodexQuest q2 = new CodexQuest(CodexQuest.Load("N1_InfiltrateFactory"));

			// show the factory on the map
			CodexSequence.SetLocationFlags("OrsiFactory1", LOCATION_FLAG_SHOWINMAP);
		}

		if(b38_1_AfterGoonsConversation)
		{
			CodexSound.PopMusic();
			AIOn();
			b38_1_AfterGoonsConversation = false;
			CodexCamera.Release(starterGuid);
		}	

		if(bAlexandraReturnConversation)
		{
			AIOn();
			bAlexandraReturnConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b38_1_MeetAlexandraConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 1:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 2:

							CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 3:

							_Lily.PlayMotionSetMode(MOTION_GESTURE10, false, (float)30.0);
							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 4:

							_Lily.StopActorAction();
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 5:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 7:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 8:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 9:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 10:

							CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 11:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 12:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 13:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 14:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 15:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 16:

							CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 17:

							_goon1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_goon1.SetCollideType(THING_COLLIDE_CYL);
							_goon1.ClearActorFlags(THING_AF_AIPAUSED);

							_goon2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
							_goon2.SetCollideType(THING_COLLIDE_CYL);
							_goon2.ClearActorFlags(THING_AF_AIPAUSED);

							_goon1.SendActorToPos(_goon1Destination.GetPosition(), (float)90.0);
							_goon2.SendActorToPos(_goon2Destination.GetPosition(), (float)90.0);
							break;

						case 18:

							//CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 19:

							//CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 20:

							//CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 21:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 22:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b38_1_AfterGoonsConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 1:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 2:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 3:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 4:

							CodexCamera.SetupCutscene(starterGuid, lilyGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 5:

							CodexCamera.SetupCutscene(starterGuid, alexandraGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 0);
							break;

						case 6:

							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

						case 7:

							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(bAlexandraReturnConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(deadGoons);
	}

	public void restore(int flags)
	{
		deadGoons = CodexSequence.RestoreInt();
	}
}
