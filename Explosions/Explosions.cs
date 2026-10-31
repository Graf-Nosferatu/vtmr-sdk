using Sdk.Templates;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Explosions;

partial class Explosion
{
	public static readonly Explosion
		eyesofthebeast = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 100, 1000 ),
			DYNAINTENSITY( 27000, 0, 3000 ),
			NOP( 30000 )
		),
		eyesofthebeast1 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			DYNAINTENSITY( 57000, 0, 3000 ),
			NOP( 60000 )
		),
		eyesofthebeast2 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			DYNAINTENSITY( 87000, 0, 3000 ),
			NOP( 90000 )
		),
		eyesofthebeast3 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			DYNAINTENSITY( 117000, 0, 3000 ),
			NOP( 120000 )
		),
		eyesofthebeast4 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			DYNAINTENSITY( 147000, 0, 3000 ),
			NOP( 150000 )
		),
		fireball = new(
			DYNAON( 1 ),
			CREATE( 1, 1, 1, 750, Template.incendexplosion, "5", "0,0,-1" ),
			DYNAINTENSITY( 120, 200, 200 ),
			DYNAINTENSITY( 430, 10, 2000 ),
			DYNACOLOR( 430, ColorOf( "rgb(128, 0, 0)" ), 1000 ),
			DYNAOFF( 2450 ),
			NOP( 2500 )
		),
		firestorm = new(
			WALLMARK( 800, 120, 15000 ),
			SETDAMAGE( 800, new( 20, 25 ), ENUM_WEAPON_DAMAGETYPE.FIRE, 80, 500 ),
			NOP( 5000 )
		),
		flamering = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 600, 300 ),
			DYNARADIUS( 1, 256, 300 ),
			SETDAMAGE( 1, new( 10, 30 ), ENUM_WEAPON_DAMAGETYPE.FIRE, 256 ),
			CREATE( 1, 1, 100, 1000, Template.flameringdisc, "5", "0,0,0" ),
			CREATE( 1, 1, 100, 1000, Template.shockflame, "5", "0,0,0" ),
			APPLYDAMAGE( 300 ),
			DYNAINTENSITY( 300, 0, 300 ),
			DYNARADIUS( 300, 0, 300 ),
			DYNAOFF( 600 ),
			NOP( 601 )
		),
		flash = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 600, 300 ),
			DYNARADIUS( 1, 256, 300 ),
			SETDAMAGE( 1, new( 10, 30 ), ENUM_WEAPON_DAMAGETYPE.SUN, 256 ),
			MODELALPHA( 1, 0.1F ),
			MODELALPHA( 1, 1, 100 ),
			MODELSCALE( 1, 0.1F ),
			MODELSCALE( 1, 25, 600 ),
			CREATE( 1, 1, 100, 1000, Template.shockwave, "5", "0,0,0" ),
			APPLYDAMAGE( 300 ),
			DYNAINTENSITY( 300, 0, 300 ),
			DYNARADIUS( 300, 0, 300 ),
			MODELALPHA( 350, 0, 250 ),
			DYNAOFF( 600),
			NOP( 601 )
		),
		grenadeexp = new(
			DYNAON( 1 ),
			MODELALPHA( 1, 0.1F ),
			MODELALPHA( 1, 1, 100 ),
			MODELSCALE( 1, 0.1F ),
			MODELSCALE( 1, 2, 400 ),
			CREATE( 1, 1, 100, 250, Template.grenadeexplosion, "5", "0,0,0" ),
			CREATE( 5, 1, 100, 2000, Template.shockflame, "5", "0,0,0" ),
			WALLMARK( 50, 120, 15000 ),
			SETDAMAGE( 120, new( 30, 1 ), ENUM_WEAPON_DAMAGETYPE.FIRE, 128 ),
			APPLYDAMAGE( 125 ),
			DYNAINTENSITY( 130, 300, 200 ),
			CREATE( 250, 1, 1, 1250, Template.smokeplume, "5", "0,0,-1" ),
			MODELALPHA( 250, 0, 250 ),
			DYNAINTENSITY( 430, 0, 500 ),
			DYNACOLOR( 430, ColorOf( "rgb(128, 0, 0)" ), 1000 ),
			NOP( 2000 )
		),
		hazmatexplosion = new(
			DYNAON( 1 ),
			MODELALPHA( 1, 0.1F ),
			MODELALPHA( 1, 1, 100 ),
			MODELSCALE( 1, 0.1F ),
			MODELSCALE( 1, 2, 400 ),
			CREATE( 1, 1, 100, 250, Template.hazmatexplosion, "5", "0,0,30" ),
			WALLMARK( 50, 120, 15000 ),
			SETDAMAGE( 120, new( 20, 60 ), ENUM_WEAPON_DAMAGETYPE.FIRE, 200 ),
			APPLYDAMAGE( 130 ),
			DYNAINTENSITY( 130, 300, 200 ),
			CREATE( 150, 1, 1, 1250, Template.smokeplume, "5", "0,0,-1" ),
			MODELALPHA( 250, 0, 250 ),
			DYNAINTENSITY( 430, 0, 500 ),
			DYNACOLOR( 430, ColorOf( "rgb(128, 0, 0)" ), 1000 ),
			NOP( 2000 )
		),
		holywaterexp = new(
			DYNAON( 1 ),
			MODELALPHA( 1, 0.1F ),
			MODELALPHA( 1, 1, 100 ),
			MODELSCALE( 1, 0.1F ),
			MODELSCALE( 1, 2, 400 ),
			CREATE( 1, 1, 100, 250, Template.holywaterexplosion, "5", "0,0,0" ),
			CREATE( 5, 1, 1, 1250, Template.whitemist, "5", "0,0,-1" ),
			SETDAMAGE( 120, new( 30, 15 ), ENUM_WEAPON_DAMAGETYPE.FAITH, 120 ),
			APPLYDAMAGE( 125 ),
			DYNAINTENSITY( 130, 300, 200 ),
			MODELALPHA( 250, 0, 250 ),
			DYNAINTENSITY( 430, 0, 500 ),
			DYNACOLOR( 430, ColorOf( "rgb(128, 0, 0)" ), 1000 ),
			NOP( 2000 )
		),
		incinfx = new(
			DYNAON( 1 ),
			CREATE( 10, 1, 1, 300, Template.incinflame, "0", "0,0,0" ),
			DYNAINTENSITY( 10, 300, 200 ),
			DYNACOLOR( 200, ColorOf( "rgb(128, 0, 0)"), 1000 ),
			NOP( 1010 )
		),
		muzzlemagnum = new(
			DYNAON( 1 ),
			CREATE( 1, 1, 1, 100, Template.muzzleflash, "0", "0,0,0" ),
			DYNAINTENSITY( 120, 150, 50 ),
			DYNAINTENSITY( 170, 0, 50 ),
			NOP( 225 )
		),
		muzzlerifle = new(
			DYNAON( 1 ),
			CREATE( 1, 1, 1, 100, Template.muzzleflashrifle, "0", "0,0,0" ),
			DYNAINTENSITY( 120, 150, 50 ),
			DYNAINTENSITY( 170, 0, 50 ),
			NOP( 225 )
		),
		muzzlerocketlauncher = new(
			DYNAON( 1 ),
			CREATE( 1, 1, 1, 100, Template.muzzlerocketlauncher, "0", "0,0,0" ),
			DYNAINTENSITY( 120, 150, 50 ),
			DYNAINTENSITY( 170, 0, 50 ),
			NOP( 225 )
		),
		muzzleshotgun = new(
			DYNAON( 1 ),
			CREATE( 1, 1, 1, 100, Template.muzzleflashshotgun, "0", "0,0,0" ),
			DYNAINTENSITY( 120, 150, 50 ),
			DYNAINTENSITY( 170, 0, 50 ),
			NOP( 225 )
		),
		plaguewind = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			CREATE( 1, 1, 1, 500, Template.plaguewind_flash, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_sec, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_tri, "5", "0,0,2" ),
			SETDAMAGE( 100, new( 5, 6 ), ENUM_WEAPON_DAMAGETYPE.DISEASE, 256, 1000 ),
			DYNAINTENSITY( 2000, 0, 3000 ),
			NOP( 5000 )
		),
		plaguewind1 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			CREATE( 1, 1, 1, 500, Template.plaguewind_flash, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_sec, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_tri, "5", "0,0,2" ),
			SETDAMAGE( 100, new( 5, 10 ), ENUM_WEAPON_DAMAGETYPE.DISEASE, 256, 1000 ),
			DYNAINTENSITY( 2000, 0, 3000 ),
			NOP( 5000 )
		),
		plaguewind2 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			CREATE( 1, 1, 1, 500, Template.plaguewind_flash, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_sec, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_tri, "5", "0,0,2" ),
			SETDAMAGE( 100, new( 5, 15 ), ENUM_WEAPON_DAMAGETYPE.DISEASE, 256, 1000 ),
			DYNAINTENSITY( 2000, 0, 3000 ),
			NOP( 5000 )
		),
		plaguewind3 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			CREATE( 1, 1, 1, 500, Template.plaguewind_flash, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_sec, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_tri, "5", "0,0,2" ),
			SETDAMAGE( 100, new( 10, 20 ), ENUM_WEAPON_DAMAGETYPE.DISEASE, 256, 1000 ),
			DYNAINTENSITY( 2000, 0, 3000 ),
			NOP( 5000 )
		),
		plaguewind4 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			CREATE( 1, 1, 1, 500, Template.plaguewind_flash, "5", "0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_sec, "5"," 0,0,2" ),
			CREATE( 1, 1, 1, 3500, Template.plaguewind_tri, "5", "0,0,2" ),
			SETDAMAGE( 100, new( 15, 25 ), ENUM_WEAPON_DAMAGETYPE.DISEASE, 256, 1000 ),
			DYNAINTENSITY( 2000, 0, 3000 ),
			NOP( 5000 )
		),
		rocketexp = new(
			DYNAON( 1 ),
			MODELALPHA( 1, 0.1F ),
			MODELALPHA( 1, 1, 100 ),
			MODELSCALE( 1, 0.1F ),
			MODELSCALE( 1, 16, 750 ),
			CREATE( 1, 1, 100, 250, Template.rocketexplosion, "5", "0,0,0" ),
			CREATE( 5, 1, 100, 2000, Template.shockflame, "5", "0,0,0" ),
			WALLMARK( 50, 150, 15000 ),
			SETDAMAGE( 120, new( 30, 1 ), ENUM_WEAPON_DAMAGETYPE.FIRE, 200 ),
			APPLYDAMAGE( 125 ),
			DYNAINTENSITY( 130, 300, 200 ),
			MODELALPHA( 500, 0, 250 ),
			DYNAINTENSITY( 430, 0, 500 ),
			DYNACOLOR( 430, ColorOf( "rgb(128, 0, 0)" ), 1000 ),
			CREATE( 750, 1, 1, 1250, Template.smokeplume, "5", "0,0,-1" ),
			NOP( 2000 )
		),
		smokepuff = new(
			CREATE( 20, 1, 1, 1000, Template.smokepuff, "0", "0,0,0" ),
			NOP( 100 )
		),
		smokepuffs = new(
			CREATE( 20, 10, 0.9F, 1000, Template.smokepuff, "r30", "r100,r100,r100" ),
			NOP( 100 )
		),
		throwerfx = new(
			DYNAON( 1 ),
			CREATE( 10, 1, 1, 1000, Template.throwerflame, "0", "0,0,0" ),
			DYNAINTENSITY( 10, 300, 200 ),
			DYNACOLOR( 430, ColorOf( "rgb(128, 0, 0)" ), 1000 ),
			NOP( 1010 )
		),
		truefaith = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 100, 1000 ),
			DYNARADIUS( 1, 64, 1000 ),
			SETDAMAGE( 1000, new( 5, 3 ), ENUM_WEAPON_DAMAGETYPE.FAITH, 100, 1000 ),
			DYNAINTENSITY( 2000, 0, 500 ),
			NOP( 2500 )
		),
		truefaith1 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			//DYNARADIUS( 1, 64, 1000 ),
			SETDAMAGE( 1000, new( 7, 5 ), ENUM_WEAPON_DAMAGETYPE.FAITH, 100, 1000 ),
			DYNAINTENSITY( 4500, 0, 500 ),
			NOP( 5000 )
		),
		truefaith2 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			//DYNARADIUS( 1, 64, 1000 ),
			SETDAMAGE( 1000, new( 10, 5 ), ENUM_WEAPON_DAMAGETYPE.FAITH, 150, 1000 ),
			DYNAINTENSITY( 7000, 0, 500 ),
			NOP( 7500 )
		),
		truefaith3 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			//DYNARADIUS( 1, 64, 1000 ),
			SETDAMAGE( 1000, new( 12, 5 ), ENUM_WEAPON_DAMAGETYPE.FAITH, 150, 1000 ),
			DYNAINTENSITY( 9500, 0, 500 ),
			NOP( 10000 )
		),
		truefaith4 = new(
			DYNAON( 1 ),
			DYNAINTENSITY( 1, 300, 1000 ),
			//DYNARADIUS( 1, 64, 1000 ),
			SETDAMAGE( 1000, new( 15, 5 ), ENUM_WEAPON_DAMAGETYPE.FAITH, 150, 1000 ),
			DYNAINTENSITY( 12000, 0, 500 ),
			NOP( 12500 )
		);
}