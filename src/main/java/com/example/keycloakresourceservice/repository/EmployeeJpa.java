package com.example.keycloakresourceservice.repository;

import com.example.keycloakresourceservice.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface EmployeeJpa extends JpaRepository<Employee, UUID> {

    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();
}
