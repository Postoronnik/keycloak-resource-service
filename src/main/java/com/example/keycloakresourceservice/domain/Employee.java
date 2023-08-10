package com.example.keycloakresourceservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "empl_name")
    private String employeeName;

    @Column(name = "empl_salary")
    private String employeeSalary;

    @Column(name = "empl_department")
    private String employeeDepartment;

}
