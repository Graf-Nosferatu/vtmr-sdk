using System;
using System.IO;
using System.Linq;
using Sdk.Extensions;

namespace Sdk.Disciplines;

partial class Discipline
{
	public const String DirectoryName = "misc";


	public static File<DisciplineGroup> DisciplineGroups { get; } = new( "game.ndg" );

	public static File<Discipline> Disciplines { get; } = new( "game.ndd" );


	public static void Save()
	{
		String path = Path.Create( Sdk.ModPath, DirectoryName, DisciplineGroups.FileName );
		File.WriteAllLines( path, DisciplineGroups.Select( SaveDisciplineGroup ) );

		path = Path.Create( Sdk.ModPath, DirectoryName, Disciplines.FileName );
		File.WriteAllLines( path, Disciplines.Select( SaveDiscipline ) );
	}


	private static String SaveDisciplineGroup( DisciplineGroup disciplineGroup )
	{
		String	name	= disciplineGroup.Name;
		String	icon	= disciplineGroup.Icon;
		String	xpCost	= Saver.SaveInt32( disciplineGroup.XpCost );
		String	flags	= Saver.SaveEnum( disciplineGroup.Flags );

		return $"{name} {icon} {xpCost} {flags}";
	}


	private static String SaveDiscipline( Discipline discipline )
	{
		String	name	= discipline.Name;
		String	group	= discipline.Group;
		String	icon	= discipline.Icon;
		String	script	= discipline.Script;
		String	sound	= discipline.Sound ?? "-";
		String	flags	= Saver.SaveEnum( discipline.Flags );
		String	type	= Saver.SaveEnum( discipline.Type );
		String	minDist	= Saver.SaveSingle( discipline.Distance.Min );
		String	maxDist	= Saver.SaveSingle( discipline.Distance.Max );
		String	fData	= Saver.SaveSingle( discipline.AdditionalData );
		String	cast	= Saver.SaveEnum( discipline.Cast );
		String	xpCost	= Saver.SaveInt32( discipline.XpCost );
		String	cN		= String.Join( " ", discipline.BloodCosts.Select( Saver.SaveInt32 ) );
		String	rN		= String.Join( " ", discipline.RecastTimes.Select( Saver.SaveInt32 ) );
		String	csvN	= String.Join( " ", discipline.Checks.Select( SaveDisciplineCheck ) );

		return $"{name} {group} {icon} {script} {sound} {flags} {type} {minDist} {maxDist} {fData} {cast} {xpCost} {cN} {rN} {csvN}";
	}

	private static String SaveDisciplineCheck( DisciplineCheck? check )
	{
		String	stat	= "-";
		String	value	= "-";

		if( check != null )
		{
			stat	= Saver.SaveEnum( check.Stat );
			value	= Saver.SaveInt32( check.Value );
		}

		return $"{stat} {value}";
	}
}