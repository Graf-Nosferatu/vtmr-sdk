/**
 * New York Uptown 40.1 Outside Cathedral script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class UTWN_40_1_OutsideCathedral extends Codex
{
	private NewYorkChronicle	chronScript;

	private static final int	TIMER_ID_MUSIC		= 0;
	private static final int	TIMER_ID_EXTCATH	= 1;
	private static final int	TIMER_ID_CLOCK1		= 2;
	private static final int	TIMER_ID_INTCATH	= 3;
	private static final int	TIMER_ID_CLOCK2		= 4;
	private static final int	TIMER_ID_OUTSIDE2	= 5;
	private static final int	TIMER_ID_LIDBLOW	= 6;
	private static final int	TIMER_ID_VUKHAND	= 7;
	private static final int	TIMER_ID_ENDSCENE	= 8;
	private static final int	TIMER_ID_COUNT3		= 9;
	private static final int	TIMER_ID_COUNT2		= 10;
	private static final int	TIMER_ID_COUNT1		= 11;
	private static final int	TIMER_ID_LIDSMASH	= 12;
	private static final int	TIMER_ID_FADEOUT	= 13;

	private CodexActor			_Vukodlak;
	private CodexActor			_Christof;
	private CodexActor			_Wilhem;
	private CodexActor			_Lily;
	private CodexActor			_Samuel;
	private CodexThing			_SarcophagusLid;
	private CodexThing			_MinuteHand;
	private CodexThing			_HourHand;

	private CodexSound			crowdAmb;
	private CodexSound			vukAmb;

	private int					christofGUID;
	private int					wilhemGUID;
	private int					lilyGUID;
	private int					samuelGUID;

	private float[]				pos;

	public static String _params[] = {"Sarcophagus Lid", "Minute Hand", "Hour Hand", "Vukodlak"};

	public UTWN_40_1_OutsideCathedral(CodexThing SarcophagusLid, CodexThing MinuteHand, CodexThing HourHand, CodexActor Vukodlak)
	{
		chronScript = (NewYorkChronicle)GetChronicleScript(0);

		_SarcophagusLid = new CodexThing(SarcophagusLid.GetGUID());
		_MinuteHand = new CodexThing(MinuteHand.GetGUID());
		_HourHand = new CodexThing(HourHand.GetGUID());
		_Vukodlak = new CodexActor(Vukodlak.GetGUID());

		pos = new float[3];
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(CodexSequence.GetChronicleFlag(chronScript.FAC4_TRIPLETSTALKED) &&
			!CodexSequence.GetChronicleFlag(chronScript.UTWN_TOOLATE))
		{
			CodexSequence.SetChronicleFlag(chronScript.UTWN_TOOLATE);

			// The party members are running down the street

			christofGUID = CodexThing.GuidFromCastID("Christof");
			wilhemGUID = CodexThing.GuidFromCastID("Wilhem");
			lilyGUID = CodexThing.GuidFromCastID("Lily");
			samuelGUID = CodexThing.GuidFromCastID("Samuel");

			_Christof = new CodexActor(christofGUID);
			_Wilhem = new CodexPlayer(wilhemGUID);
			_Lily = new CodexPlayer(lilyGUID);
			_Samuel = new CodexPlayer(samuelGUID);

			// set the clock
			_MinuteHand.RotatePivot(3, 1.0f);
			_HourHand.RotatePivot(3, 1.0f);

			CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNOutside1.ncp", 30);
	
			SetTimer((float)0.1, TIMER_ID_MUSIC);
		}
	}

	public void timer(int timerID, float arg0, float arg1, float arg2, float arg3)
	{
		switch(timerID)
		{
			case TIMER_ID_MUSIC:

				CodexSound.PushMusic("MD_Conversation_2.mp3", 50);

				SetTimer((float)3.0, TIMER_ID_EXTCATH);
				break;

			case TIMER_ID_EXTCATH:
				
				// Vukodlak is asleep in his sarcophagus
				_Vukodlak.PlayMotionSetMode(MOTION_SPECIAL3, false, (float)30.0);
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNExtCathedral.ncp", 30);
				SetTimer((float)3.0, TIMER_ID_CLOCK1);
				break;

			case TIMER_ID_CLOCK1:

				// set the clock
				_MinuteHand.RotatePivot(2, -1.0f);
				_HourHand.RotatePivot(2, -1.0f);
				CodexSound snd1 = new CodexSound("countdown_clockTick.wav", 300, 600,  80, 0, 0, CodexPlayer.GetCurrentPlayer());
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNClock2.ncp", 30);
				crowdAmb = new CodexSound("crowd_timesSquare_amb.mp3", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)3.0, TIMER_ID_INTCATH);
				break;

			case TIMER_ID_INTCATH:

				// Interior cathedral, zoom in on sarcophagus
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "GetUpVuk.ncp", 300);
				crowdAmb.Stop();
				vukAmb = new CodexSound("amb_heartbeat.mp3", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)4.0, TIMER_ID_COUNT3);
				break;

			case TIMER_ID_COUNT3:
	
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNClock3.ncp", 30);
				CodexSound snd2 = new CodexSound("finalCountdown_three.wav", 300, 600, 60, 40, 0, CodexPlayer.GetCurrentPlayer());
				vukAmb.Stop();
				crowdAmb = new CodexSound("crowd_timesSquare_amb.mp3", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)1.3, TIMER_ID_COUNT2);
				break;

			case TIMER_ID_COUNT2:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNClock3.ncp", 30);
				CodexSound snd3 = new CodexSound("finalCountdown_two.wav", 300, 600,  60, 40, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)1.3, TIMER_ID_COUNT1);
				break;

			case TIMER_ID_COUNT1:

				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNClock3.ncp", 30);
				CodexSound snd4 = new CodexSound("finalCountdown_one.wav", 300, 600,  60, 40, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)1.3, TIMER_ID_CLOCK2);
				break;

			case TIMER_ID_CLOCK2:

				// set the clock
				_MinuteHand.RotatePivot(2, -1.0f);
				_HourHand.RotatePivot(2, -1.0f);
				
				// Camera shot of clock, holy crap it's midnight!! Bell tolling, cheers, noisemakers, etc.
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNClock3.ncp", 30);
				CodexSound snd11 = new CodexSound("bell_toll_01_lp.wav", 300, 600, 99, 0, 0, CodexPlayer.GetCurrentPlayer());
				CodexSound snd5 = new CodexSound("countdown_clockTick.wav", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				CodexSound snd7 = new CodexSound("finalCountdown_HNY.wav", 300, 600,  80, 0, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)5.0, TIMER_ID_OUTSIDE2);
				break;

			case TIMER_ID_OUTSIDE2:

				// Camera shot of party members stopping, looking around ... "We're too late!"
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "UTWNOutside2.ncp", 30);
				crowdAmb.Stop();
				SetTimer((float)3.0, TIMER_ID_LIDBLOW);
				break;

			case TIMER_ID_LIDBLOW:

				// Cue the sarcophagus
				CodexCamera.PlayPath(CodexPlayer.GetCurrentPlayer(), GetGUID(), "VukHand.ncp", 30);
				_SarcophagusLid.PlayMotionSetMode(MOTION_DEATHSLOW, false, (float)30.0);
				CodexSound snd8 = new CodexSound("vukodlak_airblast.wav", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				vukAmb = new CodexSound("amb_heartbeat.mp3", 300, 600,  80, 0, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)1.0, TIMER_ID_LIDSMASH);
				SetTimer((float)3.0, TIMER_ID_VUKHAND);
				break;

			case TIMER_ID_LIDSMASH:

				CodexSound snd9 = new CodexSound("vukodlak_lidSmash.wav", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				break;
			
			case TIMER_ID_VUKHAND:

				// Cue Vuk's hand
				_Vukodlak.PlayMotionSetMode(MOTION_SPECIAL2, false, (float)30.0);
				CodexSound snd10 = new CodexSound("vukodlak_awakens.wav", 300, 600,  99, 0, 0, CodexPlayer.GetCurrentPlayer());
				SetTimer((float)4.0, TIMER_ID_FADEOUT);
				break;

			case TIMER_ID_FADEOUT:

				CodexCamera.AddFade(CodexPlayer.GetCurrentPlayer(), (float)255.0, (float)0.0, (float)4.0, false);
				SetTimer((float)4.0, TIMER_ID_ENDSCENE);
				break;

			case TIMER_ID_ENDSCENE:

				// That's all, folks
				//CodexSound.PopMusic();
				CodexCamera.ClearAllEffects(CodexPlayer.GetCurrentPlayer());
				vukAmb.Stop();
				CodexCamera.Release(0);
				break;
		}
	}

	public void entered(int guid, int causeGUID, int captureID)
	{
		if(!IsPlayerGuid(causeGUID))
			return;

		if(!CodexSequence.GetChronicleFlag(chronScript.UTWN_OUTSIDECATHEDRAL))
		{
			CodexSequence.SetChronicleFlag(chronScript.UTWN_OUTSIDECATHEDRAL);
		}
	}
}

