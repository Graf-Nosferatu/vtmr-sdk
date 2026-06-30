/**
 * CTD Derelict script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class CTD_Derelict extends Codex
{
	private CodexActor		derelict;

//	private boolean			bActive = false;

	private boolean			bDerelictConversation = false;

	public CTD_Derelict()
	{
		derelict = new CodexActor(GetClassThing());
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		//if(bActive)
		//	return;

		if(guid == derelict.GetGUID())
		{
			CodexThing initiatorThing = new CodexThing(clickerGuid);
			int locationNum = initiatorThing.GetLocationNum();

			String aFormat = "%a" + "ILIG" + "%t" + "CLICKED" + "%L" + CodexSequence.GetLocationName(locationNum);
			//CodexConsole.Print(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, FormatNLS("MP_ST_CONVTRIGGER_MSG", aFormat), 0x317FF4);
			CodexConsole.PrintFormatNLS(0, CONSOLEPRINT_FLAG_SERVERCONSOLE, "MP_ST_CONVTRIGGER_MSG", aFormat, 0x317FF4);

			// check here if ST is handling the conversation, if not, play the line
			if(!NetIsNoAutoConversations())
			{
				DerelictConversation(clickerGuid, guid);

				//bActive = true;
				
				// set inactive in 6 seconds (about the length of the line)
				//SetTimer(6);

				// say hint line
				//new CodexSound("Ilig_3_1_92", 1000, 1500, 100, 0, 0, derelict.GetGUID());
			}
		}
	}

//	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
//	{
//		bActive = false;
//	}

	public void DerelictConversation(int starterGuid, int npcGuid)
	{
		if(ExecuteConversation(starterGuid, npcGuid, "3_1_Derelict", "3_1_Derelict.nco", CONV_XFLAG_WANTFEEDBACK))
		{
			bDerelictConversation = true;
			AIOff();	
		}
	}

	public void convended(int starterGuid,  boolean bAborted , int returnValue)
	{
		if(bDerelictConversation)
		{
			AIOn();
			bDerelictConversation = false;
			CodexCamera.Release(starterGuid);
		}
	}

	public void convreached(int starterGuid, int curEvent, int curLine, int lineDuration, int speakerGuid)
	{
		if(bDerelictConversation)
		{
			switch(curEvent)
			{
				case 0:
					switch(curLine)
					{
						case 0:

							CodexCamera.SetupCutscene(starterGuid, speakerGuid, 0);
							CodexCamera.SetShot(starterGuid, CAM_SHOT_POV_A, CAM_ANGLE_MEDIUMHIGH, CAM_DIST_LONG, 0, 0);
							break;
					}
			}
		}
	}
}