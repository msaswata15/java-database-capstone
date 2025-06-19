# Step 1: Use Maven with Temurin JDK 21 for building the app
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Maven is pre-installed; no need to apt-get anything
RUN mvn clean package -DskipTests

# Step 2: Use Temurin JRE 17 for runtime
FROM eclipse-temurin:17.0.15_6-jre

WORKDIR /app

COPY --from=builder /app/target/back-end-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]