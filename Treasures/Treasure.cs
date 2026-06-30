using System;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Treasures;

public class Alteration
{
	public Alteration( Alteration? self, Int32 minAmount, Int32 maxAmount, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Amount = new( minAmount, maxAmount );
		TreasureClass.Alterations.Add( this );
	}


	public String Name { get; }

	public MinMax<Int32> Amount { get; }
}


public class Category
{
	public Category( Category? self, String[] templates, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Templates = templates;
		TreasureClass.Categories.Add( this );
	}


	public String Name { get; }

	public String[] Templates { get; }
}


public partial class TreasureClass
{
	public TreasureClass( TreasureClass? self, TreasureItem[] items, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Number = NumberOf( name );
		Items = items;
		Classes.Add( this );
	}


	public Int32 Number { get; }

	public TreasureItem[] Items { get; }
}


public class TreasureItem
{
	public TreasureItem( Category category, Int32 percent )
	{
		Category = category;
		Percent = percent;
	}


	public TreasureItem( Category category, Int32 percent, Alteration alter )
	{
		Category = category;
		Percent = percent;
		Alter = alter;
	}


	public Category Category { get; }

	public Int32 Percent { get; }

	public Alteration? Alter { get; }
}