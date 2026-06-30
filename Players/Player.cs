using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Sdk.Disciplines;
using Sdk.Templates;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Players;

public partial class Player
{
	[Obsolete]
	public String? PlayerName { get; set; }

	public String? ClanName { get; set; }

	public Int32? Cash { get; set; }

	public Int32? TotalExperience { get; set; }

	public Int32? Experience { get; set; }

	public Int32? MaxHealth { get; set; }

	public Int32? Health { get; set; }

	public Dictionary<Stat, MinMaxCurrent<Single>> Stats { get; init; } = [];

	public PlayerDiscipline[] Disciplines { get; init; } = [];

	public PlayerItem[] Items { get; init; } = [];
}


public class PlayerDiscipline
{
	public PlayerDiscipline( Discipline? discipline, Int32 level, Int32 quickSlot = -1, [CallerArgumentExpression( nameof( discipline ) )] String name = "" )
	{
		DisciplineName = NameOf( name );
		Level = level;
		QuickSlot = quickSlot;
	}


	public String DisciplineName { get; }

	public Int32 Level { get; }

	public Int32 QuickSlot { get; }
}


public record class PlayerItem
{
	public PlayerItem( Template? template, Placement placement = Placement.Main, [CallerArgumentExpression( nameof( template ) )] String name = "" )
	{
		TemplateName = NameOf( name );
		Placement = placement;
	}


	public String TemplateName { get; }

	public Placement Placement { get; }
}


public enum Placement
{
	Main,
	Belt,
	Equip
}