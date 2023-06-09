# Trade Doubler Solution
trade doubler solution is a microservice based solution to handle Advertisers needs which are
required to upload their products and provide relevant offer details to the
platform, which can be accessed by publishers through REST APIs.

Trade Doubler solution is consist of two microservices
* tradedoubler-product-service
* tradedoubler-product-processor-service

## Tradedoubler-product-service
is a small microservice to handle files uploads and uploaded file identification.

Service Structure
* API package which contains all expected expose apis
* Config package which contains all related service config
* exception package to handle exceptions structure
* model package for entity layer
* Repository package for Data access layer
* Service package to handle all business logic
* DB migration director in resources to handle DB scripts migrations

## Tech Stack
* Java 17
* Spring boot 3
* flyway migration
* Postgres DB
* Junit5 
* Jacoco plugin (Test coverage)

## Database 
This service using Postgres DB with two tables
* products_files to handle the info for uploaded products files
* flyway migration table for manage DB migration versions

## CURLS
* to upload products file and from response header you can get the id for uploaded file in location header

```
curl --location 'http://localhost:8080/api/v1/products/files' \
--form 'file=@"/Samples/Products.xml"'
```
* To update uploaded file status
```
curl --location --request PUT 'http://localhost:8080/api/v1/products/files/ee35e39d-bcb7-46b9-acd0-6659ee302fa8' \
--header 'Content-Type: application/json' \
--data '{
    "status":"FAILED_TO_PROCESS",
    "comment":"Failed to parse"
}'
```
* To get uploaded file content
```
http://localhost:8080/api/v1/products/files/cde2c86a-118a-4696-b737-bfbb5f9f19f8/content
```

*To Get al uploaded file with specific status
```
curl --location 'http://localhost:8080/api/v1/products/files?status=UPLOADED'
```

## Tradedoubler-product-processor-service
is a small microservice process and download the uploaded file in 4 phases
* By Scheduler Job will send request to the tradedoubler-product-service to retrieve uploaded files
* Start validate and parse uploaded files
* update the status of the uploaded file in case failure or success
* persist products in the DB with specified uploaded file identification 

Service Structure
* API package which contains all expected expose apis
* Config package which contains all related service config
* BO Model for mapping from XML to immutable classes
* Integration package contains fiegn client to integrate with rest service
* exception package to handle exceptions structure
* model package for entity layer
* Repository package for Data access layer
* Service package to handle all business logic
* DB migration director in resources to handle DB scripts migrations

## Tech Stack
* Java 17
* Spring boot 3
* Feign client
* Mapstruct
* flyway migration
* XML, JSON, CSV Mappers
* Postgres DB
* Junit5
* Jacoco plugin (Test coverage)

## Database
This service using Postgres DB with the following ERD
![](https://github.com/AbanoubNasser/trade-doubler-solution/blob/master/ERD.png)

## CURLs
* To Export persisted products in DB with Uploaded file id and format in [XML, CSV, JSON]
```
curl --location 'http://localhost:9090/api/v1/products?productsFileId=d9643dab-8b0e-4dce-91e9-cc3053365716&exportFileType=CSV'
```
