FROM maven:3.9.9-amazoncorretto-23-alpine AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DSkipTests
EXPOSE 8080
CMD ["java","-jar","/app/target/lol-0.0.1-SNAPSHOT.jar"]