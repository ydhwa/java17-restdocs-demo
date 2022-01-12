# Java 17 Rest Docs Demo

## links

- http://localhost:8000
- http://localhost:8000/docs/index.html

## dependencies

- Spring Boot 2.6.2
- Gradle 7.3
  - https://docs.gradle.org/current/userguide/compatibility.html
- Spring Web
- Spring REST docs + Asciidoctor
  - https://plugins.gradle.org/plugin/org.asciidoctor.jvm.convert
  - https://github.com/spring-projects/spring-restdocs/tree/main/samples/rest-notes-spring-hateoas
- Lombok
- Spring HATEOAS
- Spring Actuator + Prometheus
- Spring Data JPA
- Querydsl
  - http://honeymon.io/tech/2020/07/09/gradle-annotation-processor-with-querydsl.html
  - https://stackoverflow.com/questions/62521275/problem-with-generating-querydsl-classes-with-gradle

## build

```bash
./gradlew build -x test
java -jar build/libs/java17-restdocs-demo-0.0.1-SNAPSHOT.jar
```
