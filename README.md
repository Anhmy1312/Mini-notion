# 📚 Mini-Notion: Learning Progress Management & Tracking System

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange.svg?style=for-the-badge&logo=java" alt="Java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg?style=for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue.svg?style=for-the-badge&logo=mysql" alt="MySQL" />
  <img src="https://img.shields.io/badge/JavaScript-ES6+-yellow.svg?style=for-the-badge&logo=javascript" alt="JavaScript" />
  <img src="https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge" alt="License" />
</p>

> 🚀 **Mini-Notion** is a Fullstack Web Application designed to help students organize learning materials, filter by subject, perform real-time searches, and visually track study completion progress.

---

## 🛠️ Tech Stack

| Layer | Technologies / Tools |
| :--- | :--- |
| **Frontend** | HTML5, CSS3 (Variables, Flexbox), JavaScript ES6+ (Fetch API, Async/Await, LocalStorage) |
| **Backend** | Java 17+, Spring Boot, Spring Data JPA, Hibernate |
| **Database** | MySQL 8.0 |
| **Tools** | Maven, Git / GitHub, NetBeans / IntelliJ IDEA, MySQL Workbench |

---

## ✨ Key Features

- 🔄 **Full CRUD Lifecycle:** View, Add, Edit, and Delete documents seamlessly.
- 📊 **Progress Dashboard:** Dynamic percentage progress bar automatically calculated and updated in real time.
- ☑️ **Mark as Learned:** Interactive checkboxes to toggle completion status with a visual strikethrough effect.
- 🔍 **Combined Search & Filter:** Real-time keyword search paired with subject-based sidebar filtering.
- 🔀 **Flexible Sorting:** Sort documents by Name (A-Z, Z-A) or Creation Time (Newest, Oldest).
- 🌙 **Dark / Light Mode:** Persistent theme preference saved via LocalStorage.

---

## 🔌 RESTful API Endpoints

| Method | Endpoint | Description |
| :---: | :--- | :--- |
| `GET` | `/api/documents` | Retrieve all documents |
| `POST` | `/api/documents` | Create a new document |
| `PUT` | `/api/documents/{id}` | Update document details / status |
| `DELETE` | `/api/documents/{id}` | Delete a document |

---

## 🚀 Getting Started

Follow these steps to set up and run the project locally:

### 1. Clone the Repository
```bash
git clone [https://github.com/Anhmy1312/Mini-notion.git](https://github.com/Anhmy1312/Mini-notion.git)
cd Mini-notion
```
### 2. Database Configuration (MySQL)
* Open MySQL Workbench and create a new database:
  ```sql
  CREATE DATABASE mini_notion_db;
  ```
* Open the configuration file at:
  `src/main/resources/application.properties`
* Verify and enter your MySQL connection credentials: (username/password).
   ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mini_notion_db
  spring.datasource.username=root
  spring.datasource.password=YOUR_MYSQL_PASSWORD
  spring.jpa.hibernate.ddl-auto=update
  ```
  
### 3. Run Backend (Spring Boot)
* Open the project in your preferred IDE (NetBeans / IntelliJ IDEA).
* Locate the main entry point `MiniNotionApplication.java` and click Run.
* Ensure the server starts successfully at port `8080` (`http://localhost:8080`).

### 4. Run Frontend
* Open index.html directly in your browser (or launch it using Live Server extension in VS Code) to interact with the application.
