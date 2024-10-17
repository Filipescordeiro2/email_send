# Email Service

Este projeto é um microsserviço para envio de e-mails utilizando Java Spring Boot. O serviço permite o envio de e-mails com um corpo HTML, sendo útil para notificações e comunicação com usuários. Além disso, todos os envios de e-mail são registrados no banco de dados MySQL.

## Estrutura do Projeto

A estrutura do projeto é a seguinte:

```
src
├── controller
│   └── EmailController.java
├── dto
│   └── EmailRequestDTO.java
├── entity
│   └── EmailEntity.java
├── repository
│   └── EmailRepository.java
├── service
│   ├── EmailService.java
│   └── imp
│       └── EmailServiceImp.java
└── templates
    └── welcome-email.html
└── EmailApplication.java
```

## Pré-requisitos

Antes de executar o projeto, certifique-se de que você tem:

- JDK 11 ou superior
- Maven
- Uma conta de e-mail (preferencialmente Gmail) com configurações de SMTP habilitadas.

# Configurações

### JavaMail (para envio de e-mails)
   ```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email
spring.mail.password=sua-senha-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
   ```
- Altere `seu-email@gmail.com` e `sua-senha` para suas credenciais de e-mail.

### Thymeleaf
   ```properties
spring.thymeleaf.cache=false
   ```

###  MySQL
   ```properties
spring.datasource.url=jdbc:mysql://localhost:3306/envio_email?useSSL=false&serverTimezone=UTC
spring.datasource.username=user_mysql
spring.datasource.password=sua_senha_mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
### JPA
   ```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```
### Como Executar

1. Navegue até o diretório do projeto e execute o seguinte comando:

   ```bash
   mvn spring-boot:run
   ```

2. O serviço estará disponível em `http://localhost:8080/email/send`.

## Uso da API

Para enviar um e-mail, faça uma solicitação POST para `http://localhost:8080/email/send` com o seguinte corpo JSON:

```json
{
    "to": "destinatario@example.com",
    "subject": "Seu Assunto",
    "body": "Este é o corpo do e-mail."
}
```

## Exemplo de Resposta

```json
{
"message":"E-mail enviado com sucesso!"
}
```