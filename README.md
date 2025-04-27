# 🏥 AI Clinic Assistant

**AI Clinic Assistant** is an intelligent healthcare system powered by Java Spring Boot and LangChain4j that enables:

- 👨‍⚕️ **Doctor Assistant** – Helps doctors manage appointments and visit notes via natural conversation.
- 🧑‍⚕️ **Patient Assistant** – Helps patients find suitable doctors and book appointments based on symptoms or doctor availability.

---

## ✨ Features

### 👨‍⚕️ Doctor Assistant
- Authenticate with your name
- View upcoming and past appointments
- Add visit notes with summary, medication, and follow-up date
- Cancel appointments with confirmation
- Built-in conversation flow powered by LangChain4j

### 🧑‍⚕️ Patient Assistant
- Search doctors by name or symptoms
- Check doctor availability
- Book appointments via conversational interface

---

## 🛠 Tech Stack

- Java 17 + Spring Boot
- LangChain4j (AI agent framework)
- PostgreSQL
- Docker + Docker Compose
- pgAdmin (GUI for DB)
- Maven

---

## 🚀 Getting Started

### 📦 Step 0: Clone the Repository

```bash
git clone https://github.com/Gauravsalunke5/aiclinicassistant.git
cd aiclinicassistant

```

## 🐳 Step 1: Run PostgreSQL & pgAdmin via Docker

✅ Make sure Docker is installed and running.

### Files involved:
- `compose.yml`
- `schema.sql`
- `insert.sql`

To run PostgreSQL and pgAdmin with Docker, use the following command:

```bash
docker-compose up -d
```
Once DB is setup execute the script `schema.sql` and `insert.sql` to create tables and insert data.

### 💻 Step 2: Build & Run the Application
Build the Spring Boot application using Maven:

```bash
mvn clean install
```
Start the application:

```bash
mvn spring-boot:run
```

### 📡 Step 3: Access the Application
#### Doctor Assistant: http://localhost:8080/doctor-chat.html
#### Patient Assistant: http://localhost:8080/patient-chat.html

### Demo viedo:


https://github.com/user-attachments/assets/e9a6cc44-686f-4c58-b249-8986e2bc55b0

