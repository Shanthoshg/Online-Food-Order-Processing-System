# API Low-Level Design (LLD) - Online Food Order Processing System

This document outlines the REST APIs and Queue Messaging contracts designed for the Food Order Processing System.

---

## 1. REST Endpoints (Order Service - Port 8081)

### A. Create Order
Creates a new order, saves it to the database with status `PLACED`, and publishes an event to the ActiveMQ `order.created` queue.

* **Endpoint:** `POST /api/orders`
* **Content-Type:** `application/json`
* **Request Payload:**
```json
{
  "customerName": "John Doe",
  "item": "Gourmet Pizza",
  "amount": 15.99
}
```
* **Response Status:** `201 Created`
* **Response Payload:**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "item": "Gourmet Pizza",
  "amount": 15.99,
  "status": "PLACED",
  "createdAt": "2026-07-04T10:30:00"
}
```

---

### B. Get All Orders
Retrieves a list of all orders to populate the frontend real-time dashboard.

* **Endpoint:** `GET /api/orders`
* **Response Status:** `200 OK`
* **Response Payload:**
```json
[
  {
    "id": 2,
    "customerName": "Alice",
    "item": "Smash Burger",
    "amount": 9.99,
    "status": "DELIVERED",
    "createdAt": "2026-07-04T10:28:00"
  },
  {
    "id": 1,
    "customerName": "John Doe",
    "item": "Gourmet Pizza",
    "amount": 15.99,
    "status": "PLACED",
    "createdAt": "2026-07-04T10:30:00"
  }
]
```

---

### C. Get Order Details
Retrieves status and details for a single order.

* **Endpoint:** `GET /api/orders/{id}`
* **Response Status:** `200 OK` (or `404 Not Found`)
* **Response Payload:**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "item": "Gourmet Pizza",
  "amount": 15.99,
  "status": "PLACED",
  "createdAt": "2026-07-04T10:30:00"
}
```

---

## 2. ActiveMQ Queue Message Formats

### A. Queue: `order.created`
Used to trigger the asynchronous workflow instantiation.

* **Publisher:** Order Service
* **Consumer:** Order Service (ActiveMQ Consumer Component)
* **Message Type:** Simple text or Long (Serialized Object)
* **Message Content:**
```text
1
```
*(Represents the raw `orderId` value)*

---

## 3. Camunda External Task Topics

### A. Topic: `payment-processing`
* **Worker:** Payment Service
* **Input Variables:** `orderId` (Long)
* **Output Variables:** `paymentSuccess` (Boolean)

### B. Topic: `kitchen-preparation`
* **Worker:** Kitchen Service
* **Input Variables:** `orderId` (Long)
* **Output Variables:** None

### C. Topic: `delivery-service`
* **Worker:** Delivery Service
* **Input Variables:** `orderId` (Long)
* **Output Variables:** None

---

## 4. Error Handling & Edge Cases

1. **ActiveMQ Connection Failures:** JMS listener retry mechanisms will attempt reconnection if the ActiveMQ broker goes down temporarily.
2. **Payment Failure Flow:** If the payment processor mocks a failure (triggered when the customer's name contains `"fail"` or `"cancel"`), the process sets `paymentSuccess = false`. Camunda uses an exclusive gateway to route the flow to the order status update task to mark it as `CANCELLED` and terminates gracefully.
3. **Database Constraints:** All database operations use Spring Data JPA transactions (`@Transactional`), ensuring atomic commits and rollback in case of runtime exceptions.
