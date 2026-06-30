/**
 * Convent, Anezka script scene 4.1+ script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CNVT_4_1_Anezka extends Codex
{
	private PragueChronicle	chronScript;

	private CodexPlayer		_Christof;
	private CodexActor		_Anezka;

	private int				christofGUID;
	private int				anezkaGUID;

	public boolean			bAnezkaConversation = false;

	public CNVT_4_1_Anezka()
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		anezkaGUID = CodexThing.GuidFromCastID("Anezka");

		_Anezka = new CodexActor(anezkaGUID);
		_Christof = new CodexPlayer(christofGUID);

		if(!CodexSequence.GetChronicleFlag(chronScript.CTDY_FIRSTTIME))
		{
			CodexSequence.SetChronicleFlag(chronScript.CTDY_FIRSTTIME);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)2.0, false);
		}

		if(CodexSequence.GetChronicleFlag(chronScript.SIM4_AHZRADEAD))
		{
			// hide Anezka in this scene if they've completed the mission
			_Anezka.Remove();
		}
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		bAnezkaConversation = true;
		AIOff();

		if(!CodexSequence.GetChronicleFlag(chronScript.EGAT_OUTSIDEREGION))
		{
			// until he starts on his quest (or at least makes it to the outside region in east 
			// gate, play the first "Pray" conversation - after that it will be random between "Pray" and "Heal"
			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
			ExecuteConversation(clickerGuid, 0, "4_1_AnezkaPray", "4_1_AnezkaPray.nco", CONV_XFLAG_WANTFEEDBACK);
		}
		else
		{
			// pick a conversation at random to play
			if(Math.random() < 0.5f)
			{
				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
				ExecuteConversation(clickerGuid, 0, "4_1_AnezkaPray", "4_1_AnezkaPray.nco", CONV_XFLAG_WANTFEEDBACK);
			}
			else
			{
				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
				ExecuteConversation(clickerGuid, 0, "4_1_AnezkaHeal", "4_1_AnezkaHeal.nco", CONV_XFLAG_WANTFEEDBACK);
			}
		}

		// see if Christof is hurt
		if(_Christof.GetActorHealth() < _Christof.GetActorMaxHealth())
		{
			// heal him
			_Christof.HealActor(1000);
		}
	}

	public void convended(int starterGuid, boolean bAborted , int returnValue)
	{
		if(bAnezkaConversation)
		{
			AIOn();
			_Anezka.StopActorAction();
			bAnezkaConversation = false;
			CodexCamera.Release(starterGuid);
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

						_Anezka.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)45.0);
						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_CLOSEUP, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}
}