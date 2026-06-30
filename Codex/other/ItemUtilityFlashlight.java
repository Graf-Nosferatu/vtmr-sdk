/**
 * ItemUtilityFlashlight script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ItemUtilityFlashlight extends Codex
{
	int			flameGuid = 0;

	public ItemUtilityFlashlight()
	{
	}

	public void CreateTorch()
	{
		CodexThing flashlight = new CodexThing(GetClassThing());

		flameGuid = flashlight.SpawnThing("flashlightEffect");
		int boneNum = flashlight.FindBone(MOTIONTAG_USER0);

		float[] offset = new float[3];
		offset = flashlight.FindBoneOffset(MOTIONTAG_USER0);

		flashlight.AttachThing(flameGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE);
	}

	public void DestroyTorch()
	{
		if(flameGuid != 0)
		{
			CodexThing flame = new CodexThing(flameGuid);
			flame.Remove();
			flameGuid = 0;
		}
	}

	public void worn(int guid, int wearerGuid, int captureId)
	{
		CreateTorch();
	}

	public boolean unworn(int guid, int wearerGuid, int captureId)
	{
		DestroyTorch();
		return(true);
	}

	public void beginscene(int clientGuid, int captureID)
	{
		CodexItem torch = new CodexItem(GetClassThing());
			
		// if worn
		if((torch.GetItemFlags() & ITEM_FLAG_BEINGWORN) != 0)
			CreateTorch();
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		DestroyTorch();
	}

}