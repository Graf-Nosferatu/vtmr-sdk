using Sdk.Disciplines;
using Sdk.Templates;

namespace Sdk.Clans;

partial class Clan
{
	public static readonly Clan
		brujah = new( brujah ) {
			Icon		= "brujahIcon",
			Beckon		= nameof( Template.petdemonhound ),
			Shape		= nameof( Template.wolf_grey ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_presence ),
				nameof( Discipline.dgrp_potence ),
				nameof( Discipline.dgrp_celerity ),
			]
		},
		cappadocian = new( cappadocian ) {
			Icon		= "cappadocianIcon",
			Beckon		= nameof( Template.petzombu ),
			Shape		= nameof( Template.wolf_black ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_auspex ),
				nameof( Discipline.dgrp_fortitude ),
				nameof( Discipline.dgrp_mortis ),
			],
			Stats		= {
				[Stat.Appearance] = 25,
			}
		},
		gangrel = new( gangrel ) {
			Icon		= "gangrelIcon",
			Beckon		= nameof( Template.petwolf ),
			Shape		= nameof( Template.wolf_red ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.DecrementAppearanceOnFrenzy,
			Groups		= [
				nameof( Discipline.dgrp_animalism ),
				nameof( Discipline.dgrp_fortitude ),
				nameof( Discipline.dgrp_protean ),
			]
		},
		nosferatu = new( nosferatu ) {
			Icon		= "nosferatuIcon",
			Beckon		= nameof( Template.petrat ),
			Shape		= nameof( Template.wolf_black ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.CanNotIncreaseAppearance,
			Groups		= [
				nameof( Discipline.dgrp_animalism ),
				nameof( Discipline.dgrp_obfuscate ),
				nameof( Discipline.dgrp_potence ),
			],
			Stats		= {
				[Stat.Appearance] = 0,
			}
		},
		toreador = new( toreador ) {
			Icon		= "toreadorIcon",
			Beckon		= nameof( Template.petghoulspider ),
			Shape		= nameof( Template.wolf_white ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_auspex ),
				nameof( Discipline.dgrp_celerity ),
				nameof( Discipline.dgrp_presence ),
			]
		},
		assamite = new( assamite ) {
			Icon		= "assamiteIcon",
			Beckon		= nameof( Template.petviper ),
			Shape		= nameof( Template.wolf_red ),
			Flags		= ClanFlags.NotMultiplayer,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				null,
				null,
				null,
			]
		},
		giovanni = new( giovanni ) {
			Icon		= "giovanniIcon",
			Beckon		= nameof( Template.petalligator ),
			Shape		= nameof( Template.wolf_black ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_dominate ),
				nameof( Discipline.dgrp_mortis ),
				nameof( Discipline.dgrp_potence ),
			]
		},
		lasombra = new( lasombra ) {
			Icon		= "lasombraIcon",
			Beckon		= nameof( Template.petdarkhunter ),
			Shape		= nameof( Template.wolf_black ),
			Flags		= ClanFlags.NotMultiplayer,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_dominate ),
				nameof( Discipline.dgrp_obtenebration ),
				nameof( Discipline.dgrp_potence ),
			]
		},
		malkavian = new( malkavian ) {
			Icon		= "malkavianIcon",
			Beckon		= nameof( Template.petrat ),
			Shape		= nameof( Template.wolf_white ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_auspex ),
				nameof( Discipline.dgrp_dementation ),
				nameof( Discipline.dgrp_obfuscate ),
			]
		},
		ravnos = new( ravnos ) {
			Icon		= "ravnosIcon",
			Beckon		= nameof( Template.petskel_sword ),
			Shape		= nameof( Template.wolf_red ),
			Flags		= ClanFlags.NotMultiplayer,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_animalism ),
				nameof( Discipline.dgrp_presence ),
				nameof( Discipline.dgrp_fortitude ),
			]
		},
		setite = new( setite ) {
			Icon		= "setiteIcon",
			Beckon		= nameof( Template.petviper ),
			Shape		= nameof( Template.wolf_grey ),
			Flags		= ClanFlags.NotMultiplayer,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_obfuscate ),
				nameof( Discipline.dgrp_presence ),
				nameof( Discipline.dgrp_serpentis ),
			]
		},
		tremere = new( tremere ) {
			Icon		= "tremereIcon",
			Beckon		= nameof( Template.pethopper ),
			Shape		= nameof( Template.wolf_black ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_auspex ),
				nameof( Discipline.dgrp_dominate ),
				nameof( Discipline.dgrp_thau_bloodpath ),
			]
		},
		tzimisce = new( tzimisce ) {
			Icon		= "tzimisceIcon",
			Beckon		= nameof( Template.petszlachta ),
			Shape		= nameof( Template.wolf_black ),
			Flags		= ClanFlags.NotMultiplayer,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_animalism ),
				nameof( Discipline.dgrp_auspex ),
				null,
			]
		},
		ventrue = new( ventrue ) {
			Icon		= "ventrueIcon",
			Beckon		= nameof( Template.petviper ),
			Shape		= nameof( Template.wolf_white ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_dominate ),
				nameof( Discipline.dgrp_fortitude ),
				nameof( Discipline.dgrp_presence ),
			]
		},
		caitiff = new( caitiff ) {
			Icon		= "caitiffIcon",
			Beckon		= nameof( Template.petskel_sword ),
			Shape		= nameof( Template._wolfshape ),
			Flags		= ClanFlags.None,
			Advantages	= AdvantageFlags.AllDisciplineGroups,
			Weaknesses	= WeaknessFlags.IncreaseDisciplineCost,
			Groups		= [
				null,
				null,
				null,
			]
		},
		human = new( human ) {
			Icon		= "humanIcon",
			Beckon		= nameof( Template.petskel_sword ),
			Shape		= null,
			Flags		= ClanFlags.NotMultiplayer,
			Advantages	= AdvantageFlags.None,
			Weaknesses	= WeaknessFlags.None,
			Groups		= [
				nameof( Discipline.dgrp_numina ),
				nameof( Discipline.dgrp_faith ),
				null,
			]
		};
}