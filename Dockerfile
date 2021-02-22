FROM openjdk:11-jre
ARG JAR_FILE
RUN mkdir app
WORKDIR /app
ADD /target/craft-beer-1.0.jar /app/craft-beer-1.0.jar 
ENTRYPOINT java -jar craft-beer-1.0.jar