#### Stage 1: Build the application
FROM maven:3.8.4-amazoncorretto-11 as build
ARG APP_NAME=playtomic-wallet

COPY . .
RUN mvn clean install
CMD mvn spring-boot:run