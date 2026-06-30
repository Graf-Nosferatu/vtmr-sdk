/**
 * Ardan Chantry 1 main script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class classGroupFear extends Codex
{
	private CodexActor	_enemyLeader;
	private CodexActor	_enemy1;
	private CodexActor	_enemy2;
	private CodexActor	_enemy3;
	private CodexActor	_enemy4;
	private CodexActor	_enemy5;

	private boolean		bLeaderDead = false;

	public static String _params[] = {"Group Leader", "Enemy 1", "Enemy 2", "Enemy 3", "Enemy 4", "Enemy 5"};
	
	public classGroupFear(CodexActor enemyLeader, CodexActor enemy1, CodexActor enemy2, CodexActor enemy3, CodexActor enemy4, CodexActor enemy5)
	{
		_enemyLeader = new CodexActor(enemyLeader.GetGUID());
		_enemy1 = new CodexActor(enemy1.GetGUID());
		_enemy2 = new CodexActor(enemy2.GetGUID());
		_enemy3 = new CodexActor(enemy3.GetGUID());
		_enemy4 = new CodexActor(enemy4.GetGUID());
		_enemy5 = new CodexActor(enemy5.GetGUID());

		CaptureThing(_enemyLeader.GetGUID());
		CaptureThing(_enemy1.GetGUID());
		CaptureThing(_enemy2.GetGUID());
		CaptureThing(_enemy3.GetGUID());
		CaptureThing(_enemy4.GetGUID());
		CaptureThing(_enemy5.GetGUID());
	}

	public void killed(int guid, int causeID, int captureID)
	{
		if(guid == _enemyLeader.GetGUID())
		{
			bLeaderDead = true;
		}

		if(bLeaderDead)
		{
			int effectGuid;

			if((_enemy1.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				effectGuid = _enemy1.FindActorEffect("ef_afraid");
				if(effectGuid != 0)
					_enemy1.RemoveActorEffect(effectGuid);
				
				_enemy1.AddActorEffectByLevel("ef_afraid", 3000, 2-1, causeID, 0);
			}
			if((_enemy2.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				effectGuid = _enemy2.FindActorEffect("ef_afraid");
				if(effectGuid != 0)
					_enemy2.RemoveActorEffect(effectGuid);
				
				_enemy2.AddActorEffectByLevel("ef_afraid", 3000, 2-1, causeID, 0);
			}
			if((_enemy3.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				effectGuid = _enemy3.FindActorEffect("ef_afraid");
				if(effectGuid != 0)
					_enemy3.RemoveActorEffect(effectGuid);
				
				_enemy3.AddActorEffectByLevel("ef_afraid", 3000, 2-1, causeID, 0);
			}
			if((_enemy4.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				effectGuid = _enemy4.FindActorEffect("ef_afraid");
				if(effectGuid != 0)
					_enemy4.RemoveActorEffect(effectGuid);
				
				_enemy4.AddActorEffectByLevel("ef_afraid", 3000, 2-1, causeID, 0);
			}
			if((_enemy5.GetActorFlags() & THING_AF_DEAD) == 0)
			{
				effectGuid = _enemy5.FindActorEffect("ef_afraid");
				if(effectGuid != 0)
					_enemy5.RemoveActorEffect(effectGuid);
				
				_enemy5.AddActorEffectByLevel("ef_afraid", 3000, 2-1, causeID, 0);
			}

		}
	}
}

