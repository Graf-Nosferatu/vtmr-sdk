/**
 * Weather script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class Weather extends Codex
{
	public CodexThing	weatherThing;
	public CodexThing	weatherEffect;
	public int			weatherEffectGUID = 0;
	public String		weatherEffectName;

	public Weather()
	{
		weatherThing = new CodexThing(GetClassThing());
	}

	public void weatherchanged()
	{
		ChangeWeather();
	}
	
	public void beginscene(int clientGuid, int captureID)
	{
		ChangeWeather();
	}

	public void ChangeWeather()
	{
		if(weatherEffectGUID != 0) // weather effect exists, remove it
		{
			weatherEffect = new CodexThing(weatherEffectGUID);
			weatherEffect.Remove();
			weatherEffectGUID = 0;
		}

		weatherEffectName = GetWeatherEffect();

		if(!weatherEffectName.equalsIgnoreCase(""))
		{
			weatherEffectGUID = weatherThing.SpawnThing(weatherEffectName);
		}
	}
}
