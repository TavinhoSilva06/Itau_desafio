# ITAUtask - API de Transações e Estatísticas

## Sobre o Projeto

Este projeto foi desenvolvido como solução para o desafio de programação do Itaú.

A aplicação disponibiliza uma API REST capaz de:

* Registrar transações financeiras;
* Remover todas as transações registradas;
* Calcular estatísticas das transações realizadas nos últimos N segundos;
* Expor documentação automática da API;
* Disponibilizar endpoint de monitoramento (health check);
* Executar testes automatizados;
* Executar em container Docker.

---

## Tecnologias Utilizadas

* Java 21
* Spring Boot 3.5.0
* Maven
* Lombok
* Spring Validation
* Spring Actuator
* Springdoc OpenAPI (Swagger)
* JUnit 5
* Mockito
* Docker
* Render

---

## Estrutura do Projeto

```text
src
 ├── config
 ├── controller
 ├── dto
 ├── exception
 ├── model
 ├── repository
 ├── service
 └── resources
```

---

## Como Executar Localmente

### Clonar o Repositório

```bash
git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
```

### Entrar na Pasta

```bash
cd ITAUtask
```

### Compilar o Projeto

```bash
mvn clean install
```

### Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## Configuração

O intervalo utilizado para cálculo das estatísticas pode ser configurado no arquivo:

```properties
src/main/resources/application.properties
```

Exemplo:

```properties
# Intervalo em segundos utilizado no cálculo das estatísticas
estatistica.intervalo-segundos=60
```

Por padrão, a aplicação considera apenas as transações realizadas nos últimos 60 segundos.

---

## Endpoints

### Registrar Transação

```http
POST /transacao
```

Exemplo de requisição:

```json
{
  "valor": 100.50,
  "dataHora": "2026-06-10T15:30:00-03:00"
}
```

Resposta:

```http
201 Created
```

---

### Limpar Transações

```http
DELETE /transacao
```

Resposta:

```http
200 OK
```

---

### Consultar Estatísticas

```http
GET /estatistica
```

Exemplo de resposta:

```json
{
  "count": 2,
  "sum": 300.50,
  "avg": 150.25,
  "min": 100.00,
  "max": 200.50
}
```

---

## Documentação da API (Swagger)

Após iniciar a aplicação, a documentação pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

No ambiente publicado:

```text
https://itau-desafio.onrender.com/swagger-ui/index.html
```

---

## Health Check

A aplicação disponibiliza um endpoint para monitoramento de saúde:

```http
GET /actuator/health
```

Exemplo de resposta:

```json
{
  "status": "UP"
}
```

---

## Logs

A aplicação registra informações importantes durante sua execução, como:

* Recebimento de transações;
* Horário recebido na requisição;
* Horário atual do servidor;
* Limpeza de transações;
* Cálculo das estatísticas;
* Tempo gasto para cálculo;
* Tratamento de exceções.

Esses logs auxiliam na identificação e resolução de problemas.

---

## Testes Automatizados

Para executar todos os testes:

```bash
mvn test
```

Os testes cobrem:

* Services;
* Controllers;
* Cenários de sucesso;
* Cenários de erro;
* Validações de negócio.

---

## Docker

### Gerar Imagem

```bash
docker build -t itautask .
```

### Executar Container

```bash
docker run -p 8080:8080 itautask
```

---

## Deploy

A aplicação encontra-se publicada no Render:

```text
https://itau-desafio.onrender.com
```

---

## Autor

Projeto desenvolvido por Tavinho Augusto como solução para o desafio técnico Itaú.
