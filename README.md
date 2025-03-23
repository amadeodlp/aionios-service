# AIONIOS Backend

Java backend services for the AIONIOS blockchain time capsule project.

## Overview

This Java-based backend provides REST APIs and services that support the AIONIOS time capsule platform. It handles user management, notification services, and integration with blockchain and decentralized storage systems.

## Features

- REST API endpoints for frontend interaction
- IPFS integration for large content storage
- Blockchain event indexing and monitoring
- Notification services for capsule-related events
- User management and authentication

## Tech Stack

- **Java** - Core programming language
- **Spring Boot** - Web application framework
- **Hibernate/JPA** - Database ORM
- **Web3j** - Ethereum blockchain integration
- **IPFS-HTTP-Client** - IPFS storage integration

## Getting Started

### Prerequisites

- JDK 11 or later
- Maven or Gradle
- MySQL/PostgreSQL database
- IPFS node (local or remote)

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd AIONIOS/backend
```

2. Configure application properties in `src/main/resources/application.properties` or through environment variables

3. Build the application
```bash
./mvnw clean package
# or with gradle
./gradlew build
```

4. Run the application
```bash
./mvnw spring-boot:run
# or with gradle
./gradlew bootRun
```

## API Endpoints

### Capsule Management
- `POST /api/capsules` - Create a new time capsule
- `GET /api/capsules` - List user's time capsules
- `GET /api/capsules/{id}` - Get capsule details
- `PUT /api/capsules/{id}` - Update capsule (if allowed)

### User Management
- `POST /api/users` - Register new user
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile

### IPFS Content
- `POST /api/content` - Upload content to IPFS
- `GET /api/content/{cid}` - Retrieve content from IPFS

## Configuration

The application can be configured using the following properties:

```
# Server configuration
server.port=8080

# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/aionios
spring.datasource.username=username
spring.datasource.password=password

# Blockchain configuration
blockchain.network=rinkeby
blockchain.contract.address=0x...
blockchain.web3.endpoint=https://rinkeby.infura.io/v3/your-infura-id

# IPFS configuration
ipfs.node.host=localhost
ipfs.node.port=5001
ipfs.gateway.url=https://ipfs.io/ipfs/
```

## Blockchain Integration

The backend integrates with the Ethereum blockchain to:
- Monitor smart contract events
- Index capsule data
- Verify transaction status
- Interact with oracles

## IPFS Integration

For large content storage, the backend:
- Uploads files to IPFS
- Stores content hashes on-chain
- Retrieves content from IPFS when needed
- Manages pinning services for persistence

## Contributing

Please read the [CONTRIBUTING.md](../CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.
