$sdkRoot		= Get-Location
$MapsDirectory	= Join-Path $sdkRoot "Maps"
$MapCSharpFile	= Join-Path $MapsDirectory "Maps.cs"

$templateRegex = [regex]::new( '"TEMPLATE"[\t ]+"(?<template>[^\s"]+)"', 'Compiled' )
$treasureClassRegex = [regex]::new( '"\*TREASURECLASS"[\t ]+"(?<treasureClass>[^\s"]+)"', 'Compiled' )

$sb = [System.Text.StringBuilder]::new()
[void]$sb.AppendLine( 'using Sdk.Templates;' )
[void]$sb.AppendLine( 'using Sdk.Treasures;' )
[void]$sb.AppendLine()
[void]$sb.AppendLine( 'namespace Sdk.Maps;' )
[void]$sb.AppendLine()
[void]$sb.AppendLine( 'partial class Map' )
[void]$sb.AppendLine( '{' )

$mapFiles = [System.IO.Directory]::GetFiles( $MapsDirectory, '*.map', [System.IO.SearchOption]::AllDirectories )

foreach( $mapFilePath in $mapFiles ) {
	$mapName = [System.IO.Path]::GetFileNameWithoutExtension( $mapFilePath ).ToUpper()
	$content = [System.IO.File]::ReadAllText( $mapFilePath )

	[void]$sb.AppendLine( "	public static readonly Map ${mapName} = new() {" )

	$matches = $templateRegex.Matches( $content )
	if( $matches.Count -gt 0 ) {
		[void]$sb.AppendLine( "		Templates = [" )
		$uniqueTemplates = [System.Collections.Generic.HashSet[string]]::new( [System.StringComparer]::OrdinalIgnoreCase )

		foreach( $match in $matches ) {
			[void]$uniqueTemplates.Add( $match.Groups['template'].Value.ToLower() )
		}

		foreach( $template in $uniqueTemplates ) {
			[void]$sb.AppendLine( "			Template.${template}," )
		}

		[void]$sb.AppendLine( "		]," )
	}

	$matches = $treasureClassRegex.Matches( $content )
	if( $matches.Count -gt 0 ) {
		[void]$sb.AppendLine( "		TreasureClasses = [" )
		$uniqueClasses = [System.Collections.Generic.HashSet[string]]::new( [System.StringComparer]::OrdinalIgnoreCase )

		foreach( $match in $matches ) {
			[void]$uniqueClasses.Add( $match.Groups['treasureClass'].Value.ToLower() )
		}

		foreach( $treasureClass in $uniqueClasses ) {
			[void]$sb.AppendLine( "			TreasureClass.class${treasureClass}," )
		}

		[void]$sb.AppendLine( "		]" )
	}

	[void]$sb.AppendLine( "	};" )
}

[void]$sb.Append( '}' )

[System.IO.File]::WriteAllText( $MapCSharpFile, $sb.ToString(), [System.Text.Encoding]::UTF8 )

Write-Host "Maps: generated." -ForegroundColor Green