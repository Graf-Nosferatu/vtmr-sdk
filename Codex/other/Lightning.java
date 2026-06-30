/**
 * Lightning script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class Lightning extends Codex
{
	public Lightning()
	{	
	}

	public void beginscene(int clientGuid, int captureID)
	{	
		DisableLightStyle(7);
		MaterialSetFrame("lightning1", 0);
		DisableLightStyle(8);
		MaterialSetFrame("lightning2", 0);
		DisableLightStyle(9);
		MaterialSetFrame("lightning3", 0);

		MaterialSetFrame("lightningflare1", 0);
		MaterialSetFrame("lightningflare2", 0);
		MaterialSetFrame("lightningflare3", 0);

		SetTimer((float)(1 + Math.random() * (4 - 1)), 7, 0);
		SetTimer((float)(1 + Math.random() * (4 - 1)), 8, 0);
		SetTimer((float)(1 + Math.random() * (4 - 1)), 9, 0);
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case 7:
				if(arg0 == 1)
				{
					DisableLightStyle(7);
					MaterialSetFrame("lightning1", 0);
					MaterialSetFrame("lightningflare1", 0);
					SetTimer((float)(1 + Math.random() * (4 - 1)), 7, 0);
				}
				else
				{
					EnableLightStyle(7);
					MaterialSetFrame("lightning1", 1);
					MaterialSetFrame("lightningflare1", 1);
					SetTimer((float)0.2, 7, 1);
				}
				break;

			case 8:
				if(arg0 == 1)
				{
					DisableLightStyle(8);
					MaterialSetFrame("lightning2", 0);
					MaterialSetFrame("lightningflare2", 0);
					SetTimer((float)(1 + Math.random() * (4 - 1)), 8, 0);
				}
				else
				{
					EnableLightStyle(8);
					MaterialSetFrame("lightning2", 1);
					MaterialSetFrame("lightningflare2", 1);
					SetTimer((float)0.2, 8, 1);
				}
				break;

			case 9:
				if(arg0 == 1)
				{
					DisableLightStyle(9);
					MaterialSetFrame("lightning3", 0);
					MaterialSetFrame("lightningflare3", 0);
					SetTimer((float)(1 + Math.random() * (4 - 1)), 9, 0);
				}
				else
				{
					EnableLightStyle(9);
					MaterialSetFrame("lightning3", 1);
					MaterialSetFrame("lightningflare3", 1);
					SetTimer((float)0.2, 9, 1);
				}
				break;

		}
	}
}