using System;
using System.Drawing;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text.RegularExpressions;
using Sdk.Explosions;
using Sdk.Players;
using Sdk.Treasures;

namespace Sdk.Extensions;

public static class CallerArgumentExtensions
{
	public static String NameOf( String name )
	{
		Int32 lastDotIndex = name.LastIndexOf( '.' );
		ReadOnlySpan<Char> lastName = lastDotIndex >= 0
			? name.AsSpan( lastDotIndex + 1 )
			: name.AsSpan();

		return lastName[0] == '@'
			? lastName[1..].ToString()
			: lastName.ToString();
	}


	public static Int32 NumberOf( String name )
	{
		var index = name.LastIndexOf( "class", StringComparison.OrdinalIgnoreCase ) + 5;

		return Int32.Parse( name[index..] );
	}


	public static String FileNameOf( Player? player, [CallerArgumentExpression( nameof( player ) )] String name = "" )
	{
		name = NameOf( name );

		return $"{name}.npc";
	}


	public static String FileNameOf( Explosion? explosion, [CallerArgumentExpression( nameof( explosion ) )] String name = "" )
	{
		name = NameOf( name );

		return $"{name}.nex";
	}


	public static Int32 NumberOf( TreasureClass? treasureClass, [CallerArgumentExpression( nameof( treasureClass ) )] String name = "" )
	{
		return NumberOf( name );
	}


	public static Color ColorOf( String color )
	{
		Int32[] rgb = [..Regex.Matches( color, @"\d+" ).Select( m => Int32.Parse( m.Value ) )];

		return Color.FromArgb( rgb[0], rgb[1], rgb[2] );
	}
}