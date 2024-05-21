FROM openjdk:17

WORKDIR /app

COPY target/Faceit-0.0.1-SNAPSHOT.jar faceit.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "faceit.jar"]