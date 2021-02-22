FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/craft-beer-1.0.jar /app/craft-beer-1.0.jar 
ENTRYPOINT java -jar craft-beer-1.0.jar