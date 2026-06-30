/**
 * Skin Pact LOT script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class LOT_SkinPact extends Codex
{
	private MP_LOTChronicle	chronScript;

	private CodexThing		_skinPact;
	private CodexRegion		_questCompleteRegion;

	public static String _params[] = {"Quest complete region"};

	public LOT_SkinPact(CodexRegion questCompleteRegion)
	{
		chronScript = (MP_LOTChronicle)GetChronicleScript(0);

		_skinPact = new CodexThing(GetClassThing());
		_questCompleteRegion = new CodexRegion(questCompleteRegion.GetGUID());

		CaptureThing(_questCompleteRegion.GetGUID());
	}

	public boolean pickup(int item, int picker, int captureId)
	{
		if(item == _skinPact.GetGUID())
		{
			chronScript.Step(chronScript.WAR_FOUNDPACT);

			// set to indicate they're "keeping" it, it is cleared in
			// LOT_SLUM_7_1.java if they go there and decide not to keep it
			CodexSequence.SetChronicleFlag(chronScript.WAR_KEEPPACT);

			// set chronicle flag that is checked in SLUM_7_1.java
			//CodexSequence.SetChronicleFlag(chronScript.WAR_FOUNDPACT);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_BloodPact"));
			//q.Complete();
		}

		return(true);
	}

	public void entered(int guid, int causeGuid, int captureID)
	{
		if(!CodexSequence.GetChronicleFlag(chronScript.WAR_QUESTCOMPLETE))
		{
			chronScript.Step(chronScript.WAR_QUESTCOMPLETE);

			//CodexQuest q = new CodexQuest(CodexQuest.Load("LOT_InvadeWarehouse"));
			//q.Complete();
		}
	}

}

