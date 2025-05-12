# Spring Boot Market – Online Store

A Java-based educational project representing a simplified online store. Implements basic architecture with authentication, product and category management.

## 🚀 Technologies

- **Java 11**
- **Spring Boot**
- **Hibernate (JPA)**
- **Thymeleaf**
- **H2 Database (embedded)**
- **Maven**

## 📦 How to Run

1. Make sure you have JDK 11 and Maven installed.
2. Clone the repository:
   ```bash
   git clone https://github.com/YOUR-USERNAME/springboot-demo-app.git
   cd springboot-demo-app
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will be available at: [http://localhost:8080](http://localhost:8080)

No database installation is required — H2 is used in-memory.

## 📁 Project Structure

- `config` — Security configuration (Spring Security)
- `controller` — REST and Web controllers (`AdminController`, `AuthController`, `ProductController`)
- `converter` — Entity-to-DTO mappers
- `dto` — Data Transfer Object classes
- `model` — JPA entities (`Product`, `User`, `Category`, `Authority`)
- `repository` — Data access interfaces
- `service` — Business logic layer
- `resources/templates` — HTML templates (Thymeleaf)
- `resources/db` — Database initialization scripts (if any)

## 🔒 Security

Basic authentication and authorization are configured using Spring Security.

## 📸 UI and Examples

The user interface is under development. Example endpoints:
- `/products` — list of products
- `/admin` — admin panel

(More to be added in future versions)

## 👤 Author

Nikita Sipeikin  
[GitHub](https://github.com/NikitaSipeykin)  
Email: niksipeikin@gmail.com

## 📄 License

This project is created for educational purposes and does not include a license.