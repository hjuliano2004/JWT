FROM maven:3.8.6-amazoncorretto-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -X -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar

ENTRYPOINT [ "java", "-Dserver.port=80", "-jar", "/app/app.jar" ]

EXPOSE 80