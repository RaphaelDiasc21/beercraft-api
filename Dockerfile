FROM openjdk:11-jre
FROM maven:3-openjdk-11
RUN mkdir app
ARG JAR_FILE
COPY . /app
WORKDIR /app
RUN mvn package spring-boot:repackage
ENTRYPOINT java -jar /target/Scraft-beer-1.0.jar 