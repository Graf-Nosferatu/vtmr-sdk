/**
 * Haven, scene 11.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class HAVN_11_1 extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor	_Serena;

	private int			christofGUID;
	private int			serenaGUID;

	public boolean		bSerenaConversation		= false;

	public HAVN_11_1()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.HILL_GARINOLREGION) &&
			!CodexSequence.GetChronicleFlag(chronScript.HAVN_SERENASCENE))
		{
			CodexSequence.SetChronicleFlag(chronScript.HAVN_SERENASCENE);

			christofGUID = CodexThing.GuidFromCastID("Christof");
			serenaGUID = CodexThing.GuidFromCastID("Serena");

			_Serena = new CodexActor(serenaGUID);

			SerenaConversation(clientGuid, 0);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.HAVN_ANEZKASCENE))
		{
			// HAVN_ANEZKASCENE is set in ANZK_14_1.java

			// next time they advance, they'll come out of it and jump to this level/scene
			CodexSequence.SetupAdvance("AnezkaRoom", "ANZK_14_1.nsd", 0);
		}
	}

	public void SerenaConversation(int starterGuid, int npcGuid)
	{
		bSerenaConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "15_1_S&C", "15_1_S&C.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bSerenaConversation)
		{
			AIOn();
			bSerenaConversation = false;
			CodexCamera.Release(starterGuid);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(50);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						_Serena.LookAtThing(christofGUID);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, serenaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 1, 0);
						break;

					case 1:

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

					case 2:

						CodexCamera.SetupCutscene(starterGuid, serenaGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}
