FROM maven:3.9.9-amazoncorretto-21-debian

COPY target/consultoriomedicoele-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]