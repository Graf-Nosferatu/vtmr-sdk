using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Sdk.Treasures;

partial class TreasureClass
{
	public const String
		DirectoryName = "misc",
		FileName = "game.ntt";

	private const String
		ALTER = "ALTER:",
		CATEG = "CATEG:",
		CLASS = "CLASS:",
		CITEM = "CITEM:";


	public static List<Alteration> Alterations { get; } = [];

	public static List<Category> Categories { get; } = [];

	public static List<TreasureClass> Classes { get; } = [];


	public static void Save()
	{
		String path = Path.Create( Sdk.ModPath, DirectoryName, FileName );
		IEnumerable<String> enumerateTreasures =
		[
			..Alterations.Select( SaveAlteration ),
			..Categories.Select( SaveCategory ),
			..Classes.SelectMany( SaveClass )
		];

		File.WriteAllLines( path, enumerateTreasures );
	}


	private static String SaveAlteration( Alteration alter )
	{
		String	name			= alter.Name;
		String	expiration		= "P";
		String	group			= "-";
		String	effectLevel		= $"0 0";
		String	effectDuration	= $"0 0";
		String	amount			= $"{alter.Amount.Min} {alter.Amount.Max}";

		return $"{ALTER} {name} {expiration} {group} {effectLevel} {effectDuration} {amount}";
	}


	private static String SaveCategory( Category categ )
	{
		String	name		= categ.Name;
		String	templates	= String.Join( " ", categ.Templates );

		return $"{CATEG} {name} {templates}";
	}


	private static IEnumerable<String> SaveClass( TreasureClass @class )
	{
		String line = $"{CLASS} {@class.Number}";

		return [line, ..@class.Items.Select( SaveItem )];
	}


	private static String SaveItem( TreasureItem item )
	{
		String	category	= item.Category.Name;
		String	percent		= item.Percent.ToString();
		String	alter1		= item.Alter?.Name ?? "-";
		String	flags1		= "0";
		String	alter2		= "-";
		String	flags2		= "0";
		String	alter3		= "-";
		String	flags3		= "0";

		return $"{CITEM} {category} {percent} {alter1} {flags1} {alter2} {flags2} {alter3} {flags3}";
	}
}