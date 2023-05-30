FROM openjdk:19
VOLUME /tmp
COPY ../build/libs/ConfigService-0.0.1-Config.jar config.jar
ENTRYPOINT ["java","-jar","/config.jar"]