Ledger Microservice
Overview
The Ledger microservice is designed to serve as the source of truth for balances of assets and liabilities of an entity. It allows clients to manage multi-asset accounts, perform asset movements, track account and movement lifecycles, and retrieve historical balance data. Built with the latest Java and Spring Boot technologies, the Ledger microservice provides both synchronous and asynchronous communication capabilities to its clients.

Features
Multi-Asset Account Management: Supports creation and management of accounts with multiple wallets representing various assets.
Asset Movements: Allows clients to move assets between wallets within an account.
Transaction Consistency: Guarantees "all or nothing" execution for asset movement requests.
Account Lifecycle Management: Facilitates account state transitions such as OPEN and CLOSED.
Posting Lifecycle Management: Supports various posting states like PENDING, CLEARED, and FAILED.
Balance Change Broadcasting: Broadcasts balance changes and movements to clients for real-time updates.
Asynchronous Communication: Enables clients to make movement requests in asynchronous mode.
Historical Data Retrieval: Provides clients with historical balance data for specific wallets at given timestamps.
Scalable Database Design: Utilizes CQRS pattern for efficient handling of write-heavy operations.
Endpoints
Account Management
POST /accounts: Create a new account.
PUT /accounts/{accountId}/state: Change the state of an account.
GET /accounts/{accountId}/state: Retrieve the current state of an account.
Asset Movements
POST /movements: Perform asset movements between wallets.
PUT /movements/{movementId}: Change the state of a movement.
GET /movements/{movementId}: Retrieve details of a specific movement.
Historical Balances
GET /balances/{walletId}?timestamp={timestamp}: Retrieve the historical balance of a wallet at a given timestamp.
Webhooks
POST /webhooks/balance: Endpoint for clients to subscribe to balance change notifications.
POST /webhooks/movement: Endpoint for clients to subscribe to movement notifications.
Technologies Used
Java
Spring Boot
Spring Data
CQRS (Command Query Responsibility Segregation) pattern
RESTful API
WebSocket (for asynchronous communication)
MySQL (or any preferred database for persistence)
Getting Started
Clone the repository.
Configure the database settings in application.properties.
Build and run the application using Maven or your preferred IDE.
Access the API documentation provided through Swagger or Postman.
Documentation
For detailed usage instructions, refer to the API Documentation and the Client Manual.

Contributing
Contributions are welcome! Please open an issue to discuss any changes or improvements you would like to make.
