/**
 * Nosferatu Obfuscation script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class ClassNosferatu extends Codex
{
	public ClassNosferatu()
	{	
	}

	public void beginscene(int clientGuid, int captureID)
	{
		CodexActor nosferatuActor = new CodexActor(GetClassThing());

		if(nosferatuActor != null)
		{
//			nosferatuActor.AddActorEffectByLevel("ef_disc_cloakofshadows2", 0, 0, 0, 0);
//			nosferatuActor.AddActorEffectByLevel("ef_disc_cloakofshadows", 0, 0, 0, 0);

		}
	}
/*
	public void created(int guid)
	{
		CodexActor nosferatuActor = new CodexActor(GetClassThing());

		if(nosferatuActor != null)
		{
			nosferatuActor.AddActorEffectByLevel("ef_disc_cloakofshadows2", 0, 0, 0, 0);
			nosferatuActor.AddActorEffectByLevel("ef_disc_cloakofshadows", 0, 0, 0, 0);

		}
	}
*/
}