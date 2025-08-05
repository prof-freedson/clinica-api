# API de Clínica - CRUD de Pacientes

Esta é uma API Spring Boot simplificada para gerenciamento de pacientes de uma clínica médica.

## Funcionalidades

- **CRUD completo de pacientes** (Create, Read, Update, Delete)
- **Busca por nome** (parcial)
- **Busca por tipo sanguíneo**
- **Validação de dados** com anotações Bean Validation
- **Documentação automática** com Swagger/OpenAPI

## Tecnologias Utilizadas

- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Swagger/OpenAPI**
- **Bean Validation**

## Pré-requisitos

- Java 21
- PostgreSQL
- Maven

## Configuração do Banco de Dados

1. Instale e configure o PostgreSQL
2. Crie um banco de dados chamado `banco_clinica_postgresql`
3. Configure as credenciais no arquivo `application.yml` se necessário

## Executando a Aplicação

### Via Maven
```bash
mvn spring-boot:run
```

### Via JAR
```bash
mvn clean package
java -jar target/clinica-api-0.0.1-SNAPSHOT.jar
```

## Endpoints da API

### Base URL
```
http://localhost:8080/api/v1/pacientes
```

### Endpoints Disponíveis

#### 1. Listar Todos os Pacientes
```
GET /api/v1/pacientes
```

#### 2. Buscar Paciente por ID
```
GET /api/v1/pacientes/{id}
```

#### 3. Criar Novo Paciente
```
POST /api/v1/pacientes
```

**Exemplo de JSON:**
```json
{
  "nomePac": "João Silva",
  "dataNascPac": "1990-05-15",
  "pesoPac": 75.5,
  "altPac": 1.75,
  "tipoSang": "A+",
  "enderecoPac": "Rua das Flores, 123",
  "telefonePac": "11987654321",
  "emailPac": "joao.silva@email.com"
}
```

#### 4. Atualizar Paciente
```
PUT /api/v1/pacientes/{id}
```

#### 5. Excluir Paciente
```
DELETE /api/v1/pacientes/{id}
```

#### 6. Buscar por Nome
```
GET /api/v1/pacientes/buscar?nome=João
```

#### 7. Buscar por Tipo Sanguíneo
```
GET /api/v1/pacientes/tipo-sang/A+
```

## Documentação da API

Acesse a documentação interativa da API:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## Validações

A API inclui validações automáticas:

- **Nome**: Obrigatório, máximo 100 caracteres
- **Data de Nascimento**: Obrigatória, não pode ser futura
- **Peso**: Entre 0 e 999.99 kg
- **Altura**: Entre 0 e 9.99 metros
- **Tipo Sanguíneo**: Formato válido (A+, B-, AB+, O-, etc.)
- **Telefone**: 10 ou 11 dígitos numéricos
- **Email**: Formato de email válido
- **Endereço**: Máximo 200 caracteres

## Estrutura do Projeto

```
src/main/java/com/clinica/clinica_api/
├── controller/
│   └── PacienteController.java
├── service/
│   └── PacienteService.java
├── repository/
│   └── PacienteRepository.java
├── model/
│   ├── entity/
│   │   └── Paciente.java
│   └── dto/
│       ├── PacienteDTO.java
│       ├── PacienteCreateDTO.java
│       └── PacienteUpdateDTO.java
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── GlobalExceptionHandler.java
│   └── ErrorResponse.java
└── ClinicaApiApplication.java
```

## Próximos Passos

Esta versão simplificada foca apenas no CRUD básico de pacientes. Para futuras versões, considere adicionar:

- Relacionamentos com outras entidades (médicos, consultas, etc.)
- Autenticação e autorização
- Logs mais detalhados
- Testes automatizados
- Cache
- Rate limiting 