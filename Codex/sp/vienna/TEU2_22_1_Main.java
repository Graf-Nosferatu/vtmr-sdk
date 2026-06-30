/**
 * Teutonic Knight Base 2 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class TEU2_22_1_Main extends Codex
{
	private ViennaChronicle	chronScript;

	private CodexRegion		_AinkurnRegion;
	private CodexRegion		_WarRoomRegion;
	private CodexItem		_AinkurnSword;
	private CodexActor		_Christof;

	private int				christofGUID;
	private int				erikGUID;
	private int				serenaGUID;
	private int				wilhemGUID;

	public boolean		b22_1_AinkurnRoomConversation = false;
	public boolean		b22_1_WarRoomConversation = false;

	public static String _params[] =	{"Ainkurn Sword Region", "War Room Region", "AinkurnSword"};

	public TEU2_22_1_Main(CodexRegion AinkurnRegion, CodexRegion WarRoomRegion, CodexItem AinkurnSword)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_AinkurnRegion = new CodexRegion(AinkurnRegion.GetGUID());
		_WarRoomRegion = new CodexRegion(WarRoomRegion.GetGUID());
		_AinkurnSword  = new CodexItem(AinkurnSword.GetGUID());

		CaptureThing(_AinkurnRegion.GetGUID());
		CaptureThing(_WarRoomRegion.GetGUID());
		CaptureThing(_AinkurnSword.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		erikGUID = CodexThing.GuidFromCastID("Erik");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");

		_Christof = new CodexActor(christofGUID);
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(guid == _AinkurnRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.TEU2_AINKURNREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.TEU2_AINKURNREGION);

			c22_1_AinkurnRoomConversation(causeGUID, 0);
		}

		if(guid == _WarRoomRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.TEU2_WARROOMREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.TEU2_WARROOMREGION);

			c22_1_WarRoomConversation(causeGUID, 0);
		}
	}

	public void c22_1_AinkurnRoomConversation(int starterGuid, int npcGuid)
	{
		b22_1_AinkurnRoomConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "22_1_AinkurnRoom", "22_1_AinkurnRoom.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void c22_1_WarRoomConversation(int starterGuid, int npcGuid)
	{
		b22_1_WarRoomConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "22_1_WarRoom", "22_1_WarRoom.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b22_1_AinkurnRoomConversation)
		{
			AIOn();
			b22_1_AinkurnRoomConversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(b22_1_WarRoomConversation)
		{
			AIOn();
			b22_1_WarRoomConversation = false;
			CodexCamera.Release(starterGuid);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(b22_1_AinkurnRoomConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							_Christof.LookAtThing(_AinkurnSword.GetGUID());
							CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}

		if(b22_1_WarRoomConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, serenaGUID, wilhemGUID);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 1:

							CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "TEU2MapRoom.ncp", 30);
							break;

						case 2:

							//CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

						case 3:

							CodexCamera.SetupCutscene(starterGuid, wilhemGUID, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
							break;

					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}