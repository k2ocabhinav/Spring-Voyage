### Important Concepts Related to Spring Web MVC

1. [ResponseEntity in Spring Boot](#responseentity-in-spring-boot)
    - [ResponseEntity Explained](#responseentity-explained)
    - [Example: Get Employee by ID](#example-get-employee-by-id)
2. [Using Optional and Map](#using-optional-and-map)
    - [Optional](#optional)
    - [Example: Transforming Optional with Map](#example-transforming-optional-with-map)
    - [Detailed Steps and Visual Representation](#detailed-steps-and-visual-representation)
3. [Creating ResponseEntity in POST Request](#creating-responseentity-in-post-request)
    - [Example: Create New Employee](#example-create-new-employee)
4. [Service Class Method with Stream](#service-class-method-with-stream)
    - [Example: Convert List of Entities to DTOs](#example-convert-list-of-entities-to-dtos)
    - [Detailed Steps](#detailed-steps)
5. [Patch Method Using Reflection](#patch-method-using-reflection)
    - [ReflectionUtils](#reflectionutils)
    - [Example: Patch Employee by ID](#example-patch-employee-by-id)
    - [Explanation of Reflection Steps](#explanation-of-reflection-steps)
    - [Detailed Steps with Example](#detailed-steps-with-example)

---

### ResponseEntity in Spring Boot

#### ResponseEntity Explained

`ResponseEntity` is a type in Spring MVC that represents an HTTP response, including the status code, headers, and body. It's a flexible way to configure the HTTP response. 

**Purpose:** Represents an HTTP response, including the status code, headers, and body.

#### Example: Get Employee by ID
```java
@GetMapping(path = "/empService/{id}")
public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
    return employeeDTO
            .map(ResponseEntity::ok) // If present, return HTTP 200 with employee data
            .orElse(ResponseEntity.notFound().build()); // If not present, return HTTP 404
}
```

- `ResponseEntity.ok(body)`: Creates a response with status 200 (OK) and includes the body.
- `ResponseEntity.notFound().build()`: Creates a response with status 404 (Not Found) without a body.

---

### Using Optional and Map

#### Optional

`Optional` is a container object used to contain not-null objects. It is used to represent the absence or presence of a value. 

- When fetching an employee by ID, `Optional<EmployeeDTO>` is used to handle the case where an employee might not exist.
- Using `Optional`, you can avoid null checks and handle the absence of a value more gracefully.

**Purpose:** Handle cases where a value might be absent, without explicit null checks.

#### Example: Transforming Optional with Map
```java
Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
return employeeDTO
        .map(ResponseEntity::ok) // If employee exists, return 200 OK with employee data
        .orElse(ResponseEntity.notFound().build()); // If not, return 404 Not Found
```

- **Optional.map**: Transforms the value inside the `Optional` if present.
- **Optional.orElse**: Provides a fallback value if the `Optional` is empty.

1. **Fetching the Employee**: 
   - `Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);`
   - This line calls the service method to fetch the employee by ID. The result is wrapped in an `Optional`, which may or may not contain an `EmployeeDTO` object.

2. **Using `map` to Transform the Value if Present**:
   - `.map(ResponseEntity::ok)` is applied if the `Optional` contains a value. The `map` method takes a function (in this case, `ResponseEntity::ok`), applies it to the value inside the `Optional`, and returns a new `Optional` containing the transformed value.
   - `ResponseEntity::ok` is a method reference that effectively means `employeeDTO -> ResponseEntity.ok(employeeDTO)`. So, if `employeeDTO` is present, it will be transformed into `ResponseEntity.ok(employeeDTO)`.

3. **Handling the Case Where the Value is Not Present**:
   - `.orElse(ResponseEntity.notFound().build())` is applied if the `Optional` is empty. If there is no value present, `orElse` will return `ResponseEntity.notFound().build()`, which creates a `ResponseEntity` with a 404 status code.

#### Detailed Steps and Visual Representation 

- **Box with Toy (Example when Obj is Present):**
  - `employeeDTO` -> `Optional<EmployeeDTO>` -> `map(ResponseEntity::ok)` -> `ResponseEntity.ok(employeeDTO)`

- **Empty Box (Not Present):**
  - `employeeDTO` -> `Optional<EmployeeDTO>` -> `orElse(ResponseEntity.notFound().build())` -> `ResponseEntity.notFound().build()`

---

### Creating ResponseEntity in POST Request

#### Example: Create New Employee
```java
@PostMapping(path = "/empService/create")
public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
    EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
    return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED); // Return 201 Created with the saved employee
}
```

- **new ResponseEntity<>(body, status)**: Explicitly creates a response with the given body and status.
- **HttpStatus.CREATED** (201): Indicates that a new resource has been successfully created.

You could also use the `ResponseEntity.ok()` method for simplicity if you didn't need to specify a different status code:
This achieves the same result but might be more readable for some developers.

```java
return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
```

---

### Service Class Method with Stream

#### Example: Convert List of Entities to DTOs
```java
public List<EmployeeDTO> getAllEmployees() {
    List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
    return employeeEntities.stream()
            .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
            .collect(Collectors.toList());
}
```

- **Stream**: Converts the list into a sequence of elements for processing.
- **map**: Transforms each element of the stream.
- **collect(Collectors.toList())**: Collects the transformed elements back into a list.

#### Detailed Steps
1. `employeeEntities.stream()`: Turns the list into a stream.
2. `.map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))`: Converts each `EmployeeEntity` to `EmployeeDTO`.
3. `.collect(Collectors.toList())`: Collects all `EmployeeDTO` objects into a list.

---

### Patch Method Using Reflection

#### ReflectionUtils

`ReflectionUtils` is a utility class in Spring Framework for working with the Java Reflection API. It can be used for:

- Inspecting and manipulating fields, methods, constructors of a class at runtime.
- Setting and getting field values, invoking methods dynamically.
- It's not directly visible in your code, but it might be used internally by Spring for operations like dependency injection, proxy creation, etc.

**Purpose:** Dynamically update specific fields of an entity using reflection.

#### Example: Patch Employee by ID
```java
public EmployeeDTO patchEmployeeById(Map<String, Object> updates, Long id) {
    if(!isExistsByEmployeeId(id)) return null; // Check if the employee exists
    EmployeeEntity employeeEntity = employeeRepository.findById(id).get(); // Fetch the employee

    // Iterate over the updates map and update the corresponding fields in the employee entity
    updates.forEach((field, value) -> {
        Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
        fieldToBeUpdated.setAccessible(true);
        ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
    });

    return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class); // Save and convert to DTO
}
```

#### Explanation of Reflection Steps
1. **Check Existence:**
   ```java
   if(!isExistsByEmployeeId(id)) return null;
   ```

2. **Fetch Employee Entity:**
   ```java
   EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
   ```

3. **Iterate Over Updates Map:**
   ```java
   updates.forEach((field, value) -> {
       // Find the field in EmployeeEntity by name
       Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
       // Make the field accessible for modification
       fieldToBeUpdated.setAccessible(true);
       // Set the new value for the field in the employee entity
       ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
   });
   ```

4. **Save and Convert:**
   ```java
   return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
   ```

#### Detailed Steps with Example
- **Updates map:** `{"name": "John Doe", "age": 30}`
- **Entity before update:** `name = "Jane Smith", age = 25`
- **Entity after update:** `name = "John Doe", age = 30`