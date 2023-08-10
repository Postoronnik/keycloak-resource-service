package com.example.keycloakresourceservice.repository;

import com.example.keycloakresourceservice.domain.dto.EmployeeDto;

import java.util.List;


public interface EmployeeDao {
    List<EmployeeDto> findEmployeesByNameRegex(String name, String sortField);
}
