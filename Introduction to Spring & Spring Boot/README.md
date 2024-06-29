# 🌱 Spring Boot

# ✨ Introduction to Spring and Spring Boot

- **Why Spring?**
    - Java EE (before 2003) required a lot of time to configure (Server, DBs, Logs). Spring (launched in 2003) provided an **IOC Container** which helped developers configure their projects faster.

- **Why Spring Boot?**
    - Built on top of the Spring framework, it helps in even quicker configuration with good default settings, making project setup extremely quick.

## 🍃 Spring Framework

- **Dependency Injection Framework:** Makes development easy for Java EE applications by making them loosely-coupled.
- **Build Apps from POJOs:** You can build apps from POJOs (Plain Old Java Objects) and apply enterprise services non-invasively to them.

### 🛠️ Important Components

- **Core Container**
- **AOP (Aspect-Oriented Programming)**
- **JDBC**
- **Web**
- **Testing**

All the above components are part of the Spring framework, and the code for the same is already configured for us.

### 🔄 IoC Containers (Inversion of Control)

- **Loose Coupling:** Takes control away from you and makes the systems loosely coupled.
- **Event-Driven Programs:** Your event-driven program, AOP, etc., comes under the umbrella of IoC containers.
- **Dependency Management:** IoC container manages the components of your apps and injects dependencies into them as required.
- **Lifecycle Management:** The IoC creates your beans (Objects), configures them, and manages their whole lifecycle.
    - Example: Creating dev and prod environments in your apps. The IoC manages the required files, configures them to dev or prod environment, injects dependencies, and manages their lifecycle.

## 🚀 Setting Up Your Spring Boot Project Environment

### 📋 Prerequisites

Before we start, ensure you have the following installed on your system:

- **Java Development Kit (JDK)** ☕ - Download from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### 🖥️ Install IntelliJ IDEA (Optional)

IntelliJ IDEA is a powerful IDE that can help you streamline your development process with Spring Boot. You can download it from [here](https://www.jetbrains.com/idea/download/).

### 🌐 Go to start.spring.io

To create a new Spring Boot project, follow these steps:

1. 🌐 Open your web browser and navigate to [start.spring.io](https://start.spring.io/).

2. 📝 Fill out the project metadata:
    - **Project**: Choose either Maven or Gradle (Maven is more common for Spring Boot projects).
    - **Language**: Choose Java.
    - **Spring Boot**: Choose the latest stable version.
    - **Project Metadata**: Fill in details like Group, Artifact, Name, Description, Package Name, and Packaging type (JAR or WAR).

3. 📦 Select the dependencies you need. Common dependencies include:
    - Spring Web 🌐
    - Spring Data JPA 🗄️
    - Spring Security 🔒
    - H2 Database 🗃️ (for an in-memory database)
    - Lombok 📝 (to reduce boilerplate code)

4. ⬇️ Click on the **Generate** button to download a ZIP file containing your new Spring Boot project.

## 🛠️ Setting Up the Project

### 📂 Unzip the Downloaded File

Unzip the downloaded file to a location of your choice. This will create a directory structure similar to the following:

<pre>
your-project-name/
├── .mvn/
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── yourproject/
│   │   │               └── YourProjectApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── yourproject/
│       │               └── YourProjectApplicationTests.java
│       └── resources/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
</pre>


### 📂 Directory and File Overview

- **.mvn/wrapper/**: Contains Maven Wrapper files which allow you to run the project without having Maven installed on your system.
- **src/main/java/**: Contains your main application code.
  - **com/example/yourproject/YourProjectApplication.java**: The main class that starts your Spring Boot application.
- **src/main/resources/**: Contains static resources, templates, and configuration files.
  - **static/**: Place your static files (CSS, JavaScript, images) here.
  - **templates/**: Place your Thymeleaf templates (HTML files) here.
  - **application.properties**: Main configuration file for your Spring Boot application.
- **src/test/**: Contains test cases for your application.
  - **YourProjectApplicationTests.java**: Example test class.
- **mvnw** and **mvnw.cmd**: Scripts to run Maven commands using the Maven Wrapper.
- **pom.xml**: Project Object Model file which contains project configuration for Maven.
- **README.md**: Project README file.

### ✅ You're all set!

You can now open the project in IntelliJ IDEA or any other IDE of your choice, and start building your Spring Boot application. Happy coding! 🎉

---
## 🧩 **Beans**

- So beans are basically **Java objects** that are managed by the **Spring’s IoC containers**. Instead of you having to create the objects in Java apps, you let Spring and its IoC container do that for you. But now they will not be called as objects but as **Beans**.
- Moreover, the IoC will take care to **instantiate, assemble, and manage** the object. Oops, I meant **beans**!
- And these beans are annotated accordingly.
    - e.g., `@Component`, `@Repository`, `@Service`, `@Controller`

### 📝 **How does it look like in a Java app?**

### 🔧 **Bean Creation**

In a typical Java app, you would create new objects by using the `new` keyword.
Like `Car car = new Car();`

Let me tell you, the Spring framework does not like the `new` keyword and neither should you. This is because in real-life scenarios you would get thousands of objects and manually managing them on its own is a very hectic process. These user-created objects would take unnecessary amounts of space in your application.

So instead, you would create something like the below without using the `new` keyword and letting Spring do its own job.

```java
@Autowired
private Car car;
```

when your Car class will look like

```java
@Component
public class Car {…}
```

You see we have two annotations here, `@Autowired` and `@Component`. So here the `@Component` tells that this class should be used by the IoC container and the `@Autowired` tells that this object is supposed to be managed by the IoC.

So when the app fires up, Spring will inject the object into your app (which basically is known as **Dependency Injection**).

Now as there is `@Component`, we can use other annotations that can help the developer to understand the codebase better. For example, there is `@Service` annotation (which itself is derived from `@Component`) and it's in the part of the code where all the business logic is written.

There is one more method to create a Bean. That is by creating a configuration class and annotating with `@Configuration`. Then in this class, you can define methods to configure and annotate beans.

This method, in short, is **Explicit Bean Declaration in the Configuration class**.

### 🔄 **Bean Lifecycle**

1. **Bean creation** (Static factory method or Annotation method)
2. **Dependency Injection**
3. **Bean Initialized** (`@PostConstruct`; invokes initialization after the bean is configured)
4. **Bean is used** (Fully ready to use)
5. **Bean Destroyed**

### 🌐 **Scope of Beans**

- **Singleton Scope:** (Default) Creates a single instance per Spring ApplicationContext, shared by all clients.
- **Prototype Scope:** Generates a new instance each time the bean is requested, providing a unique instance to each client.
- **Request Scope:** Associates a new instance with each HTTP request in web-based applications, ensuring isolation between requests.
- **WebSocket Scope:** Assigns a unique instance to each WebSocket session, managing beans specific to individual WebSocket connections.
