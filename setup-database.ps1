# Script de Configuração do Banco de Dados PostgreSQL
# Execute este script como Administrador

Write-Host "=== Configuração do Banco de Dados PostgreSQL ===" -ForegroundColor Green

# Verificar se o PostgreSQL está instalado
Write-Host "Verificando se o PostgreSQL está instalado..." -ForegroundColor Yellow
$postgresService = Get-Service -Name "*postgresql*" -ErrorAction SilentlyContinue

if ($postgresService) {
    Write-Host "PostgreSQL encontrado: $($postgresService.Name)" -ForegroundColor Green
    
    # Iniciar o serviço se não estiver rodando
    if ($postgresService.Status -ne "Running") {
        Write-Host "Iniciando serviço PostgreSQL..." -ForegroundColor Yellow
        Start-Service $postgresService.Name
        Start-Sleep -Seconds 5
    }
} else {
    Write-Host "PostgreSQL não encontrado. Por favor, instale o PostgreSQL primeiro." -ForegroundColor Red
    Write-Host "Você pode baixar em: https://www.postgresql.org/download/windows/" -ForegroundColor Yellow
    Write-Host "Ou usar: choco install postgresql" -ForegroundColor Yellow
    exit 1
}

# Verificar se o psql está disponível
Write-Host "Verificando se o psql está disponível..." -ForegroundColor Yellow
try {
    $psqlVersion = & psql --version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "psql encontrado: $psqlVersion" -ForegroundColor Green
    } else {
        throw "psql não encontrado"
    }
} catch {
    Write-Host "psql não encontrado. Verifique se o PostgreSQL está instalado corretamente." -ForegroundColor Red
    exit 1
}

# Configurar variáveis
$DB_NAME = "banco_clinica_postgresql"
$DB_USER = "postgres"
$DB_PASSWORD = "fred2025@"
$SQL_SCRIPT = "../banco_clinica_postgresql.sql"

Write-Host "Configurações:" -ForegroundColor Cyan
Write-Host "  Banco: $DB_NAME" -ForegroundColor White
Write-Host "  Usuário: $DB_USER" -ForegroundColor White
Write-Host "  Script: $SQL_SCRIPT" -ForegroundColor White

# Verificar se o banco existe
Write-Host "Verificando se o banco '$DB_NAME' existe..." -ForegroundColor Yellow
$dbExists = & psql -U $DB_USER -d postgres -t -c "SELECT 1 FROM pg_database WHERE datname='$DB_NAME';" 2>$null

if ($dbExists -match "1") {
    Write-Host "Banco '$DB_NAME' já existe." -ForegroundColor Green
} else {
    Write-Host "Criando banco '$DB_NAME'..." -ForegroundColor Yellow
    & psql -U $DB_USER -d postgres -c "CREATE DATABASE $DB_NAME;"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Banco '$DB_NAME' criado com sucesso!" -ForegroundColor Green
    } else {
        Write-Host "Erro ao criar banco '$DB_NAME'." -ForegroundColor Red
        exit 1
    }
}

# Verificar se o script SQL existe
if (Test-Path $SQL_SCRIPT) {
    Write-Host "Executando script SQL..." -ForegroundColor Yellow
    & psql -U $DB_USER -d $DB_NAME -f $SQL_SCRIPT
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Script SQL executado com sucesso!" -ForegroundColor Green
    } else {
        Write-Host "Erro ao executar script SQL." -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "Script SQL não encontrado: $SQL_SCRIPT" -ForegroundColor Yellow
    Write-Host "Verifique se o arquivo existe no diretório correto." -ForegroundColor Yellow
}

# Testar conexão
Write-Host "Testando conexão com o banco..." -ForegroundColor Yellow
$testConnection = & psql -U $DB_USER -d $DB_NAME -c "SELECT version();" 2>$null

if ($LASTEXITCODE -eq 0) {
    Write-Host "Conexão com o banco estabelecida com sucesso!" -ForegroundColor Green
} else {
    Write-Host "Erro ao conectar com o banco." -ForegroundColor Red
    exit 1
}

# Verificar tabelas criadas
Write-Host "Verificando tabelas criadas..." -ForegroundColor Yellow
$tables = & psql -U $DB_USER -d $DB_NAME -t -c "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';" 2>$null

if ($tables) {
    Write-Host "Tabelas encontradas:" -ForegroundColor Green
    $tables | ForEach-Object { 
        if ($_.Trim()) {
            Write-Host "  - $($_.Trim())" -ForegroundColor White
        }
    }
} else {
    Write-Host "Nenhuma tabela encontrada." -ForegroundColor Yellow
}

Write-Host "`n=== Configuração Concluída ===" -ForegroundColor Green
Write-Host "Agora você pode executar a aplicação com:" -ForegroundColor Cyan
Write-Host "  mvn spring-boot:run" -ForegroundColor White
Write-Host "`nURLs da aplicação:" -ForegroundColor Cyan
Write-Host "  - API: http://localhost:8080" -ForegroundColor White
Write-Host "  - Swagger: http://localhost:8080/swagger-ui.html" -ForegroundColor White
Write-Host "  - Health: http://localhost:8080/actuator/health" -ForegroundColor White 