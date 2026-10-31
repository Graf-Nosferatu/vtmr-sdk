using System;
using System.IO;
using System.Runtime.CompilerServices;
using static Sdk.Extensions.CallerArgumentExtensions;

namespace Sdk.Explosions;

partial class Explosion
{
	public const String DirectoryName = "misc\\explosions";


	public static void Save()
	{
		SaveExplosion( eyesofthebeast );
		SaveExplosion( eyesofthebeast1 );
		SaveExplosion( eyesofthebeast2 );
		SaveExplosion( eyesofthebeast3 );
		SaveExplosion( eyesofthebeast4 );
		SaveExplosion( fireball );
		SaveExplosion( firestorm );
		SaveExplosion( flamering );
		SaveExplosion( flash );
		SaveExplosion( grenadeexp );
		SaveExplosion( hazmatexplosion );
		SaveExplosion( holywaterexp );
		SaveExplosion( incinfx );
		SaveExplosion( muzzlemagnum );
		SaveExplosion( muzzlerifle );
		SaveExplosion( muzzlerocketlauncher );
		SaveExplosion( muzzleshotgun );
		SaveExplosion( plaguewind );
		SaveExplosion( plaguewind1 );
		SaveExplosion( plaguewind2 );
		SaveExplosion( plaguewind3 );
		SaveExplosion( plaguewind4 );
		SaveExplosion( rocketexp );
		SaveExplosion( smokepuff );
		SaveExplosion( smokepuffs );
		SaveExplosion( throwerfx );
		SaveExplosion( truefaith );
		SaveExplosion( truefaith1 );
		SaveExplosion( truefaith2 );
		SaveExplosion( truefaith3 );
		SaveExplosion( truefaith4 );
	}


	private static void SaveExplosion( Explosion explosion, [CallerArgumentExpression( nameof( explosion ) )] String name = "" )
	{
		String path = Path.Create( Sdk.ModPath, DirectoryName, FileNameOf( explosion, name ) );
		File.WriteAllLines( path, explosion );
	}
}