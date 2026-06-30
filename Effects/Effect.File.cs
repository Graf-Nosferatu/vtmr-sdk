using System;
using System.IO;
using System.Linq;
using Sdk.Extensions;

namespace Sdk.Effects;

partial class Effect
{
	public const String DirectoryName = "misc";


	public static File<Effect> Effects { get; } = new( "effects.ned" );


	public static void Save()
	{
		String path = Path.Create( Sdk.ModPath, DirectoryName, Effects.FileName );
		File.WriteAllLines( path, Effects.Select( SaveEffect ) );
	}


	private static String SaveEffect( Effect effect )
	{
		Int32	rawType		= (Int32)effect.Type & 0xFFFF;
		Int32	rawSubtype	= (Int32)effect.Type >> 16;

		String	name	= effect.Name;
		String	icon	= effect.Icon ?? "none";
		String	row		= Saver.SaveEnum( effect.Row );
		String	script	= effect.Script ?? "none";
		String	type	= Saver.SaveInt32( rawType );
		String	subtype	= Saver.SaveInt32( rawSubtype );
		String	compose	= Saver.SaveEnum( effect.Compose );
		String	flags	= Saver.SaveEnum( effect.Flags );
		String	tick	= Saver.SaveInt32( effect.Tick );
		String	pc		= Saver.SaveInt32( effect.PC );
		String	dc		= Saver.SaveInt32( effect.DC );
		String	values	= String.Join( ' ', effect.Values.Select( Saver.SaveSingle ) );

		return $"{name} {icon} {row} {script} {type} {subtype} {compose} {flags} {tick} {pc} {dc} {values}";
	}
}