package com.example.keycloakresourceservice.repository.impl;

import com.example.keycloakresourceservice.domain.dto.EmployeeDto;
import com.example.keycloakresourceservice.repository.EmployeeDao;
import com.example.keycloakresourceservice.service.SecurityContextHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {
    private final static String SELECT_EMPLOYEES = """ 
            SELECT *
            FROM employee
            WHERE empl_department IN ('%s')
           
           """;
    private final static String SEARCH_BY_NAME = "AND LOWER(empl_name) LIKE LOWER('%s')\n";
    private final static String SORT_ORDER = "%s\n";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EmployeeDto> findEmployeesByNameRegex(
            final String name,
            final String sort
    ) {

        return jdbcTemplate.query(
                prepareSqlRequest(name, sort, SecurityContextHelper.getDepartment()),
                (ResultSet rs, int rowNum) -> new EmployeeDto(
                        rs.getString("empl_name"),
                        rs.getString("empl_salary"),
                        rs.getString("empl_department")
                )
        );
    }

    private String prepareSqlRequest(
            final String name,
            final String sort,
            final String department
    ) {
        if (department == null) {
            throw new RuntimeException("User does not belong to department");
        }

        String query = SELECT_EMPLOYEES.formatted(department);

        if (name != null) {
            query += SEARCH_BY_NAME.formatted(name);
            if (sort != null) {
                query += SORT_ORDER.formatted(sort);
            }
        }

        query += ";";

        return query;
    }
}
