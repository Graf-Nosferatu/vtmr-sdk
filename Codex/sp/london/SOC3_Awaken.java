/**
 * Society 3 Awakening script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class SOC3_Awaken extends Codex
{
	private LondonChronicle	chronScript;

	static final int	MAX_GHOSTS	= 16;
	CodexThing			ghosts[];

	float				position[];
	float				orientation[];

	public static String _params[] = {"g0", "g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15"};

	public SOC3_Awaken(	CodexThing g0, CodexThing g1, CodexThing g2, CodexThing g3, CodexThing g4, CodexThing g5, CodexThing g6, CodexThing g7,
						CodexThing g8, CodexThing g9, CodexThing g10, CodexThing g11, CodexThing g12, CodexThing g13, CodexThing g14, CodexThing g15)
	{
		chronScript = (LondonChronicle)GetChronicleScript(0);

		position			= new float[3];
		orientation			= new float[3];

		ghosts				= new CodexThing [MAX_GHOSTS];

		ghosts[0]			= new CodexThing(g0.GetGUID());
		ghosts[1]			= new CodexThing(g1.GetGUID());
		ghosts[2]			= new CodexThing(g2.GetGUID());
		ghosts[3]			= new CodexThing(g3.GetGUID());
		ghosts[4]			= new CodexThing(g4.GetGUID());
		ghosts[5]			= new CodexThing(g5.GetGUID());
		ghosts[6]			= new CodexThing(g6.GetGUID());
		ghosts[7]			= new CodexThing(g7.GetGUID());
		ghosts[8]			= new CodexThing(g8.GetGUID());
		ghosts[9]			= new CodexThing(g9.GetGUID());
		ghosts[10]			= new CodexThing(g10.GetGUID());
		ghosts[11]			= new CodexThing(g11.GetGUID());
		ghosts[12]			= new CodexThing(g12.GetGUID());
		ghosts[13]			= new CodexThing(g13.GetGUID());
		ghosts[14]			= new CodexThing(g14.GetGUID());
		ghosts[15]			= new CodexThing(g15.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		int			itemGuid;
		CodexItem	item;

		int			ghostIndex = 0;

		// if we've already dropped the inventory, don't do it again
		if(CodexSequence.GetChronicleFlag(chronScript.SOC3_DROPPEDINVENTORY))
			return;

		// get the Christof actor
		CodexActor christof	= new CodexActor(CodexThing.GuidFromCastID("Christof"));

		// walk through the inventory
		itemGuid = christof.GetActorFirstInventoryItem();

		while((itemGuid > 0))
		{
			item = new CodexItem(itemGuid);

			// get next in inventory to prepare for next loop
			itemGuid = item.GetNextInventoryItem();	

			// remove the item from Christof's inventory
			item.RemoveItemFromInventory();

			// make a test to see if the item must be placed on a ghost or destroyed
			if((item.GetItemFlags() & ITEM_FLAG_CARRYTOMODERNDAY) != 0)
			{
				// get the position and orientation from the current ghost
				position = ghosts[ghostIndex].GetPosition();
				orientation = ghosts[ghostIndex].GetOrientation();

				// move the item that position and orientation
				item.SetOrientation(orientation);
				item.SetPosition(position);

				// increment the current ghost
				ghostIndex++;

				// in case we have too many items, just reuse the ghosts... not pretty, but...
				if(ghostIndex >= MAX_GHOSTS)
					ghostIndex = 0;
			}
			else
			{
				// we destroy the object
				item.Remove();
			}
		}

		// flag that says we've dropped the inventory
		CodexSequence.SetChronicleFlag(chronScript.SOC3_DROPPEDINVENTORY);
	}
}

