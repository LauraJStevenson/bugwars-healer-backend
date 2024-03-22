FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bugwars-healer-backend-0.0.1-SNAPSHOT.jar bug-wars/bug-wars.jar
COPY src/main/resources/data.sql /docker-entrypoint-initdb.d/
RUN apt-get update && apt-get install -y postgresql-client
VOLUME /var/lib/postgresql/data
EXPOSE 5432
ENV POSTGRES_USER=$DB_USER
ENV POSTGRES_PASSWORD=$DB_PASSWORD
ENV POSTGRES_DB=$DB_NAME
WORKDIR /bug-wars
ENTRYPOINT ["java", "-jar", "bug-wars.jar", "bug-wars"]