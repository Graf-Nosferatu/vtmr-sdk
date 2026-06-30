/**
 * Spawn Random treasure/monster when triggered
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/
 
public class MysticSpawner extends Codex
{
	private String			_randomGood1 = "";
	private String			_randomGood2 = "";
	private String			_randomBad1 = "";
	private String			_randomBad2 = "";

	private CodexThing		_spawnPoint;
	private CodexThing		_particles;

	private int				particlesGuid;

	private boolean			bStarted = false;
	private boolean			bSpawned = false;

	public static String _params[] = {"Random Good 1 (template name)", "Random Good 2 (template name)",
										"Random Bad 1 (template name)", "Random Bad 2 (template name)"};

	public MysticSpawner(String randomGood1, String randomGood2,
						String randomBad1, String randomBad2)
	{
		_spawnPoint = new CodexThing(GetClassThing());

		_randomGood1 = randomGood1;
		_randomGood2 = randomGood2;
		_randomBad1 = randomBad1;
		_randomBad2 = randomBad2;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(bSpawned)
			return;

		if(bStarted)
		{
			_particles = new CodexThing(particlesGuid);
		}
		else
		{
			_particles = new CodexThing(_spawnPoint.SpawnThing("mysticIdleMagic"));
		
			particlesGuid = _particles.GetGUID();

			bStarted = true;
		}
	}

	void touched(int guid, int toucherGuid, int captureID)
	{
		int nRandom;

		if(!IsPlayerGuid(toucherGuid) || bSpawned)
			return;

		_particles.Remove();

		nRandom = (int)((Math.random() * 4) + 1);

		switch(nRandom)
		{
			case 1:
				_spawnPoint.SpawnThing(_randomGood1);
				_spawnPoint.SpawnThing("mysticGoodMagic");
				break;

			case 2:
				_spawnPoint.SpawnThing(_randomGood2);
				_spawnPoint.SpawnThing("mysticGoodMagic");
				break;

			case 3:
				_spawnPoint.SpawnThing(_randomBad1);
				_spawnPoint.SpawnThing("mysticBadMagic");
				break;

			case 4:
				_spawnPoint.SpawnThing(_randomBad2);
				_spawnPoint.SpawnThing("mysticBadMagic");

				break;
		}
		CodexSound effectSound = new CodexSound("teleport_04.wav", 200.0f, 512.0f, 80, 0, 0, guid);
		bSpawned = true;
	}

	public void save(int flags)
	{
		CodexSequence.SaveBoolean(bStarted);
		CodexSequence.SaveBoolean(bSpawned);
		CodexSequence.SaveInt(particlesGuid);
	}
 
	public void restore(int flags)
	{
		bStarted = CodexSequence.RestoreBoolean();
		bSpawned = CodexSequence.RestoreBoolean();
		particlesGuid = CodexSequence.RestoreInt();
	}
}



