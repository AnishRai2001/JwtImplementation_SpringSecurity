package com.example.jwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
boolean existsByEmail(String email);
boolean existsByPhoneNumber(String phoneNumber);
Optional<Employee> findByEmail(String email);
}
