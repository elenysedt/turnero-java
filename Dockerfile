FROM maven:3.8.5-openjdk-17 AS build

COPY target/consultoriomedicoele-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]