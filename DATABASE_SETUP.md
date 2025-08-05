# Configuração do Banco de Dados PostgreSQL

## Pré-requisitos

1. **PostgreSQL instalado** (versão 12 ou superior)
2. **Java 21** instalado
3. **Maven** instalado

## Configuração do PostgreSQL

### 1. Instalação do PostgreSQL

#### Windows:
```powershell
# Baixar e instalar do site oficial: https://www.postgresql.org/download/windows/
# Ou usar Chocolatey:
choco install postgresql
```

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```

#### macOS:
```bash
# Usando Homebrew
brew install postgresql
brew services start postgresql
```

### 2. Configuração Inicial

#### Criar usuário e banco de dados:
```sql
-- Conectar como superusuário
sudo -u postgres psql

-- Criar usuário (se necessário)
CREATE USER postgres WITH PASSWORD 'postgres';

-- Criar banco de dados
CREATE DATABASE banco_clinica;

-- Conceder privilégios
GRANT ALL PRIVILEGES ON DATABASE banco_clinica TO postgres;

-- Sair do psql
\q
```

### 3. Executar Script SQL

```bash
# Conectar ao banco e executar o script
psql -U postgres -d banco_clinica -f ../banco_clinica_postgresql.sql
```

## Configurações da Aplicação

### Variáveis de Ambiente (Opcional)

Para produção, você pode configurar variáveis de ambiente:

```bash
# Windows PowerShell
$env:DB_PASSWORD="sua_senha_segura"
$env:SPRING_PROFILES_ACTIVE="prod"

# Linux/macOS
export DB_PASSWORD="sua_senha_segura"
export SPRING_PROFILES_ACTIVE="prod"
```

### Configurações por Perfil

#### Desenvolvimento (dev):
- `ddl-auto: validate` - Valida o schema
- `show-sql: true` - Mostra SQL no console
- Logging detalhado

#### Produção (prod):
- `ddl-auto: validate` - Valida o schema
- `show-sql: false` - Não mostra SQL
- Logging mínimo
- Pool de conexões otimizado

## Testando a Conexão

### 1. Verificar se o PostgreSQL está rodando:
```bash
# Windows
net start postgresql-x64-15

# Linux
sudo systemctl status postgresql

# macOS
brew services list | grep postgresql
```

### 2. Testar conexão:
```bash
psql -U postgres -d banco_clinica -h localhost
```

### 3. Executar a aplicação:
```bash
# Na pasta clinica-api
mvn spring-boot:run
```

## Solução de Problemas

### Erro: "password authentication failed"

1. **Verificar se o PostgreSQL está rodando**
2. **Verificar credenciais no `application.yml`**
3. **Verificar se o usuário existe:**
   ```sql
   sudo -u postgres psql
   \du
   ```

### Erro: "database does not exist"

1. **Criar o banco de dados:**
   ```sql
   CREATE DATABASE banco_clinica;
   ```

### Erro: "connection refused"

1. **Verificar se o PostgreSQL está rodando na porta 5432**
2. **Verificar firewall**
3. **Verificar configuração do `pg_hba.conf`**

## Configurações Avançadas

### Pool de Conexões (HikariCP)

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

### Configurações de Performance

```yaml
spring:
  jpa:
    properties:
      hibernate:
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true
```

## Monitoramento

### Health Check
- **URL:** `http://localhost:8080/actuator/health`
- **Status:** UP/DOWN

### Métricas
- **URL:** `http://localhost:8080/actuator/metrics`
- **Conexões:** `http://localhost:8080/actuator/metrics/hikaricp.connections`

## Backup e Restore

### Backup:
```bash
pg_dump -U postgres -d banco_clinica > backup.sql
```

### Restore:
```bash
psql -U postgres -d banco_clinica < backup.sql
``` 