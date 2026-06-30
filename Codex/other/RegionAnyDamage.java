/**
 *  RegionAnyDamage script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class RegionAnyDamage extends Codex
{
	private CodexPlayer		playerToDamage;

	private String			_effect;
	private float			_damageAmount = (float)2.0;

	public static String _params[] =  {"Effect name (from .ned file)", "Damage amount (per tick in .ned file);2"};

	public RegionAnyDamage(String effect, float damageAmount)
	{
		_effect = effect;
		_damageAmount = damageAmount;
	}

	public void entered(int guid, int causeGuid, int captureID)
	{
		CodexActor enterer = new CodexActor(causeGuid);
		
		enterer.AddActorEffectByValue(_effect, 0, _damageAmount, 0, 0);
	}

	public void exited(int guid, int causeGuid, int captureID)
	{
		CodexActor exiter = new CodexActor(causeGuid);
		
		exiter.RemoveActorEffect(_effect);
	} 

	public void endscene(int clientGuid, int captureID, int exitNum)
	{
		CodexPlayer tempPlayer;
		int playerNum;

		if(NetIsServer())
		{
			// we're in a MP game

			int numPlayers = CodexPlayer.GetMaxMPPlayers();
			int i;

			for(i = 0;i < numPlayers;i++)
			{
				if(CodexPlayer.GetMPPlayer(i) != 0)
				{
					tempPlayer = new CodexPlayer(CodexPlayer.GetMPPlayer(i));

					tempPlayer.RemoveActorEffect(_effect);
				}
			}
		}
		else
		{
			// we're in a SP game

			// remove damage effects if player is still in region when level transitions
			for(playerNum = 0; playerNum < CodexPlayer.GetNumPartyPlayers(); playerNum++)
			{
				tempPlayer = new CodexPlayer(CodexPlayer.GetPartyPlayer(playerNum));

				tempPlayer.RemoveActorEffect(_effect);
			}
		}
	}
}
