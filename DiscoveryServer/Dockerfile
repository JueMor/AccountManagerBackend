FROM openjdk:19
VOLUME /tmp
COPY ../build/libs/DiscoveryServer-0.0.1-Discovery.jar gateway.jar
ENTRYPOINT ["java","-jar","/gateway.jar"]