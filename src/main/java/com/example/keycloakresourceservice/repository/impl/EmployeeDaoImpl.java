package com.example.keycloakresourceservice.repository.impl;

import com.example.keycloakresourceservice.domain.dto.EmployeeDto;
import com.example.keycloakresourceservice.repository.EmployeeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EmployeeDto> findEmployeesByNameRegex(
            final String name,
            final String sort
    ) {

        return null;
    }
}
