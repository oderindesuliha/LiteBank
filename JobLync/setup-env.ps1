# JobLync Environment Variables Setup Script
# Run this script to set up environment variables for the JobLync application

Write-Host "JobLync Environment Variables Setup" -ForegroundColor Green
Write-Host "==================================" -ForegroundColor Green
Write-Host ""

# Database Configuration
Write-Host "Database Configuration:" -ForegroundColor Yellow
$env:DB_URL = Read-Host "Enter Database URL (default: jdbc:postgresql://localhost:5432/joblync)"
if (-not $env:DB_URL) { $env:DB_URL = "jdbc:postgresql://localhost:5432/joblync" }

$env:DB_USERNAME = Read-Host "Enter Database Username (default: joblync)"
if (-not $env:DB_USERNAME) { $env:DB_USERNAME = "joblync" }

$env:DB_PASSWORD = Read-Host "Enter Database Password (default: joblync)"
if (-not $env:DB_PASSWORD) { $env:DB_PASSWORD = "joblync" }

Write-Host ""
Write-Host "JWT Configuration:" -ForegroundColor Yellow
$env:JWT_SECRET = Read-Host "Enter JWT Secret (press Enter to generate a secure one)"
if (-not $env:JWT_SECRET) { 
    # Generate a secure JWT secret
    $bytes = New-Object byte[] 64
    $rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
    $rng.GetBytes($bytes)
    $env:JWT_SECRET = [Convert]::ToBase64String($bytes)
    Write-Host "Generated secure JWT secret: $env:JWT_SECRET" -ForegroundColor Cyan
}

$env:JWT_EXPIRATION = Read-Host "Enter JWT Expiration in milliseconds (default: 86400000)"
if (-not $env:JWT_EXPIRATION) { $env:JWT_EXPIRATION = "86400000" }

$env:JWT_ALGORITHM = Read-Host "Enter JWT Algorithm (default: HS512)"
if (-not $env:JWT_ALGORITHM) { $env:JWT_ALGORITHM = "HS512" }

$env:JWT_ISSUER = Read-Host "Enter JWT Issuer (default: JobLync)"
if (-not $env:JWT_ISSUER) { $env:JWT_ISSUER = "JobLync" }

$env:JWT_AUDIENCE = Read-Host "Enter JWT Audience (default: JobLyncUsers)"
if (-not $env:JWT_AUDIENCE) { $env:JWT_AUDIENCE = "JobLyncUsers" }

Write-Host ""
Write-Host "Email Configuration:" -ForegroundColor Yellow
$env:MAIL_HOST = Read-Host "Enter Mail Host (default: smtp.gmail.com)"
if (-not $env:MAIL_HOST) { $env:MAIL_HOST = "smtp.gmail.com" }

$env:MAIL_PORT = Read-Host "Enter Mail Port (default: 587)"
if (-not $env:MAIL_PORT) { $env:MAIL_PORT = "587" }

$env:MAIL_USERNAME = Read-Host "Enter Mail Username (your email address)"
$env:MAIL_PASSWORD = Read-Host "Enter Mail Password (app-specific password)"

Write-Host ""
Write-Host "Environment variables have been set:" -ForegroundColor Green
Write-Host "DB_URL: $env:DB_URL"
Write-Host "DB_USERNAME: $env:DB_USERNAME"
Write-Host "JWT_SECRET: $($env:JWT_SECRET.Substring(0, 10))..." # Show only first 10 characters for security
Write-Host "MAIL_HOST: $env:MAIL_HOST"
Write-Host "MAIL_USERNAME: $env:MAIL_USERNAME"
Write-Host ""
Write-Host "You can now run the application with: ./mvnw spring-boot:run" -ForegroundColor Green