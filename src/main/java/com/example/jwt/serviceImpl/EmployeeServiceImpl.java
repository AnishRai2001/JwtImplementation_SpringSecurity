package com.example.jwt.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.Repository.EmployeeRepository;
import com.example.jwt.dto.EmployeeDto;
import com.example.jwt.entity.Employee;
import com.example.jwt.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public EmployeeDto registerEmployee(EmployeeDto dto) {
        // Check for existing email
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Employee already registered with email: " + dto.getEmail());
        }

        // Check for existing phone number
        if (employeeRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already in use: " + dto.getPhoneNumber());
        }

        // Convert DTO to Entity
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        employee.setPassword(encoder.encode(dto.getPassword()));

        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Convert back to DTO
        EmployeeDto resultDto = new EmployeeDto();
        BeanUtils.copyProperties(savedEmployee, resultDto);

        return resultDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDto> dtoList = new ArrayList<EmployeeDto>();

        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            EmployeeDto dto = new EmployeeDto();
            BeanUtils.copyProperties(employee, dto);
            dtoList.add(dto);
        }

        return dtoList;
    }

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return employeeRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return employeeRepository.existsByPhoneNumber(phoneNumber);
	}


}
