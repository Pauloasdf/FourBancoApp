FROM openjdk:10.0.2-13-jdk-slim

EXPOSE 8080

RUN mkdir -p /opt/application/jar
WORKDIR /opt/application/jar
COPY build/libs/FourBancoApp-0.0.1-SNAPSHOT.jar /opt/application/jar/FourBancoApp-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", \
    "-jar", \
    "/opt/application/jar/FourBancoApp-0.0.1-SNAPSHOT.jar" ]