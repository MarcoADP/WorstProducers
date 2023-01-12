# WorstProducers

API para retornar informações sobre produtores que venceram a categoria de pior filme do Golden Raspberry Awards.

Stack:
- Java
- Spring
- Maven
- Lombok
- OpenAPI
- H2

- Run: mvn spring-boot:run
- Test: mvn test
- OpenAPI: http://localhost:8080/swagger-ui/index.html#/
- Chamada Funcionalidade: (GET) http://localhost:8080/award-interval

Instruções:
- Para rodar o programa é necessário ter o arquivo de importação da base de dados na pasta src/main/resourcers ou então um erro será lançado no momento de inicialização.
- O arquivo csv precisa conter o padrão (year, title, studios, producers, winner). Todos os campos são considerados obrigatórios, exceto winner.
- O nome padrão do arquivo com filmes é movielist.csv.
- Para rodar um arquivo com diferente nome é necessário alterar a variável moviecsv.filename do application.properties, que está localizado no mesmo diretório, para o nome do arquivo desejado. 
