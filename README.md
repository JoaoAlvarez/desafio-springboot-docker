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

#### Listar Todos
GET /api/v1/conta/total-valor-pago
```bash
http://localhost:8080/api/v1/conta/total-valor-pago?dataInicial=20/02/2000&dataFinal=20/02/2026
```
Nele recebemos os possiveis parametros de URL:
- dataInicial: Data inicial do periodo formato dd/MM/yyyy
- dataFinal: Data final do periodo formato dd/MM/yyyy

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

#### Importar Contas por CSV
PATCH /api/v1/conta/import
```bash
http://localhost:8080/api/v1/conta/import
```
Request Muiltpart com o parametro "file"
```bash
descricao,dataVencimento,dataPagamento,valor,situacao
Conta de luz,20/02/2020,,100,
IPVA,12/03/2021,12/04/2021,22.50,PAGO
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
