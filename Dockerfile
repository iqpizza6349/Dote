FROM openjdk:11-jdk-slim as builder
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.yml", "-jar", "app.jar"]
