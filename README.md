## Ledger Microservice
# Overview
The Ledger microservice is designed to serve as the source of truth for balances of assets and liabilities of an entity. It allows clients to manage multi-asset accounts, perform asset movements, track account and movement lifecycles, and retrieve historical balance data. Built with the latest Java and Spring Boot technologies, the Ledger microservice provides both synchronous and asynchronous communication capabilities to its clients.

# Architecture of our Service:

# How to run: 
1. Start all services mentioned in docker compose file:
2.Images of Zookeeper, kafka, Postgres master and replica for CQRS are provided
in the postgre init db add: init.sql provided in the repo
3.Once all istnces of postgres, kafka and zookeeper are running on docker
4. Start Ledger-api servive
5. Kafka Topics are created in KafkaTopicCreation.java no need to provide topic creation manually
# Features

# Endpoints
# Account Management
POST /accounts: Create a new account.
PUT /accounts/{accountId}/state: Change the state of an account.
GET /accounts/{accountId}/state: Retrieve the current state of an account.
# Asset Movements
POST /movements: Perform asset movements between wallets.
PUT /movements/{movementId}: Change the state of a movement.
GET /movements/{movementId}: Retrieve details of a specific movement.
# Historical Balances
GET /balances/{walletId}?timestamp={timestamp}: Retrieve the historical balance of a wallet at a given timestamp.

# Technologies Used
Java
Spring Boot
Spring Data
CQRS (Command Query Responsibility Segregation) pattern
RESTful API
Kafka (for asynchronous communication and Broadcast Notifications)
PostgreSQL 

# Getting Started
Clone the repository.
Configure the database settings in application.properties.
Build and run the application using Maven or your preferred IDE.
Access the API documentation provided through Swagger or Postman.
[local](http://localhost:8080/swagger-ui/index.html#/)
Documentation
For detailed usage instructions, refer to the API Documentation and the Client Manual.

Contributing
Contributions are welcome! Please open an issue to discuss any changes or improvements you would like to make.
