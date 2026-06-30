using System;

namespace Sdk.Templates;

public enum Tag
{
	/// <summary> STRING <br/> The parent of a template. <br/> 0000 </summary>
	PARENT = 0,
	/// <summary> STRING <br/> The stringID of this thing description. <br/> 0005 </summary>
	DESCRIPTIONID = 82,
	/// <summary> ENUM_TYPE <br/> The type of this template. <br/> 0010 </summary>
	TYPE = 16,
	/// <summary> UINT <br/> The timer of template. <br/> 0090 </summary>
	TIMER = 83,
	/// <summary> TEMPLATE <br/> The template to replace the destroyed template with. <br/> 0093 </summary>
	DESTROY_TEMPLATE = 117,
	/// <summary> FLAG_THINGFLAGS <br/> The thing flags of a template. <br/> 0100 </summary>
	THINGFLAGS = 7,
	/// <summary> ENUM_COLLIDETYPE <br/> The collidetype of an object. <br/> 0110 </summary>
	COLLIDETYPE = 12,
	/// <summary> UFLOAT <br/> The radius of an object. <br/> 0120 </summary>
	RADIUS = 13,
	/// <summary> UFLOAT <br/> The height of an object. <br/> 0121 </summary>
	HEIGHT = 14,
	/// <summary> ENUM_MOVETYPE <br/> The movetype of an object. <br/> 0140 </summary>
	MOVETYPE = 78,
	/// <summary> UFLOAT <br/> The movespeed of an object. <br/> 0150 </summary>
	MOVESPEED = 15,
	/// <summary> FLAG_THINGRENDERFLAGS <br/> The thing render flags of a template. <br/> 1000 </summary>
	THINGRENDERFLAGS = 8,
	/// <summary> FILENAME <br/> The model of a template. <br/> 1010 </summary>
	MODEL = 4,
	/// <summary> FLAG_MODELFLAGS <br/> The model flags of a template. <br/> 1020 </summary>
	MODELFLAGS = 10,
	/// <summary> UFLOAT <br/> A scale for this model. <br/> 1021 </summary>
	MODELSCALE = 235,
	/// <summary> FILENAME <br/> The foley of a template. <br/> 1030 </summary>
	FOLEY = 6,
	/// <summary> ENUM_SOUNDTYPE <br/> The soundtype of an object. <br/> 1035 </summary>
	SOUNDTYPE = 110,
	/// <summary> FILENAME <br/> The motion of a template. <br/> 1040 </summary>
	MOTIONSET = 9,
	/// <summary> FILENAMENOEXT <br/> The class script of a template. <br/> 2000 </summary>
	SCRIPT = 11,
	/// <summary> UINT <br/> The skeleton ID of the template. <br/> 2010 </summary>
	SKELETONID = 118,
	/// <summary> STRING <br/> The particle reference of a template. <br/> 2020 </summary>
	PARTICLENAME = 119,
	/// <summary> UFLOAT <br/> The intensity of the dynamic light. <br/> 2100 </summary>
	DYNA_INTENSITY = 121,
	/// <summary> FILENAMENOEXT <br/> The material of the dynamic light. <br/> 2110 </summary>
	DYNA_MATERIAL = 122,
	/// <summary> RGB <br/> The color of the dynamic light blob. <br/> 2120 </summary>
	DYNA_COLOR = 123,
	/// <summary> SINT <br/> The bone of the dynamic light (-1 for no bone). <br/> 2130 </summary>
	DYNA_BONE = 124,
	/// <summary> VECTOR <br/> The offset of the dynamic light blob. <br/> 2135 </summary>
	DYNA_OFFSET = 135,
	/// <summary> FLAG_DYNAFLAGS <br/> The flags of the dynamic light. <br/> 2140 </summary>
	DYNA_FLAGS = 125,
	/// <summary> ENUM_DYNA_STYLE <br/> The style of this dynamic light. <br/> 2150 </summary>
	DYNA_STYLE = 126,
	/// <summary> UFLOAT <br/> The radius of the dynamic light. <br/> 2160 </summary>
	DYNA_RADIUS = 127,
	/// <summary> TEMPLATE <br/> The template to create when this template explodes. <br/> 2200 </summary>
	EXPLOSION = 128,
	/// <summary> UFLOAT <br/> The radius at which an object will trigger a touched message. <br/> 2350 </summary>
	PROXRADIUS = 234,
	/// <summary> STRING <br/> The wall mark for a projectile or explosion. <br/> 2360 </summary>
	WALLMARK = 244,
	/// <summary> ENUM_ICONSIZE <br/> The size of the icon. <br/> 3000 </summary>
	ICONSIZE = 17,
	/// <summary> ENUM_ICONTYPE <br/> The type of the icon (OBSOLETE) <br/> 3010 </summary>
	ICONTYPE = 18,
	/// <summary> ENUM_ITEM_TYPE <br/> The item type. <br/> 3020 </summary>
	ITEM_TYPE = 20,
	/// <summary> FILENAMENOEXT <br/> The icon of a pickable item. <br/> 3030 </summary>
	ICON = 19,
	/// <summary> FLAG_ITEM_FLAGS <br/> The item flags. <br/> 3040 </summary>
	ITEM_FLAGS = 21,
	/// <summary> STRING <br/> The identified description of the item. <br/> 3042 </summary>
	ITEM_IDENTIFIEDDESC = 33,
	/// <summary> STRING <br/> The special description of the item. <br/> 3044 </summary>
	ITEM_SPECIALDESC = 34,
	/// <summary> UINT <br/> The amount of the item. <br/> 3050 </summary>
	ITEM_AMOUNT = 22,
	/// <summary> UINT <br/> The id of the item (used for ammo type). <br/> 3060 </summary>
	ITEM_ID = 24,
	/// <summary> ENUM_ITEM_TAG <br/> The placement tag of the item (used for utility items). <br/> 3065 </summary>
	ITEM_TAG = 25,
	/// <summary> STRING <br/> The discipline of the scroll. <br/> 3066 </summary>
	ITEM_SCROLLDISC = 28,
	/// <summary> UINT <br/> The level of the scroll. <br/> 3067 </summary>
	ITEM_SCROLLLEVEL = 29,
	/// <summary> UINT <br/> The base cost of the item <br/> 3070 </summary>
	ITEM_BASECOST = 90,
	/// <summary> FLAG_ITEM_SELLFLAGS <br/> The item sell flags. <br/> 3080 </summary>
	ITEM_SELLFLAGS = 120,
	/// <summary> UINT <br/> The minimum perception to automatically identify the item. <br/> 3090 </summary>
	ITEM_MINPERCEPTION = 23,
	/// <summary> STRING <br/> The text associated with the item (books, etc). <br/> 3095 </summary>
	ITEM_TEXT = 30,
	/// <summary> STRING <br/> The armor model. <br/> 3100 </summary>
	ARMOR_MODEL = 79,
	/// <summary> ENUM_ARMOR_TYPE <br/> The armor type. <br/> 3110 </summary>
	ARMOR_TYPE = 80,
	/// <summary> ENUM_WEAPON_TYPE <br/> The type of a weapon. <br/> 3300 </summary>
	WEAPON_TYPE = 40,
	/// <summary> FLAG_WEAPON_FLAGS <br/> The weapon flags. <br/> 3310 </summary>
	WEAPON_FLAGS = 39,
	/// <summary> STRING <br/> The model of a weapon when carried by a player. <br/> 3315 </summary>
	WEAPON_MODEL = 51,
	/// <summary> VECTOR <br/> The firing position of the weapon. <br/> 3320 </summary>
	WEAPON_FIREPOS = 95,
	/// <summary> VECTOR <br/> The blade start pos of the weapon. <br/> 3322 </summary>
	WEAPON_BLADESTART = 240,
	/// <summary> VECTOR <br/> The blade end pos of the weapon. <br/> 3324 </summary>
	WEAPON_BLADEEND = 241,
	/// <summary> STRING <br/> The blade trail of the weapon. <br/> 3326 </summary>
	WEAPON_TRAIL = 242,
	/// <summary> ENUM_WEAPON_DEFENSETYPE <br/> The defense type of a weapon. <br/> 3350 </summary>
	WEAPON_DEFENSETYPE = 42,
	/// <summary> UINT <br/> The weapon throw type. <br/> 3360 </summary>
	WEAPON_THROWTYPE = 245,
	/// <summary> UFLOAT <br/> The weapon throw distance. <br/> 3362 </summary>
	WEAPON_THROWDIST = 247,
	/// <summary> UFLOAT <br/> The weapon throw chance. <br/> 3364 </summary>
	WEAPON_THROWCHANCE = 248,
	/// <summary> ENUM_WEAPON_MELEETYPE <br/> The melee type of a weapon. <br/> 3400 </summary>
	WEAPON_MELEETYPE = 41,
	/// <summary> FLAG_WEAPON_FLAGS <br/> The weapon flags1. <br/> 3402 </summary>
	WEAPON_FLAGS1 = 238,
	/// <summary> UINT <br/> The first damage of the weapon. <br/> 3405 </summary>
	WEAPON_DAMAGE1 = 43,
	/// <summary> ENUM_WEAPON_DAMAGETYPE <br/> The first damage type of the weapon. <br/> 3410 </summary>
	WEAPON_DAMAGETYPE1 = 131,
	/// <summary> UFLOAT <br/> The first strike time of the weapon. <br/> 3415 </summary>
	WEAPON_STRIKETIME1 = 45,
	/// <summary> UFLOAT <br/> The reload time of the weapon. <br/> 3420 </summary>
	WEAPON_RELOADTIME = 47,
	/// <summary> UFLOAT <br/> The min distance of the weapon. <br/> 3425 </summary>
	WEAPON_MINDISTANCE = 48,
	/// <summary> UFLOAT <br/> The max distance of the weapon. <br/> 3430 </summary>
	WEAPON_MAXDISTANCE = 49,
	/// <summary> TEMPLATE <br/> The projectile template for the weapon. <br/> 3435 </summary>
	WEAPON_PROJECTILE = 50,
	/// <summary> TEMPLATE <br/> The template to create as a muzzle flash <br/> 3440 </summary>
	WEAPON_MUZZLEFLASH1 = 133,
	/// <summary> UINT <br/> The ammo type 1 of the weapon. <br/> 3445 </summary>
	WEAPON_AMMOTYPE1 = 158,
	/// <summary> UINT <br/> The max ammo 1 of the weapon. <br/> 3450 </summary>
	WEAPON_MAXAMMO1 = 160,
	/// <summary> UINT <br/> The ammo use 1 of the weapon. <br/> 3455 </summary>
	WEAPON_AMMOUSE1 = 227,
	/// <summary> UFLOAT <br/> The accuracy of the weapon. <br/> 3460 </summary>
	WEAPON_ACCURACY1 = 236,
	/// <summary> ENUM_WEAPON_MELEETYPE <br/> The melee type 2 of a weapon. <br/> 3500 </summary>
	WEAPON_MELEETYPE2 = 226,
	/// <summary> FLAG_WEAPON_FLAGS <br/> The weapon flags2. <br/> 3502 </summary>
	WEAPON_FLAGS2 = 239,
	/// <summary> UINT <br/> The second damage of the weapon. <br/> 3505 </summary>
	WEAPON_DAMAGE2 = 44,
	/// <summary> ENUM_WEAPON_DAMAGETYPE <br/> The second damage type of the weapon. <br/> 3510 </summary>
	WEAPON_DAMAGETYPE2 = 132,
	/// <summary> UFLOAT <br/> The second strike time of the weapon. <br/> 3515 </summary>
	WEAPON_STRIKETIME2 = 46,
	/// <summary> UFLOAT <br/> The reload time 2 of the weapon. <br/> 3520 </summary>
	WEAPON_RELOADTIME2 = 162,
	/// <summary> UFLOAT <br/> The min distance 2 of the weapon. <br/> 3525 </summary>
	WEAPON_MINDISTANCE2 = 164,
	/// <summary> UFLOAT <br/> The max distance 2 of the weapon. <br/> 3530 </summary>
	WEAPON_MAXDISTANCE2 = 165,
	/// <summary> TEMPLATE <br/> The projectile 2 template for the weapon. <br/> 3535 </summary>
	WEAPON_PROJECTILE2 = 163,
	/// <summary> TEMPLATE <br/> The template to create as a muzzle flash <br/> 3540 </summary>
	WEAPON_MUZZLEFLASH2 = 134,
	/// <summary> UINT <br/> The ammo type 2 of the weapon. <br/> 3545 </summary>
	WEAPON_AMMOTYPE2 = 159,
	/// <summary> UINT <br/> The max ammo 2 of the weapon. <br/> 3550 </summary>
	WEAPON_MAXAMMO2 = 161,
	/// <summary> UINT <br/> The ammo use 2 of the weapon. <br/> 3555 </summary>
	WEAPON_AMMOUSE2 = 228,
	/// <summary> UFLOAT <br/> The accuracy of the weapon. <br/> 3560 </summary>
	WEAPON_ACCURACY2 = 237,
	/// <summary> STRING <br/> Effect name 0 <br/> 3600 </summary>
	ITEM_EFFECTNAME0 = 166,
	/// <summary> SINT <br/> Effect level 0 <br/> 3601 </summary>
	ITEM_EFFECTLEVEL0 = 167,
	/// <summary> FLOAT <br/> Effect value 0 <br/> 3602 </summary>
	ITEM_EFFECTVALUE0 = 168,
	/// <summary> UINT <br/> Effect duration 0 <br/> 3603 </summary>
	ITEM_EFFECTTIME0 = 169,
	/// <summary> STRING <br/> Effect name 1 <br/> 3610 </summary>
	ITEM_EFFECTNAME1 = 170,
	/// <summary> SINT <br/> Effect level 1 <br/> 3611 </summary>
	ITEM_EFFECTLEVEL1 = 171,
	/// <summary> FLOAT <br/> Effect value 1 <br/> 3612 </summary>
	ITEM_EFFECTVALUE1 = 172,
	/// <summary> UINT <br/> Effect duration 1 <br/> 3613 </summary>
	ITEM_EFFECTTIME1 = 173,
	/// <summary> STRING <br/> Effect name 2 <br/> 3620 </summary>
	ITEM_EFFECTNAME2 = 174,
	/// <summary> SINT <br/> Effect level 2 <br/> 3621 </summary>
	ITEM_EFFECTLEVEL2 = 175,
	/// <summary> FLOAT <br/> Effect value 2 <br/> 3622 </summary>
	ITEM_EFFECTVALUE2 = 176,
	/// <summary> UINT <br/> Effect duration 2 <br/> 3623 </summary>
	ITEM_EFFECTTIME2 = 177,
	/// <summary> STRING <br/> Effect name 3 <br/> 3630 </summary>
	ITEM_EFFECTNAME3 = 178,
	/// <summary> SINT <br/> Effect level 3 <br/> 3631 </summary>
	ITEM_EFFECTLEVEL3 = 179,
	/// <summary> FLOAT <br/> Effect value 3 <br/> 3632 </summary>
	ITEM_EFFECTVALUE3 = 180,
	/// <summary> UINT <br/> Effect duration 3 <br/> 3633 </summary>
	ITEM_EFFECTTIME3 = 181,
	/// <summary> STRING <br/> Effect name 4 <br/> 3640 </summary>
	ITEM_EFFECTNAME4 = 182,
	/// <summary> SINT <br/> Effect level 4 <br/> 3641 </summary>
	ITEM_EFFECTLEVEL4 = 183,
	/// <summary> FLOAT <br/> Effect value 4 <br/> 3642 </summary>
	ITEM_EFFECTVALUE4 = 184,
	/// <summary> UINT <br/> Effect duration 4 <br/> 3643 </summary>
	ITEM_EFFECTTIME4 = 185,
	/// <summary> STRING <br/> Effect name 5 <br/> 3650 </summary>
	ITEM_EFFECTNAME5 = 186,
	/// <summary> SINT <br/> Effect level 5 <br/> 3651 </summary>
	ITEM_EFFECTLEVEL5 = 187,
	/// <summary> FLOAT <br/> Effect value 5 <br/> 3652 </summary>
	ITEM_EFFECTVALUE5 = 188,
	/// <summary> UINT <br/> Effect duration 5 <br/> 3653 </summary>
	ITEM_EFFECTTIME5 = 189,
	/// <summary> STRING <br/> Effect name 6 <br/> 3660 </summary>
	ITEM_EFFECTNAME6 = 190,
	/// <summary> SINT <br/> Effect level 6 <br/> 3661 </summary>
	ITEM_EFFECTLEVEL6 = 191,
	/// <summary> FLOAT <br/> Effect value 6 <br/> 3662 </summary>
	ITEM_EFFECTVALUE6 = 192,
	/// <summary> UINT <br/> Effect duration 6 <br/> 3663 </summary>
	ITEM_EFFECTTIME6 = 193,
	/// <summary> STRING <br/> Effect name 7 <br/> 3670 </summary>
	ITEM_EFFECTNAME7 = 194,
	/// <summary> SINT <br/> Effect level 7 <br/> 3671 </summary>
	ITEM_EFFECTLEVEL7 = 195,
	/// <summary> FLOAT <br/> Effect value 7 <br/> 3672 </summary>
	ITEM_EFFECTVALUE7 = 196,
	/// <summary> UINT <br/> Effect duration 7 <br/> 3673 </summary>
	ITEM_EFFECTTIME7 = 197,
	/// <summary> STRING <br/> Effect name 8 <br/> 3680 </summary>
	ITEM_EFFECTNAME8 = 198,
	/// <summary> SINT <br/> Effect level 8 <br/> 3681 </summary>
	ITEM_EFFECTLEVEL8 = 199,
	/// <summary> FLOAT <br/> Effect value 8 <br/> 3682 </summary>
	ITEM_EFFECTVALUE8 = 200,
	/// <summary> UINT <br/> Effect duration 8 <br/> 3683 </summary>
	ITEM_EFFECTTIME8 = 201,
	/// <summary> STRING <br/> Effect name 9 <br/> 3690 </summary>
	ITEM_EFFECTNAME9 = 202,
	/// <summary> SINT <br/> Effect level 9 <br/> 3691 </summary>
	ITEM_EFFECTLEVEL9 = 203,
	/// <summary> FLOAT <br/> Effect value 9 <br/> 3692 </summary>
	ITEM_EFFECTVALUE9 = 204,
	/// <summary> UINT <br/> Effect duration 9 <br/> 3693 </summary>
	ITEM_EFFECTTIME9 = 205,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 0 <br/> 3700 </summary>
	ITEM_RESTRTYPE0 = 206,
	/// <summary> FLOAT <br/> Item restriction value 0 <br/> 3701 </summary>
	ITEM_RESTRVALUE0 = 207,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 1 <br/> 3710 </summary>
	ITEM_RESTRTYPE1 = 208,
	/// <summary> FLOAT <br/> Item restriction value 1 <br/> 3711 </summary>
	ITEM_RESTRVALUE1 = 209,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 2 <br/> 3720 </summary>
	ITEM_RESTRTYPE2 = 210,
	/// <summary> FLOAT <br/> Item restriction value 2 <br/> 3721 </summary>
	ITEM_RESTRVALUE2 = 211,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 3 <br/> 3730 </summary>
	ITEM_RESTRTYPE3 = 212,
	/// <summary> FLOAT <br/> Item restriction value 3 <br/> 3731 </summary>
	ITEM_RESTRVALUE3 = 213,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 4 <br/> 3740 </summary>
	ITEM_RESTRTYPE4 = 214,
	/// <summary> FLOAT <br/> Item restriction value 4 <br/> 3741 </summary>
	ITEM_RESTRVALUE4 = 215,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 5 <br/> 3750 </summary>
	ITEM_RESTRTYPE5 = 216,
	/// <summary> FLOAT <br/> Item restriction value 5 <br/> 3751 </summary>
	ITEM_RESTRVALUE5 = 217,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 6 <br/> 3760 </summary>
	ITEM_RESTRTYPE6 = 218,
	/// <summary> FLOAT <br/> Item restriction value 6 <br/> 3761 </summary>
	ITEM_RESTRVALUE6 = 219,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 7 <br/> 3770 </summary>
	ITEM_RESTRTYPE7 = 220,
	/// <summary> FLOAT <br/> Item restriction value 7 <br/> 3771 </summary>
	ITEM_RESTRVALUE7 = 221,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 8 <br/> 3780 </summary>
	ITEM_RESTRTYPE8 = 222,
	/// <summary> FLOAT <br/> Item restriction value 8 <br/> 3781 </summary>
	ITEM_RESTRVALUE8 = 223,
	/// <summary> ENUM_ITEM_RESTRTYPE <br/> Item restriction type 9 <br/> 3790 </summary>
	ITEM_RESTRTYPE9 = 224,
	/// <summary> FLOAT <br/> Item restriction value 9 <br/> 3791 </summary>
	ITEM_RESTRVALUE9 = 225,
	/// <summary> STRING <br/> The stringID of this actor cast name. <br/> 4005 </summary>
	CASTID = 81,
	/// <summary> FLAG_ACTOR_FLAGS <br/> The actor flags. <br/> 4007 </summary>
	ACTOR_FLAGS = 114,
	/// <summary> ENUM_ACTOR_TYPE <br/> The type of actor. <br/> 4008 </summary>
	ACTOR_TYPE = 138,
	/// <summary> STRINGLIST <br/> The clan of actor. <br/> 4009 </summary>
	ACTOR_CLAN = 26,
	/// <summary> ENUM_ACTOR_TEAM <br/> The team of actor. <br/> 4010 </summary>
	ACTOR_TEAM = 249,
	/// <summary> ENUM_ACTOR_AURA <br/> The aura of the actor. <br/> 4013 </summary>
	ACTOR_AURA = 27,
	/// <summary> ENUM_ACTOR_SIZE <br/> The size of the actor. <br/> 4014 </summary>
	ACTOR_SIZE = 139,
	/// <summary> UINT <br/> The starting health of an actor. <br/> 4015 </summary>
	ACTOR_HEALTH = 52,
	/// <summary> UINT <br/> The max health of an actor when fully healed. <br/> 4016 </summary>
	ACTOR_MAXHEALTH = 60,
	/// <summary> UFLOAT <br/> The walking speed of an actor. <br/> 4020 </summary>
	ACTOR_WALKSPEED = 54,
	/// <summary> UFLOAT <br/> The running speed of an actor. <br/> 4025 </summary>
	ACTOR_RUNSPEED = 55,
	/// <summary> UINT <br/> The XP worth of an actor. <br/> 4050 </summary>
	ACTOR_XP = 53,
	/// <summary> TEMPLATE <br/> The item dropped when the thing dies. <br/> 4053 </summary>
	DROPITEM = 115,
	/// <summary> UINT <br/> The TREASURECLASS of a thing. <br/> 4055 </summary>
	TREASURECLASS = 92,
	/// <summary> STRING <br/> The RPG stats of a player. <br/> 4060 </summary>
	ACTOR_RPGSTATS = 59,
	/// <summary> FILENAME <br/> The AI file of an actor. <br/> 4070 </summary>
	ACTOR_AI = 56,
	/// <summary> TEMPLATE <br/> The weapon of an actor. <br/> 4075 </summary>
	ACTOR_WEAPON = 93,
	/// <summary> VECTOR <br/> The firing position of the actor. <br/> 4080 </summary>
	ACTOR_FIREPOS = 94,
	/// <summary> FILENAME <br/> The head model of an actor. <br/> 4100 </summary>
	ACTOR_HEADMODEL = 57,
	/// <summary> FILENAME <br/> The head motion of an actor. <br/> 4110 </summary>
	ACTOR_HEADMOTION = 58,
	/// <summary> FLOAT <br/> The strength of an actor. <br/> 4200 </summary>
	STAT_STRENGTH = 99,
	/// <summary> FLOAT <br/> The dexterity of an actor. <br/> 4211 </summary>
	STAT_DEXTERITY = 100,
	/// <summary> FLOAT <br/> The stamina of an actor. <br/> 4212 </summary>
	STAT_STAMINA = 101,
	/// <summary> FLOAT <br/> The perception of an actor. <br/> 4213 </summary>
	STAT_PERCEPTION = 102,
	/// <summary> FLOAT <br/> The intelligence of an actor. <br/> 4214 </summary>
	STAT_INTELLIGENCE = 103,
	/// <summary> FLOAT <br/> The wits of an actor. <br/> 4215 </summary>
	STAT_WITS = 104,
	/// <summary> FLOAT <br/> The blood of an actor. <br/> 4216 </summary>
	STAT_BLOOD = 105,
	/// <summary> FLOAT <br/> The humanity of an actor. <br/> 4217 </summary>
	STAT_HUMANITY = 106,
	/// <summary> FLOAT <br/> The frenzy of an actor. <br/> 4218 </summary>
	STAT_FRENZY = 107,
	/// <summary> FLOAT <br/> The generation of an actor. <br/> 4219 </summary>
	STAT_GENERATION = 108,
	/// <summary> FLOAT <br/> The faith of an actor. <br/> 4220 </summary>
	STAT_FAITH = 109,
	/// <summary> FLOAT <br/> The charisma of an actor. <br/> 4221 </summary>
	STAT_CHARISMA = 142,
	/// <summary> FLOAT <br/> The manipulation of an actor. <br/> 4222 </summary>
	STAT_MANIPULATION = 143,
	/// <summary> FLOAT <br/> The appearance of an actor. <br/> 4223 </summary>
	STAT_APPEARANCE = 144,
	/// <summary> FLOAT <br/> The frenzy rating of an actor. <br/> 4224 </summary>
	STAT_FRENZYRATING = 145,
	/// <summary> FLOAT <br/> The blood rating of an actor. <br/> 4225 </summary>
	STAT_BLOODRATING = 146,
	/// <summary> FLOAT <br/> The size of the bloodpool. <br/> 4226 </summary>
	STAT_BLOODPOOL = 147,
	/// <summary> FLOAT <br/> The mana of an actor. <br/> 4227 </summary>
	STAT_MANA = 31,
	/// <summary> FLOAT <br/> The manapool of an actor. <br/> 4228 </summary>
	STAT_MANAPOOL = 32,
	/// <summary> FLOAT <br/> The soak for normal damage. <br/> 4230 </summary>
	SOAK_NORMAL = 148,
	/// <summary> FLOAT <br/> The soak for lethal damage. <br/> 4231 </summary>
	SOAK_LETHAL = 149,
	/// <summary> FLOAT <br/> The soak for aggravated damage. <br/> 4232 </summary>
	SOAK_AGGRAVATED = 150,
	/// <summary> FLOAT <br/> The soak for electrical damage. <br/> 4233 </summary>
	SOAK_ELECTRIC = 151,
	/// <summary> FLOAT <br/> The soak for fire damage. <br/> 4234 </summary>
	SOAK_FIRE = 152,
	/// <summary> FLOAT <br/> The soak for sun damage. <br/> 4235 </summary>
	SOAK_SUN = 153,
	/// <summary> FLOAT <br/> The soak for faith damage. <br/> 4236 </summary>
	SOAK_FAITH = 154,
	/// <summary> FLOAT <br/> The soak for cold damage. <br/> 4237 </summary>
	SOAK_COLD = 155,
	/// <summary> FLOAT <br/> The soak for poison damage. <br/> 4238 </summary>
	SOAK_POISON = 156,
	/// <summary> FLOAT <br/> The soak for disease damage. <br/> 4239 </summary>
	SOAK_DISEASE = 157,
	/// <summary> UINT <br/> Gib class. <br/> 4270 </summary>
	ACTOR_GIBCLASS = 246,
	/// <summary> FLAG_PROJECT_FLAGS <br/> The projectile flags of a template. <br/> 7000 </summary>
	PROJECT_FLAGS = 84,
	/// <summary> UINT <br/> The damage of a projectile. <br/> 7010 </summary>
	PROJECT_DAMAGE = 85,
	/// <summary> ENUM_PROJECT_DAMAGETYPE <br/> The damage type of a projectile. <br/> 7020 </summary>
	PROJECT_DAMAGETYPE = 86,
	/// <summary> UFLOAT <br/> The max distance for a projectile. <br/> 7030 </summary>
	PROJECT_MAXDIST = 136,
	/// <summary> FLAG_PHYSICS_FLAGS <br/> The physics flags of a template. <br/> 7100 </summary>
	PHYSICS_FLAGS = 87,
	/// <summary> VECTOR <br/> The initial velocity. <br/> 7110 </summary>
	PHYSICS_VELOCITY = 88,
	/// <summary> VECTOR <br/> The initial rotational velocity. <br/> 7120 </summary>
	PHYSICS_ROTVEL = 89,
	/// <summary> UFLOAT <br/> The velocity drag. <br/> 7130 </summary>
	PHYSICS_VELDRAG = 229,
	/// <summary> UFLOAT <br/> The rotational drag. <br/> 7140 </summary>
	PHYSICS_ROTDRAG = 230,
	/// <summary> UFLOAT <br/> The mass. <br/> 7150 </summary>
	PHYSICS_MASS = 231,
	/// <summary> FLOAT <br/> The gravity for this object. <br/> 7155 </summary>
	PHYSICS_GRAVITY = 243,
	/// <summary> UFLOAT <br/> The elasticity. <br/> 7160 </summary>
	PHYSICS_ELASTICITY = 232,
	/// <summary> VECTOR <br/> The thrust. <br/> 7170 </summary>
	PHYSICS_THRUST = 233,
	/// <summary> FLAG_REGION_FLAGS <br/> The region flags of a template. <br/> 7200 </summary>
	REGION_FLAGS = 91,
	/// <summary> FILENAMENOEXT <br/> The material of the halo. <br/> 7300 </summary>
	HALOMATERIAL = 96,
	/// <summary> UFLOAT <br/> The size of the halo. <br/> 7310 </summary>
	HALOSIZE = 97,
	/// <summary> RGB <br/> The color of the halo. <br/> 7320 </summary>
	HALOCOLOR = 98,
	/// <summary> FILENAME <br/> The name of the explosion file for this template. <br/> 7500 </summary>
	EXPLOSION_NAME = 129,
	/// <summary> FLAG_EXPLOSION_FLAGS <br/> The explosion flags. <br/> 7510 </summary>
	EXPLOSION_FLAGS = 130,
	/// <summary> UINT <br/> The damage a container can take. <br/> 8020 </summary>
	CONTAINER_HEALTH = 116,
	/// <summary> ENUM_EDITORCATEGORY <br/> The editor category of the item <br/> 9000 </summary>
	EDITORCATEGORY = 5,
	/// <summary> VECTOR <br/> Mins vector for the editor <br/> 9010 </summary>
	EDITORSIZEMINS = 35,
	/// <summary> VECTOR <br/> Maxs vector for the editor <br/> 9020 </summary>
	EDITORSIZEMAXS = 36,
	/// <summary> RGB <br/> Editor color <br/> 9030 </summary>
	EDITORCOLOR = 37,
	/// <summary> STRING <br/> The editor subcategory (free form text). <br/> 9040 </summary>
	EDITORSUBCATEGORY = 137
}

[AttributeUsage( AttributeTargets.Property )]
public class TagAttribute( Tag id, Int32 order ) : Attribute
{
	public Tag Id { get; } = id;

	public Int32 Order { get; } = order;
}