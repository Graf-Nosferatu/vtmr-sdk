using System;
using System.IO;
using System.Linq;

namespace Sdk.Stores;

partial class Store
{
	public const String DirectoryName = "stores";


	public static readonly File<Store>
		PragueSmithy = new( "PragueSmithy.nbs" ), UnornaShop = new( "UnornaShop.nbs" ), UnornaVampireShop = new( "UnornaVampireShop.nbs" ),
		WeaponSmith = new( "WeaponSmith.nbs" ), Orvus = new( "Orvus.nbs" ),
		OttoVan = new( "OttoVan.nbs" ), CurioShop = new( "CurioShop.nbs" ), ClubTen = new( "ClubTen.nbs" ),
		GunHaven = new( "GunHaven.nbs" ), NewMoon = new( "NewMoon.nbs" ),
		SorvenaShop = new( "SorvenaShop.nbs" ),
		Generic_DA_weapon = new( "generic_DA_weapon.nbs" ), Generic_DA_magic = new( "generic_DA_magic.nbs" ),
		Generic_MD_weapon = new( "generic_MD_weapon.nbs" ), Generic_MD_magic = new( "generic_MD_magic.nbs" );


	private static readonly File<Store>[] Files = [
		PragueSmithy, UnornaShop, UnornaVampireShop,
		WeaponSmith, Orvus,
		OttoVan, CurioShop, ClubTen,
		GunHaven, NewMoon,
		SorvenaShop,
		Generic_DA_weapon, Generic_DA_magic,
		Generic_MD_weapon, Generic_MD_magic
	];


	public static void Save()
	{
		foreach( var file in Files )
		{
			String path = Path.Create( Sdk.ModPath, DirectoryName, file.FileName );
			File.WriteAllLines( path, file.Select( SaveStore ) );
		}
	}

	private static String SaveStore( Store store )
	{
		if( store.TemplateName != null ) {
			return $"D {store.TemplateName} {store.Amount}";
		}

		if( store.TreasureClassNumber != null ) {
			return $"R {store.TreasureClassNumber} {store.Amount}";
		}

		throw new Exception( $"Unknown store type." );
	}
}