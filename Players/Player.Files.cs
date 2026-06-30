using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.CompilerServices;
using Sdk.Extensions;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Players;

partial class Player
{
	public const String DirectoryName = "misc";


	public static void Save()
	{
		SavePlayer( christof );
		SavePlayer( christofmodern );
		SavePlayer( christophda );
		SavePlayer( erik );
		SavePlayer( lily );
		SavePlayer( mp_christof );
		SavePlayer( mp_erik );
		SavePlayer( mp_lily );
		SavePlayer( mp_pink );
		SavePlayer( mp_samuel );
		SavePlayer( mp_serena );
		SavePlayer( mp_tremere );
		SavePlayer( mp_wilhem );
		SavePlayer( pink );
		SavePlayer( samuel );
		SavePlayer( serena );
		SavePlayer( wilhemda );
		SavePlayer( wilhemmodern );
	}


	private static void SavePlayer( Player? player, [CallerArgumentExpression( nameof( player ) )] String name = "" )
	{
		IEnumerable<String> EnumeratePlayer()
		{
			if( player.PlayerName != null ) {
				yield return $"name {player.PlayerName}";
			}
			if( player.ClanName != null ) {
				yield return $"clan {player.ClanName}";
			}
			if( player.Cash != null ) {
				yield return $"cash {player.Cash}";
			}
			if( player.TotalExperience != null ) {
				yield return $"totalexperience {player.TotalExperience}";
			}
			if( player.Experience != null ) {
				yield return $"experience {player.Experience}";
			}
			if( player.MaxHealth != null ) {
				yield return $"maxhealth {player.MaxHealth}";
			}
			if( player.Health != null ) {
				yield return $"health {player.Health}";
			}
			foreach( var stat in player.Stats ) {
				yield return $"{stat.Key.ToString().ToLower()} {Saver.SaveSingle( stat.Value.Min )} {Saver.SaveSingle( stat.Value.Max )} {Saver.SaveSingle( stat.Value.Current )}";
			}
			foreach( var discipline in player.Disciplines ) {
				yield return $"discipline {discipline.DisciplineName} {discipline.Level} {discipline.QuickSlot}";
			}
			foreach( var item in player.Items ) {
				yield return $"item {item.TemplateName} {item.Placement.ToString().ToLower()}";
			}
		}

		String path = Path.Create( Sdk.ModPath, DirectoryName, FileNameOf( name ) );
		File.WriteAllLines( path, EnumeratePlayer() );
	}
}