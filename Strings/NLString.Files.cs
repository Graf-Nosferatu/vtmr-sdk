using System;
using System.IO;
using System.Linq;
using System.Text;

namespace Sdk.Strings;

partial class NLString
{
	private const String DirectoryName = "strings";


	private static File<NLString> System { get; } = new( "system.nls" );

	private static File<NLString> Game { get; } = new( "game.nls" );

	private static File<NLString> Effects { get; } = new( "effects.nls" );


	private static readonly File<NLString>[] Files = [System, Game, Effects];


	public static void Save()
	{
		Encoding.RegisterProvider( CodePagesEncodingProvider.Instance );
		Encoding encoding = Sdk.Localization switch
		{
			"Eng"	=> Encoding.ASCII,
			"Rus"	=> Encoding.GetEncoding( "windows-1251" ),
			_		=> Encoding.ASCII
		};

		foreach( var file in Files )
		{
			String path = Path.Create( Sdk.ModPath, DirectoryName, file.FileName );
			File.WriteAllLines( path, file.Select( SaveNLString ), encoding );
		}
	}


	private static String SaveNLString( NLString nlString )
	{
		String	name	= nlString.Name;
		String	value	= Sdk.Localization switch
		{
			"Eng"	=> nlString.Eng,
			"Rus"	=> nlString.Rus,
			_		=> nlString.Name
		};

		return $"{name} {value ?? name}";
	}
}