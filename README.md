# 🍔 Online Food Order Processing System

A **Microservices-based Online Food Order Processing System** developed using **Java Spring Boot**, **ReactJS**, **MySQL**, and **Apache ActiveMQ**. The application demonstrates asynchronous communication between independent microservices while processing customer orders from placement to delivery.

This project was developed as a **Java Full Stack Take-Home Assessment** to showcase backend development, REST APIs, event-driven architecture, and frontend integration.

---

# 🚀 Features

- Place food orders through a React frontend
- Order management using Spring Boot
- Payment processing workflow
- Kitchen order preparation
- Delivery assignment workflow
- RESTful APIs
- Event-driven communication using ActiveMQ
- MySQL database integration
- Microservices architecture

---

# 🏗️ System Architecture

```
                  React + Vite Frontend
                           │
                     REST API Calls
                           │
                  ┌─────────────────┐
                  │ Order Service   │
                  └─────────────────┘
                           │
                     ActiveMQ Queue
                           │
                  ┌─────────────────┐
                  │ Payment Service │
                  └─────────────────┘
                           │
                     ActiveMQ Queue
                           │
                  ┌─────────────────┐
                  │ Kitchen Service │
                  └─────────────────┘
                           │
                     ActiveMQ Queue
                           │
                  ┌─────────────────┐
                  │ Delivery Service│
                  └─────────────────┘
                           │
                        MySQL
```

---

# 📂 Project Structure

```
Online-Food-Order-Processing-System
│
├── frontend
│
├── order-service
│
├── payment-service
│
├── kitchen-service
│
├── delivery-service
│
├── database_design.md
│
├── api_lld.md
│
└── README.md
```

---

# 🛠️ Tech Stack

## Frontend

- ReactJS
- Vite
- HTML5
- CSS3
- JavaScript

## Backend

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Maven
- REST API

## Messaging

- Apache ActiveMQ

## Database

- MySQL

## Tools

- Eclipse / STS
- VS Code
- Postman
- Git
- GitHub

---

# ⚙️ Microservices

## 📦 Order Service

Responsibilities

- Create Orders
- Store Orders
- Publish Order Events

---

## 💳 Payment Service

Responsibilities

- Receive Order Messages
- Process Payments
- Publish Payment Status

---

## 👨‍🍳 Kitchen Service

Responsibilities

- Receive Paid Orders
- Prepare Food
- Notify Delivery Service

---

## 🚚 Delivery Service

Responsibilities

- Assign Delivery Partner
- Update Delivery Status
- Complete Delivery

---

# 🔄 Order Workflow

1. Customer places an order.
2. Order Service saves the order.
3. Order Service publishes an event to ActiveMQ.
4. Payment Service processes payment.
5. Kitchen Service prepares the order.
6. Delivery Service dispatches the order.
7. Customer receives the completed order.

---

# 📡 REST APIs

## Order Service

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /orders | Create Order |
| GET | /orders | Get All Orders |
| GET | /orders/{id} | Get Order |
| PUT | /orders/{id} | Update Order |
| DELETE | /orders/{id} | Delete Order |

---

## Payment Service

| Method | Endpoint |
|---------|----------|
| POST | /payments |
| GET | /payments |

---

## Kitchen Service

| Method | Endpoint |
|---------|----------|
| GET | /kitchen/orders |
| PUT | /kitchen/{id}/prepare |

---

## Delivery Service

| Method | Endpoint |
|---------|----------|
| GET | /deliveries |
| PUT | /deliveries/{id}/dispatch |
| PUT | /deliveries/{id}/complete |

---

# 🗄️ Database

The project uses **MySQL** for persistent storage.

Database schema and table relationships are documented in:

- database_design.md

---

# ▶️ Getting Started

## Clone Repository

```bash
git clone https://github.com/Shanthoshg/Online-Food-Order-Processing-System.git
```

---

## Start MySQL

Create the required database.

Update every `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_order_db
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Start ActiveMQ

Ensure Apache ActiveMQ is running before starting the services.

---

## Run Backend Services

For each microservice:

```bash
mvn clean install
mvn spring-boot:run
```

Run:

- order-service
- payment-service
- kitchen-service
- delivery-service

---

## Run Frontend

```bash
cd frontend

npm install

npm run dev
```

---

# 🧪 API Testing

The REST APIs were tested using **Postman**.

---

# 📸 Screenshots

> Screenshots will be added soon.

---

# 🔮 Future Improvements

- JWT Authentication
- Role-Based Access
- API Gateway
- Eureka Service Discovery
- Docker
- Kubernetes
- Kafka
- Redis
- CI/CD Pipeline
- Unit Testing
- Swagger/OpenAPI Documentation

---

# 👨‍💻 Author

**Shanthosh G**

Java Full Stack Developer

GitHub:

https://github.com/Shanthoshg

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.
