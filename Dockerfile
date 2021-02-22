FROM openjdk:11-jre
FROM maven:3-openjdk-11
RUN mkdir app
ARG JAR_FILE
COPY . /app
WORKDIR /app
RUN mvn package spring-boot:repackage
ADD /target/craft-beer-1.0.jar /app/craft-beer-1.0.jar 
ENTRYPOINT java -jar craft-beer-1.0.jar 