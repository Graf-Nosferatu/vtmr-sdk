using Sdk.Templates;

namespace Sdk.Treasures;

partial class TreasureClass
{
	private static readonly Alteration
		cash0 = new( cash0, 1, 5 ),
		cash1 = new( cash1, 2, 10 ),
		cash2 = new( cash2, 11, 50 ),
		cash3 = new( cash3, 51, 100 ),
		cash4 = new( cash4, 101, 500 ),
		cash5 = new( cash5, 501, 1000 ),
		cash6 = new( cash6, 1001, 2000 ),
		cash7 = new( cash7, 2001, 5000 );


	private static readonly Category
		baseweapons = new( baseweapons, [
			nameof( Template.rags ),
			nameof( Template.broadswordrusty ),
			nameof( Template.daggerrusty ),
			nameof( Template.club ),
			nameof( Template.quarterstaff ),
			nameof( Template.axerusty ),
			nameof( Template.mace ),
			nameof( Template.rapier )
		] ),
		petrinweapons = new( petrinweapons, [
			nameof( Template.halberd ),
			nameof( Template.morodagger ),
			nameof( Template.scythe ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword ),
			nameof( Template.broadsword ),
			nameof( Template.dagger ),
			nameof( Template.club ),
			nameof( Template.quarterstaff ),
			nameof( Template.shortbow ),
			nameof( Template.axe ),
			nameof( Template.mace ),
			nameof( Template.rapier )
		] ),
		josefweapons = new( josefweapons, [
			nameof( Template.claymore ),
			nameof( Template.falchion ),
			nameof( Template.pointymace ),
			nameof( Template.longbow ),
			nameof( Template.halberd ),
			nameof( Template.morodagger ),
			nameof( Template.scythe ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword ),
			nameof( Template.broadswordrusty ),
			nameof( Template.daggerrusty ),
			nameof( Template.shortbow ),
			nameof( Template.axerusty ),
			nameof( Template.mace )
		] ),
		ardanweapons = new( ardanweapons, [
			nameof( Template.warhammer ),
			nameof( Template.flamberge ),
			nameof( Template.bastion ),
			nameof( Template.claymore ),
			nameof( Template.falchion ),
			nameof( Template.pointymace ),
			nameof( Template.longbow ),
			nameof( Template.halberd ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword ),
			nameof( Template.broadsword ),
			nameof( Template.dagger ),
			nameof( Template.axe ),
			nameof( Template.mace )
		] ),
		stephanweapons = new( stephanweapons, [
			nameof( Template.greatsword ),
			nameof( Template.battleaxe ),
			nameof( Template.crossbow ),
			nameof( Template.warhammer ),
			nameof( Template.flamberge ),
			nameof( Template.bastion ),
			nameof( Template.claymore ),
			nameof( Template.falchion ),
			nameof( Template.pointymace ),
			nameof( Template.longbow ),
			nameof( Template.halberd ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword )
		] ),
		teutonweapons = new( teutonweapons, [
			nameof( Template.lance ),
			nameof( Template.greatsword ),
			nameof( Template.battleaxe ),
			nameof( Template.crossbow ),
			nameof( Template.warhammer ),
			nameof( Template.flamberge ),
			nameof( Template.bastion ),
			nameof( Template.claymore ),
			nameof( Template.falchion ),
			nameof( Template.pointymace ),
			nameof( Template.longbow ),
			nameof( Template.halberd ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword )
		] ),
		hausweapons = new( hausweapons, [
			nameof( Template.lance ),
			nameof( Template.greatsword ),
			nameof( Template.battleaxe ),
			nameof( Template.crossbow ),
			nameof( Template.warhammer ),
			nameof( Template.flamberge ),
			nameof( Template.bastion ),
			nameof( Template.claymore ),
			nameof( Template.falchion ),
			nameof( Template.pointymace ),
			nameof( Template.longbow ),
			nameof( Template.halberd ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword )
		] ),
		vysweapons = new( vysweapons, [
			nameof( Template.lance ),
			nameof( Template.greatsword ),
			nameof( Template.battleaxe ),
			nameof( Template.crossbow ),
			nameof( Template.warhammer ),
			nameof( Template.flamberge ),
			nameof( Template.bastion ),
			nameof( Template.claymore ),
			nameof( Template.falchion ),
			nameof( Template.pointymace ),
			nameof( Template.longbow ),
			nameof( Template.halberd ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword )
		] ),
		socweapons = new( socweapons, [
			nameof( Template.baseballbat ),
			nameof( Template.brassknuckles ),
			nameof( Template.leadpipe ),
			nameof( Template.sapgloves ),
			nameof( Template.pistol ),
			nameof( Template.incinerator ),
			nameof( Template.compoundbow )
		] ),
		setweapons = new( setweapons, [
			nameof( Template.machete ),
			nameof( Template.shotgun ),
			nameof( Template.taser ),
			nameof( Template.chainsaw ),
			nameof( Template.baseballbat ),
			nameof( Template.brassknuckles ),
			nameof( Template.leadpipe ),
			nameof( Template.sapgloves ),
			nameof( Template.pistol ),
			nameof( Template.incinerator ),
			nameof( Template.compoundbow )
		] ),
		towerweapons = new( towerweapons, [
			nameof( Template.revolver ),
			nameof( Template.crossbowmd ),
			nameof( Template.assaultrifle ),
			nameof( Template.stakegun ),
			nameof( Template.machete ),
			nameof( Template.shotgun ),
			nameof( Template.taser ),
			nameof( Template.chainsaw ),
			nameof( Template.baseballbat ),
			nameof( Template.brassknuckles ),
			nameof( Template.leadpipe ),
			nameof( Template.pistol ),
			nameof( Template.incinerator )
		] ),
		sewerweapons = new( sewerweapons, [
			nameof( Template.submachinegun ),
			nameof( Template.grenadelauncher ),
			nameof( Template.revolver ),
			nameof( Template.crossbow ),
			nameof( Template.assaultrifle ),
			nameof( Template.stakegun ),
			nameof( Template.machete ),
			nameof( Template.shotgun ),
			nameof( Template.taser ),
			nameof( Template.chainsaw ),
			nameof( Template.baseballbat ),
			nameof( Template.pistol ),
			nameof( Template.incinerator )
		] ),
		wareweapons = new( wareweapons, [
			nameof( Template.rocketlauncher ),
			nameof( Template.submachinegun ),
			nameof( Template.grenadelauncher ),
			nameof( Template.revolver ),
			nameof( Template.crossbowmd ),
			nameof( Template.assaultrifle ),
			nameof( Template.stakegun ),
			nameof( Template.shotgun ),
			nameof( Template.chainsaw ),
			nameof( Template.chaingun ),
			nameof( Template.incinerator )
		] ),
		orsiweapons = new( orsiweapons, [
			nameof( Template.flamethrower ),
			nameof( Template.rocketlauncher ),
			nameof( Template.submachinegun ),
			nameof( Template.grenadelauncher ),
			nameof( Template.rifle ),
			nameof( Template.crossbowmd ),
			nameof( Template.assaultrifle ),
			nameof( Template.stakegun ),
			nameof( Template.shotgun ),
			nameof( Template.chainsaw ),
			nameof( Template.chaingun ),
			nameof( Template.incinerator )
		] ),
		cathweapons = new( cathweapons, [
			nameof( Template.flamethrower ),
			nameof( Template.rocketlauncher ),
			nameof( Template.submachinegun ),
			nameof( Template.grenadelauncher ),
			nameof( Template.rifle ),
			nameof( Template.crossbowmd ),
			nameof( Template.assaultrifle ),
			nameof( Template.stakegun ),
			nameof( Template.shotgun ),
			nameof( Template.chainsaw ),
			nameof( Template.chaingun ),
			nameof( Template.incinerator )
		] ),
		baseweapenh = new( baseweapenh, [
			nameof( Template.broadswordfine ),
			nameof( Template.daggerfine ),
			nameof( Template.axe ),
			nameof( Template.macefine ),
			nameof( Template.rapierfine )
		] ),
		petrinweapenh = new( petrinweapenh, [
			nameof( Template.halberdfine ),
			nameof( Template.morodaggerfine ),
			nameof( Template.bastardswordfine ),
			nameof( Template.broadswordfine ),
			nameof( Template.daggerfine ),
			nameof( Template.macefine ),
			nameof( Template.rapierfine )
		] ),
		josefweapenh = new( josefweapenh, [
			nameof( Template.claymorefine ),
			nameof( Template.falchionfine ),
			nameof( Template.pointymacefine ),
			nameof( Template.halberdfine ),
			nameof( Template.morodaggerfine ),
			nameof( Template.bastardswordfine ),
			nameof( Template.broadswordexq ),
			nameof( Template.daggerexq ),
			nameof( Template.maceexq )
		] ),
		ardanweapenh = new( ardanweapenh, [
			nameof( Template.warhammerfine ),
			nameof( Template.flambergefine ),
			nameof( Template.bastionfine ),
			nameof( Template.claymorefine ),
			nameof( Template.falchionfine ),
			nameof( Template.pointymacefine ),
			nameof( Template.halberdexq ),
			nameof( Template.bastardswordexq ),
			nameof( Template.broadswordexq )
		] ),
		stephanweapenh = new( stephanweapenh, [
			nameof( Template.greatswordfine ),
			nameof( Template.battleaxefine ),
			nameof( Template.warhammerfine ),
			nameof( Template.flambergefine ),
			nameof( Template.bastionfine ),
			nameof( Template.claymoreexq ),
			nameof( Template.falchionexq ),
			nameof( Template.pointymaceexq ),
			nameof( Template.shortbow_inc ),
			nameof( Template.halberdexq ),
			nameof( Template.bastardswordexq )
		] ),
		teutonweapenh = new( teutonweapenh, [
			nameof( Template.lancefine ),
			nameof( Template.greatswordfine ),
			nameof( Template.battleaxefine ),
			nameof( Template.warhammerexq ),
			nameof( Template.flambergeexq ),
			nameof( Template.bastionexq ),
			nameof( Template.claymoreexq ),
			nameof( Template.falchionexq ),
			nameof( Template.pointymaceexq ),
			nameof( Template.halberdexq ),
			nameof( Template.bastardswordexq )
		] ),
		hausweapenh = new( hausweapenh, [
			nameof( Template.lancefine ),
			nameof( Template.greatswordexq ),
			nameof( Template.battleaxeexq ),
			nameof( Template.warhammerexq ),
			nameof( Template.flambergeexq ),
			nameof( Template.bastionexq ),
			nameof( Template.claymoreexq ),
			nameof( Template.falchionexq ),
			nameof( Template.pointymaceexq ),
			nameof( Template.longbow_inc ),
			nameof( Template.halberdexq ),
			nameof( Template.bastardswordexq )
		] ),
		vysweapenh = new( vysweapenh, [
			nameof( Template.lanceexq ),
			nameof( Template.greatswordexq ),
			nameof( Template.battleaxeexq ),
			nameof( Template.crossbow_inc ),
			nameof( Template.warhammerexq ),
			nameof( Template.flambergeexq ),
			nameof( Template.bastionexq ),
			nameof( Template.claymoreexq ),
			nameof( Template.falchionexq ),
			nameof( Template.pointymaceexq ),
			nameof( Template.longbow_inc ),
			nameof( Template.halberdexq ),
			nameof( Template.bastardswordexq )
		] ),
		socweapenh = new( socweapenh, [
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		setweapenh = new( setweapenh, [
			nameof( Template.shotgun_modified ),
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		towerweapenh = new( towerweapenh, [
			nameof( Template.revolver_sighted ),
			nameof( Template.revolver_modified ),
			nameof( Template.assaultrifle_sighted ),
			nameof( Template.assaultrifle_modified ),
			nameof( Template.shotgun_modified ),
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		sewerweapenh = new( sewerweapenh, [
			nameof( Template.submachinegun_modified ),
			nameof( Template.revolver_sighted ),
			nameof( Template.revolver_modified ),
			nameof( Template.assaultrifle_sighted ),
			nameof( Template.assaultrifle_modified ),
			nameof( Template.shotgun_modified ),
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		wareweapenh = new( wareweapenh, [
			nameof( Template.submachinegun_modified ),
			nameof( Template.revolver_sighted ),
			nameof( Template.revolver_modified ),
			nameof( Template.assaultrifle_sighted ),
			nameof( Template.assaultrifle_modified ),
			nameof( Template.shotgun_modified ),
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		orsiweapenh = new( orsiweapenh, [
			nameof( Template.submachinegun_modified ),
			nameof( Template.revolver_sighted ),
			nameof( Template.revolver_modified ),
			nameof( Template.assaultrifle_sighted ),
			nameof( Template.assaultrifle_modified ),
			nameof( Template.shotgun_modified ),
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		cathweapenh = new( cathweapenh, [
			nameof( Template.submachinegun_modified ),
			nameof( Template.revolver_sighted ),
			nameof( Template.revolver_modified ),
			nameof( Template.assaultrifle_sighted ),
			nameof( Template.assaultrifle_modified ),
			nameof( Template.shotgun_modified ),
			nameof( Template.pistol_sighted ),
			nameof( Template.pistol_modified )
		] ),
		baseammo = new( baseammo, [
			nameof( Template.arrows_05 ),
			nameof( Template.arrows_10 ),
			nameof( Template.arrows_20 )
		] ),
		stephanammo = new( stephanammo, [
			nameof( Template.bolts_05 ),
			nameof( Template.bolts_10 ),
			nameof( Template.bolts_20 )
		] ),
		teutonammo = new( teutonammo, [
			nameof( Template.arrows_inc_05 ),
			nameof( Template.arrows_inc_10 ),
			nameof( Template.arrows_inc_20 )
		] ),
		vysammo = new( vysammo, [
			nameof( Template.bolts_inc_05 ),
			nameof( Template.bolts_inc_10 ),
			nameof( Template.bolts_inc_20 )
		] ),
		socammo = new( socammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.arrows_md_05 ),
			nameof( Template.arrows_md_10 ),
			nameof( Template.arrows_md_20 )
		] ),
		setammo = new( setammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.shotgun_shells_10 ),
			nameof( Template.arrows_md_05 ),
			nameof( Template.arrows_md_10 ),
			nameof( Template.arrows_md_20 )
		] ),
		towerammo = new( towerammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.shotgun_shells_10 ),
			nameof( Template.shotgun_shells_20 ),
			nameof( Template.revolver_rounds ),
			nameof( Template.assaultrifle_clip_20 )
		] ),
		sewerammo = new( sewerammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.shotgun_shells_10 ),
			nameof( Template.shotgun_shells_20 ),
			nameof( Template.revolver_rounds ),
			nameof( Template.submachinegun_clip_16 ),
			nameof( Template.assaultrifle_clip_20 )
		] ),
		wareammo = new( wareammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.shotgun_shells_10 ),
			nameof( Template.shotgun_shells_20 ),
			nameof( Template.revolver_rounds ),
			nameof( Template.submachinegun_clip_16 ),
			nameof( Template.assaultrifle_clip_20 ),
			nameof( Template.rocket_pack_03 )
		] ),
		orsiammo = new( orsiammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.shotgun_shells_20 ),
			nameof( Template.revolver_rounds ),
			nameof( Template.submachinegun_clip_32 ),
			nameof( Template.assaultrifle_clip_40 ),
			nameof( Template.rocket_pack_03 ),
			nameof( Template.napalm_tank_10 )
		] ),
		cathammo = new( cathammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.shotgun_shells_20 ),
			nameof( Template.revolver_rounds ),
			nameof( Template.submachinegun_clip_32 ),
			nameof( Template.assaultrifle_clip_40 ),
			nameof( Template.rocket_pack_03 ),
			nameof( Template.napalm_tank_20 )
		] ),
		basearmor = new( basearmor, [
			nameof( Template.rags ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing )
		] ),
		petrinarmor = new( petrinarmor, [
			nameof( Template.rags ),
			nameof( Template.leathergloves ),
			nameof( Template.leatherclothing ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing )
		] ),
		josefarmor = new( josefarmor, [
			nameof( Template.rags ),
			nameof( Template.gauntlets ),
			nameof( Template.lighthelm ),
			nameof( Template.shield ),
			nameof( Template.studdedleather ),
			nameof( Template.leathergloves ),
			nameof( Template.leatherclothing ),
			nameof( Template.shield ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing )
		] ),
		ardanarmor = new( ardanarmor, [
			nameof( Template.largeshield ),
			nameof( Template.chainmail ),
			nameof( Template.gauntlets ),
			nameof( Template.lighthelm ),
			nameof( Template.shield ),
			nameof( Template.studdedleather ),
			nameof( Template.leatherclothing ),
			nameof( Template.footmanshield ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing )
		] ),
		stephanarmor = new( stephanarmor, [
			nameof( Template.sunshield ),
			nameof( Template.scalemail ),
			nameof( Template.largeshield ),
			nameof( Template.chainmail ),
			nameof( Template.gauntlets ),
			nameof( Template.lighthelm ),
			nameof( Template.shield ),
			nameof( Template.studdedleather ),
			nameof( Template.leatherclothing ),
			nameof( Template.footmanshield ),
			nameof( Template.skullcap ),
			nameof( Template.buckler )
		] ),
		teutonarmor = new( teutonarmor, [
			nameof( Template.gauntlets ),
			nameof( Template.fullhelm ),
			nameof( Template.halfplate ),
			nameof( Template.sunshield ),
			nameof( Template.scalemail ),
			nameof( Template.largeshield ),
			nameof( Template.chainmail ),
			nameof( Template.gauntlets ),
			nameof( Template.lighthelm ),
			nameof( Template.shield ),
			nameof( Template.studdedleather ),
			nameof( Template.leatherclothing ),
			nameof( Template.footmanshield )
		] ),
		hausarmor = new( hausarmor, [
			nameof( Template.greatshield ),
			nameof( Template.platemail ),
			nameof( Template.gauntlets ),
			nameof( Template.fullhelm ),
			nameof( Template.halfplate ),
			nameof( Template.sunshield ),
			nameof( Template.scalemail ),
			nameof( Template.largeshield ),
			nameof( Template.chainmail ),
			nameof( Template.gauntlets ),
			nameof( Template.lighthelm ),
			nameof( Template.shield ),
			nameof( Template.studdedleather )
		] ),
		vysarmor = new( vysarmor, [
			nameof( Template.greatshield ),
			nameof( Template.platemail ),
			nameof( Template.gauntlets ),
			nameof( Template.fullhelm ),
			nameof( Template.halfplate ),
			nameof( Template.sunshield ),
			nameof( Template.scalemail ),
			nameof( Template.largeshield ),
			nameof( Template.chainmail ),
			nameof( Template.gauntlets ),
			nameof( Template.lighthelm ),
			nameof( Template.shield ),
			nameof( Template.studdedleather )
		] ),
		socarmor = new( socarmor, [
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		setarmor = new( setarmor, [
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		towerarmor = new( towerarmor, [
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		sewerarmor = new( sewerarmor, [
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		warearmor = new( warearmor, [
			nameof( Template.nomexsuit ),
			nameof( Template.tacticaljacket ),
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt )
		] ),
		orsiarmor = new( orsiarmor, [
			nameof( Template.nomexsuit ),
			nameof( Template.tacticaljacket ),
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt )
		] ),
		catharmor = new( catharmor, [
			nameof( Template.nomexsuit ),
			nameof( Template.tacticaljacket ),
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt )
		] ),
		basearmorenh = new( basearmorenh, [
			nameof( Template.rags ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing ),
			nameof( Template.leathergloves )
		] ),
		petrinarmorenh = new( petrinarmorenh, [
			nameof( Template.leatherclothing_holy ),
			nameof( Template.leatherclothing_unholy ),
			nameof( Template.footmanshield ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing )
		] ),
		josefarmorenh = new( josefarmorenh, [
			nameof( Template.studdedleather_unholy ),
			nameof( Template.leatherclothing_unholy )
		] ),
		ardanarmorenh = new( ardanarmorenh, [
			nameof( Template.lighthelm_cold ),
			nameof( Template.lighthelm_elec ),
			nameof( Template.lighthelm_fire ),
			nameof( Template.shield_sun ),
			nameof( Template.studdedleather_unholy ),
			nameof( Template.leatherclothing_unholy )
		] ),
		stephanarmorenh = new( stephanarmorenh, [
			nameof( Template.shield_cold ),
			nameof( Template.shield_elec ),
			nameof( Template.shield_fire ),
			nameof( Template.shield_unholy ),
			nameof( Template.scalemail_unholy ),
			nameof( Template.chainmail_unholy ),
			nameof( Template.studdedleather_unholy ),
			nameof( Template.leatherclothing_unholy )
		] ),
		teutonarmorenh = new( teutonarmorenh, [
			nameof( Template.shield_sun ),
			nameof( Template.shield_elec ),
			nameof( Template.halfplate_unholy ),
			nameof( Template.scalemail_unholy ),
			nameof( Template.chainmail_unholy ),
			nameof( Template.studdedleather_unholy ),
			nameof( Template.leatherclothing_unholy )
		] ),
		hausarmorenh = new( hausarmorenh, [
			nameof( Template.gauntlets_cold ),
			nameof( Template.gauntlets_grounded ),
			nameof( Template.gauntlets_fire ),
			nameof( Template.greatshield_cold ),
			nameof( Template.greatshield_elec ),
			nameof( Template.greatshield_fire ),
			nameof( Template.platemail_unholy ),
			nameof( Template.halfplate_unholy ),
			nameof( Template.shield_sun ),
			nameof( Template.scalemail_unholy ),
			nameof( Template.chainmail_unholy ),
			nameof( Template.studdedleather_unholy )
		] ),
		vysarmorenh = new( vysarmorenh, [
			nameof( Template.fullhelm_cold ),
			nameof( Template.fullhelm_elec ),
			nameof( Template.fullhelm_fire ),
			nameof( Template.platemail_unholy ),
			nameof( Template.halfplate_unholy ),
			nameof( Template.scalemail_unholy ),
			nameof( Template.chainmail_unholy ),
			nameof( Template.studdedleather_unholy )
		] ),
		socarmorenh = new( socarmorenh, [
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		setarmorenh = new( setarmorenh, [
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		towerarmorenh = new( towerarmorenh, [
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		sewerarmorenh = new( sewerarmorenh, [
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.goggles )
		] ),
		warearmorenh = new( warearmorenh, [
			nameof( Template.nomexsuit ),
			nameof( Template.tacticaljacket ),
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt )
		] ),
		orsiarmorenh = new( orsiarmorenh, [
			nameof( Template.nomexsuit ),
			nameof( Template.tacticaljacket ),
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt )
		] ),
		catharmorenh = new( catharmorenh, [
			nameof( Template.nomexsuit ),
			nameof( Template.tacticaljacket ),
			nameof( Template.swathelmet ),
			nameof( Template.mediumballisticvest ),
			nameof( Template.armyhelmet ),
			nameof( Template.lightballisticvest ),
			nameof( Template.riotshield ),
			nameof( Template.motorcyclehelmet ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt )
		] ),
		baseitems = new( baseitems, [
			nameof( Template.potionhealth ),
			nameof( Template.potionhealth ),
			nameof( Template.potionhealth ),
			nameof( Template.potionhealth ),
			nameof( Template.potiondisease ),
			nameof( Template.holywater )
		] ),
		petrinitems = new( petrinitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.potionpoison ),
			nameof( Template.potiondisease )
		] ),
		josefitems = new( josefitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.potionpoison ),
			nameof( Template.potiondisease )
		] ),
		ardanitems = new( ardanitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.potionpoison ),
			nameof( Template.bloodstone ),
			nameof( Template.greekfire )
		] ),
		stephanitems = new( stephanitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.potionpoison ),
			nameof( Template.bloodstone ),
			nameof( Template.greekfire ),
			nameof( Template.vitaewerewolf )
		] ),
		teutonitems = new( teutonitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.potionpoison ),
			nameof( Template.bloodstone ),
			nameof( Template.greekfire ),
			nameof( Template.bloodpearl )
		] ),
		hausitems = new( hausitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.potionpoison ),
			nameof( Template.bloodstone ),
			nameof( Template.bloodpearl ),
			nameof( Template.bloodpouch )
		] ),
		vysitems = new( vysitems, [
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitae ),
			nameof( Template.vitaediseased ),
			nameof( Template.bloodstone ),
			nameof( Template.bloodpearl ),
			nameof( Template.bloodpouch )
		] ),
		socitems = new( socitems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased )
		] ),
		setitems = new( setitems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased ),
			nameof( Template.potionpoisonmd ),
			nameof( Template.potiondiseasemd )
		] ),
		toweritems = new( toweritems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased ),
			nameof( Template.potionpoisonmd ),
			nameof( Template.potiondiseasemd )
		] ),
		seweritems = new( seweritems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased ),
			nameof( Template.potionpoisonmd ),
			nameof( Template.potiondiseasemd )
		] ),
		wareitems = new( wareitems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased ),
			nameof( Template.potionpoisonmd ),
			nameof( Template.potiondiseasemd )
		] ),
		orsiitems = new( orsiitems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased ),
			nameof( Template.potionpoisonmd ),
			nameof( Template.potiondiseasemd )
		] ),
		cathitems = new( cathitems, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmadiseased ),
			nameof( Template.potionpoisonmd ),
			nameof( Template.potiondiseasemd )
		] ),
		baseloot = new( baseloot, [
			nameof( Template.silvercoins ),
			nameof( Template.silvercoins ),
			nameof( Template.silvercoins ),
			nameof( Template.silvercoins ),
			nameof( Template.ringonyx ),
			nameof( Template.ringsilver )
		] ),
		petrinloot = new( petrinloot, [
			nameof( Template.silvercoins ),
			nameof( Template.silvercoins ),
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.ringsilver ),
			nameof( Template.silverbracelet ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.holydavid )
		] ),
		josefloot = new( josefloot, [
			nameof( Template.silvercoins ),
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldbag ),
			nameof( Template.ringsilver ),
			nameof( Template.silverbracelet ),
			nameof( Template.holydavid )
		] ),
		ardanloot = new( ardanloot, [
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldbag ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.silverbracelet ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette )
		] ),
		stephanloot = new( stephanloot, [
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldbag ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.silverbracelet ),
			nameof( Template.silvernecklace ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette )
		] ),
		teutonloot = new( teutonloot, [
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldbag ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.silverbracelet ),
			nameof( Template.silvernecklace ),
			nameof( Template.goldnecklace ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette )
		] ),
		hausloot = new( hausloot, [
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldbag ),
			nameof( Template.ringgold ),
			nameof( Template.silverbracelet ),
			nameof( Template.silvernecklace ),
			nameof( Template.goldnecklace ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette )
		] ),
		vysloot = new( vysloot, [
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldcoins ),
			nameof( Template.goldbag ),
			nameof( Template.ringgold ),
			nameof( Template.silverbracelet ),
			nameof( Template.silvernecklace ),
			nameof( Template.goldnecklace ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette )
		] ),
		socloot = new( socloot, [
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.rags )
		] ),
		setloot = new( setloot, [
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.rags )
		] ),
		towerloot = new( towerloot, [
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.rags )
		] ),
		sewerloot = new( sewerloot, [
			nameof( Template.cashus ),
			nameof( Template.cashuk ),
			nameof( Template.cashuk ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holydavid ),
			nameof( Template.rags )
		] ),
		wareloot = new( wareloot, [
			nameof( Template.cashus ),
			nameof( Template.cashus ),
			nameof( Template.cashus ),
			nameof( Template.bonds ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.rags )
		] ),
		orsiloot = new( orsiloot, [
			nameof( Template.cashus ),
			nameof( Template.cashus ),
			nameof( Template.cashus ),
			nameof( Template.silvercoins ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.rags )
		] ),
		cathloot = new( cathloot, [
			nameof( Template.cashus ),
			nameof( Template.cashus ),
			nameof( Template.cashus ),
			nameof( Template.goldbullion ),
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.goldwatch ),
			nameof( Template.holycross ),
			nameof( Template.holystatuette ),
			nameof( Template.rags )
		] ),
		arrowsda = new( arrowsda, [
			nameof( Template.arrows_05 ),
			nameof( Template.arrows_10 ),
			nameof( Template.arrows_20 )
		] ),
		boltsda = new( boltsda, [
			nameof( Template.bolts_05 ),
			nameof( Template.bolts_10 ),
			nameof( Template.bolts_20 )
		] ),
		arrowsmd = new( arrowsmd, [
			nameof( Template.arrows_md_05 ),
			nameof( Template.arrows_md_10 ),
			nameof( Template.arrows_md_20 )
		] ),
		boltsmd = new( boltsmd, [
			nameof( Template.bolts_md_05 ),
			nameof( Template.bolts_md_10 ),
			nameof( Template.bolts_md_20 )
		] ),
		pistolclip = new( pistolclip, [
			nameof( Template.pistol_clip )
		] ),
		revolverrounds = new( revolverrounds, [
			nameof( Template.revolver_rounds )
		] ),
		rifleclip = new( rifleclip, [
			nameof( Template.rifle_clip_11 ),
			nameof( Template.rifle_clip_22 )
		] ),
		shotgunshells = new( shotgunshells, [
			nameof( Template.shotgun_shells_10 ),
			nameof( Template.shotgun_shells_20 )
		] ),
		powercel = new( powercel, [
			nameof( Template.power_cel_12 ),
			nameof( Template.power_cel_24 )
		] ),
		submachineclip = new( submachineclip, [
			nameof( Template.submachinegun_clip_16 ),
			nameof( Template.submachinegun_clip_32 )
		] ),
		assaultclip = new( assaultclip, [
			nameof( Template.assaultrifle_clip_20 ),
			nameof( Template.assaultrifle_clip_40 )
		] ),
		launchgrenades = new( launchgrenades, [
			nameof( Template.launch_grenades )
		] ),
		napalmtank = new( napalmtank, [
			nameof( Template.napalm_tank_10 ),
			nameof( Template.napalm_tank_20 )
		] ),
		rockets = new( rockets, [
			nameof( Template.rocket_pack_03 )
		] ),
		potion = new( potion, [
			nameof( Template.potionhealth ),
			nameof( Template.potionpoison ),
			nameof( Template.potiondisease )
		] ),
		potionhealth = new( potionhealth, [
			nameof( Template.potionhealth )
		] ),
		potionpoison = new( potionpoison, [
			nameof( Template.potionpoison )
		] ),
		potiondisease = new( potiondisease, [
			nameof( Template.potiondisease )
		] ),
		vitaecommon = new( vitaecommon, [
			nameof( Template.vitaered ),
			nameof( Template.vitaered ),
			nameof( Template.vitaered ),
			nameof( Template.vitaered ),
			nameof( Template.bloodpearl ),
			nameof( Template.bloodpouch ),
			nameof( Template.bloodstone )
		] ),
		vitaemodern = new( vitaemodern, [
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.plasmabag ),
			nameof( Template.bloodpearl ),
			nameof( Template.bloodpouch ),
			nameof( Template.bloodstone )
		] ),
		vitaerare = new( vitaerare, [
			nameof( Template.vitaekindred ),
			nameof( Template.vitaewerewolf ),
			nameof( Template.vitaeelder ),
			nameof( Template.vitaefae )
		] ),
		scrollsteleport = new( scrollsteleport, [
			nameof( Template.discscroll_walktheabyss_1 )
		] ),
		scrollsawaken = new( scrollsawaken, [
			nameof( Template.discscroll_awaken_1 )
		] ),
		scrollsidentify = new( scrollsidentify, [
			nameof( Template.discscroll_spiritstouch_1 )
		] ),
		scrollssense = new( scrollssense, [
			nameof( Template.discscroll_heightenedsenses_1 )
		] ),
		scrollsgeneric = new( scrollsgeneric, [
			nameof( Template.discscroll_celerity_1 ),
			nameof( Template.discscroll_cloakofshadows_1 ),
			nameof( Template.discscroll_fortitude_1 ),
			nameof( Template.discscroll_potence_1 ),
			nameof( Template.discscroll_awaken_1 ),
			nameof( Template.discscroll_walktheabyss_1 ),
			nameof( Template.discscroll_spiritstouch_1 )
		] ),
		scrollsoffense = new( scrollsoffense, [
			nameof( Template.discscroll_fireball_1 ),
			nameof( Template.discscroll_feralclaws_1 ),
			nameof( Template.discscroll_summonelemental_1 ),
			nameof( Template.discscroll_plaguewind_1 ),
			nameof( Template.discscroll_theftofvitae_1 )
		] ),
		scrollsanimal = new( scrollsanimal, [
			nameof( Template.discscroll_feralwhispers_1 ),
			nameof( Template.discscroll_beckoning_1 ),
			nameof( Template.discscroll_quellbeast_1 ),
			nameof( Template.discscroll_drawingoutbeast_1 )
		] ),
		scrollsauspex = new( scrollsauspex, [
			nameof( Template.discscroll_auraperception_1 ),
			nameof( Template.discscroll_heightenedsenses_1 ),
			nameof( Template.discscroll_psychicprojection_1 ),
			nameof( Template.discscroll_spiritstouch_1 )
		] ),
		scrollscelerity = new( scrollscelerity, [
			nameof( Template.discscroll_celerity_1 )
		] ),
		scrollscommon = new( scrollscommon, [
			nameof( Template.discscroll_bloodhealing_1 ),
			nameof( Template.discscroll_bloodstrength_1 ),
			nameof( Template.discscroll_blooddexterity_1 ),
			nameof( Template.discscroll_bloodstamina_1 ),
			nameof( Template.discscroll_awaken_1 ),
			nameof( Template.discscroll_walktheabyss_1 ),
			nameof( Template.discscroll_spiritstouch_1 )
		] ),
		scrollsdementation = new( scrollsdementation, [
			nameof( Template.discscroll_eyesofchaos_1 ),
			nameof( Template.discscroll_passion_1 ),
			nameof( Template.discscroll_thehaunting_1 ),
			nameof( Template.discscroll_voiceofmadness_1 )
		] ),
		scrollsdominate = new( scrollsdominate, [
			nameof( Template.discscroll_command_1 ),
			nameof( Template.discscroll_mesmerize_1 ),
			nameof( Template.discscroll_theforgetfulmind_1 ),
			nameof( Template.discscroll_possession_1 )
		] ),
		scrollsfortitude = new( scrollsfortitude, [
			nameof( Template.discscroll_fortitude_1 )
		] ),
		scrollsmortis = new( scrollsmortis, [
			nameof( Template.discscroll_blackdeath_1 ),
			nameof( Template.discscroll_plaguewind_1 ),
			nameof( Template.discscroll_shamblinghordes_1 ),
			nameof( Template.discscroll_summonsoul_1 ),
			nameof( Template.discscroll_vigormortis_1 )
		] ),
		scrollsobfuscate = new( scrollsobfuscate, [
			nameof( Template.discscroll_cloakofshadows_1 ),
			nameof( Template.discscroll_cloakthegathering_1 )
		] ),
		scrollsobtenebration = new( scrollsobtenebration, [
			nameof( Template.discscroll_darkhunter_1 )
		] ),
		scrollspotence = new( scrollspotence, [
			nameof( Template.discscroll_potence_1 )
		] ),
		scrollspresence = new( scrollspresence, [
			nameof( Template.discscroll_awe_1 ),
			nameof( Template.discscroll_dreadgaze_1 ),
			nameof( Template.discscroll_entrancement_1 ),
			nameof( Template.discscroll_majesty_1 )
		] ),
		scrollsprotean = new( scrollsprotean, [
			nameof( Template.discscroll_earthmeld_1 ),
			nameof( Template.discscroll_eyesofthebeast_1 ),
			nameof( Template.discscroll_feralclaws_1 ),
			nameof( Template.discscroll_mistform_1 ),
			nameof( Template.discscroll_shapeofthebeast_1 )
		] ),
		scrollsserpentis = new( scrollsserpentis, [
			nameof( Template.discscroll_eyesoftheserpent_1 ),
			nameof( Template.discscroll_hatchtheviper_1 ),
			nameof( Template.discscroll_skinoftheadder_1 )
		] ),
		scrollsthaublood = new( scrollsthaublood, [
			nameof( Template.discscroll_bloodofpotency_1 ),
			nameof( Template.discscroll_bloodrage_1 ),
			nameof( Template.discscroll_cauldronofblood_1 ),
			nameof( Template.discscroll_theftofvitae_1 )
		] ),
		scrollsthauflame = new( scrollsthauflame, [
			nameof( Template.discscroll_fireball_1 ),
			nameof( Template.discscroll_firestorm_1 ),
			nameof( Template.discscroll_torch_1 )
		] ),
		scrollsthauhands = new( scrollsthauhands, [
			nameof( Template.discscroll_acidictouch_1 ),
			nameof( Template.discscroll_atrophy_1 ),
			nameof( Template.discscroll_decay_1 ),
			nameof( Template.discscroll_turntodust_1 )
		] ),
		scrollsthaurituals = new( scrollsthaurituals, [
			nameof( Template.discscroll_calllightning_1 ),
			nameof( Template.discscroll_heartofstone_1 ),
			nameof( Template.discscroll_ignoreflame_1 ),
			nameof( Template.discscroll_prisonofice_1 ),
			nameof( Template.discscroll_summonelemental_1 ),
			nameof( Template.discscroll_walktheabyss_1 )
		] ),
		scrollsnumina = new( scrollsnumina, [
			nameof( Template.numscroll_flash_1 ),
			nameof( Template.numscroll_truefaith_1 ),
			nameof( Template.numscroll_truesight_1 ),
			nameof( Template.numscroll_prayer_1 ),
			nameof( Template.numscroll_invisibility_1 )
		] ),
		rings = new( rings, [
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.ringonyx ),
			nameof( Template.ringdiamond )
		] ),
		necklaces = new( necklaces, [
			nameof( Template.silvernecklace ),
			nameof( Template.goldnecklace ),
			nameof( Template.diamondnecklace )
		] ),
		kineitems = new( kineitems, [
			nameof( Template.stake ),
			nameof( Template.holywater )
		] ),
		kindreditems = new( kindreditems, [
			nameof( Template.bloodring ),
			nameof( Template.bloodnecklace ),
			nameof( Template.heartshield )
		] ),
		lightarmorda = new( lightarmorda, [
			nameof( Template.paddedclothing ),
			nameof( Template.leatherclothing ),
			nameof( Template.studdedleather ),
			nameof( Template.skullcap ),
			nameof( Template.lighthelm ),
			nameof( Template.shield )
		] ),
		heavyarmorda = new( heavyarmorda, [
			nameof( Template.scalemail ),
			nameof( Template.chainmail ),
			nameof( Template.halfplate ),
			nameof( Template.platemail ),
			nameof( Template.chestplate ),
			nameof( Template.lighthelm ),
			nameof( Template.fullhelm ),
			nameof( Template.largeshield ),
			nameof( Template.greatshield )
		] ),
		shieldsda = new( shieldsda, [
			nameof( Template.footmanshield ),
			nameof( Template.cavalryshield ),
			nameof( Template.largeshield ),
			nameof( Template.buckler ),
			nameof( Template.shield ),
			nameof( Template.greatshield ),
			nameof( Template.gauntlets ),
			nameof( Template.gauntlets_heavy )
		] ),
		headgearda = new( headgearda, [
			nameof( Template.skullcap ),
			nameof( Template.lighthelm ),
			nameof( Template.fullhelm )
		] ),
		neckwearda = new( neckwearda, [
			nameof( Template.neckguard )
		] ),
		weapons = new( weapons, [
			nameof( Template.broadsword )
		] ),
		rangedweapons = new( rangedweapons, [
			nameof( Template.shortbow ),
			nameof( Template.longbow )
		] ),
		meleeweapons = new( meleeweapons, [
			nameof( Template.dagger ),
			nameof( Template.club ),
			nameof( Template.broadsword ),
			nameof( Template.halberd ),
			nameof( Template.mace ),
			nameof( Template.warhammer ),
			nameof( Template.pointymace ),
			nameof( Template.bastion )
		] ),
		swordsda = new( swordsda, [
			nameof( Template.broadsword ),
			nameof( Template.falchion ),
			nameof( Template.scimitar ),
			nameof( Template.bastardsword ),
			nameof( Template.claymore ),
			nameof( Template.greatsword )
		] ),
		daggersda = new( daggersda, [
			nameof( Template.dagger ),
			nameof( Template.dirk ),
			nameof( Template.morodagger ),
			nameof( Template.poignard )
		] ),
		bluntda = new( bluntda, [
			nameof( Template.mace ),
			nameof( Template.warhammer ),
			nameof( Template.club ),
			nameof( Template.quarterstaff )
		] ),
		archeryda = new( archeryda, [
			nameof( Template.shortbow ),
			nameof( Template.longbow ),
			nameof( Template.crossbow )
		] ),
		polearms = new( polearms, [
			nameof( Template.pitch_fork ),
			nameof( Template.spear ),
			nameof( Template.lance ),
			nameof( Template.halberd ),
			nameof( Template.bastion ),
			nameof( Template.poleaxe )
		] ),
		quickuseda = new( quickuseda, [
			nameof( Template.greekfire ),
			nameof( Template.throwingknives )
		] ),
		ammoda = new( ammoda, [
			nameof( Template.arrows_05 ),
			nameof( Template.arrows_10 ),
			nameof( Template.arrows_20 ),
			nameof( Template.bolts_05 ),
			nameof( Template.bolts_10 ),
			nameof( Template.bolts_20 )
		] ),
		lightarmormd = new( lightarmormd, [
			nameof( Template.reienforcedclothing ),
			nameof( Template.leathervest ),
			nameof( Template.leatherjacket ),
			nameof( Template.armortshirt ),
			nameof( Template.lightballisticvest )
		] ),
		heavyarmormd = new( heavyarmormd, [
			nameof( Template.mediumballisticvest ),
			nameof( Template.flakjacket ),
			nameof( Template.tacticaljacket ),
			nameof( Template.tailoredarmor ),
			nameof( Template.nomexsuit )
		] ),
		shieldsmd = new( shieldsmd, [
			nameof( Template.riotshield )
		] ),
		headgearmd = new( headgearmd, [
			nameof( Template.motorcyclehelmet ),
			nameof( Template.armyhelmet ),
			nameof( Template.swathelmet )
		] ),
		neckwearmd = new( neckwearmd, [
			nameof( Template.neckguard )
		] ),
		daggersmd = new( daggersmd, [
			nameof( Template.stake )
		] ),
		bluntmd = new( bluntmd, [
			nameof( Template.leadpipe ),
			nameof( Template.baseballbat ),
			nameof( Template.brassknuckles ),
			nameof( Template.sapgloves )
		] ),
		archerymd = new( archerymd, [
			nameof( Template.compoundbow ),
			nameof( Template.crossbowmd )
		] ),
		lightfirearms = new( lightfirearms, [
			nameof( Template.revolver ),
			nameof( Template.pistol )
		] ),
		medfirearms = new( medfirearms, [
			nameof( Template.rifle ),
			nameof( Template.shotgun )
		] ),
		heavyfirearms = new( heavyfirearms, [
			nameof( Template.assaultrifle )
		] ),
		artillery = new( artillery, [
			nameof( Template.flamethrower ),
			nameof( Template.grenadelauncher ),
			nameof( Template.rocketlauncher )
		] ),
		quickusemd = new( quickusemd, [
			nameof( Template.molotovcocktail ),
			nameof( Template.fraggrenade ),
			nameof( Template.concgrenade ),
			nameof( Template.chemgrenade ),
			nameof( Template.phosgrenade ),
			nameof( Template.satchelcharge ),
			nameof( Template.napalmbomb )
		] ),
		archeryammomd = new( archeryammomd, [
			nameof( Template.bolts_md_05 ),
			nameof( Template.bolts_md_10 ),
			nameof( Template.bolts_md_20 ),
			nameof( Template.arrows_md_05 ),
			nameof( Template.arrows_md_10 ),
			nameof( Template.arrows_md_20 )
		] ),
		lightammo = new( lightammo, [
			nameof( Template.revolver_rounds ),
			nameof( Template.pistol_clip ),
			nameof( Template.rifle_clip_11 ),
			nameof( Template.shotgun_shells_10 )
		] ),
		heavyammo = new( heavyammo, [
			nameof( Template.submachinegun_clip_16 ),
			nameof( Template.submachinegun_clip_32 ),
			nameof( Template.assaultrifle_clip_20 ),
			nameof( Template.assaultrifle_clip_40 )
		] ),
		artilleryammo = new( artilleryammo, [
			nameof( Template.chaingun_rounds_50 ),
			nameof( Template.chaingun_rounds_100 ),
			nameof( Template.napalm_tank_10 ),
			nameof( Template.napalm_tank_20 ),
			nameof( Template.rocket_pack_03 )
		] ),
		barrelloot = new( barrelloot, [
			nameof( Template.rags ),
			nameof( Template.broadswordrusty ),
			nameof( Template.daggerrusty ),
			nameof( Template.axerusty )
		] ),
		silvercoins = new( silvercoins, [
			nameof( Template.silvercoins )
		] ),
		cashda = new( cashda, [
			nameof( Template.silvercoins ),
			nameof( Template.goldcoins )
		] ),
		jewelryda = new( jewelryda, [
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.ringonyx ),
			nameof( Template.silverbracelet ),
			nameof( Template.silvernecklace )
		] ),
		gemstonesda = new( gemstonesda, [
			nameof( Template.sapphires ),
			nameof( Template.rubies ),
			nameof( Template.emeralds ),
			nameof( Template.diamonds )
		] ),
		cashuk = new( cashuk, [
			nameof( Template.cashuk )
		] ),
		cashus = new( cashus, [
			nameof( Template.cashus )
		] ),
		bonds = new( bonds, [
			nameof( Template.bonds )
		] ),
		monetaryuk = new( monetaryuk, [
			nameof( Template.cashuk ),
			nameof( Template.bonds ),
			nameof( Template.goldbullion )
		] ),
		monetaryus = new( monetaryus, [
			nameof( Template.cashus ),
			nameof( Template.bonds ),
			nameof( Template.goldbullion )
		] ),
		jewelrymd = new( jewelrymd, [
			nameof( Template.ringsilver ),
			nameof( Template.ringgold ),
			nameof( Template.ringonyx ),
			nameof( Template.ringdiamond ),
			nameof( Template.silverbracelet ),
			nameof( Template.goldbracelet ),
			nameof( Template.goldwatch ),
			nameof( Template.silvernecklace ),
			nameof( Template.goldnecklace ),
			nameof( Template.diamondnecklace )
		] ),
		gemstonesmd = new( gemstonesmd, [
			nameof( Template.sapphires ),
			nameof( Template.rubies ),
			nameof( Template.emeralds ),
			nameof( Template.diamonds )
		] ),
		artamulet = new( artamulet, [
			nameof( Template.amuletcontrol ),
			nameof( Template.amuletdiscipline ),
			nameof( Template.amuletint ),
			nameof( Template.amuletper ),
			nameof( Template.amuletwit ),
			nameof( Template.bloodnecklace )
		] ),
		artarmor = new( artarmor, [
			nameof( Template.leatherclothing_holy ),
			nameof( Template.leatherclothing_unholy ),
			nameof( Template.studdedleather_holy ),
			nameof( Template.studdedleather_unholy ),
			nameof( Template.chainmail_holy ),
			nameof( Template.chainmail_unholy ),
			nameof( Template.scalemail_holy ),
			nameof( Template.scalemail_unholy ),
			nameof( Template.halfplate_holy ),
			nameof( Template.halfplate_unholy ),
			nameof( Template.platemail_holy ),
			nameof( Template.platemail_unholy )
		] ),
		artbracelet = new( artbracelet, [
			nameof( Template.braceletblood ),
			nameof( Template.braceletcontrol ),
			nameof( Template.braceletdiscipline ),
			nameof( Template.heartshield )
		] ),
		artgauntlet = new( artgauntlet, [
			nameof( Template.gauntletaccuracy ),
			nameof( Template.gauntletdex ),
			nameof( Template.gauntletdodge ),
			nameof( Template.gauntletfortitude ),
			nameof( Template.gauntletpower ),
			nameof( Template.gauntletsta ),
			nameof( Template.gauntletstr )
		] ),
		artpotion = new( artpotion, [
			nameof( Template.giantblood ),
			nameof( Template.vitaekindred ),
			nameof( Template.vitaeelder ),
			nameof( Template.vitaefae ),
			nameof( Template.vitaewerewolf )
		] ),
		artring = new( artring, [
			nameof( Template.bloodring ),
			nameof( Template.ringapp ),
			nameof( Template.ringcha ),
			nameof( Template.ringcontrol ),
			nameof( Template.ringdiscipline ),
			nameof( Template.ringman )
		] ),
		artsword = new( artsword, [
			nameof( Template.broadsworddread ),
			nameof( Template.broadswordholy ),
			nameof( Template.claymoredread ),
			nameof( Template.falchiondread ),
			nameof( Template.flambergedread ),
			nameof( Template.greatsworddread ),
			nameof( Template.greatswordholy )
		] ),
		artweapon = new( artweapon, [
			nameof( Template.battleaxeberserker ),
			nameof( Template.maceholy ),
			nameof( Template.pointymaceholy ),
			nameof( Template.warhammerberserker )
		] );

	public static readonly TreasureClass
		class1 = new( class1, [
			new( baseloot, 30, cash0 ),
			new( baseammo, 30 ),
			new( baseitems, 30 ),
			new( baseweapons, 30 ),
			new( basearmor, 90 )
		] ),
		class2 = new( class2, [
			new( baseloot, 50, cash0 ),
			new( baseitems, 90 )
		] ),
		class3 = new( class3, [
			new( baseitems, 50 ),
			new( baseweapons, 90 )
		] ),
		class4 = new( class4, [
			new( baseweapons, 50 ),
			new( basearmor, 90 )
		] ),
		class5 = new( class5, [
			new( baseweapenh, 50 ),
			new( basearmorenh, 100 )
		] ),
		class6 = new( class6, [
			new( petrinloot, 30, cash1 ),
			new( baseammo, 30 ),
			new( petrinitems, 30 ),
			new( scrollscommon, 30 ),
			new( petrinweapons, 30 ),
			new( petrinarmor, 90 )
		] ),
		class7 = new( class7, [
			new( petrinloot, 50, cash1 ),
			new( gemstonesda, 10, cash1 ),
			new( petrinitems, 50 ),
			new( scrollscommon, 90 )
		] ),
		class8 = new( class8, [
			new( petrinitems, 50 ),
			new( scrollscommon, 50 ),
			new( petrinweapons, 90 )
		] ),
		class9 = new( class9, [
			new( petrinweapons, 50 ),
			new( petrinarmor, 90 )
		] ),
		class10 = new( class10, [
			new( petrinweapenh, 50 ),
			new( petrinarmorenh, 100 )
		] ),
		class11 = new( class11, [
			new( josefloot, 30, cash1 ),
			new( baseammo, 30 ),
			new( josefitems, 30 ),
			new( scrollscommon, 30 ),
			new( josefweapons, 30 ),
			new( josefarmor, 90 )
		] ),
		class12 = new( class12, [
			new( josefloot, 50, cash1 ),
			new( gemstonesda, 10, cash2 ),
			new( josefitems, 50 ),
			new( scrollscommon, 90 )
		] ),
		class13 = new( class13, [
			new( josefitems, 50 ),
			new( scrollscommon, 50 ),
			new( josefweapons, 90 )
		] ),
		class14 = new( class14, [
			new( josefweapons, 50 ),
			new( josefarmor, 90 )
		] ),
		class15 = new( class15, [
			new( josefweapenh, 50 ),
			new( josefarmorenh, 100 )
		] ),
		class16 = new( class16, [
			new( ardanloot, 30, cash2 ),
			new( baseammo, 30 ),
			new( ardanitems, 30 ),
			new( scrollsgeneric, 30 ),
			new( ardanweapons, 30 ),
			new( ardanarmor, 90 )
		] ),
		class17 = new( class17, [
			new( ardanloot, 50, cash2 ),
			new( gemstonesda, 10, cash3 ),
			new( ardanitems, 50 ),
			new( scrollsgeneric, 90 )
		] ),
		class18 = new( class18, [
			new( ardanitems, 50 ),
			new( scrollsgeneric, 50 ),
			new( ardanweapons, 90 )
		] ),
		class19 = new( class19, [
			new( ardanweapons, 50 ),
			new( ardanarmor, 90 )
		] ),
		class20 = new( class20, [
			new( ardanweapenh, 50 ),
			new( ardanarmorenh, 100 )
		] ),
		class21 = new( class21, [
			new( stephanloot, 30, cash3 ),
			new( stephanammo, 30 ),
			new( stephanitems, 30 ),
			new( scrollsgeneric, 30 ),
			new( stephanweapons, 30 ),
			new( stephanarmor, 90 )
		] ),
		class22 = new( class22, [
			new( stephanloot, 50, cash3 ),
			new( gemstonesda, 10, cash4 ),
			new( stephanitems, 50 ),
			new( scrollsgeneric, 90 )
		] ),
		class23 = new( class23, [
			new( stephanitems, 50 ),
			new( scrollsgeneric, 50 ),
			new( stephanweapons, 90 )
		] ),
		class24 = new( class24, [
			new( stephanweapons, 50 ),
			new( stephanarmor, 90 )
		] ),
		class25 = new( class25, [
			new( stephanweapenh, 50 ),
			new( stephanarmorenh, 100 )
		] ),
		class26 = new( class26, [
			new( teutonloot, 30, cash4 ),
			new( teutonammo, 30 ),
			new( teutonitems, 30 ),
			new( scrollsthaublood, 30 ),
			new( teutonweapons, 30 ),
			new( teutonarmor, 90 )
		] ),
		class27 = new( class27, [
			new( teutonloot, 50, cash4 ),
			new( gemstonesda, 10, cash5 ),
			new( teutonitems, 50 ),
			new( scrollsthaublood, 90 )
		] ),
		class28 = new( class28, [
			new( teutonitems, 50 ),
			new( scrollsthaublood, 50 ),
			new( teutonweapons, 90 )
		] ),
		class29 = new( class29, [
			new( teutonweapons, 50 ),
			new( teutonarmor, 90 )
		] ),
		class30 = new( class30, [
			new( teutonweapenh, 50 ),
			new( teutonarmorenh, 100 )
		] ),
		class31 = new( class31, [
			new( hausloot, 30, cash5 ),
			new( teutonammo, 30 ),
			new( hausitems, 30 ),
			new( scrollsthauflame, 30 ),
			new( hausweapons, 30 ),
			new( hausarmor, 90 )
		] ),
		class32 = new( class32, [
			new( hausloot, 50, cash5 ),
			new( gemstonesda, 10, cash6 ),
			new( hausitems, 50 ),
			new( scrollsthauflame, 90 )
		] ),
		class33 = new( class33, [
			new( hausitems, 50 ),
			new( scrollsthauflame, 50 ),
			new( hausweapons, 90 )
		] ),
		class34 = new( class34, [
			new( hausweapons, 50 ),
			new( hausarmor, 90 )
		] ),
		class35 = new( class35, [
			new( hausweapenh, 50 ),
			new( hausarmorenh, 100 )
		] ),
		class36 = new( class36, [
			new( vysloot, 30, cash6 ),
			new( vysammo, 30 ),
			new( vysitems, 30 ),
			new( scrollsthauhands, 30 ),
			new( vysweapons, 30 ),
			new( vysarmor, 90 )
		] ),
		class37 = new( class37, [
			new( vysloot, 50, cash6 ),
			new( gemstonesda, 10, cash7 ),
			new( vysitems, 50 ),
			new( scrollsthauhands, 90 )
		] ),
		class38 = new( class38, [
			new( vysitems, 50 ),
			new( scrollsthauhands, 50 ),
			new( vysweapons, 90 )
		] ),
		class39 = new( class39, [
			new( vysweapons, 50 ),
			new( vysarmor, 90 )
		] ),
		class40 = new( class40, [
			new( vysweapenh, 50 ),
			new( vysarmorenh, 100 )
		] ),
		class41 = new( class41, [
			new( socloot, 30, cash1 ),
			new( socammo, 30 ),
			new( socitems, 30 ),
			new( scrollsthaurituals, 30 ),
			new( socweapons, 90 )
		] ),
		class42 = new( class42, [
			new( socloot, 50, cash1 ),
			new( gemstonesmd, 10, cash2 ),
			new( socitems, 50 ),
			new( scrollsthaurituals, 90 )
		] ),
		class43 = new( class43, [
			new( socitems, 50 ),
			new( scrollsthaurituals, 50 ),
			new( socweapons, 90 )
		] ),
		class44 = new( class44, [
			new ( socweapons, 90 )
		] ),
		class45 = new( class45, [
			new( socweapenh, 100 )
		] ),
		class46 = new( class46, [
			new( setloot, 30, cash2 ),
			new( setammo, 30 ),
			new( setitems, 30 ),
			new( scrollsserpentis, 30 ),
			new( setweapons, 30 ),
			new( setarmor, 90 )
		] ),
		class47 = new( class47, [
			new( setloot, 50, cash2 ),
			new( gemstonesmd, 10, cash3 ),
			new( setitems, 50 ),
			new( scrollsserpentis, 90 )
		] ),
		class48 = new( class48, [
			new( setitems, 50 ),
			new( scrollsserpentis, 50 ),
			new( setweapons, 90 )
		] ),
		class49 = new( class49, [
			new( setweapons, 50 ),
			new( setarmor, 90 )
		] ),
		class50 = new( class50, [
			new( setweapenh, 50 ),
			new( setarmorenh, 100 )
		] ),
		class51 = new( class51, [
			new( towerloot, 30, cash3 ),
			new( towerammo, 30 ),
			new( toweritems, 30 ),
			new( scrollsoffense, 30 ),
			new( towerweapons, 30 ),
			new( towerarmor, 90 )
		] ),
		class52 = new( class52, [
			new( towerloot, 50, cash3 ),
			new( gemstonesmd, 10, cash4 ),
			new( toweritems, 50 ),
			new( scrollsoffense, 90 )
		] ),
		class53 = new( class53, [
			new( toweritems, 50 ),
			new( scrollsoffense, 50 ),
			new( towerweapons, 90 )
		] ),
		class54 = new( class54, [
			new( towerweapons, 50 ),
			new( towerarmor, 90 )
		] ),
		class55 = new( class55, [
			new( towerweapenh, 50 ),
			new( towerarmorenh, 100 )
		] ),
		class56 = new( class56, [
			new( sewerloot, 30, cash4 ),
			new( sewerammo, 30 ),
			new( seweritems, 30 ),
			new( scrollsdominate, 30 ),
			new( sewerweapons, 30 ),
			new( sewerarmor, 90 )
		] ),
		class57 = new( class57, [
			new( sewerloot, 50, cash4 ),
			new( gemstonesmd, 10, cash5 ),
			new( seweritems, 50 ),
			new( scrollsdominate, 90 )
		] ),
		class58 = new( class58, [
			new( seweritems, 50 ),
			new( scrollsdominate, 50 ),
			new( sewerweapons, 90 )
		] ),
		class59 = new( class59, [
			new( sewerweapons, 50 ),
			new( sewerarmor, 90 )
		] ),
		class60 = new( class60, [
			new( sewerweapenh, 50 ),
			new( sewerarmorenh, 100 )
		] ),
		class61 = new( class61, [
			new( wareloot, 30, cash5 ),
			new( wareammo, 30 ),
			new( wareitems, 30 ),
			new( scrollsmortis, 30 ),
			new( wareweapons, 30 ),
			new( warearmor, 90 )
		] ),
		class62 = new( class62, [
			new( wareloot, 50, cash5 ),
			new( gemstonesmd, 10, cash6 ),
			new( wareitems, 50 ),
			new( scrollsmortis, 90 )
		] ),
		class63 = new( class63, [
			new( wareitems, 50 ),
			new( scrollsmortis, 50 ),
			new( wareweapons, 90 )
		] ),
		class64 = new( class64, [
			new( wareweapons, 50 ),
			new( warearmor, 90 )
		] ),
		class65 = new( class65, [
			new( wareweapenh, 50 ),
			new( warearmorenh, 100 )
		] ),
		class66 = new( class66, [
			new( orsiloot, 30, cash6 ),
			new( orsiammo, 30 ),
			new( orsiitems, 30 ),
			new( scrollspresence, 30 ),
			new( orsiweapons, 30 ),
			new( orsiarmor, 90 )
		] ),
		class67 = new( class67, [
			new( orsiloot, 50, cash6 ),
			new( gemstonesmd, 10, cash7 ),
			new( orsiitems, 50 ),
			new( scrollspresence, 90 )
		] ),
		class68 = new( class68, [
			new( orsiitems, 50 ),
			new( scrollsoffense, 50 ),
			new( orsiweapons, 90 )
		] ),
		class69 = new( class69, [
			new( orsiweapons, 50 ),
			new( orsiarmor, 90 )
		] ),
		class70 = new( class70, [
			new( orsiweapenh, 50 ),
			new( orsiarmorenh, 100 )
		] ),
		class71 = new( class71, [
			new( cathloot, 30, cash7 ),
			new( cathammo, 30 ),
			new( cathitems, 30 ),
			new( scrollsmortis, 30 ),
			new( cathweapons, 30 ),
			new( catharmor, 90 )
		] ),
		class72 = new( class72, [
			new( cathloot, 50, cash7 ),
			new( gemstonesmd, 10, cash7 ),
			new( cathitems, 50 ),
			new( scrollsmortis, 90 )
		] ),
		class73 = new( class73, [
			new( cathitems, 50 ),
			new( scrollsoffense, 50 ),
			new( cathweapons, 90 )
		] ),
		class74 = new( class74, [
			new( cathweapons, 50 ),
			new( catharmor, 90 )
		] ),
		class75 = new( class75, [
			new( cathweapenh, 50 ),
			new( catharmorenh, 100 )
		] ),
		class76 = new( class76, [
			new( scrollssense, 100 )
		] ),
		class87 = new( class87, [
			new( baseloot, 0 )
		] ),
		class131 = new( class131, [
			new( baseloot, 10, cash0 ),
			new( silvercoins, 30, cash0 ),
			new( daggersda, 10 )
		] ),
		class132 = new( class132, [
			new( cashda, 30, cash0 ),
			new( arrowsda, 10 )
		] ),
		class133 = new( class133, [
			new( jewelryda, 10, cash0 ),
			new( cashda, 30, cash0 ),
			new( swordsda, 10 )
		] ),
		class141 = new( class141, [
			new( jewelryda, 10, cash1 ),
			new( cashda, 50, cash2 ),
			new( daggersda, 10 )
		] ),
		class142 = new( class142, [
			new( jewelryda, 10, cash1 ),
			new( cashda, 30, cash2 ),
			new( arrowsda, 10 )
		] ),
		class143 = new( class143, [
			new( jewelryda, 10, cash1 ),
			new( cashda, 30, cash2 ),
			new( boltsda, 10 )
		] ),
		class151 = new( class151, [
			new( jewelrymd, 10, cash1 ),
			new( cashuk, 30, cash2 )
		] ),
		class152 = new( class152, [
			new( jewelrymd, 15, cash1 ),
			new( cashuk, 13, cash2 ),
			new( revolverrounds, 30 ),
			new( lightfirearms, 10 )
		] ),
		class153 = new( class153, [
			new( jewelrymd, 15, cash1 ),
			new( cashuk, 13, cash2 ),
			new( pistolclip, 20 ),
			new( lightfirearms, 10 )
		] ),
		class154 = new( class154, [
			new( jewelrymd, 15, cash2 ),
			new( cashuk, 13, cash2 ),
			new( rifleclip, 20 ),
			new( medfirearms, 10 )
		] ),
		class155 = new( class155, [
			new( jewelrymd, 15, cash2 ),
			new( cashuk, 13, cash2 ),
			new( shotgunshells, 20 ),
			new( medfirearms, 10 )
		] ),
		class156 = new( class156, [
			new( jewelrymd, 15, cash2 ),
			new( cashuk, 13, cash2 ),
			new( submachineclip, 20 ),
			new( heavyfirearms, 10 )
		] ),
		class157 = new( class157, [
			new( jewelrymd, 15, cash2 ),
			new( cashuk, 13, cash2 ),
			new( assaultclip, 20 ),
			new( heavyfirearms, 10 )
		] ),
		class158 = new( class158, [
			new( jewelrymd, 15, cash2 ),
			new( cashuk, 13, cash2 ),
			new( bluntmd, 20 ),
			new( lightfirearms, 10 )
		] ),
		class161 = new( class161, [
			new( jewelrymd, 10, cash2 ),
			new( cashus, 20, cash2 )
		] ),
		class162 = new( class162, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( revolverrounds, 20 ),
			new( lightfirearms, 10 )
		] ),
		class163 = new( class163, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( pistolclip, 20 ),
			new( lightfirearms, 10 )
		] ),
		class164 = new( class164, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( rifleclip, 20 ),
			new( lightfirearms, 10 )
		] ),
		class165 = new( class165, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( shotgunshells, 20 ),
			new( lightfirearms, 10 )
		] ),
		class166 = new( class166, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( submachineclip, 20 ),
			new( lightfirearms, 10 )
		] ),
		class167 = new( class167, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( assaultclip, 20 ),
			new( heavyfirearms, 10 )
		] ),
		class168 = new( class168, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( bluntmd, 20 ),
			new( lightfirearms, 10 )
		] ),
		class169 = new( class169, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( launchgrenades, 20 ),
			new( lightfirearms, 10 )
		] ),
		class170 = new( class170, [
			new( jewelrymd, 15, cash2 ),
			new( cashus, 13, cash2 ),
			new( rockets, 20 ),
			new( lightfirearms, 10 )
		] ),
		class171 = new( class171, [
			new( potionhealth, 30 ),
			new( potion, 30 )
		] ),
		class172 = new( class172, [
			new( potion, 100 )
		] ),
		class173 = new( class173, [
			new( potionhealth, 100 )
		] ),
		class174 = new( class174, [
			new( potionpoison, 100 )
		] ),
		class175 = new( class175, [
			new( potiondisease, 100 )
		] ),
		class181 = new( class181, [
			new( vitaecommon, 100 )
		] ),
		class182 = new( class182, [
			new( vitaerare, 100 )
		] ),
		class183 = new( class183, [
			new( vitaecommon, 90 ),
			new( vitaerare, 100 )
		] ),
		class184 = new( class184, [
			new( vitaemodern, 100 )
		] ),
		class190 = new( class190, [
			new( scrollsteleport, 100 )
		] ),
		class191 = new( class191, [
			new( scrollsawaken, 100 )
		] ),
		class192 = new( class192, [
			new( scrollsidentify, 100 )
		] ),
		class193 = new( class193, [
			new( scrollsgeneric, 70 ),
			new( scrollsoffense, 100 )
		] ),
		class194 = new( class194, [
			new( scrollsgeneric, 25 ),
			new( scrollscelerity, 33 ),
			new( scrollspotence, 50 ),
			new( scrollspresence, 100 )
		] ),
		class195 = new( class195, [
			new( scrollsgeneric, 25 ),
			new( scrollsauspex, 33 ),
			new( scrollsfortitude, 25 ),
			new( scrollsmortis, 100 )
		] ),
		class196 = new( class196, [
			new( scrollsgeneric, 25 ),
			new( scrollsanimal, 33 ),
			new( scrollsobfuscate, 50 ),
			new( scrollspotence, 100 )
		] ),
		class197 = new( class197, [
			new( scrollsgeneric, 25 ),
			new( scrollsauspex, 20 ),
			new( scrollsdominate, 30 ),
			new( scrollsthaublood, 30 ),
			new( scrollsthauflame, 30 ),
			new( scrollsthauhands, 50 ),
			new( scrollsthaurituals, 100 )
		] ),
		class198 = new( class198, [
			new( scrollsgeneric, 25 ),
			new( scrollsobfuscate, 33 ),
			new( scrollspresence, 50 ),
			new( scrollsserpentis, 10 )
		] ),
		class199 = new( class199, [
			new( scrollsanimal, 3 ),
			new( scrollsauspex, 4 ),
			new( scrollscelerity, 5 ),
			new( scrollscommon, 6 ),
			new( scrollsdementation, 7 ),
			new( scrollsdominate, 8 ),
			new( scrollsfortitude, 9 ),
			new( scrollsmortis, 10 ),
			new( scrollsobfuscate, 10 ),
			new( scrollsobtenebration, 12 ),
			new( scrollspotence, 13 ),
			new( scrollspresence, 14 ),
			new( scrollsprotean, 17 ),
			new( scrollsserpentis, 20 ),
			new( scrollsthaublood, 25 ),
			new( scrollsthauflame, 33 ),
			new( scrollsthauhands, 50 ),
			new( scrollsthaurituals, 100 )
		] ),
		class201 = new( class201, [
			new( scrollsnumina, 100 )
		] ),
		class202 = new( class202, [
			new( kineitems, 100 )
		] ),
		class205 = new( class205, [
			new( artamulet, 12 ),
			new( artbracelet, 14 ),
			new( artpotion, 17 ),
			new( artring, 20 ),
			new( artarmor, 25 ),
			new( artgauntlet, 33 ),
			new( artsword, 50 ),
			new( artweapon, 100 )
		] ),
		class211 = new( class211, [
			new( lightarmorda, 100 )
		] ),
		class212 = new( class212, [
			new( heavyarmorda, 100 )
		] ),
		class213 = new( class213, [
			new( lightarmorda, 70 ),
			new( heavyarmorda, 100 )
		] ),
		class214 = new( class214, [
			new( rangedweapons, 100 )
		] ),
		class215 = new( class215, [
			new( meleeweapons, 100 )
		] ),
		class216 = new( class216, [
			new( swordsda, 100 )
		] ),
		class217 = new( class217, [
			new( daggersda, 100 )
		] ),
		class218 = new( class218, [
			new( bluntda, 100 )
		] ),
		class219 = new( class219, [
			new( archeryda, 100 )
		] ),
		class220 = new( class220, [
			new( polearms, 100 )
		] ),
		class221 = new( class221, [
			new( quickuseda, 100 )
		] ),
		class222 = new( class222, [
			new( ammoda, 100 )
		] ),
		class223 = new( class223, [
			new( quickuseda, 50 ),
			new( ammoda, 100 )
		] ),
		class224 = new( class224, [
			new( lightarmorda, 10 ),
			new( heavyarmorda, 12 ),
			new( shieldsda, 13 ),
			new( headgearda, 14 ),
			new( neckwearda, 17 ),
			new( daggersda, 20 ),
			new( bluntda, 25 ),
			new( archeryda, 33 ),
			new( quickuseda, 50 ),
			new( ammoda, 100 )
		] ),
		class225 = new( class225, [
			new( cashda, 14 ),
			new( jewelryda, 17 ),
			new( gemstonesda, 20 ),
			new( potion, 25 ),
			new( vitaecommon, 33 ),
			new( scrollscommon, 50 ),
			new( ammoda, 100 )
		] ),
		class226 = new( class226, [
			new( baseweapenh, 14 ),
			new( petrinweapenh, 17 ),
			new( josefweapenh, 20 ),
			new( ardanweapenh, 25 ),
			new( stephanweapenh, 33 ),
			new( teutonweapenh, 50 ),
			new( hausweapenh, 100 )
		] ),
		class227 = new( class227, [
			new( basearmorenh, 14 ),
			new( petrinarmorenh, 17 ),
			new( josefarmorenh, 20 ),
			new( ardanarmorenh, 25 ),
			new( stephanarmorenh, 33 ),
			new( teutonarmorenh, 50 ),
			new( hausarmorenh, 100 )
		] ),
		class228 = new( class228, [
			new( silvercoins, 10, cash0 ),
			new( barrelloot, 20 ),
			new( potion, 20 )
		] ),
		class229 = new( class229, [
			new( stephanweapenh, 30 ),
			new( teutonweapenh, 50 ),
			new( hausweapenh, 100 )
		] ),
		class230 = new( class230, [
			new( artamulet, 25 ),
			new( artbracelet, 33 ),
			new( artpotion, 50 ),
			new( artring, 100 )
		] ),
		class231 = new( class231, [
			new( artarmor, 25 ),
			new( artgauntlet, 33 ),
			new( artsword, 50 ),
			new( artweapon, 100 )
		] ),
		class233 = new( class233, [
			new( lightarmormd, 70 ),
			new( heavyarmormd, 100 )
		] ),
		class234 = new( class234, [
			new( lightfirearms, 100 )
		] ),
		class235 = new( class235, [
			new( heavyfirearms, 100 )
		] ),
		class236 = new( class236, [
			new( artillery, 90 )
		] ),
		class237 = new( class237, [
			new( jewelrymd, 10, cash2 ),
			new( cashuk, 30, cash1 ),
			new( daggersmd, 30 ),
			new( lightammo, 50 ),
			new( lightfirearms, 90 )
		] ),
		class238 = new( class238, [
			new( quickusemd, 10 ),
			new( archeryammomd, 10 ),
			new( lightammo, 80 ),
			new( heavyammo, 100 )
		] ),
		class239 = new( class239, [
			new( lightarmormd, 10 ),
			new( heavyarmormd, 10 ),
			new( shieldsmd, 10 ),
			new( headgearmd, 10 ),
			new( neckwearmd, 10 ),
			new( daggersmd, 10 ),
			new( bluntmd, 10 ),
			new( archerymd, 10 ),
			new( lightfirearms, 10 ),
			new( heavyfirearms, 10 ),
			new( quickusemd, 100 )
		] ),
		class240 = new( class240, [
			new( lightarmormd, 10 ),
			new( heavyarmormd, 10 ),
			new( shieldsmd, 10 ),
			new( headgearmd, 10 ),
			new( quickusemd, 100 )
		] ),
		class241 = new( class241, [
			new( heavyfirearms, 20 ),
			new( heavyammo, 100 )
		] ),
		class242 = new( class242, [
			new( artillery, 20 ),
			new( artilleryammo, 100 )
		] ),
		class243 = new( class243, [
			new( jewelrymd, 10, cash2 ),
			new( cashuk, 30, cash1 ),
			new( vitaecommon, 30 ),
			new( scrollscommon, 30 ),
			new( lightammo, 50 ),
			new( lightfirearms, 90 )
		] ),
		class244 = new( class244, [
			new( monetaryus, 10 ),
			new( jewelrymd, 10 ),
			new( gemstonesmd, 10 ),
			new( potion, 10 ),
			new( vitaecommon, 10 ),
			new( scrollscommon, 10 ),
			new( scrollsgeneric, 10 ),
			new( lightammo, 50 ),
			new( heavyammo, 100 )
		] ),
		class245 = new( class245, [
			new( socweapenh, 40 ),
			new( setweapenh, 30 ),
			new( towerweapenh, 30 ),
			new( sewerweapenh, 30 ),
			new( wareweapenh, 30 ),
			new( orsiweapenh, 50 ),
			new( cathweapenh, 100 )
		] ),
		class246 = new( class246, [
			new( socarmorenh, 40 ),
			new( setarmorenh, 30 ),
			new( towerarmorenh, 30 ),
			new( sewerarmorenh, 30 ),
			new( warearmorenh, 30 ),
			new( orsiarmorenh, 50 ),
			new( catharmorenh, 100 )
		] ),
		class247 = new( class247, [
			new( stephanweapenh, 100 )
		] ),
		class248 = new( class248, [
			new( socweapenh, 50 ),
			new( setweapenh, 50 ),
			new( towerweapenh, 100 )
		] ),
		class255 = new( class255, [
			new( potion, 0 )
		] );
}