package com.example.jwt.service;

import java.util.List;

import com.example.jwt.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto registerEmployee(EmployeeDto employeeDto);

	List<EmployeeDto> getAllEmployees();

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);
}
