/**
 * OLDT Libussa script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_Libussa extends Codex
{
	private PragueChronicle	chronScript;

	private CodexActor		_libussa;
	private int				libussaGUID;

	private int				convCounter = 0;

	public boolean			bLibussaRant1Conversation = false;
	public boolean			bLibussaRant2Conversation = false;
	public boolean			bLibussaRant3Conversation = false;
	public boolean			bLibussaRant4Conversation = false;

	public OLDT_Libussa()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_libussa = new CodexActor(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION))
		{
			// the embrace hasn't happened yet so she shouldn't be showing up
			_libussa.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_libussa.SetCollideType(THING_COLLIDE_NONE);
			_libussa.SetActorFlags(THING_AF_AIPAUSED);

			return;
		}
		else
		{
			libussaGUID = CodexThing.GuidFromCastID("Libussa");

			// randomly hide/show libussa at each beginscene after the embrace
			if(Math.random() < 0.5f) 
			{
				_libussa.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_libussa.SetCollideType(THING_COLLIDE_NONE);
				_libussa.SetActorFlags(THING_AF_AIPAUSED);
			}
			else
			{
				_libussa.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				_libussa.SetCollideType(THING_COLLIDE_CYL);
				_libussa.ClearActorFlags(THING_AF_AIPAUSED);		
			}
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		// convCounter is just used to cycle through each of the
		// four lines that libussa can say
		convCounter++;

		// if they've listened to them all in one sitting
		if((convCounter == 4) &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_LIBUSSAXP))
		{
			// so they only get XP once for hearing all 4
			CodexSequence.SetChronicleFlag(chronScript.OLDT_LIBUSSAXP);

			// award conversation XP
			CodexPlayer.AwardPartyExperience(100);
		}

		// if they've clicked on her and convCounter is more than 4, reset it to 1 
		if(convCounter > 4)
			convCounter = 1;

		switch(convCounter)
		{
			case 1:
				LibussaRant1Conversation(clickerGuid, 0);
				break;
			case 2:
				LibussaRant2Conversation(clickerGuid, 0);
				break;
			case 3:
				LibussaRant3Conversation(clickerGuid, 0);
				break;
			case 4:
				LibussaRant4Conversation(clickerGuid, 0);
				break;
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveInt(convCounter);
	}
 
	public void restore(int flags)
	{
		convCounter = CodexSequence.RestoreInt();
	}

	public void LibussaRant1Conversation(int starterGuid, int npcGuid)
	{
		bLibussaRant1Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 10);
		ExecuteConversation(starterGuid, npcGuid, "10_2_LibussaRant1", "10_2_LibussaRant1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LibussaRant2Conversation(int starterGuid, int npcGuid)
	{
		bLibussaRant2Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 10);
		ExecuteConversation(starterGuid, npcGuid, "10_2_LibussaRant2", "10_2_LibussaRant2.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LibussaRant3Conversation(int starterGuid, int npcGuid)
	{
		bLibussaRant3Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 10);
		ExecuteConversation(starterGuid, npcGuid, "10_2_LibussaRant3", "10_2_LibussaRant3.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void LibussaRant4Conversation(int starterGuid, int npcGuid)
	{
		bLibussaRant4Conversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 10);
		ExecuteConversation(starterGuid, npcGuid, "10_2_LibussaRant4", "10_2_LibussaRant4.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		_libussa.StopActorAction();

		if(bLibussaRant1Conversation)
		{
			AIOn();
			bLibussaRant1Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bLibussaRant2Conversation)
		{
			AIOn();
			bLibussaRant2Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bLibussaRant3Conversation)
		{
			AIOn();
			bLibussaRant3Conversation = false;
			CodexCamera.Release(starterGuid);
		}

		if(bLibussaRant4Conversation)
		{
			AIOn();
			bLibussaRant4Conversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		// NOTE: all four of these conversations should be able to use the same convreached
		// that's why there's only the one line here

		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						_libussa.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
						CodexCamera.SetupCutscene(starterGuid, libussaGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_FULL, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}