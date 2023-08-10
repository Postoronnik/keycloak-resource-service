package com.example.keycloakresourceservice.domain.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum EmployeeFields {

    EMPLOYEE_NAME("employeeName", "empl_name"),
    EMPLOYEE_SALARY("employeeSalary", "empl_salary"),
    EMPLOYEE_DEPARTMENT("employeeDepartment", "empl_department");

    private final String fieldName;
    private final String sqlName;

    public static Optional<String> getRequiredField(String fieldName) {
        final var requiredFiled = Arrays.stream(EmployeeFields.values())
                .filter(employeeFields -> employeeFields.getFieldName().equals(fieldName))
                .map(EmployeeFields::getSqlName)
                .collect(Collectors.joining());

        return Optional.of(requiredFiled);
    }
}
