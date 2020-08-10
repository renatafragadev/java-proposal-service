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


### Aspectos gerais da aplicação
Na implementação deste projeto foi prezado as seguintes práticas:
- Tentativa de separar as entidades em domínio agregador e objetos de valor (DDD)
- Uso otimizado do ORM buscando melhores práticas tanto na persistência quanto na consulta de dados
- Tratamento de exceções 
- Padrões REST (nível de maturidade 3): tratamento de erros, payload de resposta, códigos de resposta, URIs
- Criação de anotações customizadas
- Integração com barramento (RabbitMQ)
- Integração com serviços externos via HTTP (utilizando WebClient)
- Uso de componentes customizados a fim de prover baixo acoplamento 
- Implementação de padrões de projeto (Builder, Converter, Singleton, Assembler Model, etc)
- Testes unitários e de integração focado em camadas - agilidade e objetividade (não havendo necessidade de disponibilizar todo o contexto Spring, deixando os testes mais lentos) 
- Versionamento dos scripts de base de dados 
#### Pontos a melhorar:
- Pelo tempo curto não pude realizar uma cobertura completa nos testes unitários, por isso, criei alguns em ambas camadas (web e service) com objetivo de mostrar meu conhecimento acerca do Mockito e o SpringExtension. Não achei pertinente no momento criar um teste de integração com a base de dados, porém se tivesse mais tempo teria utilizado o banco em memória.  
- Documentação de API: pelo mesmo motivo consegui apenas fazer a introdução do Swagger,mas meu objetivo era ter feito a documentação de API e de filas utilizando AsyncAPI. 

