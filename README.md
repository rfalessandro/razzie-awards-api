# Razzie Awards API

Esta aplicação tem como objetivo importar dados de um arquivo CSV contendo
informações sobre filmes premiados e disponibilizar, via API RESTful, o
produtor com maior intervalo entre prêmios e o que conquistou dois prêmios em
menor tempo.

O projeto foi desenvolvido com Quarkus, uma framework Java que permite criar
aplicações de alta performance e escala.

## Endpoints

A aplicação disponibiliza os seguintes endpoints:
- GET `/api/v1/producers/award-intervals/worst-movie`: Retorna o produtor com maior intervalo entre prêmios e o que conquistou dois prêmios em menor tempo.
- GET `/q/swagger-ui/`: Web interface para a Documentação da API (OpenAPI spec).
- GET `/swagger?format=json`: Documentação da API (OpenAPI spec) em formato JSON.

### Exemplo de uso

```shell script
curl -X GET "http://localhost:8080/api/v1/producers/award-intervals/worst-movie"
```
Dica: para formatar o json de resposta pode ser usado o `jq`
```shell script
curl -X GET "http://localhost:8080/api/v1/producers/award-intervals/worst-movie" | jq
```

## Executando a Aplicação em Modo de Desenvolvimento

Você pode executar sua aplicação em modo de desenvolvimento que permite codificação ao vivo usando:

```shell script
./gradlew quarkusDev
```

### Usar outro CSV
Para usar outros CSV faça o override da propriedade `csv.file.path` com o caminho para o CSV desejado.

Parametro da JVM
```shell script
-Dcsv.file.path=moviestest.csv
```
Environment Variable

```shell script
CSV_FILE_PATH=moviestest.csv
```
* Comando completo usando uberjar
```shell script
java -Dcsv.file.path=moviestest.csv -jar razzie-awards-api/build/razzie-awards-api-1.0.0-SNAPSHOT-runner.jar
```

Outras opçoes: https://quarkus.io/guides/config-reference


## Executando os testes

```shell script
./gradlew test
```
ou

```shell script
./gradlew check
```

## Empacotamento e Execução da Aplicação

A aplicação pode ser empacotada usando:

```shell script
./gradlew build
```

Isso produz o arquivo `quarkus-run.jar` no diretório `build/quarkus-app/`.
Tenha em mente que não é um _uber-jar_ porque as dependências são copiadas para o diretório `build/quarkus-app/lib/`.

A aplicação agora pode ser executada usando `java -jar build/quarkus-run.jar`.

Se você quiser criar um _uber-jar_, execute o seguinte comando:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

A aplicação, empacotada como um _uber-jar_, pode ser executada usando `java -jar build/*-runner.jar`.


## Criando um Executável Nativo

Você pode criar um executável nativo usando:

```shell script
./gradlew build -Dquarkus.package.type=native
```

Ou, se você não tiver o GraalVM instalado, você pode executar a construção do executável nativo em um contêiner usando:

```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./build/razzie-awards-api-1.0.0-SNAPSHOT-runner`


## Formatando o Código

O projeto utiliza o [Spotless](https://github.com/diffplug/spotless) para formatar o código, execute:
```shell script
./gradlew spotlessCheck
```
O comando acima pode ser usado em qualquer CI, como Jenkins ou GitHub Actions para validar o formato do código.
O `spotless` ainda conta com uma auto-fix para formatar o código.
```shell script
./gradlew spotlessApply
```
