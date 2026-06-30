/**
 * Frog Inn, scene 20.3 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class FROG_20_3 extends Codex
{
	private ViennaChronicle	chronScript;

	private static final int	TIMER_ID_AFTERFLASH		= 0;

	private CodexActor		_Kazi;
	private CodexActor		_Teta;
	private CodexActor		_Zil;
	private CodexActor		_Drunk;
	private CodexThing		_invitation;

    public boolean			b20_1_TripletsConversation	= false;

	private int				christofGUID;
	private int				kaziGUID;
	
	public static String _params[] = {"Kazi", "Teta", "Zil", "Drunk", "Orsi Invitation"};

	public FROG_20_3(CodexActor Kazi, CodexActor Teta, CodexActor Zil, CodexActor Drunk, CodexThing invitation)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_Kazi = new CodexActor(Kazi.GetGUID());
		_Teta = new CodexActor(Teta.GetGUID());
		_Zil = new CodexActor(Zil.GetGUID());
		_Drunk = new CodexActor(Drunk.GetGUID());
		_invitation = new CodexThing(invitation.GetGUID());

		CaptureThing(_Kazi.GetGUID());
		CaptureThing(_Teta.GetGUID());
		CaptureThing(_Zil.GetGUID());
		CaptureThing(_Drunk.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{		
		christofGUID = CodexThing.GuidFromCastID("Christof");
		kaziGUID = CodexThing.GuidFromCastID("Kazi");

		_Drunk.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		_Teta.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		_Zil.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);

		_Drunk.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		_Drunk.SetActorFlags(THING_AF_INVUL);

		if(CodexSequence.GetChronicleFlag(chronScript.FROG_TRIPLETSCONVERSATION))
		{
			// hides the triplets if the player has already had this conversation
			_Kazi.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Teta.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Zil.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_Drunk.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

			_Kazi.SetCollideType(THING_COLLIDE_NONE);
			_Teta.SetCollideType(THING_COLLIDE_NONE);
			_Zil.SetCollideType(THING_COLLIDE_NONE);
			_Drunk.SetCollideType(THING_COLLIDE_NONE);

			_Kazi.SetActorFlags(THING_AF_AIPAUSED);
			_Teta.SetActorFlags(THING_AF_AIPAUSED);
			_Zil.SetActorFlags(THING_AF_AIPAUSED);
			_Drunk.SetActorFlags(THING_AF_AIPAUSED);
		}
		else
		{
			// so they can't pick it up right away
			_invitation.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
		}

		if(!CodexSequence.GetChronicleFlag(chronScript.FROG_INTRO))
		{
			CodexSequence.SetChronicleFlag(chronScript.FROG_INTRO);

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "FrogIntro.ncp", 40);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_AFTERFLASH:

				_Kazi.StopActorAction();
				CodexCamera.Release(0);
				break;
		}
	}

	public void pathended(int clientGuid)
	{
		_Kazi.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		SetTimer((float)3.5, TIMER_ID_AFTERFLASH);
	}

	public void clicked(int guid, int clickerGuid, int captureId)
	{
		if(guid != _Drunk.GetGUID() &&
			!CodexSequence.GetChronicleFlag(chronScript.FROG_TRIPLETSCONVERSATION))
		{
			CodexSequence.SetChronicleFlag(chronScript.FROG_TRIPLETSCONVERSATION);

			CodexSequence.SetChronicleFlag(chronScript.ORSIINVITATION);

            c20_1_TripletsConversation(clickerGuid, 0);
		}
	}	

	public void c20_1_TripletsConversation(int starterGuid, int npcGuid)
	{
		b20_1_TripletsConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "20_1_Triplets", "20_1_Triplets.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b20_1_TripletsConversation)
		{
			AIOn();
			b20_1_TripletsConversation = false;
			CodexCamera.Release(starterGuid);

			CodexQuest q = new CodexQuest(CodexQuest.Load("V1_OrsiParty"));

			// show western ring and orsi mansion on map
			CodexSequence.SetLocationFlags("OrsiMansion", LOCATION_FLAG_SHOWINMAP);
			CodexSequence.SetLocationFlags("WesternRingStrasse", LOCATION_FLAG_SHOWINMAP);

			// add conversation XP
			CodexPlayer.AwardPartyExperience(25);

			// show and read the text for the invitation
			//ExecuteText(starterGuid, 0, "OrsiInvitation");
			// so they can pick it up
			_invitation.ClearThingFlags(THING_FLAG_NOHIGHLIGHT);

			// so they can't talk to them anymore
			_Kazi.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Teta.SetThingFlags(THING_FLAG_NOHIGHLIGHT); 
			_Zil.SetThingFlags(THING_FLAG_NOHIGHLIGHT);
			_Drunk.SetThingFlags(THING_FLAG_NOHIGHLIGHT);

			// open orsi's mansion
			CodexSequence.OpenExit("WesternRingStrasse", 1);
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, kaziGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_A, CAM_ANGLE_MEDIUM, CAM_DIST_LONG, 0, 0);
						_Kazi.LookAtThing(christofGUID);
						break;

					case 1:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 2:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

					case 3:

						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}


}
