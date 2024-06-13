# desafio-springboot-docker

### 📋 Sobre o projeto
Aplicação Web para gerenciamento de tarefas(To-Do list), onde é possível adicionar, atualizar, visualizar e deletar tarefas.

### Tecnologias
- Java 17
- Spring Boot
- PostgreSQL
- Hibernate
- Flyway
- Google jib
- Docker Compose

## 🚀 Executando projeto
#### Para build da imagem docker
```
./mvnw -ntp verify -DskipTests jib:dockerBuild
```
#### Para execução do BackEnd em conjunto ao Banco de dados (PostgreSQL)
```
docker-compose -f src/main/docker/app.yml up -d
```

<hr>

## Endpoints
O projeto disponibiliza endpoints para Usuários e Contas, onde utilizam o padrão Rest de comunicação, consumindo e retornando dados no formato JSON.
### Contas
#### Listar Todos
GET /api/v1/conta/listar
```bash
http://localhost:8080/api/v1/conta/listar?dataVencimento=13/06/2024&descricao=ta&size=10&sort=dataVencimento,asc&page=0
```
Nele recebemos os possiveis parametros de URL:
- dataVencimento: Data do vencimento da conta no formato dd/MM/yyyy
- descricao: Descrição da conta
- page: pagina desejada (default: 0)
- size: quantidade de Itens por página
- sort: Ordenaçao da paginação no seguinte formato "{campo},{asc ou desc}" (exemplo: dataVencimento,asc) 


#### Listar por Id
GET api/v1/conta/:id
```bash
http://localhost:8080/api/v1/conta/1
```
#### Criar
POST /api/v1/conta
```bash
http://localhost:8080/api/v1/conta
```
Request Body
```bash
{
  "dataVencimento": "13/06/2024",
  "valor": 100.00,
  "descricao": "Conta de água"
}
```
#### Atualizar
PATCH /api/v1/conta/:id
```bash
http://localhost:8080/api/v1/conta/13
```
Request Body
```bash
{
	"id": 13,
	"dataVencimento": "13/06/2024",
	"dataPagamento": "2025-06-13",
	"valor": 100.00,
	"descricao": "Conta de água",
	"situacao": "PAGO"
}
```

## Usuário
#### Criar
POST /usuario
```bash
http://localhost:8080/usuario/
```
Request Body
```bash
{
	"nome": "João Alvarez",
	"email": "contato@joaoalvarez.dev",
	"senha": "123"
}
```
#### Listar por Id
GET /usuario/:id
```bash
http://localhost:8080/usuario/1
```
