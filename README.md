# Chess-API
_Game in Spring Boot using the State Machine and Antlr4_

## Description
A server application that executes a game of Chess exposing the interactions with an REST API.
It allows two Players to play the game on the same machine.

---

# Local Development
## Technology Stack
- [Java 17](https://openjdk.org/projects/jdk/17/)
- [Spring Boot v2.7.5](https://spring.io/projects/spring-boot)
- [Antlr v4](https://www.antlr.org/download.html)
- [Maven 3.8.6](https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache/)


## Build
This project uses the [ANTLR 4 Maven plugin](https://www.antlr.org/api/maven-plugin/latest/usage.html) to generate the parser sources in the `compile` maven lifecycle.
```shell
# generate the source code based on the grammar in the directory /target/generated-sources/antlr4
$ ./mvnw clean compile
```

> Note: if you want your IDE to recognize the generated sources, you must add the `/target/generated-sources/antlr4` as one of your Source Folders

## Run Tests
To ensure the local environment is all set.
```shell
$ ./mvnw clean verify
```

## Run
One of the benefits of Sprint Boot is that it comes with an embedded web server. Using the Maven build provided by the _Spring Boot Initialzr_, you'll get a `.jar` file inside the `/target` directory. The `.jar` file can run the entire Spring application.

```shell
# generates chess-api-0.0.1-SNAPSHOT.jar
$ ./mvnw clean install

# runs the application using the embedded web server.
$ java -jar target/chess-api-0.0.1-SNAPSHOT.jar
```

After running it you can take a look at how to play the game at [User Manual](docs/UM.md) 

---

# User Guide
## Documentation
- [User Manual](docs/UM.md)
- [API Documentation](docs/API.md)
- [Software Architecture Document](docs/SAD.md)

---

# Authors
- [Gerardo Cortes](mailto:gerardo.cortes.o@gmail.com?subject=You%20are%20hired)
