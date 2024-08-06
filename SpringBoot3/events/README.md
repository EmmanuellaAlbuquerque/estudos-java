# API de Eventos

<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white"/>	
</p>

## Lista de Conteúdos

:pushpin: [Documentação da API](#api-documentation)

:pushpin: [Requisitos do Desafio](#requisitos-do-desafio)

# Documentação da API

## Endpoints

---
### Cria um Evento

```
POST /api/v1/events
```

**Body:**
```json
{
    "name": "Evento de Programação no Nordeste",
    "vacancies": 100,
    "starts_at": "2024-08-11T11:01:34.00",
    "ends_at": "2024-08-20T12:56:00"
}
```

**Response:**

![HTTP Status 201](https://img.shields.io/badge/HTTP_Status-201-success)
```text 
Location	http://localhost:8080/api/v1/events/UUID-do-evento
```

**Outros Exemplos de Respostas:**

![HTTP Status 400](https://img.shields.io/badge/HTTP_Status-400-orange)
*Retorno para envio de Objeto vazio {}* 
```json
{
  "vacancies": "A quantidade de vagas disponíveis para o evento é obrigatória",
  "starts_at": "A data de início do evento é obrigatória",
  "name": "O nome do evento é obrigatório",
  "ends_at": "A data de fim do evento é obrigatória"
}
```

![HTTP Status 400](https://img.shields.io/badge/HTTP_Status-400-orange)
*Retorno para quantidade de vagas inválida*
```json
{
  "vacancies": "O número de vagas do evento deve ser maior que zero"
}
```

![HTTP Status 400](https://img.shields.io/badge/HTTP_Status-400-orange)
*Retorno para datas inválidas*
```json
{
  "starts_at": "A data de início do evento deve ser uma data no presente ou no futuro",
  "ends_at": "A data de fim do evento deve ser uma data no futuro"
}
```

---


### Cria um Participante

```
POST /api/v1/participants
```

**Body:**
```json
{
    "name": "Emmanuella",
    "surname": "Albuquerque",
    "cpf": "123.456.789-09"
}
```

**Response:**

![HTTP Status 201](https://img.shields.io/badge/HTTP_Status-201-success)
```text
Location	http://localhost:8080/api/v1/participants/UUID-do-participante
```

**Outros Exemplos de Respostas:**

![HTTP Status 400](https://img.shields.io/badge/HTTP_Status-400-orange)
*Retorno para envio de Objeto vazio {}*
```json
{
  "surname": "O sobrenome do Participante é obrigatório",
  "name": "O nome do Participante é obrigatório",
  "cpf": "O CPF do Participante é obrigatório"
}
```

![HTTP Status 400](https://img.shields.io/badge/HTTP_Status-400-orange)
*Retorno para CPF inválido*
```json
{
  "cpf": "O CPF informado é inválido"
}
```

---

### Obtém detalhes de um Evento

```
GET /api/v1/events/:event_id
```

![HTTP Status 404](https://img.shields.io/badge/HTTP_Status-404-red)
*Retorno quando o Evento não é encontrado*
```json
{
  "error": "Evento não encontrado!"
}
```


---

### Obtém detalhes de um Participante

```
GET /api/v1/participants/:participant_id
```

![HTTP Status 404](https://img.shields.io/badge/HTTP_Status-404-red)
*Retorno quando o Participante não é encontrado*
```json
{
  "error": "Participante não encontrado!"
}
```

---

### Registra um Participante em um Evento

```
POST /api/v1/events/:event_id/participants/:participant_id/register
```


---



### Reserva um Participante em um Evento

```
POST /api/v1/events/:event_id/participants/:participants_id/reserve
```

---


### Lista Participantes Registrados no Evento

```
GET /api/v1/events/:event_id/participants
```

**Response:**

![HTTP Status 201](https://img.shields.io/badge/HTTP_Status-201-success)
```json
[
  {
    "id": 1,
    "participant": {
      "id": "f62fa4c8-bf3b-4cb0-83f9-2f15f2d6cea2",
      "name": "Manu",
      "surname": "Albuquerque",
      "cpf": "333.456.789-09"
    },
    "registration_date": "2024-08-06T18:39:54.207405",
    "registration_status": "REGISTERED"
  },
  {
    "id": 2,
    "participant": {
      "id": "3b4c142a-89ba-4c17-9a1b-0670bfba6962",
      "name": "Harry",
      "surname": "Potter",
      "cpf": "666.456.789-09"
    },
    "registration_date": "2024-08-06T18:40:29.932833",
    "registration_status": "REGISTERED"
  }
]
```



---


### Valida a entrada do Participante no evento

```
GET /api/v1/events/:event_id/participants/:participant_id/valid
```

**Response:**

![HTTP Status](https://img.shields.io/badge/HTTP_Status-200-brightgreen)
```json
{
  "content": "Participante validado com sucesso!"
}
```

---


### Converte uma Reserva de um Participante em Inscrição

```
PUT /api/v1/events/:event_id/participants/:participant_id/convert
```

---


### Lista Todos os Eventos que o Participante se Inscreveu

```
GET /api/v1/participants/:participant_id/events
```


**Response:**

![HTTP Status 200](https://img.shields.io/badge/HTTP_Status-200-brightgreen)
```json
[
  {
    "id": "dba8b0b8-3753-46f3-9324-885405aa2c7e",
    "name": "Evento de Programação no Nordeste",
    "vacancies": 3,
    "starts_at": "2024-08-06T22:00:00",
    "ends_at": "2024-08-06T23:00:00"
  }
]
```

---



### Desinscreve um Participante de um Evento

```
DELETE /api/v1/events/:event_id/participants/:participant_id/cancel
```


---

## Requisitos do Desafio

- [x] 1 - Cadastro de Eventos
```text
⚪ O Evento deve ter um nome, a quantidade de vagas, quando o evento inícia e quando termina.

⦿ O nome de ser obrigatório
⦿ A quantidade de vagas deve ser obrigatória
⦿ A data de início deve ser obrigatória
⦿ A data de fim deve ser obrigatória

«» regras de negócio e requisitos do sistema
- O nome deve ter no Máx 150 caracteres ✔
- A quantidade de vagas deve ser positiva ✔
- A data de início deve ser uma data atual ou futura ✔
- A data de fim deve ser uma data futura ✔
- Retornar erro se as datas forem inválidas ✔
- A data de fim enviada deve ser posterior a data de início ✔
```



- [x] 2 - Cadastro Participantes
```text
⚪ Um participante deve ter um nome, um sobrenome e um CPF.

⦿ O nome deve ser obrigatório
⦿ O sobrenome deve ser obrigatório
⦿ O CPF deve ser obrigatório

«» regras de negócio e requisitos do sistema
- O nome deve ter no Máx 50 caracteres ✔
- O sobrenome deve ter no Máx 50 caracteres ✔
- O CPF deve estar no formato 123.456.789-09 (14 dígitos) ou 12345678909 (11 dígitos) ✔
- CPF deve ser único
```



- [x] 3 - Detalhes de um Evento
- [x] 4 - Detalhes de um Participante
- [x] 5 - Inscrição do Participante no Evento
```text
«» lidando com as exceptions
- Verificar se o Participante ainda pode se inscrever no evento ✔
```



- [x] 6 - Realização da Reserva no Evento
```text
«» lidando com as exceptions
- Verificar se o Participante ainda pode reservar uma vaga no evento ✔
```



- [x] 7 - Cancelar Inscrição no Evento
- [x] 8 - Listar Inscritos no Evento
- [x] 9 - Listar Eventos Inscritos do Participante
- [x] 10 - Validar Entrada do Participante no Evento 
- [x] 11 - Conversão de Reservas em Inscrições
- [x] 12 - Adicionar Swagger
- [x] 13 - Adicionar Testes
- [ ] 14 - Capturar as exceptions e personalizar os retornos
```text
«» lidando com as exceptions
- Criar exceção personalizada para 404 Event Not Found
- Criar exceção personalizada para 404 Participant Not Found
- Retornar erro quando event_id não for definido
- Retornar erro quando participant_id não for definido
```


### Ao rodar o projeto, a Documentação da API se encontra em:
http://localhost:8080/swagger-ui/index.html#

