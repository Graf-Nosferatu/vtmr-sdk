/**
 * Trap player in a closed room until monsters are defeated
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 
public class TrapCloseRoom extends Codex
{
	private CodexRegion		_trapRegion;

	private CodexThing		_rotateDoor1;
	private CodexThing		_rotateDoor2;

	private int				_numberOfEnemies;

	private String			_enemy1Type = "";
	private String			_enemy2Type = "";
	private String			_enemy3Type = "";
	private String			_enemy4Type = "";
	private String			_enemy5Type = "";

	private CodexThing		_spawn1;
	private CodexThing		_spawn2;
	private CodexThing		_spawn3;
	private CodexThing		_spawn4;
	private CodexThing		_spawn5;

	private int				_enemiesDead = 0;

	private int				initiatorGuid;

	private boolean			_bTrapOn = true;
	private boolean			_bClosed = true;
	private boolean			bSpawned = false;

	public static String _params[] = {"Rotate door 1", "Rotate door 2", "Number of enemies needed to kill;5", 
										"Enemy 1 type (template name)", "Enemy 2 type (template name)", "Enemy 3 type (template name)", 
										"Enemy 4 type (template name)", "Enemy 5 type (template name)", 
										"Spawn 1", "Spawn 2", "Spawn 3", "Spawn 4", "Spawn 5"};

	public TrapCloseRoom(CodexThing rotateDoor1, CodexThing rotateDoor2, int numberOfEnemies, 
						String enemy1Type, String enemy2Type, String enemy3Type, 
						String enemy4Type, String enemy5Type, 
						CodexThing spawn1, CodexThing spawn2, CodexThing spawn3, CodexThing spawn4, CodexThing spawn5)
	{
		_trapRegion = new CodexRegion(GetClassThing());

		_rotateDoor1 = new CodexThing(rotateDoor1.GetGUID());
		_rotateDoor2 = new CodexThing(rotateDoor2.GetGUID());

		_numberOfEnemies = numberOfEnemies;

		_enemy1Type = enemy1Type;
		_enemy2Type = enemy2Type;
		_enemy3Type = enemy3Type;
		_enemy4Type = enemy4Type;
		_enemy5Type = enemy5Type;

		_spawn1 = new CodexThing(spawn1.GetGUID());
		_spawn2 = new CodexThing(spawn2.GetGUID());
		_spawn3 = new CodexThing(spawn3.GetGUID());
		_spawn4 = new CodexThing(spawn4.GetGUID());
		_spawn5 = new CodexThing(spawn5.GetGUID());

		CaptureThing(_trapRegion.GetGUID());
		CaptureThing(_rotateDoor1.GetGUID());
		CaptureThing(_rotateDoor2.GetGUID());

		if(_rotateDoor1.GetDescriptionID().equalsIgnoreCase("PROP"))
			_rotateDoor1.SetDescriptionID("GEN_DOOR");

		if(_rotateDoor2.GetDescriptionID().equalsIgnoreCase("PROP"))
			_rotateDoor2.SetDescriptionID("GEN_DOOR");
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(bSpawned)
			return;

		// make door block visibility
		_rotateDoor1.SetThingFlags(THING_FLAG_VISBLOCK);
		_rotateDoor2.SetThingFlags(THING_FLAG_VISBLOCK);

		for(int i = 0; i < _numberOfEnemies; i++)
		{
			switch(i)
			{
				case 0:
					CaptureThing(_spawn1.SpawnThing(_enemy1Type));
					break;
				case 1:
					CaptureThing(_spawn2.SpawnThing(_enemy2Type));
					break;
				case 2:
					CaptureThing(_spawn3.SpawnThing(_enemy3Type));
					break;
				case 3:
					CaptureThing(_spawn4.SpawnThing(_enemy4Type));
					break;
				case 4:
					CaptureThing(_spawn5.SpawnThing(_enemy5Type));
					break;
			}
		}

		bSpawned = true;
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		if(guid == _rotateDoor1.GetGUID() && _bTrapOn)
		{
			if(_bClosed)
			{
				_rotateDoor1.RotatePivot(1, 3);

				_bClosed = false;
				//_rotateDoor1.ClearThingFlags(THING_FLAG_VISBLOCK);
			}
			else if(!_bClosed)
			{
				_rotateDoor1.RotatePivot(1, -3);

				_bClosed = true;
				//_rotateDoor1.SetThingFlags(THING_FLAG_VISBLOCK);
			}
		}
		else
		{
			// sound indicating this door is locked
			new CodexSound("locked_large_02.WAV", 300, 600, 100, 0, 0, guid);

			// display locked message
			CodexConsole.PrintNLS(CodexPlayer.GetCurrentPlayer(), 0, "GEN_DOORLOCKED");
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		// if it's not a player, the trap is not on, or the doors are already closed, return
		if(!IsPlayerGuid(causeGUID) || !_bTrapOn ||	_bClosed)
		{
			return;
		}
		else
		{
			// remember the guid of the guy who triggered the trap
			initiatorGuid = causeGUID;
			CaptureThing(initiatorGuid);

			// else close door to trap the player inside the room
			_rotateDoor1.RotatePivot(1, -1);

			// disable trap
			_bTrapOn = false;

			// doors closed
			_bClosed = true;
			//_rotateDoor1.SetThingFlags(THING_FLAG_VISBLOCK);

			// timer to open the doors 45 seconds later, just as a failsafe check in
			// case something strange happens and they don't open by the intended method
			SetTimer(45);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		// setting this to a number above what is possible (enemies in this trap
		// are limited to 5) so if the "initiatorGuid" causes the doors to open and the party
		// comes in and kills the other enemies the doors won't rotate again
		_enemiesDead = 10;

		// open doors again
		_rotateDoor1.RotatePivot(1, 1);
		_rotateDoor2.RotatePivot(1, 1);
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == initiatorGuid ||
		++_enemiesDead == _numberOfEnemies)
		{
			// kill the failsafe timer 
			KillAllTimers();

			// open doors again
			_rotateDoor1.RotatePivot(1, 1);
			_rotateDoor2.RotatePivot(1, 1);

			//_rotateDoor1.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_rotateDoor2.ClearThingFlags(THING_FLAG_VISBLOCK);

			// setting this to a number above what is possible (enemies in this trap
			// are limited to 5) so if the "initiatorGuid" causes the doors to open and the party
			// comes in and kills the other enemies the doors won't rotate again
			_enemiesDead = 10;
		}
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(_bTrapOn);
		CodexSequence.SaveBoolean(_bClosed);
		CodexSequence.SaveBoolean(bSpawned);
		CodexSequence.SaveInt(_enemiesDead);
		CodexSequence.SaveInt(_numberOfEnemies);
	}
 
	public void restore(int flags)
	{
		_bTrapOn = CodexSequence.RestoreBoolean();
		_bClosed = CodexSequence.RestoreBoolean();
		bSpawned = CodexSequence.RestoreBoolean();
		_enemiesDead = CodexSequence.RestoreInt();
		_numberOfEnemies = CodexSequence.RestoreInt();
	}
}



