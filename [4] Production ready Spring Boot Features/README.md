# Production-Ready Spring Boot Features

## ✨ Contents

1. [Spring Boot Devtools](#spring-boot-devtools)
2. [Spring Boot Auditing](#spring-boot-auditing)
3. [Spring Boot Rest Client](#spring-boot-rest-client)
4. [Spring Boot Logging](#spring-boot-logging)
5. [Spring Boot Actuator](#spring-boot-actuator)
6. [Spring Boot Open API and Swagger](#spring-boot-open-api-and-swagger)

---

## 🌟 Spring Boot Devtools

### 🔧 Introduction
Spring Boot Devtools provides a suite of tools that can enhance the development experience by enabling quick restarts, LiveReload, and more. These tools are intended to improve the developer's productivity by reducing the need for manual steps during the development process.

### 🚀 Features
- **Automatic Restart**: Automatically restarts the application whenever files in the classpath change.
- **LiveReload**: Automatically refreshes the browser whenever resources change.
- **Development-only Configurations**: Development-friendly properties are only enabled in development mode.

### 🛠️ Usage
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

### ⚙️ Example Configuration
To enable LiveReload, add the following to your `application.properties`:
```properties
spring.devtools.livereload.enabled=true
```

### 📝 Detailed Explanation
- **Automatic Restart**: When a classpath file changes, Spring Boot Devtools automatically triggers a restart. This feature is highly useful as it eliminates the need to manually restart the server after changes.
  
  Example:
  ```java
  @RestController
  public class DemoController {
      
      @GetMapping("/hello")
      public String hello() {
          return "Hello, World!";
      }
  }
  ```
  Change the return string, save, and the application restarts automatically.

- **LiveReload**: With LiveReload enabled, any changes to HTML, CSS, or JavaScript files automatically refresh the browser.

  Example:
  ```html
  <html>
  <body>
      <h1>Welcome to Spring Boot!</h1>
  </body>
  </html>
  ```
  Change the heading text, save, and see the browser refresh instantly.

### 🌟 Additional Features
- **Global Settings**: Some settings can be applied globally and others locally. For instance, `spring.devtools.restart.enabled=false` can be used to disable restart.

---

## 🌟 Spring Boot Auditing

### 🔧 Introduction
Spring Boot Auditing helps in tracking and logging changes made to data. It automatically captures who made the changes and when, which is essential for maintaining a reliable audit trail.

### 🚀 Features
- **Automatic Population of Audit Fields**: Automatically fills in audit-related fields such as createdBy, createdDate, lastModifiedBy, and lastModifiedDate.
- **Annotations**: Uses annotations like `@CreatedBy`, `@CreatedDate`, `@LastModifiedBy`, and `@LastModifiedDate` to mark fields that need to be audited.

### 🛠️ Usage
Add the following dependencies to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### ⚙️ Example Configuration
Annotate your entity with `@EntityListeners` and `@CreatedBy`/`@LastModifiedBy`:
```java
package com.springvoyage.prod.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Audited
public class AuditableEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
}
```

Enable auditing in your configuration class:
```java
package com.springvoyage.prod.configs;

import com.springvoyage.prod.auth.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    @Bean
    AuditorAware<String> getAuditorAwareImpl(){
        return new AuditorAwareImpl();
    }
}
```

Create an implementation for `AuditorAware`:
```java
package com.springvoyage.prod.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        /*
        * Get info regarding the auditor
        * Get authentication
        * Get the principle
        * Get the username
        */
        return Optional.of("Abhinav");
    }
}
```

### 📝 Detailed Explanation
- **@CreatedBy and @LastModifiedBy**: These annotations are used to automatically capture the user who created or last modified the entity.
  
  Example:
  ```java
  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String lastModifiedBy;
  ```

- **@CreatedDate and @LastModifiedDate**: These annotations are used to automatically capture the date and time when the entity was created or last modified.
  
  Example:
  ```java
  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  ```

### 🌟 Additional Features
- **AuditingHandler**: You can customize the auditing handler if you have specific requirements for how auditing is handled.
- **Security Context**: Integrate with Spring Security to automatically capture the currently authenticated user for audit fields.

---

## 🌟 Spring Boot Rest Client

### 🔧 Introduction
Spring Boot provides easy integration with RESTful web services using RestTemplate and WebClient. These tools help in consuming REST APIs efficiently.

### 🚀 Features
- **RestTemplate**: Synchronous client to perform HTTP requests.
- **WebClient**: Asynchronous client for reactive applications.

### 🛠️ Usage
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### ⚙️ Example Configuration

#### Using RestTemplate:
Configure `RestTemplate` bean:
```java
package com.springvoyage.prod.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

Use `RestTemplate` to make a GET request:
```java
package com.springvoyage.prod.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consume")
    public String consume() {
        String response = restTemplate.getForObject("http://example.com/api", String.class);
        return response;
    }
}
```

#### Using WebClient:
Configure `WebClient` bean:
```java
package com.springvoyage.prod.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
```

Use `WebClient` to make a GET request:
```java
package com.springvoyage.prod.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/consume")
    public Mono<String> consume() {
        return webClientBuilder.build()
                .get()
                .uri("http://example.com/api")
                .retrieve()
                .bodyToMono(String.class);
    }
}
```

### 📝 Detailed Explanation
- **RestTemplate**: A synchronous client for making HTTP requests. It is simple to use and suitable for most standard use cases.
  
  Example:
  ```java
  String response = restTemplate.getForObject("http://example.com/api", String.class);
  ```

- **WebClient**: A more modern, asynchronous client that supports reactive programming. It is suitable for high-load applications that require non-blocking operations.
  
  Example:
  ```java
  Mono<String> response = webClientBuilder.build()
      .get()
      .uri("http://example.com/api")
     

 .retrieve()
      .bodyToMono(String.class);
  ```

### 🌟 Additional Features
- **Error Handling**: Both RestTemplate and WebClient provide mechanisms for handling errors in HTTP responses.
- **Customization**: Both clients can be customized extensively, such as setting default headers or configuring timeouts.

---

## 🌟 Spring Boot Logging

### 🔧 Introduction
Logging is essential for debugging and monitoring applications. Spring Boot simplifies logging configuration by providing sensible defaults and easy customization.

### 🚀 Features
- **Default Logback Configuration**: Out-of-the-box logging setup with Logback.
- **Custom Configuration**: Easily customize logging with `application.properties` or XML configuration.

### 🛠️ Usage
To customize logging levels, add the following to your `application.properties`:
```properties
logging.level.org.springframework=DEBUG
logging.level.com.example=TRACE
```

### ⚙️ Example Configuration

#### Custom Logback configuration (logback-spring.xml):
```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

#### Example Logger Usage in Java:
```java
package com.springvoyage.prod.advices;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private LocalDateTime happenedAt;
    private HttpStatus httpStatus;

    public ApiError() {
        this.happenedAt = LocalDateTime.now();
    }

    public ApiError(String message, HttpStatus httpStatus) {
        this();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
```
```java
package com.springvoyage.prod.advices;

import com.springvoyage.prod.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(){
                ApiError apiError = new ApiError(
                        "This post was not found",
                        HttpStatus.NOT_FOUND
                );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
```

### 📝 Detailed Explanation
- **Default Configuration**: Spring Boot provides a default Logback configuration that is suitable for most applications. It includes logging to the console with a standard format.
  
  Example:
  ```properties
  logging.level.root=INFO
  ```

- **Custom Configuration**: Allows you to define your own logging behavior, such as different logging levels for different packages, custom appenders, and more.

  Example:
  ```xml
  <root level="DEBUG">
      <appender-ref ref="FILE" />
  </root>
  ```

### 🌟 Additional Features
- **Log Rotation**: Logback supports log rotation, which is essential for managing log file sizes.
- **External Configuration**: Logging can be configured externally, making it flexible for different environments.

---

## 🌟 Spring Boot Actuator

### 🔧 Introduction
Spring Boot Actuator provides production-ready features to help you monitor and manage your application. It includes a wide range of built-in endpoints for monitoring application health, metrics, and more.

### 🚀 Features
- **Health Checks**: Monitor the health of your application and its dependencies.
- **Metrics**: Gather application metrics for performance monitoring.
- **HTTP Endpoints**: Access various management endpoints to interact with your application.

### 🛠️ Usage
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### ⚙️ Example Configuration

#### Enable and customize Actuator endpoints in `application.properties`:
```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

#### Accessing Actuator Endpoints:
- **Health Endpoint**: `http://localhost:8080/actuator/health`
- **Info Endpoint**: `http://localhost:8080/actuator/info`

### 📝 Detailed Explanation
- **Health Checks**: The health endpoint provides information about the application's health status. It can be customized to include details from various components like databases, message queues, etc.
  
  Example:
  ```properties
  management.endpoint.health.show-details=always
  ```

- **Metrics**: The metrics endpoint provides various metrics related to the application, such as memory usage, CPU usage, HTTP request counts, etc.
  
  Example:
  ```properties
  management.metrics.export.prometheus.enabled=true
  ```

### 🌟 Additional Features
- **Custom Endpoints**: You can create custom Actuator endpoints to expose specific application information or behavior.
- **Security**: Actuator endpoints can be secured to restrict access to sensitive information.

---

## 🌟 Spring Boot Open API and Swagger

### 🔧 Introduction
Open API and Swagger help in documenting and testing REST APIs with ease. They provide a user-friendly interface to explore and test the APIs.

### 🚀 Features
- **Auto-Generate Documentation**: Automatically generate API documentation from your code.
- **Interactive API Console**: Test APIs directly from the documentation interface.

### 🛠️ Usage
Add the following dependencies to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.5.9</version>
</dependency>
```

### ⚙️ Example Configuration

#### Annotate your REST controller with Swagger annotations:
```java
package com.springvoyage.prod.controllers;

import com.springvoyage.prod.dto.PostDTO;
import com.springvoyage.prod.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @GetMapping(path = "/{postId}")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PutMapping(path = "/{postId}")
        public PostDTO updatePostById(@PathVariable Long postId, @RequestBody PostDTO postDTO){
        return postService.updatePostById(postId, postDTO);

    }

}
```

#### Accessing Swagger UI:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

### 📝 Detailed Explanation
- **Auto-Generate Documentation**: Swagger automatically generates documentation based on the annotations in your code. This eliminates the need for manual documentation.
  
  Example:
  ```java
  @Operation(summary = "Get user details", description = "Retrieve user details by user ID")
  @GetMapping("/users/{id}")
  public User getUser(@PathVariable Long id) {
      return userService.getUserById(id);
  }
  ```

- **Interactive API Console**: Swagger UI provides an interface where you can execute API calls and see the results in real-time. This is useful for testing and debugging.

### 🌟 Additional Features
- **Custom Documentation**: You can add custom documentation and descriptions to your APIs to provide more context.
- **API Versioning**: Support for documenting multiple versions of your API.

---