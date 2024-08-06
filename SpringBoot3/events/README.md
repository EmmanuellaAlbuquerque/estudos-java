# API de Eventos



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
- A API deve retornar o nome completo
```


- [ ] 14 - Detalhes de um Evento
- [ ] 15 - Detalhes de um Participante


- [x] 3 - Inscrição do Participante no Evento
```text
«» lidando com as exceptions
- Verificar se o Participante ainda pode se inscrever no evento ✔
```


- [x] 4 - Realização da Reserva no Evento
- [x] 5 - Cancelar Inscrição no Evento
- [x] 6 - Listar Inscritos no Evento
- [x] 7 - Listar Eventos Inscritos do Participante
- [x] 8 - Validar Entrada do Participante no Evento 
- [x] 9 - Conversão de Reservas em Inscrições
- [ ] 10 - Pensar na experiência de Uso da API e possíveis melhorias (ex.: cpf, frontend - ciclo de telas)
- [x] 11 - Adicionar Swagger
- [x] 12 - Adicionar Testes



- [ ] 13 - Capturar as exceptions e personalizar os retornos
```text
«» lidando com as exceptions
- Criar exceção personalizada para 404 Event Not Found
- Criar exceção personalizada para 404 Participant Not Found
- Retornar erro quando event_id não for definido
- Retornar erro quando participant_id não for definido
```




### Ao rodar o projeto, a Documentação da API se encontra em:
http://localhost:8080/swagger-ui/index.html#

