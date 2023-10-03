package com.example.keycloakresourceservice.controller;

import com.example.keycloakresourceservice.domain.Employee;
import com.example.keycloakresourceservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search")
    public List<Employee> getEmployees() {
        return employeeService.getEmployeesByDepartment();
    }

    @GetMapping("/search/departments")
    public List<Employee> getEmployeesByDepartments() {
        return employeeService.getEmployeesByDepartments();
    }

}
