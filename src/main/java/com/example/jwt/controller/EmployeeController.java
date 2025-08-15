package com.example.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.jwt.dto.EmployeeDto;
import com.example.jwt.dto.LoginDto;
import com.example.jwt.filter.CustomUserDetailsService;
import com.example.jwt.service.EmployeeService;
import com.example.jwt.service.JwtService;
import com.example.jwt.structure.ResponseStructure;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Endpoint for employee registration
    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<String>> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        ResponseStructure<String> response = new ResponseStructure<>();
        try {
            // Check if the email already exists
            if (employeeService.existsByEmail(employeeDto.getEmail())) {
                response.setSuccess(false);
                response.setMessage("Email already registered");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // Check if the phone number already exists
            if (employeeService.existsByPhoneNumber(employeeDto.getPhoneNumber())) {
                response.setSuccess(false);
                response.setMessage("Phone number already registered");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // Register the employee
            EmployeeDto savedEmployee = employeeService.registerEmployee(employeeDto);

            response.setSuccess(true);
            response.setMessage("Employee registered successfully");
            response.setData("Employee registered with email: " + savedEmployee.getEmail());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("An error occurred: " + e.getMessage());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for employee login
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<LoginDto>> userLogin(@RequestBody LoginDto loginDto) {
        ResponseStructure<LoginDto> response = new ResponseStructure<>();

        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            // Load user details to access roles
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());

            // Get the role from the authorities
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            // Generate JWT token
            String token = jwtService.generateToken(loginDto.getEmail(), role);

            // Set token into response DTO
            loginDto.setToken(token);

            response.setSuccess(true);
            response.setMessage("Login successful");
            response.setData(loginDto);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException e) {
            response.setSuccess(false);
            response.setMessage("Invalid username or password");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("An error occurred: " + e.getMessage());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
