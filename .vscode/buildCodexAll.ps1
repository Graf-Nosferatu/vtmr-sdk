param( [string]$outPath = "" )

$sdkRoot	= Get-Location
$javacPath	= "$sdkRoot\.vscode\jdk\bin\javac.exe"

if( $outPath -eq "" ) {
	$outPath = "$sdkRoot\..\vtmr-mod\Codex"
}

if( Test-Path $outPath ) {
	Remove-Item -Path $outPath -Recurse -Force
}
New-Item -ItemType Directory -Path $outPath -Force | Out-Null

$lib			= "$sdkRoot\.vscode\jdk\lib\classes.zip"

$core			= "$sdkRoot\Codex\core"
$disciplines	= "$sdkRoot\Codex\disciplines"
$sp				= "$sdkRoot\Codex\sp"
$mp				= "$sdkRoot\Codex\mp"
$other			= "$sdkRoot\Codex\other"

$fullClasspath	= ".;$lib;$core;$disciplines;$sp;$mp;$other"

$dirs = Get-ChildItem -Path "$sdkRoot\Codex" -Recurse -Directory

foreach( $dir in $dirs ) {
	$files = Get-ChildItem -Path $dir.FullName -Filter "*.java"
	if( $files.Count -gt 0 ) {
		Write-Host "Codex: $($dir.Name) building..."

		& $javacPath -d $outPath -classpath $fullClasspath $files.FullName
	}
}

Write-Host "Codex: builded."