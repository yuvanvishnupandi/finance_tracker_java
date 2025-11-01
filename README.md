

# Finance Tracker: A Full-Stack Personal Finance Manager

A **robust, full-stack desktop application** designed to help users track, manage, and visualize their income and expenses using a modern Java stack. The project now features a professional, theme-aware UI, advanced reporting, and utility modules.

It demonstrates best practices using a **layered architecture** for maintainability and clean separation of concerns.

-----

## 🎯 Primary Use Cases & Core Functionality

This Finance Tracker is built to give users complete control over their money with these key capabilities:

  * **Transaction Management:** Seamlessly **add, edit, and delete** income and expense records.
  * **Categorization:** Organize every transaction by **categorizing** it (e.g., Groceries, Rent, Salary).
  * **Budgeting & Goals:** **Set budgets** for each expense category and track progress toward **savings goals** (monthly/quarterly/yearly).
  * **Data Export:** **Export** financial data to your computer as **CSV** or generate detailed **PDF reports** for data backup and external analysis.
  * **User Management:** Secure **user login and sign-up** functionality.
  * **Real-Time Dashboard:** View your balance, recent transactions, and financial charts in real-time.
  * **World Currency Converter:** Use a dedicated utility to convert amounts between major world currencies.

-----

## ✨ Key Features & Enhancements

In addition to core tracking, the application provides a modern user experience:

  * **Seamless Theme Switching:** Toggle instantly between **Dark Mode** and **Light Mode (White UI)** using the switch in the top bar. All UI components are theme-aware.
  * **Professional Dashboard:** Modern, card-based layout providing immediate summaries of **Current Balance, Total Income, Total Expense**, and **Savings Goal** progress with dynamic color coding.
  * **Comprehensive Reporting:** Generate a detailed **PDF report** for any specified date range.
  * **UI Modernization:** Significant **CSS cleanup and restructure** to ensure a clean, modern aesthetic across all components.

-----

## 🛠️ Technology Stack

| Layer | Technology | Key Components |
| :--- | :--- | :--- |
| **Frontend (Client)** | **JavaFX** | MVC Architecture, **ThemeManager** utility, Custom CSS (theme-split), Gson |
| **Backend (Server)** | **Spring Boot 3.3.x** | RESTful APIs (`@RestController`), Service Layer, **Spring Data JPA** |
| **Database** | **MariaDB / MySQL** | Persistent storage for user accounts, categories, and transactions |
| **Utilities** | **Java HttpClient** | World Currency conversion API access |
| **Build Tool** | **Maven** | Dependency management and project lifecycle |
| **JDK Version** | **Java 23** | Modern Java runtime and language features |

-----

## 🚀 Getting Started

To run this project locally, make sure you have **Java 23** and a **MariaDB/MySQL** server running.

### 1️⃣ Database Setup

Make sure your database service is running. Then, create a database and user with the following commands:

```sql
CREATE DATABASE IF NOT EXISTS expense_tracker_db;
CREATE USER IF NOT EXISTS 'expenseuser'@'localhost' IDENTIFIED BY 'yuvan';
GRANT ALL PRIVILEGES ON expense_tracker_db.* TO 'expenseuser'@'localhost';
FLUSH PRIVILEGES;
```

### 2️⃣ Configure Spring Boot Server

Navigate to the server directory and edit the file: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/expense_tracker_db
spring.datasource.username=expenseuser
spring.datasource.password=yuvan
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

### 3️⃣ Run the Backend Server (Terminal 1)

Execute this command from the server directory:

```bash
cd expense-tracker-springboot-server
./mvnw clean spring-boot:run
```

> **Wait for the message:** `Started ExpenseTrackerApplication in X.XXX seconds`

### 4️⃣ Run the JavaFX Client (Terminal 2)

Execute this command from the client directory:

```bash
cd ../expense-tracker-client
mvn exec:java
```

The **Finance Tracker desktop window** should appear.

-----

## 🔑 Demonstration & Data Flow

### UI/UX Flow

The application starts in **Light Mode** by default. Use the "☀ Light" / "🌙 Dark" toggle in the top-right corner to switch themes instantly.

### Data Connectivity Demonstration

This project showcases end-to-end communication between **JavaFX (client), Spring Boot (server),** and **MariaDB (database)**.

  * **Data Write Path (Sign Up):**
    User details entered in the GUI are sent via **REST API** $\rightarrow$ handled by **Spring Boot Service Layer** $\rightarrow$ inserted into **MariaDB**.

  * **Data Read Path (Login):**
    Credentials entered in the GUI $\rightarrow$ verified via **API** $\rightarrow$ retrieved using **SQL SELECT query** via **Spring Data JPA**.

-----

## 🧠 Author

**Yuvan Vishnu Pandi**
  * **Contact:** [GitHub](https://github.com/yuvanvishnupandi)

## 🪪 License

This project is open-source and available under the **MIT License**.
