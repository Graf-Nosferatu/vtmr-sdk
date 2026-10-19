$sdkRoot	= Get-Location
$csFile		= Join-Path $sdkRoot "Effects\Effects.cs"
$javaFile	= Join-Path $sdkRoot "Codex\core\CodexEffect.java"

$content = Get-Content $csFile -Raw
# ef_XXX = new( ef_XXX )
$pattern = '\b(ef_[a-zA-Z0-9_]+)\s*=\s*new\s*\(\s*\1\s*\)'
$matches = [regex]::Matches( $content, $pattern )

$effectNames = $matches | ForEach-Object { $_.Groups[1].Value } | Sort-Object

$sb = [System.Text.StringBuilder]::new()

[void]$sb.AppendLine( "/**" )
[void]$sb.AppendLine( " * Auto-generated effect string constants from Effects.cs" )
[void]$sb.AppendLine( "*/" )
[void]$sb.AppendLine( "public class CodexEffect" )
[void]$sb.AppendLine( "{" )
[void]$sb.AppendLine( "`tpublic static final String" )

foreach( $name in $effectNames ) {
	$isLast = ( $i++ -eq $effectNames.Count - 1 )

	[void]$sb.AppendLine( "`t`t$($name.ToUpper().PadRight( 32 )) = `"$name`"$(if( $isLast ){ ";" } else{ "," } )" )
}

[void]$sb.Append( "}" )

[System.IO.File]::WriteAllText( $javaFile, $sb.ToString(), ( New-Object System.Text.UTF8Encoding $false ) )

Write-Host "CodexEffect: generated."