FROM openjdk:11-jre
FROM maven:3-openjdk-11
RUN mkdir app
ARG JAR_FILE
ADD /target/craft-beer-1.0.jar /app/craft-beer-1.0.jar 
WORKDIR /app
ENTRYPOINT java -jar craft-beer-1.0.jar 