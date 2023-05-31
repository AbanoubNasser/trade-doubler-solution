FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/tradedoubler-product-service-1.0.0-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","tradedoubler-product-service-1.0.0-SNAPSHOT.jar"]