# Agendify — Backend (Spring Boot + MongoDB)

> **Agendify** é um sistema backend escrito em **Java** usando **Spring Boot** e **MongoDB**, que gerencia Eventos, Eventos Pendentes, Usuários, Usuários Pendentes, Cadastro de Usuários e todos os respectivos CRUDs.  
> Possui autenticação via **JWT**, aprovações de cadastros/eventos pendentes e agendamento automático de status de eventos.

---

## Sumário

* [Sobre](#sobre)
* [Principais recursos](#principais-recursos)
* [Tech stack](#tech-stack)
* [Estrutura do projeto](#estrutura-do-projeto)
* [Endpoints principais](#endpoints-principais)
* [Exemplos de payloads](#exemplos-de-payloads)
* [Configuração](#configuração--variaveis-de-ambiente)
* [Rodando localmente](#rodando-localmente)
* [Autenticação](#autenticacao-fluxo)
* [Contribuindo](#contribuindo)
* [Licença](#licenca)

---

# Sobre

Este repositório contém o backend da aplicação **Agendify**, escrito em **Spring Boot** com persistência em **MongoDB**. O sistema oferece:

* CRUD de **Eventos** (gerenciados/executados)
* CRUD de **Eventos Pendentes** (criados por usuários e aprovados/rejeitados por administradores)
* CRUD de **Usuários**
* CRUD de **Usuários Pendentes** (cadastros aguardando aprovação)
* Registro de usuários e login com **JWT**
* Validações customizadas (ex.: validação de local do evento)
* Agendador para atualizar status de eventos (ex.: `EventStatusScheduler`)

---

# Principais recursos

* REST API com DTOs para criar/atualizar/consultar recursos
* Segurança com JWT (filtro `JwtAuthenticationFilter`, `SecurityConfig`)
* Inicialização de usuários padrão via `InitialUserSetup`
* Repositórios Spring Data (MongoDB)
* Mappers e DTOs para separar camada de persistência e API
* Endpoints para aprovação de eventos/usuários pendentes

---

# Tech stack

* **Java 17+**
* **Spring Web / Spring Data MongoDB / Spring Security**
* **JWT** para autenticação
* **MongoDB** (NoSQL)
* **Maven** (ou Gradle — ajuste conforme o projeto)
* **Testes com JUnit** (estrutura de testes presente)

---

# Estrutura do projeto (principais pacotes)

```
src/main/java/com/fatec/agendify/agendify/
├─ config/                # SecurityConfig, InitialUserSetup, etc
├─ controller/            # Controllers: EventController, PendingEventController, UserController, PendingUserController, EventApprovalController, DateTestController
├─ dto/                   # DTOs por recurso (EventDTO, PendingEventDTO, UserDTO, etc)
├─ mapper/                # Mappers entre model <-> DTO
├─ model/                 # Entities / enums (Event, PendingEvent, User, EventStatus, Floor, ...)
├─ repository/            # Repositórios e implementações customizadas
├─ security/              # JwtAuthenticationFilter, utilidades de JWT
├─ service/               # Lógica de negócio (EventService, PendingEventService, UserService, ...)
├─ validation/            # Validators customizados (EventLocationValidator)
└─ AgendifyApplication.java
```

---

## Autenticação / Usuários

* `POST /auth/register` — cria usuário (pode gerar PendingUser dependendo da regra)
* `POST /auth/login` — autentica e retorna token JWT (`LoginResponseDTO`)
* `GET /users` — lista usuários (requer permissão)
* `GET /users/{id}` — obtém usuário por id
* `POST /users` — cria usuário (admin)
* `PUT /users/{id}` — atualiza usuário
* `DELETE /users/{id}` — remove usuário

## Usuários Pendentes

* `GET /pending-users` — lista cadastros pendentes
* `GET /pending-users/{id}` — detalha cadastro pendente
* `POST /pending-users` — cria cadastro pendente (ex.: quando signup precisa aprovação)
* `PUT /pending-users/{id}` — atualiza (ex.: aprovar/rejeitar)
* `DELETE /pending-users/{id}` — excluir pedido pendente

## Eventos

* `GET /events` — lista eventos com filtros (`EventFilterDTO`)
* `GET /events/{id}` — detalhe do evento
* `POST /events` — cria evento
* `PUT /events/{id}` — atualiza evento
* `DELETE /events/{id}` — remove evento

## Eventos Pendentes

* `GET /pending-events` — lista eventos pendentes
* `GET /pending-events/{id}` — detalhes
* `POST /pending-events` — cria evento pendente (usuários submetem evento)
* `PUT /pending-events/{id}` — atualiza (ex.: admin aprova/recusa)
* `DELETE /pending-events/{id}` — deleta evento pendente

## Aprovação de Eventos

* `POST /event-approval/{pendingEventId}/approve` — aprovar evento pendente (move para `events`)
* `POST /event-approval/{pendingEventId}/reject` — rejeitar

---

# Exemplos de payloads (DTOs)

### Ex.: `UserCreateDTO` (registro simples)

```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha-segura",
  "role": "USER"         
}
```

### Ex.: Login (`UserLoginDTO`)

```json
{
  "email": "joao@example.com",
  "password": "senha-segura"
}
```

### Ex.: `EventCreateDTO`

```json
{
  "title": "Reunião de Planejamento",
  "description": "Planejar próximos sprints",
  "day": "2025-08-20",            
  "startTime": "09:00",
  "endTime": "10:30",
  "location": {
    "floor": "PRIMEIRO",
    "room": "Sala 101"
  },
  "priority": "ALTA",
  "mode": "PRESENCIAL"          
}
```

### Ex.: `PendingEventCreateDTO`

```json
{
  "title": "Workshop Angular",
  "description": "Workshop para iniciantes",
  "day": "2025-08-30",
  "startTime": "14:00",
  "endTime": "17:00",
  "requestedByUserId": "642a1f..." 
}
```

---

# Configuração / Variáveis de ambiente

Edite `src/main/resources/application.properties` ou use variáveis de ambiente:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/agendify

# Server
server.port=8080

# JWT (exemplo)
app.jwtSecret=CHANGE_THIS_SECRET
app.jwtExpirationMs=3600000

# Timezone (se necessário)
spring.jackson.time-zone=America/Sao_Paulo
```

Substitua `app.jwtSecret` por uma chave forte e segura em produção.

---

# Rodando localmente

1. **Banco de dados**
   Inicie uma instância MongoDB local (ex.: `mongod`) ou use um serviço em nuvem (Mongo Atlas) e ajuste `spring.data.mongodb.uri`.

2. **Build & Run**
   Assumindo Maven:

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

   Ou com wrapper (se presente):

   ```bash
   ./mvnw spring-boot:run
   ```

3. **Endpoints**
   Acesse `http://localhost:8080` (ou porta configurada). Use o endpoint `/auth/register` e `/auth/login` para criar e autenticar usuários.

4. **Usuário inicial**
   O projeto contém `InitialUserSetup` que pode criar um usuário admin inicial automaticamente (verifique as propriedades ou código para os detalhes).

---

# Autenticação (fluxo)

1. `POST /auth/login` com `UserLoginDTO` -> retorna `LoginResponseDTO` com `accessToken`.
2. Para endpoints protegidos, inclua header:

   ```
   Authorization: Bearer <token>
   ```
3. Rotas de criação/alteração sensíveis podem exigir papel/role `ADMIN` ou `MASTER` (verifique as anotações em `SecurityConfig`).

---

# Exemplos de `curl`

**Login**

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@example.com","password":"senha-segura"}'
```

**Criar evento (autenticado)**

```bash
curl -X POST http://localhost:8080/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "title":"Reunião",
    "description":"...",
    "day":"2025-08-20",
    "startTime":"10:00",
    "endTime":"11:00",
    "location":{"floor":"PRIMEIRO","room":"Sala 1"},
    "priority":"MEDIA",
    "mode":"PRESENCIAL"
  }'
```

---

# Testes / Coleções recomendadas

* Importe uma coleção Postman com os endpoints (crie uma localmente).
* Teste seguintes cenários:

  * Registro → login → criar pending-event → aprovar pending-event → verificar evento criado
  * Registro de `pending-user` → aprovação → login como user aprovado
  * Testes unitários nos serviços e validações (EventLocationValidator, regras de conflito de horário)

---

# Observações de implementação (dicas rápidas)

* Há mappers (`EventMapper`, `PendingEventMapper`, `UserMapper`) para separar model e DTOs; mantenha validações no DTO/Controller.
* `EventStatusScheduler` atualiza status automaticamente — cheque o timezone `America/Sao_Paulo` se necessário.
* `EventCustomRepositoryImpl` contém buscas customizadas (filtros por data, local, prioridade); reusar para endpoints de filtragem.
* `JwtAuthenticationFilter` é responsável por obter token e popular contexto de segurança.

---
