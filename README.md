# Razzie Awards API

Esta aplicação tem como objetivo importar dados de um arquivo CSV contendo
informações sobre filmes premiados e disponibilizar, via API RESTful, o
produtor com maior intervalo entre prêmios e o que conquistou dois prêmios em
menor tempo.

O projet foi desenvolvido com Quarkus, uma framework Java que permite criar
aplicações de alta performance e escala.

## Executando a Aplicação em Modo de Desenvolvimento

Você pode executar sua aplicação em modo de desenvolvimento que permite codificação ao vivo usando:

```shell script
./gradlew quarkusDev
```

## Executando os testes

```shell script
./gradlew test
```
ou

```shell script
./gradlew check
```

## OpenAPI Spec

A documentação da API pode ser encontrada na rota `/q/swagger-ui/` ou na rota `/swagger?format=json`


## Spotless

O projeto utiliza o [Spotless](https://github.com/diffplug/spotless) para formatar o código, execute:
```shell script
./gradlew spotlessCheck
```
O comando acima pode ser usado em qualquer CI, como Jenkins ou GitHub Actions para validar o formato do código.
O `spotless` ainda conta com uma auto-fix para formatar o código.
```shell script
./gradlew spotlessApply
```

# Usar outro CSV
Para usar outros CSV faça o override da propriedade `csv.file.path` com o caminho para o CSV desejado.

Parametro da JVM
```shell script
-Dcsv.file.path=moviestest.csv
```
Environment Variable

```shell script
CSV_FILE_PATH=moviestest.csv
```
Outras opçoes: https://quarkus.io/guides/config-reference

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
./gradlew build -Dquarkus.native.enabled=true
```

Ou, se você não tiver o GraalVM instalado, você pode executar a construção do executável nativo em um contêiner usando:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./build/razzie-awards-api-1.0.0-SNAPSHOT-runner`
