FROM azul/zulu-openjdk-alpine:8u322 as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src src

RUN  chmod +x mvnw
RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine

WORKDIR /app

ARG DEPENDENCY=/app/target/book-1.0
COPY --from=build /app/target/book-1.0.jar /app

EXPOSE 8083

ENTRYPOINT ["java","-jar","/app/book-1.0.jar"]