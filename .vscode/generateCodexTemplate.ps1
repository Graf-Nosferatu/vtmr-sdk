$sdkRoot	= Get-Location
$csFile		= Join-Path $sdkRoot "Templates\Templates.cs"
$javaFile	= Join-Path $sdkRoot "Codex\core\CodexTemplate.java"

$content = Get-Content $csFile -Raw
# TEMPLATE_NAME = new( FILE, TEMPLATE_NAME )
# TEMPLATE_NAME = new( FILE, TEMPLATE_NAME, COMMENT )
# \1 backreference keeps only "NAME = new( FILE, NAME )" and skips nested new( 0, 50, 80 )
$pattern = '(?<![\w@])@?([A-Za-z_][A-Za-z0-9_]*)\s*=\s*new\(\s*[A-Za-z_][A-Za-z0-9_]*,\s*@?\1\s*(?:,|\))'
$matches = [regex]::Matches( $content, $pattern )

$templateNames = $matches | ForEach-Object { $_.Groups[1].Value } | Sort-Object -Unique
#$templateNames = $matches | ForEach-Object { $_.Value } | Sort-Object -Unique

$sb = [System.Text.StringBuilder]::new()

[void]$sb.AppendLine( "/**" )
[void]$sb.AppendLine( " * Auto-generated effect string constants from Templates.cs" )
[void]$sb.AppendLine( "*/" )
[void]$sb.AppendLine( "public class CodexTemplate" )
[void]$sb.AppendLine( "{" )
[void]$sb.AppendLine( "`tpublic static final String" )

foreach( $name in $templateNames ) {
	$isLast = ( $i++ -eq $templateNames.Count - 1 )

	[void]$sb.AppendLine( "`t`t$($name.ToUpper().PadRight( 32 )) = `"$name`"$(if( $isLast ){ ";" } else{ "," } )" )
}

[void]$sb.Append( "}" )

[System.IO.File]::WriteAllText( $javaFile, $sb.ToString(), ( New-Object System.Text.UTF8Encoding $false ) )

Write-Host "CodexTemplate: generated."