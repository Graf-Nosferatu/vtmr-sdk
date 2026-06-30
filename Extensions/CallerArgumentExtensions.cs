using System;
using System.Runtime.CompilerServices;
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


	public static String FileNameOf( String name )
	{
		name = NameOf( name );

		return $"{name}.npc";
	}


	public static Int32 NumberOf( String name )
	{
		var index = name.LastIndexOf( "class", StringComparison.OrdinalIgnoreCase ) + 5;

		return Int32.Parse( name[index..] );
	}


	public static String FileNameOf( Player? player, [CallerArgumentExpression( nameof( player ) )] String name = "" )
	{
		return FileNameOf( name );
	}


	public static Int32 NumberOf( TreasureClass? treasureClass, [CallerArgumentExpression( nameof( treasureClass ) )] String name = "" )
	{
		return NumberOf( name );
	}
}