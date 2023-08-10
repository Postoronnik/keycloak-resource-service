package com.example.keycloakresourceservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String employeeName;
    private String employeeSalary;
    private String employeeDepartment;

}
