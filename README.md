# 🚀 DesignLog

> A scalable newsletter platform to share system design (HLD/LLD), backend engineering insights, and real interview experiences.

---

## 🧠 Overview

**DesignLog** is a developer-focused newsletter application built to publish and distribute technical content such as:

* System Design (HLD & LLD)
* Backend architecture patterns
* Real-world interview experiences
* Engineering learnings

The goal is to build a **production-ready, scalable backend system** while keeping the architecture clean and extensible.

---

## ✨ Features (MVP)

* 📝 Create and publish blog posts
* 👤 User management (basic for now)
* 📖 View all blogs / individual blog
* 📬 Subscription system (upcoming)
* 📧 Newsletter email delivery (upcoming)

---

## 🏗️ Tech Stack

### Backend

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Lombok

### Planned Integrations

* Spring Security (JWT + OAuth2)
* Kafka (event-driven notifications)
* Redis (caching)
* Email Service (AWS SES / SendGrid)

---

## 📂 Project Structure

```text
com.newsletter
├── controller
├── service
│   └── impl
├── repository
├── entity
├── dto
├── config
├── exception
└── security (upcoming)
```

---

## ⚙️ Getting Started

### Prerequisites

* Java 17+
* PostgreSQL
* Maven

---

### 1️⃣ Clone the repository

```bash
git clone https://github.com/your-username/designlog.git
cd designlog
```

---

### 2️⃣ Configure Database

Create database:

```sql
CREATE DATABASE newsletter_db;
```

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/newsletter_db
    username: your_username
    password: your_password
```

---

### 3️⃣ Run the Application

```bash
mvn spring-boot:run
```

---

### 4️⃣ Test APIs

Use tools like Postman to test endpoints:

* `POST /api/blogs`
* `GET /api/blogs`
* `GET /api/blogs/{id}`

---

## 🧩 Roadmap

### Phase 1 (Current)

* Blog CRUD APIs
* Basic user handling

### Phase 2

* Subscription system
* Email notifications

### Phase 3

* Authentication (JWT + OAuth2)
* Role-based access control

### Phase 4

* Event-driven architecture (Kafka)
* Analytics (open rate, engagement)

### Phase 5

* Microservices architecture (optional)

---

## 🧠 System Design Vision

DesignLog is being built with scalability in mind:

* Modular layered architecture
* Event-driven notification system (future)
* Decoupled services for extensibility
* Cloud-ready deployment

---

## 🚀 Deployment (Planned)

* Backend → Docker + AWS / Render
* Database → PostgreSQL (RDS)
* Frontend → React + Vercel / S3

---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## 📌 Author

**Juyel Hushen**
Backend Engineer | Java | Spring Boot | System Design

---

## ⭐ Final Note

This project is not just a newsletter app —
it’s a **learning playground for scalable backend systems and real-world architecture design**.
