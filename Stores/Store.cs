using System;
using System.Runtime.CompilerServices;
using Sdk.Templates;
using Sdk.Treasures;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Stores;

public partial class Store
{
	public Store( Template? template, Int32 amount = 0, [CallerArgumentExpression( nameof( template ) )] String name = "" )
	{
		TemplateName = NameOf( name );
		Amount = amount;
	}

	public Store( TreasureClass? treasureClass, Int32 amount = 0, [CallerArgumentExpression( nameof( treasureClass ) )] String name = "" )
	{
		TreasureClassNumber = NumberOf( name );
		Amount = amount;
	}


	public String? TemplateName { get; }

	public Int32? TreasureClassNumber { get; }

	public Int32 Amount { get; }
}