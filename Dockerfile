FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bugwars-healer-backend-0.0.1-SNAPSHOT.jar bug-wars/bug-wars.jar
COPY src/main/resources/data.sql /bug-wars/
WORKDIR /bug-wars
RUN apt-get update && apt-get install -y postgresql-client

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bug-wars.jar", "bug-wars"]