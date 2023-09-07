package com.example.keycloakresourceservice.repository;

import com.example.keycloakresourceservice.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface EmployeeJpa extends JpaRepository<Employee, UUID> {

    @Query("SELECT e FROM Employee e WHERE e.employeeDepartment = :department")
    List<Employee> findAllEmployees(@Param("department") String department);
}
