/**
 * IN4D Bartender script script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class IN4D_Bartender extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor		_barKeep;
	private CodexThing		_spawnSpot;

	private int				barKeepGUID;
	private int				christofGUID;

	public boolean			bBartenderConversation = false;
	public boolean			bBartender2Conversation = false;

	public static String _params[] = {"Health spawn spot"};

	public IN4D_Bartender(CodexThing spawnSpot)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_barKeep = new CodexActor(GetClassThing());
		_spawnSpot = new CodexThing(spawnSpot.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		barKeepGUID = _barKeep.GetGUID();
		christofGUID = CodexThing.GuidFromCastID("Christof");
		
		if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.INN4_MINECONGRATS))
			{
				CodexSequence.SetChronicleFlag(chronScript.INN4_MINECONGRATS);

				Bartender2Conversation(CodexPlayer.GetCurrentPlayer(), 0);
			}

			// after this, you cannot click on him to talk (in case this wasn't
			// set in the conversation below and/or after Ahzra's dead, make him nohighlight
			_barKeep.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _barKeep.GetGUID())
		{
			if(!CodexSequence.GetChronicleFlag(chronScript.INN4_FIRSTBARKEEP) &&
				!CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
			{
				CodexSequence.SetChronicleFlag(chronScript.INN4_FIRSTBARKEEP);

				// after this, you cannot click on him to talk - he will say something
				// if you visit him after the mine mission, but no more click on to talk
				_barKeep.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

				BartenderConversation(clickerGuid, 0);
			}
		}
	}

	public void BartenderConversation(int starterGuid, int npcGuid)
	{
		bBartenderConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "4_1_Bartender", "4_1_Bartender.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void Bartender2Conversation(int starterGuid, int npcGuid)
	{
		bBartenderConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "6_1_Bartender", "6_1_Bartender.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bBartenderConversation)
		{
			AIOn();
			bBartenderConversation = false;
			CodexCamera.Release(starterGuid);

			_spawnSpot.SpawnThing("potionmajorhealth");
		}

		if(bBartender2Conversation)
		{
			AIOn();
			bBartender2Conversation = false;
			CodexCamera.Release(starterGuid);

			if(!CodexSequence.GetChronicleFlag(chronScript.INN4_FIRSTBARKEEP))
				_spawnSpot.SpawnThing("potionmajorhealth");
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, barKeepGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}