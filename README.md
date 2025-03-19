# Product API

This document provides instructions for executing the Product API service, explains the architectural, business and technical decisions, and suggests immediate improvements.
## Project Overview

This project is a Spring Boot application written in Kotlin that provides a REST API for retrieving a product catalog. It adheres to Hexagonal Architecture principles to ensure maintainability, testability, and flexibility.
## Prerequisites

- **Java 17+:** Ensure you have Java 17 or a later version installed.
- **Gradle:** Gradle is used for building and managing dependencies.
- **Docker** In order to run the application with a containerised database.
- **Docker compose** A tool for defining and running multi-container applications.
## Running the Application

### Building the JAR file

Open the terminal and navigate to the root directory (`products/productsapi`). Execute the following command:

```bash
# For Windows
gradlew.bat build

#For Linux/MacOS
./gradlew build
```

### Building the images

Ensure you have Docker and Docker Compose installed. Run this command to build the images:

```bash
docker-compose build
```

Once the build is successful, run:

```bash
docker-compose up -d
```

And the containers of both the service and the database should be up, with the database populated. Now you can send requests using curl or a client like Postman. The endpoints are:

``` bash
http://localhost:8080/products
http://localhost:8080/health
```

### Documentation

Once running, the service documentation can be found at `http://localhost:8080/swagger-ui/index.html`
## Architectural decisions

An hexagonal architecture approach was chosen to decouple the core business logic from external dependencies (database, web, etc.) because it promotes testability and maintainability by separating concerns.

The domain layer contains the  models. In this case we decided to create `Discount` as a model since it allows modularisation and future extensibility and because a discount is an integral part of the business domain.

The application layer contains the `ProductService` class that encapsulates all the core business logic of the service.

The infrastructure layer contains adapters for external systems, namely the database connectivity and the endpoint controller. The controller incorporates some little validations to ensure the validity of the parameters received.

## Business decisions

The endpoint always returns at least the first 10 products in the catalog ordered by SKU. This decision was made for two main reasons:

- The endpoint should always return the catalog at the very least, since it is in itself a catalog endpoint. An error should be returned only if there are no products in the database.
- It's assumed that this endpoint is not exposed through a public API, so anyone querying it should be familiar with the specification thanks to the OpenAPI documentation.

If the request is malformed an empty page is returned with an error code in the header. It should be further discussed if it's better to send an error or an empty response upstream.

In case a sorting parameter is not present the service sorts by `SKU` by default.
## Technical decisions

There are a few technical decisions that must be explained:

- Since the service always returns something regardless of the parameters of the request, provided that the database is populated, for the purposes of this technical test it wasn't necessary to use `Optional`.
- Two kinds of integration tests are present, one that maps the response to a string and another that maps it to an object. The first one could be used when the response is always the same and can be compared with a JSON file, but since the `expected` and `actual` values in a test that inserts and retrieves products from the database cannot be the same because they differ in the `createdAt` field, the mapping to an object option has been provided.

## Future considerations

### Improved input validation

A validator for the category parameter in the endpoint controller should be created to make sure the category exists. This could be done using  custom validator based on a `categories` table in the database acting like a source of truth that would populate the fields on start up.

### Error handling

As of now there's no error handling beyond the simple validation in the controller. Since the query is so simple and the database always has values in it, the only error would be a validation error or a system error that prevents the normal functioning of the service. Once the business logic grows it would be necessary to add exception handling, error logging and more informative HTTP status codes.

### Observability

We can integrate metrics collection, distributed tracing and centralised logging. Metrics, such as request latency and error rates, can be exposed via Prometheus and visualised with Grafana, providing real-time insights into performance. Centralised logging, implemented with ELK stack or similar solutions, would aggregate logs from all components, enabling error analysis and debugging.

