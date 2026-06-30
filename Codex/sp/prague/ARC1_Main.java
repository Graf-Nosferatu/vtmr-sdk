/**
 * Ardan Chantry 1 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC1_Main extends Codex
{
	private PragueChronicle	chronScript;

	private	CodexRegion		_Tree1Region;
	private	CodexRegion		_Tree2Region;
	private	CodexRegion		_Tree3Region;

	private int				christofGUID;
	private int				serenaGUID;
	private int				wilhemGUID;
	private float			lineLength;

	private boolean			bShopMaskLine		= false;
	private boolean			bTreeConversation1	= false;
	private boolean			bTreeConversation2	= false;
	private boolean			bTreeConversation3	= false;

	public static String _params[] = {"Tree1 region", "Tree2 region", "Tree3 region"};

	public ARC1_Main(CodexRegion Tree1Region, CodexRegion Tree2Region, CodexRegion Tree3Region)
	{
		chronScript = (PragueChronicle)GetChronicleScript(0);

		_Tree1Region = new CodexRegion(Tree1Region.GetGUID());
		_Tree2Region = new CodexRegion(Tree2Region.GetGUID());
		_Tree3Region = new CodexRegion(Tree3Region.GetGUID());

		CaptureThing(_Tree1Region.GetGUID());
		CaptureThing(_Tree2Region.GetGUID());
		CaptureThing(_Tree3Region.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		christofGUID = CodexThing.GuidFromCastID("Christof");
		serenaGUID = CodexThing.GuidFromCastID("Serena");
		wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
		
		SetTimer(1, 1, clientGuid);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case 1:

				if(!CodexSequence.GetChronicleFlag(chronScript.ARC1_SHOPBUTAMASK))
				{
					CodexSequence.SetChronicleFlag(chronScript.ARC1_SHOPBUTAMASK);

					ShopMaskLine((int)arg0, 0);
				}
				break;

			case 2:

				CodexSound.PlayVoice(serenaGUID, "Serena_17_1_585", 75);
				break;

			case 3:

				CodexSound.PlayVoice(wilhemGUID, "Wilhem_17_2_587", 75);
				break;

		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if((guid == _Tree1Region.GetGUID()) && 
			!CodexSequence.GetChronicleFlag(chronScript.ARC1_TREE1REGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC1_TREE1REGION);

			TreeConversation1(causeGUID, 0);
		}

		if((guid == _Tree2Region.GetGUID()) && 
			!CodexSequence.GetChronicleFlag(chronScript.ARC1_TREE2REGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC1_TREE2REGION);

			lineLength = CodexSound.PlayVoice(christofGUID, "Christof_17_1_584", 75);

			SetTimer(lineLength, 2);
		}

		if((guid == _Tree3Region.GetGUID()) && 
			!CodexSequence.GetChronicleFlag(chronScript.ARC1_TREE3REGION))
		{
			CodexSequence.SetChronicleFlag(chronScript.ARC1_TREE3REGION);

			lineLength = CodexSound.PlayVoice(serenaGUID, "Serena_17_2_586", 75);

			SetTimer(lineLength, 3);
		}
	}

	public void ShopMaskLine(int starterGuid, int npcGuid)
	{
		bShopMaskLine = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 0);
		ExecuteConversation(starterGuid, npcGuid, "17_1_EnterChantry", "17_1_EnterChantry.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void TreeConversation1(int starterGuid, int npcGuid)
	{
		bTreeConversation1 = true;
		AIOff();
		CodexPlayer.TeleportPartyToConversation(CodexPlayer.GetCurrentPlayer(), 1);
		ExecuteConversation(starterGuid, npcGuid, "17_1_Tree1", "17_1_Tree1.nco", CONV_XFLAG_WANTFEEDBACK);
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bShopMaskLine)
		{
			AIOn();
			bShopMaskLine = false;
			CodexCamera.Release(starterGuid);
		}

		if(bTreeConversation1)
		{
			AIOn();
			bTreeConversation1 = false;
			CodexCamera.Release(starterGuid);
		}
	}
	
	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bShopMaskLine)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, 0);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}

		if(bTreeConversation1)
		{
			switch(curLine)
			{
				case 0:

					CodexCamera.SetupCutscene(starterGuid, christofGUID, serenaGUID);
					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;

				case 1:

					CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_C, CAM_ANGLE_MEDIUM, CAM_DIST_MEDIUM, 0, 0);
					break;
			}
		}
	}
}

