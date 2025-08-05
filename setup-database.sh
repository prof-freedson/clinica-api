#!/bin/bash

# Script de Configuração do Banco de Dados PostgreSQL
# Execute este script como root ou com sudo

echo "=== Configuração do Banco de Dados PostgreSQL ==="

# Verificar se o PostgreSQL está instalado
echo "Verificando se o PostgreSQL está instalado..."
if command -v psql &> /dev/null; then
    echo "PostgreSQL encontrado: $(psql --version)"
else
    echo "PostgreSQL não encontrado. Por favor, instale o PostgreSQL primeiro."
    echo "Ubuntu/Debian: sudo apt install postgresql postgresql-contrib"
    echo "CentOS/RHEL: sudo yum install postgresql postgresql-server"
    echo "macOS: brew install postgresql"
    exit 1
fi

# Verificar se o serviço está rodando
echo "Verificando se o serviço PostgreSQL está rodando..."
if systemctl is-active --quiet postgresql; then
    echo "PostgreSQL está rodando."
elif brew services list | grep -q postgresql; then
    echo "PostgreSQL está rodando (macOS)."
else
    echo "Iniciando PostgreSQL..."
    if command -v systemctl &> /dev/null; then
        sudo systemctl start postgresql
    elif command -v brew &> /dev/null; then
        brew services start postgresql
    fi
    sleep 3
fi

# Configurar variáveis
DB_NAME="banco_clinica"
DB_USER="postgres"
SQL_SCRIPT="../banco_clinica_postgresql.sql"

echo "Configurações:"
echo "  Banco: $DB_NAME"
echo "  Usuário: $DB_USER"
echo "  Script: $SQL_SCRIPT"

# Verificar se o banco existe
echo "Verificando se o banco '$DB_NAME' existe..."
if sudo -u postgres psql -lqt | cut -d \| -f 1 | grep -qw $DB_NAME; then
    echo "Banco '$DB_NAME' já existe."
else
    echo "Criando banco '$DB_NAME'..."
    sudo -u postgres createdb $DB_NAME
    
    if [ $? -eq 0 ]; then
        echo "Banco '$DB_NAME' criado com sucesso!"
    else
        echo "Erro ao criar banco '$DB_NAME'."
        exit 1
    fi
fi

# Verificar se o script SQL existe
if [ -f "$SQL_SCRIPT" ]; then
    echo "Executando script SQL..."
    sudo -u postgres psql -d $DB_NAME -f $SQL_SCRIPT
    
    if [ $? -eq 0 ]; then
        echo "Script SQL executado com sucesso!"
    else
        echo "Erro ao executar script SQL."
        exit 1
    fi
else
    echo "Script SQL não encontrado: $SQL_SCRIPT"
    echo "Verifique se o arquivo existe no diretório correto."
fi

# Testar conexão
echo "Testando conexão com o banco..."
if sudo -u postgres psql -d $DB_NAME -c "SELECT version();" &> /dev/null; then
    echo "Conexão com o banco estabelecida com sucesso!"
else
    echo "Erro ao conectar com o banco."
    exit 1
fi

# Verificar tabelas criadas
echo "Verificando tabelas criadas..."
TABLES=$(sudo -u postgres psql -d $DB_NAME -t -c "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';" 2>/dev/null)

if [ ! -z "$TABLES" ]; then
    echo "Tabelas encontradas:"
    echo "$TABLES" | while read -r table; do
        if [ ! -z "$table" ]; then
            echo "  - $table"
        fi
    done
else
    echo "Nenhuma tabela encontrada."
fi

echo ""
echo "=== Configuração Concluída ==="
echo "Agora você pode executar a aplicação com:"
echo "  mvn spring-boot:run"
echo ""
echo "URLs da aplicação:"
echo "  - API: http://localhost:8080"
echo "  - Swagger: http://localhost:8080/swagger-ui.html"
echo "  - Health: http://localhost:8080/actuator/health" 