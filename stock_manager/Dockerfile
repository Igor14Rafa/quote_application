# Use an official Maven image to build the JAR file
FROM maven:3.9.9-amazoncorretto-23 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download the project dependencies (this step is separate for better caching)
RUN mvn dependency:go-offline

# Copy the rest of the application files
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image for the runtime environment
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/stock-manager-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which the Spring Boot application will run
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]