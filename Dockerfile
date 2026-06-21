# 1. Start with a lightweight Linux machine that already has Java 17 installed
FROM eclipse-temurin:17-jdk-alpine

# 2. Create a temporary volume for Spring Boot's internal Tomcat server
VOLUME /tmp

# 3. Copy the compiled .jar file from your computer/GitHub into the Docker container
COPY target/preorder-system-0.0.1-SNAPSHOT.jar app.jar

# 4. Tell the container what command to run when it starts up
ENTRYPOINT ["java","-jar","/app.jar"]


