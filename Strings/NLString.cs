using System;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Strings;

public partial class NLString
{
	public NLString( File<NLString> file, Object? self, [CallerArgumentExpression( nameof( self ) )] String name = "" )
		: this( file, NameOf( name ) )
	{
	}


	public NLString( File<NLString> file, String name )
	{
		Name = name;
		file.Content.Add( this );
	}


	public String Name { get; }

	/// <summary> Official english version (Nihilistic Software)</summary>
	public required String Eng { get; init; }

	/// <summary> Неофициальная русская версия (behar) </summary>
	public required String Rus { get; init; }
}