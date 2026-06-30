/**
 * Convent, 3.1 scene script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CNVT_3_1 extends Codex
{
	private PragueChronicle	chronScript;

	private static final int	TIMER_ID_FADEIN			= 0;
	private static final int	TIMER_ID_EYESOPEN		= 1;
	private static final int	TIMER_ID_GETUP			= 2;
	private static final int	TIMER_ID_CHRISGROAN		= 3;
	private static final int	TIMER_ID_NUKESWORD		= 4;
	private static final int	TIMER_ID_CHRISARMED		= 5;
	private static final int	TIMER_ID_LASTSZLACHTA	= 6;
	private static final int	TIMER_ID_BEFORECOLLAPSE	= 7;
	private static final int	TIMER_ID_FADEOUT		= 8;
	private static final int	TIMER_ID_AFTERCOLLAPSE	= 9;
	
	private	CodexRegion			_OtherRoomRegion;
	private CodexActor			_Szlachta1;
	private CodexActor			_Szlachta2;
	private CodexItem			_Sword;
	private CodexPlayer			christofActor;
	private CodexActor			anezkaActor;
	private CodexActor			nun1Actor;
	private CodexActor			chrisHead;
	private CodexThing			bedSheet;
	private CodexThing			sword;

	private int					bedSheetGuid = 0;
	private int					swordGuid = 0;
 
	private int					christofGUID;
	private int					anezkaGUID;

	private int					deadSzlachtas			= 0;

	private float[]				offset = new float[3];

	private int					seqID					= 0;
	private static final int	SEQ_ID_INTRO			= 1;
	private static final int	SEQ_ID_FIGHT			= 2;
	private static final int	SEQ_ID_LASTSZLACHTA		= 3;
	private static final int	SEQ_ID_COLLAPSE			= 4;

	public static String _params[] = {"Other room region", "Szlachta1", "Szlachta2", "Sword", "Nun1"};

	public CNVT_3_1(CodexRegion OtherRoomRegion, CodexActor Szlachta1, CodexActor Szlachta2, CodexItem Sword, CodexActor Nun1)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_OtherRoomRegion	= new CodexRegion(OtherRoomRegion.GetGUID());
		_Szlachta1			= new CodexActor(Szlachta1.GetGUID());
		_Szlachta2			= new CodexActor(Szlachta2.GetGUID());
		_Sword				= new CodexItem(Sword.GetGUID());
		nun1Actor			= new CodexActor(Nun1.GetGUID());

		CaptureThing(_OtherRoomRegion.GetGUID());
		CaptureThing(_Szlachta1.GetGUID());
		CaptureThing(_Szlachta2.GetGUID());

		CaptureThing(_Sword.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!IsActorGuid(_Szlachta1.GetGUID()) || ((_Szlachta1.GetActorFlags() & THING_AF_DEAD) != 0))
			deadSzlachtas++;

		if(!IsActorGuid(_Szlachta2.GetGUID()) || ((_Szlachta2.GetActorFlags() & THING_AF_DEAD) != 0))
			deadSzlachtas++;

		anezkaGUID = CodexThing.GuidFromCastID("Anezka");
		christofGUID = CodexThing.GuidFromCastID("Christof");

		anezkaActor = new CodexActor(anezkaGUID);
		christofActor = new CodexPlayer(christofGUID);

		_Szlachta1.AISetTarget(anezkaGUID);
		_Szlachta2.AISetTarget(anezkaGUID);

		anezkaActor.SetActorFlags(THING_AF_AIPAUSED);
		
		nun1Actor.SetActorFlags(THING_AF_INVUL);
		nun1Actor.SetActorFlags(THING_AF_NEUTRAL);

		if(!CodexSequence.GetChronicleFlag(chronScript.CNVT_OPENINGSCENE))
		{
			CodexSequence.SetChronicleFlag(chronScript.CNVT_OPENINGSCENE);

			// make him a human
			christofActor.SetActorType(ACTOR_TYPE_HUMAN);
			christofActor.RemoveActorEffect("ef_decreasefrenzy");
			christofActor.AddActorEffectByLevel("ef_increasemanaot", 0, 0, 0, 0);
			christofActor.AddActorEffectByLevel("ef_increasebloodot", 0, 0, 0, 0);

			christofActor.SetFoley("playerChristofHuman.nag");

			// teleport player to the bed spot
			CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);

			// spawn the animated bed sheet in at the player's spot
			bedSheetGuid = christofActor.SpawnThing("chrisAwakenSheet");
			bedSheet = new CodexThing(bedSheetGuid);
			bedSheet.SetThingFlags(THING_FLAG_SAVENEVER);

			// Set christof in bandages, in bed
			christofActor.SetModel("christof_wounded.nod");
			//christofActor.SetPlayerHeadModel("HEADchristofMod.nod");
			//christofActor.SetActorNoItemArmor("wounded");
			christofActor.PlayMotionSetMode(MOTION_SPECIAL10, false, (float)30.0);
			christofActor.EnableActorWeapon(false);
			
			// Close convent exits
			CodexSequence.CloseExit("ConventDay", 0);
			CodexSequence.CloseExit("Convent", 1);

			// close the judith bridge and petrin hill day exits
			CodexSequence.CloseExit("JudithBridgeDay", 2);
			CodexSequence.CloseExit("PetrinHillDay", 1);

			// AI off at start
			AIOff();

			// Fade in
			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)0.0, (float)255.0, (float)1.0, false);
			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "Intro1.ncp", 30);

			// Intro camera path
			seqID = SEQ_ID_INTRO;
		}

	}

	// Timer handler
	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_FADEIN:

				// First scream, wake Chris up
				CodexSound snd1 = new CodexSound("Anezka_Scream_8.wav", (float)2048.0, (float)4096.0, 100, 0, 0, anezkaActor.GetGUID());
				//christofActor.SetRenderFlags(THING_RENDERFLAG_DONTRENDER);
				//chrisHead = new CodexActor(christofActor.SpawnThing("ChrisAwakenCloseup"));

				//chrisHead.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
				//CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "WakeUpChris.ncp", 30);
				SetTimer((float)0.5, TIMER_ID_EYESOPEN);
				break;

			case TIMER_ID_EYESOPEN:

				//chrisHead.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
				SetTimer((float)0.0, TIMER_ID_GETUP);
				break;

			case TIMER_ID_GETUP:

				// Get rid of fake head
				//chrisHead.Remove();
				//christofActor.ClearRenderFlags(THING_RENDERFLAG_DONTRENDER);
				
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "GetUpChris.ncp", 30);

				// play getting out of bed anim on christof and bedsheet
				christofActor.PlayMotionSetMode(MOTION_SPECIAL9, false, (float)30.0);
				bedSheet.PlayMotionSetMode(MOTION_SPECIAL9, false, (float)30.0);

				SetTimer((float)2.5, TIMER_ID_CHRISGROAN);
				SetTimer((float)9.3, TIMER_ID_NUKESWORD);
				SetTimer((float)10.5, TIMER_ID_CHRISARMED);
				break;

			case TIMER_ID_CHRISGROAN:

				CodexSound snd2 = new CodexSound("Christof_Gasp_5.wav", (float)516.0, (float)2042.0, 40, 0, 0, christofActor.GetGUID());
				break;
			
			case TIMER_ID_NUKESWORD:

				_Sword.Remove();
				christofActor.EnableActorWeapon(true);
				break;
			
			case TIMER_ID_CHRISARMED:

				// Another woman screams
				CodexSound snd3 = new CodexSound("Anezka_Scream_10.wav", (float)2048.0, (float)4096.0, 100, 0, 0, anezkaActor.GetGUID());
				CodexCamera.Release((int)arg0);
				christofActor.StopActorAction();
				break;

			case TIMER_ID_LASTSZLACHTA:
   
				// Christof is near collapse
				CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
				nun1Actor.StopActorAction();
				ExecuteConversation(CodexPlayer.GetCurrentPlayer(), 0, "3_1_Collapse", "3_1_Collapse.nco", CONV_XFLAG_WANTFEEDBACK | CONV_XFLAG_NORESTOREWEAPONS);
				break;
		   
/*			case TIMER_ID_BEFORECOLLAPSE:
   
				// Christof has said his 'tis a scratch' line, now he collapses
				christofActor.StopActorAction();
				christofActor.PlayMotionSetMode(MOTION_DEATHSLOW, false, (float)30.0);

				SetTimer((float)3, TIMER_ID_FADEOUT);
				break;

			case TIMER_ID_FADEOUT:
				
				// Fade out
			   	CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
				SetTimer(3, TIMER_ID_AFTERCOLLAPSE);
				break;

			case TIMER_ID_AFTERCOLLAPSE:
   
				// remove the animated bed sheet and dropped sword
				bedSheet.Remove();
				sword.Remove();

				// Jump to the next day
				CodexSequence.ChangeScene("Convent", "CNVT_3_3.nsd");
				CodexSequence.Jump("Convent", 0);
				break;
*/
		}
	}// timer

	// Entered handler
	public void entered(int guid, int causeGUID, int captureID)
	{
		if(IsPlayerGuid(causeGUID) &&
			guid == _OtherRoomRegion.GetGUID() && 
			!CodexSequence.GetChronicleFlag(chronScript.CNVT_OTHERROOMREGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.CNVT_OTHERROOMREGION);

			// AI on, peel two off from Anezka
			AIOn();
			_Szlachta1.AISetTarget(christofGUID);

			// Start Anezka swinging torch
			anezkaActor.PlayMotionSetMode(MOTION_STAND_ONEHAND, true, (float)30.0);
			nun1Actor.PlayMotionSetMode(MOTION_SPECIAL1, false, (float)30.0);
		}
	} // entered

	// Killed handler
	public void killed(int guid, int causeID, int captureID)
	{
		// see if all the szlachtas are dead
		if(++deadSzlachtas == 2)
		{
			// wait while the last one falls
			SetTimer(2, TIMER_ID_LASTSZLACHTA, causeID);
			++seqID;
		}
	} //killed	

	// Pick up handler
	public boolean pickup(int item, int picker, int captureID)
	{
		// once Christof has picked up the sword, prompt player to wear it
		if(item == _Sword.GetGUID())
		{
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "CODEX_TRAININGWEARSWORD");
		}

		return(true);
	} // pickup

	// Worn handler
	public void worn(int item, int wearer, int captureID)
	{
		// once Christof is armed, wake up Szlachta and prompt player to attack
		if(item == _Sword.GetGUID())
		{
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "CODEX_TRAININGATTACKSZLACHTA");
		}

	}// worn

	// Path ended handler
	public void pathended(int clientGuid)
	{
	   switch(seqID)
	   {
		   case SEQ_ID_INTRO:
		   {
			   SetTimer(0, TIMER_ID_FADEIN, 0);
			   ++seqID;
		   }
	   }
	} // pathended

	// Conversation line handler
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		//switch(seqID)
		//{
		//	case SEQ_ID_LASTSZLACHTA:

		switch(curEvent)
		{
			case 0:
				switch(curLine)
				{
					case 0:

						// Christof drops his sword here
						swordGuid = christofActor.SpawnThing("broadsword");
						sword = new CodexThing(swordGuid);
						sword.SetThingFlags(THING_FLAG_SAVENEVER);
						
						offset = christofActor.GetPosition();
						offset[0] = offset[0]-32;
						offset[1] = offset[1]-32;

						sword.SetPosition(offset);
						//CaptureThing(sword.GetGUID());
						
						CodexSound snd1 = new CodexSound("metalWeapon_drop_05.wav", (float)2048.0, (float)4096.0, 100, 0, 0, christofActor.GetGUID());
						
						CodexCamera.SetupCutscene(starterGuid, christofGUID, anezkaGUID);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 1, 0);
						CodexCamera.SetShot(starterGuid, CAM_SHOT_ANGULAR_B, CAM_ANGLE_MEDIUM, CAM_DIST_EXTREMECLOSEUP, 0, 5);

						christofActor.PlayMotionSetMode(MOTION_GESTURE1, false, (float)30.0);
						break;

					case 1:

						anezkaActor.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
						break;

					case 2:

						//SetTimer((float)2.5, TIMER_ID_BEFORECOLLAPSE);
						//++seqID;
						break;

					case 3:

						// Make Christof fall down
						//christofActor.StopActorAction();
						christofActor.PlayMotionSetMode(MOTION_DEATHSLOW, false, (float)30.0);
						break;

					case 4:

						// Fade out
			   			CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)3.0, false);
						break;
				}
				break;
		}
		// }
	} // convreached


	// Conversation end handler
	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bedSheetGuid != 0)
			bedSheet.Remove();

		if(swordGuid != 0)
			sword.Remove();

		// Jump to the next day
		CodexSequence.ChangeScene("Convent", "CNVT_3_3.nsd");
		CodexSequence.Jump("Convent", 0);

	} // convended

}
