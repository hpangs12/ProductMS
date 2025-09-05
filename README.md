# 🛒 ProductMS

**ProductMS** is a microservice responsible for managing products in an e-commerce system. It supports CRUD operations, search, filtering, stock checks, caching, and event-driven communication with other services like `ReviewMS`.  

---

## ✨ Features

- **CRUD Operations**: Add, update, delete, and fetch products.  
- **Advanced Search & Filtering**: Search by name, category, price range; filter in-stock products.  
- **Pagination & Sorting**: Supports pageable and sortable product listings.  
- **Stock Management**: Lightweight endpoint to check stock availability.  
- **Caching**: In-memory caching with Caffeine (per-cache TTLs for performance).  
- **DTO-based Responses**: Returns `ProductDTO` to ensure API stability.  
- **Event-Driven Communication**: Publishes Kafka events like product deletion so other services (e.g., `ReviewMS`) can react automatically.  

---

## 🏗 Architecture

- **Framework**: Spring Boot + Spring Data JPA  
- **Database**: MySQL / PostgreSQL  
- **Caching**: Caffeine (optional Redis for distributed caching)  
- **Messaging**: Kafka (for event-driven communication)  
- **DTO Layer**: Product → ProductDTO  
- **Optional Real-Time Updates**: WebSocket / STOMP  (**unimplemented**)

---

## 🔧 API Endpoints

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/products/{id}` | Get product by ID |
| GET    | `/products` | Get all products (paged, filtered) |
| GET    | `/search` | Search (Product Name, Desc) |
| GET    | `/filter` | Filter (Name, Desc, Category, Price) |
| POST   | `/products/bulk` | Add a list of products |
| POST   | `/products` | Add a new product |
| PUT    | `/products/{id}` | Update an existing product |
| PATCH  | `/products/{id}` | Updating some fields (eg. updating qty) |
| DELETE | `/products/{id}` | Delete a product (soft delete recommended) |
| DELETE | `/products/bulk` | Delete a list of products |
| GET    | `/products/{id}/stock` | Check if product is in stock |

### Search & Filtering
/products?name={key}&category={category}&page={page}&size={size}&sort={field,asc|desc}


---

## ⚡ Caching Strategy

| Cache Name      | TTL        | Notes                                      |
|-----------------|------------|--------------------------------------------|
| `products`      | 10 min     | Product details by ID                       |
| `instock`       | 30 sec     | Stock availability, frequently updated      |

---

## 🛠 Event-Driven Kafka Integration

- **Topic**: `product_delete_event`  

### Flow:

1. ProductMS deletes a product (soft or hard delete).

2. ProductMS publishes a message with the product ID to Kafka.

3. ReviewMS consumes the event and deletes or marks reviews associated with the product.

### Example Producer:

```java
kafkaTemplate.send("product_delete_event", "Product ID", productId);
```

## 🛠 Getting Started

### Prerequisites

* Java 17+

* Spring Boot 3.x

* MySQL

* Kafka

* Maven

* Optional: Redis for distributed caching

### Clone the repository

```java
git clone https://github.com/your-org/ProductMS.git
cd ProductMS
```

### Configure application.yml

```YAML
spring:
  application:
    name: ProductMS
  datasource:
    url: jdbc:mysql://localhost:3306/productdb
    username: <<your username>>
    password: <<your pass>>
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
    show-sql: true
  cache:
    type: caffeine
    
springdoc:
  show-actuator: true
    
server:
  port: 8082

kafka:
  bootstrap-servers: localhost:9092
```

### Run the service

```bash
./mvnw spring-boot:run
```

## 🧩 Integration with Other Services

- OrderMS: Validates stock and fetches user info at checkout.
- ReviewMS: Automatically deletes reviews when a product is deleted via Kafka.
- Frontend: Can subscribe to product events for real-time updates.

## 🛡 Security

* JWT authentication via separate AuthMS.
* Secured endpoints: CRUD and stock-check require a valid token.

## 🚀 Future Plans

- Distributed Caching / Redis Integration

  - Move from in-memory Caffeine to Redis for multi-instance deployments.

- Real-Time Updates

  - Publish product creation, updates, and stock changes via WebSocket or Kafka for frontend updates.

- Advanced Filtering & Recommendation Engine

  - Implement recommendation endpoints (related products, bestsellers, trending items).

- Soft Delete Enhancements

  - Implement full soft-delete workflow with history tracking and admin restore capability.

- API Versioning

  - Add versioning for REST APIs to maintain backward compatibility as DTOs evolve.

- Rate Limiting & Throttling

  - Protect product endpoints from abuse with rate limiting (via Spring Cloud Gateway or API Gateway).

- Analytics / Metrics

  - Track product views, popular searches, and stock trends for reporting and dashboard integration.
