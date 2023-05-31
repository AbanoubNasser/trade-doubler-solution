FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/tradedoubler-product-processor-service-1.0.0-SNAPSHOT.jar .
EXPOSE 9090
ENTRYPOINT ["java","-jar","tradedoubler-product-processor-service-1.0.0-SNAPSHOT.jar"]