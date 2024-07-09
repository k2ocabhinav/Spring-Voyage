package com.springvoyage.mvc_restful_api.controllers;

import com.springvoyage.mvc_restful_api.dto.EmployeeDTO;
import com.springvoyage.mvc_restful_api.entities.EmployeeEntity;
import com.springvoyage.mvc_restful_api.repositories.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/welcome")
    public String welcome() {
        return "Welcome to the Employees Portal";
    }

    @GetMapping
    public String welcome(@RequestParam(required = false, name = "name") String n) {
        return "EmployeeController.welcome " + n;
    }

//  ONLY FOR LEARNING
    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return EmployeeDTO.builder()
                .id(employeeId)
                .name("Abhinav")
                .age(25)
                .dateOfJoining(LocalDate.of(2024, 7, 7))
                .email("myemail@mail.com")
                .isActive(true)
                .build();

    }


    @PostMapping(path = "/post")
    public String postHello() {
        return "Hello from POST request";
    }

//    USING REPOSITORY TO WORK WITH ENTITIES
    @GetMapping(path = "/emp/{id}")
    public EmployeeEntity findEmployeeById(@PathVariable Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping(path = "/emp")
    public List<EmployeeEntity> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping(path = "/emp/create")
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeRepository.save(employeeEntity);
    }

}
