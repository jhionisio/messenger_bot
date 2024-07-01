# Messenger Bot

## Descrição do Projeto

Este projeto é um bot para o Messenger da Meta que processa e responde mensagens. Ele foi desenvolvido em Java usando Spring Boot e MongoDB para armazenamento de logs. O bot é capaz de receber mensagens de texto e postback, processá-las e responder com payload para mensagens de texto ou botões clicáveis.

## Objetivo

O objetivo deste projeto é criar um bot escalável e eficiente que possa interagir com os usuários do Messenger, oferecendo respostas automáticas baseadas em comandos predefinidos. Ele é projetado para ser facilmente extensível para novos tipos de mensagens e funcionalidades no futuro.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Data MongoDB
- **MongoDB**
- **MongoDB Atlas**
- **ModelMapper**
- **SLF4J** para logging
- **Heroku**

## Padrão de commit
(Escopo geral de determinado desenvolvimento feito)
DONE:

(Escopo geral de determinado desenvolvimento em progresso)
WIP:

(Mudanças do código para atender o escopo)
FIX:

## Padrão de branch
(Branch principal)
main/

(Branches de adição de novas features)
feature/

(Branch de correção)
fix/

## Estrutura do Projeto

```
messenger_bot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── messenger_bot/
│   │   │               ├── MessengerBotApplication.java
│   │   │               ├── controller/
│   │   │               │   ├── dto/
│   │   │               │   └── converter/
│   │   │               ├── usecase/
│   │   │               │   ├── handler/
│   │   │               │   └── processor/
│   │   │               │   └── service/
│   │   │               ├── domain/
│   │   │               │   ├── MessageDomain.java
│   │   │               │   ├── enums/
│   │   │               │   └── messaging/
│   │   │               │       ├── Messaging.java
│   │   │               │       ├── Sender.java
│   │   │               │       ├── Recipient.java
│   │   │               │       ├── Postback.java
│   │   │               │       └── MessageContent.java
│   │   │               ├── config/
│   │   │               └── repository/
│   └── resources/
│       └── application.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Potencial de Escalabilidade

### Arquitetura

A arquitetura do projeto segue o padrão de camadas, que é fundamental para a escalabilidade futura. Visando às benesses da arquitetura hexagonal junto a conceitos de DDD(Domain Driven Design) para gerar facilmente APIs que possam ser integradas à arquitetura de BFF e gateways de micro-serviços. As camadas são organizadas da seguinte forma:

1. **Controller**: Responsável por receber as requisições e retornar as respostas.
2. **Use Case**: Contém a lógica de negócio e manipulação de dados.
3. **Repository**: Interface para interações com o banco de dados.
4. **Domain**: Contém as entidades do domínio.
5. **Processor**: Processa diferentes tipos de mensagens.
6. **Handler**: Lida com diferentes casos de uso dentro do processo de mensagens.
7. **Enums**: Lista categorias diferentes.
8. **Service**: Instância serviços específicos usados pelos Use Cases.

### Importância para a Escalabilidade

- **Separação de Responsabilidades**: Cada camada tem uma responsabilidade clara, facilitando a manutenção e a evolução do código.
- **Facilidade de Testes**: Com a lógica de negócio separada em camadas, é mais fácil testar cada parte do sistema isoladamente.
- **Extensibilidade**: Novos tipos de mensagens e funcionalidades podem ser adicionados sem modificar a estrutura existente.
- **Desempenho**: Uso de MongoDB para armazenamento rápido e eficiente de logs.

## Como testar o projeto localmente ou deployado

### Pré-requisitos para testar localmente

- **JDK 17**
- **IntelliJ IDEA ou outro compilador**
- **MongoDB**
- **Ferrmanta de teste. Ex\ Insomnia ou postman**

 ### Pré-requisitos para testar deployado

- **Ferrmanta de teste. Ex\ Insomnia ou postman**

### Passos para Rodar

1. **Clone o Repositório**

   ```bash
   git clone https://github.com/seu-usuario/messenger-bot.git
   cd messenger-bot
   ```

2. **Abrir o Projeto no IntelliJ**

1. Abra o IntelliJ IDEA.
2. Clique em `File > Open` e selecione o diretório do projeto.

----

3. **Configurar Variáveis de Ambiente**

Configure o token de acesso da página do Messenger em uma variável de ambiente ou diretamente no arquivo `application.properties`:

```properties
server.port=4444
facebook.page.access.token=seu-token
spring.data.mongodb.uri=mongodb+srv://mrjoaomarcelo:Joao1404@cluster0.edqsfzc.mongodb.net/smarters-test?retryWrites=true&w=majority
```

4. **Build e Run**

----

1. No IntelliJ, abra o painel `Maven` à direita.
2. Navegue até `Lifecycle > clean` e clique duas vezes para limpar o projeto.
3. Em seguida, navegue até `Lifecycle > install` e clique duas vezes para compilar o projeto.
4. Finalmente, clique com o botão direito em `MessengerBotApplication.java` e selecione `Run`.
----
5. **Testar o Bot (Rodando localmente)**

----

1. O bot estará rodando na porta `4444`.
2. Você pode usar ferramentas como Postman ou o próprio Messenger para enviar mensagens e verificar as respostas.
----
6. **Testar o Bot via serviços (Testar sistema deployado)**

----

1. Baixar o MongoDB Compass.
2. Adicionar à conexão ao meu Atlas pessoal. (Conn: mongodb+srv://mrjoaomarcelo:Joao1404@cluster0.edqsfzc.mongodb.net/)
3. Abrir o Insomnia e adicionar o endereço (https://smarters-test-e1ee18c2d4c7.herokuapp.com).
4. O Meta espera receber sempre a conexão com o webhook numa rota de nome `webhook`. Para fins de exemplo foi definido um post sob a rota `webhook` porém a conexão com o meta não terminou de ser configurada pois não entra no escopo deste entregável, porém para manter a nomenclatura necessária futuramente foi definida a terminologia `webhook` para a rota porém dentro dela temos um post à avessar chamado `local-test`. Então para testar o bom funcionamento do código deve-se entrar a rota `https://smarters-test-e1ee18c2d4c7.herokuapp.com/webhook/local-test` dentro do insomnia ou postman para teste.
----
- **À baixo seguem em anexo dois exemplos de de envios de post para testes do software**

  POST -> https://smarters-test-e1ee18c2d4c7.herokuapp.com/webhook/local-test
  (Envia uma mensagem de Postback e recebe uma mensagem de texto de resposta.)
  ```json
  {
  "id": "PAGE_ID",
  "time": 1458692752478,
  "messaging": [
      {
        "sender": {
          "id": "USER_ID"
        },
        "recipient": {
          "id": "PAGE_ID"
        },
        "timestamp": 1762902671,
        "postback": {
          "mid": "mid.1457764197618:41d102a3e1ae206a38",
          "payload": "PRA_QUE_VOCE_SERVE_PAYLOAD"
        }
      }
    ]
  }
```

  POST -> https://smarters-test-e1ee18c2d4c7.herokuapp.com/webhook/local-test
  (Envia uma mensagem de texto e recebe uma mensagem de payload para botões clicáveis de resposta.)
  ```json
  {
  "id": "PAGE_ID",
  "time": 1458692752478,
  "messaging": [
    {
      "sender": {
        "id": "USER_ID"
      },
      "recipient": {
        "id": "PAGE_ID"
      },
      "timestamp": 1762902671,
      "message": {
        "mid": "mid.1457764197618:41d102a3e1ae206a38",
        "text": "botões"
      }
    }
  ]
}
```
----
## Conclusão

Este projeto demonstra como criar um software escalável e eficiente usando Java e Spring Boot. A arquitetura bem definida e a separação de responsabilidades garantem que o sistema possa ser facilmente mantido e expandido, oferecendo uma base sólida para o desenvolvimento de funcionalidades futuras.

