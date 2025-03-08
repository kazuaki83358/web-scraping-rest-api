# Use a lightweight JDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the generated JAR file from the Gradle build
COPY build/libs/web-scraping-all.jar app.jar

# Expose the port your Ktor app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
