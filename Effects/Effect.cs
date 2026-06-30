using System;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Effects;

public partial class Effect
{
	public Effect( Effect? self, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Effects.Content.Add( this );
	}


	/// <summary> Name of the effect (single word, also used as localized stringId) </summary>
	public String Name { get; init; }

	/// <summary> Icon to use for screen display, or null or the keyword 'none' </summary>
	public String? Icon { get; set; }

	/// <summary> The row the icon has to be displayed on (0 = disciplines, 1 = others) </summary>
	public IconsRow Row { get; set; } = IconsRow.Others;

	/// <summary> Script to use for the visual/sound of the effect, or null or the keyword 'none' </summary>
	public String? Script { get; set; }

	/// <summary> Type of effect (see list) and Subtype of effect inside each type (see list) </summary>
	public EffectType Type { get; set; } = EffectType.None;

	/// <summary> How this effect is composed with others of same type and subtype (see list) </summary>
	public ComposeType Compose { get; set; } = ComposeType.None;

	/// <summary> Flags altering the behavior of the effect (see list) </summary>
	public EffectFlags Flags { get; set; } = EffectFlags.None;

	/// <summary> Tick rate for over time effects in milliseconds </summary>
	public Int32 Tick { get; set; } = 0;

	/// <summary> Permanent cost (how much to add to the cost of an object with this effect as permanent) </summary>
	[Obsolete]
	public Int32 PC { get; set; } = 0;

	/// <summary> Duration cost (how much to add to the cost of an object with this effect, per second of duration, per charge) </summary>
	[Obsolete]
	public Int32 DC { get; set; } = 0;

	/// <summary> A preset value for the effect at level N </summary>
	public Single[] Values { get; set; } = [0, 0, 0, 0, 0, 0];
}


public enum IconsRow
{
	Disciplines	= 0,
	Others		= 1
}


public enum EffectType
{
	None						= 00,

	StrengthInstant				= 01 | (00 << 16),
	DexterityInstant			= 01 | (01 << 16),
	StaminaInstant				= 01 | (02 << 16),
	PerceptionInstant			= 01 | (03 << 16),
	IntelligenceInstant			= 01 | (04 << 16),
	WitsInstant					= 01 | (05 << 16),
	BloodInstant				= 01 | (06 << 16),
	HumanityInstant				= 01 | (07 << 16),
	FrenzyInstant				= 01 | (08 << 16),
	GenerationInstant			= 01 | (09 << 16),
	FaithInstant				= 01 | (10 << 16),
	CharismaInstant				= 01 | (11 << 16),
	ManipulationInstant			= 01 | (12 << 16),
	AppearanceInstant			= 01 | (13 << 16),
	FrenzyRatingInstant			= 01 | (14 << 16),
	BloodRatingInstant			= 01 | (15 << 16),
	BloodPoolInstant			= 01 | (16 << 16),
	ManaInstant					= 01 | (17 << 16),
	ManaPoolInstant				= 01 | (18 << 16),

	StrengthOvertime			= 02 | (00 << 16),
	DexterityOvertime			= 02 | (01 << 16),
	StaminaOvertime				= 02 | (02 << 16),
	PerceptionOvertime			= 02 | (03 << 16),
	IntelligenceOvertime		= 02 | (04 << 16),
	WitsOvertime				= 02 | (05 << 16),
	BloodOvertime				= 02 | (06 << 16),
	HumanityOvertime			= 02 | (07 << 16),
	FrenzyOvertime				= 02 | (08 << 16),
	GenerationOvertime			= 02 | (09 << 16),
	FaithOvertime				= 02 | (10 << 16),
	CharismaOvertime			= 02 | (11 << 16),
	ManipulationOvertime		= 02 | (12 << 16),
	AppearanceOvertime			= 02 | (13 << 16),
	FrenzyRatingOvertime		= 02 | (14 << 16),
	BloodRatingOvertime			= 02 | (15 << 16),
	BloodPoolOvertime			= 02 | (16 << 16),
	ManaOvertime				= 02 | (17 << 16),
	ManaPoolOvertime			= 02 | (18 << 16),

	Strength					= 03 | (00 << 16),
	Dexterity					= 03 | (01 << 16),
	Stamina						= 03 | (02 << 16),
	Perception					= 03 | (03 << 16),
	Intelligence				= 03 | (04 << 16),
	Wits						= 03 | (05 << 16),
	Blood						= 03 | (06 << 16),
	Humanity					= 03 | (07 << 16),
	Frenzy						= 03 | (08 << 16),
	Generation					= 03 | (09 << 16),
	Faith						= 03 | (10 << 16),
	Charisma					= 03 | (11 << 16),
	Manipulation				= 03 | (12 << 16),
	Appearance					= 03 | (13 << 16),
	FrenzyRating				= 03 | (14 << 16),
	BloodRating					= 03 | (15 << 16),
	BloodPool					= 03 | (16 << 16),
	Mana						= 03 | (17 << 16),
	ManaPool					= 03 | (18 << 16),

	SoakNormalInstant			= 04 | (00 << 16),
	SoakLethalInstant			= 04 | (01 << 16),
	SoakAggravatedInstant		= 04 | (02 << 16),
	SoakElectricInstant			= 04 | (03 << 16),
	SoakFireInstant				= 04 | (04 << 16),
	SoakSunInstant				= 04 | (05 << 16),
	SoakFaithInstant			= 04 | (06 << 16),
	SoakColdInstant				= 04 | (07 << 16),
	SoakPoisonInstant			= 04 | (08 << 16),
	SoakDiseaseInstant			= 04 | (09 << 16),

	SoakNormalOvertime			= 05 | (00 << 16),
	SoakLethalOvertime			= 05 | (01 << 16),
	SoakAggravatedOvertime		= 05 | (02 << 16),
	SoakElectricOvertime		= 05 | (03 << 16),
	SoakFireOvertime			= 05 | (04 << 16),
	SoakSunOvertime				= 05 | (05 << 16),
	SoakFaithOvertime			= 05 | (06 << 16),
	SoakColdOvertime			= 05 | (07 << 16),
	SoakPoisonOvertime			= 05 | (08 << 16),
	SoakDiseaseOvertime			= 05 | (09 << 16),

	SoakNormal					= 06 | (00 << 16),
	SoakLethal					= 06 | (01 << 16),
	SoakAggravated				= 06 | (02 << 16),
	SoakElectric				= 06 | (03 << 16),
	SoakFire					= 06 | (04 << 16),
	SoakSun						= 06 | (05 << 16),
	SoakFaith					= 06 | (06 << 16),
	SoakCold					= 06 | (07 << 16),
	SoakPoison					= 06 | (08 << 16),
	SoakDisease					= 06 | (09 << 16),

	DamageNormalInstant			= 07 | (00 << 16),
	DamageLethalInstant			= 07 | (01 << 16),
	DamageAggravatedInstant		= 07 | (02 << 16),
	DamageElectricInstant		= 07 | (03 << 16),
	DamageFireInstant			= 07 | (04 << 16),
	DamageSunInstant			= 07 | (05 << 16),
	DamageFaithInstant			= 07 | (06 << 16),
	DamageColdInstant			= 07 | (07 << 16),
	DamagePoisonInstant			= 07 | (08 << 16),
	DamageDiseaseInstant		= 07 | (09 << 16),

	DamageNormalOvertime		= 08 | (00 << 16),
	DamageLethalOvertime		= 08 | (01 << 16),
	DamageAggravatedOvertime	= 08 | (02 << 16),
	DamageElectricOvertime		= 08 | (03 << 16),
	DamageFireOvertime			= 08 | (04 << 16),
	DamageSunOvertime			= 08 | (05 << 16),
	DamageFaithOvertime			= 08 | (06 << 16),
	DamageColdOvertime			= 08 | (07 << 16),
	DamagePoisonOvertime		= 08 | (08 << 16),
	DamageDiseaseOvertime		= 08 | (09 << 16),

	/// <summary> heal, instant </summary>
	HealInstant					= 09,

	/// <summary> heal, over time </summary>
	HealOvertime				= 10,

	/// <summary> additional offense bonus, added to the offense formula [hit if random(Adex + weaponAccuracy) + Axoffense > random(Ddex) + Dxdefense] </summary>
	XOffense					= 11,

	/// <summary> additional defense bonus added to the defense formula </summary>
	XDefense					= 12,

	/// <summary> additional damage bonus (same type as the damage being computed) added to the damage formula [dam is random(Astr + weaponDamage) + Apotence + Axdamage - random(Dsoak [+ Dstamina]) [-Dfortitude] - Dxsoak] </summary>
	XDamage						= 13,

	/// <summary> additional soaking bonus (all damage types) added to the damage formula </summary>
	XSoak						= 14,

	/// <summary> potence (used in combat system, and certain checks) </summary>
	Potence						= 15,

	/// <summary> fortitude (used in combat system) </summary>
	Fortitude					= 16,

	/// <summary> multiplier for movement speed </summary>
	MoveSpeed					= 17,

	/// <summary> multiplier for attack speed </summary>
	AttackSpeed					= 18,

	/// <summary> how well you are "hidden" </summary>
	Hide						= 19,

	/// <summary> how well you sense "hidden" things </summary>
	Sense						= 20,

	/// <summary> a protection against staking </summary>
	StakeProtection				= 21,

	/// <summary> wanted by the law! </summary>
	Wanted						= 22,

	/// <summary> mesmerized </summary>
	Mesmerized					= 23,

	/// <summary> frenzy (this is a system effect type, this is *NOT* meant to start frenzy) </summary>
	Frenzied					= 24,

	/// <summary> afraid </summary>
	Afraid						= 25,

	/// <summary> dazed </summary>
	Dazed						= 26,

	/// <summary> becomes pet of someone else </summary>
	Pet							= 27,

	/// <summary> possessing someone else </summary>
	Possessing					= 28,

	/// <summary> changing the model's alpha </summary>
	ModelAlpha					= 29,

	/// <summary> changing the model's scale </summary>
	ModelScale					= 30,

	/// <summary> alters the cost in blood of using a discipline </summary>
	BloodCost					= 31,

	/// <summary> under the spell of 'majesty' (i.e. stands in place looking at the majestic thing) </summary>
	Majesty						= 32,

	/// <summary> immobilized </summary>
	Immobilize					= 33,

	/// <summary> a protection against feeding </summary>
	FeedProtection				= 34,

	/// <summary> friendly to another team than his own (or more exactly, on top of his own) </summary>
	TeamFriendly				= 35
}


public enum ComposeType
{
	None	= 0,
	First	= 1,
	Last	= 2,
	Min		= 3,
	Max		= 4,
	Add		= 5,
	Mul		= 6,
	Avg		= 7
}


[Flags]
public enum EffectFlags
{
	None			= 0,

	/// <summary> no blink icon </summary>
	NoBlink			= 0x1,

	/// <summary> keep (and display in rollover as) float value </summary>
	FloatValue		= 0x2,

	/// <summary> effect negator (actually KILLS any effect of the same type/subtype with a lesser of equal value -- no instance is created, so this cannot have a script) </summary>
	Negator			= 0x4,

	/// <summary> effect is NOT used in the rollover description (assuming a SPECIALDESCRIPTION has been set in the template) </summary>
	NoDescription	= 0x8,

	/// <summary> effect will be removed when its creator dies </summary>
	RemoveWhenDie	= 0x10,

	/// <summary> display as a percentage in rollover (only valid when 0x2 is set too) </summary>
	PercentValue	= 0x20,

	/// <summary> this effect is synced only for the "owner" of the effect </summary>
	OwnerSynced		= 0x40,

	/// <summary> this effect is not synced to any clients </summary>
	NotSynced		= 0x80,

	/// <summary> this effect is not saved </summary>
	NotSaved		= 0x100,

	/// <summary> this effect is cancellable </summary>
	Cancellable		= 0x200
}