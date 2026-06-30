using System;
using System.Drawing;
using System.Numerics;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Templates;

public partial class Template
{
	public Template( File<Template> file, Template? self, String? comment = null, [CallerArgumentExpression( nameof( self ) )] String name = "" )
	{
		Name = NameOf( name );
		Comment = comment;
		file.Content.Add( this );
	}


	public String Name { get; init; }

	public String? Comment { get; set; }

	/// <summary> STRING <br/> The parent of a template. <br/> 0000 </summary>
	[Tag( Tag.PARENT, 0000 )]
	public String? PARENT { get; set; }

	/// <summary> STRING <br/> The stringID of this thing description. <br/> 0005 </summary>
	[Tag( Tag.DESCRIPTIONID, 0005 )]
	public String? DESCRIPTIONID { get; set; }

	/// <summary> ENUM_TYPE <br/> The type of this template. <br/> 0010 </summary>
	[Tag( Tag.TYPE, 0010 )]
	public ENUM_TYPE? TYPE { get; set; }

	/// <summary> UINT <br/> The timer of template. <br/> 0090 </summary>
	[Tag( Tag.TIMER, 0090 )]
	public Int32? TIMER { get; set; }

	/// <summary> TEMPLATE <br/> The template to replace the destroyed template with. <br/> 0093 </summary>
	[Tag( Tag.DESTROY_TEMPLATE, 0093 )]
	public String? DESTROY_TEMPLATE { get; set; }

	/// <summary> FLAG_THINGFLAGS <br/> The thing flags of a template. <br/> 0100 </summary>
	[Tag( Tag.THINGFLAGS, 0100 )]
	public FLAG_THINGFLAGS? THINGFLAGS { get; set; }

	/// <summary> ENUM_COLLIDETYPE <br/> The collidetype of an object. <br/> 0110 </summary>
	[Tag( Tag.COLLIDETYPE, 0110 )]
	public ENUM_COLLIDETYPE? COLLIDETYPE { get; set; }

	/// <summary> UFLOAT <br/> The radius of an object. <br/> 0120 </summary>
	[Tag( Tag.RADIUS, 0120 )]
	public Single? RADIUS { get; set; }

	/// <summary> UFLOAT <br/> The height of an object. <br/> 0121 </summary>
	[Tag( Tag.HEIGHT, 0121 )]
	public Single? HEIGHT { get; set; }

	/// <summary> ENUM_MOVETYPE <br/> The movetype of an object. <br/> 0140 </summary>
	[Tag( Tag.MOVETYPE, 0140 )]
	public ENUM_MOVETYPE? MOVETYPE { get; set; }

	/// <summary> UFLOAT <br/> The movespeed of an object. <br/> 0150 </summary>
	[Tag( Tag.MOVESPEED, 0150 )]
	public Single? MOVESPEED { get; set; }

	/// <summary> FLAG_THINGRENDERFLAGS <br/> The thing render flags of a template. <br/> 1000 </summary>
	[Tag( Tag.THINGRENDERFLAGS, 1000 )]
	public FLAG_THINGRENDERFLAGS? THINGRENDERFLAGS { get; set; }

	/// <summary> FILENAME <br/> The model of a template. <br/> 1010 </summary>
	[Tag( Tag.MODEL, 1010 )]
	public String? MODEL { get; set; }

	/// <summary> FLAG_MODELFLAGS <br/> The model flags of a template. <br/> 1020 </summary>
	[Tag( Tag.MODELFLAGS, 1020 )]
	public FLAG_MODELFLAGS? MODELFLAGS { get; set; }

	/// <summary> UFLOAT <br/> A scale for this model. <br/> 1021 </summary>
	[Tag( Tag.MODELSCALE, 1021 )]
	public Single? MODELSCALE { get; set; }

	/// <summary> FILENAME <br/> The foley of a template. <br/> 1030 </summary>
	[Tag( Tag.FOLEY, 1030 )]
	public String? FOLEY { get; set; }

	/// <summary> ENUM_SOUNDTYPE <br/> The soundtype of an object. <br/> 1035 </summary>
	[Tag( Tag.SOUNDTYPE, 1035 )]
	public ENUM_SOUNDTYPE? SOUNDTYPE { get; set; }

	/// <summary> FILENAME <br/> The motion of a template. <br/> 1040 </summary>
	[Tag( Tag.MOTIONSET, 1040 )]
	public String? MOTIONSET { get; set; }

	/// <summary> FILENAMENOEXT <br/> The class script of a template. <br/> 2000 </summary>
	[Tag( Tag.SCRIPT, 2000 )]
	public String? SCRIPT { get; set; }

	/// <summary> UINT <br/> The skeleton ID of the template. <br/> 2010 </summary>
	[Tag( Tag.SKELETONID, 2010 )]
	public Int32? SKELETONID { get; set; }

	/// <summary> STRING <br/> The particle reference of a template. <br/> 2020 </summary>
	[Tag( Tag.PARTICLENAME, 2020 )]
	public String? PARTICLENAME { get; set; }

	/// <summary> UFLOAT <br/> The intensity of the dynamic light. <br/> 2100 </summary>
	[Tag( Tag.DYNA_INTENSITY, 2100 )]
	public Single? DYNA_INTENSITY { get; set; }

	/// <summary> FILENAMENOEXT <br/> The material of the dynamic light. <br/> 2110 </summary>
	[Tag( Tag.DYNA_MATERIAL, 2110 )]
	public String? DYNA_MATERIAL { get; set; }

	/// <summary> RGB <br/> The color of the dynamic light blob. <br/> 2120 </summary>
	[Tag( Tag.DYNA_COLOR, 2120 )]
	public Color? DYNA_COLOR { get; set; }

	/// <summary> SINT <br/> The bone of the dynamic light (-1 for no bone). <br/> 2130 </summary>
	[Tag( Tag.DYNA_BONE, 2130 )]
	public Int32? DYNA_BONE { get; set; }

	/// <summary> VECTOR <br/> The offset of the dynamic light blob. <br/> 2135 </summary>
	[Tag( Tag.DYNA_OFFSET, 2135 )]
	public Vector3? DYNA_OFFSET { get; set; }

	/// <summary> FLAG_DYNAFLAGS <br/> The flags of the dynamic light. <br/> 2140 </summary>
	[Tag( Tag.DYNA_FLAGS, 2140 )]
	public FLAG_DYNAFLAGS? DYNA_FLAGS { get; set; }

	/// <summary> ENUM_DYNA_STYLE <br/> The style of this dynamic light. <br/> 2150 </summary>
	[Tag( Tag.DYNA_STYLE, 2150 )]
	public ENUM_DYNA_STYLE? DYNA_STYLE { get; set; }

	/// <summary> UFLOAT <br/> The radius of the dynamic light. <br/> 2160 </summary>
	[Tag( Tag.DYNA_RADIUS, 2160 )]
	public Single? DYNA_RADIUS { get; set; }

	/// <summary> TEMPLATE <br/> The template to create when this template explodes. <br/> 2200 </summary>
	[Tag( Tag.EXPLOSION, 2200 )]
	public String? EXPLOSION { get; set; }

	/// <summary> UFLOAT <br/> The radius at which an object will trigger a touched message. <br/> 2350 </summary>
	[Tag( Tag.PROXRADIUS, 2350 )]
	public Single? PROXRADIUS { get; set; }

	/// <summary> STRING <br/> The wall mark for a projectile or explosion. <br/> 2360 </summary>
	[Tag( Tag.WALLMARK, 2360 )]
	public String? WALLMARK { get; set; }

	/// <summary> ENUM_ICONSIZE <br/> The size of the icon. <br/> 3000 </summary>
	[Tag( Tag.ICONSIZE, 3000 )]
	public ENUM_ICONSIZE? ICONSIZE { get; set; }

	/// <summary> ENUM_ICONTYPE <br/> The type of the icon (OBSOLETE) <br/> 3010 </summary>
	[Tag( Tag.ICONTYPE, 3010 )]
	public ENUM_ICONTYPE? ICONTYPE { get; set; }

	/// <summary> ENUM_ITEM_TYPE <br/> The item type. <br/> 3020 </summary>
	[Tag( Tag.ITEM_TYPE, 3020 )]
	public ENUM_ITEM_TYPE? ITEM_TYPE { get; set; }

	/// <summary> FILENAMENOEXT <br/> The icon of a pickable item. <br/> 3030 </summary>
	[Tag( Tag.ICON, 3030 )]
	public String? ICON { get; set; }

	/// <summary> FLAG_ITEM_FLAGS <br/> The item flags. <br/> 3040 </summary>
	[Tag( Tag.ITEM_FLAGS, 3040 )]
	public FLAG_ITEM_FLAGS? ITEM_FLAGS { get; set; }

	/// <summary> STRING <br/> The identified description of the item. <br/> 3042 </summary>
	[Tag( Tag.ITEM_IDENTIFIEDDESC, 3042 )]
	public String? ITEM_IDENTIFIEDDESC { get; set; }

	/// <summary> STRING <br/> The special description of the item. <br/> 3044 </summary>
	[Tag( Tag.ITEM_SPECIALDESC, 3044 )]
	public String? ITEM_SPECIALDESC { get; set; }

	/// <summary> UINT <br/> The amount of the item. <br/> 3050 </summary>
	[Tag( Tag.ITEM_AMOUNT, 3050 )]
	public Int32? ITEM_AMOUNT { get; set; }

	/// <summary> UINT <br/> The id of the item (used for ammo type). <br/> 3060 </summary>
	[Tag( Tag.ITEM_ID, 3060 )]
	public Int32? ITEM_ID { get; set; }

	/// <summary> ENUM_ITEM_TAG <br/> The placement tag of the item (used for utility items). <br/> 3065 </summary>
	[Tag( Tag.ITEM_TAG, 3065 )]
	public ENUM_ITEM_TAG? ITEM_TAG { get; set; }

	/// <summary> STRING <br/> The discipline of the scroll. <br/> 3066 </summary>
	[Tag( Tag.ITEM_SCROLLDISC, 3066 )]
	public String? ITEM_SCROLLDISC { get; set; }

	/// <summary> UINT <br/> The level of the scroll. <br/> 3067 </summary>
	[Tag( Tag.ITEM_SCROLLLEVEL, 3067 )]
	public Int32? ITEM_SCROLLLEVEL { get; set; }

	/// <summary> UINT <br/> The base cost of the item <br/> 3070 </summary>
	[Tag( Tag.ITEM_BASECOST, 3070 )]
	public Int32? ITEM_BASECOST { get; set; }

	/// <summary> FLAG_ITEM_SELLFLAGS <br/> The item sell flags. <br/> 3080 </summary>
	[Tag( Tag.ITEM_SELLFLAGS, 3080 )]
	public FLAG_ITEM_SELLFLAGS? ITEM_SELLFLAGS { get; set; }

	/// <summary> UINT <br/> The minimum perception to automatically identify the item. <br/> 3090 </summary>
	[Tag( Tag.ITEM_MINPERCEPTION, 3090 )]
	public Int32? ITEM_MINPERCEPTION { get; set; }

	/// <summary> STRING <br/> The text associated with the item (books, etc). <br/> 3095 </summary>
	[Tag( Tag.ITEM_TEXT, 3095 )]
	public String? ITEM_TEXT { get; set; }

	/// <summary> STRING <br/> The armor model. <br/> 3100 </summary>
	[Tag( Tag.ARMOR_MODEL, 3100 )]
	public String? ARMOR_MODEL { get; set; }

	/// <summary> ENUM_ARMOR_TYPE <br/> The armor type. <br/> 3110 </summary>
	[Tag( Tag.ARMOR_TYPE, 3110 )]
	public ENUM_ARMOR_TYPE? ARMOR_TYPE { get; set; }

	/// <summary> ENUM_WEAPON_TYPE <br/> The type of a weapon. <br/> 3300 </summary>
	[Tag( Tag.WEAPON_TYPE, 3300 )]
	public ENUM_WEAPON_TYPE? WEAPON_TYPE { get; set; }

	/// <summary> FLAG_WEAPON_FLAGS <br/> The weapon flags. <br/> 3310 </summary>
	[Tag( Tag.WEAPON_FLAGS, 3310 )]
	public FLAG_WEAPON_FLAGS? WEAPON_FLAGS { get; set; }

	/// <summary> STRING <br/> The model of a weapon when carried by a player. <br/> 3315 </summary>
	[Tag( Tag.WEAPON_MODEL, 3315 )]
	public String? WEAPON_MODEL { get; set; }

	/// <summary> VECTOR <br/> The firing position of the weapon. <br/> 3320 </summary>
	[Tag( Tag.WEAPON_FIREPOS, 3320 )]
	public Vector3? WEAPON_FIREPOS { get; set; }

	/// <summary> VECTOR <br/> The blade start pos of the weapon. <br/> 3322 </summary>
	[Tag( Tag.WEAPON_BLADESTART, 3322 )]
	public Vector3? WEAPON_BLADESTART { get; set; }

	/// <summary> VECTOR <br/> The blade end pos of the weapon. <br/> 3324 </summary>
	[Tag( Tag.WEAPON_BLADEEND, 3324 )]
	public Vector3? WEAPON_BLADEEND { get; set; }

	/// <summary> STRING <br/> The blade trail of the weapon. <br/> 3326 </summary>
	[Tag( Tag.WEAPON_TRAIL, 3326 )]
	public String? WEAPON_TRAIL { get; set; }

	/// <summary> ENUM_WEAPON_DEFENSETYPE <br/> The defense type of a weapon. <br/> 3350 </summary>
	[Tag( Tag.WEAPON_DEFENSETYPE, 3350 )]
	public ENUM_WEAPON_DEFENSETYPE? WEAPON_DEFENSETYPE { get; set; }

	/// <summary> UINT <br/> The weapon throw type. <br/> 3360 </summary>
	[Tag( Tag.WEAPON_THROWTYPE, 3360 )]
	public Int32? WEAPON_THROWTYPE { get; set; }

	/// <summary> UFLOAT <br/> The weapon throw distance. <br/> 3362 </summary>
	[Tag( Tag.WEAPON_THROWDIST, 3362 )]
	public Single? WEAPON_THROWDIST { get; set; }

	/// <summary> UFLOAT <br/> The weapon throw chance. <br/> 3364 </summary>
	[Tag( Tag.WEAPON_THROWCHANCE, 3364 )]
	public Single? WEAPON_THROWCHANCE { get; set; }

	/// <summary> ENUM_WEAPON_MELEETYPE <br/> The melee type of a weapon. <br/> 3400 </summary>
	[Tag( Tag.WEAPON_MELEETYPE, 3400 )]
	public ENUM_WEAPON_MELEETYPE? WEAPON_MELEETYPE { get; set; }

	/// <summary> FLAG_WEAPON_FLAGS <br/> The weapon flags1. <br/> 3402 </summary>
	[Tag( Tag.WEAPON_FLAGS1, 3402 )]
	public FLAG_WEAPON_FLAGS? WEAPON_FLAGS1 { get; set; }

	/// <summary> UINT <br/> The first damage of the weapon. <br/> 3405 </summary>
	[Tag( Tag.WEAPON_DAMAGE1, 3405 )]
	public Int32? WEAPON_DAMAGE1 { get; set; }

	/// <summary> ENUM_WEAPON_DAMAGETYPE <br/> The first damage type of the weapon. <br/> 3410 </summary>
	[Tag( Tag.WEAPON_DAMAGETYPE1, 3410 )]
	public ENUM_WEAPON_DAMAGETYPE? WEAPON_DAMAGETYPE1 { get; set; }

	/// <summary> UFLOAT <br/> The first strike time of the weapon. <br/> 3415 </summary>
	[Tag( Tag.WEAPON_STRIKETIME1, 3415 )]
	public Single? WEAPON_STRIKETIME1 { get; set; }

	/// <summary> UFLOAT <br/> The reload time of the weapon. <br/> 3420 </summary>
	[Tag( Tag.WEAPON_RELOADTIME, 3420 )]
	public Single? WEAPON_RELOADTIME { get; set; }

	/// <summary> UFLOAT <br/> The min distance of the weapon. <br/> 3425 </summary>
	[Tag( Tag.WEAPON_MINDISTANCE, 3425 )]
	public Single? WEAPON_MINDISTANCE { get; set; }

	/// <summary> UFLOAT <br/> The max distance of the weapon. <br/> 3430 </summary>
	[Tag( Tag.WEAPON_MAXDISTANCE, 3430 )]
	public Single? WEAPON_MAXDISTANCE { get; set; }

	/// <summary> TEMPLATE <br/> The projectile template for the weapon. <br/> 3435 </summary>
	[Tag( Tag.WEAPON_PROJECTILE, 3435 )]
	public String? WEAPON_PROJECTILE { get; set; }

	/// <summary> TEMPLATE <br/> The template to create as a muzzle flash <br/> 3440 </summary>
	[Tag( Tag.WEAPON_MUZZLEFLASH1, 3440 )]
	public String? WEAPON_MUZZLEFLASH1 { get; set; }

	/// <summary> UINT <br/> The ammo type 1 of the weapon. <br/> 3445 </summary>
	[Tag( Tag.WEAPON_AMMOTYPE1, 3445 )]
	public Int32? WEAPON_AMMOTYPE1 { get; set; }

	/// <summary> UINT <br/> The max ammo 1 of the weapon. <br/> 3450 </summary>
	[Tag( Tag.WEAPON_MAXAMMO1, 3450 )]
	public Int32? WEAPON_MAXAMMO1 { get; set; }

	/// <summary> UINT <br/> The ammo use 1 of the weapon. <br/> 3455 </summary>
	[Tag( Tag.WEAPON_AMMOUSE1, 3455 )]
	public Int32? WEAPON_AMMOUSE1 { get; set; }

	/// <summary> UFLOAT <br/> The accuracy of the weapon. <br/> 3460 </summary>
	[Tag( Tag.WEAPON_ACCURACY1, 3460 )]
	public Single? WEAPON_ACCURACY1 { get; set; }

	/// <summary> ENUM_WEAPON_MELEETYPE <br/> The melee type 2 of a weapon. <br/> 3500 </summary>
	[Tag( Tag.WEAPON_MELEETYPE2, 3500 )]
	public ENUM_WEAPON_MELEETYPE? WEAPON_MELEETYPE2 { get; set; }

	/// <summary> FLAG_WEAPON_FLAGS <br/> The weapon flags2. <br/> 3502 </summary>
	[Tag( Tag.WEAPON_FLAGS2, 3502 )]
	public FLAG_WEAPON_FLAGS? WEAPON_FLAGS2 { get; set; }

	/// <summary> UINT <br/> The second damage of the weapon. <br/> 3505 </summary>
	[Tag( Tag.WEAPON_DAMAGE2, 3505 )]
	public Int32? WEAPON_DAMAGE2 { get; set; }

	/// <summary> ENUM_WEAPON_DAMAGETYPE <br/> The second damage type of the weapon. <br/> 3510 </summary>
	[Tag( Tag.WEAPON_DAMAGETYPE2, 3510 )]
	public ENUM_WEAPON_DAMAGETYPE? WEAPON_DAMAGETYPE2 { get; set; }

	/// <summary> UFLOAT <br/> The second strike time of the weapon. <br/> 3515 </summary>
	[Tag( Tag.WEAPON_STRIKETIME2, 3515 )]
	public Single? WEAPON_STRIKETIME2 { get; set; }

	/// <summary> UFLOAT <br/> The reload time 2 of the weapon. <br/> 3520 </summary>
	[Tag( Tag.WEAPON_RELOADTIME2, 3520 )]
	public Single? WEAPON_RELOADTIME2 { get; set; }

	/// <summary> UFLOAT <br/> The min distance 2 of the weapon. <br/> 3525 </summary>
	[Tag( Tag.WEAPON_MINDISTANCE2, 3525 )]
	public Single? WEAPON_MINDISTANCE2 { get; set; }

	/// <summary> UFLOAT <br/> The max distance 2 of the weapon. <br/> 3530 </summary>
	[Tag( Tag.WEAPON_MAXDISTANCE2, 3530 )]
	public Single? WEAPON_MAXDISTANCE2 { get; set; }

	/// <summary> TEMPLATE <br/> The projectile 2 template for the weapon. <br/> 3535 </summary>
	[Tag( Tag.WEAPON_PROJECTILE2, 3535 )]
	public String? WEAPON_PROJECTILE2 { get; set; }

	/// <summary> TEMPLATE <br/> The template to create as a muzzle flash <br/> 3540 </summary>
	[Tag( Tag.WEAPON_MUZZLEFLASH2, 3540 )]
	public String? WEAPON_MUZZLEFLASH2 { get; set; }

	/// <summary> UINT <br/> The ammo type 2 of the weapon. <br/> 3545 </summary>
	[Tag( Tag.WEAPON_AMMOTYPE2, 3545 )]
	public Int32? WEAPON_AMMOTYPE2 { get; set; }

	/// <summary> UINT <br/> The max ammo 2 of the weapon. <br/> 3550 </summary>
	[Tag( Tag.WEAPON_MAXAMMO2, 3550 )]
	public Int32? WEAPON_MAXAMMO2 { get; set; }

	/// <summary> UINT <br/> The ammo use 2 of the weapon. <br/> 3555 </summary>
	[Tag( Tag.WEAPON_AMMOUSE2, 3555 )]
	public Int32? WEAPON_AMMOUSE2 { get; set; }

	/// <summary> UFLOAT <br/> The accuracy of the weapon. <br/> 3560 </summary>
	[Tag( Tag.WEAPON_ACCURACY2, 3560 )]
	public Single? WEAPON_ACCURACY2 { get; set; }

	/// <summary> STRING <br/> Effect name 0 <br/> 3600 </summary>
	[Tag( Tag.ITEM_EFFECTNAME0, 3600 )]
	public String? ITEM_EFFECTNAME0 { get; set; }

	/// <summary> SINT <br/> Effect level 0 <br/> 3601 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL0, 3601 )]
	public Int32? ITEM_EFFECTLEVEL0 { get; set; }

	/// <summary> FLOAT <br/> Effect value 0 <br/> 3602 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE0, 3602 )]
	public Single? ITEM_EFFECTVALUE0 { get; set; }

	/// <summary> UINT <br/> Effect duration 0 <br/> 3603 </summary>
	[Tag( Tag.ITEM_EFFECTTIME0, 3603 )]
	public Int32? ITEM_EFFECTTIME0 { get; set; }

	/// <summary> STRING <br/> Effect name 1 <br/> 3610 </summary>
	[Tag( Tag.ITEM_EFFECTNAME1, 3610 )]
	public String? ITEM_EFFECTNAME1 { get; set; }

	/// <summary> SINT <br/> Effect level 1 <br/> 3611 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL1, 3611 )]
	public Int32? ITEM_EFFECTLEVEL1 { get; set; }

	/// <summary> FLOAT <br/> Effect value 1 <br/> 3612 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE1, 3612 )]
	public Single? ITEM_EFFECTVALUE1 { get; set; }

	/// <summary> UINT <br/> Effect duration 1 <br/> 3613 </summary>
	[Tag( Tag.ITEM_EFFECTTIME1, 3613 )]
	public Int32? ITEM_EFFECTTIME1 { get; set; }

	/// <summary> STRING <br/> Effect name 2 <br/> 3620 </summary>
	[Tag( Tag.ITEM_EFFECTNAME2, 3620 )]
	public String? ITEM_EFFECTNAME2 { get; set; }

	/// <summary> SINT <br/> Effect level 2 <br/> 3621 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL2, 3621 )]
	public Int32? ITEM_EFFECTLEVEL2 { get; set; }

	/// <summary> FLOAT <br/> Effect value 2 <br/> 3622 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE2, 3622 )]
	public Single? ITEM_EFFECTVALUE2 { get; set; }

	/// <summary> UINT <br/> Effect duration 2 <br/> 3623 </summary>
	[Tag( Tag.ITEM_EFFECTTIME2, 3623 )]
	public Int32? ITEM_EFFECTTIME2 { get; set; }

	/// <summary> STRING <br/> Effect name 3 <br/> 3630 </summary>
	[Tag( Tag.ITEM_EFFECTNAME3, 3630 )]
	public String? ITEM_EFFECTNAME3 { get; set; }

	/// <summary> SINT <br/> Effect level 3 <br/> 3631 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL3, 3631 )]
	public Int32? ITEM_EFFECTLEVEL3 { get; set; }

	/// <summary> FLOAT <br/> Effect value 3 <br/> 3632 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE3, 3632 )]
	public Single? ITEM_EFFECTVALUE3 { get; set; }

	/// <summary> UINT <br/> Effect duration 3 <br/> 3633 </summary>
	[Tag( Tag.ITEM_EFFECTTIME3, 3633 )]
	public Int32? ITEM_EFFECTTIME3 { get; set; }

	/// <summary> STRING <br/> Effect name 4 <br/> 3640 </summary>
	[Tag( Tag.ITEM_EFFECTNAME4, 3640 )]
	public String? ITEM_EFFECTNAME4 { get; set; }

	/// <summary> SINT <br/> Effect level 4 <br/> 3641 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL4, 3641 )]
	public Int32? ITEM_EFFECTLEVEL4 { get; set; }

	/// <summary> FLOAT <br/> Effect value 4 <br/> 3642 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE4, 3642 )]
	public Single? ITEM_EFFECTVALUE4 { get; set; }

	/// <summary> UINT <br/> Effect duration 4 <br/> 3643 </summary>
	[Tag( Tag.ITEM_EFFECTTIME4, 3643 )]
	public Int32? ITEM_EFFECTTIME4 { get; set; }

	/// <summary> STRING <br/> Effect name 5 <br/> 3650 </summary>
	[Tag( Tag.ITEM_EFFECTNAME5, 3650 )]
	public String? ITEM_EFFECTNAME5 { get; set; }

	/// <summary> SINT <br/> Effect level 5 <br/> 3651 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL5, 3651 )]
	public Int32? ITEM_EFFECTLEVEL5 { get; set; }

	/// <summary> FLOAT <br/> Effect value 5 <br/> 3652 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE5, 3652 )]
	public Single? ITEM_EFFECTVALUE5 { get; set; }

	/// <summary> UINT <br/> Effect duration 5 <br/> 3653 </summary>
	[Tag( Tag.ITEM_EFFECTTIME5, 3653 )]
	public Int32? ITEM_EFFECTTIME5 { get; set; }

	/// <summary> STRING <br/> Effect name 6 <br/> 3660 </summary>
	[Tag( Tag.ITEM_EFFECTNAME6, 3660 )]
	public String? ITEM_EFFECTNAME6 { get; set; }

	/// <summary> SINT <br/> Effect level 6 <br/> 3661 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL6, 3661 )]
	public Int32? ITEM_EFFECTLEVEL6 { get; set; }

	/// <summary> FLOAT <br/> Effect value 6 <br/> 3662 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE6, 3662 )]
	public Single? ITEM_EFFECTVALUE6 { get; set; }

	/// <summary> UINT <br/> Effect duration 6 <br/> 3663 </summary>
	[Tag( Tag.ITEM_EFFECTTIME6, 3663 )]
	public Int32? ITEM_EFFECTTIME6 { get; set; }

	/// <summary> STRING <br/> Effect name 7 <br/> 3670 </summary>
	[Tag( Tag.ITEM_EFFECTNAME7, 3670 )]
	public String? ITEM_EFFECTNAME7 { get; set; }

	/// <summary> SINT <br/> Effect level 7 <br/> 3671 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL7, 3671 )]
	public Int32? ITEM_EFFECTLEVEL7 { get; set; }

	/// <summary> FLOAT <br/> Effect value 7 <br/> 3672 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE7, 3672 )]
	public Single? ITEM_EFFECTVALUE7 { get; set; }

	/// <summary> UINT <br/> Effect duration 7 <br/> 3673 </summary>
	[Tag( Tag.ITEM_EFFECTTIME7, 3673 )]
	public Int32? ITEM_EFFECTTIME7 { get; set; }

	/// <summary> STRING <br/> Effect name 8 <br/> 3680 </summary>
	[Tag( Tag.ITEM_EFFECTNAME8, 3680 )]
	public String? ITEM_EFFECTNAME8 { get; set; }

	/// <summary> SINT <br/> Effect level 8 <br/> 3681 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL8, 3681 )]
	public Int32? ITEM_EFFECTLEVEL8 { get; set; }

	/// <summary> FLOAT <br/> Effect value 8 <br/> 3682 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE8, 3682 )]
	public Single? ITEM_EFFECTVALUE8 { get; set; }

	/// <summary> UINT <br/> Effect duration 8 <br/> 3683 </summary>
	[Tag( Tag.ITEM_EFFECTTIME8, 3683 )]
	public Int32? ITEM_EFFECTTIME8 { get; set; }

	/// <summary> STRING <br/> Effect name 9 <br/> 3690 </summary>
	[Tag( Tag.ITEM_EFFECTNAME9, 3690 )]
	public String? ITEM_EFFECTNAME9 { get; set; }

	/// <summary> SINT <br/> Effect level 9 <br/> 3691 </summary>
	[Tag( Tag.ITEM_EFFECTLEVEL9, 3691 )]
	public Int32? ITEM_EFFECTLEVEL9 { get; set; }

	/// <summary> FLOAT <br/> Effect value 9 <br/> 3692 </summary>
	[Tag( Tag.ITEM_EFFECTVALUE9, 3692 )]
	public Single? ITEM_EFFECTVALUE9 { get; set; }

	/// <summary> UINT <br/> Effect duration 9 <br/> 3693 </summary>
	[Tag( Tag.ITEM_EFFECTTIME9, 3693 )]
	public Int32? ITEM_EFFECTTIME9 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 0 <br/> 3700 </summary>
	[Tag( Tag.ITEM_RESTRTYPE0, 3700 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE0 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 0 <br/> 3701 </summary>
	[Tag( Tag.ITEM_RESTRVALUE0, 3701 )]
	public Single? ITEM_RESTRVALUE0 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 1 <br/> 3710 </summary>
	[Tag( Tag.ITEM_RESTRTYPE1, 3710 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE1 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 1 <br/> 3711 </summary>
	[Tag( Tag.ITEM_RESTRVALUE1, 3711 )]
	public Single? ITEM_RESTRVALUE1 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 2 <br/> 3720 </summary>
	[Tag( Tag.ITEM_RESTRTYPE2, 3720 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE2 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 2 <br/> 3721 </summary>
	[Tag( Tag.ITEM_RESTRVALUE2, 3721 )]
	public Single? ITEM_RESTRVALUE2 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 3 <br/> 3730 </summary>
	[Tag( Tag.ITEM_RESTRTYPE3, 3730 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE3 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 3 <br/> 3731 </summary>
	[Tag( Tag.ITEM_RESTRVALUE3, 3731 )]
	public Single? ITEM_RESTRVALUE3 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 4 <br/> 3740 </summary>
	[Tag( Tag.ITEM_RESTRTYPE4, 3740 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE4 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 4 <br/> 3741 </summary>
	[Tag( Tag.ITEM_RESTRVALUE4, 3741 )]
	public Single? ITEM_RESTRVALUE4 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 5 <br/> 3750 </summary>
	[Tag( Tag.ITEM_RESTRTYPE5, 3750 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE5 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 5 <br/> 3751 </summary>
	[Tag( Tag.ITEM_RESTRVALUE5, 3751 )]
	public Single? ITEM_RESTRVALUE5 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 6 <br/> 3760 </summary>
	[Tag( Tag.ITEM_RESTRTYPE6, 3760 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE6 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 6 <br/> 3761 </summary>
	[Tag( Tag.ITEM_RESTRVALUE6, 3761 )]
	public Single? ITEM_RESTRVALUE6 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 7 <br/> 3770 </summary>
	[Tag( Tag.ITEM_RESTRTYPE7, 3770 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE7 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 7 <br/> 3771 </summary>
	[Tag( Tag.ITEM_RESTRVALUE7, 3771 )]
	public Single? ITEM_RESTRVALUE7 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 8 <br/> 3780 </summary>
	[Tag( Tag.ITEM_RESTRTYPE8, 3780 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE8 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 8 <br/> 3781 </summary>
	[Tag( Tag.ITEM_RESTRVALUE8, 3781 )]
	public Single? ITEM_RESTRVALUE8 { get; set; }

	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 9 <br/> 3790 </summary>
	[Tag( Tag.ITEM_RESTRTYPE9, 3790 )]
	public ENUM_ITEM_RESTRTYPE? ITEM_RESTRTYPE9 { get; set; }

	/// <summary> FLOAT <br/> Item restriction value 9 <br/> 3791 </summary>
	[Tag( Tag.ITEM_RESTRVALUE9, 3791 )]
	public Single? ITEM_RESTRVALUE9 { get; set; }

	/// <summary> STRING <br/> The stringID of this actor cast name. <br/> 4005 </summary>
	[Tag( Tag.CASTID, 4005 )]
	public String? CASTID { get; set; }

	/// <summary> FLAG_ACTOR_FLAGS <br/> The actor flags. <br/> 4007 </summary>
	[Tag( Tag.ACTOR_FLAGS, 4007 )]
	public FLAG_ACTOR_FLAGS? ACTOR_FLAGS { get; set; }

	/// <summary> ENUM_ACTOR_TYPE <br/> The type of actor. <br/> 4008 </summary>
	[Tag( Tag.ACTOR_TYPE, 4008 )]
	public ENUM_ACTOR_TYPE? ACTOR_TYPE { get; set; }

	/// <summary> STRINGLIST <br/> The clan of actor. <br/> 4009 </summary>
	[Tag( Tag.ACTOR_CLAN, 4009 )]
	public String? ACTOR_CLAN { get; set; }

	/// <summary> ENUM_ACTOR_TEAM <br/> The team of actor. <br/> 4010 </summary>
	[Tag( Tag.ACTOR_TEAM, 4010 )]
	public ENUM_ACTOR_TEAM? ACTOR_TEAM { get; set; }

	/// <summary> ENUM_ACTOR_AURA <br/> The aura of the actor. <br/> 4013 </summary>
	[Tag( Tag.ACTOR_AURA, 4013 )]
	public ENUM_ACTOR_AURA? ACTOR_AURA { get; set; }

	/// <summary> ENUM_ACTOR_SIZE <br/> The size of the actor. <br/> 4014 </summary>
	[Tag( Tag.ACTOR_SIZE, 4014 )]
	public ENUM_ACTOR_SIZE? ACTOR_SIZE { get; set; }

	/// <summary> UINT <br/> The starting health of an actor. <br/> 4015 </summary>
	[Tag( Tag.ACTOR_HEALTH, 4015 )]
	public Int32? ACTOR_HEALTH { get; set; }

	/// <summary> UINT <br/> The max health of an actor when fully healed. <br/> 4016 </summary>
	[Tag( Tag.ACTOR_MAXHEALTH, 4016 )]
	public Int32? ACTOR_MAXHEALTH { get; set; }

	/// <summary> UFLOAT <br/> The walking speed of an actor. <br/> 4020 </summary>
	[Tag( Tag.ACTOR_WALKSPEED, 4020 )]
	public Single? ACTOR_WALKSPEED { get; set; }

	/// <summary> UFLOAT <br/> The running speed of an actor. <br/> 4025 </summary>
	[Tag( Tag.ACTOR_RUNSPEED, 4025 )]
	public Single? ACTOR_RUNSPEED { get; set; }

	/// <summary> UINT <br/> The XP worth of an actor. <br/> 4050 </summary>
	[Tag( Tag.ACTOR_XP, 4050 )]
	public Int32? ACTOR_XP { get; set; }

	/// <summary> TEMPLATE <br/> The item dropped when the thing dies. <br/> 4053 </summary>
	[Tag( Tag.DROPITEM, 4053 )]
	public String? DROPITEM { get; set; }

	/// <summary> UINT <br/> The TREASURECLASS of a thing. <br/> 4055 </summary>
	[Tag( Tag.TREASURECLASS, 4055 )]
	public Int32? TREASURECLASS { get; set; }

	/// <summary> STRING <br/> The RPG stats of a player. <br/> 4060 </summary>
	[Tag( Tag.ACTOR_RPGSTATS, 4060 )]
	public String? ACTOR_RPGSTATS { get; set; }

	/// <summary> FILENAME <br/> The AI file of an actor. <br/> 4070 </summary>
	[Tag( Tag.ACTOR_AI, 4070 )]
	public String? ACTOR_AI { get; set; }

	/// <summary> TEMPLATE <br/> The weapon of an actor. <br/> 4075 </summary>
	[Tag( Tag.ACTOR_WEAPON, 4075 )]
	public String? ACTOR_WEAPON { get; set; }

	/// <summary> VECTOR <br/> The firing position of the actor. <br/> 4080 </summary>
	[Tag( Tag.ACTOR_FIREPOS, 4080 )]
	public Vector3? ACTOR_FIREPOS { get; set; }

	/// <summary> FILENAME <br/> The head model of an actor. <br/> 4100 </summary>
	[Tag( Tag.ACTOR_HEADMODEL, 4100 )]
	public String? ACTOR_HEADMODEL { get; set; }

	/// <summary> FILENAME <br/> The head motion of an actor. <br/> 4110 </summary>
	[Tag( Tag.ACTOR_HEADMOTION, 4110 )]
	public String? ACTOR_HEADMOTION { get; set; }

	/// <summary> FLOAT <br/> The strength of an actor. <br/> 4200 </summary>
	[Tag( Tag.STAT_STRENGTH, 4200 )]
	public Single? STAT_STRENGTH { get; set; }

	/// <summary> FLOAT <br/> The dexterity of an actor. <br/> 4211 </summary>
	[Tag( Tag.STAT_DEXTERITY, 4211 )]
	public Single? STAT_DEXTERITY { get; set; }

	/// <summary> FLOAT <br/> The stamina of an actor. <br/> 4212 </summary>
	[Tag( Tag.STAT_STAMINA, 4212 )]
	public Single? STAT_STAMINA { get; set; }

	/// <summary> FLOAT <br/> The perception of an actor. <br/> 4213 </summary>
	[Tag( Tag.STAT_PERCEPTION, 4213 )]
	public Single? STAT_PERCEPTION { get; set; }

	/// <summary> FLOAT <br/> The intelligence of an actor. <br/> 4214 </summary>
	[Tag( Tag.STAT_INTELLIGENCE, 4214 )]
	public Single? STAT_INTELLIGENCE { get; set; }

	/// <summary> FLOAT <br/> The wits of an actor. <br/> 4215 </summary>
	[Tag( Tag.STAT_WITS, 4215 )]
	public Single? STAT_WITS { get; set; }

	/// <summary> FLOAT <br/> The blood of an actor. <br/> 4216 </summary>
	[Tag( Tag.STAT_BLOOD, 4216 )]
	public Single? STAT_BLOOD { get; set; }

	/// <summary> FLOAT <br/> The humanity of an actor. <br/> 4217 </summary>
	[Tag( Tag.STAT_HUMANITY, 4217 )]
	public Single? STAT_HUMANITY { get; set; }

	/// <summary> FLOAT <br/> The frenzy of an actor. <br/> 4218 </summary>
	[Tag( Tag.STAT_FRENZY, 4218 )]
	public Single? STAT_FRENZY { get; set; }

	/// <summary> FLOAT <br/> The generation of an actor. <br/> 4219 </summary>
	[Tag( Tag.STAT_GENERATION, 4219 )]
	public Single? STAT_GENERATION { get; set; }

	/// <summary> FLOAT <br/> The faith of an actor. <br/> 4220 </summary>
	[Tag( Tag.STAT_FAITH, 4220 )]
	public Single? STAT_FAITH { get; set; }

	/// <summary> FLOAT <br/> The charisma of an actor. <br/> 4221 </summary>
	[Tag( Tag.STAT_CHARISMA, 4221 )]
	public Single? STAT_CHARISMA { get; set; }

	/// <summary> FLOAT <br/> The manipulation of an actor. <br/> 4222 </summary>
	[Tag( Tag.STAT_MANIPULATION, 4222 )]
	public Single? STAT_MANIPULATION { get; set; }

	/// <summary> FLOAT <br/> The appearance of an actor. <br/> 4223 </summary>
	[Tag( Tag.STAT_APPEARANCE, 4223 )]
	public Single? STAT_APPEARANCE { get; set; }

	/// <summary> FLOAT <br/> The frenzy rating of an actor. <br/> 4224 </summary>
	[Tag( Tag.STAT_FRENZYRATING, 4224 )]
	public Single? STAT_FRENZYRATING { get; set; }

	/// <summary> FLOAT <br/> The blood rating of an actor. <br/> 4225 </summary>
	[Tag( Tag.STAT_BLOODRATING, 4225 )]
	public Single? STAT_BLOODRATING { get; set; }

	/// <summary> FLOAT <br/> The size of the bloodpool. <br/> 4226 </summary>
	[Tag( Tag.STAT_BLOODPOOL, 4226 )]
	public Single? STAT_BLOODPOOL { get; set; }

	/// <summary> FLOAT <br/> The mana of an actor. <br/> 4227 </summary>
	[Tag( Tag.STAT_MANA, 4227 )]
	public Single? STAT_MANA { get; set; }

	/// <summary> FLOAT <br/> The manapool of an actor. <br/> 4228 </summary>
	[Tag( Tag.STAT_MANAPOOL, 4228 )]
	public Single? STAT_MANAPOOL { get; set; }

	/// <summary> FLOAT <br/> The soak for normal damage. <br/> 4230 </summary>
	[Tag( Tag.SOAK_NORMAL, 4230 )]
	public Single? SOAK_NORMAL { get; set; }

	/// <summary> FLOAT <br/> The soak for lethal damage. <br/> 4231 </summary>
	[Tag( Tag.SOAK_LETHAL, 4231 )]
	public Single? SOAK_LETHAL { get; set; }

	/// <summary> FLOAT <br/> The soak for aggravated damage. <br/> 4232 </summary>
	[Tag( Tag.SOAK_AGGRAVATED, 4232 )]
	public Single? SOAK_AGGRAVATED { get; set; }

	/// <summary> FLOAT <br/> The soak for electrical damage. <br/> 4233 </summary>
	[Tag( Tag.SOAK_ELECTRIC, 4233 )]
	public Single? SOAK_ELECTRIC { get; set; }

	/// <summary> FLOAT <br/> The soak for fire damage. <br/> 4234 </summary>
	[Tag( Tag.SOAK_FIRE, 4234 )]
	public Single? SOAK_FIRE { get; set; }

	/// <summary> FLOAT <br/> The soak for sun damage. <br/> 4235 </summary>
	[Tag( Tag.SOAK_SUN, 4235 )]
	public Single? SOAK_SUN { get; set; }

	/// <summary> FLOAT <br/> The soak for faith damage. <br/> 4236 </summary>
	[Tag( Tag.SOAK_FAITH, 4236 )]
	public Single? SOAK_FAITH { get; set; }

	/// <summary> FLOAT <br/> The soak for cold damage. <br/> 4237 </summary>
	[Tag( Tag.SOAK_COLD, 4237 )]
	public Single? SOAK_COLD { get; set; }

	/// <summary> FLOAT <br/> The soak for poison damage. <br/> 4238 </summary>
	[Tag( Tag.SOAK_POISON, 4238 )]
	public Single? SOAK_POISON { get; set; }

	/// <summary> FLOAT <br/> The soak for disease damage. <br/> 4239 </summary>
	[Tag( Tag.SOAK_DISEASE, 4239 )]
	public Single? SOAK_DISEASE { get; set; }

	/// <summary> UINT <br/> Gib class. <br/> 4270 </summary>
	[Tag( Tag.ACTOR_GIBCLASS, 4270 )]
	public Int32? ACTOR_GIBCLASS { get; set; }

	/// <summary> FLAG_PROJECT_FLAGS <br/> The projectile flags of a template. <br/> 7000 </summary>
	[Tag( Tag.PROJECT_FLAGS, 7000 )]
	public FLAG_PROJECT_FLAGS? PROJECT_FLAGS { get; set; }

	/// <summary> UINT <br/> The damage of a projectile. <br/> 7010 </summary>
	[Tag( Tag.PROJECT_DAMAGE, 7010 )]
	public Int32? PROJECT_DAMAGE { get; set; }

	/// <summary> ENUM_PROJECT_DAMAGETYPE <br/> The damage type of a projectile. <br/> 7020 </summary>
	[Tag( Tag.PROJECT_DAMAGETYPE, 7020 )]
	public ENUM_PROJECT_DAMAGETYPE? PROJECT_DAMAGETYPE { get; set; }

	/// <summary> UFLOAT <br/> The max distance for a projectile. <br/> 7030 </summary>
	[Tag( Tag.PROJECT_MAXDIST, 7030 )]
	public Single? PROJECT_MAXDIST { get; set; }

	/// <summary> FLAG_PHYSICS_FLAGS <br/> The physics flags of a template. <br/> 7100 </summary>
	[Tag( Tag.PHYSICS_FLAGS, 7100 )]
	public FLAG_PHYSICS_FLAGS? PHYSICS_FLAGS { get; set; }

	/// <summary> VECTOR <br/> The initial velocity. <br/> 7110 </summary>
	[Tag( Tag.PHYSICS_VELOCITY, 7110 )]
	public Vector3? PHYSICS_VELOCITY { get; set; }

	/// <summary> VECTOR <br/> The initial rotational velocity. <br/> 7120 </summary>
	[Tag( Tag.PHYSICS_ROTVEL, 7120 )]
	public Vector3? PHYSICS_ROTVEL { get; set; }

	/// <summary> UFLOAT <br/> The velocity drag. <br/> 7130 </summary>
	[Tag( Tag.PHYSICS_VELDRAG, 7130 )]
	public Single? PHYSICS_VELDRAG { get; set; }

	/// <summary> UFLOAT <br/> The rotational drag. <br/> 7140 </summary>
	[Tag( Tag.PHYSICS_ROTDRAG, 7140 )]
	public Single? PHYSICS_ROTDRAG { get; set; }

	/// <summary> UFLOAT <br/> The mass. <br/> 7150 </summary>
	[Tag( Tag.PHYSICS_MASS, 7150 )]
	public Single? PHYSICS_MASS { get; set; }

	/// <summary> FLOAT <br/> The gravity for this object. <br/> 7155 </summary>
	[Tag( Tag.PHYSICS_GRAVITY, 7155 )]
	public Single? PHYSICS_GRAVITY { get; set; }

	/// <summary> UFLOAT <br/> The elasticity. <br/> 7160 </summary>
	[Tag( Tag.PHYSICS_ELASTICITY, 7160 )]
	public Single? PHYSICS_ELASTICITY { get; set; }

	/// <summary> VECTOR <br/> The thrust. <br/> 7170 </summary>
	[Tag( Tag.PHYSICS_THRUST, 7170 )]
	public Vector3? PHYSICS_THRUST { get; set; }

	/// <summary> FLAG_REGION_FLAGS <br/> The region flags of a template. <br/> 7200 </summary>
	[Tag( Tag.REGION_FLAGS, 7200 )]
	public FLAG_REGION_FLAGS? REGION_FLAGS { get; set; }

	/// <summary> FILENAMENOEXT <br/> The material of the halo. <br/> 7300 </summary>
	[Tag( Tag.HALOMATERIAL, 7300 )]
	public String? HALOMATERIAL { get; set; }

	/// <summary> UFLOAT <br/> The size of the halo. <br/> 7310 </summary>
	[Tag( Tag.HALOSIZE, 7310 )]
	public Single? HALOSIZE { get; set; }

	/// <summary> RGB <br/> The color of the halo. <br/> 7320 </summary>
	[Tag( Tag.HALOCOLOR, 7320 )]
	public Color? HALOCOLOR { get; set; }

	/// <summary> FILENAME <br/> The name of the explosion file for this template. <br/> 7500 </summary>
	[Tag( Tag.EXPLOSION_NAME, 7500 )]
	public String? EXPLOSION_NAME { get; set; }

	/// <summary> FLAG_EXPLOSION_FLAGS <br/> The explosion flags. <br/> 7510 </summary>
	[Tag( Tag.EXPLOSION_FLAGS, 7510 )]
	public FLAG_EXPLOSION_FLAGS? EXPLOSION_FLAGS { get; set; }

	/// <summary> UINT <br/> The damage a container can take. <br/> 8020 </summary>
	[Tag( Tag.CONTAINER_HEALTH, 8020 )]
	public Int32? CONTAINER_HEALTH { get; set; }

	/// <summary> ENUM_EDITORCATEGORY <br/> The editor category of the item <br/> 9000 </summary>
	[Tag( Tag.EDITORCATEGORY, 9000 )]
	public ENUM_EDITORCATEGORY? EDITORCATEGORY { get; set; }

	/// <summary> VECTOR <br/> Mins vector for the editor <br/> 9010 </summary>
	[Tag( Tag.EDITORSIZEMINS, 9010 )]
	public Vector3? EDITORSIZEMINS { get; set; }

	/// <summary> VECTOR <br/> Maxs vector for the editor <br/> 9020 </summary>
	[Tag( Tag.EDITORSIZEMAXS, 9020 )]
	public Vector3? EDITORSIZEMAXS { get; set; }

	/// <summary> RGB <br/> Editor color <br/> 9030 </summary>
	[Tag( Tag.EDITORCOLOR, 9030 )]
	public Color? EDITORCOLOR { get; set; }

	/// <summary> STRING <br/> The editor subcategory (free form text). <br/> 9040 </summary>
	[Tag( Tag.EDITORSUBCATEGORY, 9040 )]
	public String? EDITORSUBCATEGORY { get; set; }
}


public enum ACTOR_CLAN
{
	BRUJAH,
	CAPPADOCIAN,
	GANGREL,
	NOSFERATU,
	TOREADOR,
	ASSAMITE,
	GIOVANNI,
	LASOMBRA,
	MALKAVIAN,
	RAVNOS,
	SETITE,
	TREMERE,
	TZIMISCE,
	VENTRUE,
	CAITIFF
}


public enum ENUM_ACTOR_AURA
{
	NONE			= 0,
	AFRAID			= 1,
	AGGRESSIVE		= 2,
	ANGRY			= 3,
	BITTER			= 4,
	CALM			= 5,
	COMPASSIONATE	= 6,
	CONFUSED		= 7,
	CONSERVATIVE	= 8,
	DAYDREAMING		= 9,
	DEPRESSED		= 10,
	DESIROUS		= 11,
	DIABLERIST		= 12,
	DISTRUSTFUL		= 13,
	ENVIOUS			= 14,
	EXCITED			= 15,
	FRENZIED		= 16,
	GENEROUS		= 17,
	HAPPY			= 18,
	HATEFUL			= 19,
	IDEALISTIC		= 20,
	INNOCENT		= 21,
	LOVESTRUCK		= 22,
	LUSTFUL			= 23,
	OBSESSED		= 24,
	PSYCHOTIC		= 25,
	SAD				= 26,
	SPIRITUAL		= 27,
	SUSPICIOUS		= 28,
	FAERIE			= 29,
	GHOST			= 30,
	MAGICUSE		= 31,
	VAMPIRE			= 32,
	WEREBEAST		= 33,
	USERDEFINED		= 34
}


public enum ENUM_ACTOR_SIZE
{
	SMALL	= 0,
	MEDIUM	= 1,
	LARGE	= 2
}


public enum ENUM_ACTOR_TEAM
{
	PLAYER_0	= 0,
	PLAYER_1	= 1,
	PLAYER_2	= 2,
	PLAYER_3	= 3,
	PLAYER_4	= 4,
	PLAYER_5	= 5,
	PLAYER_6	= 6,
	PLAYER_7	= 7,
	NONE		= 8,
	NA			= 9,
	PEDESTRIANS	= 10,
	LAWMEN		= 11,
	ENEMY_0		= 12,
	ENEMY_1		= 13,
	ENEMY_2		= 14,
	ENEMY_3		= 15,
	ENEMY_4		= 16,
	ENEMY_5		= 17,
	ENEMY_6		= 18,
	ENEMY_7		= 19
}


public enum ENUM_ACTOR_TYPE
{
	VAMPIRE	= 0,
	GHOUL	= 1,
	HUMAN	= 2,
	MONSTER	= 3
}


public enum ENUM_ARMOR_TYPE
{
	ARMOR_TYPE_0	= 0
}


public enum ENUM_COLLIDETYPE
{
	NONE			= 0,
	SPHERE			= 1,
	CYLINDER		= 2,
	BOUNDING_BOX	= 3
}


public enum ENUM_DYNA_STYLE
{
	ConstantIntensity				= 0,
	FlickeringTorch_LowTurbulence	= 1,
	FlickeringTorch_MedTurbulence	= 2,
	FlickeringTorch_HighTurbulence	= 3,
	SlowPulse						= 4,
	MediumPulse						= 5,
	FastPulse						= 6,
	SlowFluorescent_Flicker			= 7,
	MedFluorescent_Flicker			= 8,
	FastFluorescent_Flicker			= 9
}


public enum ENUM_EDITORCATEGORY
{
	/// <summary> base template, cannot be instantiated </summary>
	Abstract				= 0,

	/// <summary> template cannot be placed (projectiles, etc.) </summary>
	Dynamic					= 1,

	Armor					= 2,
	Armor_Magical			= 3,
	Bosses					= 4,
	Enemies					= 5,
	Items_Blood				= 6,
	Items_Holy_Relics		= 7,
	Items_Magical			= 8,
	Items_Quest				= 9,
	Items_Scrolls_Books		= 10,
	Lights					= 11,
	NPCs					= 12,
	Particles				= 13,
	Players					= 14,
	Props					= 15,
	Props_Large				= 16,
	Regions					= 17,
	Scripts					= 18,
	Treasure				= 19,
	Weapons_Melee			= 20,
	Weapons_Range			= 21,
	Weapons_Magical_Melee	= 22,
	Weapons_Magical_Range	= 23,
	Miscellaneous			= 24,
	Reserved1				= 25,
	Reserved2				= 26,
	Reserved3				= 27,
	Reserved4				= 28,
	Reserved5				= 29
}


public enum ENUM_ICONSIZE
{
	RPG_ICONSIZE_1x1 = 0,
	RPG_ICONSIZE_1x2 = 1,
	RPG_ICONSIZE_1x3 = 2,
	RPG_ICONSIZE_1x4 = 3,
	RPG_ICONSIZE_2x2 = 4,
	RPG_ICONSIZE_2x3 = 5,
	RPG_ICONSIZE_2x4 = 6,
	RPG_ICONSIZE_2x1 = 7
}


public enum ENUM_ICONTYPE
{
	INVENTORY	= 0,
	DISCIPLINE	= 1
}


public enum ENUM_ITEM_RESTRTYPE
{
	NONE			= 0,
	STRENGTH		= 1,
	DEXTERITY		= 2,
	STAMINA			= 3,
	PERCEPTION		= 4,
	INTELLIGENCE	= 5,
	WITS			= 6,
	BLOOD			= 7,
	HUMANITY		= 8,
	FRENZY			= 9,
	GENERATION		= 10,
	FAITH			= 11,
	CHARISMA		= 12,
	MANIPULATION	= 13,
	APPEARANCE		= 14,
	FRENZYRATING	= 15,
	BLOODRATING		= 16,
	BLOODPOOL		= 17,
	HEALTH			= 18,
	VAMPIRE			= 19,
	GHOUL			= 20,
	HUMAN			= 21,
	NOVAMPIRE		= 22,
	NOGHOUL			= 23,
	NOHUMAN			= 24,
	MANA			= 25,
	MANAPOOL		= 26
}


public enum ENUM_ITEM_TAG
{
	INVALID		= 0,
	ROOT		= 1,
	LHIP		= 2,
	LKNEE		= 3,
	LANKLE		= 4,
	LFOOT		= 5,
	RHIP		= 6,
	RKNEE		= 7,
	RANKLE		= 8,
	RFOOT		= 9,
	BTUNIC1		= 10,
	BTUNIC2		= 11,
	BTUNIC3		= 12,
	FTUNIC1		= 13,
	FTUNIC2		= 14,
	BACK1		= 15,
	BACK2		= 16,
	CHEST		= 17,
	NECK		= 18,
	HEAD		= 19,
	HELMET		= 20,
	JAW			= 21,
	RSHOULDER	= 22,
	RELBOW		= 23,
	RWRIST		= 24,
	RFINGERS	= 25,
	WEAPON		= 26,
	LSHOULDER	= 27,
	LELBOW		= 28,
	LWRIST		= 29,
	LFINGERS	= 30,
	SHIELD		= 31,
	MCAPE1		= 32,
	SCABBARD	= 33,
	USER0		= 34,
	USER1		= 35,
	USER2		= 36,
	USER3		= 37,
	USER4		= 38,
	USER5		= 39,
	USER6		= 40,
	USER7		= 41,
	USER8		= 42,
	USER9		= 43
}


public enum ENUM_ITEM_TYPE
{
	NORMAL		= 0,
	WEAPON		= 1,
	HAT			= 2,
	SHIELD		= 3,
	RING		= 4,
	AMULET		= 5,
	GAUNTLET	= 6,
	QUEST		= 7,
	ARMOR		= 8,
	AMMO		= 9,
	UTILITY		= 10,
	SCROLL		= 11,
	BLOODTEXT	= 12,
	BOOK		= 13
}


public enum ENUM_MOVETYPE
{
	NONE	= 0,
	WALK	= 1,
	TRACK	= 2,
	PHYSICS	= 3
}


public enum ENUM_PROJECT_DAMAGETYPE
{
	NORMAL		= 0,
	LETHAL		= 1,
	AGGRAVATED	= 2,
	ELECTRIC	= 3,
	FIRE		= 4,
	SUN			= 5,
	FAITH		= 6,
	COLD		= 7,
	POISON		= 8,
	DISEASE		= 9
}


public enum ENUM_SOUNDTYPE
{
	FLESH	= 0,
	STONE	= 1,
	METAL	= 2,
	LEATHE	= 3,
	CLOTH	= 4,
	MAIL	= 5,
	MAGIC	= 6,
	KEVLAR	= 7,
	WOOD	= 8,
	BONE	= 9
}


public enum ENUM_TYPE
{
	ACTOR		= 0,
	ITEM		= 1,
	PROP		= 2,
	REGION		= 3,
	PROJECTILE	= 4,
	WEAPON		= 5,
	ARMOR		= 6,
	PLAYER		= 7,
	CONTAINER	= 8,
	NONE		= 9,
	EXPLOSION	= 10
}


public enum ENUM_WEAPON_DAMAGETYPE
{
	NORMAL		= 0,
	LETHAL		= 1,
	AGGRAVATED	= 2,
	ELECTRIC	= 3,
	FIRE		= 4,
	SUN			= 5,
	FAITH		= 6,
	COLD		= 7,
	POISON		= 8,
	DISEASE		= 9
}


public enum ENUM_WEAPON_DEFENSETYPE
{
	SWORD_1H_overhead	= 0,
	SWORD_2H			= 1,
	PARRY_1H_sideways	= 2,
	PARRY_2H			= 3,
	STAFF				= 4,
	DUCK				= 5,
	SHIELD				= 6
}


public enum ENUM_WEAPON_MELEETYPE
{
	ATTACK_CLAW			= 0,
	ATTACK_SWORDSLASH	= 1,
	ATTACK_SWORDSLASH2	= 2,
	ATTACK_SWORDTHRUST	= 3,
	ATTACK_SWORDTHRUST2	= 4,
	ATTACK_BLUNTBASH	= 5,
	ATTACK_BLUNTBASH2	= 6,
	ATTACK_BOW			= 7,
	ATTACK_CROSSBOW		= 8,
	ATTACK_DAGGERSTAB	= 9,
	ATTACK_DAGGERCUT	= 10,
	ATTACK_SWING		= 11,
	ATTACK_PISTOL		= 12,
	ATTACK_RIFLEAIMED	= 13,
	ATTACK_SHOTGUN		= 14,
	ATTACK_RIFLEWAIST	= 15,
	ATTACK_ROCKET		= 16,
	ATTACK_STAKE		= 17,
	ATTACK_THROW		= 18,
	ATTACK_POLETHRUST	= 19,
	ATTACK_POLESLASH	= 20,
	ATTACK_BLUNTSLASH	= 21,
	ATTACK_BLUNTSLASH2	= 22,
	ATTACK_CLAWSPECIAL	= 23,
	ATTACK_PUNCH		= 24,
	ATTACK_CHAINSAW		= 25,
	ATTACK_TOSS			= 26,
	ATTACK_UZI			= 27,
	ATTACK_FLAMETHROWER	= 28,
	ATTACK_CHAINGUN		= 29
}


public enum ENUM_WEAPON_TYPE
{
	CARRY_UNARMED		= 0,
	CARRY_ONEHAND		= 1,
	CARRY_AXE2			= 2,
	CARRY_SWORD2		= 3,
	CARRY_POLEARM		= 4,
	CARRY_GUN			= 5,
	CARRY_GUN2			= 6,
	CARRY_BOW			= 7,
	CARRY_SHOTGUN		= 8,
	CARRY_CROSSBOW		= 9,
	CARRY_ROCKET		= 10,
	CARRY_CHAINSAW		= 11,
	CARRY_CHAINGUN		= 12,
	CARRY_UTIL			= 13,
	CARRY_UTIL_ONEHAND	= 14,
	CARRY_UTIL_GUN		= 15
}


[Flags]
public enum FLAG_ACTOR_FLAGS
{
	NONE					= 0,
	INVULNERABLE			= 0b_0000_0000_0000_0000_0000_0000_0001,
	INVUL_TO_PHYSICAL		= 0b_0000_0000_0000_0000_0000_0000_0010,
	AI_PAUSED				= 0b_0000_0000_0000_0000_0000_0000_0100,
	NO_FEED					= 0b_0000_0000_0000_0000_0000_0000_1000,
	NO_BLEED				= 0b_0000_0000_0000_0000_0000_0001_0000,
	ALWAYS_GIB_KILL			= 0b_0000_0000_0000_0000_0000_0010_0000,
	CAN_GIB_KILL			= 0b_0000_0000_0000_0000_0000_0100_0000,
	TALK_TO					= 0b_0000_0000_0000_0000_0000_1000_0000,
	PICKUP					= 0b_0000_0000_0000_0000_0001_0000_0000,
	STAKED					= 0b_0000_0000_0000_0000_0010_0000_0000,
	HURT_BY_SILVER			= 0b_0000_0000_0000_0000_0100_0000_0000,
	FAST_DRINKER			= 0b_0000_0000_0000_0000_1000_0000_0000,
	SUMMONED				= 0b_0000_0000_0000_0001_0000_0000_0000,
	ANIMAL					= 0b_0000_0000_0000_0010_0000_0000_0000,
	CAUSES_HUMANITY_LOSS	= 0b_0000_0000_0000_0100_0000_0000_0000,
	HUMANITY_TRACK			= 0b_0000_0000_0000_1000_0000_0000_0000,
	CAN_BEHEAD				= 0b_0000_0000_0001_0000_0000_0000_0000,
	ETHEREAL				= 0b_0000_0000_0010_0000_0000_0000_0000,
	NEUTRAL					= 0b_0000_0000_0100_0000_0000_0000_0000,
	AI_NO_RETURN_HOME		= 0b_0000_0000_1000_0000_0000_0000_0000,
	START_HIDDEN			= 0b_0000_0001_0000_0000_0000_0000_0000,
	NO_CORPSE_ROT			= 0b_0000_0010_0000_0000_0000_0000_0000,
	NO_DISC_CASTING			= 0b_0000_0100_0000_0000_0000_0000_0000,
	NO_SHAPE_DISC_CASTING	= 0b_0000_1000_0000_0000_0000_0000_0000,
	VALID_SHAPE				= 0b_0001_0000_0000_0000_0000_0000_0000,
	LUPINE					= 0b_0010_0000_0000_0000_0000_0000_0000,
	NO_SKELETON_DEATH		= 0b_0100_0000_0000_0000_0000_0000_0000,
	ONLY_GIB_KILL			= 0b_1000_0000_0000_0000_0000_0000_0000
}


[Flags]
public enum FLAG_DYNAFLAGS
{
	NONE			= 0,
	NO_FLAGS_YET	= 0b_0001,
}


[Flags]
public enum FLAG_EXPLOSION_FLAGS
{
	NONE						= 0,
	NO_DAMAGE_TO_SELF			= 0b_0000_0001,
	NO_DAMAGE_TO_TEAM			= 0b_0000_0010,
	NO_DAMAGE_TO_PLAYERS		= 0b_0000_0100,
	NO_DAMAGE_TO_ACTORS			= 0b_0000_1000,
	NO_DAMAGE_TO_CONTAINERS		= 0b_0001_0000,
	NO_DAMAGE_TO_PROJECTILES	= 0b_0010_0000,
	NO_DAMAGE_TO_PROPS			= 0b_0100_0000,
	WORLD_ORIENT				= 0b_1000_0000
}


[Flags]
public enum FLAG_ITEM_FLAGS
{
	NONE					= 0,
	DESTROYED_AT_0_CHARGES	= 0b_0000_0000_0000_0000_0001,
	IDENTIFIED				= 0b_0000_0000_0000_0000_0010,
	PRECOMPUTED_COST		= 0b_0000_0000_0000_0000_0100,
	UNIQUE_COST				= 0b_0000_0000_0000_0000_1000,
	NO_SELL					= 0b_0000_0000_0000_0001_0000,
	NO_RANDOM_MOD			= 0b_0000_0000_0000_0010_0000,
	CANNOT_BE_IDENTIFIED	= 0b_0000_0000_0000_0100_0000,
	INSTANT_CASH			= 0b_0000_0000_0000_1000_0000,
	NO_SMALL				= 0b_0000_0000_0001_0000_0000,
	NO_MEDIUM				= 0b_0000_0000_0010_0000_0000,
	NO_LARGE				= 0b_0000_0000_0100_0000_0000,
	CARRY_SPECIAL			= 0b_0000_0000_1000_0000_0000,
	MAGIC					= 0b_0000_0001_0000_0000_0000,
	CARRY_TO_MODERN_DAY		= 0b_0000_0010_0000_0000_0000,
	CURSED					= 0b_0000_0100_0000_0000_0000,
	TWO_HANDED				= 0b_0000_1000_0000_0000_0000,
	NO_PARTY_AI_USE			= 0b_0001_0000_0000_0000_0000
}


[Flags]
public enum FLAG_ITEM_SELLFLAGS
{
	NONE				= 0,
	MUNDANE				= 0b_0000_0000_0001,
	TREASURE			= 0b_0000_0000_0010,
	BLOOD_ITEM			= 0b_0000_0000_0100,
	POTION				= 0b_0000_0000_1000,
	ARMOR				= 0b_0000_0001_0000,
	MAGIC_ARMOR			= 0b_0000_0010_0000,
	WEAPON				= 0b_0000_0100_0000,
	MAGIC_WEAPON		= 0b_0000_1000_0000,
	SCROLLS_AND_BOOKS	= 0b_0001_0000_0000
}


[Flags]
public enum FLAG_MODELFLAGS
{
	NONE			= 0,
	PRE_LIT 		= 0b_0001,
	FULLY_LIT		= 0b_0010,

	/// <summary> do not use </summary>
	CONSTANT_LIT	= 0b_0100
}


[Flags]
public enum FLAG_PHYSICS_FLAGS
{
	NONE			= 0,
	GRAVITY			= 0b_0001,
	ROTATEVELOCITY	= 0b_0010,
	SURFACE_BOUNCE	= 0b_0100,
	STOPPED			= 0b_1000
}


[Flags]
public enum FLAG_PROJECT_FLAGS
{
	NONE				= 0,
	EXPLODE_ON_TIMER	= 0b_0000_0000_0001,
	EXPLODE_ON_THING	= 0b_0000_0000_0010,
	EXPLODE_ON_WALL		= 0b_0000_0000_0100,
	EXPLODE_ON_FLOOR	= 0b_0000_0000_1000,
	EXPLODE_ON_DAMAGE	= 0b_0000_0001_0000,
	INSTANT_FIRE		= 0b_0000_0010_0000,
	SILVER				= 0b_0000_0100_0000,
	STAKING				= 0b_0000_1000_0000,
	CAN_DODGE			= 0b_0001_0000_0000,
	BULLET				= 0b_0010_0000_0000
}


[Flags]
public enum FLAG_REGION_FLAGS
{
	NONE								= 0,
	Region_does_not_track_entry_exit	= 0b_0001,
	Region_collides_with_cameras		= 0b_0010,
	Region_is_intended_to_be_rendered	= 0b_0100
}


[Flags]
public enum FLAG_THINGFLAGS
{
	NONE				= 0,
	SAVE_NEVER			= 0b_0000_0000_0000_0000_0000_0001,
	SAVE_NEVER_MP		= 0b_0000_0000_0000_0000_0000_0010,
	unused0				= 0b_0000_0000_0000_0000_0000_0100,
	unused1				= 0b_0000_0000_0000_0000_0000_1000,
	NO_REFLECTION		= 0b_0000_0000_0000_0000_0001_0000,
	EMITTER				= 0b_0000_0000_0000_0000_0010_0000,
	NO_HIGHLIGHT		= 0b_0000_0000_0000_0000_0100_0000,
	DYNAMIC_LIGHT		= 0b_0000_0000_0000_0000_1000_0000,
	NO_NET_SYNCH		= 0b_0000_0000_0000_0001_0000_0000,
	DISABLE_COLLIDE		= 0b_0000_0000_0000_0010_0000_0000,
	PROXIMITY_TRIGGER	= 0b_0000_0000_0000_0100_0000_0000,
	STATIC_SHADOW		= 0b_0000_0000_0000_1000_0000_0000,
	NO_REGION_TRIGGER	= 0b_0000_0000_0001_0000_0000_0000,
	VIS_BLOCK			= 0b_0000_0000_0010_0000_0000_0000,
	CAMERA_BLOCK		= 0b_0000_0000_0100_0000_0000_0000,
	NO_SCATTER			= 0b_0000_0000_1000_0000_0000_0000,
	SECRET				= 0b_0000_0001_0000_0000_0000_0000,
	TOP_SECRET			= 0b_0000_0010_0000_0000_0000_0000,
	SILENT				= 0b_0000_0100_0000_0000_0000_0000,
	BLOCKSELECT			= 0b_0000_1000_0000_0000_0000_0000,
	TELEPORT			= 0b_0001_0000_0000_0000_0000_0000,
	NOSTDELETE			= 0b_0010_0000_0000_0000_0000_0000,

	UNKNOWN_BUT_NEEDED	= 0b_1000_0000_0000_0000_0000_0000_0000
}


[Flags]
public enum FLAG_THINGRENDERFLAGS
{
	NONE			= 0,
	HALO			= 0b_0000_0000_0001,
	DONT_RENDER		= 0b_0000_0000_0010,
	DROP_SHADOW		= 0b_0000_0000_0100,
	FULL_SHADOW		= 0b_0000_0000_1000,
	FLUORO_HALO		= 0b_0000_0001_0000,
	TWINKLE			= 0b_0000_0010_0000,
	SORT_RENDER		= 0b_0000_0100_0000,
	REFLECTION_ONLY	= 0b_0000_1000_0000,
	RECREATE		= 0b_0001_0000_0000
}


[Flags]
public enum FLAG_WEAPON_FLAGS
{
	NONE					= 0,
	PROJECTILE				= 0b_0000_0000_0000_0000_0001,
	DISTANCESCALE			= 0b_0000_0000_0000_0000_0010,
	REPEATABLE				= 0b_0000_0000_0000_0000_0100,
	STAKING					= 0b_0000_0000_0000_0000_1000,
	SILVER					= 0b_0000_0000_0000_0001_0000,
	QUICKUSE				= 0b_0000_0000_0000_0010_0000,
	BLADED					= 0b_0000_0000_0000_0100_0000,
	USE_SPECIAL				= 0b_0000_0000_0000_1000_0000,
	SECONDARY_USE_SPECIAL	= 0b_0000_0000_0001_0000_0000,
	BLADE_TRAIL				= 0b_0000_0000_0010_0000_0000,
	CAN_PARRY				= 0b_0000_0000_0100_0000_0000,
	CAN_DODGE				= 0b_0000_0000_1000_0000_0000,
	THROW					= 0b_0000_0001_0000_0000_0000,
	STRIKETRAIL				= 0b_0000_0010_0000_0000_0000,
	SPECIFIC_MODELS			= 0b_0000_0100_0000_0000_0000,

	[Obsolete]
	TWO_HANDED				= 0b_0000_1000_0000_0000_0000,

	NO_STR_BONUS			= 0b_0001_0000_0000_0000_0000
}