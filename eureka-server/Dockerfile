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

ARG DEPENDENCY=/app/target/eureka-server
COPY --from=build /app/target/eureka-server.jar /app

EXPOSE 8761

ENTRYPOINT ["java","-jar","/app/eureka-server.jar"]

