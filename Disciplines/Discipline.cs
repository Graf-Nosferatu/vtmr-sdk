using System;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Disciplines;

public class DisciplineGroup
{
	public DisciplineGroup( DisciplineGroup? self, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Discipline.DisciplineGroups.Content.Add( this );
	}


	/// <summary> name of the discipline group </summary>
	public String Name { get; init; }

	/// <summary> icon for the discipline group in the discipline pane </summary>
	public required String Icon { get; set; }

	/// <summary> additional xp cost to pay to buy the first discipline of the group </summary>
	public Int32 XpCost { get; set; } = 0;

	/// <summary> modifier flags </summary>
	public required DisciplineGroupFlags Flags { get; set; }
}


[Flags]
public enum DisciplineGroupFlags
{
	None					= 0,

	/// <summary> this group should not be given to characters of clans that have the "access to all discipline groups at start" flag set (basically a limiter on that flag in the clan file). </summary>
	NotAllDisciplineGroup	= 0x1
}


public partial class Discipline
{
	public Discipline( Discipline? self, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Disciplines.Content.Add( this );
	}


	/// <summary> Name of the discipline </summary>
	public String Name { get; init; }

	/// <summary> Group (tab) the discipline belongs to </summary>
	public required String Group { get; set; }

	/// <summary> Icon for the discipline </summary>
	public required String Icon { get; set; }

	/// <summary> Name of the script (CASE SENSITIVE as any Java related stuff!!!) </summary>
	public required String Script { get; set; }

	/// <summary> The casting sound, or '-' or null </summary>
	public String? Sound { get; set; }

	/// <summary> Flags for how the discipline works (see list below) </summary>
	public required DisciplineFlags Flags { get; set; }

	/// <summary> Type of dicipline (for AI, see list below) </summary>
	public required DisciplineType Type { get; set; }

	/// <summary> Minimum/Maximum distance to cast (for AI) </summary>
	public required MinMax<Single> Distance { get; set; }

	/// <summary> Additional float data </summary>
	public required Single AdditionalData { get; set; }

	/// <summary> Cast animation </summary>
	public required CastType Cast { get; set; }

	/// <summary> Initial cost in experience to "buy" the discipline </summary>
	public required Int32 XpCost { get; set; }

	/// <summary> Cost in blood to cast per level </summary>
	public required Int32[] BloodCosts { get; set; }

	/// <summary> Time in ms before the discipline can be recast </summary>
	public required Int32[] RecastTimes { get; set; }

	public DisciplineCheck?[] Checks { get; set; } = [null, null, null];
}


[Flags]
public enum DisciplineFlags
{
	/// <summary> Discipline can be cast without a target </summary>
	TargetNone		= 0x01,

	/// <summary> Discipline can be cast on a location in the world </summary>
	TargetArea		= 0x02,

	/// <summary> Discipline can be cast on a held item </summary>
	TargetInventory	= 0x04,

	/// <summary> Discipline can be cast on enemy actors </summary>
	TargetEnemy		= 0x08,

	/// <summary> Discipline can be cast on friendly actors </summary>
	TargetFriendly	= 0x10,

	/// <summary> Discipline is offensive in nature, alerts party members to attack the target as well. </summary>
	Offensive		= 0x20,

	/// <summary> Discipline can target dead actors </summary>
	TargetDead		= 0x40,

	/// <summary> Discipline can be cast on party members head </summary>
	TargetHead		= 0x80,

	/// <summary> projectile, should try to avoid hitting friends in the back </summary>
	Projectile		= 0x2000,

	/// <summary> explosion, should try to avoid hitting friends (radius is in fData) </summary>
	AreaOfEffect	= 0x4000,

	/// <summary> this is a shape change that cannot be combined with other shape changes </summary>
	ShapeChange		= 0x8000,

	/// <summary> should terrify/astonish mortals (peds) witnessing its use </summary>
	Terrifying		= 0x10000,

	/// <summary> counter to OFFENSIVE, does not notify party members of this 'attack' </summary>
	NoPartyNotify	= 0x20000
}


public enum DisciplineType
{
	/// <summary> AI cannot use or use only in special conditions </summary>
	None	= 0,

	/// <summary> Healing discipline (when damaged) </summary>
	Heal	= 1,

	/// <summary> Buffing discipline (start of fight generally) </summary>
	Buff	= 2,

	/// <summary> Summoning discipline (start of fight generally) </summary>
	Summon	= 3,

	/// <summary> Hide, sneak, etc. (preferably at start of fight or when damaged too much) </summary>
	Hide	= 4,

	/// <summary> Sense discipline (used by AI on losing its target) </summary>
	Sense	= 5,

	/// <summary> Feed discipline </summary>
	Feed	= 6,

	/// <summary> Damage discipline (in fight damage) </summary>
	Damage	= 10,

	/// <summary> Mind/control (in fight mind control, fear, mesmerize, etc.) </summary>
	Mind	= 11,

	/// <summary> Makes frenzy higher </summary>
	Frenzy	= 12
}


public enum CastType
{
	General	= 0,
	Hands	= 1,
	Throw	= 2,
	Spit	= 3,
	None	= 4
}


public record class DisciplineCheck( Stat Stat, Int32 Value );