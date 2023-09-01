# Use an official Maven runtime with OpenJDK 17 as a parent image
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY src ./src
COPY pom.xml ./

# Build the application using Maven
RUN mvn clean package -X



# Set the working directory inside the runtime container
WORKDIR /app/target

# Copy the built JAR file from the build container to the runtime container
#COPY /target/starter-1-fat.jar ./app-fat.jar

# Expose the port your Vert.x application is listening on
EXPOSE 8080

# Specify the command to run your Vert.x application
CMD ["java", "-jar", "starter-1-fat.jar"]
