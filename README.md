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

- JDK 17 or later
- Maven 3.6+
- MySQL/PostgreSQL database (H2 in-memory database used by default for development)
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
mvn clean package
```

4. Run the application
```bash
mvn spring-boot:run
```

## API Endpoints

### Capsule Management
- `POST /api/capsules` - Create a new time capsule
- `GET /api/capsules/{id}` - Get capsule details
- `GET /api/capsules/blockchain/{blockchainId}` - Get capsule by blockchain ID
- `PUT /api/capsules/{id}` - Update capsule (if allowed)
- `PATCH /api/capsules/{id}/status` - Update capsule status
- `POST /api/capsules/{id}/open` - Open a capsule

### User Capsule Queries
- `GET /api/capsules/creator/{address}` - List capsules created by an address
- `GET /api/capsules/recipient/{address}` - List capsules sent to an address
- `GET /api/capsules/address/{address}` - List all capsules associated with an address

### Explore
- `GET /api/capsules/explore/popular` - Most-viewed capsules (query param: `limit`, default 10)
- `GET /api/capsules/explore/featured` - Manually curated featured capsules
- `GET /api/capsules/explore/recent` - Recently opened capsules (query param: `limit`, default 10)
- `GET /api/capsules/explore/subscribed` - Most subscribed sealed capsules (query param: `limit`, default 10)

### Engagement
- `POST /api/capsules/{id}/view` - Increment view count
- `POST /api/capsules/{id}/share` - Increment share count
- `POST /api/capsules/{id}/subscribe` - Subscribe to a capsule (query param: `userAddress`)

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

# Database configuration (H2 used by default for development)
spring.datasource.url=jdbc:mysql://localhost:3306/aionios
spring.datasource.username=username
spring.datasource.password=password

# Web3j / Blockchain configuration
web3j.client-address=https://mainnet.infura.io/v3/your-infura-id
web3j.network-id=1

# Contract address (populate after deployment)
time.capsule.contract.address=0x...

# IPFS configuration
ipfs.node.host=localhost
ipfs.node.port=5001

# JWT configuration
jwt.secret=your-secret-key-min-32-chars
jwt.expiration=86400000

# Capsule scheduler
capsule.scheduler.enabled=true
capsule.scheduler.cron=0 */10 * * * *
```

## Blockchain Integration

The backend integrates with the Ethereum blockchain to:
- Monitor smart contract events
- Index capsule data
- Verify transaction status
- Interact with oracles

A mock blockchain service (`MockBlockchainService`) is active by default for local development. The real Web3j implementation (`Web3jBlockchainService`) is wired in once a deployed contract address is provided.

## IPFS Integration

For large content storage, the backend:
- Uploads files to IPFS
- Stores content hashes on-chain
- Retrieves content from IPFS when needed
- Manages pinning services for persistence

A mock IPFS service (`MockIPFSServiceImpl`) is active by default for local development, storing content in memory. The real IPFS implementation (`IPFSServiceImpl`) connects to a running IPFS node.

## Contributing

Please read the [CONTRIBUTING.md](../CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.
