using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Clans;

public partial class Clan
{
	public Clan( Clan? self, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Clans.Content.Add( this );
	}


	/// <summary>
	/// Name of the clan.
	/// Do not in any way alter the spelling of the predefined clan names!
	/// </summary>
	public String Name { get; init; }

	/// <summary> The name of the icon to display in the character sheet for this clan (without TGA extension) </summary>
	public required String Icon { get; set; }

	/// <summary> The name of the template summoned by the Beckoning spell. 'none' or null = no beckoning. </summary>
	public String? Beckon { get; set; }

	/// <summary> The name of the template to use for shape-shifting discipline. 'none' or null = no shapeshifting possible </summary>
	public String? Shape { get; set; }

	/// <summary> Basic flags to control clan behavior </summary>
	public ClanFlags Flags { get; set; } = ClanFlags.None;

	/// <summary> Advantages flags (see below) </summary>
	public AdvantageFlags Advantages { get; set; } = AdvantageFlags.None;

	/// <summary> Weaknesses flags (see below) </summary>
	public WeaknessFlags Weaknesses { get; set; } = WeaknessFlags.None;

	/// <summary> Names of the three discipline groups the clan begins with knowledge of. Group names are found in the NDG file. </summary>
	public required String?[] Groups { get; set; }

	/// <summary> There are 19 maximum stats which are listed in the order shown in the file. –1 means there is no stat limit below 100 </summary>
	public Dictionary<Stat, Int32> Stats { get; private set; } = [];
}


[Flags]
public enum ClanFlags
{
	None			= 0,

	/// <summary> not selectable in MP </summary>
	NotMultiplayer	= 0x1,
}


[Flags]
public enum AdvantageFlags
{
	None				= 0,

	/// <summary> access to all discipline groups at start </summary>
	AllDisciplineGroups	= 0x1
}


[Flags]
public enum WeaknessFlags
{
	None						= 0,

	/// <summary> chance of decrementing appearance on frenzy </summary>
	DecrementAppearanceOnFrenzy	= 0x1,

	/// <summary> increased discipline advancement cost </summary>
	IncreaseDisciplineCost		= 0x2,

	/// <summary> cannot increase socials </summary>
	CanNotIncreaseAppearance	= 0x4
}