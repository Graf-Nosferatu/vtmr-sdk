/**
 * ItemGangrelEye script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ItemGangrelEye extends Codex
{
	int			gleamGuid = 0;

	public ItemGangrelEye()
	{
	}

	public void CreateTorch()
	{
		CodexThing gangrelEye = new CodexThing(GetClassThing());

		gleamGuid = gangrelEye.SpawnThing("gangrelEyeEffect");
		int boneNum = gangrelEye.FindBone(MOTIONTAG_USER0);

		float[] offset = new float[3];
		offset = gangrelEye.FindBoneOffset(MOTIONTAG_USER0);

		gangrelEye.AttachThing(gleamGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE);
	}

	public void DestroyTorch()
	{
		if(gleamGuid != 0)
		{
			CodexThing gleam = new CodexThing(gleamGuid);
			gleam.Remove();
			gleamGuid = 0;
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
		CodexItem gangrelEye = new CodexItem(GetClassThing());
			
		// if worn
		if((gangrelEye.GetItemFlags() & ITEM_FLAG_BEINGWORN) != 0)
			CreateTorch();
	}

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		DestroyTorch();
	}

}