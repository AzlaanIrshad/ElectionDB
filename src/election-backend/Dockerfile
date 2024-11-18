# Stage 1: Build stage
FROM openjdk:21-slim AS build

# Installeer Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

# Stage 2: Runtime stage
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
