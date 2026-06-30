/**
 *  SOUT Haus Gate 20.1 script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class SOUT_20_1_HausGate extends Codex
{
	private ViennaChronicle chronScript;
	
	private CodexThing		_Gate;
	private CodexPlayer		player;

	private int				christofGUID;
	private boolean			bOpen = false;
	public boolean			b20_1_HausGateConversation = false;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Gate"};

	public SOUT_20_1_HausGate(CodexThing Gate)
	{
		chronScript = (ViennaChronicle)GetChronicleScript(0);

		_Gate = new CodexRegion(Gate.GetGUID());

		CaptureThing(_Gate.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.ORDR_AMULETCHARGED))
		{
			CodexSequence.CloseExit("SouthernRingStrasse", 1);
		}
		else
		{
			CodexSequence.OpenExit("SouthernRingStrasse", 1);
		}

		christofGUID = CodexThing.GuidFromCastID("Christof");
	}
	
	public void clicked(int guid, int clickerGUID, int captureID)
	{
		CodexThing		beam;
		int				beamGuid;
		int				smokeGuid;
		float[]			offset = new float[3];

		if(bOpen || !IsPlayerGuid(clickerGUID))
			return;

		if(CodexSequence.GetChronicleFlag(chronScript.ORDR_AMULETCHARGED))
		{
			_Gate.RotatePivot(1, (float)2.0);
			bOpen = true;
			return;
		}

		// zappem
		player = new CodexPlayer(clickerGUID);

		player.DamageActor(10, DAMAGE_TYPE_ELECTRIC, 0);

		//////////////////////////////////////////////////////////////////////////
		// visuals
		//////////////////////////////////////////////////////////////////////////

		// spawn a beam at the player's position
		beamGuid = player.SpawnThing("bluelightning");
		beam = new CodexThing(beamGuid);

		// allocate two frames for the beam (0th frame is its "origin" and the 1st is its target)
		// then set the target of the beam to the gate they just clicked
		beam.AllocateFrames(2);
		beam.SetFramePosition(1, _Gate.GetPosition());

		// attach thing needs an array of floats as a parm, using no offset essentially
		offset[0] = 0; 
		offset[1] = 0; 
		offset[2] = 0;

		// attach the beam to their jaw bone - if FindBone fails, it will attach it to the player's origin
		player.AttachThing(beamGuid, player.FindBone(MOTIONTAG_HELMET), offset, ATTACH_FLAG_AUTOREMOVE);

		// create a smoke plume at their position
		smokeGuid = player.SpawnThing("smokeplume");

		// attach that smoke to the player
		player.AttachThing(smokeGuid, player.FindBone(MOTIONTAG_HELMET), offset, ATTACH_FLAG_AUTOREMOVE);

		//////////////////////////////////////////////////////////////////////////

		// detach the beam 2 seconds later
		SetTimer(2, 1, beamGuid, smokeGuid);

		if(!CodexSequence.GetChronicleFlag(chronScript.SOUT_GATELINESAID))
			SetTimer(2, 0, guid);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case 0:
				CodexSequence.SetChronicleFlag(chronScript.SOUT_GATELINESAID);
				c20_1_HausGateConversation(CodexPlayer.GetCurrentPlayer(), 0);
				break;
			case 1:
				// detach the beam and smoke from the player
				player.DetachThing((int)arg0);
				player.DetachThing((int)arg1);
				break;
		}
	}
	public void c20_1_HausGateConversation(int starterGuid, int npcGuid)
	{
		b20_1_HausGateConversation = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "20_1_HausGate", "20_1_HausGate.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(b20_1_HausGateConversation)
		{
			AIOn();
			b20_1_HausGateConversation = false;
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

						CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
						break;

				} // switch(curLine)
				break;
		} // switch(curEvent)
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bOpen);
	}

	public void restore(int flags)
	{
		bOpen = CodexSequence.RestoreBoolean();
	}
}
