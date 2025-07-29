# Start with an official Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Metadata (optional but useful)
LABEL maintainer="timthecook0705@gmail.com"

# Set working directory
WORKDIR /app

# Expose the port Spring Boot runs on
EXPOSE 8080

# Copy compiled JAR into container
COPY target/accountservice-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
