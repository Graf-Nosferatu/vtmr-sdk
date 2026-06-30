/**
 * Trap player in a closed room until monsters are defeated
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 
public class TrapCloseRoom2 extends Codex
{
	private CodexRegion		_trapRegion;

	private CodexThing		_door1;
	private CodexThing		_door2;

	private int				_speed;

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

	public static String _params[] = {"Door 1", "Door 2", "Speed;300", "Number of enemies needed to kill;5", 
										"Enemy 1 type (template name)", "Enemy 2 type (template name)", "Enemy 3 type (template name)", 
										"Enemy 4 type (template name)", "Enemy 5 type (template name)", 
										"Spawn 1", "Spawn 2", "Spawn 3", "Spawn 4", "Spawn 5"};

	public TrapCloseRoom2(CodexThing door1, CodexThing door2, int speed, int numberOfEnemies, 
						String enemy1Type, String enemy2Type, String enemy3Type, 
						String enemy4Type, String enemy5Type, 
						CodexThing spawn1, CodexThing spawn2, CodexThing spawn3, CodexThing spawn4, CodexThing spawn5)
	{
		_trapRegion = new CodexRegion(GetClassThing());

		_door1 = new CodexThing(door1.GetGUID());
		_door2 = new CodexThing(door2.GetGUID());
		
		_speed = speed;

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
		CaptureThing(_door1.GetGUID());
		CaptureThing(_door2.GetGUID());

		if(_door1.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door1.SetDescriptionID("GEN_DOOR");

		if(_door2.GetDescriptionID().equalsIgnoreCase("PROP"))
			_door2.SetDescriptionID("GEN_DOOR");
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(bSpawned)
			return;

		// make door block visibility
		_door1.SetThingFlags(THING_FLAG_VISBLOCK);
		_door2.SetThingFlags(THING_FLAG_VISBLOCK);

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
		if(guid == _door1.GetGUID() && _bTrapOn)
		{
			if(_bClosed)
			{
				_door1.MoveToFrame(1, _speed);

				_bClosed = false;
				//_door1.ClearThingFlags(THING_FLAG_VISBLOCK);
			}
			else if(!_bClosed)
			{
				_door1.MoveToFrame(0, _speed);

				_bClosed = true;
				//_door1.SetThingFlags(THING_FLAG_VISBLOCK);
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
			_door1.MoveToFrame(0, _speed * 2);

			// disable trap
			_bTrapOn = false;

			// doors closed
			_bClosed = true;
			//_door1.SetThingFlags(THING_FLAG_VISBLOCK);

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
		_door1.MoveToFrame(1, _speed);
		_door2.MoveToFrame(1, _speed);
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == initiatorGuid ||
		++_enemiesDead == _numberOfEnemies)
		{
			// kill the failsafe timer 
			KillAllTimers();

			// open doors again
			_door1.MoveToFrame(1, _speed);
			_door2.MoveToFrame(1, _speed);

			//_door1.ClearThingFlags(THING_FLAG_VISBLOCK);
			//_door2.ClearThingFlags(THING_FLAG_VISBLOCK);

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



