# Stage 1: Build stage
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY pom.xml ./
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]
