using System;
using System.Linq;
using System.Reflection;
using Sdk.Clans;
using Sdk.Disciplines;
using Sdk.Effects;
using Sdk.Players;
using Sdk.Stores;
using Sdk.Strings;
using Sdk.Templates;
using Sdk.Treasures;

namespace Sdk;

public static class Sdk
{
	static Sdk()
	{
		var attributes = typeof(Sdk).Assembly.GetCustomAttributes<AssemblyMetadataAttribute>();

		ModPath = attributes.First( a => a.Key == nameof( ModPath ) ).Value!;
		Localization = attributes.First( a => a.Key == nameof( Localization ) ).Value!;
	}


	public static String ModPath { get; private set; }

	public static String Localization { get; private set; }


	static void Main()
	{
		Clan.Save();
		Discipline.Save();
		Effect.Save();
		Player.Save();
		Store.Save();
		NLString.Save();
		Template.Save();
		TreasureClass.Save();
	}
}