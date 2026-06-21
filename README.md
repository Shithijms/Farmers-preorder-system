# 🌾 Farmers' Market Pre-Order System

A full-stack Spring Boot REST API that digitises the pre-ordering workflow for farmers' markets — letting customers browse fresh produce, place multi-item orders, and track order status in real time. Built to mirror the kind of in-house retail systems used by companies like Belc.


---

## Why this project exists

Farmers' markets have a core inefficiency: farmers have no idea how many customers will show up, so they either overproduce and waste or sell out early. Customers don't know what's available until they physically arrive. This system solves both problems — farmers list their produce in advance, customers pre-order, and the system manages inventory and order lifecycle automatically.

---

## Features

- Farmer and product management — farmers register their produce with quantity and price
- Customer registration with validation
- Multi-item pre-orders — customers can order from multiple farmers in a single order
- Real-time inventory tracking — stock decrements on order, restores on cancellation
- Order lifecycle management — `PENDING → CONFIRMED → COMPLETED` with `CANCELLED` at any stage
- Business rule enforcement — can't confirm a completed order, can't cancel a completed order, etc.
- Structured error responses with timestamps and HTTP status codes
- CI/CD pipeline — every push to `main` automatically builds, containerises, and deploys to AWS EC2

---

## Tech stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot, Spring Data JPA |
| Validation | Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Email`) |
| Database | H2 (in-memory, dev) / configurable via env vars |
| Containerisation | Docker, Docker Hub |
| CI/CD | GitHub Actions |
| Cloud | AWS EC2 |
| Code quality | Qodana (JetBrains static analysis) |
| Frontend | HTML, Bootstrap 5, Vanilla JS |

---

## Architecture

```
┌─────────────────────────────────────────────┐
│               Spring Boot App               │
│                                             │
│  Controller → Service → Repository → DB     │
│                                             │
│  GlobalExceptionHandler (all error paths)   │
└─────────────────────────────────────────────┘
```

### Entity relationships

```
Farmer (1) ──── (many) Product
                    │
                    └──── (many) OrderProduct ──── (many) PreOrder ──── (1) Customer
```

- A `Farmer` lists many `Product`s
- A `PreOrder` belongs to one `Customer` and contains many `OrderProduct` line items
- Each `OrderProduct` links a product to an order with a specific quantity

---

## API endpoints

### Farmers
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/farmers` | List all farmers |
| `POST` | `/api/farmers` | Register a new farmer |

### Products
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/products` | List all products with stock levels |
| `POST` | `/api/products` | Add a new product under a farmer |

### Customers
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/customers` | List all customers |
| `POST` | `/api/customers` | Register a new customer |

### Orders
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/orders` | List all orders |
| `GET` | `/api/orders/{id}` | Get a specific order |
| `POST` | `/api/orders` | Place a new pre-order |
| `PUT` | `/api/orders/{id}/status` | Update order status |

### Sample order request

```json
POST /api/orders
{
  "customerId": 1,
  "orderProducts": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

### Sample response

```json
{
  "id": 4,
  "orderDate": "2025-06-21T10:30:00",
  "status": "PENDING",
  "totalAmount": 14.97,
  "customer": { "id": 1, "name": "Alice Parker" },
  "orderProducts": [...]
}
```

---

## CI/CD pipeline

Every push to the `main` branch triggers an automated pipeline:

```
Push to main
     │
     ▼
GitHub Actions runner (ubuntu-latest)
     │
     ├── Checkout code
     ├── Set up JDK 17
     ├── mvn clean package (build .jar)
     ├── Docker login (Docker Hub)
     ├── Build & push Docker image → Docker Hub
     │
     ▼
SSH into AWS EC2
     │
     ├── docker pull (latest image)
     ├── docker stop + rm (old container)
     └── docker run (new container on port 8081)
```

Secrets managed via GitHub repository secrets: `DOCKER_USERNAME`, `DOCKER_PASSWORD`, `EC2_HOST`, `EC2_USERNAME`, `EC2_SSH_KEY`.

---

## Running locally

**Prerequisites:** Java 17, Maven

```bash
# Clone the repo
git clone https://github.com/your-username/farmers-market-preorder.git
cd farmers-market-preorder

# Run the app
./mvnw spring-boot:run
```

App starts at `http://localhost:8081`

H2 console available at `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:farmersmarketdb`
- Username: `sa`

### Running with Docker

```bash
# Build the image
docker build -t farmers-market .

# Run the container
docker run -p 8081:8081 farmers-market
```

### Environment variables (for production DB swap)

| Variable | Default | Description |
|---|---|---|
| `DB_URL` | `jdbc:h2:mem:farmersmarketdb` | JDBC connection URL |
| `DB_USERNAME` | `sa` | Database username |
| `DB_PASSWORD` | ` ` | Database password |

---

## Business rules enforced

- Orders with out-of-stock products are rejected with a clear error message
- Stock is automatically decremented on order creation
- Cancelling an order restores stock to all affected products
- Order status transitions are strictly enforced:
  - `PENDING` → `CONFIRMED` or `CANCELLED`
  - `CONFIRMED` → `COMPLETED` or `CANCELLED`
  - `COMPLETED` → no further changes allowed

---

## Seed data (loaded on startup)

The app auto-seeds two farmers, two customers, and three products so the system is immediately usable without any manual setup.

---

## Project structure

```
src/
├── main/
│   ├── java/com/farmersmarket/preorder_system/
│   │   ├── controller/     # REST endpoints
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access (JPA)
│   │   ├── model/          # JPA entities
│   │   ├── dto/            # Request objects with validation
│   │   └── exception/      # GlobalExceptionHandler, custom exceptions
│   └── resources/
│       ├── application.properties
│       └── static/index.html   # Single-page dashboard
└── test/
```
