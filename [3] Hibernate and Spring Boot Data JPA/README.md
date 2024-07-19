# 🌱 Hibernate and JPA with Spring Boot

## ✨ Introduction to Hibernate and JPA

- **What is Hibernate?**
  - Hibernate is an **ORM (Object-Relational Mapping)** tool that simplifies the data manipulation between Java applications and relational databases by mapping Java classes to database tables.

- **What is JPA?**
  - **Java Persistence API (JPA)** is a specification that defines how to manage relational data in Java applications. Hibernate is an implementation of JPA.

## 🍃 Benefits of Using Hibernate and JPA

- **Database Independence:** Hibernate allows for easy switching between databases without major code changes.
- **Automatic Table Creation:** It can generate database tables based on entity classes.
- **Caching:** Provides built-in caching mechanisms to improve performance.
- **Lazy Loading:** Loads data on demand, improving performance by fetching only required data.

## 🛠️ Setting Up Hibernate with Spring Boot

### 📋 Prerequisites

Ensure you have the following before setting up Hibernate in your Spring Boot project:

- **Spring Boot Starter Data JPA** dependency
- **Database Driver** dependency (e.g., H2, MySQL, PostgreSQL)

### 🌐 Adding Dependencies

Add the necessary dependencies in your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## 🗂️ Configuring Data Source

Configure the data source in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## 📦 Defining JPA Entities

### 🌟 Entity Annotations

Use annotations to map Java classes to database tables:

- **`@Entity`**: Specifies that the class is an entity.
- **`@Id`**: Denotes the primary key.
- **`@GeneratedValue`**: Specifies how the primary key should be generated.
- **`@Column`**: Maps the field to a column in the database.

### 📝 Example Entity

```java
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    // Getters and setters
}
```

## 🔄 Repository Layer

### 🗃️ Creating Repositories

Use Spring Data JPA repositories to perform database operations without writing boilerplate code:

- **`@Repository`**: Indicates that the class is a repository.
- **`CrudRepository`**: Provides CRUD operations.
- **`JpaRepository`**: Provides JPA-related methods.

### 📝 Example Repository

```java
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // Custom query methods (if any)
}
```

## 🔧 Using the Repository

### 🌐 Autowiring Repository

Use the repository in your service or controller:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
```

## 🔍 Querying Data

### 🌟 Query Methods

Spring Data JPA allows you to define query methods directly in the repository interface:

```java
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findByEmail(String email);
}
```

## 🛠️ Configuring Additional Hibernate Settings

### 📋 Hibernate Properties

Additional Hibernate properties can be configured in `application.properties`:

```properties
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

- **`show-sql`**: Shows generated SQL statements in the console.
- **`ddl-auto`**: Specifies how the schema should be managed (create, update, validate, none).

### 🌐 H2 Console

Enable H2 console for in-memory database testing:

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Access the H2 console at `http://localhost:8080/h2-console`.
