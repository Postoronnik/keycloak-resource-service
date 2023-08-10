package com.example.keycloakresourceservice.service;

import com.example.keycloakresourceservice.domain.dto.EmployeeDto;
import com.example.keycloakresourceservice.domain.enums.sort.EmployeeFields;
import com.example.keycloakresourceservice.repository.EmployeeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final String ASC = "asc";
    private static final String DESC = "desc";

    private final EmployeeDao employeeDao;

    public List<EmployeeDto> getEmployeesByNameRegex(String nameRegex, String sort) {
        return employeeDao.findEmployeesByNameRegex(
                "%" + nameRegex + "%",
                prepareSort(sort)
        );
    }

    private String prepareSort(String sort) {
        final var sortCommands = sort.split(",");
        String completeSort = "";

        final var field = sortCommands[0];
        final var sqlFieldNameOptional = EmployeeFields.getRequiredField(field);
        completeSort += "ORDER BY " + sqlFieldNameOptional.orElseThrow(
                () -> new IllegalArgumentException("Field with name " + field + " is not supported")
        );

        if (sortCommands.length == 2) {
            final var sortMethod = sortCommands[1].toLowerCase();
            if (sortMethod.equalsIgnoreCase(ASC) || sortMethod.equalsIgnoreCase(DESC)) {
                completeSort += " " + sortMethod.toUpperCase();
            } else {
                throw new IllegalArgumentException("Sorting method " + sortMethod + " is not supported");
            }
        }

        return completeSort;
    }

}
