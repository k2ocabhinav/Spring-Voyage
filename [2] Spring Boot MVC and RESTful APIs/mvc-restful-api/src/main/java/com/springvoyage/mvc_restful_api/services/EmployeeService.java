package com.springvoyage.mvc_restful_api.services;

import com.springvoyage.mvc_restful_api.dto.EmployeeDTO;
import com.springvoyage.mvc_restful_api.entities.EmployeeEntity;
import com.springvoyage.mvc_restful_api.repositories.EmployeeRepository;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    public boolean isExistsByEmployeeId(Long id){
        return employeeRepository.existsById(id);
    }


    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);

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
        if(!isExistsByEmployeeId(id)) return false;
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO patchEmployeeById(Map<String, Object> updates, Long id) {
        if(!isExistsByEmployeeId(id)) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
