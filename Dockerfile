FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bugwars-healer-backend-0.0.1-SNAPSHOT.jar bug-wars.jar
COPY src/main/resources/data.sql /app/data.sql
WORKDIR /app
RUN apt-get update && apt-get install -y postgresql-client
RUN psql -h <dpg-cmcb82o21fec73cp5rk0-a> -U <bug_wars_o8wh_user> -d <bug_wars_o8wh> -a -f data.sql
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bug-wars.jar"]