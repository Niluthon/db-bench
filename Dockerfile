# Use an official Maven runtime with OpenJDK 17 as a parent image
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the pom.xml and the source code to the container
COPY pom.xml .
COPY src src

# Build the Maven project inside the container
RUN mvn clean install

# Copy the compiled JAR file to the container
COPY target/starter-1-fat.jar .

# Expose the port your Vert.x application will run on (change as needed)
EXPOSE 8080

# Define the command to run your Vert.x application using java -jar
CMD ["java", "-jar", "starter-1-fat.jar"]
