package com.springvoyage.mvc_restful_api.services;

import com.springvoyage.mvc_restful_api.dto.EmployeeDTO;
import com.springvoyage.mvc_restful_api.entities.EmployeeEntity;
import com.springvoyage.mvc_restful_api.exceptions.ResourceNotFoundException;
import com.springvoyage.mvc_restful_api.repositories.EmployeeRepository;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    public void isExistsByEmployeeId(Long id) throws ResourceNotFoundException {
        if(!employeeRepository.existsById(id)){
            throw new ResourceNotFoundException(STR."Employee not found with ID: \{id}");
        }
    }


    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class));

    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream()
                .map(EmployeeEntity -> modelMapper.map(EmployeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO save(EmployeeDTO inputemployeeDTO) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputemployeeDTO, EmployeeEntity.class);
        EmployeeEntity savedEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        EmployeeEntity toUpdateEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        toUpdateEntity.setId(id);
        EmployeeEntity updatedEntity = employeeRepository.save(toUpdateEntity);
        return modelMapper.map(updatedEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id) {
        isExistsByEmployeeId(id);
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO patchEmployeeById(Map<String, Object> updates, Long id) {
        // Check if the employee exists
        isExistsByEmployeeId(id);
        // Fetch the employee entity from the repository
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();

        // Iterate over the updates map and update the corresponding fields in the employee entity
/*      ReflectionUtils.findRequiredField: Finds a field in the EmployeeEntity class by its name.
        setAccessible(true): Makes the field accessible for modification, even if it is private.
        ReflectionUtils.setField: Sets the value of the specified field in the given object to the provided value.*/
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });

        // Save the updated employee entity and convert it to an EmployeeDTO
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

}
