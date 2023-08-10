package com.example.keycloakresourceservice.controller;

import com.example.keycloakresourceservice.domain.dto.EmployeeDto;
import com.example.keycloakresourceservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search")
    public List<EmployeeDto> getEmployeesByRegexName(
            @RequestParam(name = "name") String nameRegex,
            @RequestParam(name = "sort") String sort
    ) {
        return employeeService.getEmployeesByNameRegex(nameRegex, sort);
    }
}
