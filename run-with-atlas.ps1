# Loads .env and starts the app. Edit .env first: set YOUR_CLUSTER to your Atlas cluster host.
$envFile = Join-Path $PSScriptRoot ".env"
if (-not (Test-Path $envFile)) {
    Write-Error ".env file not found. Copy .env.example and fill in your Atlas details."
    exit 1
}

Get-Content $envFile | ForEach-Object {
    if ($_ -match '^\s*#' -or $_ -notmatch '^\s*([^#=]+)=(.*)$') { return }
    $name = $matches[1].Trim()
    $value = $matches[2].Trim()
    Set-Item -Path "env:$name" -Value $value
}

if ($env:MONGODB_URI -match 'YOUR_CLUSTER') {
    Write-Error "Edit .env: replace YOUR_CLUSTER with your Atlas host (e.g. cluster0.abc12.mongodb.net)."
    exit 1
}

Set-Location $PSScriptRoot
mvn spring-boot:run
