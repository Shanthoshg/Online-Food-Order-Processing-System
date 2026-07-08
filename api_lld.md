# 🍕 FoodFlow Orchestrator - Online Food Order Processing System

> A Microservices-Based Food Order Processing System built using **Spring Boot**, **React**, **Camunda BPM**, **MySQL**, and **ActiveMQ** to automate and orchestrate the complete food ordering workflow.

---

## 📌 Project Overview

FoodFlow Orchestrator is a distributed food ordering application that demonstrates how modern microservices communicate to process customer orders.

The system simulates the complete lifecycle of a food order—from order placement to payment processing, kitchen preparation, and delivery—using asynchronous messaging and workflow orchestration.

This project was developed as a **Take-Home Assessment** to showcase backend development, workflow automation, microservices architecture, and frontend integration.

---

# 🚀 Features

- ✅ Microservices Architecture
- ✅ RESTful APIs
- ✅ React Dashboard
- ✅ Real-time Order Tracking
- ✅ Camunda BPM Workflow
- ✅ ActiveMQ Message Queue
- ✅ MySQL Database
- ✅ Order Management
- ✅ Payment Processing
- ✅ Kitchen Processing
- ✅ Delivery Management
- ✅ Spring Data JPA
- ✅ Maven Build System

---

# 🏗️ System Architecture

```
                 React Frontend
                       │
                REST API Calls
                       │
               Order Service
                       │
             Camunda Workflow
                       │
      ┌──────────┬──────────────┐
      │          │              │
 Payment Service Kitchen Service Delivery Service
      │          │              │
      └──────────┴──────────────┘
              ActiveMQ Queue
                     │
                 MySQL Database
```

---

# 📂 Repository Structure

```
Online-Food-Order-Processing-System
│
├── frontend/
│
├── order-service/
│
├── payment-service/
│
├── kitchen-service/
│
├── delivery-service/
│
├── api_lld.md
│
├── database_design.md
│
└── README.md
```

---

# 🛠️ Technologies Used

## Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- REST APIs

## Workflow

- Camunda BPM
- BPMN Process

## Messaging

- ActiveMQ

## Frontend

- React
- Vite
- CSS

## Database

- MySQL

## Tools

- Antigravity IDE
- Git
- GitHub
- MySQL Workbench
- Postman

---

# ⚙️ Microservices

## 🛒 Order Service

Responsible for:

- Creating Orders
- Managing Customer Requests
- Starting Workflow
- Publishing Events

---

## 💳 Payment Service

Responsible for:

- Payment Processing
- Payment Status
- Payment Validation

---

## 👨‍🍳 Kitchen Service

Responsible for:

- Preparing Orders
- Kitchen Queue
- Order Status

---

## 🚚 Delivery Service

Responsible for:

- Delivery Assignment
- Delivery Tracking
- Delivery Status Updates

---

# 🔄 Workflow

Customer Places Order

↓

Order Service

↓

Camunda BPM starts Process

↓

Payment Service

↓

Kitchen Service

↓

Delivery Service

↓

Order Delivered

---

# 📸 Project Screenshots

## 🏠 Dashboard

The React dashboard provides a clean interface for placing new orders and monitoring the complete processing pipeline in real time.

![Dashboard](screenshotshome.png)

---

## 🗄️ Database

MySQL database showing successfully processed Orders, Kitchen Tickets, and Deliveries.

![Database](screenshotsdatabase.png)

---

## ⚙️ Camunda Workflow Engine

Camunda BPM integrated with Spring Boot orchestrating the order workflow.

![Camunda](screenshotscamunda.png)

---

## 💻 Project Structure

Project opened in Antigravity IDE showing all microservices and frontend modules.

![Project Structure](screenshotsproject-structure.png)

---

## ▶️ Running Frontend

Frontend running successfully using the Vite Development Server.

![Frontend](screenshotsrun.png)

---

# 🗄️ Database

Database Name

```
food_order_db
```

Tables

- orders
- deliveries
- kitchen_tickets

---

# 📡 REST API

## Order APIs

### Create Order

```
POST /orders
```

### Get Orders

```
GET /orders
```

---

## Payment APIs

```
POST /payments
```

```
GET /payments
```

---

## Kitchen APIs

```
POST /kitchen
```

```
GET /kitchen
```

---

## Delivery APIs

```
POST /deliveries
```

```
GET /deliveries
```

---

# ▶️ Running the Project

## Clone Repository

```bash
git clone https://github.com/Shanthoshg/Online-Food-Order-Processing-System.git
```

---

## Backend

Run each Spring Boot service individually.

```
order-service

payment-service

kitchen-service

delivery-service
```

---

## Frontend

```bash
cd frontend

npm install

npm run dev
```

Open

```
http://localhost:5173
```

---

## Database

1. Create MySQL Database

```
food_order_db
```

2. Update

```
application.properties
```

with your MySQL username and password.

---

## ActiveMQ

Start ActiveMQ before running the services.

Default URL

```
tcp://localhost:61616
```

---

## Camunda

Run the Order Service to start the Camunda engine.

Access Camunda (if enabled):

```
http://localhost:8080/camunda
```

---

# 📚 Documentation

Additional documentation is included in:

- api_lld.md
- database_design.md

---

# 🎯 Future Enhancements

- JWT Authentication
- User Roles
- Email Notifications
- Docker Support
- Kubernetes Deployment
- CI/CD Pipeline
- Swagger/OpenAPI Documentation
- Payment Gateway Integration

---

# 👨‍💻 Author

**Shanthosh G**

Full Stack Java Developer

**GitHub**

https://github.com/Shanthoshg

---

# ⭐ Repository

If you found this project useful, please consider giving it a ⭐ on GitHub.

---
