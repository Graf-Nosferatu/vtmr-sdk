using System;
using System.IO;
using System.Linq;
using Sdk.Extensions;

namespace Sdk.Clans;

partial class Clan
{
	public const String DirectoryName = "misc";


	public static File<Clan> Clans { get; } = new( "game.ncd" );


	public static void Save()
	{
		String path = Path.Create( Sdk.ModPath, DirectoryName, Clans.FileName );
		File.WriteAllLines( path, Clans.Select( SaveClan ) );
	}


	private static String SaveClan( Clan clan )
	{
		String	name		= clan.Name;
		String	icon		= clan.Icon;
		String	beckon		= clan.Beckon ?? "none";
		String	shape		= clan.Shape ?? "none";
		String	flags		= Saver.SaveEnum( clan.Flags );
		String	advantages	= Saver.SaveEnum( clan.Advantages );
		String	weaknesses	= Saver.SaveEnum( clan.Weaknesses );
		String	groups		= String.Join( " ", clan.Groups.Select( g => g ?? "none" ) );
		String	stats		= String.Join( " ", Enum.GetValues<Stat>().Select( s => clan.Stats.TryGetValue( s, out Int32 value ) ? value : -1 ) );

		return $"{name} {icon} {beckon} {shape} {flags} {advantages} {weaknesses} {groups} {stats}";
	}
}