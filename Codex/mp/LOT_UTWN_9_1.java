/**
 * New York Uptown LOT 9.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_UTWN_9_1 extends Codex
{
	private MP_LOTChronicle	chronScript;

	private CodexActor		_Bill;

	private CodexThing		_billSpawnSpot;
	private CodexRegion		_BloodHuntRegion;
	private CodexActor		_BloodHuntGuy1;
	private CodexActor		_BloodHuntGuy2;
	private CodexActor		_BloodHuntGuy3;
	private CodexActor		_BloodHuntGuy4;

	private int				deadEnemies = 0;

	public boolean			bBillConversation = false;

	public static String _params[] = {"Bloodhunt region", "Blood hunt guy 1", "Blood hunt guy 2", "Blood hunt guy 3", "Blood hunt guy 4"};

	public LOT_UTWN_9_1(CodexRegion BloodHuntRegion, CodexActor BloodHuntGuy1, CodexActor BloodHuntGuy2, CodexActor BloodHuntGuy3, CodexActor BloodHuntGuy4)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_billSpawnSpot = new CodexThing(GetClassThing());
		_BloodHuntRegion = new CodexRegion(BloodHuntRegion.GetGUID());
		_BloodHuntGuy1 = new CodexActor(BloodHuntGuy1.GetGUID());
		_BloodHuntGuy2 = new CodexActor(BloodHuntGuy2.GetGUID());
		_BloodHuntGuy3 = new CodexActor(BloodHuntGuy3.GetGUID());
		_BloodHuntGuy4 = new CodexActor(BloodHuntGuy4.GetGUID());

		CaptureThing(_BloodHuntRegion.GetGUID());
		CaptureThing(_BloodHuntGuy1.GetGUID());
		CaptureThing(_BloodHuntGuy2.GetGUID());
		CaptureThing(_BloodHuntGuy3.GetGUID());
		CaptureThing(_BloodHuntGuy4.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		//_Bill = new CodexActor(CodexThing.GuidFromCastID("Bill"));

		if(!CodexSequence.GetChronicleFlag(chronScript.SLUM_DOMINIC))
		{
			//_Bill.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy1.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy2.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy3.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy4.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);

			//_Bill.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy1.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy2.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy3.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy4.SetActorFlags(THING_AF_AIPAUSED);

			//_Bill.SetCollideType(THING_COLLIDE_NONE);
			_BloodHuntGuy1.SetCollideType(THING_COLLIDE_NONE);
			_BloodHuntGuy2.SetCollideType(THING_COLLIDE_NONE);
			_BloodHuntGuy3.SetCollideType(THING_COLLIDE_NONE);
			_BloodHuntGuy4.SetCollideType(THING_COLLIDE_NONE);

			// so they can't kill them early
			//_Bill.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy1.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy2.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy3.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy4.SetActorFlags(THING_AF_INVUL);

			// to keep police from targetting actors.
			_BloodHuntGuy1.SetActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy2.SetActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy3.SetActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy4.SetActorFlags(THING_AF_NEUTRAL);
		}
		else if(!CodexSequence.GetChronicleFlag(chronScript.UTWN_BILLSPAWNEDIN) && 
			!CodexSequence.GetChronicleFlag(chronScript.UTWN_TALKEDBILL))
		{
			chronScript.Step(chronScript.UTWN_BILLSPAWNEDIN);

			_Bill = new CodexActor(_billSpawnSpot.SpawnThing("Bill"));
			_Bill.SetActorFlags(THING_AF_INVUL);

			_BloodHuntGuy1.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy2.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy3.SetActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy4.SetActorFlags(THING_AF_AIPAUSED);

			// so they can't kill them early
			_BloodHuntGuy1.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy2.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy3.SetActorFlags(THING_AF_INVUL);
			_BloodHuntGuy4.SetActorFlags(THING_AF_INVUL);

			// to keep police from targetting actors.
			_BloodHuntGuy1.SetActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy2.SetActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy3.SetActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy4.SetActorFlags(THING_AF_NEUTRAL);

			//_Bill.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy1.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy2.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy3.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
			_BloodHuntGuy4.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);

			//_Bill.SetCollideType(THING_COLLIDE_CYL);
			_BloodHuntGuy1.SetCollideType(THING_COLLIDE_CYL);
			_BloodHuntGuy2.SetCollideType(THING_COLLIDE_CYL);
			_BloodHuntGuy3.SetCollideType(THING_COLLIDE_CYL);
			_BloodHuntGuy4.SetCollideType(THING_COLLIDE_CYL);		
		}
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.UTWN_TALKEDBILL))
		{
			_Bill.Remove();
		}
	}
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID) || 
			!CodexSequence.GetChronicleFlag(chronScript.SLUM_DOMINIC))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.UTWN_BLOODHUNT))
		{
			CodexSequence.SetChronicleFlag(chronScript.UTWN_BLOODHUNT);

			_BloodHuntGuy1.ClearActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy2.ClearActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy3.ClearActorFlags(THING_AF_AIPAUSED);
			_BloodHuntGuy4.ClearActorFlags(THING_AF_AIPAUSED);

			_BloodHuntGuy1.ClearActorFlags(THING_AF_INVUL);
			_BloodHuntGuy2.ClearActorFlags(THING_AF_INVUL);
			_BloodHuntGuy3.ClearActorFlags(THING_AF_INVUL);
			_BloodHuntGuy4.ClearActorFlags(THING_AF_INVUL);

			_BloodHuntGuy1.ClearActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy2.ClearActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy3.ClearActorFlags(THING_AF_NEUTRAL);
			_BloodHuntGuy4.ClearActorFlags(THING_AF_NEUTRAL);

			// sick them on the guy who triggered this
			_BloodHuntGuy1.AISetTarget(causeGUID);
			_BloodHuntGuy2.AISetTarget(causeGUID);
			_BloodHuntGuy3.AISetTarget(causeGUID);
			_BloodHuntGuy4.AISetTarget(causeGUID);
		}
	}

	public void killed(int guid, int causeID, int captureID)
	{
		deadEnemies++;

		// see if all the bloodhunt guys are dead
		if(deadEnemies == 4)
		{
			chronScript.Step(chronScript.UTWN_TALKEDBILL);

			// this is set to check later if they go back to the triplets in the slum room
			//CodexSequence.SetChronicleFlag(chronScript.UTWN_TALKEDBILL);

			CodexThing initiatorThing = new CodexThing(causeID);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "BILL" + "%t" + "KILLED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, call conversation
			if(!NetIsNoAutoConversations())
			{
				BillConversation(causeID, _Bill.GetGUID());
			}
		}
	}	

	public void BillConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "9_1_Bill", "9_1_Bill.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bBillConversation = true;
			AIOff();
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bBillConversation)
		{
			AIOn();
			bBillConversation = false;
			CodexCamera.Release(starterGuid);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_InvadeWarehouse"));

			// open the warehouse
			//CodexSequence.OpenExit("NewYorkDocks", 5);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bBillConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:
							
							CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTBillBloodHunt.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 1:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 2:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 3:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 4:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 5:

							//CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTBillBloodHunt.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 6:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 7:

							//CodexCamera.PlayPath(starterGuid, GetGUID(), "LOTBillBloodHunt.ncp", 30);
							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 8:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;

						case 9:

							//CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							//CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_FULL, 0, 0);
							break;
					} // switch(curLine)
					break;
			} // switch(curEvent)
		}
	}
}

