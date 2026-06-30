/**
 * Conveyer Hold script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ConveyerHold extends Codex
{
	private CodexThing	_conveyer;
	private CodexThing	_heldObject;
	private CodexThing	_spikes;
	private CodexThing	_spikesBlood;
	private CodexThing	_rollerBlood;
	private CodexThing	_steam1;
	private CodexThing	_steam2;

	// --------------------------------------------------------------------------------------------

	public static String _params[] = {"Held Object", "Spikes", "Spikes Blood", "Roller Blood", "Steam 1", "Steam 2"};

	public ConveyerHold(CodexThing heldObject, CodexThing spikes, CodexThing spikesBlood, CodexThing rollerBlood, CodexThing steam1, CodexThing steam2)
	{	
		_heldObject	= new CodexThing(heldObject.GetGUID());
		_spikes	= new CodexThing(spikes.GetGUID());
		_spikesBlood = new CodexThing(spikesBlood.GetGUID());
		_rollerBlood = new CodexThing(rollerBlood.GetGUID());
		_steam1 = new CodexThing(steam1.GetGUID());
		_steam2 = new CodexThing(steam2.GetGUID());

		CaptureThing(_spikes.GetGUID());
	}

	// --------------------------------------------------------------------------------------------

	public void beginscene(int clientGuid, int captureID)
	{
		float[]			offset = new float[3];
		
		offset[0] = 0; offset[1] = 0; offset[2] = -188;

		_conveyer = new CodexThing(GetClassThing());

		_conveyer.AttachThing(_heldObject.GetGUID(), -1, offset, ATTACH_FLAG_AUTOREMOVE);

		_conveyer.MoveToFrame(1, 100);
	}

	public void arrived(int thingGuid, int frameNum, int captureId)
	{
		if(thingGuid == _conveyer.GetGUID())
		{
			int		boneNum;
			int		bloodGuid;
			float[]	offset = new float[3];
			float[]	orient = new float[3];

			offset[0] = 0; offset[1] = 0; offset[2] = 0;

			if(frameNum + 1 < _conveyer.GetNumFrames())
			{
				switch(frameNum)
				{
					case 1: // spikes/blood
						_spikes.MoveToFrame(1, 300); // trigger spikes
						_spikesBlood.SpawnThing("blood0"); // trigger blood

						// random blood spurts on model
						for(boneNum = 0; boneNum < 20; boneNum++)
						{
							if(Math.random() < 0.33)
							{
								bloodGuid = _heldObject.SpawnThing("blood0");

								CodexThing tempBlood = new CodexThing(bloodGuid);
								orient[0] = ((float)Math.random() * 360 - 180);
								orient[1] = ((float)Math.random() * 360 - 180);
								orient[2] = ((float)Math.random() * 360);

								tempBlood.SetOrientation(orient);
								_heldObject.AttachThing(bloodGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE + 0x2000);
							}
						}
						SetTimer(2, 1, frameNum);
						break;

					case 2: // roller/blood
						// trigger blood
						_rollerBlood.SpawnThing("blood0"); // trigger blood;
						// random blood drips on model
						for(boneNum = 0; boneNum < 20; boneNum++)
						{
							if(Math.random() < 0.33)
							{
								bloodGuid = _heldObject.SpawnThing("blood");
								_heldObject.AttachThing(bloodGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE);
							}
						}
						_conveyer.MoveToFrame(frameNum + 1, 100);
						break;

					case 6:
						_conveyer.MoveToFrame(frameNum + 1, 448);
						break;

					case 7: // steam 1
						_steam1.SpawnThing("steamBlast"); // trigger steam 1
						SetTimer(2, 7, frameNum);
						break;

					case 9:
						_conveyer.MoveToFrame(frameNum + 1, 448);
						break;

					case 10: // steam 2
						_steam2.SpawnThing("steamBlast"); // trigger steam 2
						SetTimer(2, 10, frameNum);
						break;

					default:
						_conveyer.MoveToFrame(frameNum + 1, 100);
						break;
				}
			}
			else
			{
				// change to JumpToFrame when available
				_conveyer.MoveToFrame(1, 100);
			}
		}
		else
		{
			if(frameNum == 1)
				_spikes.MoveToFrame(0, 300);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		float[]	offset = new float[3];
		offset[0] = 0; offset[1] = 0; offset[2] = 0;

		switch(timerID)
		{
			case 1:
				_conveyer.MoveToFrame((int)arg0 + 1, 100);
				break;

			case 7:
				int boneNum;

				for(boneNum = 0; boneNum < 20; boneNum++)
				{
					if(Math.random() < 0.33)
					{
						int gunkGuid = _heldObject.SpawnThing("blood");
						_heldObject.AttachThing(gunkGuid, boneNum, offset, ATTACH_FLAG_AUTOREMOVE + 0x2000);
					}
				}
				_conveyer.MoveToFrame((int)arg0 + 1, 112);
				break;

			case 10:
				int steamGuid = _heldObject.SpawnThing("steamBlast");
				_heldObject.AttachThing(steamGuid, -1, offset, ATTACH_FLAG_AUTOREMOVE);
				_conveyer.MoveToFrame((int)arg0 + 1, 112);
				break;

		}
	}
}
