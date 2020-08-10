# desafio-tecnico-cwi

## Cooperative Decision Service

### Stack
- Java 11
- Spring Boot 2.2.9 RELEASE
- Spring AMQP (RabbitMQ)
- JPA
- Lombok
- Junit
- Mockito
- MySQL
- Flyway
- WebClient
- Faker
- Maven
- Jacoco

## Orientações sobre RabbitMQ
- Arquivo com imagem do RabbitMQ /artifacts/rabbitmq-docker
- Criar virtual host: meeting
Obs: As filas estão sendo criadas dentro da aplicação afim de auxiliar na avaliação.  

## Orientações e regras para uso da API
### Meeting: Assembléia
- Criar uma assembléia
    - Não pode haver assembléia com o mesmo título e dia cadastrado
- Adicionar associados a assembléia: Possibilidade de escolher associado e atribuí-lo como moderador
    - Não pode adicionar o associado mais de uma vez na assembléia
### Schedule: Pauta
- Criar pauta 
    - Associar a um Meeting já existente
    - Não pode haver pauta associada a esta pauta com este mesmo nome
### Session: Sessão
- Abrir Sessão
    - Informar Pauta já existente
    - Não pode haver sessão já associada a esta Pauta
    - A data de início não pode ser inferior a data atual
    - A data final não pode ser inferior a data inicial
    - Caso a data final estiver em branco, informar no campo data inicial + 1 minuto
    - A data inicial não pode ser menor que a data de início da assembléia
- Rotina para finalizar contagem de votos
    - Rotina executada de minuto a minuto para consultar sessões finalizadas e enviar para a fila a contagem de resultados
### Vote: Voto 
- Votar
    - Sessão deve estar vigente
    - O associado não deve ter votado nesta sessão
    - Associado tem que estar ativo
    - Associado deve estar na lista de convidados da assembléia
### Associate: associado
- Criar
    - Não deve existir usuário cadastrado com este documento
    - O serviço realiza uma consulta de CPF no serviço **user-info**. Caso o serviço responda ABLE_TO_VOTE o campo enabled recebe true caso contrário recebe false.





