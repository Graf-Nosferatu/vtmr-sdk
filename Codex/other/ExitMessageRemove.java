/**
 * Exit Message Remove script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ExitMessageRemove extends Codex
{
	private CodexThing		exitThing;

	public ExitMessageRemove()
	{
		exitThing = new CodexThing(GetClassThing());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		///////////////////////////////////////////////////////////////////////////////
		// 0x10000 is a user flag that is being checked to see whether or not to leave 
		// the exit rollover that is in the static scene in the world or not - if we're 
		// in the prague chronicle, the rollover will be removed from the static scene 
		// because the rollovers in the 5.1 scene are being hidde/shown at the appropriate times                                          
                                                                                    
		// SGT                                                                            
		///////////////////////////////////////////////////////////////////////////////

		if((CodexSequence.GetChronicleInfoFlags() & 0x10000) != 0)
			exitThing.Remove();
	}
}
