FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bugwars-healer-backend-0.0.1-SNAPSHOT.jar bug-wars.jar
COPY src/main/resources/data.sql /app/data.sql
WORKDIR /app
RUN apt-get update && apt-get install -y postgresql-client
RUN psql -h ${DB_HOST} -U ${DB_USER} -d ${DB_NAME} -a -f data.sql
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bug-wars.jar"]