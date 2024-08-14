# BookStore API

## Tecnologias
- Spring Web
- Spring Data JPA
- PostgreSQL v16.4

## Ferramentas
- pgAdmin 4 v8.10

## Scripts DB

```sql
insert into tb_publisher values(gen_random_uuid(), 'Alta Books');
insert into tb_publisher values(gen_random_uuid(), 'Pearson');

select * from tb_publisher;

insert into tb_author values(gen_random_uuid(), 'Eric Evans');
insert into tb_author values(gen_random_uuid(), 'Paul Deitel');
insert into tb_author values(gen_random_uuid(), 'Harvey Deitel');

select * from tb_author;
select * from tb_book;
select * from tb_book_author;
select * from tb_review;
```

##### Exemplos de Requisição para Salvar um Livro
```json
{
	"title": "Domain Driven Design",
	"publisherId": "dc046498-6fa5-4dd5-bad7-3202fccccad3",
	"authorIds": ["89edef68-ecc3-4869-b8c6-ce68d043b1d5"],
	"reviewComment": "Reunindo práticas de design e implementação, este livro incorpora vários exemplos baseados em projetos que ilustram a aplicação do design dirigido por domínios no desenvolvimento de softwares na vida real."
}

```

```json
{
	"title": "Java como programar",
	"publisherId": "dc046498-6fa5-4dd5-bad7-3202fccccad3",
	"authorIds": ["89edef68-ecc3-4869-b8c6-ce68d043b1d5", "ddce6ece-acf2-4f73-8d37-8da1bdab8ab2"],
	"reviewComment": "Java - como programar fornece uma introdução clara, simples, envolvente e divertida à programação Java com ênfase inicial em objetos.."
}
```

##### Retorno sem @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) && (fetch = FetchType.LAZY)
```json
{
	"id": "a48e20df-7442-4326-9def-eeae307222f9",
	"title": "Domain Driven Design",
	"publisher": {
		"id": "dc046498-6fa5-4dd5-bad7-3202fccccad3",
		"name": "Alta Books"
	},
	"authors": [
		{
			"id": "a51f2ea1-1591-4037-b5a0-30a0348b9591",
			"name": "Eric Evans"
		}
	],
	"review": {
		"id": "6dd738fe-5df9-4f62-bf7c-4e14fd333b31",
		"comment": "Reunindo práticas de design e implementação, este livro incorpora vários exemplos baseados em projetos que ilustram a aplicação do design dirigido por domínios no desenvolvimento de softwares na vida real."
	}
}
```

##### Retorno com @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) && (fetch = FetchType.LAZY)
```json
{
	"id": "daa77796-197d-43be-9647-a9ca9f70e67c",
	"title": "Domain Driven Design",
	"review": {
		"id": "7ff841c0-81ad-483d-b435-e4147868d158",
		"comment": "Reunindo práticas de design e implementação, este livro incorpora vários exemplos baseados em projetos que ilustram a aplicação do design dirigido por domínios no desenvolvimento de softwares na vida real."
	}
}
```

### Skeleton das pastas do Projeto
```text
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───app
│   │   │           └───bookstore
│   │   │               ├───controllers
│   │   │               ├───dtos
│   │   │               ├───models
│   │   │               ├───repositories
│   │   │               └───services
│   │   └───resources
│   │       ├───static
│   │       └───templates
│   └───test
└───target
```

