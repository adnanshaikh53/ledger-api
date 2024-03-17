## Ledger Microservice
# Overview
The Ledger microservice is designed to serve as the source of truth for balances of assets and liabilities of an entity. It allows clients to manage multi-asset accounts, perform asset movements, track account and movement lifecycles, and retrieve historical balance data. 

# Architecture of our Service:
![Design](![Ledger-api-design-final drawio](https://github.com/adnanshaikh53/ledger-api/assets/53299413/52c62c64-3e3a-49a0-a524-ca54084c31a1))

# Endpoints
<img width="936" alt="endpoints" src="https://github.com/adnanshaikh53/ledger-api/assets/53299413/8b76baf0-5a35-48e6-95d4-c6783523066c">


# Entity Management and State changes
POST /entity/create: Create a new Entity with accounts and wallet details.

PUT /accounts/state/update: Change the state of an account. Currently supported OPEN/CLOSED

PUT /posting/state/update: change state of posting. Currently supported PENDING, CLEARED, REJECTED

# Asset Movements

POST /moveAsset: Perform asset movements between wallets. Supports 10 transactions Synchronously

POST /moveAsset/async: Perform asset movements between wallets. Supports more than 10 Transactions

# Historical Balances

GET /balances/{walletId}?timestamp={timestamp}: Retrieve the historical balance of a wallet at a given timestamp.

GET /balances/{walletId}: Retrieve the current balance of a wallet at a given timestamp.

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
Start all services mentioned in docker compose file:
Images of Zookeeper, kafka, Postgres master and replica for CQRS are provided
in the postgre init db add: init.sql provided in the repo
Once all instances of postgres, kafka and zookeeper are running on docker
Start Ledger-api servive
Kafka Topics are created in KafkaTopicCreation.java no need to provide topic creation manually
Access the API documentation provided through Swagger or Postman.
[local](http://localhost:8080/swagger-ui/index.html#/)
Documentation
For detailed usage instructions, refer to the API Documentation and the Client Manual.


Contributing
Contributions are welcome! Please open an issue to discuss any changes or improvements you would like to make.
