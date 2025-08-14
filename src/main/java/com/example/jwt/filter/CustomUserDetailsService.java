package com.example.jwt.filter;

import com.example.jwt.Repository.EmployeeRepository;
import com.example.jwt.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Employee> user = employeeRepository.findByEmail(email);
		if(user.isPresent()) {
			   return new User(email, user.get().getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.get().getRole())));
		}
		   else {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	}
	}
}



