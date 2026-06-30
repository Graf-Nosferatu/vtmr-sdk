param( [string]$outPath = "" )

$sdkRoot	= Get-Location
$javacPath	= "$sdkRoot\.vscode\jdk\bin\javac.exe"
$listPath	= "$sdkRoot\.vscode\buildCodexList.txt"

if( $outPath -eq "" ) {
	$outPath = "$sdkRoot\..\vtmr-mod\Codex"
}

if( Test-Path $outPath ) {
	Remove-Item -Path $outPath -Recurse -Force
}
New-Item -ItemType Directory -Path $outPath -Force | Out-Null

$javaFiles = New-Object System.Collections.Generic.List[string]

$entries = Get-Content $listPath | Where-Object { $_ -notmatch '^\s*#' -and $_ -match '\S' }
foreach( $path in $entries )
{
	if( Test-Path $path )
	{
		$files = @(Get-ChildItem -Path $path -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName)
		if( $files.Count -gt 0 ) {
			$javaFiles.AddRange( [string[]]$files )
		}
	}
	else {
		Write-Host "Codex: $path not found."
	}
}

if( $javaFiles.Count -gt 0 )
{
	$lib			= "$sdkRoot\.vscode\jdk\lib\classes.zip"

	$core			= "$sdkRoot\Codex\core"
	$disciplines	= "$sdkRoot\Codex\disciplines"
	$sp				= "$sdkRoot\Codex\sp"
	$mp				= "$sdkRoot\Codex\mp"
	$other			= "$sdkRoot\Codex\other"

	$fullClasspath	= ".;$lib;$core;$disciplines;$sp"

	& $javacPath -d $outPath -classpath $fullClasspath $javaFiles

	if( $LASTEXITCODE -ne 0 ) {
		Write-Host "Codex: building failed."
	}
	else {
		Write-Host "Codex: builded."
	}
}