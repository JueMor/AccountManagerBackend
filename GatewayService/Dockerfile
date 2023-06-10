FROM openjdk:19
VOLUME /tmp
COPY ../build/libs/GatewayService-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]