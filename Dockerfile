#FROM gradle:8.1.1-jdk17 AS builder
#WORKDIR /app
#COPY . .
#RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/Connectify-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]