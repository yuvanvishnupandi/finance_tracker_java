# 💰 Finance Tracker  
### Full-Stack Personal Finance Application (JavaFX & Spring Boot)

This project is a **robust full-stack desktop application** designed to help users track their income and expenses, providing clear visualization of financial activity.  
It demonstrates best practices using a **layered architecture** for maintainability and clean separation of concerns.

---


---

## 🛠️ Technology Stack

| Layer | Technology | Key Components |
|-------|-------------|----------------|
| **Frontend (Client)** | JavaFX | MVC Architecture, Custom CSS, `javafx-controls`, Gson (for API data handling) |
| **Backend (Server)** | Spring Boot 3.3.x | RESTful APIs (`@RestController`), Service Layer, Spring Data JPA |
| **Database** | MariaDB / MySQL | Persistent storage for user accounts, categories, and transactions |
| **Build Tool** | Maven | Dependency management and project lifecycle |
| **JDK Version** | Java 23 | Modern Java runtime and language features |

---

## 🚀 Getting Started

To run this project locally, make sure you have **Java 23** and a **MariaDB/MySQL** server running.

---

### 1️⃣ Database Setup

Make sure your database service is running.  
Then, create a database and user with the following commands:

```sql
CREATE DATABASE IF NOT EXISTS expense_tracker_db;
CREATE USER IF NOT EXISTS 'expenseuser'@'localhost' IDENTIFIED BY 'yuvan';
GRANT ALL PRIVILEGES ON expense_tracker_db.* TO 'expenseuser'@'localhost';
FLUSH PRIVILEGES;
2️⃣ Configure Spring Boot Server
Navigate to the server directory and edit the file:
src/main/resources/application.properties

properties
Copy code
spring.datasource.url=jdbc:mariadb://localhost:3306/expense_tracker_db
spring.datasource.username=expenseuser
spring.datasource.password=yuvan
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
3️⃣ Run the Backend Server (Terminal 1)
bash
Copy code
cd expense-tracker-springboot-server
./mvnw clean spring-boot:run
Wait for the message:
Started ExpenseTrackerApplication in X.XXX seconds

4️⃣ Run the JavaFX Client (Terminal 2)
bash
Copy code
cd ../expense-tracker-client
mvn exec:java
The Finance Tracker desktop window should appear.

🔑 Database Connectivity Demonstration
This project showcases end-to-end communication between JavaFX (client), Spring Boot (server), and MariaDB (database).

Data Write Path (Sign Up):
User details entered in the GUI are sent via REST API → handled by Spring Boot Service Layer → inserted into MariaDB.

Data Read Path (Login):
Credentials entered in the GUI → verified via API → retrieved using SQL SELECT query via Spring Data JPA.

📊 Example Demonstration Flow
Open the Finance Tracker app.

Create a new account via Sign Up → verifies data insertion.

Log in using the same account → verifies data retrieval.

Add income and expense records.

View the updated balance and charts in real-time.

🧩 Export to Sheets
The project can be extended with an "Export to Google Sheets" or CSV Export feature for data backup and visualization.

🧠 Author
Yuvan Vishnu
Full-Stack Java Developer & Cloud Computing Student

📫 Contact: GitHub

🪪 License
This project is open-source and available under the MIT License.
