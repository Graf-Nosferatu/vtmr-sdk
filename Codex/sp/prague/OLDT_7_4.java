/**
 * Old Town 7.4 script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class OLDT_7_4 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int TIMER_ID_RUN		= 1;

	private	CodexRegion		_EmbraceRegion;

	private CodexPlayer		chris;

	private float[]			pos;

	public static String _params[] = {"Embrace region"};

	public OLDT_7_4(CodexRegion EmbraceRegion)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_EmbraceRegion = new CodexRegion(EmbraceRegion.GetGUID());

		CaptureThing(_EmbraceRegion.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		chris = new CodexPlayer(CodexThing.GuidFromCastID("christof"));

		if(!CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION))
		{
			// close exit to convent 
			CodexSequence.CloseExit("OldTown", 4);

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1.0, false);

			// play path tracking christof running into ecaterina's region
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Embrace.ncp", 150);

			SetTimer(1, TIMER_ID_RUN);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_RUN:
				// send christof to ecaterina
				pos = _EmbraceRegion.GetPosition();
				chris.SendActorToPos(pos, (float)220);
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if((IsPlayerGuid(causeGUID) &&
			guid == _EmbraceRegion.GetGUID()) &&
			!CodexSequence.GetChronicleFlag(chronScript.OLDT_ECATERINAREGION))
		{
			// ALERT!! THIS CHRON FLAG IS CHECKED IN UNORNA SCRIPT TO SEE WHICH CONVERSATION TO PLAY
			CodexSequence.SetChronicleFlag(chronScript.OLDT_ECATERINAREGION);

			// Fade out
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)2.0, false);

			// remove quests on log prior to being embraced
			CodexQuest q = new CodexQuest(CodexQuest.Load("P1_PragueByNight"));
			q.Destroy();
			CodexQuest q2 = new CodexQuest(CodexQuest.Load("P1_SilverMines"));
			q2.Destroy();
			CodexQuest q3 = new CodexQuest(CodexQuest.Load("P1_Geza"));
			q3.Destroy();
			CodexQuest q4 = new CodexQuest(CodexQuest.Load("P1_AnezkaVisit"));
			q4.Destroy();

			// change stats here
			
			// roughly convert any XP they've spent on faith back to XP they can use
			chris.AwardPlayerExperience((int)(chris.GetActorStat(ACTOR_STAT_FAITH) - 50) * 275);

			// make him a vampire
			chris.SetActorType(ACTOR_TYPE_VAMPIRE);
			chris.RemoveActorEffect("ef_increasemanaot");
			chris.RemoveActorEffect("ef_increasebloodot");
			chris.AddActorEffectByLevel("ef_decreasefrenzy", 0, 0, 0, 0);

			// make christof use the vampire model
			chris.SetModel("christof.nod");
			chris.SetPlayerHeadModel("christofH.nod");

			chris.SetFoley("playerChristof.nag");

			// give him his starting disciplines
			chris.SetActorDisciplineLevel("Feed", 0);
			chris.SetActorDisciplineSlot("Feed", 0);
			chris.SetActorDisciplineLevel("BloodHealing", 0);
			chris.SetActorDisciplineSlot("BloodHealing", 1);
			chris.SetActorDisciplineLevel("BloodStrength", 0);
			chris.SetActorDisciplineSlot("BloodStrength", 2);
			chris.SetActorDisciplineLevel("Awe", 0);
			chris.SetActorDisciplineSlot("Awe", 3);
			chris.SetActorDisciplineLevel("Potence", 0);
			chris.SetActorDisciplineSlot("Potence", 4);
			chris.SetActorDisciplineLevel("Celerity", 0);
			chris.SetActorDisciplineSlot("Celerity", 5);
			
			// set blood pool size and blood level
			chris.SetActorBaseStat(ACTOR_STAT_BLOODPOOL, 80);
			chris.SetActorStat(ACTOR_STAT_BLOODPOOL, 80);
			chris.SetActorBaseStat(ACTOR_STAT_BLOOD, 30);
			chris.SetActorStat(ACTOR_STAT_BLOOD, 30);
			

			// close exit to st thomas
			CodexSequence.CloseExit("OldTown", 5);

			// change scene in haven to set up for awakening
			CodexSequence.ChangeScene("Haven", "HAVN_7_4.nsd");

			// set this so we get the proper conversation with unorna if the
			// player hasn't been to visit her yet
			CodexSequence.SetChronicleFlag(chronScript.UNORD_TALKEDONCE);

			PlayVideo("embrace.bik");
		}
	}

	public void videoended(int id)
	{
		// jump to the haven for the recovery scene
		CodexSequence.Jump("Haven", 1);
	}
}