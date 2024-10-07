FROM ubuntu:latest AS build
LABEL authors="PATRICK-ANJOS"

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y

RUN mvn clean install

FROM openjdk:17-jdk-slim

COPY --from=build /target/order-service-0.0.1.jar order-service.jar

EXPOSE 8080

ENV PORT=from_compose
ENV MONGO_URL=from_compose
ENV MONGO_DATABASE=from_compose
ENV SQS_PROCESSED_ORDERS_QUEUE=from_compose
ENV SNS_ORDERS_TOPIC=from_compose

ENTRYPOINT [ "java", "-jar", "order-service.jar" ]