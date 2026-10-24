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
			nameof( Template.axe ),
		] ),
		stephanweapenh = new( stephanweapenh, [
			nameof( Template.battleaxefine ),
			nameof( Template.shortbow_inc ),
		] ),
		hausweapenh = new( hausweapenh, [
			nameof( Template.longbow_inc ),
		] ),
		vysweapenh = new( vysweapenh, [
			nameof( Template.crossbow_inc ),
			nameof( Template.longbow_inc ),
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
		socammo = new( socammo, [
			nameof( Template.pistol_clip ),
			nameof( Template.arrows_md_05 ),
			nameof( Template.arrows_md_10 ),
			nameof( Template.arrows_md_20 )
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
			nameof( Template.footmanshield ),
			nameof( Template.skullcap ),
			nameof( Template.buckler ),
			nameof( Template.paddedclothing )
		] ),
		stephanarmorenh = new( stephanarmorenh, [
			nameof( Template.shield_cold ),
			nameof( Template.shield_elec ),
			nameof( Template.shield_fire ),
			nameof( Template.shield_unholy ),
		] ),
		hausarmorenh = new( hausarmorenh, [
			nameof( Template.gauntlets_cold ),
			nameof( Template.gauntlets_grounded ),
			nameof( Template.gauntlets_fire ),
			nameof( Template.greatshield_cold ),
			nameof( Template.greatshield_elec ),
			nameof( Template.greatshield_fire ),
			nameof( Template.shield_sun ),
		] ),
		vysarmorenh = new( vysarmorenh, [
			nameof( Template.fullhelm_cold ),
			nameof( Template.fullhelm_elec ),
			nameof( Template.fullhelm_fire ),
		] ),
		setarmorenh = new( setarmorenh, [
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
		rockets = new( rockets, [
			nameof( Template.rocket_pack_03 )
		] ),
		potion = new( potion, [
			nameof( Template.potionhealth ),
			nameof( Template.potionpoison ),
			nameof( Template.potiondisease )
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
		scrollscommon = new( scrollscommon, [
			nameof( Template.discscroll_bloodhealing_1 ),
			nameof( Template.discscroll_bloodpower_1 ),
			nameof( Template.discscroll_awaken_1 ),
			nameof( Template.discscroll_walktheabyss_1 ),
			nameof( Template.discscroll_spiritstouch_1 )
		] ),
		scrollsdominate = new( scrollsdominate, [
			nameof( Template.discscroll_command_1 ),
			nameof( Template.discscroll_mesmerize_1 ),
			nameof( Template.discscroll_theforgetfulmind_1 ),
			nameof( Template.discscroll_possession_1 )
		] ),
		scrollsmortis = new( scrollsmortis, [
			nameof( Template.discscroll_blackdeath_1 ),
			nameof( Template.discscroll_plaguewind_1 ),
			nameof( Template.discscroll_shamblinghordes_1 ),
			nameof( Template.discscroll_summonsoul_1 ),
			nameof( Template.discscroll_vigormortis_1 )
		] ),
		scrollspresence = new( scrollspresence, [
			nameof( Template.discscroll_awe_1 ),
			nameof( Template.discscroll_dreadgaze_1 ),
			nameof( Template.discscroll_entrancement_1 ),
			nameof( Template.discscroll_majesty_1 )
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
		daggersda = new( daggersda, [
			nameof( Template.dagger ),
			nameof( Template.dirk ),
			nameof( Template.morodagger ),
			nameof( Template.poignard )
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
		lightammo = new( lightammo, [
			nameof( Template.revolver_rounds ),
			nameof( Template.pistol_clip ),
			nameof( Template.rifle_clip_11 ),
			nameof( Template.shotgun_shells_10 )
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
		] );

	public static readonly TreasureClass
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
		class10 = new( class10, [
			new( petrinweapons, 50 ),
			new( petrinarmorenh, 100 )
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
		class21 = new( class21, [
			new( stephanloot, 30, cash3 ),
			new( stephanammo, 30 ),
			new( stephanitems, 30 ),
			new( scrollsgeneric, 30 ),
			new( stephanweapons, 30 ),
			new( stephanarmor, 90 )
		] ),
		class23 = new( class23, [
			new( stephanitems, 50 ),
			new( scrollsgeneric, 50 ),
			new( stephanweapons, 90 )
		] ),
		class25 = new( class25, [
			new( stephanweapenh, 50 ),
			new( stephanarmorenh, 100 )
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
			new( socweapons, 100 )
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
			new( setweapons, 50 ),
			new( setarmorenh, 100 )
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
			new( sewerweapons, 50 ),
			new( sewerarmorenh, 100 )
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
			new( wareweapons, 50 ),
			new( warearmorenh, 100 )
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
			new( cathweapons, 50 ),
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
		class141 = new( class141, [
			new( jewelryda, 10, cash1 ),
			new( cashda, 50, cash2 ),
			new( daggersda, 10 )
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
		class190 = new( class190, [
			new( scrollsteleport, 100 )
		] ),
		class191 = new( class191, [
			new( scrollsawaken, 100 )
		] ),
		class192 = new( class192, [
			new( scrollsidentify, 100 )
		] ),
		class228 = new( class228, [
			new( silvercoins, 10, cash0 ),
			new( barrelloot, 20 ),
			new( potion, 20 )
		] ),
		class237 = new( class237, [
			new( jewelrymd, 10, cash2 ),
			new( cashuk, 30, cash1 ),
			new( daggersmd, 30 ),
			new( lightammo, 50 ),
			new( lightfirearms, 90 )
		] ),
		class255 = new( class255, [
			new( potion, 0 )
		] );
}