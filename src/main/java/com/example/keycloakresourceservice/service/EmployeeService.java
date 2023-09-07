package com.example.keycloakresourceservice.service;

import com.example.keycloakresourceservice.domain.Employee;
import com.example.keycloakresourceservice.repository.EmployeeJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeJpa employeeJpa;

    public List<Employee> findAllEmployees() {
        return employeeJpa.findAllEmployees();
    }
}
